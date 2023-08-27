package com.example.ass_app.DTO;

import java.io.Serializable;
import java.util.List;

public class ReadDTO implements Serializable {
    private List<String> data ;

    public ReadDTO() {
    }

    public ReadDTO(List<String> data) {
        this.data = data;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
