package com.example.nietzche.test3.login;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.example.nietzche.test3.MainActivity;
import com.example.nietzche.test3.Person;
import com.example.nietzche.test3.R;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;


public class Login_Fragment extends Fragment {
    private EditText account;
    private EditText password;
    private CheckBox rememberAccount;
    private CheckBox rememberPassword;
    private SharedPreferences preferences;
    private Button btn_Login;
    private Button btn_Register;
    private Button btn_skip;

    public Login_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.login, container, false);
        account = view.findViewById(R.id.etAccount);
        password = view.findViewById(R.id.etPassword);
        rememberAccount = view.findViewById(R.id.checkRememberAccount);
        rememberPassword = view.findViewById(R.id.checkRememberPassword);
        btn_Register = view.findViewById(R.id.btnRegister);
        preferences = getActivity().getSharedPreferences("save", Context.MODE_PRIVATE);
        btn_skip = view.findViewById(R.id.btnSkip);
        btn_Login = view.findViewById(R.id.btnLogin);
        btn_Register = view.findViewById(R.id.btnRegister);


        //跳过登录
        btn_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MainActivity.class));
                getActivity().finish();
            }
        });

        //注册
        btn_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.act_login, new Register_Fragment()).addToBackStack(null).commit();
            }
        });

        //如果记住账号密码，则填入账号密码
        String t = "";
        if ((t = preferences.getString("account", null)) != null) {
            account.setText(t);
            rememberAccount.setChecked(true);
        }
        t = "";
        if ((t = preferences.getString("password", null)) != null) {
            password.setText(t);
            rememberPassword.setChecked(true);
        }

        //记住账号密码check监听，记住账号取消则连同记住密码取消，记住密码则连同记住账号
        rememberAccount.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked){
                    rememberPassword.setChecked(false);
                }
            }
        });
        rememberPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    rememberAccount.setChecked(true);
                }
            }
        });


        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog=new ProgressDialog(getActivity());
                progressDialog.setTitle("登录中");
                progressDialog.setCancelable(true);
                progressDialog.show();
                final Person person = new Person();
                person.setAccount(account.getText().toString());
                Log.d("my", person.getAccount());
                person.setPassword(password.getText().toString());
                Log.d("my", person.getPassword());
                SharedPreferences.Editor editor = preferences.edit();
                if (rememberAccount.isChecked()) {
                    editor.putString("account", person.getAccount());
                }else {
                    editor.remove("account");
                }
                if (rememberPassword.isChecked()) {
                    editor.putString("password", person.getPassword());
                } else {
                    editor.remove("password");
                }
                editor.commit();


                BmobQuery<Person> query = new BmobQuery<Person>();
                query.addWhereEqualTo("account", person.getAccount());
                query.addWhereEqualTo("password", person.getPassword());

                query.findObjects(new FindListener<Person>() {
                    @Override
                    public void done(List<Person> list, BmobException e) {
                        if (e == null && list.size() != 0) {
                            startActivity(new Intent(getActivity(), MainActivity.class));
                            progressDialog.dismiss();
                            getActivity().finish();
                        } else {
                            progressDialog.dismiss();
                            toastShow("账号不存在或密码错误");
                        }
                    }
                });
            }
        });


        return view;
    }

    private void toastShow(String content) {
        ((Login_Activity) getActivity()).toastShow(content);
    }
}
