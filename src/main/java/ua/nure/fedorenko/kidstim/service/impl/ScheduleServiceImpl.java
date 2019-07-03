package ua.nure.fedorenko.kidstim.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.nure.fedorenko.kidstim.exception.NotMeetsDeadlineException;
import ua.nure.fedorenko.kidstim.model.entity.*;
import ua.nure.fedorenko.kidstim.model.repository.AssignmentRepository;
import ua.nure.fedorenko.kidstim.model.repository.ScheduleRepository;
import ua.nure.fedorenko.kidstim.planning.Knapsack;
import ua.nure.fedorenko.kidstim.service.ChildService;
import ua.nure.fedorenko.kidstim.service.ScheduleService;
import ua.nure.fedorenko.kidstim.service.TaskService;
import ua.nure.fedorenko.kidstim.service.date.DateUtils;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private ChildService childService;
    @Autowired
    private TaskService taskService;

    @Override
    public Schedule findActiveScheduleForChild(String childId) {
        return scheduleRepository.findActiveScheduleForChild(childId);
    }

    @Override
    public Schedule generateSchedule(LocalDateTime startDay, String childId, int... capacities) {
        Schedule schedule = new Schedule();
        List<Day> days = getDaysForScheduling(startDay, capacities);
        //Task[] allTasksOfChild = taskService.getTasksByChild(childService.getChildById(childId)).toArray(new Task[0]);
        Task[] jobs = new Task[7];
        jobs[0] = new Task("1", "wash dishes", 20, 20, DateUtils.getMillisFromTime(LocalDateTime.of(2030, 3, 8, 23, 23)));
        jobs[0].setMinFrequency(3);
        jobs[1] = new Task("2", "math", 50, 60, DateUtils.getMillisFromTime(LocalDateTime.of(2019, 3, 15, 3, 23)));
        jobs[2] = new Task("3", "english", 70, 50, DateUtils.getMillisFromTime(LocalDateTime.of(2019, 3, 13, 4, 30)));
        jobs[3] = new Task("4", "cook", 30, 20, DateUtils.getMillisFromTime(LocalDateTime.of(2030, 3, 8, 5, 24)));
        jobs[3].setMinFrequency(3);
        jobs[3].setMaxFrequency(5);
        jobs[4] = new Task("5", "read", 20, 30, DateUtils.getMillisFromTime(LocalDateTime.of(2019, 3, 13, 6, 35)));
        jobs[5] = new Task("6", "feed cat", 10, 15, DateUtils.getMillisFromTime(LocalDateTime.of(2030, 3, 8, 8, 9)));
        jobs[5].setMinFrequency(4);
        jobs[6] = new Task("7", "help", 40, 35, DateUtils.getMillisFromTime(LocalDateTime.of(2019, 3, 19, 12, 12)));
        int totalLoadForDays = getTotalLoadForDays(days);
        List<Task> tasksWithFrequencies = new ArrayList<>();
        for (Task task : jobs) {
            putElementMultipleTimesToList(tasksWithFrequencies, task, task.getMinFrequency());
        }

        List<Task> tasksWithBiggestProfit = new Knapsack(tasksWithFrequencies.toArray(new Task[0]), totalLoadForDays).solve().getItems();

        Set<Task> set = new HashSet<>(tasksWithBiggestProfit);

        List<Task> tasksToSchedule = new LinkedList<>(set);
        tasksToSchedule.sort(Comparator.comparing(Task::getExpirationDate));
        Map<Day, List<Task>> table = getSchedule(days, tasksToSchedule);

        for (Map.Entry<Day, List<Task>> entry : table.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getKey().getRealCapacity());
            System.out.println("Tasks --->" + entry.getValue());
        }

        return schedule;
    }

    private Map<Day, List<Task>> getSchedule(List<Day> days, List<Task> tasksToSchedule) {
        Map<Day, List<Task>> table = new HashMap<>();
        int indexToRetrieve = 0;

        for (Day day : days) {
            while (day.getRealCapacity() <= day.getMaxCapacity()) {
                if (indexToRetrieve < tasksToSchedule.size()) {
                    Task task = tasksToSchedule.get(indexToRetrieve);
                    boolean isScheduled = false;
                    try {
                        isScheduled = tryPutIntoDay(task, day);
                    } catch (NotMeetsDeadlineException e) {
                        tasksToSchedule.remove(indexToRetrieve);
                    }
                    indexToRetrieve = processSchedulingResult(isScheduled, task, indexToRetrieve, tasksToSchedule);
                } else {
                    break;
                }
            }
            indexToRetrieve = 0;
            table.put(day, day.getAssignedTasks());

        }


        return table;
    }

    private int processSchedulingResult(boolean isScheduled, Task task, int indexToRetrieve, List<Task> tasksToSchedule) {
        if (isScheduled) {
            if (isDeadlined(task)) {
                tasksToSchedule.remove(indexToRetrieve);
            } else {
                indexToRetrieve++;
            }
        } else {
            indexToRetrieve++;
        }
        return indexToRetrieve;
    }

    private boolean tryPutIntoDay(Task task, Day day) throws NotMeetsDeadlineException {
        //check for deadline
        if (isDeadlined(task)) {
            return processTaskWithDeadline(task, day);
        } else {
            //task has no deadline but frequency
            return processTaskWithFrequency(task, day);
        }
    }

    private boolean processTaskWithDeadline(Task task, Day day) throws NotMeetsDeadlineException {
        LocalDateTime deadline = DateUtils.getTimeFromLong(task.getExpirationDate());

        LocalDateTime dayDate = DateUtils.getTimeFromLong(day.getDate());
        if (isMeetDeadline(deadline, dayDate)) {
            if (isFitIntoDay(task, day)) {
                addTaskIntoSchedule(task, day);
                return true;
            } else {
                return false;
            }
        } else {
            throw new NotMeetsDeadlineException();
        }
    }

    private boolean processTaskWithFrequency(Task task, Day day) {
        if (isFrequencyTaskFitsDay(task, day)) {
            addTaskIntoSchedule(task, day);
            task.setScheduledFrequency(task.getScheduledFrequency() + 1);
            return true;
        } else {
            return false;
        }
    }

    private boolean isFrequencyTaskFitsDay(Task task, Day day) {
        return task.getScheduledFrequency() < task.getMinFrequency() && isFitIntoDay(task, day) && !day.getAssignedTasks().contains(task);
    }

    private List<Task> getAdditionalTasks(List<Task> tasksToSchedule) {
        List<Task> additionalTasks = new ArrayList<>();
        for (Task task : tasksToSchedule) {
            if (task.getMaxFrequency() > 0) {
                task.setPoints(task.getPoints() / 2 + task.getPoints());
                additionalTasks.add(task);
            }
        }
        return additionalTasks;

    }

    private boolean isDeadlined(Task task) {
        return task.getMinFrequency() == 1 && task.getMaxFrequency() == 0;
    }

    private void addTaskIntoSchedule(Task task, Day day) {
        day.getAssignedTasks().add(task);
        int realCapacity = day.getRealCapacity() + task.getDuration();
        day.setRealCapacity(realCapacity);
    }

    private boolean isFitIntoDay(Task task, Day day) {
        return day.getRealCapacity() + task.getDuration() <= day.getMaxCapacity();
    }

    private boolean isMeetDeadline(LocalDateTime deadline, LocalDateTime dayDate) {
        return deadline.isAfter(dayDate) || deadline.isEqual(dayDate);
    }

    public static void main(String[] args) {
        new ScheduleServiceImpl().generateSchedule(LocalDateTime.now(), "1", 50, 80, 40, 160, 45, 70);
    }

    // TODO: 2/21/2019 implement frequencies features
    private Task[] getTasksByChildWithFrequencies(String childId) {
        Child child = childService.getChildById(childId);
        List<Task> allTasksOfChild = taskService.getTasksByChild(child);
        List<Task> tasksWithFrequencies = new ArrayList<>();
        for (Task task : allTasksOfChild) {
            putElementMultipleTimesToList(tasksWithFrequencies, task, task.getMinFrequency());
        }
        return tasksWithFrequencies.toArray(new Task[0]);
    }

    @Override
    public Assignment findAssignmentByDay(String dayId) {
        return assignmentRepository.findByDay(dayId);
    }

    private int getLoadByDay(Day day) {
        List<Task> tasks = findAssignmentByDay(day.getId()).getTasks();
        return tasks.stream()
                .mapToInt(Task::getDuration)
                .sum();
    }

    private int getTotalLoadForDays(List<Day> days) {
        return days.stream()
                .mapToInt(Day::getMaxCapacity)
                .sum();
    }

    private static List<Day> getDaysForScheduling(LocalDateTime start, int... capacities) {
        List<Day> daysForScheduling = new ArrayList<>();
        for (int i = 0; i < capacities.length; i++) {
            daysForScheduling.add(new Day(String.valueOf(i), DateUtils.getMillisFromTime(start.plusDays(i)), capacities[i]));
        }
        return daysForScheduling;
    }


    private void putElementMultipleTimesToList(List<Task> list, Task task, int min) {
        int i = 0;
        while (i < min) {
            list.add(task);
            i++;
        }
    }


}
