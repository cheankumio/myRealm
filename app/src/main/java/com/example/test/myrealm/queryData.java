package com.example.test.myrealm;

import android.content.Context;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by c1103304 on 2017/1/17.
        =======查詢資料=======
        * 查詢 age >= 5
        * query.greaterThanOrEqualTo("age",5);
        * 查詢 age <= 5
        * query.lessThanOrEqualTo("age",5);
        * 查詢 age > 5
        * query.greaterThan("age",5);
        * 查詢 age < 5
        * query.lessThan("age",5);
        * 查詢 age 3~7 之間的資料
        * query.between("age",3,7);
        * 查詢 name 以a 開頭的
        * query.beginsWith("name", "a");
        * 查詢 name 以a 結尾的
        * query.endsWith("name", "a");
        * 查詢 name 內容包含a 的
        * query.contains("name", "a");
 */

public class queryData {
    queryData(Context cx, LinearLayout ly, Realm realm) {
        // 建立查詢
        RealmQuery<Dog> query = realm.where(Dog.class);
        printdata(cx,ly,realm,query);
    }
    queryData(Context cx, LinearLayout ly, Realm realm,String attr,int num) {
        // 建立查詢
        RealmQuery<Dog> query = realm.where(Dog.class);
        // 查找color 編號為2 者
        query.equalTo(attr, num);
        // 印出資料
        printdata(cx,ly,realm,query);
    }

    // 印出資料
    private void printdata(Context cx, LinearLayout ly, Realm realm,RealmQuery<Dog> query){
        ly.removeAllViews();
        RealmResults<Dog> result = query.findAll();
        for (Dog d : result) {
            showText(cx,ly,"Dog ID: " + d.getName() + "\tAge: " + d.getAge() + "\tDog Color: " + getColor_str(d.getColor()));
        }
    }


    // 回傳 Dog color str
    private String getColor_str(int color){
        String str="";
        switch (color){
            case 1:
                str="black";
                break;
            case 2:
                str="yellow";
                break;
            case 3:
                str="white";
                break;
            case 4:
                str="gray";
                break;
            case 5:
                str="brown";
                break;
        }
        return str;
    }

    public void showText(Context cx, LinearLayout ly, String txt) {
        Log.d("MYLOG", txt);
        TextView tv = new TextView(cx);
        tv.setTextSize(17f);
        tv.setText(txt);
        ly.addView(tv);
    }
}
