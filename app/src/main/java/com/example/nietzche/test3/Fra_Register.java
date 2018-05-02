package com.example.nietzche.test3;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;


public class Fra_Register extends Fragment {
private EditText account;
private EditText password;
private EditText againpassword;
private Button confirm;
    public Fra_Register() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_register, container, false);
        account=view.findViewById(R.id.layout_reg_account).findViewById(R.id.reg_account);
        password=view.findViewById(R.id.layout_reg_password).findViewById(R.id.reg_password);
        againpassword=view.findViewById(R.id.layout_again_password).findViewById(R.id.reg_again_password);
        confirm=view.findViewById(R.id.btn_register_confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(password.getText().toString().equals(againpassword.getText().toString())) {
                    Person p = new Person();
                    p.setAccount(account.getText().toString());
                    p.setPassword(password.getText().toString());
                    p.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            toastShow("注册成功");
                        }
                    });
                }else {
                    toastShow("两次密码输入不正确");
                }
            }
        });
        return view;
    }

    private void toastShow(String content){
        ((Act_Login)getActivity()).toastShow(content);
    }
}
