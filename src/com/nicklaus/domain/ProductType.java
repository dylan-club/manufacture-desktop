package com.nicklaus.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nicklaus.view.element.MyCheckBox;

public class ProductType {
    private int id;
    private String name;
    @JsonIgnore
    public MyCheckBox myCheckBox = new MyCheckBox();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ProductType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
