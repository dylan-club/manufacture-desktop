package com.nicklaus.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.nicklaus.domain.Order;
import com.nicklaus.domain.User;
import com.nicklaus.service.OrderService;
import com.nicklaus.service.impl.OrderServiceImpl;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ReceiveOrderInfoController implements Initializable {

    private OrderService orderService = OrderServiceImpl.getInstance();

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
    private JFXButton receiveOrderBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeTable();
        //初始化数据
        if (LoginUtils.getUser().getPower().equals("云工厂")){
            orderTable.setItems(FXCollections.observableList(orderService.findByOrderState("已发布")));
        }
    }

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

    public void refreshAction(ActionEvent event){
        orderTable.setItems(FXCollections.observableList(orderService.findByOrderState("已发布")));
    }

    public void receiveOrderAction(ActionEvent event){
        ObservableList<Order> orders = orderTable.getItems();
        boolean isReceive = false;
        if (orders.size()!=0){
            for (Order order: orders) {
                if (order.myCheckBox.isSelected()){
                    isReceive = true;
                }
            }

            //表格中有被选中的条目，提示云工厂是否需要接单
            if (isReceive){
                dialogUtils.addControl(receiveOrderBtn).setTitle("提示信息").setMessage("您确认要将选中条目进行接单吗？").setCancelBtn("取消").setConfirmBtn("确定").create();

                if (dialogUtils.getFlag()){
                    for (Order order: orders) {
                        if (order.myCheckBox.isSelected()){
                            orderService.modifyStateAndFactoryName(order,LoginUtils.getUser().getFactoryName());
                        }
                    }

                    //接单成功，刷新表格
                    dialogUtils.addControl(receiveOrderBtn).setTitle("提示信息").setMessage("接单成功！").setCancelBtn(null).setConfirmBtn("确定").create();
                    orderTable.setItems(FXCollections.observableList(orderService.findByOrderState("已发布")));
                }
            }else{
                dialogUtils.addControl(receiveOrderBtn).setTitle("提示信息").setMessage("请先选择一个订单进行接单！").setCancelBtn(null).setConfirmBtn("确定").create();
            }
        }else{
            dialogUtils.addControl(receiveOrderBtn).setTitle("提示信息").setMessage("当前订单表中没有数据，无法接单！").setCancelBtn(null).setConfirmBtn("确定").create();
        }
    }
}
