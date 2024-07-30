package schedalgo;

public class Process {
    private int processID;
    private int arrivalTime;
    private int priority;
    private int cpuBurstTime;
    private int originalCpuBurstTime; // Add original CPU burst time
    private int completionTime; // Add completion time

    public Process(int processID, int arrivalTime, int priority, int cpuBurstTime) {
        this.processID = processID;
        this.arrivalTime = arrivalTime;
        this.priority = priority;
        this.cpuBurstTime = cpuBurstTime;
        this.originalCpuBurstTime = cpuBurstTime; // Store original CPU burst time
    }

    public int getProcessID() {
        return processID;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getPriority() {
        return priority;
    }

    public int getCpuBurstTime() {
        return cpuBurstTime;
    }

    public void setCpuBurstTime(int cpuBurstTime) {
        this.cpuBurstTime = cpuBurstTime;
    }

    public int getOriginalCpuBurstTime() {
        return originalCpuBurstTime;
    }

    public int getCompletionTime() {
        return completionTime;
    }

    public void setCompletionTime(int completionTime) {
        this.completionTime = completionTime;
    }

    @Override
    public String toString() {
        return String.format("ProcessID: %d, ArrivalTime: %d, Priority: %d, CPUBurstTime: %d",
                             processID, arrivalTime, priority, originalCpuBurstTime);
    }
}

