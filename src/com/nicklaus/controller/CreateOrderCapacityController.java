package com.nicklaus.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.nicklaus.domain.CapacityView;
import com.nicklaus.domain.Device;
import com.nicklaus.domain.Order;
import com.nicklaus.domain.OrderCapacity;
import com.nicklaus.service.DeviceService;
import com.nicklaus.service.OrderCapacityService;
import com.nicklaus.service.OrderService;
import com.nicklaus.service.impl.DeviceServiceImpl;
import com.nicklaus.service.impl.OrderCapacityServiceImpl;
import com.nicklaus.service.impl.OrderServiceImpl;
import com.nicklaus.util.DialogUtils;
import com.nicklaus.util.LoginUtils;
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
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class CreateOrderCapacityController implements Initializable {

    private DialogUtils dialogUtils = new DialogUtils();
    private DeviceService deviceService = DeviceServiceImpl.getInstance();
    private OrderCapacityService orderCapacityService = OrderCapacityServiceImpl.getInstance();
    private OrderService orderService = OrderServiceImpl.getInstance();

    @FXML
    public JFXTextField orderIdInput;

    @FXML
    public JFXTextField productNameInput;

    @FXML
    public ChoiceBox<String> deviceNameInput;

    @FXML
    private JFXDatePicker startDatePicker;

    @FXML
    private JFXDatePicker endDatePicker;

    @FXML
    private JFXButton exitBtn;

    @FXML
    private JFXButton removeBtn;

    @FXML
    private JFXButton addBtn;

    @FXML
    public TableView<OrderCapacity> deviceCapacityTable;

    @FXML
    private TableColumn<OrderCapacity, Integer> idCol;

    @FXML
    private TableColumn<OrderCapacity, String> orderIdCol;

    @FXML
    private TableColumn<OrderCapacity, String> deviceNameCol;

    @FXML
    private TableColumn<OrderCapacity, String> startDateCol;

    @FXML
    private TableColumn<OrderCapacity, String> endDateCol;

    @FXML
    private TableColumn<OrderCapacity, JFXCheckBox> checkBoxCol;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeTable();
    }

    public void initializeTable(){
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        orderIdCol.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        deviceNameCol.setCellValueFactory(new PropertyValueFactory<>("deviceName"));
        startDateCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endDateCol.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        checkBoxCol.setCellValueFactory(cellData ->cellData.getValue().myCheckBox.getCheckBox());
    }

    public void removeAction(ActionEvent event){
        ObservableList<OrderCapacity> orderCapacities = deviceCapacityTable.getItems();
        boolean isRemove = false;
        if (orderCapacities.size()!=0){
            for (OrderCapacity orderCapacity: orderCapacities) {
                if (orderCapacity.myCheckBox.isSelected()){
                    isRemove = true;
                }
            }

            //表格中有被选中的条目，提示用户是否需要删除
            if (isRemove){
                dialogUtils.addControl(removeBtn).setTitle("提示信息").setMessage("您确认要删除选中条目吗？").setCancelBtn("取消").setConfirmBtn("确定").create();

                if (dialogUtils.getFlag()){
                    for (OrderCapacity orderCapacity: orderCapacities) {
                        if (orderCapacity.myCheckBox.isSelected()){
                            //先更新设备的状态
                            deviceService.modifyDeviceStateByDeviceIdAndState(orderCapacity.getDeviceId(),"闲置中");
                            //后删除记录
                            orderCapacityService.remove(orderCapacity);
                        }
                    }


                    //更新设备名称下拉框
                    deviceNameInput.setItems(FXCollections.observableList(deviceService.findAvailableDevicesByProductName(productNameInput.getText(),LoginUtils.getUser().getFactoryName())));
                    //删除成功，刷新表格
                    deviceCapacityTable.setItems(FXCollections.observableList(orderCapacityService.findByOrderId(orderIdInput.getText())));
                }
            }else{
                dialogUtils.addControl(removeBtn).setTitle("提示信息").setMessage("请先选择一个排产设备进行删除！").setCancelBtn(null).setConfirmBtn("确定").create();
            }
        }else{
            dialogUtils.addControl(removeBtn).setTitle("提示信息").setMessage("当前排产表中没有数据，无法删除！").setCancelBtn(null).setConfirmBtn("确定").create();
        }
    }

    public void addOrderCapacityAction(ActionEvent event){
        //获取数据
        String orderId = orderIdInput.getText();
        String deviceName = CommonValidator.notNullInput(deviceNameInput.getSelectionModel().getSelectedItem());

        LocalDate endDate = endDatePicker.getValue();
        LocalDate startDate = startDatePicker.getValue();

        //判断设备是否选择
        if ("".equals(deviceName)){
            dialogUtils.addControl(addBtn).setTitle("提示信息").setMessage("请先选择一个设备！").setCancelBtn(null).setConfirmBtn("确定").create();
        }else{
            if (endDate == null || startDate == null){
                dialogUtils.addControl(addBtn).setTitle("提示信息").setMessage("请选择起止日期！").setCancelBtn(null).setConfirmBtn("确定").create();
            }else{
                if (endDate.isBefore(startDate)){
                    dialogUtils.addControl(addBtn).setTitle("提示信息").setMessage("结束时间必须在开始时间之前！").setCancelBtn(null).setConfirmBtn("确定").create();
                }else{
                    //开始添加设备
                    Device device = deviceService.findByName(deviceName).get(0);
                    OrderCapacity orderCapacity = new OrderCapacity();
                    orderCapacity.setOrderId(orderId);
                    orderCapacity.setDeviceId(device.getDeviceId());
                    orderCapacity.setDeviceName(deviceName);
                    orderCapacity.setStartDate(startDate.toString());
                    orderCapacity.setEndDate(endDate.toString());
                    //先修改设备的状态
                    deviceService.modifyDeviceStateByDeviceIdAndState(device.getDeviceId(),"生产中");
                    //修改订单状态
                    Order order = new Order();
                    order.setOrderId(orderId);
                    orderService.modifyOrderState(order,"生产中");
                    //再添加设备
                    String message = orderCapacityService.add(orderCapacity);
                    dialogUtils.addControl(addBtn).setTitle("提示信息").setMessage(message).setCancelBtn(null).setConfirmBtn("确定").create();

                    //更新设备名称列表
                    deviceNameInput.setItems(FXCollections.observableList(deviceService.findAvailableDevicesByProductName(productNameInput.getText(),LoginUtils.getUser().getFactoryName())));
                    //更新排产表
                    deviceCapacityTable.setItems(FXCollections.observableList(orderCapacityService.findByOrderId(orderId)));
                }
            }

        }
    }

    public void exitAction(ActionEvent event){
        exitBtn.getScene().getWindow().hide();
    }
}
