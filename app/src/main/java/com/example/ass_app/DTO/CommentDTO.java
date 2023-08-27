package com.example.ass_app.DTO;

public class CommentDTO {
   private String _id ;
    private  UserModel id_user ;
    private  String noidung ;
    private  String ngayGio ;


    public CommentDTO() {
    }

    public CommentDTO(String _id, UserModel id_user, String noidung, String ngayGio) {
        this._id = _id;
        this.id_user = id_user;
        this.noidung = noidung;
        this.ngayGio = ngayGio;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public UserModel getId_user() {
        return id_user;
    }

    public void setId_user(UserModel id_user) {
        this.id_user = id_user;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public String getNgayGio() {
        return ngayGio;
    }

    public void setNgayGio(String ngayGio) {
        this.ngayGio = ngayGio;
    }

    public class UserModel{
        private String _id;
        private String username;

        public UserModel() {
        }

        public UserModel(String _id, String username) {
            this._id = _id;
            this.username = username;
        }

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}
