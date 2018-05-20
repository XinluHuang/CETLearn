package com.example.nietzche.test3.login;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.nietzche.test3.Person;
import com.example.nietzche.test3.R;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;


public class Register_Fragment extends Fragment {
private EditText etAccount;
private EditText etPassword;
private EditText etAgainPassword;
private Button btnConfirm;
    public Register_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_register, container, false);
        etAccount =view.findViewById(R.id.et_account);
        etPassword =view.findViewById(R.id.et_password);
        etAgainPassword =view.findViewById(R.id.et_again_password);
        btnConfirm =view.findViewById(R.id.btn_register_confirm);



        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etAccount.getText().toString().equals("") || etAccount.getText().toString()==null){
                    toastShow("用户名不能为空");
                    return;
                }
                if(etPassword.getText().toString().equals("") || etPassword.getText().toString()==null){
                    toastShow("密码不能为空");
                    return;
                }
                if(etPassword.getText().toString().equals(etAgainPassword.getText().toString())) {
                    Person p = new Person();
                    p.setAccount(etAccount.getText().toString());
                    p.setPassword(etPassword.getText().toString());
                    p.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.act_login,new RegisterSuccess_Fragment(),"success").commit();
                        }
                    });

                }else {
                    toastShow("两次密码输入不相同");
                }
            }
        });
        return view;
    }

    private void toastShow(String content){
        ((Login_Activity)getActivity()).toastShow(content);
    }
}
