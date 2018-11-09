package com.xinluhuang.englishlearn.module;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.xinluhuang.englishlearn.R;
import com.xinluhuang.englishlearn.application.MyApplication;
import com.xinluhuang.englishlearn.dict.Collins;
import com.xinluhuang.englishlearn.dict.DictBean;
import com.xinluhuang.englishlearn.dict.NormalDict;
import com.xinluhuang.englishlearn.greendao.SearchWordHelper;
import com.xinluhuang.englishlearn.module.find.event.ShowWordEvent;
import com.xinluhuang.englishlearn.util.LogUtil;
import com.xinluhuang.englishlearn.util.SPUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class WordFragment extends Fragment {
    @BindView(R.id.tv_collins)
    TextView tvCollins;
    @BindView(R.id.tv_head)
    TextView tvHead;
    @BindView(R.id.iv_add)
    ImageView imageView;
    public static final String WORD = "word";

    private String wordInit;
    private SearchWordHelper searchWordHelper = SearchWordHelper.getInstance();

    @Override
    public void onStart() {
        EventBus.getDefault().register(this);
        super.onStart();
    }

    public WordFragment() {
    }

    public static WordFragment newInstance(String word) {
        WordFragment fragment = new WordFragment();
        Bundle bundle = new Bundle();
        bundle.putString(WORD, word);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_word, container, false);
        ButterKnife.bind(this, view);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (searchWordHelper.isMarkedWordExist(wordInit)) {
                    searchWordHelper.deleteMarkedWord(wordInit);
                    imageView.setImageResource(R.drawable.ic_add);
                    Toast.makeText(MyApplication.getContext(), "删除生词成功", Toast.LENGTH_SHORT).show();
                } else {
                    searchWordHelper.insertMarkedWord(wordInit);
                    imageView.setImageResource(R.drawable.ic_delete);
                    Toast.makeText(MyApplication.getContext(), "添加生词成功", Toast.LENGTH_SHORT).show();
                }
            }
        });
        if (getArguments() != null) {
            String word = getArguments().getString(WORD);
            if (word != null && !"".equals(word)) {
                showWord(word);
            }
        }


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showWord(ShowWordEvent showWordEvent) {
        showWord(showWordEvent.word);
    }

    public void showWord(String word) {
        this.wordInit = word;
        if (NormalDict.getInstance().isExist(wordInit)) {
            searchWordHelper.insertSearchWord(wordInit);
            DictBean dictBean = NormalDict.getInstance().getDictBean(wordInit);
            tvHead.setText(Html.fromHtml(dictBean.getHeadForHtml()));
            tvCollins.setText(Html.fromHtml(Collins.findWordInHtml(wordInit)));
            if (searchWordHelper.isMarkedWordExist(wordInit)) {
                imageView.setImageResource(R.drawable.ic_delete);
            } else {
                imageView.setImageResource(R.drawable.ic_add);
            }
            if ((Boolean) SPUtil.get(SPUtil.FIRST_FIND_WORD, true)) {
                showTapTarget();
            }
        }
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    private void showTapTarget() {
        new TapTargetSequence(getActivity())
                .targets(
                        TapTarget.forView(imageView, "点击添加或删除生词")
                                .dimColor(android.R.color.black)
                                .outerCircleColor(R.color.colorPrimary)
                                .drawShadow(true)
                                .id(2)
                ).listener(new TapTargetSequence.Listener() {
            @Override
            public void onSequenceFinish() {
                SPUtil.put(SPUtil.FIRST_FIND_WORD, false);
            }

            @Override
            public void onSequenceStep(TapTarget lastTarget, boolean targetClicked) {

            }

            @Override
            public void onSequenceCanceled(TapTarget lastTarget) {
                SPUtil.put(SPUtil.FIRST_FIND_WORD, false);
            }
        }).start();
    }
}
