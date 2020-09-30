package com.nicklaus.util;

import com.jfoenix.controls.JFXDecorator;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Border;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneUtils {

    public static FXMLLoader openPane(String fxmlPath, Object controller) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(controller.getClass().getResource(fxmlPath));
        Parent root = loader.load();
        JFXDecorator decorator = new JFXDecorator(stage, root, false, false, true);
        decorator.setCustomMaximize(false);
        decorator.setBorder(Border.EMPTY);


        Scene scene = new Scene(decorator);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setIconified(false);
        stage.show();

        return loader;
    }
}
