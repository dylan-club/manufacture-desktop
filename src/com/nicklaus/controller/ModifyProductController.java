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
import javafx.scene.control.SingleSelectionModel;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ModifyProductController implements Initializable {

    private ProductService productService = ProductServiceImpl.getInstance();

    private DialogUtils dialogUtils = new DialogUtils();

    @FXML
    public ChoiceBox<String> productTypeNameInput;

    @FXML
    public JFXTextField idInput;

    @FXML
    public JFXTextField productIdInput;

    @FXML
    public JFXTextField nameInput;

    @FXML
    public JFXTextField productSizeInput;

    @FXML
    public JFXTextField productDetailInput;

    @FXML
    public JFXButton modifyProductBtn;

    @FXML
    public JFXButton exitBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        productTypeNameInput.setItems(FXCollections.observableList(productService.findAllTypes()));
    }

    public void exitAction(ActionEvent event){
        exitBtn.getScene().getWindow().hide();
    }

    public void modifyProductAction(ActionEvent event){
        String productTypeName = CommonValidator.notNullInput(productTypeNameInput.getSelectionModel().getSelectedItem());
        String name = CommonValidator.notNullInput(nameInput.getText());



        //如果输入为空
        if ("".equals(productTypeName) || "".equals(name)){
            dialogUtils.addControl(modifyProductBtn).setTitle("提示信息").setMessage("产品名称和产品类别不能为空！").setConfirmBtn("确定").create();
        }else{
            Product product = new Product();
            product.setId(Integer.parseInt(idInput.getText()));
            product.setProductId(productIdInput.getText());
            product.setName(name);
            product.setProductTypeName(productTypeName);
            product.setProductSize(productSizeInput.getText());
            product.setProductDetail(productDetailInput.getText());
            //修改产品
            String message = productService.modify(product);

            dialogUtils.addControl(modifyProductBtn).setTitle("提示信息").setMessage(message).setConfirmBtn("确定").create();

        }
    }
}
