package com.nicklaus.util;

public class NavigationUtils {

    /**
     * 登录和注册面板
     */
    public static final String loginView;
    public static final String homeView;
    public static final String registrationView;
    public static final String anotherLoginView;

    /**
     * 不同用户的路由
     */
    public static final String adminSideBarView;  //超级管理员侧边栏
    public static final String agentSideBarView;  //经销商侧边栏
    public static final String factorySideBarView; //云工厂管理员侧边栏

    /**
     * 超级管理员功能面板
     */
    public static final String startView;
    /**
     * 用户管理面板
     */
    public static final String userInfoView;
    public static final String createUserView;
    public static final String modifyUserView;

    public static final String factoryInfoView;

    /**
     * 设备类别管理
     */
    public static final String deviceTypeInfoView;
    public static final String createDeviceTypeView;
    public static final String modifyDeviceTypeView;

    /**
     * 设备信息管理
     */
    public static final String deviceInfoView;
    public static final String createDeviceView;
    public static final String modifyDeviceView;

    /**
     * 产品信息管理
     */
    public static final String productInfoView;
    public static final String createProductView;
    public static final String modifyProductView;
    /**
     * 产品类别管理
     */
    public static final String productTypeInfoView;
    public static final String createProductTypeView;
    public static final String modifyProductTypeView;

    /**
     * 云工厂管理员面板
     */

    /**
     * 云工厂设备管理
     */
    public static final String myDeviceInfoView;
    public static final String addDeviceView;
    public static final String addDeviceCapacityView;

    /**
     * 云工厂订单管理
     */
    public static final String receiveOrderInfoView;
    public static final String produceOrderInfoView;
    public static final String createOrderCapacityView;

    /**
     * 经销商管理员面板
     */
    public static final String myOrderInfoView;
    public static final String createOrderView;
    public static final String modifyOrderView;


    //文件路径
    public static final String userFile;
    public static final String productTypeFile;
    public static final String productFile;
    public static final String deviceTypeFile;
    public static final String deviceFile;
    public static final String deviceCapacityFile;
    public static final String orderFile;
    public static final String orderCapacityFile;

    static {
        loginView = "../login/login.fxml";
        anotherLoginView = "../view/login/login.fxml";
        homeView = "../view/application/home.fxml";
        registrationView = "../view/component/registration.fxml";

        adminSideBarView = "../view/application/AdminNavbar.fxml";
        agentSideBarView = "../view/application/AgentNavbar.fxml";
        factorySideBarView = "../view/application/FactoryNavbar.fxml";

        startView = "../view/application/start.fxml";

        deviceInfoView = "../view/component/deviceInfo.fxml";
        createDeviceView = "../view/device/createDevice.fxml";
        modifyDeviceView = "../view/device/modifyDevice.fxml";

        deviceTypeInfoView = "../view/component/deviceTypeInfo.fxml";
        createDeviceTypeView = "../view/device/createDeviceType.fxml";
        modifyDeviceTypeView = "../view/device/modifyDeviceType.fxml";

        factoryInfoView = "../view/component/factoryInfo.fxml";

        productTypeInfoView = "../view/component/productTypeInfo.fxml";
        createProductTypeView = "../view/product/createProductType.fxml";
        modifyProductTypeView = "../view/product/modifyProductType.fxml";


        productInfoView = "../view/component/productInfo.fxml";
        createProductView = "../view/product/createProduct.fxml";
        modifyProductView = "../view/product/modifyProduct.fxml";

        userInfoView = "../view/component/userInfo.fxml";
        createUserView = "../view/user/createUser.fxml";
        modifyUserView = "../view/user/modifyUser.fxml";

        myDeviceInfoView = "../view/component/myDeviceInfo.fxml";
        addDeviceView = "../view/device/addDevice.fxml";
        addDeviceCapacityView = "../view/device/addDeviceCapacity.fxml";

        receiveOrderInfoView = "../view/component/receiveOrderInfo.fxml";
        produceOrderInfoView = "../view/component/produceOrderInfo.fxml";
        createOrderCapacityView = "../view/order/createOrderCapacity.fxml";

        myOrderInfoView = "../view/component/myOrderInfo.fxml";
        createOrderView = "../view/order/createOrder.fxml";
        modifyOrderView = "../view/order/modifyOrder.fxml";



        //文件路径
        userFile = "src//com//nicklaus//persistence//user.json";
        productTypeFile = "src//com//nicklaus//persistence//productType.json";
        productFile = "src//com//nicklaus//persistence//product.json";
        deviceTypeFile = "src//com//nicklaus//persistence//deviceType.json";
        deviceFile = "src//com//nicklaus//persistence//device.json";
        deviceCapacityFile = "src//com//nicklaus//persistence//deviceCapacity.json";
        orderFile = "src//com//nicklaus//persistence//order.json";
        orderCapacityFile = "src//com//nicklaus//persistence//orderCapacity.json";
    }
}
