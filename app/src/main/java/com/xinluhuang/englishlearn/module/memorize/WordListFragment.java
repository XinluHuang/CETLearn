package com.xinluhuang.englishlearn.module.memorize;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.xinluhuang.englishlearn.R;
import com.xinluhuang.englishlearn.greendao.CETHelper;
import com.xinluhuang.englishlearn.greendao.entity.CET6Word;
import com.xinluhuang.englishlearn.greendao.entity.CETWord;
import com.xinluhuang.englishlearn.module.WordFragment;
import com.xinluhuang.englishlearn.module.adapter.CETWordListAdapter;
import com.xinluhuang.englishlearn.module.adapter.commomadapter.CommomViewHolder;
import com.xinluhuang.englishlearn.util.Constant;
import com.xinluhuang.englishlearn.util.LogUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class WordListFragment extends Fragment implements View.OnClickListener,AdapterView.OnItemClickListener{
    @BindView(R.id.word_list)
    ListView listView;
    @BindView(R.id.memorize_begin)
    Button btnStart;

    private List wordlist;
    private CETWordListAdapter wordListAdapter;
    private CETHelper cetHelper;


    public WordListFragment() {
        // Required empty public constructor
    }
    public static WordListFragment newInstance(int order,int type){
        WordListFragment wordListFragment=new WordListFragment();
        Bundle args=wordListFragment.getArguments();
        if(args==null){
            args=new Bundle();
        }
        args.putInt(Constant.TYPE,type);
        args.putInt(Constant.ORDER,order);
        wordListFragment.setArguments(args);

        return wordListFragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wordlist, container, false);
        ButterKnife.bind(this,view);
        initView();
        return view;
    }

    private void initView(){
        int order=0;
        int type=0;
        if(getArguments()!=null) {
            order = getArguments().getInt(Constant.ORDER);
            type=getArguments().getInt(Constant.TYPE);
        }


        cetHelper = CETHelper.getInstance();
        if(type==CETHelper.CET6) {
            wordlist = cetHelper.getCET6CustomList(order);
        }else if(type==CETHelper.CET4){
            wordlist = cetHelper.getCET4CustomList(order);
        }
        wordListAdapter=new CETWordListAdapter(getActivity(),wordlist,R.layout.item_word);
        listView.setAdapter(wordListAdapter);
        listView.setOnItemClickListener(this);

        btnStart.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.act_memorize,MemorizingFragment.newInstance(wordlist))
                .commit();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String word=(String) ((TextView)CommomViewHolder.get(view,R.id.tv_word)).getText();
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.act_memorize, WordFragment.newInstance(word))
                .addToBackStack(null)
                .commit();
    }
}
