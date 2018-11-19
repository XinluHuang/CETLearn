package com.xinluhuang.englishlearn.module.memorize;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.xinluhuang.englishlearn.R;
import com.xinluhuang.englishlearn.util.Constant;
import com.xinluhuang.englishlearn.util.LogUtil;
import com.xinluhuang.englishlearn.util.StatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WordMemorizingActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_memorizing);
        ButterKnife.bind(this);
        StatusBarUtil.translucentStatusBar(this,true);
        StatusBarUtil.fillStatusBar(this,R.id.fill_status,getResources().getColor(R.color.colorPrimary));
        int order=getIntent().getIntExtra(Constant.ORDER, 0);
        int type=getIntent().getIntExtra(Constant.TYPE, 0);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, WordListFragment.newInstance(order,type))
                .commit();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getIntent().getStringExtra(Constant.TITLE));
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
