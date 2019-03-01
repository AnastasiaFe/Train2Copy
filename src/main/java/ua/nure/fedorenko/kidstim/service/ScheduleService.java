package ua.nure.fedorenko.kidstim.service;

import ua.nure.fedorenko.kidstim.model.entity.Assignment;
import ua.nure.fedorenko.kidstim.model.entity.Schedule;

import java.time.LocalDate;

public interface ScheduleService {

    Schedule findActiveScheduleForChild(String childId);

    Schedule generateSchedule(LocalDate startDay, String childId, int... capacities);

    Assignment findAssignmentByDay(String dayId);
}
