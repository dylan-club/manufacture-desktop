package com.nicklaus.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import com.nicklaus.domain.DeviceType;
import com.nicklaus.domain.Product;
import com.nicklaus.domain.ProductType;
import com.nicklaus.service.DeviceTypeService;
import com.nicklaus.service.impl.DeviceTypeServiceImpl;
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

public class DeviceTypeInfoController implements Initializable {

    private DeviceTypeService deviceTypeService = DeviceTypeServiceImpl.getInstance();

    private DialogUtils dialogUtils = new DialogUtils();

    @FXML
    private JFXTextField nameInput;

    @FXML
    private JFXButton searchBtn;

    @FXML
    private JFXButton refreshBtn;

    @FXML
    private JFXButton removeBtn;

    @FXML
    private JFXButton createBtn;

    @FXML
    private JFXButton modifyBtn;

    @FXML
    private TableView<DeviceType> deviceTypeTable;

    @FXML
    private TableColumn<DeviceType, Integer> idCol;

    @FXML
    private TableColumn<DeviceType, String> nameCol;

    @FXML
    private TableColumn<DeviceType, JFXCheckBox> checkBoxCol;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //初始化表格
        initializeTable();

        //查找所有数据
        deviceTypeTable.setItems(FXCollections.observableList(deviceTypeService.findAll()));
    }

    private void initializeTable(){
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        checkBoxCol.setCellValueFactory(cellData ->cellData.getValue().myCheckBox.getCheckBox());
    }

    public void createAction(ActionEvent event) throws IOException {
        SceneUtils.openPane(NavigationUtils.createDeviceTypeView,this);
    }

    public void searchAction(ActionEvent event){
        String name = CommonValidator.notNullInput(nameInput.getText());

        if ("".equals(name)){
            dialogUtils.addControl(searchBtn).setTitle("提示信息").setMessage("设备类别不能为空！").setConfirmBtn("确定").create();
        }else{
            dialogUtils.addControl(searchBtn).setTitle("提示信息").setMessage("查询成功！").setConfirmBtn("确定").create();

            List<DeviceType> types = deviceTypeService.findByName(name);

            if (types.size()!=0){
                deviceTypeTable.setItems(FXCollections.observableList(types));
            }else {
                deviceTypeTable.setItems(null);
            }
        }
    }

    public void refreshAction(ActionEvent event){
        //重置输入框
        nameInput.setText(null);

        //重置表单
        deviceTypeTable.setItems(FXCollections.observableList(deviceTypeService.findAll()));
    }

    public void removeAction(ActionEvent event) {
        ObservableList<DeviceType> types = (deviceTypeTable.getItems() == null ? FXCollections.observableList(new ArrayList<>()) : deviceTypeTable.getItems());
        boolean isRemove = false;
        DeviceType removeType = new DeviceType();
        if (types.size()!=0){
            int count = 0;
            for (DeviceType type: types) {
                if (type.myCheckBox.isSelected()){
                    isRemove = true;
                    count++;
                    removeType = type;
                }
            }

            //表格中有被选中的条目
            if (isRemove){

                //提示是否有多选
                if (count > 1){
                    dialogUtils.addControl(removeBtn).setTitle("提示信息").setMessage("只能选择一个设备类别进行删除，请勿多选！").setCancelBtn(null).setConfirmBtn("确定").create();
                }else{
                    dialogUtils.addControl(removeBtn).setTitle("提示信息").setMessage("您确认要删除选中条目吗？").setCancelBtn("取消").setConfirmBtn("确定").create();
                    //判断是否需要删除
                    if (dialogUtils.getFlag()){
                        String message = deviceTypeService.remove(removeType);
                        dialogUtils.addControl(removeBtn).setTitle("提示信息").setMessage(message).setCancelBtn(null).setConfirmBtn("确定").create();
                        deviceTypeTable.setItems(FXCollections.observableList(deviceTypeService.findAll()));
                    }
                }


            }else{
                dialogUtils.addControl(removeBtn).setTitle("提示信息").setMessage("请先选择一个设备类别进行修改！").setCancelBtn(null).setConfirmBtn("确定").create();
            }
        }else{
            dialogUtils.addControl(removeBtn).setTitle("提示信息").setMessage("当前设备类别表中没有数据，无法删除！").setCancelBtn(null).setConfirmBtn("确定").create();
        }
    }

    public void modifyAction(ActionEvent event) throws IOException {
        ObservableList<DeviceType> types = (deviceTypeTable.getItems() == null ? FXCollections.observableList(new ArrayList<>()) : deviceTypeTable.getItems());
        boolean isModify = false;
        DeviceType modifyType = new DeviceType();
        if (types.size()!=0){
            int count = 0;
            for (DeviceType type: types) {
                if (type.myCheckBox.isSelected()){
                    isModify = true;
                    count++;
                    modifyType = type;
                }
            }

            //表格中有被选中的条目
            if (isModify){

                //提示是否有多选
                if (count > 1){
                    dialogUtils.addControl(modifyBtn).setTitle("提示信息").setMessage("只能选择一个设备类别进行修改，请勿多选！").setCancelBtn(null).setConfirmBtn("确定").create();
                }else{
                    //打开窗口
                    FXMLLoader fxmlLoader = SceneUtils.openPane(NavigationUtils.modifyDeviceTypeView, this);
                    ModifyDeviceTypeController controller = fxmlLoader.getController();

                    //设置初始值
                    controller.idInput.setText(String.valueOf(modifyType.getId()));
                    controller.deviceTypeNameInput.setText(modifyType.getName());
                }


            }else{
                dialogUtils.addControl(modifyBtn).setTitle("提示信息").setMessage("请先选择一个设备类别进行修改！").setCancelBtn(null).setConfirmBtn("确定").create();
            }
        }else{
            dialogUtils.addControl(modifyBtn).setTitle("提示信息").setMessage("当前设备类别表中没有数据，无法删除！").setCancelBtn(null).setConfirmBtn("确定").create();
        }
    }
}
