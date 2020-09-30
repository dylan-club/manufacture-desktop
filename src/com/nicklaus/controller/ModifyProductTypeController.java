package com.nicklaus.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.nicklaus.domain.ProductType;
import com.nicklaus.service.ProductTypeService;
import com.nicklaus.service.impl.ProductTypeServiceImpl;
import com.nicklaus.util.DialogUtils;
import com.nicklaus.view.validator.CommonValidator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class ModifyProductTypeController implements Initializable {

    private DialogUtils dialogUtils = new DialogUtils();

    private ProductTypeService productTypeService = ProductTypeServiceImpl.getInstance();

    @FXML
    public JFXTextField idInput;

    @FXML
    public JFXTextField productTypeNameInput;

    @FXML
    public JFXButton exitBtn;

    @FXML
    public JFXButton modifyProductTypeBtn;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //TODO
    }

    public void exitAction(ActionEvent event){
        exitBtn.getScene().getWindow().hide();
    }

    public void modifyProductTypeAction(ActionEvent event){
        String productTypeName = CommonValidator.notNullInput(productTypeNameInput.getText());
        String id = idInput.getText();


        //如果输入为空
        if ("".equals(productTypeName)){
            dialogUtils.addControl(modifyProductTypeBtn).setTitle("提示信息").setMessage("类别名称不能为空！").setConfirmBtn("确定").create();
        }else{

            ProductType type = new ProductType();
            type.setId(Integer.parseInt(id));
            type.setName(productTypeName);

            String message = productTypeService.modify(type);

            dialogUtils.addControl(modifyProductTypeBtn).setTitle("提示信息").setMessage(message).setConfirmBtn("确定").create();

        }
    }
}
