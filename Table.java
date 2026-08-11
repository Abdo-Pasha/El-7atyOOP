public class Table {
    private int tableNumber;
    private int capacity;
    private String status; // "AVAILABLE", "RESERVED", "OCCUPIED"

    public Table(int tableNumber, int capacity) {
        this.tableNumber = tableNumber;
        this.capacity = capacity;
        this.status = "AVAILABLE";
    }

    public int getTableNumber() { return tableNumber; }
    public int getCapacity() { return capacity; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}