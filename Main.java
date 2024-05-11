import teammanagement.*;

public class Main {
    public static void main(String[] args) {
        // Tworzenie zespołu
        Team team = new Team();

        // Dodawanie członków do zespołu
        Member Kacper = new Member("Kacper");
        Member Julia = new Member("Julia");
        team.addMember(Kacper);
        team.addMember(Julia);

        // Dodawanie zadań
        Task task1 = new Task("feature X", Kacper);
        Task task2 = new Task("Write documentation", Julia);
        team.addTask(task1);
        team.addTask(task2);

        // Przypisanie zadań do członków zespołu
        task1.setStatus(TaskStatus.IN_PROGRESS);
        task2.setStatus(TaskStatus.TODO);

        // Wyświetlenie informacji o zespole i zadaniach
        System.out.println("Team Members:");
        for (Member member : team.getMembers()) {
            System.out.println("Name: " + member.getName());
        }

        System.out.println("\nTasks:");
        for (Task task : team.getTasks()) {
            System.out.println("Description: " + task.getDescription());
            System.out.println("Assigned to: " + task.getAssignedTo().getName());
            System.out.println("Status: " + task.getStatus());
            System.out.println("Time Spent: " + task.getTimeSpent() + " milliseconds\n");
        }

        // Zmiana statusu zadań
        task1.setStatus(TaskStatus.COMPLETED);
        task2.setStatus(TaskStatus.IN_PROGRESS);

        // Ponowne wyświetlenie informacji o zadaniach po zmianie statusu
        System.out.println("\nTasks After Status Change:");
        for (Task task : team.getTasks()) {
            System.out.println("Description: " + task.getDescription());
            System.out.println("Status: " + task.getStatus());
            System.out.println("Time Spent: " + task.getTimeSpent() + " milliseconds\n");
        }
    }
}
