package com.example.nietzche.test3;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import cn.bmob.v3.Bmob;

public class Act_Login extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_login);
        Bmob.initialize(this,"c2f3318aa0aec9707f8b67546a39bf5a");

        getSupportFragmentManager().beginTransaction().replace(R.id.MainActivity,new Fra_Login(),"login").commit();

    }
    public void toastShow(String content){
        Toast.makeText(this,content,Toast.LENGTH_SHORT).show();
    }
}
