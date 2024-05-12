package teammanagement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportGenerator {

    public static Map<Member, Integer> tasksAssignedToMembers(Team team) {
        Map<Member, Integer> tasksAssignedMap = new HashMap<>();
        List<Member> members = team.getMembers();
        List<Task> tasks = team.getTasks();

        for (Member member : members) {
            int count = 0;
            for (Task task : tasks) {
                if (task.getAssignedTo() == member) {
                    count++;
                }
            }
            tasksAssignedMap.put(member, count);
        }

        return tasksAssignedMap;
    }

    public static long totalTimeSpentOnTasks(Team team) {
        long totalTime = 0;
        List<Task> tasks = team.getTasks();

        for (Task task : tasks) {
            if (task.getStatus() == TaskStatus.COMPLETED) {
                totalTime += task.getTimeSpent();
            }
        }

        return totalTime;
    }

    public static Map<Member, Integer> tasksCompletedByMembers(Team team) {
        Map<Member, Integer> tasksCompletedMap = new HashMap<>();
        List<Member> members = team.getMembers();
        List<Task> tasks = team.getTasks();

        for (Member member : members) {
            int count = 0;
            for (Task task : tasks) {
                if (task.getAssignedTo() == member && task.getStatus() == TaskStatus.COMPLETED) {
                    count++;
                }
            }
            tasksCompletedMap.put(member, count);
        }

        return tasksCompletedMap;
    }
}
