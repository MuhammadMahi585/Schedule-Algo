package schedalgo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FCFSAlgorithm implements SchedulingAlgorithm {
    private List<GanttChartEntry> ganttChart = new ArrayList<>();
    private double averageTurnaroundTime;
    private double averageWaitingTime;

    @Override
    public void schedule(List<Process> processes) {
        Collections.sort(processes, Comparator.comparingInt(Process::getArrivalTime));
        int currentTime = 0;
        for (Process process : processes) {
            int startTime = currentTime;
            currentTime = Math.max(currentTime, process.getArrivalTime()) + process.getCpuBurstTime();
            int endTime = currentTime;

            ganttChart.add(new GanttChartEntry(String.valueOf(process.getProcessID()), startTime, endTime));
            process.setCompletionTime(endTime);
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
