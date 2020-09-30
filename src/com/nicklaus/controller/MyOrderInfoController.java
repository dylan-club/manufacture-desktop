package com.nicklaus.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.nicklaus.domain.Device;
import com.nicklaus.domain.Order;
import com.nicklaus.service.OrderService;
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
import java.util.ResourceBundle;

public class MyOrderInfoController implements Initializable {

    private OrderService orderService = OrderServiceImpl.getInstance();

    DialogUtils dialogUtils = new DialogUtils();

    @FXML
    private JFXButton createBtn;

    @FXML
    private JFXButton removeBtn;

    @FXML
    private JFXButton modifyBtn;

    @FXML
    private JFXButton pushBtn;

    @FXML
    private JFXButton refreshBtn;

    @FXML
    private JFXButton finishBtn;

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



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeTable();
        //初始化数据
        if (LoginUtils.getUser().getPower().equals("经销商")){
            orderTable.setItems(FXCollections.observableList(orderService.findForAgent(LoginUtils.getUser().getUsername())));
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

    public void createAction(ActionEvent event) throws IOException {
        SceneUtils.openPane(NavigationUtils.createOrderView,this);
    }

    public void removeAction(ActionEvent event){
        ObservableList<Order> orders = orderTable.getItems();
        boolean isRemove = false;
        Order removeOrder = new Order(); //要删除的订单
        if (orders.size()!=0){
            int count = 0;
            for (Order order: orders) {
                if (order.myCheckBox.isSelected()){
                    isRemove = true;
                    count++;
                    removeOrder = order;
                }
            }

            //表格中有被选中的条目
            if (isRemove){

                //提示是否有多选
                if (count > 1){
                    dialogUtils.addControl(removeBtn).setTitle("提示信息").setMessage("只能选择一个订单进行删除，请勿多选！").setCancelBtn(null).setConfirmBtn("确定").create();
                }else{
                    dialogUtils.addControl(removeBtn).setTitle("提示信息").setMessage("您确认要删除选中条目吗？").setCancelBtn("取消").setConfirmBtn("确定").create();
                    //判断是否需要删除
                    if (dialogUtils.getFlag()){
                        //只能删除订单状态为已保存的订单
                        if (removeOrder.getOrderState().equals("已保存")){
                            //删除订单
                            orderService.remove(removeOrder);

                            //刷新数据
                            orderTable.setItems(FXCollections.observableList(orderService.findForAgent(LoginUtils.getUser().getUsername())));
                        }else {
                            dialogUtils.addControl(removeBtn).setTitle("提示信息").setMessage("该订单不在已保存状态，无法删除！").setCancelBtn(null).setConfirmBtn("确定").create();
                        }

                    }
                }


            }else{
                dialogUtils.addControl(removeBtn).setTitle("提示信息").setMessage("请先选择一个订单进行删除！").setCancelBtn(null).setConfirmBtn("确定").create();
            }
        }else{
            dialogUtils.addControl(removeBtn).setTitle("提示信息").setMessage("当前订单表中没有数据，无法删除！").setCancelBtn(null).setConfirmBtn("确定").create();
        }
    }

    public void modifyAction(ActionEvent event) throws IOException {
        ObservableList<Order> orders = orderTable.getItems();
        boolean isModify = false;
        Order modifyOrder = new Order(); //要修改的订单
        if (orders.size()!=0){
            int count = 0;
            for (Order order: orders) {
                if (order.myCheckBox.isSelected()){
                    isModify = true;
                    count++;
                    modifyOrder = order;
                }
            }

            //表格中有被选中的条目
            if (isModify){

                //提示是否有多选
                if (count > 1){
                    dialogUtils.addControl(modifyBtn).setTitle("提示信息").setMessage("只能选择一个订单进行修改，请勿多选！").setCancelBtn(null).setConfirmBtn("确定").create();
                }else{
                    if (modifyOrder.getOrderState().equals("已保存")){

                        //打开修改订单界面
                        FXMLLoader fxmlLoader = SceneUtils.openPane(NavigationUtils.modifyOrderView, this);
                        ModifyOrderController controller = fxmlLoader.getController();

                        //设置默认信息
                        controller.idInput.setText(String.valueOf(modifyOrder.getId()));
                        controller.orderIdInput.setText(modifyOrder.getOrderId());
                        controller.productNameInput.setValue(modifyOrder.getProductName());
                        controller.amountInput.setText(String.valueOf(modifyOrder.getAmount()));
                        controller.endDatePicker.getEditor().setText(modifyOrder.getEndDate());
                        controller.startDatePicker.getEditor().setText(modifyOrder.getStartDate());

                    }else{
                        dialogUtils.addControl(modifyBtn).setTitle("提示信息").setMessage("只有已保存的订单可以修改！").setCancelBtn(null).setConfirmBtn("确定").create();
                    }
                }


            }else{
                dialogUtils.addControl(modifyBtn).setTitle("提示信息").setMessage("请先选择一个订单进行修改！").setCancelBtn(null).setConfirmBtn("确定").create();
            }
        }else{
            dialogUtils.addControl(modifyBtn).setTitle("提示信息").setMessage("当前订单表中没有数据，无法修改！").setCancelBtn(null).setConfirmBtn("确定").create();
        }
    }

    public void pushAction(ActionEvent event){
        ObservableList<Order> orders = orderTable.getItems();
        boolean isPush = false;
        Order pushOrder = new Order(); //发布的订单
        if (orders.size()!=0){
            int count = 0;
            for (Order order: orders) {
                if (order.myCheckBox.isSelected()){
                    isPush = true;
                    count++;
                    pushOrder = order;
                }
            }

            //表格中有被选中的条目
            if (isPush){

                //提示是否有多选
                if (count > 1){
                    dialogUtils.addControl(pushBtn).setTitle("提示信息").setMessage("只能选择一个订单进行发布，请勿多选！").setCancelBtn(null).setConfirmBtn("确定").create();
                }else{
                    if (pushOrder.getOrderState().equals("已保存")){

                        orderService.modifyOrderState(pushOrder, "已发布");
                        dialogUtils.addControl(pushBtn).setTitle("提示信息").setMessage("发布成功！").setCancelBtn(null).setConfirmBtn("确定").create();

                        //刷新表格
                        orderTable.setItems(FXCollections.observableList(orderService.findForAgent(LoginUtils.getUser().getUsername())));
                    }else{
                        dialogUtils.addControl(pushBtn).setTitle("提示信息").setMessage("只有已保存的订单可以发布！").setCancelBtn(null).setConfirmBtn("确定").create();
                    }
                }

            }else{
                dialogUtils.addControl(pushBtn).setTitle("提示信息").setMessage("请先选择一个订单进行发布！").setCancelBtn(null).setConfirmBtn("确定").create();
            }
        }else{
            dialogUtils.addControl(pushBtn).setTitle("提示信息").setMessage("当前订单表中没有数据，无法发布订单！").setCancelBtn(null).setConfirmBtn("确定").create();
        }
    }

    public void refreshAction(ActionEvent event){
        orderTable.setItems(FXCollections.observableList(orderService.findForAgent(LoginUtils.getUser().getUsername())));
    }

    public void finishAction(ActionEvent event){
        ObservableList<Order> orders = orderTable.getItems();
        boolean isFinish = false;
        Order finishOrder = new Order(); //完成的订单
        if (orders.size()!=0){
            int count = 0;
            for (Order order: orders) {
                if (order.myCheckBox.isSelected()){
                    isFinish = true;
                    count++;
                    finishOrder = order;
                }
            }

            //表格中有被选中的条目
            if (isFinish){

                //提示是否有多选
                if (count > 1){
                    dialogUtils.addControl(finishBtn).setTitle("提示信息").setMessage("只能选择一个订单进行完成，请勿多选！").setCancelBtn(null).setConfirmBtn("确定").create();
                }else{
                    if (finishOrder.getOrderState().equals("已发货")){

                        orderService.modifyOrderState(finishOrder, "已完成");
                        dialogUtils.addControl(finishBtn).setTitle("提示信息").setMessage("订单收货成功！").setCancelBtn(null).setConfirmBtn("确定").create();

                        //刷新表格
                        orderTable.setItems(FXCollections.observableList(orderService.findForAgent(LoginUtils.getUser().getUsername())));
                    }else{
                        dialogUtils.addControl(finishBtn).setTitle("提示信息").setMessage("只有已发货的订单可以完成！").setCancelBtn(null).setConfirmBtn("确定").create();
                    }
                }

            }else{
                dialogUtils.addControl(finishBtn).setTitle("提示信息").setMessage("请先选择一个订单进行完成！").setCancelBtn(null).setConfirmBtn("确定").create();
            }
        }else{
            dialogUtils.addControl(finishBtn).setTitle("提示信息").setMessage("当前订单表中没有数据，无法完成订单！").setCancelBtn(null).setConfirmBtn("确定").create();
        }
    }
}
