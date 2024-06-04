package jesh.project.jeshproject.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import jesh.project.jeshproject.HelloApplication;
import jesh.project.jeshproject.brightness.BrightnessManager;
import jesh.project.jeshproject.model.*;

import java.io.IOException;
import java.util.ArrayList;

public class MainPage {
    @FXML private Button nightModeButton;
    @FXML private Slider brightnessSlider;
    @FXML private Text brightnessLevelTitle;
    @FXML private Button timeslot_1;
    @FXML private Button timeslot_2;
    @FXML private Button timeslot_3;
    @FXML private Button timeslot_4;
    @FXML private Button timeslot_5;
    @FXML private Text timelineName;
    @FXML private Button saveButton;
    @FXML private VBox infoBox;
    @FXML private Button signoutButton;
    @FXML private Button profileButton;
    @FXML private Button settingsButton;
    @FXML private Button testButton;
    @FXML private TextField timeline_name;
    @FXML private TextField sleepwell_logo;
    @FXML private Button save_button;
    @FXML private Button add_time_button;
    @FXML private Slider start_time_slider;
    @FXML private Slider end_time_slider;
    @FXML private TextField NM_start_title;
    @FXML private TextField NM_end_title;
    @FXML private TextField NM_start;
    @FXML private TextField NM_end;
    @FXML private Button NM_button;
    @FXML private TextField BL_title;
    @FXML private Slider brightness_slider;
    @FXML private ChoiceBox<String> timelineChoiceBox;


    private final TimelineManager timelineManager;
    private final UserManager userManager;


    public MainPage() {
        SqliteUserDAO userDAO = new SqliteUserDAO();
        userManager = new UserManager(userDAO);
        timelineManager = new TimelineManager(userDAO);
    }


    public void testBrightness(ActionEvent actionEvent) {
        try{
            BrightnessManager.ChangeBrightness((int) brightness_slider.getValue());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @FXML
    public void saveTimeline(ActionEvent actionEvent) {
        String name = timeline_name.getText();
        int startTime = (int) start_time_slider.getValue();
        int endTime = (int) end_time_slider.getValue();
        int brightness = (int) brightness_slider.getValue();
        int userID = userManager.getLoggedInUser().getId();

        Timeline newTimeline = new Timeline(name, startTime, endTime, brightness, userID);
        Timeline existingTimeline = timelineManager.getTimeline(name, userID);

        if (existingTimeline != null) {
            timelineManager.updateTimeline(newTimeline);
        } else {
            timelineManager.addTimeline(newTimeline);
        }

        updateTimelineChoiceBox();
    }

    @FXML
    public void updateTimelineChoiceBox() {
        // get all rows matching userID, make array with row names, set choices
        int userID = userManager.getLoggedInUser().getId();
        ArrayList<String> timelines = timelineManager.getUserTimelines(userID);
        timelineChoiceBox.setItems(FXCollections.observableArrayList(timelines));
    }

    @FXML
    private void loadTimeline() {
        String selectedValue = timelineChoiceBox.getValue();
        int userID = userManager.getLoggedInUser().getId();

        if (selectedValue != null) {
            Timeline timeline = timelineManager.getTimeline(selectedValue, userManager.getLoggedInUser().getId());
            start_time_slider.setValue(timeline.getStartTime());
            end_time_slider.setValue(timeline.getEndTime());
            timeline_name.setText(timeline.getName());
            brightness_slider.setValue(timeline.getBrightness());
        }
    }

    @FXML
    private void goToProfilePage() throws IOException {
        // Get the stage
        Stage stage = (Stage) profileButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("ProfilePage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        stage.setScene(scene);
    }

}
