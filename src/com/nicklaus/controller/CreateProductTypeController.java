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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class CreateProductTypeController {

    private ProductTypeService productTypeService = ProductTypeServiceImpl.getInstance();

    private DialogUtils dialogUtils = new DialogUtils();

    @FXML
    private JFXButton exitBtn;

    @FXML
    private JFXTextField productTypeNameInput;

    @FXML
    private JFXButton createProductTypeBtn;

    public void exitAction(ActionEvent event){
        exitBtn.getScene().getWindow().hide();
    }

    public void createProductTypeAction(ActionEvent event){
        String productTypeName = CommonValidator.notNullInput(productTypeNameInput.getText());

        //如果输入为空
        if ("".equals(productTypeName)){
            dialogUtils.addControl(createProductTypeBtn).setTitle("提示信息").setMessage("类别名称不能为空！").setConfirmBtn("确定").create();
        }else{

            String message = productTypeService.add(productTypeName);

            dialogUtils.addControl(createProductTypeBtn).setTitle("提示信息").setMessage(message).setConfirmBtn("确定").create();

        }

    }
}
