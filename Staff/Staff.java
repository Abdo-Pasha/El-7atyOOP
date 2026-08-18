package Staff;
import java.time.LocalDate;

public abstract class Staff {
    protected String username;
    protected String password;
    protected LocalDate dateOfBirth;
    protected Role role;
    protected WorkingHours workingHours;

    public Staff(String username, String password, LocalDate dateOfBirth, Role role, WorkingHours workingHours) {
        this.username = username;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.role = role;
        this.workingHours = workingHours;
    }

    public boolean login(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    // Getters and Setters
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public Role getRole() { return role; }
    public WorkingHours getWorkingHours() { return workingHours; }
    public void setWorkingHours(WorkingHours workingHours) { this.workingHours = workingHours; }
}