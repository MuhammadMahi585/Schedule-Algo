package schedalgo;

import schedalgo.Process;  // Import the Process class
import schedalgo.ProcessParser;  // Import the ProcessParser class

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

public class SchedulerFrame extends JFrame {

    private JComboBox<String> algorithmComboBox;
    private JButton selectFileButton;
    private JLabel selectedFileLabel;
    private JButton proceedButton;
    private File selectedFile;

    public SchedulerFrame() {
        setTitle("Process Scheduling Simulator");
        setSize(1600, 1600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(1600, 1600));
        setContentPane(layeredPane);

        JLabel backgroundLabel = new JLabel();
        backgroundLabel.setIcon(new ImageIcon("C:\\Users\\shahi\\Downloads\\11.jpg")); // Set your background image path
        backgroundLabel.setBounds(0, 0, 1600, 1600);
        layeredPane.add(backgroundLabel, JLayeredPane.DEFAULT_LAYER);

        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        String[] algorithms = {"FCFS", "SJF", "RR", "Priority Scheduling", "Multilevel Queue Scheduling"};
        algorithmComboBox = new JComboBox<>(algorithms);
        selectFileButton = new JButton("Select Input File");
        selectedFileLabel = new JLabel("No file selected");
        proceedButton = new JButton("Proceed");

        // Change the background and text color of the buttons using hex values
        selectFileButton.setBackground(Color.decode("#ffcea3")); // Blue
        selectFileButton.setForeground(Color.decode("#110D0C")); // White
        proceedButton.setBackground(Color.decode("#ffcea3")); // Green
        proceedButton.setForeground(Color.decode("#110D0C")); // White

        algorithmComboBox.setFont(new Font("Andalus", Font.PLAIN, 18));
        selectFileButton.setFont(new Font("Andalus", Font.PLAIN, 18));
        selectedFileLabel.setFont(new Font("Andalus", Font.PLAIN, 18));
        proceedButton.setFont(new Font("Andalus", Font.PLAIN, 18));

        selectFileButton.setPreferredSize(new Dimension(500, 100));
        proceedButton.setPreferredSize(new Dimension(500, 100));
        algorithmComboBox.setPreferredSize(new Dimension(500, 100));
        selectedFileLabel.setPreferredSize(new Dimension(500, 100));

        selectFileButton.setHorizontalTextPosition(SwingConstants.CENTER);
        selectFileButton.setVerticalTextPosition(SwingConstants.CENTER);
        proceedButton.setHorizontalTextPosition(SwingConstants.CENTER);
        proceedButton.setVerticalTextPosition(SwingConstants.CENTER);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Select Scheduling Algorithm:", SwingConstants.CENTER), gbc);
        gbc.gridy++;
        panel.add(algorithmComboBox, gbc);
        gbc.gridy++;
        panel.add(selectFileButton, gbc);
        gbc.gridy++;
        panel.add(selectedFileLabel, gbc);
        gbc.gridy++;
        panel.add(proceedButton, gbc);

        layeredPane.add(panel, JLayeredPane.PALETTE_LAYER);
        panel.setBounds(500, 200, 400, 400);

        selectFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    selectedFile = fileChooser.getSelectedFile();
                    selectedFileLabel.setText("Selected file: " + selectedFile.getName());
                }
            }
        });

        proceedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedFile != null) {
                    String selectedAlgorithm = (String) algorithmComboBox.getSelectedItem();
                    List<Process> processes = ProcessParser.parseInputFile(selectedFile.getAbsolutePath());
                    SchedulingAlgorithm algorithm = getSchedulingAlgorithm(selectedAlgorithm);
                    algorithm.schedule(processes);

                    AlgorithmFrame algorithmFrame = new AlgorithmFrame(
                            algorithm.getGanttChart(),
                            algorithm.getAverageTurnaroundTime(),
                            algorithm.getAverageWaitingTime()
                    );
                    algorithmFrame.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Please select an input file.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private SchedulingAlgorithm getSchedulingAlgorithm(String algorithm) {
    switch (algorithm) {
        case "FCFS":
            return new FCFSAlgorithm();
        case "SJF":
            return new ShortestJobFirstAlgorithm();
        case "RR":
            return new RoundRobinAlgorithm();
        case "Priority Scheduling":
            return new PriorityAlgorithm(); // Return PriorityAlgorithm instance
        case "Multilevel Queue Scheduling":
            return new MultilevelQueueAlgorithm();
        default:
            throw new IllegalArgumentException("Invalid algorithm selected");
    }
}


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SchedulerFrame().setVisible(true);
            }
        });
    }
}
