package com.nicklaus.controller;

import com.jfoenix.controls.*;
import com.nicklaus.domain.User;
import com.nicklaus.service.UserService;
import com.nicklaus.service.impl.UserServiceImpl;
import com.nicklaus.util.DialogUtils;
import com.nicklaus.util.NavigationUtils;
import com.nicklaus.util.SceneUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Border;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class UserInfoController implements Initializable {

    UserService userService = UserServiceImpl.getInstance();

    DialogUtils dialogUtils = new DialogUtils();

    @FXML
    private TableView<User> userTable;

    @FXML
    private TableColumn<User,Integer> idCol;

    @FXML
    private TableColumn<User,String> usernameCol;

    @FXML
    private TableColumn<User,String> nameCol;

    @FXML
    private TableColumn<User,String> phoneCol;

    @FXML
    private TableColumn<User,String> powerCol;

    @FXML
    private TableColumn<User, JFXCheckBox> checkBoxCol;

    @FXML
    private JFXTextField searchNameInput;

    @FXML
    private JFXButton resetBtn;

    @FXML
    private JFXButton findByNameBtn;

    @FXML
    private JFXButton removeUsersBtn;


    @FXML
    private JFXButton openCreatePaneBtn;

    @FXML
    private JFXButton createModifyPaneBtn;

    //用户信息表
    private ObservableList<User> userList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        initializeTable();

        //查询所有的用户
        List<User> users = userService.findAllUsers();

        if (users != null){
            userList = FXCollections.observableList(users);

            userTable.setItems(userList);
        }
    }

    private void initializeTable() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        powerCol.setCellValueFactory(new PropertyValueFactory<>("power"));
        checkBoxCol.setCellValueFactory(cellData ->cellData.getValue().myCheckBox.getCheckBox());
    }


    public void findByNameAction(ActionEvent event){
        String name = searchNameInput.getText().trim();

        if (name != null){
            name = name.trim();
        }else{
            name = "";
        }

        if ("".equals(name)){
            dialogUtils.addControl(findByNameBtn).setTitle("提示信息").setMessage("姓名不能为空！").setCancelBtn(null).setConfirmBtn("确定").create();
        }else{
            dialogUtils.addControl(findByNameBtn).setTitle("提示信息").setMessage("查询成功！").setCancelBtn(null).setConfirmBtn("确定").create();

            List<User> users = userService.findByName(name);
            userTable.setItems(FXCollections.observableList(users));
        }
    }

    public void resetTableAction(ActionEvent event){
        //重置输入框
        searchNameInput.setText(null);

        //重置表单
        userTable.setItems(FXCollections.observableList(userService.findAllUsers()));
    }


    public void removeUsersAction(ActionEvent event){
        ObservableList<User> users = (userTable.getItems() == null ? FXCollections.observableList(new ArrayList<>()) : userTable.getItems());
        boolean isRemove = false;
        if (users.size()!=0){
            for (User user: users) {
                if (user.myCheckBox.isSelected()){
                    isRemove = true;
                }
            }

            //表格中有被选中的条目，提示用户是否需要删除
            if (isRemove){
                dialogUtils.addControl(removeUsersBtn).setTitle("提示信息").setMessage("您确认要删除选中条目吗？").setCancelBtn("取消").setConfirmBtn("确定").create();

                if (dialogUtils.getFlag()){
                    for (User user: users) {
                        if (user.myCheckBox.isSelected()){
                            userService.removeUser(user);
                        }
                    }

                    //删除成功，刷新表格
                    userTable.setItems(FXCollections.observableList(userService.findAllUsers()));
                }
            }else{
                dialogUtils.addControl(removeUsersBtn).setTitle("提示信息").setMessage("请先选择一个用户进行删除！").setCancelBtn(null).setConfirmBtn("确定").create();
            }
        }else{
            dialogUtils.addControl(removeUsersBtn).setTitle("提示信息").setMessage("当前User表中没有数据，无法删除！").setCancelBtn(null).setConfirmBtn("确定").create();
        }
    }

    public void openCreatePaneAction(ActionEvent event) throws IOException {
//        Stage stage = new Stage();
//
//        Parent root = FXMLLoader.load(this.getClass().getResource(NavigationUtils.createUserView));
//        JFXDecorator decorator = new JFXDecorator(stage, root, false, false, true);
//        decorator.setCustomMaximize(false);
//        decorator.setBorder(Border.EMPTY);
//
//
//        Scene scene = new Scene(decorator);
//        stage.setScene(scene);
//        stage.setResizable(false);
//        stage.setIconified(false);
//        stage.show();

        SceneUtils.openPane(NavigationUtils.createUserView,this);

    }

    public void createModifyPaneAction(ActionEvent event) throws IOException {
        int count = 0;
        ObservableList<User> users = (userTable.getItems() == null ? FXCollections.observableList(new ArrayList<>()) : userTable.getItems());
        User modifyUser = new User();
        //判断当前表格中是否有值
        if (users.size()!=0){

            //查看当前是否选中多个条目
            for (User user:users) {
                if (user.myCheckBox.isSelected()){
                    modifyUser = user;
                    count++;
                }
            }

            if (count > 1){
                dialogUtils.addControl(createModifyPaneBtn).setTitle("提示信息").setMessage("一次只能修改一个用户的信息，请勿多选！").setCancelBtn(null).setConfirmBtn("确定").create();
            }else if (count == 1){
                //跳转新的窗口
//                Stage stage = new Stage();
                FXMLLoader fxmlLoader = SceneUtils.openPane(NavigationUtils.modifyUserView, this);
//                FXMLLoader loader = new FXMLLoader(this.getClass().getResource(NavigationUtils.modifyUserView));
//                Parent root = loader.load();
                ModifyUserController modifyController = fxmlLoader.getController();

                //回显数据
                modifyController.idInput.setText(String.valueOf(modifyUser.getId()));
                modifyController.powerInput.setText(modifyUser.getPower());
                modifyController.usernameInput.setText(modifyUser.getUsername());
                modifyController.passwordInput.setText(modifyUser.getPassword());
                modifyController.nameInput.setText(modifyUser.getName());
                modifyController.phoneInput.setText(modifyUser.getPhone());

                modifyController.factoryRadio.setUserData("云工厂");
                modifyController.salemanRadio.setUserData("经销商");
                modifyController.factoryRadio.setDisable(true);
                modifyController.salemanRadio.setDisable(true);


                if (modifyUser.getPower().equals("云工厂")){
                    modifyController.factoryRadio.setSelected(true);
                }else{
                    modifyController.salemanRadio.setSelected(true);
                    modifyController.factoryDetailInput.setEditable(false);
                    modifyController.factoryNameInput.setEditable(false);
                }

                modifyController.factoryNameInput.setText(modifyUser.getFactoryName());
                modifyController.factoryDetailInput.setText(modifyUser.getDetail());

//                JFXDecorator decorator = new JFXDecorator(stage, root, false, false, true);
//                decorator.setCustomMaximize(false);
//                decorator.setBorder(Border.EMPTY);
//
//
//                Scene scene = new Scene(decorator);
//                stage.setScene(scene);
//                stage.setResizable(false);
//                stage.setIconified(false);
//                stage.show();
            }else{
                dialogUtils.addControl(createModifyPaneBtn).setTitle("提示信息").setMessage("请先选中一个需要修改的用户").setCancelBtn(null).setConfirmBtn("确定").create();
            }
        }else {
            dialogUtils.addControl(createModifyPaneBtn).setTitle("提示信息").setMessage("当前User表中没有数据，无法修改！").setCancelBtn(null).setConfirmBtn("确定").create();
        }
    }

}
