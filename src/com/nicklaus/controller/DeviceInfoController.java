package com.nicklaus.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import com.nicklaus.domain.Device;
import com.nicklaus.domain.DeviceType;
import com.nicklaus.service.DeviceService;
import com.nicklaus.service.impl.DeviceServiceImpl;
import com.nicklaus.util.DialogUtils;
import com.nicklaus.util.NavigationUtils;
import com.nicklaus.util.SceneUtils;
import com.nicklaus.view.validator.CommonValidator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DeviceInfoController implements Initializable {

    DeviceService deviceService = DeviceServiceImpl.getInstance();

    DialogUtils dialogUtils = new DialogUtils();

    @FXML
    private JFXButton findBtn;

    @FXML
    private JFXButton refreshBtn;

    @FXML
    private JFXButton createBtn;

    @FXML
    private JFXButton removeBtn;

    @FXML
    private JFXButton switchStateBtn;

    @FXML
    private JFXButton modifyBtn;

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
    private TableColumn<Device,String> deviceRentalStateCol;

    @FXML
    private TableColumn<Device,String> factoryNameCol;

    @FXML
    private TableColumn<Device, JFXCheckBox> checkBoxCol;

    @FXML
    private JFXTextField nameInput;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeTable();

        //查找所有数据
        deviceTable.setItems(FXCollections.observableList(deviceService.findAll()));
    }

    private void initializeTable(){
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        deviceIdCol.setCellValueFactory(new PropertyValueFactory<>("deviceId"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        deviceTypeNameCol.setCellValueFactory(new PropertyValueFactory<>("deviceTypeName"));
        deviceSizeCol.setCellValueFactory(new PropertyValueFactory<>("deviceSize"));
        deviceDetailCol.setCellValueFactory(new PropertyValueFactory<>("deviceDetail"));
        deviceStateCol.setCellValueFactory(new PropertyValueFactory<>("deviceState"));
        deviceRentalStateCol.setCellValueFactory(new PropertyValueFactory<>("deviceRentalState"));
        factoryNameCol.setCellValueFactory(new PropertyValueFactory<>("factoryName"));
        checkBoxCol.setCellValueFactory(cellData ->cellData.getValue().myCheckBox.getCheckBox());
    }

    public void findAction(ActionEvent event){
        String name = CommonValidator.notNullInput(nameInput.getText());

        if ("".equals(name)){
            dialogUtils.addControl(findBtn).setTitle("提示信息").setMessage("设备名称不能为空！").setConfirmBtn("确定").create();
        }else{
            dialogUtils.addControl(findBtn).setTitle("提示信息").setMessage("查询成功！").setConfirmBtn("确定").create();

            List<Device> devices = deviceService.findByName(name);

            if (devices.size()!=0){
                deviceTable.setItems(FXCollections.observableList(devices));
            }else {
                deviceTable.setItems(null);
            }
        }
    }

    public void refreshAction(ActionEvent event){
        //重置输入框
        nameInput.setText(null);

        //重置表单
        deviceTable.setItems(FXCollections.observableList(deviceService.findAll()));
    }

    public void createAction(ActionEvent event) throws IOException {
        SceneUtils.openPane(NavigationUtils.createDeviceView,this);
    }

    public void removeAction(ActionEvent event){
        ObservableList<Device> devices = (deviceTable.getItems() == null ? FXCollections.observableList(new ArrayList<>()) : deviceTable.getItems());
        boolean isRemove = false;
        Device removeDevice = new Device(); //要删除的设备
        if (devices.size()!=0){
            int count = 0;
            for (Device device: devices) {
                if (device.myCheckBox.isSelected()){
                    isRemove = true;
                    count++;
                    removeDevice = device;
                }
            }

            //表格中有被选中的条目
            if (isRemove){

                //提示是否有多选
                if (count > 1){
                    dialogUtils.addControl(removeBtn).setTitle("提示信息").setMessage("只能选择一个设备进行删除，请勿多选！").setCancelBtn(null).setConfirmBtn("确定").create();
                }else{
                    dialogUtils.addControl(removeBtn).setTitle("提示信息").setMessage("您确认要删除选中条目吗？").setCancelBtn("取消").setConfirmBtn("确定").create();
                    //判断是否需要删除
                    if (dialogUtils.getFlag()){
                        if (removeDevice.getDeviceRentalState().equals("未被租用")){
                            deviceService.remove(removeDevice);
                            dialogUtils.addControl(removeBtn).setTitle("提示信息").setMessage("删除成功").setCancelBtn(null).setConfirmBtn("确定").create();
                            deviceTable.setItems(FXCollections.observableList(deviceService.findAll()));
                        }else {
                            //工厂设备或设备已被租用，无法删除
                            dialogUtils.addControl(removeBtn).setTitle("提示信息").setMessage("此设备已被租用或此设备非产能中心设备，无法删除！").setCancelBtn(null).setConfirmBtn("确定").create();
                        }

                    }
                }


            }else{
                dialogUtils.addControl(removeBtn).setTitle("提示信息").setMessage("请先选择一个设备进行删除！").setCancelBtn(null).setConfirmBtn("确定").create();
            }
        }else{
            dialogUtils.addControl(removeBtn).setTitle("提示信息").setMessage("当前设备信息表中没有数据，无法删除！").setCancelBtn(null).setConfirmBtn("确定").create();
        }
    }

    public void switchStateAction(ActionEvent event){
        ObservableList<Device> devices = (deviceTable.getItems() == null ? FXCollections.observableList(new ArrayList<>()) : deviceTable.getItems());

        boolean isSwitch = false;
        Device switchDevice = new Device(); //要开关机的设备


        if (devices.size()!=0){
            int count = 0;
            for (Device device: devices) {
                if (device.myCheckBox.isSelected()){
                    isSwitch = true;
                    count++;
                    switchDevice = device;
                }
            }

            //表格中有被选中的条目
            if (isSwitch){

                //提示是否有多选
                if (count > 1){
                    dialogUtils.addControl(removeBtn).setTitle("提示信息").setMessage("只能选择一个设备进行开关机，请勿多选！").setCancelBtn(null).setConfirmBtn("确定").create();
                }else{
                    if (switchDevice.getDeviceRentalState().equals("未被租用")){
                        deviceService.modifyDeviceState(switchDevice);
                        dialogUtils.addControl(removeBtn).setTitle("提示信息").setMessage("切换状态成功").setCancelBtn(null).setConfirmBtn("确定").create();
                        deviceTable.setItems(FXCollections.observableList(deviceService.findAll()));
                    }else {
                        //工厂设备或设备已被租用，无法开关机
                        dialogUtils.addControl(removeBtn).setTitle("提示信息").setMessage("此设备已被租用或此设备非产能中心设备，无法开关机！").setCancelBtn(null).setConfirmBtn("确定").create();
                    }

                }


            }else{
                dialogUtils.addControl(removeBtn).setTitle("提示信息").setMessage("请先选择一个设备进行开关机！").setCancelBtn(null).setConfirmBtn("确定").create();
            }
        }else{
            dialogUtils.addControl(removeBtn).setTitle("提示信息").setMessage("当前设备表中没有数据，无法开关机！").setCancelBtn(null).setConfirmBtn("确定").create();
        }
    }

    public void modifyAction(ActionEvent event) throws IOException {
        ObservableList<Device> devices = (deviceTable.getItems() == null ? FXCollections.observableList(new ArrayList<>()) : deviceTable.getItems());
        boolean isModify = false;
        Device modifyDevice = new Device(); //要修改的设备
        if (devices.size()!=0){
            int count = 0;
            for (Device device: devices) {
                if (device.myCheckBox.isSelected()){
                    isModify = true;
                    count++;
                    modifyDevice = device;
                }
            }

            //表格中有被选中的条目
            if (isModify){

                //提示是否有多选
                if (count > 1){
                    dialogUtils.addControl(removeBtn).setTitle("提示信息").setMessage("只能选择一个设备进行修改，请勿多选！").setCancelBtn(null).setConfirmBtn("确定").create();
                }else{
                    if (!modifyDevice.getDeviceRentalState().equals("工厂设备")){
                        //打开窗口
                        FXMLLoader fxmlLoader = SceneUtils.openPane(NavigationUtils.modifyDeviceView, this);
                        ModifyDeviceController controller = fxmlLoader.getController();

                        //设置默认值
                        controller.idInput.setText(String.valueOf(modifyDevice.getId()));
                        controller.deviceIdInput.setText(modifyDevice.getDeviceId());
                        controller.nameInput.setText(modifyDevice.getName());
                        controller.deviceTypeNameInput.setValue(modifyDevice.getDeviceTypeName());
                        controller.deviceSizeInput.setText(modifyDevice.getDeviceSize());
                        controller.deviceDetailInput.setText(modifyDevice.getDeviceDetail());

                    }else {
                        //工厂设备无法修改
                        dialogUtils.addControl(removeBtn).setTitle("提示信息").setMessage("此设备为工厂设备，无法修改！").setCancelBtn(null).setConfirmBtn("确定").create();
                    }

                }


            }else{
                dialogUtils.addControl(removeBtn).setTitle("提示信息").setMessage("请先选择一个设备进行修改！").setCancelBtn(null).setConfirmBtn("确定").create();
            }
        }else{
            dialogUtils.addControl(removeBtn).setTitle("提示信息").setMessage("当前设备信息表中没有数据，无法修改！").setCancelBtn(null).setConfirmBtn("确定").create();
        }
    }


}
