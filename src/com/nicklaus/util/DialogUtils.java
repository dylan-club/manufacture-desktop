package com.nicklaus.util;

import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.springframework.stereotype.Component;


public class DialogUtils {

    private String title; //对话框的标题
    private String message; //对话框的主体消息
    private Window window; //对话框窗口
    private JFXButton confirmBtn; //确认按钮
    private JFXButton cancelBtn; //取消按钮
    private Paint confirmBtnPaint = Paint.valueOf("#0099ff"); //确认按钮的默认颜色
    private Paint cancelBtnPaint = Paint.valueOf("#747474"); //确认按钮的默认颜色
    private JFXAlert<String> alert; //对话框

    private boolean flag;


    public DialogUtils(){}


    public boolean getFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    /**
     * 获取窗口
     */
    public DialogUtils addControl(Control control){
        this.window = control.getScene().getWindow();
        return this;
    }

    /**
     * 设置标题
     * @param title
     * @return
     */
    public DialogUtils setTitle(String title){
        this.title = title;
        return this;
    }

    /**
     * 设置消息
     * @param message
     * @return
     */
    public DialogUtils setMessage(String message){
        this.message = message;
        return this;
    }

    /**
     * 设置确定按钮
     * @param btnText
     * @param colorRGB
     * @return
     */
    public DialogUtils setConfirmBtn(String btnText, String colorRGB){
        if (!"".equals(colorRGB) && colorRGB != null){
            confirmBtnPaint = Paint.valueOf(colorRGB);
        }
        return setConfirmBtn(btnText);
    }

    public DialogUtils setConfirmBtn(String btnText){
        confirmBtn = new JFXButton(btnText);
        confirmBtn.setDefaultButton(true);
        confirmBtn.setTextFill(confirmBtnPaint);

        //设置单击事件，当按钮点击，隐藏对话框
        confirmBtn.setOnAction(clickEvent -> {
            alert.hideWithAnimation();
            //设置确认标志
            flag = true;
        });

        return this;
    }

    public DialogUtils setCancelBtn(String btnText, String colorRGB){
        if (!"".equals(colorRGB) && colorRGB != null){
            cancelBtnPaint = Paint.valueOf(colorRGB);
        }
        return setCancelBtn(btnText);
    }

    public DialogUtils setCancelBtn(String btnText) {
        if (btnText == null){
            cancelBtn = null;
        }else {
            cancelBtn = new JFXButton(btnText);
            cancelBtn.setDefaultButton(true);
            cancelBtn.setTextFill(cancelBtnPaint);

            //设置单击事件，当按钮点击，隐藏对话框
            cancelBtn.setOnAction(clickEvent -> {
                alert.hideWithAnimation();
                //设置取消标志
                flag = false;
            });
        }

        return this;
    }

    /**
     * 创建对话框
     * @return
     */
    public JFXAlert<String> create(){
        alert = new JFXAlert<String>((Stage) (window));
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setOverlayClose(false);

        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setHeading(new Label(title));

        layout.setBody(new VBox(new Label(this.message)));

        //如果设置了按钮就添加
        if (cancelBtn != null && confirmBtn != null){
            layout.setActions(cancelBtn,confirmBtn);
        }else{
            if (cancelBtn != null){
                layout.setActions(cancelBtn);
            }

            if (confirmBtn != null){
                layout.setActions(confirmBtn);
            }
        }

        //将组件添加到对话框内部
        alert.setContent(layout);

        //显示对话框
        alert.showAndWait();
        return alert;
    }



}
