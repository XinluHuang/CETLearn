package com.example.nietzche.test3.wordmemorize;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.nietzche.test3.dict.CollinsDict;
import com.example.nietzche.test3.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_wordDetail extends Fragment implements View.OnClickListener{
private TextView worddetial;
    private TextView detail;
    private Button btn_continue;
    public Fragment_wordDetail() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_word_detail, container, false);
        String word=getArguments().getString("word");
        CollinsDict dict=new CollinsDict();
        dict.find(word);

        detail=view.findViewById(R.id.word_scroll).findViewById(R.id.textview_worddetail);

        detail.setText(Html.fromHtml(dict.outInHtml()));
        btn_continue=view.findViewById(R.id.btn_continue);
        btn_continue.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        getActivity().onBackPressed();
    }
}
