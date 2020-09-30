package com.nicklaus.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import com.nicklaus.domain.CapacityView;
import com.nicklaus.domain.DeviceCapacity;
import com.nicklaus.domain.User;
import com.nicklaus.service.DeviceCapacityService;
import com.nicklaus.service.ProductService;
import com.nicklaus.service.impl.DeviceCapacityServiceImpl;
import com.nicklaus.service.impl.ProductServiceImpl;
import com.nicklaus.util.DialogUtils;
import com.nicklaus.view.validator.CommonValidator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class AddDeviceCapacityController implements Initializable {

    private ProductService productService = ProductServiceImpl.getInstance();

    private DeviceCapacityService deviceCapacityService = DeviceCapacityServiceImpl.getInstance();

    private DialogUtils dialogUtils = new DialogUtils();

    @FXML
    private JFXButton exitBtn;

    @FXML
    private JFXButton removeBtn;

    @FXML
    private JFXButton addBtn;

    @FXML
    public JFXTextField deviceIdInput;

    @FXML
    public JFXTextField deviceNameInput;

    @FXML
    public ChoiceBox<String> productNameInput;

    @FXML
    public JFXTextField amountInput;

    @FXML
    public TableView<CapacityView> deviceCapacityTable;

    @FXML
    private TableColumn<CapacityView, Integer> idCol;

    @FXML
    private TableColumn<CapacityView, String> deviceNameCol;

    @FXML
    private TableColumn<CapacityView, String> productNameCol;

    @FXML
    private TableColumn<CapacityView, Integer> amountCol;

    @FXML
    private TableColumn<CapacityView, JFXCheckBox> checkBoxCol;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeTable();
        deviceNameInput.setEditable(false);
        deviceIdInput.setEditable(false);

        //加载所有产品的名称
        productNameInput.setItems(FXCollections.observableList(productService.findAllNames()));
    }

    private void initializeTable(){
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        deviceNameCol.setCellValueFactory(new PropertyValueFactory<>("deviceName"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("productName"));
        amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
        checkBoxCol.setCellValueFactory(cellData ->cellData.getValue().myCheckBox.getCheckBox());
    }

    public void exitAction(ActionEvent event){
        exitBtn.getScene().getWindow().hide();
    }

    public void addDeviceCapacityAction(ActionEvent event){
        String deviceName = deviceNameInput.getText();
        String productName = CommonValidator.notNullInput(productNameInput.getSelectionModel().getSelectedItem());
        String amount = CommonValidator.notNullInput(amountInput.getText());

        if ("".equals(productName) || "".equals(amount)){
            dialogUtils.addControl(addBtn).setTitle("提示信息").setMessage("产品名称和产能不能为空！").setCancelBtn(null).setConfirmBtn("确定").create();
        }else{
            //定义正则
            String positiveRegex = "[1-9]\\d*";
            if (Pattern.matches(positiveRegex,amount)){
                //添加产品
                CapacityView capacityView = new CapacityView();
                capacityView.setDeviceName(deviceName);
                capacityView.setProductName(productName);
                capacityView.setAmount(Integer.parseInt(amount));
                String message = deviceCapacityService.add(capacityView);

                dialogUtils.addControl(addBtn).setTitle("提示信息").setMessage(message).setCancelBtn(null).setConfirmBtn("确定").create();

                //刷新表单
                deviceCapacityTable.setItems(FXCollections.observableList(deviceCapacityService.findByDeviceId(deviceIdInput.getText())));
            }else{
                dialogUtils.addControl(addBtn).setTitle("提示信息").setMessage("产能必须是大于0的正整数！").setCancelBtn(null).setConfirmBtn("确定").create();
            }
        }
    }

    public void removeAction(ActionEvent event){
        ObservableList<CapacityView> capacityViews = deviceCapacityTable.getItems();
        boolean isRemove = false;
        if (capacityViews.size()!=0){
            for (CapacityView capacityView: capacityViews) {
                if (capacityView.myCheckBox.isSelected()){
                    isRemove = true;
                }
            }

            //表格中有被选中的条目，提示用户是否需要删除
            if (isRemove){
                dialogUtils.addControl(removeBtn).setTitle("提示信息").setMessage("您确认要删除选中条目吗？").setCancelBtn("取消").setConfirmBtn("确定").create();

                if (dialogUtils.getFlag()){
                    for (CapacityView capacityView: capacityViews) {
                        if (capacityView.myCheckBox.isSelected()){
                            deviceCapacityService.remove(capacityView);
                        }
                    }

                    //删除成功，刷新表格
                    deviceCapacityTable.setItems(FXCollections.observableList(deviceCapacityService.findByDeviceId(deviceIdInput.getText())));
                }
            }else{
                dialogUtils.addControl(removeBtn).setTitle("提示信息").setMessage("请先选择一个配置产品进行删除！").setCancelBtn(null).setConfirmBtn("确定").create();
            }
        }else{
            dialogUtils.addControl(removeBtn).setTitle("提示信息").setMessage("当前产能表中没有数据，无法删除！").setCancelBtn(null).setConfirmBtn("确定").create();
        }
    }
}
