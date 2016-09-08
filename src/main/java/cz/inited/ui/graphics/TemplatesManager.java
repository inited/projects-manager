package cz.inited.ui.graphics;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.HashMap;

/**
 * Created by ondre on 08.07.2016.
 */
public final class TemplatesManager {

	private static final TemplatesManager INSTANCE = new TemplatesManager();

	public static TemplatesManager getInstance() {
		return INSTANCE;
	}

	private HashMap<String, Parent> templates;

	private TemplatesManager() {
		templates = new HashMap<>();
		loadTemplates();
	}

	private void loadTemplates() {
		try {
			templates.put("main", FXMLLoader.load(getClass().getResource("/FXTemplates/main.fxml")));
			templates.put("create-cordova", FXMLLoader.load(getClass().getResource("/FXTemplates/create-cordova.fxml")));
		} catch (Exception ex) {
			ex.printStackTrace();
			//TODO: Create logging
		}
	}

	public Parent getTemplate(String name) {
		return templates.get(name);
	}

	public void createNewWindow(Parent template, String title, double width, double height) {
		Scene scene = new Scene(template, width, height);

		Stage stage = new Stage();
		stage.setTitle(title);
		stage.setScene(scene);

		stage.show();
	}

	public void createNewWindow(Parent template, String title) {
		createNewWindow(template, title, 480, 240);
	}
}
