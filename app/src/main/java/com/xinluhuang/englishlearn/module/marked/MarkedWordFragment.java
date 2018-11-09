package com.xinluhuang.englishlearn.module.marked;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.xinluhuang.englishlearn.R;
import com.xinluhuang.englishlearn.dict.DictBean;
import com.xinluhuang.englishlearn.dict.NormalDict;
import com.xinluhuang.englishlearn.greendao.SearchWordHelper;
import com.xinluhuang.englishlearn.module.WordFragment;
import com.xinluhuang.englishlearn.module.adapter.NormalWordListAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MarkedWordFragment extends Fragment {
    @BindView(R.id.lv)
    ListView listView;
    private List<DictBean> dictBeanList;

    public MarkedWordFragment() {
        // Required empty public constructor
    }

    public static MarkedWordFragment newInstance(/*String word*/) {
        MarkedWordFragment markedWordFragment = new MarkedWordFragment();
        /*Bundle args = markedWordFragment.getArguments();
        if (args == null) {
            args = new Bundle();
        }
        args.putString("word", word);
        markedWordFragment.setArguments(args);*/
        return markedWordFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_marked_word, container, false);
        ButterKnife.bind(this, view);
        List<String> wordlist = SearchWordHelper.getInstance().getMarkedWordList();
        dictBeanList = NormalDict.getInstance().getWordForItem(wordlist);
        NormalWordListAdapter wordListAdapter = new NormalWordListAdapter(getActivity(), dictBeanList, R.layout.item_word);
        listView.setAdapter(wordListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView=view.findViewById(R.id.tv_word);
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, WordFragment.newInstance((String) textView.getText()))
                        .addToBackStack(null)
                        .commit();
            }
        });
        return view;
    }


}
