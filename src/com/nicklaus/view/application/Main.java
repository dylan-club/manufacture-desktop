package com.nicklaus.view.application;

import com.jfoenix.controls.JFXDecorator;
import com.nicklaus.util.NavigationUtils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;

public class Main extends Application {

        @Override
        public void start(Stage primaryStage) throws Exception{
            Parent root = FXMLLoader.load(getClass().getResource(NavigationUtils.loginView));
            JFXDecorator decorator=new JFXDecorator(primaryStage, root, false, false, true);
            decorator.setCustomMaximize(false);

            Scene scene = new Scene(decorator);

            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
        }


        public static void main(String[] args) {
            launch(args);
        }
}
