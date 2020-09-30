package com.nicklaus.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.nicklaus.service.DeviceTypeService;
import com.nicklaus.service.ProductTypeService;
import com.nicklaus.service.impl.DeviceTypeServiceImpl;
import com.nicklaus.service.impl.ProductTypeServiceImpl;
import com.nicklaus.util.DialogUtils;
import com.nicklaus.view.validator.CommonValidator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class CreateDeviceTypeController {
    private DeviceTypeService deviceTypeService = DeviceTypeServiceImpl.getInstance();

    private DialogUtils dialogUtils = new DialogUtils();

    @FXML
    private JFXButton exitBtn;

    @FXML
    private JFXTextField deviceTypeNameInput;

    @FXML
    private JFXButton createDeviceTypeBtn;

    public void exitAction(ActionEvent event){
        exitBtn.getScene().getWindow().hide();
    }

    public void createDeviceTypeAction(ActionEvent event){
        String deviceTypeName = CommonValidator.notNullInput(deviceTypeNameInput.getText());

        //如果输入为空
        if ("".equals(deviceTypeName)){
            dialogUtils.addControl(createDeviceTypeBtn).setTitle("提示信息").setMessage("设备类型不能为空！").setConfirmBtn("确定").create();
        }else{

            String message = deviceTypeService.add(deviceTypeName);

            dialogUtils.addControl(createDeviceTypeBtn).setTitle("提示信息").setMessage(message).setConfirmBtn("确定").create();

        }

    }
}
