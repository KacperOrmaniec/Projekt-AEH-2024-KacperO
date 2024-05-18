package teammanagement;

import javafx.application.Application; //klasa dla aplikacji
import javafx.geometry.Insets; // klasa do marginesów
import javafx.geometry.Pos; // ustawienie pozycji
import javafx.scene.Scene; // scena
import javafx.scene.control.*; // kontrolki od Gui
import javafx.scene.layout.*; // układy elementów
import javafx.scene.paint.Color; // kolory
import javafx.stage.Stage; // okno aplikacji

import java.util.Map; //mapowanie klucz-wartość

public class TeamManagementGUI extends Application {

    private Team team;
    private final String accentColor = "#87CEEB"; // jasnoniebieski 
    
    // Metoda do pobierania tekstu od użytkownika
    private String promptForName(String prompt) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setHeaderText(null);
        dialog.setContentText(prompt);
        dialog.showAndWait();
        return dialog.getResult();
    }

    // Alerty/powiadomienia 
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait(); //czekanie aż użytkownik zamknie okienko powiadomienia/alertu
    }

    // Metoda pomocnicza do formatowania czasu w formacie HH:MM:SS:MS
    private String formatTime(long millis) {
        long hours = (millis / (1000 * 60 * 60)) % 24;
        long minutes = (millis / (1000 * 60)) % 60;
        long seconds = (millis / 1000) % 60;
        long milliseconds = millis % 1000;
        return String.format("%02d:%02d:%02d:%03d", hours, minutes, seconds, milliseconds);
    }

    // Metoda pomocnicza do stylizacji koloru tła dla przycisków 
    private void styleButton(Button button, String backgroundColor) {
        button.setStyle("-fx-background-color: " + backgroundColor + "; -fx-text-fill: white; -fx-font-weight: bold;");
    }


    @Override
    public void start(Stage primaryStage) { // primaryStage - główne okno aplkacji, metoda start to główna moetoda aplikacji JavaFX, zaczyna się po uruchomieniu
        team = new Team();

        //Komponenty GUI - deklaracja
        ListView<Member> membersListView = new ListView<>();
        membersListView.setStyle("-fx-background-color: white; -fx-border-color: #ccc;");

        ListView<Task> tasksListView = new ListView<>();
        tasksListView.setStyle("-fx-background-color: white; -fx-border-color: #ccc;");

        Button addMemberButton = new Button("Add Member");
        styleButton(addMemberButton, accentColor);

        Button addTaskButton = new Button("Add Task");
        styleButton(addTaskButton, accentColor);

        Button assignTaskButton = new Button("Assign Task");
        styleButton(assignTaskButton, accentColor);

        ComboBox<TaskStatus> setStatusComboBox = new ComboBox<>();
        setStatusComboBox.getItems().addAll(TaskStatus.values());
        setStatusComboBox.setStyle("-fx-background-color: white; -fx-border-color: #ccc;");

        Button generateReportButton = new Button("Generate Report");
        styleButton(generateReportButton, accentColor);
        generateReportButton.setStyle("-fx-font-size: 24px;"); // większy przycisk

        Button refreshButton = new Button("Refresh Details");
        styleButton(refreshButton, accentColor);

        Label assignedMemberLabel = new Label();
        Label currentStatusLabel = new Label();
        Label timeSpentLabel = new Label();
       

        // Listener dla zmiany wybranego elementu w tasksListView
        tasksListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Member assignedMember = newValue.getAssignedTo();
                if (assignedMember != null) {
                    assignedMemberLabel.setText("Assigned to: " + assignedMember.getName());
                } else {
                    assignedMemberLabel.setText("Not assigned to any member.");
                }

                currentStatusLabel.setText("Current Status: " + newValue.getStatus());

                TaskStatus status = newValue.getStatus();
                if (status == TaskStatus.COMPLETED) {
                    long timeSpentMillis = newValue.getTimeSpent();
                    timeSpentLabel.setText("Time Spent: " + formatTime(timeSpentMillis));
                } else {
                    timeSpentLabel.setText("");
                }
            } else {
                assignedMemberLabel.setText("");
                currentStatusLabel.setText("");
                timeSpentLabel.setText("");
            }
        });

        // Obsługa przycisku Refresh
        refreshButton.setOnAction(e -> {
            Task selectedTask = tasksListView.getSelectionModel().getSelectedItem();
            if (selectedTask != null) {
                Member assignedMember = selectedTask.getAssignedTo();
                assignedMemberLabel.setText(assignedMember != null ? "Assigned to: " + assignedMember.getName() : "Not assigned to any member.");

                currentStatusLabel.setText("Current Status: " + selectedTask.getStatus());

                if (selectedTask.getStatus() == TaskStatus.COMPLETED) {
                    timeSpentLabel.setText("Time Spent: " + formatTime(selectedTask.getTimeSpent()));
                } else {
                    timeSpentLabel.setText("");
                }
            }
        });

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
        VBox membersBox = new VBox(10, new Label("Team Members"), membersListView, addMemberButton);
        membersBox.setPadding(new Insets(20));
        
        VBox tasksBox = new VBox(10, new Label("Tasks"), tasksListView, addTaskButton, assignTaskButton, setStatusComboBox);
        tasksBox.setPadding(new Insets(20));
        
        HBox taskDetailsBox = new HBox(10, assignedMemberLabel, currentStatusLabel, timeSpentLabel, refreshButton);
        taskDetailsBox.setAlignment(Pos.CENTER_LEFT); 
        taskDetailsBox.setPadding(new Insets(0, 20, 20, 20));

        tasksBox.getChildren().add(taskDetailsBox);

        HBox root = new HBox(20, membersBox, tasksBox);
        root.setPadding(new Insets(20));

        VBox mainLayout = new VBox(10, root, generateReportButton);
        mainLayout.setAlignment(Pos.TOP_CENTER);

        // Scena
        Scene scene = new Scene(mainLayout, 1050, 600);
        scene.setFill(Color.web("#f4f4f4")); // jasno-szary
        // Stage
        primaryStage.setScene(scene);
        primaryStage.setTitle("Team Management App");
        primaryStage.show();
    }
}
