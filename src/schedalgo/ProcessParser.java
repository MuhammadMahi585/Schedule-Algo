package schedalgo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProcessParser {

    public static List<Process> parseInputFile(String filePath) {
        List<Process> processes = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstLine = true;

            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    // Skip header line
                    isFirstLine = false;
                    continue;
                }
                line = line.trim();
                if (!line.isEmpty()) {
                    String[] parts = line.split("\\s+");
                    if (parts.length == 4) {
                        int processID = Integer.parseInt(parts[0]);
                        int arrivalTime = Integer.parseInt(parts[1]);
                        int priority = Integer.parseInt(parts[2]);
                        int cpuBurstTime = Integer.parseInt(parts[3]);
                        processes.add(new Process(processID, arrivalTime, priority, cpuBurstTime));
                    } else {
                        System.out.println("Error: Invalid format in line: " + line);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return processes;
    }

    public static void printProcesses(List<Process> processes) {
        if (processes.isEmpty()) {
            System.out.println("No processes found.");
        } else {
            System.out.println("Parsed Process Information:");
            for (Process process : processes) {
                System.out.println(process);
            }
        }
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java ProcessParser <input_file_path>");
            return;
        }
        String filePath = args[0];
        List<Process> processes = parseInputFile(filePath);
        printProcesses(processes);
    }
}
