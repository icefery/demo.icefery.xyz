package xyz.icefery.demo;

import java.io.Serializable;

public class Student implements Serializable {

    public String code;
    public String name;
    public String pass;
    public String mail;

    public Student(String code, String name, String pass, String mail) {
        this.code = code;
        this.name = name;
        this.pass = pass;
        this.mail = mail;
    }
}
