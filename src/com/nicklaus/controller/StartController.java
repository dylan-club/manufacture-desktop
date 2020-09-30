package com.nicklaus.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDecorator;
import com.nicklaus.util.DialogUtils;
import com.nicklaus.util.LoginUtils;
import com.nicklaus.util.NavigationUtils;
import com.nicklaus.util.SceneUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StartController implements Initializable {

    DialogUtils dialogUtils = new DialogUtils();

    @FXML
    private Label usernameLabel;

    @FXML
    private Label userInfoLabel;

    @FXML
    private JFXButton exitBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        usernameLabel.setText(LoginUtils.getUser().getUsername());
        userInfoLabel.setText(LoginUtils.getUser().toString());
    }

    /**
     * 退出系统界面，返回登录界面
     * @param event
     */
    public void exitAction(ActionEvent event) throws IOException {

        //弹出提示框，退出成功
        dialogUtils.addControl(exitBtn).setTitle("提示信息").setMessage("退出成功，祝您生活愉快！").setConfirmBtn("确定").create();

//        Stage stage = new Stage();
//        Parent root = FXMLLoader.load(this.getClass().getResource(NavigationUtils.anotherLoginView));
//        JFXDecorator decorator = new JFXDecorator(stage, root, false, false, true);
//        decorator.setCustomMaximize(false);
//        decorator.setBorder(Border.EMPTY);
//
//        Scene scene = new Scene(decorator);
//        stage.setScene(scene);
//        stage.setResizable(false);
//        stage.setIconified(false);
//        stage.show();
        SceneUtils.openPane(NavigationUtils.anotherLoginView,this);
        //隐藏系统界面
        exitBtn.getScene().getWindow().hide();
        //清除登录记录
        LoginUtils.setUser(null);
    }
}
