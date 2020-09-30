package com.nicklaus.view.element;

import com.jfoenix.controls.JFXCheckBox;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.io.Serializable;

public class MyCheckBox implements Serializable {

    JFXCheckBox checkbox=new JFXCheckBox();
    public ObservableValue<JFXCheckBox> getCheckBox()
    {
        return new  ObservableValue<JFXCheckBox>() {
            @Override
            public void addListener(ChangeListener<? super JFXCheckBox> listener) {

            }

            @Override
            public void removeListener(ChangeListener<? super JFXCheckBox> listener) {

            }

            @Override
            public JFXCheckBox getValue() {
                return checkbox;
            }

            @Override
            public void addListener(InvalidationListener listener) {

            }

            @Override
            public void removeListener(InvalidationListener listener) {

            }
        };
    }
    public Boolean isSelected()
    {
        return checkbox.isSelected();
    }
}
