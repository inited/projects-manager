package cz.inited.ui.graphics.controllers;

import cz.inited.Project;
import cz.inited.ui.graphics.TemplatesManager;
import cz.inited.utils.TextAreaAppender;
import cz.inited.utils.exceptions.ProjectException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Path;
import java.util.ResourceBundle;

/**
 * Created by ondre on 08.07.2016.
 */
public class MainController implements Initializable {

	private static final Logger LOGGER = Logger.getLogger(MainController.class);

	@FXML
	private TextArea logArea;
	@FXML
	private Text projectName;
	@FXML
	private VBox scriptsBox;

	/**
	 * Opens dialog for creating cordova application
	 * @param event
	 */
	@FXML
	private void handleCreateCordova(ActionEvent event) {
		TemplatesManager manager = TemplatesManager.getInstance();
		manager.createNewWindow(manager.getTemplate("create-cordova"), "Vytvořit aplikaci");
	}

	@FXML
	private void handleOpenProject(ActionEvent event) {
		try {
			DirectoryChooser directoryChooser = new DirectoryChooser();
			File directory = directoryChooser.showDialog(logArea.getScene().getWindow());
			LOGGER.info("Otevírám projekt");
			Project project = new Project(directory.toPath());
			projectName.setText(project.getInited().getName());

			project.getScripts().forEach((path) -> {
				Button button = new Button();
				button.setOnAction(event1 -> {
					runScript(path);
				});
				button.setPrefWidth(100);
				button.setText(path.getFileName().toString());

				scriptsBox.getChildren().add(button);
			});
			LOGGER.info("Projekt otevřen");
		} catch (ProjectException ex) {
			Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage());
			alert.showAndWait();
		}
	}

	public void runScript(Path scrpit) {
		try {
			ProcessBuilder pb = new ProcessBuilder("bash", "-c", scrpit.toString());
			Process p = pb.start();
			InputStream inputStream = p.getInputStream();
			int consoleDisplay;
			while((consoleDisplay=inputStream.read())!=-1) {
				LOGGER.info(consoleDisplay);
			}
			p.waitFor();
			LOGGER.info("Script skončil");
		} catch(Exception ex) {
			LOGGER.warn(ex.getMessage());
			ex.printStackTrace();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println("Setting log area");
		TextAreaAppender.setTextArea(logArea);
	}
}
