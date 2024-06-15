package com.app.sigarayibirak;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

//Bu sınıfı yaratma amacımız kullanıcının verilerini kayıt olurken oluşturduğumuz database'e eklemektir.
public class User {

    private String name, eMail, age, password, date;
    private int dayCount;


    User(){

    }

    public User(String name, String eMail, String age, String password) {
        this.name = name;
        this.eMail = eMail;
        this.age = age;
        this.password = password;
        this.dayCount = 1;
        setDate();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getDayCount() {
        return dayCount;
    }

    public void setDayCount(int dayCount) {
        this.dayCount = dayCount;
    }
    public String getDate(){
        return date;
    }
    public void setDate(){
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        this.date = dateFormat.format(date);
    }
}
