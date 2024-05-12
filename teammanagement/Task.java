package teammanagement;

import java.util.Date;

public class Task {
    private String description;
    private Member assignedTo;
    private TaskStatus status;
    private Date assignedDate;
    private Date completedDate;

    public Task(String description) {
        this.description = description;
        this.status = TaskStatus.NEW; // Ustawienie domyślnego statusu na NEW
        this.assignedDate = new Date();
    }

    public Task(String description, Member assignedTo) {
        this.description = description;
        this.assignedTo = assignedTo;
        this.status = TaskStatus.NEW; // Ustawienie domyślnego statusu na NEW
        this.assignedDate = new Date();
    }

    public void assignTo(Member member) {
        this.assignedTo = member;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
        if (status == TaskStatus.COMPLETED) {
            this.completedDate = new Date();
        }
    }

    public String getDescription() {
        return description;
    }

    public Member getAssignedTo() {
        return assignedTo;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public long getTimeSpent() {
        if (completedDate == null) {
            return 0;
        }
        return completedDate.getTime() - assignedDate.getTime();
    }

    @Override
    public String toString() {
        return description;
    }
}