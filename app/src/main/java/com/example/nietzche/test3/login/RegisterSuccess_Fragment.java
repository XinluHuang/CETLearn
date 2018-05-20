package com.example.nietzche.test3.login;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.nietzche.test3.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterSuccess_Fragment extends Fragment {


    public RegisterSuccess_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_register_complete, container, false);
        Button btn=view.findViewById(R.id.btn_register_back);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager=getActivity().getSupportFragmentManager();
                Fragment f=manager.findFragmentById(R.id.login_fragment);

                manager.beginTransaction().remove(RegisterSuccess_Fragment.this).commit();
                manager.popBackStack();
            }
        });
        return view;
    }

}
