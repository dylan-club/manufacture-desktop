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
import javafx.scene.control.ToggleGroup;

import java.net.URL;
import java.util.ResourceBundle;

public class ModifyUserController implements Initializable{

    private DialogUtils dialogUtils = new DialogUtils();

    private UserService userService = UserServiceImpl.getInstance();

    @FXML
    public JFXTextField idInput;

    @FXML
    public JFXTextField powerInput;

    @FXML
    public JFXTextField usernameInput;

    @FXML
    public JFXTextField passwordInput;

    @FXML
    public JFXTextField nameInput;

    @FXML
    public JFXTextField phoneInput;

    @FXML
    public JFXTextField factoryNameInput;

    @FXML
    public JFXTextField factoryDetailInput;

    @FXML
    public JFXRadioButton factoryRadio;

    @FXML
    public JFXRadioButton salemanRadio;

    @FXML
    public JFXButton modifyBtn;

    @FXML
    public JFXButton exitModifyUserBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //默认将一些信息置灰（不可编辑）
        usernameInput.setEditable(false);

    }


    public void exitModifyPaneAction(ActionEvent event){
        exitModifyUserBtn.getScene().getWindow().hide();
    }

    public void modifyUserAction(ActionEvent event){
        int id = Integer.parseInt(idInput.getText());
        String password = passwordInput.getText().trim();
        String name = nameInput.getText().trim();
        String phone = phoneInput.getText().trim();
        String factoryName = factoryNameInput.getText().trim();
        String detail = factoryDetailInput.getText().trim();

        User modifyUser = new User();

        modifyUser.setId(id);
        modifyUser.setUsername(usernameInput.getText());
        modifyUser.setPassword(password);
        modifyUser.setName(name);
        modifyUser.setPhone(phone);
        modifyUser.setPower(powerInput.getText());
        modifyUser.setFactoryName(factoryName);
        modifyUser.setDetail(detail);

        String validateMsg = UserValidator.validate(modifyUser);
        //验证成功，进行保存
        if (validateMsg == null){
            userService.modifyUser(modifyUser);
            dialogUtils.addControl(modifyBtn).setTitle("提示信息").setMessage("修改成功！").setConfirmBtn("确定").create();
        }else{
            //验证失败，弹出提示信息
            dialogUtils.addControl(modifyBtn).setTitle("提示信息").setMessage(validateMsg).setConfirmBtn("确定").create();
        }


    }
}
