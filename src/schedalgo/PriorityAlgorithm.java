package schedalgo;
import java.util.*;

public class PriorityAlgorithm implements SchedulingAlgorithm {
    private List<GanttChartEntry> ganttChart = new ArrayList<>();
    private double averageTurnaroundTime;
    private double averageWaitingTime;

    @Override
    public void schedule(List<Process> processes) {
        // Sort processes by priority (lower priority value means higher priority)
        processes.sort(Comparator.comparingInt(Process::getPriority));

        int currentTime = 0;
        for (Process process : processes) {
            int startTime = Math.max(currentTime, process.getArrivalTime());
            int endTime = startTime + process.getCpuBurstTime();
            ganttChart.add(new GanttChartEntry(String.valueOf(process.getProcessID()), startTime, endTime));
            process.setCompletionTime(endTime);

            currentTime = endTime;
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
