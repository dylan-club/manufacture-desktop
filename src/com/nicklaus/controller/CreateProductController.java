package com.nicklaus.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.nicklaus.domain.Product;
import com.nicklaus.service.ProductService;
import com.nicklaus.service.impl.ProductServiceImpl;
import com.nicklaus.util.DialogUtils;
import com.nicklaus.view.validator.CommonValidator;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CreateProductController implements Initializable {

    private ProductService productService = ProductServiceImpl.getInstance();

    private DialogUtils dialogUtils = new DialogUtils();

    @FXML
    private JFXTextField nameInput;

    @FXML
    private ChoiceBox<String> productTypeNameInput;

    @FXML
    private JFXTextField productSizeInput;

    @FXML
    private JFXTextField productDetailInput;

    @FXML
    private JFXButton exitBtn;

    @FXML
    private JFXButton createProductBtn;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        productTypeNameInput.setItems(FXCollections.observableList(productService.findAllTypes()));

    }

    public void exitAction(ActionEvent event){
        exitBtn.getScene().getWindow().hide();
    }

    public void createProductAction(ActionEvent event){
        String productTypeName = CommonValidator.notNullInput(productTypeNameInput.getSelectionModel().getSelectedItem());
        String name = CommonValidator.notNullInput(nameInput.getText());



        //如果输入为空
        if ("".equals(productTypeName) || "".equals(name)){
            dialogUtils.addControl(createProductBtn).setTitle("提示信息").setMessage("产品名称和产品类别不能为空！").setConfirmBtn("确定").create();
        }else{
            Product product = new Product();
            product.setName(name);
            product.setProductTypeName(productTypeName);
            product.setProductSize(productSizeInput.getText());
            product.setProductDetail(productDetailInput.getText());
            //添加产品
            String message = productService.add(product);

            dialogUtils.addControl(createProductBtn).setTitle("提示信息").setMessage(message).setConfirmBtn("确定").create();

        }
    }
}
