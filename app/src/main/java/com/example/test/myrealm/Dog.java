package com.example.test.myrealm;

import io.realm.RealmObject;

/**
 * Created by c1103304 on 2017/1/17.
 */

public class Dog extends RealmObject {
    private String name;
    private int age;
    private int color;

    public void setColor(int color) {
        this.color = color;
    }

    public int getColor() {

        return color;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {

        return name;
    }

    public int getAge() {
        return age;
    }
}