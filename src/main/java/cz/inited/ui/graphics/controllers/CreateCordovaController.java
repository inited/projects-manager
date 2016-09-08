package cz.inited.ui.graphics.controllers;

import cz.inited.actions.cordova.CordovaProject;
import cz.inited.utils.exceptions.ActionException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.io.File;

import static cz.inited.utils.Strings.CREATE_FILL_ALL_FIELDS;
import static cz.inited.utils.Strings.CREATE_PROJECT_CREATED;

/**
 * Created by ondre on 08.07.2016.
 */
public class CreateCordovaController {

	private static final Logger LOGGER = Logger.getLogger(CreateCordovaController.class);

	@FXML
	private TextField appFolder;
	@FXML
	private TextField appName;
	@FXML
	private Button createProjectButton;

	/**
	 * Opens directory chooser which allows to choose directory where application should be created
	 * @param actionEvent
	 */
	@FXML
	private void handleSelectFolder(ActionEvent actionEvent) {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		File directory = directoryChooser.showDialog(appFolder.getScene().getWindow());
		if (directory != null) {
			appFolder.setText(directory.getAbsolutePath());
		}
	}

	/**
	 * Checks if all information are filled in and creates the app on the new thread
	 * Shows alert if some error occurs
	 * @param actionEvent
	 */
	@FXML
	private void handleCreateApp(ActionEvent actionEvent) {
		if (appFolder.getText().isEmpty() || appName.getText().isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.ERROR, CREATE_FILL_ALL_FIELDS);
			alert.showAndWait();
			return;
		}
		createProjectButton.setDisable(true);
		Thread thread = new Thread(this::createCordovaApp);
		thread.start();

	}

	private void createCordovaApp() {
		try {
			CordovaProject cordovaProject = new CordovaProject();
			cordovaProject.create(String.format("%s/%s", appFolder.getText(), appName.getText()));
			LOGGER.info(CREATE_PROJECT_CREATED);
			Platform.runLater(() -> {
				createProjectButton.setDisable(false);
				Alert alert = new Alert(Alert.AlertType.INFORMATION, CREATE_PROJECT_CREATED);
				alert.showAndWait();
			});
			Platform.runLater(((Stage)appFolder.getScene().getWindow())::close);
		} catch (ActionException ex) {
			LOGGER.info(ex.getMessage());
			Platform.runLater(() -> {
				createProjectButton.setDisable(false);
				Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage());
				alert.showAndWait();
			});
		}
	}
}
