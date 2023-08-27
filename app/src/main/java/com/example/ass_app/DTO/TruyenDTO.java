package com.example.ass_app.DTO;

import java.io.Serializable;
import java.util.List;

public class TruyenDTO implements Serializable {
    private String _id;
    private String name;
    private String mota;
    private String tacgia;
    private String year;
    private String img;
    private List<String> listImgs;

    public TruyenDTO() {
    }

    public TruyenDTO(String _id, String name, String mota, String tacgia, String year, String img, List<String> listImgs) {
        this._id = _id;
        this.name = name;
        this.mota = mota;
        this.tacgia = tacgia;
        this.year = year;
        this.img = img;
        this.listImgs = listImgs;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public String getTacgia() {
        return tacgia;
    }

    public void setTacgia(String tacgia) {
        this.tacgia = tacgia;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public List<String> getListImgs() {
        return listImgs;
    }

    public void setListImgs(List<String> listImgs) {
        this.listImgs = listImgs;
    }
}
