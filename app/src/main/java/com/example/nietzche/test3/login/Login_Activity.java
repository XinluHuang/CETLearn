package com.example.nietzche.test3.login;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.Toast;

import com.example.nietzche.test3.R;

import cn.bmob.v3.Bmob;

public class Login_Activity extends AppCompatActivity {
    private FragmentManager manager;
    private Fragment tempFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_login);
        Bmob.initialize(this, "c2f3318aa0aec9707f8b67546a39bf5a");
        manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.act_login, new Login_Fragment(), "login").commit();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((tempFragment = manager.findFragmentByTag("success")) != null) {
                manager.beginTransaction().remove(tempFragment).commit();
                manager.popBackStack();
            }
            return true;
            /*返回false是不吃掉，后面的监听也能得到这个事件，
            而返回true是吃掉，后面的监听就得不到这个事件了。*/
        }
        return super.onKeyDown(keyCode, event);
    }

    public void toastShow(String content) {
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
    }
}
