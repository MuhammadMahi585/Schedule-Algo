package schedalgo;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlgorithmFrame extends JFrame {
    public AlgorithmFrame(List<GanttChartEntry> ganttChart, double avgTurnaroundTime, double avgWaitingTime) {
        setTitle("Gantt Chart");
        setSize(1600, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        GanttChartPanel ganttChartPanel = new GanttChartPanel(ganttChart);
        add(ganttChartPanel, BorderLayout.CENTER);

        JPanel metricsPanel = new JPanel();
        metricsPanel.setLayout(new GridLayout(2, 1));
        metricsPanel.add(new JLabel("Average Turnaround Time: " + avgTurnaroundTime));
        metricsPanel.add(new JLabel("Average Waiting Time: " + avgWaitingTime));
        add(metricsPanel, BorderLayout.SOUTH);
    }
}

class GanttChartPanel extends JPanel {
    private List<GanttChartEntry> ganttChart;
    private Map<Integer, Color> processColors;

    public GanttChartPanel(List<GanttChartEntry> ganttChart) {
        this.ganttChart = ganttChart;
        this.processColors = new HashMap<>();
        initializeProcessColors();
        setPreferredSize(new Dimension(1600, 800)); // Adjust as needed
        setOpaque(false); // Make the Gantt chart panel transparent
    }

    private void initializeProcessColors() {
        // Define your hex colors here for each process ID
        processColors.put(1, Color.decode("#ffcea3")); // Example hex color
        processColors.put(2, Color.decode("#ffcea3")); // Example hex color
        processColors.put(3, Color.decode("#ffcea3")); // Example hex color
        // Add more colors as needed
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int y = 50; // Initial y position
        int height = 65; // Height of each block
        int startX = 50; // Initial x position
        int scaleFactor = 30; // Scale factor for the length of each block (1 time unit = 30 pixels)

        for (GanttChartEntry entry : ganttChart) {
            int startXPosition = startX + entry.getStartTime() * scaleFactor;
            int width = (entry.getEndTime() - entry.getStartTime()) * scaleFactor;

            // Get color for the process
            Color color = processColors.getOrDefault(Integer.parseInt(entry.getPid()), Color.decode("#FFDAB9"));
            g.setColor(color);
            g.fillRect(startXPosition, y, width, height);

            g.setColor(Color.BLACK);
            g.drawRect(startXPosition, y, width, height);
            g.drawString("P" + entry.getPid(), startXPosition + width / 2 - 10, y + height / 2 - 10);
            g.drawString("Start: " + entry.getStartTime() + ", End: " + entry.getEndTime(),
                    startXPosition + width / 2 - 30, y + height / 2 + 10);
            y += height + 5; // Space between blocks
        }
    }
}
