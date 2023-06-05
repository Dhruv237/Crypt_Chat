package com.example.communitychatapp.ModelClass;
public class Users {
    String uid;
    String name;
    String email;
    String imageuri;
    String status;

    public Users() {
    }

    public Users(String uid, String name, String email, String imageuri,String status) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.imageuri = imageuri;
        this.status=status;
    }
    public String getStatus() {
        return status;
    }

    public String getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getImageuri() {
        return imageuri;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setImageuri(String imageuri) {
        this.imageuri = imageuri;
    }
    public void setStatus() {
        this.status=status;
    }
}
