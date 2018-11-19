package com.xinluhuang.englishlearn.module.memorize;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.xinluhuang.englishlearn.R;
import com.xinluhuang.englishlearn.dict.NormalDict;
import com.xinluhuang.englishlearn.greendao.entity.CETWord;
import com.xinluhuang.englishlearn.module.WordFragment;
import com.xinluhuang.englishlearn.util.LogUtil;
import com.xinluhuang.englishlearn.util.SPUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class MemorizingFragment extends Fragment implements View.OnClickListener, MemorizingView {
    @BindView(R.id.progressbar)
    ProgressBar progressBar;
    @BindView(R.id.word)
    TextView tv_word;

    private TextView[] textviews = new TextView[4];

    public static final String WORD_LIST = "wordlist";

    private MemorizingPresenter memorizingPresenter;

    public MemorizingFragment() {
        // Required empty public constructor
    }


    public static MemorizingFragment newInstance(List wordlist) {
        MemorizingFragment memorizingFragment = new MemorizingFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(WORD_LIST, wordlist.toArray(new CETWord[wordlist.size()]));
        memorizingFragment.setArguments(bundle);
        return memorizingFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        memorizingPresenter = new MemorizingPresenter(this);
        memorizingPresenter.init();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_memorizing, container, false);
        ButterKnife.bind(this, view);

        textviews[0] = view.findViewById(R.id.A);
        textviews[1] = view.findViewById(R.id.B);
        textviews[2] = view.findViewById(R.id.C);
        textviews[3] = view.findViewById(R.id.D);
        for (int i = 0; i < textviews.length; i++) {
            textviews[i].setOnClickListener(this);
        }



        return view;
    }

    @Override
    public void onResume() {
        LogUtil.d("refresh");
        memorizingPresenter.refresh();
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        int option = Integer.parseInt(v.getTag().toString());
        if (!memorizingPresenter.isRightOption(option)) {
            memorizingPresenter.showWordDetail();
        }else {
            memorizingPresenter.refresh();
        }
    }

    @Override
    public void onDestroy() {
        memorizingPresenter.destroy();
        super.onDestroy();
    }

    @Override
    public void refreshOption(String word, Spanned[] option) {
        tv_word.setText(word);
        for (int i = 0; i < textviews.length; i++) {
            textviews[i].setText(option[i]);
        }
    }

    @Override
    public CETWord[] getWordList() {
        CETWord[] cetWords = null;
        if (getArguments() != null) {
            cetWords = (CETWord[]) getArguments().getSerializable(WORD_LIST);
        }
        return cetWords;
    }

    @Override
    public void refreshProgress(int progress) {
        LogUtil.d("setprogress");
        progressBar.setProgress(progress);
    }

    @Override
    public void showWordDetail(String word) {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.act_memorize, WordFragment.newInstance(word))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void pass() {
        new PassFragment().show(getActivity().getSupportFragmentManager(), null);
    }

}
