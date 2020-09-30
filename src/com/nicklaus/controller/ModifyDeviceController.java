package com.nicklaus.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.nicklaus.domain.Device;
import com.nicklaus.service.DeviceService;
import com.nicklaus.service.impl.DeviceServiceImpl;
import com.nicklaus.util.DialogUtils;
import com.nicklaus.view.validator.CommonValidator;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;

import java.net.URL;
import java.util.ResourceBundle;

public class ModifyDeviceController implements Initializable {

    private DeviceService deviceService = DeviceServiceImpl.getInstance();

    private DialogUtils dialogUtils = new DialogUtils();

    @FXML
    public JFXTextField idInput;

    @FXML
    public JFXTextField deviceIdInput;

    @FXML
    public JFXTextField nameInput;

    @FXML
    public ChoiceBox<String> deviceTypeNameInput;

    @FXML
    public JFXTextField deviceSizeInput;

    @FXML
    public JFXTextField deviceDetailInput;

    @FXML
    private JFXButton exitBtn;

    @FXML
    private JFXButton modifyDeviceBtn;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        deviceTypeNameInput.setItems(FXCollections.observableList(deviceService.findAllTypes()));

    }

    public void exitAction(ActionEvent event){
        exitBtn.getScene().getWindow().hide();
    }

    public void modifyDeviceAction(ActionEvent event){
        String deviceTypeName = CommonValidator.notNullInput(deviceTypeNameInput.getSelectionModel().getSelectedItem());
        String name = CommonValidator.notNullInput(nameInput.getText());



        //如果输入为空
        if ("".equals(deviceTypeName) || "".equals(name)){
            dialogUtils.addControl(modifyDeviceBtn).setTitle("提示信息").setMessage("设备名称和设备类别不能为空！").setConfirmBtn("确定").create();
        }else{
            Device device = new Device();
            device.setName(name);
            device.setDeviceTypeName(deviceTypeName);
            device.setDeviceSize(deviceSizeInput.getText());
            device.setDeviceDetail(deviceDetailInput.getText());
            device.setId(Integer.parseInt(idInput.getText()));
            device.setDeviceId(deviceIdInput.getText());
            //修改设备
            String message = deviceService.modify(device);

            dialogUtils.addControl(modifyDeviceBtn).setTitle("提示信息").setMessage(message).setConfirmBtn("确定").create();

        }
    }

}
