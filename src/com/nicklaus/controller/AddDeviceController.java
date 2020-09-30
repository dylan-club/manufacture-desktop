package com.nicklaus.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.nicklaus.domain.Device;
import com.nicklaus.domain.User;
import com.nicklaus.service.DeviceService;
import com.nicklaus.service.impl.DeviceServiceImpl;
import com.nicklaus.util.DialogUtils;
import com.nicklaus.util.LoginUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class AddDeviceController implements Initializable {

    private DeviceService deviceService = DeviceServiceImpl.getInstance();

    private DialogUtils dialogUtils = new DialogUtils();

    @FXML
    private JFXButton exitBtn;

    @FXML
    private JFXButton addDeviceBtn;

    @FXML
    private TableView<Device> deviceTable;

    @FXML
    private TableColumn<Device,Integer> idCol;

    @FXML
    private TableColumn<Device,String> deviceIdCol;

    @FXML
    private TableColumn<Device,String> nameCol;

    @FXML
    private TableColumn<Device,String> deviceTypeNameCol;

    @FXML
    private TableColumn<Device,String> deviceSizeCol;

    @FXML
    private TableColumn<Device,String> deviceDetailCol;

    @FXML
    private TableColumn<Device,String> deviceStateCol;

    @FXML
    private TableColumn<Device, JFXCheckBox> checkBoxCol;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeTable();

        //初始化可租用列表
        deviceTable.setItems(FXCollections.observableList(deviceService.findAvailableDevices()));
    }

    public void initializeTable(){
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        deviceIdCol.setCellValueFactory(new PropertyValueFactory<>("deviceId"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        deviceTypeNameCol.setCellValueFactory(new PropertyValueFactory<>("deviceTypeName"));
        deviceSizeCol.setCellValueFactory(new PropertyValueFactory<>("deviceSize"));
        deviceDetailCol.setCellValueFactory(new PropertyValueFactory<>("deviceDetail"));
        deviceStateCol.setCellValueFactory(new PropertyValueFactory<>("deviceState"));
        checkBoxCol.setCellValueFactory(cellData ->cellData.getValue().myCheckBox.getCheckBox());
    }

    public void exitAction(ActionEvent event){
        exitBtn.getScene().getWindow().hide();
    }

    public void addDeviceAction(ActionEvent event){
        ObservableList<Device> devices = deviceTable.getItems();
        boolean isAdd = false;
        if (devices.size()!=0){
            for (Device device: devices) {
                if (device.myCheckBox.isSelected()){
                    isAdd = true;
                }
            }

            //表格中有被选中的条目，提示用户是否需要租用
            if (isAdd){
                dialogUtils.addControl(addDeviceBtn).setTitle("提示信息").setMessage("您确认要租用选中设备吗？").setCancelBtn("取消").setConfirmBtn("确定").create();

                if (dialogUtils.getFlag()){
                    for (Device device: devices) {
                        if (device.myCheckBox.isSelected()){
                            deviceService.rentalDevice(device, LoginUtils.getUser().getFactoryName());
                        }
                    }

                    dialogUtils.addControl(addDeviceBtn).setTitle("提示信息").setMessage("租用成功！").setCancelBtn(null).setConfirmBtn("确定").create();

                    //租用成功刷新表格
                    deviceTable.setItems(FXCollections.observableList(deviceService.findAvailableDevices()));
                }
            }else{
                dialogUtils.addControl(addDeviceBtn).setTitle("提示信息").setMessage("请至少选择一个设备，再租用！").setCancelBtn(null).setConfirmBtn("确定").create();
            }
        }else{
            dialogUtils.addControl(addDeviceBtn).setTitle("提示信息").setMessage("当前租用表中没有数据，无法租用！").setCancelBtn(null).setConfirmBtn("确定").create();
        }
    }
}
