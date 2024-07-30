/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package schedalgo;

import java.util.List;

public interface SchedulingAlgorithm {
    void schedule(List<Process> processes);
    List<GanttChartEntry> getGanttChart();
    double getAverageTurnaroundTime();
    double getAverageWaitingTime();
}
