package com.xinluhuang.englishlearn.module.main;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.xinluhuang.englishlearn.R;
import com.xinluhuang.englishlearn.application.MyApplication;
import com.xinluhuang.englishlearn.greendao.CETHelper;
import com.xinluhuang.englishlearn.module.find.FindWordActivity;
import com.xinluhuang.englishlearn.module.marked.MarkedWordActivity;
import com.xinluhuang.englishlearn.module.setting.SettingsActivity;
import com.xinluhuang.englishlearn.util.SPUtil;
import com.xinluhuang.englishlearn.util.StatusBarUtil;


import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.right)
    View right;
    @BindView(R.id.navigation)
    NavigationView navigationView;
    @BindView(R.id.main_activity)
    DrawerLayout drawerLayout;
    @BindView(R.id.toolbar_main)
    Toolbar toolbar;
    @BindView(R.id.rv_main)
    RecyclerView recyclerView;

    private long lasttime = 0;
    private RecyclerAdapter recyclerAdapter;
    private CETHelper cetHelper;
    int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDayNight((Boolean) SPUtil.get(SPUtil.isDay, true));
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        if ((Boolean) SPUtil.get(SPUtil.FIRST_START, true)) {
            showTapTarget();
        }
    }

    private void showTapTarget() {
        new TapTargetSequence(this)
                .targets(
                        TapTarget.forToolbarMenuItem(toolbar, R.id.item_find, "点击这里进行搜索")
                                .dimColor(android.R.color.black)
                                .outerCircleColor(R.color.colorPrimary)
                                .drawShadow(true)
                                .id(1),
                        TapTarget.forToolbarNavigationIcon(toolbar, "点击这里展开侧栏")
                                .dimColor(android.R.color.black)
                                .outerCircleColor(R.color.colorPrimary)
                                .drawShadow(true)
                                .id(2)
                ).listener(new TapTargetSequence.Listener() {
            @Override
            public void onSequenceFinish() {
                //设置第一次开启app为false
                SPUtil.put(SPUtil.FIRST_START, false);
            }

            @Override
            public void onSequenceStep(TapTarget lastTarget, boolean targetClicked) {

            }

            @Override
            public void onSequenceCanceled(TapTarget lastTarget) {
                //设置第一次开启app为false
                SPUtil.put(SPUtil.FIRST_START, false);
            }
        }).start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_find:
                startActivity(new Intent(this, FindWordActivity.class));
                break;
            default:
                break;
        }
        return true;
    }

    private void initView() {
        StatusBarUtil.translucentStatusBar(this, true);
        StatusBarUtil.fillStatusBar(this, R.id.fill_status, getResources().getColor(R.color.colorPrimary));

        initNavigationView();
        toolbar.inflateMenu(R.menu.toolbar);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.test1, R.string.test2);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                right.layout(navigationView.getRight(), 0, navigationView.getRight() + right.getWidth(), right.getHeight());
            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {
            }

            @Override
            public void onDrawerStateChanged(int newState) {
            }
        });

        cetHelper = CETHelper.getInstance();
        type = (Integer) SPUtil.get(SPUtil.DICT_TYPE, CETHelper.CET6);
        getSupportActionBar().setTitle(type == 0 ? "英语四级单词" : "英语六级单词");
        int custom = cetHelper.getWordSum(type) / 15;
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerAdapter = new RecyclerAdapter(this, custom, type);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
    }

    private void initNavigationView() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_newword:
                        startActivity(new Intent(MainActivity.this, MarkedWordActivity.class));
                        break;
                    case R.id.item_dict:
                        int order = 0;
                        if (type == CETHelper.CET6) {
                            type = CETHelper.CET4;
                            order = cetHelper.getWordSum(type) / 15;
                        } else if (type == CETHelper.CET4) {
                            type = CETHelper.CET6;
                            order = cetHelper.getWordSum(type) / 15;
                        }
                        SPUtil.put(SPUtil.DICT_TYPE, type);

                        recyclerAdapter = new RecyclerAdapter(MainActivity.this, order, type);
                        recyclerView.setAdapter(recyclerAdapter);
                        if (type == 0) {
                            getSupportActionBar().setTitle("英语四级单词");
                            Toast.makeText(MyApplication.getContext(), "已切换为英语四级单词", Toast.LENGTH_SHORT).show();
                        } else if (type == 1) {
                            getSupportActionBar().setTitle("英语六级单词");
                            Toast.makeText(MyApplication.getContext(), "已切换为英语六级单词", Toast.LENGTH_SHORT).show();
                        }

                        break;
                    case R.id.item_switch_night:
                        int mode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
                        if (mode == Configuration.UI_MODE_NIGHT_YES) {
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                            SPUtil.put(SPUtil.isDay, true);
                        } else {
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                            SPUtil.put(SPUtil.isDay, false);
                        }
                        getWindow().setWindowAnimations(R.style.WindowAnimationFadeInOut);
                        recreate();
                        break;
                    case R.id.item_setting:
                        startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                        break;
                    case R.id.item_share:
                        Intent shareIntent = new Intent()
                                .setAction(Intent.ACTION_SEND)
                                .setType("text/plain")
                                .putExtra(Intent.EXTRA_TEXT, getString(R.string.share_app_text) + getString(R.string.download_code_url));
                        startActivity(Intent.createChooser(shareIntent, "分享"));
                    default:
                        break;
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - lasttime > 3000) {
                Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
                lasttime = System.currentTimeMillis();
            } else {
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void setDayNight(boolean isDay) {
        if (isDay) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
    }


}