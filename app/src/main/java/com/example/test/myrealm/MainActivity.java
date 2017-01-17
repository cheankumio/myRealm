package com.example.test.myrealm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.annotations.RealmModule;

public class MainActivity extends AppCompatActivity {
    private Realm realm;
    private LinearLayout rootLayout=null;

    // 設定Module 欄位 ( 資料格式 )
    @RealmModule(classes = {Dog.class})
    public static class Module {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rootLayout = (LinearLayout)findViewById(R.id.layout);

        // Realm 配置
        realmSetting();

        // 插入資料
        insertdata();

        // 查詢資料
        new queryData(this,rootLayout,realm);
    }


    // 設置資料庫
    private void realmSetting(){
        // Realm 基本屬性配置
        RealmConfiguration config = new RealmConfiguration.Builder(this)
                .name("database_name.realm")
                .setModules(new Module())
                .deleteRealmIfMigrationNeeded()
                .build();
        // 實例Realm，並設置其基本屬性config
        realm = Realm.getInstance(config);
        // 啟動Realm 資料庫
        realm.beginTransaction();
        realm.clear(Dog.class);
    }

    // 插入資料
    private void insertdata(){
        for(int i =0;i<200;i++) {
            Dog d1 = realm.createObject(Dog.class);
            d1.setName("a"+i);
            d1.setAge(new Random().nextInt(10)+1);
            d1.setColor(new Random().nextInt(5)+1);
        }
        realm.commitTransaction();
    }


    public void searchbtn(View view){
        EditText editText1 = (EditText)findViewById(R.id.editText);
        EditText editText2 = (EditText)findViewById(R.id.editText2);
        String str1 = editText1.getText().toString();
        int value = Integer.parseInt(editText2.getText().toString());
        new queryData(this,rootLayout,realm,str1,value);
    }


    @Override
    protected void onDestroy() {
        realm.close();
        super.onDestroy();
    }
}
