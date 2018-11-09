package com.xinluhuang.englishlearn.module.find;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.xinluhuang.englishlearn.R;
import com.xinluhuang.englishlearn.application.MyApplication;
import com.xinluhuang.englishlearn.dict.NormalDict;
import com.xinluhuang.englishlearn.greendao.SearchWordHelper;
import com.xinluhuang.englishlearn.module.WordFragment;
import com.xinluhuang.englishlearn.module.find.event.FindWordEvent;
import com.xinluhuang.englishlearn.module.find.event.ShowWordEvent;
import com.xinluhuang.englishlearn.util.StatusBarUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FindWordActivity extends AppCompatActivity {
    @BindView(R.id.container)
    FrameLayout frameLayout;
    @BindView(R.id.et_search)
    EditText editText;
    @BindView(R.id.toolbar)
    Toolbar toolbar;


    private SearchFragment searchFragment = new SearchFragment();
    private WordFragment wordFragment = new WordFragment();

    @Override
    protected void onStart() {
        EventBus.getDefault().register(this);
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_word);
        ButterKnife.bind(this);
        StatusBarUtil.translucentStatusBar(this, true);
        StatusBarUtil.fillStatusBar(this, R.id.fill_status, getResources().getColor(R.color.colorPrimary));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container,wordFragment)
                .hide(wordFragment)
                .add(R.id.container, searchFragment)
                .commit();

        //回车查找
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    findword(editText.getText().toString().trim());
                    return true;
                }
                return false;
            }
        });


    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void findword(FindWordEvent findWordEvent) {
        findword(findWordEvent.word);
    }

    public void findword(String word) {
        if (NormalDict.getInstance().isExist(word)) {
            SearchWordHelper.getInstance().insertSearchWord(word);
            getSupportFragmentManager().beginTransaction().hide(searchFragment).commit();
            getSupportFragmentManager().beginTransaction().show(wordFragment).commit();
            EventBus.getDefault().post(new ShowWordEvent(word));
        } else {
            Toast.makeText(MyApplication.getContext(), "查无此词", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.item_find:
                findword(editText.getText().toString().trim());
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
}
