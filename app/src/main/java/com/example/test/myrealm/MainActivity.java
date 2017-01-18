package com.example.test.myrealm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.annotations.RealmModule;
import io.realm.internal.IOException;

public class MainActivity extends AppCompatActivity {
    private Realm realm;
    private LinearLayout rootLayout=null;
    Placemark placemark_adapter;

    // 設定Module 欄位 ( 資料格式 )
    @RealmModule(classes = {Placemark.class})
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
        //insertdata();

        // 查詢資料
        new queryData(this,rootLayout,realm);
    }


//    private List<Placemark> loaddata() throws java.io.IOException {
//        loadJsonFromStream();
////        loadJsonFromJsonObject();
////        loadJsonFromString();
//        return realm.where(Placemark.class).findAll();
//    }

    // 讀取Json 資料進realm
    private void loadJsonFromStream() throws java.io.IOException {
        InputStream stream = getAssets().open("geouseraccount.json");
        // 開啟realm 資料庫
        realm.beginTransaction();
        realm.clear(Placemark.class);
        try {
            // 資料和欄位整個帶入realm
            realm.createAllFromJson(Placemark.class, stream);
            // 確定插入資料
            realm.commitTransaction();
        } catch (IOException e) {
            // Remember to cancel the transaction if anything goes wrong.
            realm.cancelTransaction();
        } finally {
            if (stream != null) {
                stream.close();
            }
        }
    }

//    private void loadJsonFromJsonObject() {
//        Map<String, String> user = new HashMap<String, String>();
//        user.put("name", "København");
//        user.put("votes", "9");
//        final JSONObject json = new JSONObject(user);
//
//        realm.executeTransaction(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//                realm.createObjectFromJson(Placemark.class, json);
//            }
//        });
//    }
//
//
    public void insertdata(View view) {
        EditText ed1 = (EditText)findViewById(R.id.editText3);
        EditText ed2 = (EditText)findViewById(R.id.editText4);
        EditText ed3 = (EditText)findViewById(R.id.editText5);
        String str1 = fs("Name")+":"+fs(ed1.getText().toString())+",";
        String str2 = fs("Account")+":"+fs(ed2.getText().toString())+",";
        String str3 = fs("Password")+":"+fs(ed3.getText().toString());
        final String json = "{"+str1+str2+str3+"}";
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.createObjectFromJson(Placemark.class, json);
                Log.d("MYLOG","JSON: "+json);
            }
        });
    }

    // format text to "text"
    private String fs(String str){
        return "\""+str+"\"";
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
        //realm.beginTransaction();

        try {
            loadJsonFromStream();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    // 插入資料
//    private void insertdata(){
//        for(int i =0;i<200;i++) {
//            Placemark d1 = realm.createObject(Placemark.class);
//            d1.setName("a"+i);
//            d1.setAccount("");
//            d1.setPassword("");
//        }
//        realm.commitTransaction();
//    }


    public void searchbtn(View view){
        EditText editText1 = (EditText)findViewById(R.id.editText);
        EditText editText2 = (EditText)findViewById(R.id.editText2);
        String str1 = editText1.getText().toString();
        //int value = Integer.parseInt(editText2.getText().toString());
        String str2 = editText2.getText().toString();
        new queryData(this,rootLayout,realm,str1,str2);
    }


    @Override
    protected void onDestroy() {
        realm.close();
        super.onDestroy();
    }
}
