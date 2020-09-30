package com.nicklaus.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.nicklaus.domain.DeviceType;
import com.nicklaus.domain.ProductType;
import com.nicklaus.service.DeviceTypeService;
import com.nicklaus.service.ProductTypeService;
import com.nicklaus.service.impl.DeviceTypeServiceImpl;
import com.nicklaus.service.impl.ProductTypeServiceImpl;
import com.nicklaus.util.DialogUtils;
import com.nicklaus.view.validator.CommonValidator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class ModifyDeviceTypeController implements Initializable {

    private DialogUtils dialogUtils = new DialogUtils();

    private DeviceTypeService deviceTypeService = DeviceTypeServiceImpl.getInstance();

    @FXML
    public JFXTextField idInput;

    @FXML
    public JFXTextField deviceTypeNameInput;

    @FXML
    public JFXButton exitBtn;

    @FXML
    public JFXButton modifyDeviceTypeBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //TODO
    }

    public void exitAction(ActionEvent event){
        exitBtn.getScene().getWindow().hide();
    }

    public void modifyDeviceTypeAction(ActionEvent event){
        String deviceTypeName = CommonValidator.notNullInput(deviceTypeNameInput.getText());
        String id = idInput.getText();


        //如果输入为空
        if ("".equals(deviceTypeName)){
            dialogUtils.addControl(modifyDeviceTypeBtn).setTitle("提示信息").setMessage("设备类别不能为空！").setConfirmBtn("确定").create();
        }else{

            DeviceType type = new DeviceType();
            type.setId(Integer.parseInt(id));
            type.setName(deviceTypeName);

            String message = deviceTypeService.modify(type);

            dialogUtils.addControl(modifyDeviceTypeBtn).setTitle("提示信息").setMessage(message).setConfirmBtn("确定").create();

        }
    }
}
