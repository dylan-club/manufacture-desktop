package com.nicklaus.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDecorator;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import com.nicklaus.domain.User;
import com.nicklaus.service.UserService;
import com.nicklaus.service.impl.UserServiceImpl;
import com.nicklaus.util.DialogUtils;
import com.nicklaus.util.NavigationUtils;
import com.nicklaus.util.SceneUtils;
import com.nicklaus.view.validator.UserValidator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Border;
import javafx.stage.Stage;
import org.apache.commons.beanutils.BeanUtils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class RegistrationController implements Initializable {

    private final UserService userService = UserServiceImpl.getInstance();

    private DialogUtils dialogUtils = new DialogUtils();

    private ToggleGroup toggleGroup;

    @FXML
    private JFXTextField usernameInput;

    @FXML
    private JFXTextField passwordInput;

    @FXML
    private JFXTextField nameInput;

    @FXML
    private JFXTextField phoneInput;

    @FXML
    private JFXTextField factoryNameInput;

    @FXML
    private JFXTextField factoryDetailInput;

    @FXML
    private JFXRadioButton factoryRadio;

    @FXML
    private JFXRadioButton salemanRadio;

    @FXML
    private JFXButton backToLoginBtn;

    @FXML
    private JFXButton registerBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        toggleGroup = new ToggleGroup();
        factoryRadio.setUserData("云工厂");
        salemanRadio.setUserData("经销商");
        factoryRadio.setToggleGroup(toggleGroup);
        salemanRadio.setToggleGroup(toggleGroup);
    }

    public void backToLoginAction(ActionEvent event) throws IOException {
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
        //隐藏注册页面
        backToLoginBtn.getScene().getWindow().hide();
    }

    /**
     * 注册功能
     */
    public void registerAction(ActionEvent event) throws Exception {
        String username = usernameInput.getText().trim();
        String password = passwordInput.getText().trim();
        String name = nameInput.getText().trim();
        String phone = phoneInput.getText().trim();
        Toggle selectedPower = toggleGroup.getSelectedToggle();
        String power;
        if (selectedPower == null){
            power = null;
        }else{
            power = (String)selectedPower.getUserData();
        }
        String factoryName = factoryNameInput.getText().trim();
        String factoryDetail = factoryDetailInput.getText().trim();

        Map<String,Object> userMap = new HashMap<String,Object>();
        userMap.put("username",username);
        userMap.put("password",password);
        userMap.put("power",power);
        userMap.put("name",name);
        userMap.put("phone",phone);
        userMap.put("factoryName",factoryName);
        userMap.put("detail",factoryDetail);
        userMap.put("state","正常");

        User registerUser = new User();
        BeanUtils.populate(registerUser,userMap);
        String validateMsg = UserValidator.validate(registerUser);
        //验证成功，进行保存
        if (validateMsg == null){
            String message = userService.register(registerUser);
            dialogUtils.addControl(registerBtn).setTitle("提示信息").setMessage(message).setConfirmBtn("确定").create();
        }else{
            //验证失败，弹出提示信息
            dialogUtils.addControl(registerBtn).setTitle("提示信息").setMessage(validateMsg).setConfirmBtn("确定").create();
        }
    }


    public void disableInputAction(ActionEvent event){
        factoryDetailInput.setDisable(true);
        factoryNameInput.setDisable(true);
    }

    public void enableInputAction(ActionEvent event){
        factoryNameInput.setDisable(false);
        factoryDetailInput.setDisable(false);
    }
}
