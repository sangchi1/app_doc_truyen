package com.example.ass_app.DTO;


import java.io.Serializable;

public class UserDTO implements Serializable {

    private String _id;

    private String userName;

    private String email;

    private String fullname ;

    public UserDTO() {
    }

    public UserDTO(String _id, String userName, String email, String fullname) {
        this._id = _id;
        this.userName = userName;
        this.email = email;
        this.fullname = fullname;
    }


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }


}
