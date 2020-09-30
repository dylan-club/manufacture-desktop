package com.nicklaus.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import com.nicklaus.domain.Device;
import com.nicklaus.domain.DeviceType;
import com.nicklaus.domain.ProductType;
import com.nicklaus.domain.User;
import com.nicklaus.service.ProductTypeService;
import com.nicklaus.service.impl.ProductTypeServiceImpl;
import com.nicklaus.util.DialogUtils;
import com.nicklaus.util.NavigationUtils;
import com.nicklaus.util.SceneUtils;
import com.nicklaus.view.validator.CommonValidator;
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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ProductTypeInfoController implements Initializable {

    private ProductTypeService productTypeService = ProductTypeServiceImpl.getInstance();

    private DialogUtils dialogUtils = new DialogUtils();

    @FXML
    private JFXButton openCreatePaneBtn;

    @FXML
    private JFXButton refreshBtn;

    @FXML
    private TableView<ProductType> productTypeTable;

    @FXML
    private TableColumn<ProductType,Integer> idCol;

    @FXML
    private TableColumn<ProductType,String> nameCol;

    @FXML
    private TableColumn<ProductType, JFXCheckBox> checkBoxCol;

    @FXML
    private JFXTextField nameInput;

    @FXML
    private JFXButton searchBtn;

    @FXML
    private JFXButton removeBtn;

    @FXML
    private JFXButton modifyBtn;


    private void initializeTable(){
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        checkBoxCol.setCellValueFactory(cellData ->cellData.getValue().myCheckBox.getCheckBox());
    }

    public void openCreatePaneAction(ActionEvent event) throws IOException {
        SceneUtils.openPane(NavigationUtils.createProductTypeView,this);
    }

    public void refreshAction(ActionEvent event){
        //重置输入框
        nameInput.setText(null);

        //重置表单
        productTypeTable.setItems(FXCollections.observableList(productTypeService.findAll()));
    }

    public void searchAction(ActionEvent event){
        String name = CommonValidator.notNullInput(nameInput.getText());

        if ("".equals(name)){
            dialogUtils.addControl(searchBtn).setTitle("提示信息").setMessage("产品类别不能为空！").setCancelBtn(null).setConfirmBtn("确定").create();
        }else{
            dialogUtils.addControl(searchBtn).setTitle("提示信息").setMessage("查询成功！").setCancelBtn(null).setConfirmBtn("确定").create();

            List<ProductType> types = productTypeService.findByName(name);

            if (types.size()!=0){
                productTypeTable.setItems(FXCollections.observableList(types));
            }else {
                productTypeTable.setItems(null);
            }
        }
    }

    public void removeAction(ActionEvent event){
        ObservableList<ProductType> types = (productTypeTable.getItems() == null ? FXCollections.observableList(new ArrayList<>()) : productTypeTable.getItems());
        boolean isRemove = false;
        if (types.size()!=0){
            for (ProductType type: types) {
                if (type.myCheckBox.isSelected()){
                    isRemove = true;
                }
            }

            //表格中有被选中的条目，提示用户是否需要删除
            if (isRemove){
                dialogUtils.addControl(removeBtn).setTitle("提示信息").setMessage("您确认要删除选中条目吗？").setCancelBtn("取消").setConfirmBtn("确定").create();

                if (dialogUtils.getFlag()){
                    for (ProductType type: types) {
                        if (type.myCheckBox.isSelected()){
                            productTypeService.remove(type);
                        }
                    }

                    //删除成功，刷新表格
                    productTypeTable.setItems(FXCollections.observableList(productTypeService.findAll()));
                }
            }else{
                dialogUtils.addControl(removeBtn).setTitle("提示信息").setMessage("请先选择一个产品类别进行删除！").setCancelBtn(null).setConfirmBtn("确定").create();
            }
        }else{
            dialogUtils.addControl(removeBtn).setTitle("提示信息").setMessage("当前产品类别表中没有数据，无法删除！").setCancelBtn(null).setConfirmBtn("确定").create();
        }
    }

    public void modifyAction(ActionEvent event) throws IOException {
        ObservableList<ProductType> types = (productTypeTable.getItems() == null ? FXCollections.observableList(new ArrayList<>()) : productTypeTable.getItems());
        boolean isModify = false;
        ProductType modifyType = new ProductType();
        if (types.size()!=0){
            int count = 0;
            for (ProductType type: types) {
                if (type.myCheckBox.isSelected()){
                    isModify = true;
                    count++;
                    modifyType = type;
                }
            }

            //表格中有被选中的条目
            if (isModify){

                //提示是否有多选
                if (count > 1){
                    dialogUtils.addControl(modifyBtn).setTitle("提示信息").setMessage("只能选择一个产品类别进行修改，请勿多选！").setCancelBtn(null).setConfirmBtn("确定").create();
                }else{
                    //打开窗口
                    FXMLLoader fxmlLoader = SceneUtils.openPane(NavigationUtils.modifyProductTypeView, this);
                    ModifyProductTypeController controller = fxmlLoader.getController();

                    //设置初始值
                    controller.idInput.setText(String.valueOf(modifyType.getId()));
                    controller.productTypeNameInput.setText(modifyType.getName());
                }


            }else{
                dialogUtils.addControl(modifyBtn).setTitle("提示信息").setMessage("请先选择一个产品类别进行修改！").setCancelBtn(null).setConfirmBtn("确定").create();
            }
        }else{
            dialogUtils.addControl(modifyBtn).setTitle("提示信息").setMessage("当前产品类别表中没有数据，无法删除！").setCancelBtn(null).setConfirmBtn("确定").create();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //初始化表格
        initializeTable();

        //查找所有数据
        productTypeTable.setItems(FXCollections.observableList(productTypeService.findAll()));
    }


}
