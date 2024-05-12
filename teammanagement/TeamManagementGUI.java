package teammanagement;
import java.util.Map;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class TeamManagementGUI extends Application {

    private Team team;

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Metoda pomocnicza do formatowania czasu w formacie HH:MM:SS:MS
    private String formatTime(long millis) {
    long hours = (millis / (1000 * 60 * 60)) % 24;
    long minutes = (millis / (1000 * 60)) % 60;
    long seconds = (millis / 1000) % 60;
    long milliseconds = millis % 1000;
    return String.format("%02d:%02d:%02d:%03d", hours, minutes, seconds, milliseconds);
}


    @Override
    public void start(Stage primaryStage) {
        team = new Team();

        //Komponenty GUI
        ListView<Member> membersListView = new ListView<>();
        ListView<Task> tasksListView = new ListView<>();
        Button addMemberButton = new Button("Add Member");
        Button addTaskButton = new Button("Add Task");
        Button assignTaskButton = new Button("Assign Task");
        ComboBox<TaskStatus> setStatusComboBox = new ComboBox<>();
        setStatusComboBox.getItems().addAll(TaskStatus.NEW, TaskStatus.TODO, TaskStatus.IN_PROGRESS, TaskStatus.COMPLETED);
        Button generateReportButton = new Button("Generate Report");
        Label assignedMemberLabel = new Label();
        Label currentStatusLabel = new Label();
        Label timeSpentLabel = new Label();

        // Dodawanie członków zespołu oraz zadań
        addMemberButton.setOnAction(e -> {
            String name = promptForName("Enter member name:");
            if (name != null && !name.isEmpty()) {
                Member newMember = new Member(name);
                team.addMember(newMember);
                membersListView.getItems().add(newMember);
            }
        });

        addTaskButton.setOnAction(e -> {
            String description = promptForName("Enter task description:");
            if (description != null && !description.isEmpty()) {
                Task newTask = new Task(description);
                team.addTask(newTask);
                tasksListView.getItems().add(newTask);
            }
        });

        assignTaskButton.setOnAction(e -> {
            Member selectedMember = membersListView.getSelectionModel().getSelectedItem();
            Task selectedTask = tasksListView.getSelectionModel().getSelectedItem();
            if (selectedMember == null || selectedTask == null) {
                // Alert
                showAlert("Please select both a task and a member.");
            } else {
                selectedTask.assignTo(selectedMember);
            }
        });
        
        // Listener który wyświetla osobe przypisaną do Taska
        tasksListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Member assignedMember = newValue.getAssignedTo();
                if (assignedMember != null) {
                    assignedMemberLabel.setText("Assigned to: " + assignedMember.getName());
                } else {
                    assignedMemberLabel.setText("Not assigned to any member.");
                }
            } else {
                assignedMemberLabel.setText("");
            }
        });
        // Listener który wyświetla status Taska
        tasksListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                currentStatusLabel.setText("Current Status: " + newValue.getStatus());
            } else {
                currentStatusLabel.setText("");
            }
        });
        //Listener który zwraca czas trwania Task'a
        tasksListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                TaskStatus status = newValue.getStatus();
                if (status == TaskStatus.COMPLETED) {
                    long timeSpentMillis = newValue.getTimeSpent();
                    timeSpentLabel.setText("Time Spent: " + formatTime(timeSpentMillis));
                } else {
                    timeSpentLabel.setText("");
                }
            } else {
                timeSpentLabel.setText("");
            }
        });

        setStatusComboBox.setOnAction(e -> {
            Task selectedTask = tasksListView.getSelectionModel().getSelectedItem();
            if (selectedTask != null) {
                selectedTask.setStatus(setStatusComboBox.getValue());
            }
        });

        generateReportButton.setOnAction(e -> {
            // Wywołujemy metody z klasy ReportGenerator i otrzymujemy statystyki
            Map<Member, Integer> tasksAssignedMap = ReportGenerator.tasksAssignedToMembers(team);
            long totalTimeSpent = ReportGenerator.totalTimeSpentOnTasks(team);
            Map<Member, Integer> tasksCompletedMap = ReportGenerator.tasksCompletedByMembers(team);

            // Tworzymy tekst raportu na podstawie uzyskanych statystyk
            StringBuilder reportText = new StringBuilder();
            reportText.append("Tasks Assigned to Members:\n");
            for (Map.Entry<Member, Integer> entry : tasksAssignedMap.entrySet()) {
                reportText.append(entry.getKey().getName()).append(": ").append(entry.getValue()).append("\n");
            }
            reportText.append("\nTotal Time Spent on Tasks: ").append(formatTime(totalTimeSpent)).append("\n\n");
            reportText.append("Tasks Completed by Members:\n");
            for (Map.Entry<Member, Integer> entry : tasksCompletedMap.entrySet()) {
                reportText.append(entry.getKey().getName()).append(": ").append(entry.getValue()).append("\n");
            }

            // Wyświetlamy raport w oknie dialogowym
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText(reportText.toString());
            alert.showAndWait();
        });

        // Layout
        VBox membersBox = new VBox(new Label("Team Members"), membersListView, addMemberButton);
        VBox tasksBox = new VBox(new Label("Tasks"), tasksListView, addTaskButton, assignTaskButton, setStatusComboBox,assignedMemberLabel, currentStatusLabel,timeSpentLabel);
        HBox root = new HBox(membersBox, tasksBox);
        VBox mainLayout = new VBox(root, generateReportButton);

        // Scena
        Scene scene = new Scene(mainLayout, 600, 400);

        // Stage
        primaryStage.setScene(scene);
        primaryStage.setTitle("Team Management App");
        primaryStage.show();
    }

    private String promptForName(String prompt) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setHeaderText(null);
        dialog.setContentText(prompt);
        dialog.showAndWait();
        return dialog.getResult();
    }
}
