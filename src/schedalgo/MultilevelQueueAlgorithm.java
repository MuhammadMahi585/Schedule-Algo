package schedalgo;

import java.util.*;

public class MultilevelQueueAlgorithm implements SchedulingAlgorithm {
    private List<GanttChartEntry> ganttChart = new ArrayList<>();
    private double averageTurnaroundTime;
    private double averageWaitingTime;
    private static final int TIME_QUANTUM = 4; // Example time quantum for round-robin scheduling

    @Override
    public void schedule(List<Process> processes) {
        Map<Integer, Queue<Process>> queues = new HashMap<>();
        for (Process process : processes) {
            queues.computeIfAbsent(process.getPriority(), k -> new LinkedList<>()).add(process);
        }

        int currentTime = 0;
        for (int priority : queues.keySet()) {
            Queue<Process> queue = queues.get(priority);
            while (!queue.isEmpty()) {
                Process process = queue.poll();
                int startTime = currentTime;

                if (priority == 3) {
                    // FCFS for priority 3
                    int burstTime = process.getCpuBurstTime();
                    currentTime += burstTime;
                    process.setCpuBurstTime(0); // Process completes
                } else {
                    // Round-Robin for other priorities
                    int burstTime = Math.min(process.getCpuBurstTime(), TIME_QUANTUM);
                    currentTime += burstTime;
                    process.setCpuBurstTime(process.getCpuBurstTime() - burstTime);
                }

                int endTime = currentTime;
                ganttChart.add(new GanttChartEntry(String.valueOf(process.getProcessID()), startTime, endTime));
                process.setCompletionTime(endTime);

                if (process.getCpuBurstTime() > 0 && priority != 3) {
                    queue.add(process); // Add back to queue if not finished and not priority 3
                }
            }
        }

        calculateMetrics(processes);
    }

    private void calculateMetrics(List<Process> processes) {
        int totalTurnaroundTime = 0;
        int totalWaitingTime = 0;
        for (Process process : processes) {
            int turnaroundTime = process.getCompletionTime() - process.getArrivalTime();
            int waitingTime = turnaroundTime - process.getOriginalCpuBurstTime();
            totalTurnaroundTime += turnaroundTime;
            totalWaitingTime += waitingTime;
        }
        averageTurnaroundTime = (double) totalTurnaroundTime / processes.size();
        averageWaitingTime = (double) totalWaitingTime / processes.size();
    }

    @Override
    public List<GanttChartEntry> getGanttChart() {
        return ganttChart;
    }

    @Override
    public double getAverageTurnaroundTime() {
        return averageTurnaroundTime;
    }

    @Override
    public double getAverageWaitingTime() {
        return averageWaitingTime;
    }
}
