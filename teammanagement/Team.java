package teammanagement;

import java.util.ArrayList;
import java.util.List;

public class Team {
    private List<Member> members;
    private List<Task> tasks;

    public Team() {
        members = new ArrayList<>();
        tasks = new ArrayList<>();
    }

    public void addMember(Member member) {
        members.add(member);
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public List<Member> getMembers() {
        return members;
    }

    public List<Task> getTasks() {
        return tasks;
    }
}
