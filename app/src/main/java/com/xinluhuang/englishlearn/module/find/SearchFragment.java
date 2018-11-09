package com.xinluhuang.englishlearn.module.find;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.xinluhuang.englishlearn.R;
import com.xinluhuang.englishlearn.application.MyApplication;
import com.xinluhuang.englishlearn.dict.DictBean;
import com.xinluhuang.englishlearn.dict.NormalDict;
import com.xinluhuang.englishlearn.greendao.DAOManager;
import com.xinluhuang.englishlearn.greendao.SearchWordHelper;
import com.xinluhuang.englishlearn.greendao.entity.SearchWord;
import com.xinluhuang.englishlearn.module.adapter.NormalWordListAdapter;
import com.xinluhuang.englishlearn.module.adapter.commomadapter.CommomViewHolder;
import com.xinluhuang.englishlearn.module.find.event.FindWordEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {
    @BindView(R.id.lv_history)
    ListView listView;
    @BindView(R.id.iv_delete)
    ImageView ivDelete;

    List<DictBean> dictBeanList;
    NormalWordListAdapter wordListAdapter;

    public SearchFragment() {
        // Required empty public constructor
    }
    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, view);

        initListView();
        ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //清空历史记录
                DAOManager.getInstance().getDaoSession(DAOManager.MARK_WORD).deleteAll(SearchWord.class);
                dictBeanList.clear();
                wordListAdapter.notifyDataSetChanged();
                Toast.makeText(MyApplication.getContext(),"清除成功",Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
    private void initListView(){
        List<String> list=SearchWordHelper.getInstance().getSearchWordList();
        dictBeanList= NormalDict.getInstance().getWordForItem(list);

        wordListAdapter=new NormalWordListAdapter(getActivity(),dictBeanList,R.layout.item_word);
        listView.setAdapter(wordListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView=CommomViewHolder.get(view,R.id.tv_word);
                String word=(String) textView.getText();
                EventBus.getDefault().post(new FindWordEvent(word));
            }
        });
    }

}
