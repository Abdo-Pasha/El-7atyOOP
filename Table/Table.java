package Table;

public class Table {
    private int tableNumber;
    private int capacity;
    private TableType location;
    private TableStatus status;

    public Table(int tableNumber, int capacity, TableType location) {
        this.tableNumber = tableNumber;
        this.capacity = capacity;
        this.location = location;
        this.status = TableStatus.AVAILABLE;
    }

    public int getTableNumber() { return tableNumber; }
    public int getCapacity() { return capacity; }
    public TableType getLocation() { return location; }
    public TableStatus getStatus() { return status; }
    public void setStatus(TableStatus status) { this.status = status; }

    @Override
    public String toString() {
        return "Table{" + "Number=" + tableNumber + ", Capacity=" + capacity +
                ", Location=" + location + ", Status=" + status + '}';
    }
}
