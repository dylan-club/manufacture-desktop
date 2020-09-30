package com.nicklaus.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
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
import com.nicklaus.util.NavigationUtils;
import com.nicklaus.util.SceneUtils;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ProduceOrderInfoController implements Initializable {

    private OrderService orderService = OrderServiceImpl.getInstance();

    private DeviceService deviceService = DeviceServiceImpl.getInstance();

    private OrderCapacityService orderCapacityService = OrderCapacityServiceImpl.getInstance();

    DialogUtils dialogUtils = new DialogUtils();

    @FXML
    private TableView<Order> orderTable;

    @FXML
    private TableColumn<Order, Integer> idCol;

    @FXML
    private TableColumn<Order, String> orderIdCol;

    @FXML
    private TableColumn<Order, String> productNameCol;

    @FXML
    private TableColumn<Order, Integer> amountCol;

    @FXML
    private TableColumn<Order, LocalDate> endDateCol;

    @FXML
    private TableColumn<Order, LocalDate> startDateCol;

    @FXML
    private TableColumn<Order, String> salemanNickNameCol;

    @FXML
    private TableColumn<Order, String> salemanPhoneCol;

    @FXML
    private TableColumn<Order, String> orderStateCol;

    @FXML
    private TableColumn<Order, JFXCheckBox> checkBoxCol;

    @FXML
    private JFXButton refreshBtn;

    @FXML
    private JFXButton produceOrderBtn;

    @FXML
    private JFXButton finishOrderBtn;


    private void initializeTable(){
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        orderIdCol.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("productName"));
        amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
        endDateCol.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        startDateCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        salemanNickNameCol.setCellValueFactory(new PropertyValueFactory<>("salemanNickName"));
        salemanPhoneCol.setCellValueFactory(new PropertyValueFactory<>("salemanPhone"));
        orderStateCol.setCellValueFactory(new PropertyValueFactory<>("orderState"));
        checkBoxCol.setCellValueFactory(cellData ->cellData.getValue().myCheckBox.getCheckBox());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeTable();
        //初始化表格数据
        if (LoginUtils.getUser().getPower().equals("云工厂")){
            orderTable.setItems(FXCollections.observableList(orderService.findForFactory(LoginUtils.getUser().getFactoryName())));
        }
    }

    public void refreshAction(ActionEvent event){
        orderTable.setItems(FXCollections.observableList(orderService.findForFactory(LoginUtils.getUser().getFactoryName())));
    }

    public void produceOrderAction(ActionEvent event) throws IOException {
        ObservableList<Order> orders = orderTable.getItems();
        boolean isAddCapacity = false;
        Order selectOrder = new Order(); //要配置设备的订单
        if (orders.size()!=0){
            int count = 0;
            for (Order order: orders) {
                if (order.myCheckBox.isSelected()){
                    isAddCapacity = true;
                    count++;
                    selectOrder = order;
                }
            }

            //表格中有被选中的条目
            if (isAddCapacity){

                //提示是否有多选
                if (count > 1){
                    dialogUtils.addControl(produceOrderBtn).setTitle("提示信息").setMessage("只能选择一个订单进行排产，请勿多选！").setCancelBtn(null).setConfirmBtn("确定").create();
                }else{
                    if (selectOrder.getOrderState().equals("已接单") || selectOrder.getOrderState().equals("生产中")){
                        //打开排产面板
                        FXMLLoader fxmlLoader = SceneUtils.openPane(NavigationUtils.createOrderCapacityView, this);

                        //初始化参数
                        CreateOrderCapacityController controller = fxmlLoader.getController();
                        controller.orderIdInput.setText(selectOrder.getOrderId());
                        controller.productNameInput.setText(selectOrder.getProductName());
                        controller.deviceNameInput.setItems(FXCollections.observableList(deviceService.findAvailableDevicesByProductName(selectOrder.getProductName(),LoginUtils.getUser().getFactoryName())));
                        controller.deviceCapacityTable.setItems(FXCollections.observableList(orderCapacityService.findByOrderId(selectOrder.getOrderId())));
                    }else{
                        //只有已接单状态的订单可以排产
                        dialogUtils.addControl(produceOrderBtn).setTitle("提示信息").setMessage("只有已接单或生产中的订单可以进行排产！").setCancelBtn(null).setConfirmBtn("确定").create();
                    }
                }

            }else{
                dialogUtils.addControl(produceOrderBtn).setTitle("提示信息").setMessage("请先选择一个订单！").setCancelBtn(null).setConfirmBtn("确定").create();
            }
        }else{
            dialogUtils.addControl(produceOrderBtn).setTitle("提示信息").setMessage("当前订单表没有数据，无法排产！").setCancelBtn(null).setConfirmBtn("确定").create();
        }
    }

    public void finishOrderAction(ActionEvent event){
        ObservableList<Order> orders = orderTable.getItems();
        boolean isFinish = false;
        Order selectOrder = new Order(); //要配置设备的订单
        if (orders.size()!=0){
            int count = 0;
            for (Order order: orders) {
                if (order.myCheckBox.isSelected()){
                    isFinish = true;
                    count++;
                    selectOrder = order;
                }
            }

            //表格中有被选中的条目
            if (isFinish){

                //提示是否有多选
                if (count > 1){
                    dialogUtils.addControl(finishOrderBtn).setTitle("提示信息").setMessage("只能选择一个订单进行完工，请勿多选！").setCancelBtn(null).setConfirmBtn("确定").create();
                }else{
                    if (selectOrder.getOrderState().equals("生产中")){
                        //让当前订单中的设备处于闲置中
                        List<OrderCapacity> capacities = orderCapacityService.findByOrderId(selectOrder.getOrderId());
                        //找到了当前订单中的设备
                        for (OrderCapacity orderCapacity:
                             capacities) {
                            //修改设备状态
                            deviceService.modifyDeviceStateByDeviceIdAndState(orderCapacity.getDeviceId(),"闲置中");
                        }

                        //修改订单状态为已发货
                        orderService.modifyOrderState(selectOrder,"已发货");
                        dialogUtils.addControl(finishOrderBtn).setTitle("提示信息").setMessage("订单发货成功！").setCancelBtn(null).setConfirmBtn("确定").create();

                    }else{
                        //只有已接单状态的订单可以排产
                        dialogUtils.addControl(finishOrderBtn).setTitle("提示信息").setMessage("只有生产中的订单可以进行完工！").setCancelBtn(null).setConfirmBtn("确定").create();
                    }
                }

            }else{
                dialogUtils.addControl(finishOrderBtn).setTitle("提示信息").setMessage("请先选择一个订单！").setCancelBtn(null).setConfirmBtn("确定").create();
            }
        }else{
            dialogUtils.addControl(finishOrderBtn).setTitle("提示信息").setMessage("当前订单表没有数据，无法完工！").setCancelBtn(null).setConfirmBtn("确定").create();
        }
    }
}
