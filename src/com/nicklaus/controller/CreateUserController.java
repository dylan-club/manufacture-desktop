package com.nicklaus.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import com.nicklaus.domain.User;
import com.nicklaus.service.UserService;
import com.nicklaus.service.impl.UserServiceImpl;
import com.nicklaus.util.DialogUtils;
import com.nicklaus.view.validator.UserValidator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class CreateUserController implements Initializable {

    private UserService userService = UserServiceImpl.getInstance();

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
    private JFXButton createBtn;

    @FXML
    private JFXButton exitCreateUserBtn;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        toggleGroup = new ToggleGroup();
        factoryRadio.setUserData("云工厂");
        salemanRadio.setUserData("经销商");
        factoryRadio.setToggleGroup(toggleGroup);
        salemanRadio.setToggleGroup(toggleGroup);
    }

    public void addUserAction(ActionEvent event){
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

        User newUser = new User();
        try {
            BeanUtils.populate(newUser,userMap);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        String validateMsg = UserValidator.validate(newUser);
        //验证成功，进行保存
        if (validateMsg == null){
            String message = userService.register(newUser);
            dialogUtils.addControl(createBtn).setTitle("提示信息").setMessage(message).setConfirmBtn("确定").create();
        }else{
            //验证失败，弹出提示信息
            dialogUtils.addControl(createBtn).setTitle("提示信息").setMessage(validateMsg).setConfirmBtn("确定").create();
        }
    }

    public void exitPaneAction(ActionEvent actionEvent){
        exitCreateUserBtn.getScene().getWindow().hide();
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
