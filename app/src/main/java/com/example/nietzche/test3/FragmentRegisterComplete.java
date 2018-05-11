package com.example.nietzche.test3;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;



/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentRegisterComplete extends Fragment {


    public FragmentRegisterComplete() {
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
                Fragment f=manager.findFragmentById(R.id.fra_login);

                manager.beginTransaction().remove(FragmentRegisterComplete.this).commit();
                manager.popBackStack();
            }
        });
        return view;
    }

}
