package ua.nure.fedorenko.kidstim.planning;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.nure.fedorenko.kidstim.model.entity.Task;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Knapsack {

    private static final Logger LOG = LoggerFactory.getLogger(Knapsack.class);
    private Task[] tasks;

    private int capacity;

    public Knapsack(@NotNull Task[] tasks, int capacity) {
        this.tasks = tasks;
        this.capacity = capacity;
    }


    // we write the solve algorithm
    public Solution solve() {
        logInputData();
        int numberOfItems = tasks.length;
        // we use a matrix to store the max value at each n-th item
        int[][] matrix = new int[numberOfItems + 1][capacity + 1];

        // first line is initialized to 0
        for (int i = 0; i <= capacity; i++)
            matrix[0][i] = 0;

        // we iterate on tasks
        for (int i = 1; i <= numberOfItems; i++) {
            // we iterate on each capacity
            for (int j = 0; j <= capacity; j++) {
                if (tasks[i - 1].getDuration() > j)
                    matrix[i][j] = matrix[i - 1][j];
                else
                    // we maximize value at this rank in the matrix
                    matrix[i][j] = Math.max(matrix[i - 1][j], matrix[i - 1][j - tasks[i - 1].getDuration()]
                            + tasks[i - 1].getPoints());
            }
        }

        int res = matrix[numberOfItems][capacity];
        int w = capacity;
        List<Task> itemsSolution = new ArrayList<>();

        for (int i = numberOfItems; i > 0 && res > 0; i--) {
            if (res != matrix[i - 1][w]) {
                itemsSolution.add(tasks[i - 1]);
                // we remove tasks value and weight
                res -= tasks[i - 1].getPoints();
                w -= tasks[i - 1].getDuration();
            }
        }
        int value = matrix[numberOfItems][capacity];
        logSolution(itemsSolution, value);
        return new Solution(itemsSolution, value);
    }

    private void logInputData() {
        if (tasks != null && tasks.length > 0) {
            LOG.info("Knapsack problem");
            LOG.info("Capacity : {}", capacity);
            LOG.info("Items :");
            for (Task task : tasks) {
                LOG.info("{}", task);
            }
        }
    }

    private void logSolution(List<Task> itemsSolution, int value) {
        if (!itemsSolution.isEmpty()) {
            LOG.info("\nKnapsack solution");
            LOG.info("Value = {} ", value);
            LOG.info("Items to pick :");

            for (Task task : itemsSolution) {
                LOG.info("{}", task);
            }
        }
    }

    public static void main(String[] args) {
        // we take the same instance of the problem displayed in the image
        Task[] jobs = new Task[7];
        jobs[0] = new Task("1", "wash dishes", 20, 20, LocalDate.of(2019, 2, 21));
        jobs[1] = new Task("2", "math", 50, 60, LocalDate.of(2019, 2, 22));
        jobs[2] = new Task("3", "english", 70, 50, LocalDate.of(2019, 2, 22));
        jobs[3] = new Task("4", "cook", 30, 20, LocalDate.of(2019, 2, 21));
        jobs[4] = new Task("5", "read", 20, 30, LocalDate.of(2019, 2, 23));
        jobs[5] = new Task("6", "feed cat", 10, 15, LocalDate.of(2019, 2, 23));
        jobs[6] = new Task("7", "help", 40, 35, LocalDate.of(2019, 2, 21));

        Knapsack knapsack = new Knapsack(jobs, 70);
        knapsack.solve();
    }
}