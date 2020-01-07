package application;

import java.util.Random;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import weka.core.*;
public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			//System.out.println("hello");
			AnchorPane rootPane;
			interfaceController controller = new interfaceController();
			Parent root = FXMLLoader.load(getClass().getResource("interface.fxml"));
			primaryStage.setTitle("TP Data Minig");
			primaryStage.setScene(new Scene(root,1321.0,566.0));
			
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws Exception {
		launch(args);
		
	}
}
