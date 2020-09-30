package com.nicklaus.controller;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.nicklaus.domain.User;
import com.nicklaus.util.LoginUtils;
import com.nicklaus.util.NavigationUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML
    private JFXHamburger navbar;

    @FXML
    private JFXDrawer sideBar;

    @FXML
    private AnchorPane holderPane;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        navbar.addEventHandler(MouseEvent.MOUSE_PRESSED,event -> {
            if (sideBar.isOpened()){
                sideBar.close();
            }else{
                sideBar.open();
            }
        });

        User loginUser = LoginUtils.getUser();

        try {
            VBox sideBarPane;

            //如果是超级管理员
            if ("admin".equals(loginUser.getUsername()) && "admin".equals(loginUser.getPassword())){
                sideBarPane = FXMLLoader.load(getClass().getResource(NavigationUtils.adminSideBarView));
            }else{
                //如果是云工厂管理员
                if (loginUser.getPower().equals("云工厂")){
                    sideBarPane = FXMLLoader.load(getClass().getResource(NavigationUtils.factorySideBarView));
                }else {
                    //如果是经销商
                    sideBarPane = FXMLLoader.load(getClass().getResource(NavigationUtils.agentSideBarView));
                }
            }

            //超级管理员面板
            AnchorPane startPane = FXMLLoader.load(getClass().getResource(NavigationUtils.startView));
            AnchorPane userInfoPane = FXMLLoader.load(getClass().getResource(NavigationUtils.userInfoView));
            AnchorPane factoryInfoPane = FXMLLoader.load(getClass().getResource(NavigationUtils.factoryInfoView));
            AnchorPane productInfoPane = FXMLLoader.load(getClass().getResource(NavigationUtils.productInfoView));
            AnchorPane productTypeInfoPane = FXMLLoader.load(getClass().getResource(NavigationUtils.productTypeInfoView));
            AnchorPane deviceInfoPane = FXMLLoader.load(getClass().getResource(NavigationUtils.deviceInfoView));
            AnchorPane deviceTypeInfoPane = FXMLLoader.load(getClass().getResource(NavigationUtils.deviceTypeInfoView));

            //云工厂管理员面板
            AnchorPane myDeviceInfoPane = FXMLLoader.load(getClass().getResource(NavigationUtils.myDeviceInfoView));
            AnchorPane receiveOrderInfoPane = FXMLLoader.load(getClass().getResource(NavigationUtils.receiveOrderInfoView));
            AnchorPane produceOrderInfoPane = FXMLLoader.load(getClass().getResource(NavigationUtils.produceOrderInfoView));

            //经销商面板
            AnchorPane myOrderInfoPane = FXMLLoader.load(getClass().getResource(NavigationUtils.myOrderInfoView));
            setNode(startPane);

            sideBar.setSidePane(sideBarPane);

            for (Node node : sideBarPane.getChildren()) {
                if (node.getAccessibleText() != null) {
                    node.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent ev) -> {
                        switch (node.getAccessibleText()) {
                            //超级管理员界面
                            case "startMenu":
                                sideBar.close();
                                setNode(startPane);
                                break;
                            case "userInfoMenu":
                                sideBar.close();
                                setNode(userInfoPane);
                                break;
                            case "factoryInfoMenu":
                                sideBar.close();
                                setNode(factoryInfoPane);
                                break;
                            case "productTypeInfoMenu":
                                sideBar.close();
                                setNode(productTypeInfoPane);
                                break;
                            case "productInfoMenu":
                                sideBar.close();
                                setNode(productInfoPane);
                                break;
                            case "deviceInfoMenu":
                                sideBar.close();
                                setNode(deviceInfoPane);
                                break;
                            case "deviceTypeInfoMenu":
                                sideBar.close();
                                setNode(deviceTypeInfoPane);
                                break;
                            //云工厂管理员界面
                            case "myDeviceInfoMenu":
                                sideBar.close();
                                setNode(myDeviceInfoPane);
                                break;
                            case "receiveOrderInfoMenu":
                                sideBar.close();
                                setNode(receiveOrderInfoPane);
                                break;
                            case "produceOrderInfoMenu":
                                sideBar.close();
                                setNode(produceOrderInfoPane);
                                break;
                            case "myOrderInfoMenu":
                                sideBar.close();
                                setNode(myOrderInfoPane);
                                break;
                        }
                    });
                }

            }

        }catch (IOException exception){

        }
    }

    private void setNode(Node node){
        holderPane.getChildren().clear();
        holderPane.getChildren().add(node);
    }
}
