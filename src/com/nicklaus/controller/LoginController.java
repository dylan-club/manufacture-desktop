package com.nicklaus.controller;

import com.jfoenix.controls.*;
import com.nicklaus.domain.User;
import com.nicklaus.service.UserService;
import com.nicklaus.service.impl.UserServiceImpl;
import com.nicklaus.util.DialogUtils;
import com.nicklaus.util.LoginUtils;
import com.nicklaus.util.NavigationUtils;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.Border;
import javafx.stage.Stage;
import javafx.util.Duration;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    private final UserService userService = UserServiceImpl.getInstance();

    private DialogUtils dialogUtils = new DialogUtils();

    @FXML
    private JFXButton loginBtn;

    @FXML
    private Hyperlink registerLink;

    @FXML
    private JFXSpinner loginSpinner;

    @FXML
    private JFXTextField usernameInput;

    @FXML
    private JFXPasswordField passwordInput;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loginSpinner.setVisible(false);
    }

    @FXML
    private void loginAction(ActionEvent event) throws IOException {


        String username  = usernameInput.getText().trim();
        String password  = passwordInput.getText().trim();

        boolean loginFlag = false;

        //判断是否是超级管理员
        if ("admin".equals(username) && "admin".equals(password)){
            User loginUser = new User(username, password);
            loginUser.setPower("超级管理员");
            loginUser.setName("Nicklaus");
            LoginUtils.setUser(loginUser);
            loginFlag = true;
        }else{
            //不是超级管理员就查询是否有该用户存在
            User loginUser = userService.login(username,password);

            if (loginUser == null){
                dialogUtils.addControl(loginBtn).setTitle("提示信息").setMessage("用户名或密码错误！").setConfirmBtn("确定").create();
            }else{
                LoginUtils.setUser(loginUser);
                loginFlag = true;
            }
        }

        //登录成功
        if (loginFlag){

            loginSpinner.setVisible(true);
            PauseTransition pauseTransition = new PauseTransition();
            pauseTransition.setDuration(Duration.seconds(1));
            pauseTransition.setOnFinished(ev -> {

                try {
                    loginOver();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            });
            pauseTransition.play();
        }

    }

    private void loginOver() throws IOException {
        Stage stage = new Stage();

        Scene scene = new Scene(makeJFXDecorator(stage,NavigationUtils.homeView));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setIconified(false);
        stage.show();
        //隐藏登录界面
        loginBtn.getScene().getWindow().hide();
    }

    public void goToRegisterPaneAction(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Scene scene = new Scene(makeJFXDecorator(stage,NavigationUtils.registrationView));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setIconified(false);
        stage.show();
        //隐藏登录界面
        loginBtn.getScene().getWindow().hide();
    }

    private JFXDecorator makeJFXDecorator(Stage stage, String fxmlPath) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
        JFXDecorator decorator = new JFXDecorator(stage, root, false, false, true);
        decorator.setCustomMaximize(false);
        decorator.setBorder(Border.EMPTY);

        return decorator;
    }

}
