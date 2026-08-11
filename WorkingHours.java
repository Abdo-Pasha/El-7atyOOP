public class WorkingHours {
    private String startTime; // e.g., "09:00 AM"
    private String endTime;   // e.g., "05:00 PM"

    public WorkingHours(String startTime, String endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getStartTime() { return startTime; }
    public void setStartTime(String startTime) { this.startTime = startTime; }
    public String getEndTime() { return endTime; }
    public void setEndTime(String endTime) { this.endTime = endTime; }

    @Override
    public String toString() {
        return startTime + " - " + endTime;
    }
}