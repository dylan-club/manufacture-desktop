package com.nicklaus.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.nicklaus.domain.Order;
import com.nicklaus.service.OrderService;
import com.nicklaus.service.ProductService;
import com.nicklaus.service.impl.OrderServiceImpl;
import com.nicklaus.service.impl.ProductServiceImpl;
import com.nicklaus.util.DialogUtils;
import com.nicklaus.util.LoginUtils;
import com.nicklaus.view.validator.CommonValidator;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;

import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.regex.Pattern;

public class CreateOrderController implements Initializable {

    private ProductService productService = ProductServiceImpl.getInstance();

    private OrderService orderService = OrderServiceImpl.getInstance();

    DialogUtils dialogUtils = new DialogUtils();

    @FXML
    private JFXButton exitBtn;

    @FXML
    private JFXButton createOrderBtn;

    @FXML
    private JFXTextField orderIdInput;

    @FXML
    private ChoiceBox<String> productNameInput;

    @FXML
    private JFXTextField amountInput;

    @FXML
    private JFXDatePicker endDatePicker;


    @FXML
    private JFXDatePicker startDatePicker;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //初始化数据
        orderIdInput.setText(UUID.randomUUID().toString());
        productNameInput.setItems(FXCollections.observableList(productService.findAllNames()));
    }

    public void exitAction(ActionEvent event){
        exitBtn.getScene().getWindow().hide();
    }

    public void createOrderAction(ActionEvent event) throws ParseException {
        //获取数据
        String orderId = orderIdInput.getText();
        String productName = CommonValidator.notNullInput(productNameInput.getSelectionModel().getSelectedItem());
        String amount = CommonValidator.notNullInput(amountInput.getText());
        LocalDate endDate = endDatePicker.getValue();
        LocalDate startDate = startDatePicker.getValue();

        //判断商品和需求数量是否为空
        if ("".equals(productName) || "".equals(amount)){
            dialogUtils.addControl(createOrderBtn).setTitle("提示信息").setMessage("产品名称和采购数量不能为空！").setCancelBtn(null).setConfirmBtn("确定").create();
        }else{
            if (Pattern.matches("[1-9]\\d*",amount)){
                if (endDate == null || startDate == null){
                    dialogUtils.addControl(createOrderBtn).setTitle("提示信息").setMessage("请选择投标截至日期和交付日期！").setCancelBtn(null).setConfirmBtn("确定").create();
                }else{
                    if (endDate.isBefore(startDate)){
                        dialogUtils.addControl(createOrderBtn).setTitle("提示信息").setMessage("投标截至日期必须在交付日期之前！").setCancelBtn(null).setConfirmBtn("确定").create();
                    }else{
                        Order order = new Order();
                        order.setOrderId(orderId);
                        order.setProductName(productName);
                        order.setAmount(Integer.parseInt(amount));
                        order.setEndDate(endDate.toString());
                        order.setStartDate(startDate.toString());
                        order.setSalemanNickName(LoginUtils.getUser().getUsername());
                        order.setSalemanPhone(LoginUtils.getUser().getPhone());
                        order.setOrderState("已保存");
                        String message = orderService.add(order);
                        dialogUtils.addControl(createOrderBtn).setTitle("提示信息").setMessage(message).setCancelBtn(null).setConfirmBtn("确定").create();
                    }
                }
            }else{
                dialogUtils.addControl(createOrderBtn).setTitle("提示信息").setMessage("采购数量必须是大于0的正整数！").setCancelBtn(null).setConfirmBtn("确定").create();
            }
        }

    }
}
