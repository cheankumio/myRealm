package com.example.test.myrealm;

import io.realm.RealmObject;

/**
 * Created by c1103304 on 2017/1/18.
 */

public class Placemark extends RealmObject {

    private String Name;
    private String Account;
    private String Password;

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getAccount() {
        return Account;
    }

    public void setAccount(String Account) {
        this.Account = Account;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Pass) {
        this.Password = Pass;
    }
}