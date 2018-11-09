package com.xinluhuang.englishlearn.module.marked;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;

import com.xinluhuang.englishlearn.R;
import com.xinluhuang.englishlearn.dict.DictBean;
import com.xinluhuang.englishlearn.dict.NormalDict;
import com.xinluhuang.englishlearn.greendao.SearchWordHelper;
import com.xinluhuang.englishlearn.module.adapter.NormalWordListAdapter;
import com.xinluhuang.englishlearn.util.StatusBarUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MarkedWordActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    List<DictBean> dictBeanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_markedword);
        ButterKnife.bind(this);
        StatusBarUtil.translucentStatusBar(this, true);
        StatusBarUtil.fillStatusBar(this, R.id.fill_status, getResources().getColor(R.color.colorPrimary));

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("生词本");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        getSupportFragmentManager().beginTransaction().replace(R.id.container, MarkedWordFragment.newInstance()).commit();
        initRecyclerView();
    }

    private void initRecyclerView() {
        List<String> wordlist = SearchWordHelper.getInstance().getMarkedWordList();
        dictBeanList = NormalDict.getInstance().getWordForItem(wordlist);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                break;
        }
        return true;
    }
}
