package com.example.nietzche.test3.wordmemorize;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.nietzche.test3.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_WordList extends Fragment implements View.OnClickListener,AdapterView.OnItemClickListener{

    private ArrayList<String> wordlist;
    private ListView listView;
    public Fragment_WordList() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wordlist, container, false);
        wordlist=(ArrayList<String>)getArguments().getSerializable("wordlist");
        listView=view.findViewById(R.id.word_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, wordlist);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        view.findViewById(R.id.memorize_begin).setOnClickListener(this);
        return view;
    }


    @Override
    public void onClick(View v) {
        Fragment_Memorizing fragment_memorizing=new Fragment_Memorizing();
        fragment_memorizing.setArguments(getArguments());
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.act_memorize,fragment_memorizing).commit();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String word=wordlist.get(position);
        Fragment_wordDetail detail=new Fragment_wordDetail();
        Bundle b=new Bundle();
        b.putString("word",word);
        detail.setArguments(b);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.act_memorize,detail).addToBackStack(null).commit();
    }
}
