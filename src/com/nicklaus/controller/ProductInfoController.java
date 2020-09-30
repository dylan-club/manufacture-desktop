package com.nicklaus.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import com.nicklaus.domain.Product;
import com.nicklaus.domain.ProductType;
import com.nicklaus.service.ProductService;
import com.nicklaus.service.impl.ProductServiceImpl;
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

public class ProductInfoController implements Initializable {

    private ProductService productService = ProductServiceImpl.getInstance();

    private DialogUtils dialogUtils = new DialogUtils();

    @FXML
    private JFXButton createBtn;

    @FXML
    private JFXButton removeBtn;

    @FXML
    private JFXButton findBtn;

    @FXML
    private JFXButton modifyBtn;

    @FXML
    private JFXButton refreshBtn;

    @FXML
    private TableView<Product> productTable;

    @FXML
    private TableColumn<Product,Integer> idCol;

    @FXML
    private TableColumn<Product,String> productIdCol;

    @FXML
    private TableColumn<Product,String> nameCol;

    @FXML
    private TableColumn<Product,String> productTypeNameCol;

    @FXML
    private TableColumn<Product,String> productSizeCol;

    @FXML
    private TableColumn<Product,String> productDetailCol;

    @FXML
    private TableColumn<Product, JFXCheckBox> checkBoxCol;

    @FXML
    private JFXTextField nameInput;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeTable();

        //查找所有数据
        productTable.setItems(FXCollections.observableList(productService.findAll()));
    }

    public void initializeTable(){
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("productId"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productTypeNameCol.setCellValueFactory(new PropertyValueFactory<>("productTypeName"));
        productSizeCol.setCellValueFactory(new PropertyValueFactory<>("productSize"));
        productDetailCol.setCellValueFactory(new PropertyValueFactory<>("productDetail"));
        checkBoxCol.setCellValueFactory(cellData ->cellData.getValue().myCheckBox.getCheckBox());
    }

    public void createAction(ActionEvent event) throws IOException {
        SceneUtils.openPane(NavigationUtils.createProductView,this);
    }

    public void removeAction(ActionEvent event){
        ObservableList<Product> products = (productTable.getItems() == null ? FXCollections.observableList(new ArrayList<>()) : productTable.getItems());
        boolean isRemove = false;
        if (products.size()!=0){
            for (Product product: products) {
                if (product.myCheckBox.isSelected()){
                    isRemove = true;
                }
            }

            //表格中有被选中的条目，提示用户是否需要删除
            if (isRemove){
                dialogUtils.addControl(removeBtn).setTitle("提示信息").setMessage("您确认要删除选中条目吗？").setCancelBtn("取消").setConfirmBtn("确定").create();

                if (dialogUtils.getFlag()){
                    for (Product product: products) {
                        if (product.myCheckBox.isSelected()){
                            productService.remove(product);
                        }
                    }

                    //删除成功，刷新表格
                    productTable.setItems(FXCollections.observableList(productService.findAll()));
                }
            }else{
                dialogUtils.addControl(removeBtn).setTitle("提示信息").setMessage("请先选择一个产品进行删除！").setCancelBtn(null).setConfirmBtn("确定").create();
            }
        }else{
            dialogUtils.addControl(removeBtn).setTitle("提示信息").setMessage("当前产品表中没有数据，无法删除！").setCancelBtn(null).setConfirmBtn("确定").create();
        }
    }

    public void findAction(ActionEvent event){
        String name = CommonValidator.notNullInput(nameInput.getText());


        if ("".equals(name)){
            dialogUtils.addControl(findBtn).setTitle("提示信息").setMessage("产品名称不能为空！").setCancelBtn(null).setConfirmBtn("确定").create();
        }else{
            dialogUtils.addControl(findBtn).setTitle("提示信息").setMessage("查询成功！").setCancelBtn(null).setConfirmBtn("确定").create();

            List<Product> products = productService.findByName(name);

            if (products.size()!=0){
                productTable.setItems(FXCollections.observableList(products));
            }else {
                productTable.setItems(null);
            }
        }
    }

    public void modifyAction(ActionEvent event) throws IOException {
        ObservableList<Product> products = (productTable.getItems() == null ? FXCollections.observableList(new ArrayList<>()) : productTable.getItems());
        boolean isModify = false;
        Product modifyProduct = new Product();
        if (products.size()!=0){
            int count = 0;
            for (Product product: products) {
                if (product.myCheckBox.isSelected()){
                    isModify = true;
                    count++;
                    modifyProduct = product;
                }
            }

            //表格中有被选中的条目
            if (isModify){

                //提示是否有多选
                if (count > 1){
                    dialogUtils.addControl(modifyBtn).setTitle("提示信息").setMessage("只能选择一个产品进行修改，请勿多选！").setCancelBtn(null).setConfirmBtn("确定").create();
                }else{
                    //打开窗口
                    FXMLLoader fxmlLoader = SceneUtils.openPane(NavigationUtils.modifyProductView, this);
                    ModifyProductController controller = fxmlLoader.getController();

                    //设置初始值
                    controller.idInput.setText(String.valueOf(modifyProduct.getId()));
                    controller.productIdInput.setText(modifyProduct.getProductId());
                    controller.nameInput.setText(modifyProduct.getName());
                    controller.productTypeNameInput.setValue(modifyProduct.getProductTypeName());
                    controller.productSizeInput.setText(modifyProduct.getProductSize());
                    controller.productDetailInput.setText(modifyProduct.getProductDetail());

                }


            }else{
                dialogUtils.addControl(modifyBtn).setTitle("提示信息").setMessage("请先选择一个产品进行修改！").setCancelBtn(null).setConfirmBtn("确定").create();
            }
        }else{
            dialogUtils.addControl(modifyBtn).setTitle("提示信息").setMessage("当前产品信息表中没有数据，无法修改！").setCancelBtn(null).setConfirmBtn("确定").create();
        }
    }

    public void refreshAction(ActionEvent event){
        //重置输入框
        nameInput.setText(null);

        //重置表单
        productTable.setItems(FXCollections.observableList(productService.findAll()));
    }
}
