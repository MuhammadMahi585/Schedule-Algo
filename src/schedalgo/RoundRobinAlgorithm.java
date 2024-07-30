package schedalgo;

import java.util.*;

public class RoundRobinAlgorithm implements SchedulingAlgorithm {
    private List<GanttChartEntry> ganttChart = new ArrayList<>();
    private double averageTurnaroundTime;
    private double averageWaitingTime;
    private static final int TIME_QUANTUM = 6; // Set time quantum to 4 msec as per given scenario

    @Override
    public void schedule(List<Process> processes) {
        int currentTime = 0;
        Queue<Process> readyQueue = new LinkedList<>();
        Map<Integer, Integer> remainingBurstTimes = new HashMap<>();

        // Initialize remaining burst times map and ready queue
        for (Process process : processes) {
            remainingBurstTimes.put(process.getProcessID(), process.getCpuBurstTime());
            readyQueue.add(process); // Add all processes to the ready queue initially
        }

        while (!readyQueue.isEmpty()) {
            Process process = readyQueue.poll();
            int startTime = currentTime;
            int burstTime = Math.min(remainingBurstTimes.get(process.getProcessID()), TIME_QUANTUM);
            currentTime = startTime + burstTime;
            remainingBurstTimes.put(process.getProcessID(), remainingBurstTimes.get(process.getProcessID()) - burstTime);

            // Check if the process is not completed and needs to be added back to the queue
            if (remainingBurstTimes.get(process.getProcessID()) > 0) {
                readyQueue.add(process);
            } else {
                process.setCompletionTime(currentTime);
            }

            int endTime = currentTime;
            ganttChart.add(new GanttChartEntry(String.valueOf(process.getProcessID()), startTime, endTime));
        }

        calculateMetrics(processes); // Calculate metrics using executed processes
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
