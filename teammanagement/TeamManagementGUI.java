package teammanagement;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class TeamManagementGUI extends Application {

    private Team team;

    public static void main(String[] args) {
        launch(args);
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
            if (selectedMember != null && selectedTask != null) {
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
            // Generowanie raportu do implementacji
        });

        // Layout
        VBox membersBox = new VBox(new Label("Team Members"), membersListView, addMemberButton);
        VBox tasksBox = new VBox(new Label("Tasks"), tasksListView, addTaskButton, assignTaskButton, setStatusComboBox);
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
