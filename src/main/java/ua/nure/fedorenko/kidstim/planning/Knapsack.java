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

    public Solution solve() {
        logInputData();
        int numberOfItems = tasks.length;
        int[][] matrix = new int[numberOfItems + 1][capacity + 1];

        for (int i = 0; i <= capacity; i++) {
            matrix[0][i] = 0;
        }

        for (int i = 1; i <= numberOfItems; i++) {

            for (int j = 0; j <= capacity; j++) {
                if (tasks[i - 1].getDuration() > j) {
                    matrix[i][j] = matrix[i - 1][j];
                }
                else {
                    matrix[i][j] = Math.max(matrix[i - 1][j], matrix[i - 1][j - tasks[i - 1].getDuration()]
                            + tasks[i - 1].getPoints());
                }
            }
        }

        int res = matrix[numberOfItems][capacity];
        int w = capacity;
        List<Task> itemsSolution = new ArrayList<>();

        for (int i = numberOfItems; i > 0 && res > 0; i--) {
            if (res != matrix[i - 1][w]) {
                itemsSolution.add(tasks[i - 1]);
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


}