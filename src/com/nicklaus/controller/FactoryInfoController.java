package com.nicklaus.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import com.nicklaus.domain.DeviceType;
import com.nicklaus.domain.User;
import com.nicklaus.service.UserService;
import com.nicklaus.service.impl.UserServiceImpl;
import com.nicklaus.util.DialogUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class FactoryInfoController implements Initializable {

    private UserService userService = UserServiceImpl.getInstance();

    private DialogUtils dialogUtils = new DialogUtils();

    @FXML
    private JFXButton findByFactoryNameBtn;

    @FXML
    private JFXButton resetBtn;

    @FXML
    private JFXButton switchStateBtn;

    @FXML
    private JFXTextField searchFactoryNameInput;

    @FXML
    private TableView<User> userTable;

    @FXML
    private TableColumn<User, Integer> idCol;

    @FXML
    private TableColumn<User, String> factoryNameCol;

    @FXML
    private TableColumn<User, String> detailCol;

    @FXML
    private TableColumn<User, String> nameCol;

    @FXML
    private TableColumn<User, String> phoneCol;

    @FXML
    private TableColumn<User, String> usernameCol;

    @FXML
    private TableColumn<User, String> stateCol;

    @FXML
    private TableColumn<User, JFXCheckBox> checkBoxCol;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeTable();

        //查询所有工厂管理员用户
        List<User> factoryManagers = userService.findAllUserByPower("云工厂");

        if (factoryManagers.size() != 0){
            userTable.setItems(FXCollections.observableList(factoryManagers));
        }
    }

    private void initializeTable() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        factoryNameCol.setCellValueFactory(new PropertyValueFactory<>("factoryName"));
        detailCol.setCellValueFactory(new PropertyValueFactory<>("detail"));
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        stateCol.setCellValueFactory(new PropertyValueFactory<>("state"));
        checkBoxCol.setCellValueFactory(cellData ->cellData.getValue().myCheckBox.getCheckBox());
    }

    public void resetAction(ActionEvent event){
        //重置输入框
        searchFactoryNameInput.setText(null);

        //重置表单
        userTable.setItems(FXCollections.observableList(userService.findAllUserByPower("云工厂")));
    }

    public void findByFactoryNameAction(ActionEvent event){
        //获取用户输入的工厂名称
        String name = searchFactoryNameInput.getText();

        if (name != null){
            name = name.trim();
        }else{
            name = "";
        }

        if ("".equals(name)){
            dialogUtils.addControl(findByFactoryNameBtn).setTitle("提示信息").setMessage("工厂名称不能为空！").setCancelBtn(null).setConfirmBtn("确定").create();
        }else{
            dialogUtils.addControl(findByFactoryNameBtn).setTitle("提示信息").setMessage("查询成功！").setCancelBtn(null).setConfirmBtn("确定").create();

            List<User> factories = userService.findByPowerAndName("云工厂",name);
            userTable.setItems(FXCollections.observableList(factories));
        }
    }

    public void switchStateAction(ActionEvent event){
        ObservableList<User> factories = (userTable.getItems() == null ? FXCollections.observableList(new ArrayList<>()) : userTable.getItems());
        boolean isRemove = false;
        if (factories.size()!=0){
            for (User user: factories) {
                if (user.myCheckBox.isSelected()){
                    isRemove = true;
                }
            }

            //表格中有被选中的条目，提示用户是否需要切换状态
            if (isRemove){
                dialogUtils.addControl(switchStateBtn).setTitle("提示信息").setMessage("您确认要切换选中条目的状态吗？").setCancelBtn("取消").setConfirmBtn("确定").create();

                if (dialogUtils.getFlag()){
                    for (User user: factories) {
                        if (user.myCheckBox.isSelected()){
                            userService.toggleStatus(user);
                        }
                    }

                    //切换成功，刷新表格
                    userTable.setItems(FXCollections.observableList(userService.findAllUserByPower("云工厂")));
                }
            }else{
                dialogUtils.addControl(switchStateBtn).setTitle("提示信息").setMessage("请先选择一个工厂进行状态切换！").setCancelBtn(null).setConfirmBtn("确定").create();
            }
        }else{
            dialogUtils.addControl(switchStateBtn).setTitle("提示信息").setMessage("当前工厂表中没有数据，无法切换状态！").setCancelBtn(null).setConfirmBtn("确定").create();
        }
    }
}
