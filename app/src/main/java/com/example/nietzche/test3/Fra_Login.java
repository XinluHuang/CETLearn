package com.example.nietzche.test3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;


public class Fra_Login extends Fragment {
    private EditText account;
    private EditText password;
    private CheckBox rem_account;
    private CheckBox rem_password;
    private SharedPreferences pre;
    private Button login;
    private Button register;
    private Button skip;

    public Fra_Login() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        account = view.findViewById(R.id.layout_account).findViewById(R.id.edit_account);
        password = view.findViewById(R.id.layout_password).findViewById(R.id.edit_password);
        rem_account = view.findViewById(R.id.rem_account);
        pre = getActivity().getSharedPreferences("save", Context.MODE_PRIVATE);
        skip = view.findViewById(R.id.btn_skip);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),MainActivity.class));
                getActivity().finish();
            }
        });
        rem_password = view.findViewById(R.id.rem_password);
        String t = "";
        if ((t = pre.getString("account", null)) != null) {
            account.setText(t);
            rem_account.setChecked(true);
        }
        t = "";
        if ((t = pre.getString("password", null)) != null) {
            password.setText(t);
            rem_password.setChecked(true);
        }
        login = view.findViewById(R.id.btn_login);
        register = view.findViewById(R.id.btn_register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.MainActivity, new Fra_Register()).addToBackStack(null).commit();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Person p = new Person();
                p.setAccount(account.getText().toString());
                Log.d("my", p.getAccount());
                p.setPassword(password.getText().toString());
                Log.d("my", p.getPassword());
                SharedPreferences pre = getActivity().getSharedPreferences("save", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pre.edit();
                if (rem_account.isChecked()) {
                    editor.putString("account", p.getAccount());
                } else {
                    editor.remove("account");
                }
                if (rem_password.isChecked()) {
                    editor.putString("password", p.getPassword());
                } else {
                    editor.remove("password");
                }
                editor.commit();
                BmobQuery<Person> query = new BmobQuery<Person>();
                query.addWhereEqualTo("account", p.getAccount());
                query.addWhereEqualTo("password", p.getPassword());

                query.findObjects(new FindListener<Person>() {
                    @Override
                    public void done(List<Person> list, BmobException e) {
                        if (e == null && list.size() != 0) {
                            startActivity(new Intent(getActivity(),MainActivity.class));
                            getActivity().finish();
                        } else {
                            toastShow("Login failed");
                        }


                    }
                });
            }
        });
        return view;
    }

    private void toastShow(String content) {
        ((Act_Login) getActivity()).toastShow(content);
    }
}
