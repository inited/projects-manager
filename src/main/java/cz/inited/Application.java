package cz.inited;

import cz.inited.ui.graphics.TemplatesManager;
import cz.inited.ui.text.CommandsManager;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.log4j.PropertyConfigurator;

import java.io.FileInputStream;
import java.util.Properties;

import static cz.inited.utils.Strings.*;
/**
 * Created by ondre on 08.07.2016.
 */
public class Application extends javafx.application.Application {

	public static boolean graphics = false;

	/**
	 * Checks if user wants to run graphics version
	 * If he does, it runs javafx application
	 * If not, passes the arguments to the commands manager if any
	 * If there are no arguments, it prints help
	 * @param args
	 */
	public static void main(String... args) {
		try {
			//PropertyConfigurator.configure(Application.class.getResource("/log4j.properties"));
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		if (args.length == 0) {
			printHelp();
		} else if (args.length > 0) {
			if (args[0].equals("-g") || args[0].equals("--graphics")) {
				graphics = true;
				launch(args);
			} else {
				CommandsManager.getInstance().processCommand(args);
			}
		}
	}

	/**
	 * Prints help
	 */
	public static void printHelp() {
		System.out.println(HELP);
	}

	/**
	 * Starts graphics application
	 * @param primaryStage shown stage
	 * @throws Exception
	 */
	public void start(Stage primaryStage) throws Exception {
		Parent root = TemplatesManager.getInstance().getTemplate("main");
		Scene scene = new Scene(root, 800, 600);

		primaryStage.setTitle("Projects Manager");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
