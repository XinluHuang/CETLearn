package com.example.nietzche.test3;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, ViewPager.OnPageChangeListener {
    private long lasttime = 0;
    private RadioGroup radioGroup;
    private List<Fragment> fragment_list;
    private ViewPager viewpager;
    private FragmentPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        radioGroup = findViewById(R.id.radioline).findViewById(R.id.radiogroup);
        viewpager = findViewById(R.id.viewpager);
        radioGroup.setOnCheckedChangeListener(this);
        fragment_list = new ArrayList<Fragment>();
        fragment_list.add(new Findword_Fragment());
        fragment_list.add(new Grammar_Fragment());
        fragment_list.add(new WordMemorizeEntry_Fragment());
        fragment_list.add(new Listen_Fragment());

        adapter = new FragmentPagerAdapter(this.getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragment_list.get(position);
            }

            @Override
            public int getCount() {
                return fragment_list.size();
            }
        };
        viewpager.setAdapter(adapter);
        viewpager.setOnPageChangeListener(this);
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

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.btn_findword:
                viewpager.setCurrentItem(0);
                break;
            case R.id.btn_grammar:
                viewpager.setCurrentItem(1);
                break;
            case R.id.btn_wordmemorize:
                viewpager.setCurrentItem(2);
                break;
            case R.id.btn_listen:
                viewpager.setCurrentItem(3);
                break;
            default:
                break;
        }
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                radioGroup.check(R.id.btn_findword);
                break;
            case 1:
                radioGroup.check(R.id.btn_grammar);
                break;
            case 2:
                radioGroup.check(R.id.btn_wordmemorize);
                break;
            case 3:
                radioGroup.check(R.id.btn_listen);
                break;
            default:
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
