package com.example.nietzche.test3;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nietzche.test3.wordmemorize.WordMemorizing_Act;


/**
 * A simple {@link Fragment} subclass.
 */
public class WordMemorize_Fragment extends Fragment implements View.OnClickListener{

    public WordMemorize_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_word_memorize, container, false);
        view.findViewById(R.id.custom_1).setOnClickListener(this);
        view.findViewById(R.id.custom_2).setOnClickListener(this);
        view.findViewById(R.id.custom_3).setOnClickListener(this);
        view.findViewById(R.id.custom_4).setOnClickListener(this);
        view.findViewById(R.id.custom_5).setOnClickListener(this);
        view.findViewById(R.id.custom_6).setOnClickListener(this);
        view.findViewById(R.id.custom_7).setOnClickListener(this);
        view.findViewById(R.id.custom_8).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        int tag=Integer.parseInt(v.getTag().toString());
        Intent i=new Intent(getActivity(),WordMemorizing_Act.class);

        i.addFlags(tag);
        startActivity(i);
    }
}
