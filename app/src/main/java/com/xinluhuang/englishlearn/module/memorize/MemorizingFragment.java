package com.xinluhuang.englishlearn.module.memorize;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.xinluhuang.englishlearn.R;
import com.xinluhuang.englishlearn.dict.NormalDict;
import com.xinluhuang.englishlearn.greendao.entity.CET6Word;
import com.xinluhuang.englishlearn.greendao.entity.CETWord;
import com.xinluhuang.englishlearn.module.WordFragment;
import com.xinluhuang.englishlearn.util.LogUtil;
import com.xinluhuang.englishlearn.util.SPUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class MemorizingFragment extends Fragment implements View.OnClickListener {
    @BindView(R.id.progressbar)
    ProgressBar progressBar;
    @BindView(R.id.word)
    TextView tv_word;
    private List<CETWord> wordlist;
    private int[] wordseq;
    //当前进度
    private int count = 0;
    //当前正确选项的位置
    private int right;
    private boolean isDay = true;

    private TextView[] textviews = new TextView[4];
    private NormalDict dict = NormalDict.getInstance();

    public static final String WORD_SEQ = "wordseq";
    public static final String WORD_LIST = "wordlist";
    public static final String WORD = "word";

    public MemorizingFragment() {
        // Required empty public constructor
    }

    public static MemorizingFragment newInstance(List wordlist) {
        MemorizingFragment memorizingFragment = new MemorizingFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("wordlist", wordlist.toArray(new CETWord[wordlist.size()]));
        memorizingFragment.setArguments(bundle);
        return memorizingFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_memorizing, container, false);
        ButterKnife.bind(this, view);

        if (getArguments() != null) {
            CETWord[] cetWords = (CETWord[]) getArguments().getSerializable(WORD_LIST);
            wordlist = new ArrayList<>(Arrays.asList(cetWords));
            if ((wordseq = (int[]) getArguments().getSerializable(WORD_SEQ)) == null) {
                wordseq = getRandomArray(wordlist.size());//后退会刷新,需要保存状态
            }
        }
        progressBar.setProgress(count);

        textviews[0] = view.findViewById(R.id.A);
        textviews[1] = view.findViewById(R.id.B);
        textviews[2] = view.findViewById(R.id.C);
        textviews[3] = view.findViewById(R.id.D);
        for (int i = 0; i < textviews.length; i++) {
            textviews[i].setOnClickListener(this);
        }

        isDay = (Boolean) SPUtil.get(SPUtil.isDay, true);


        showOption();

        return view;
    }

    private int[] getRandomArray(int size) {
        Random r = new Random();
        int[] seq = new int[size];
        //先按顺序赋值，再打乱顺序
        for (int i = 0; i < seq.length; i++) {
            seq[i] = i;
        }
        //类似从左到右的选择排序法，t为交换数值的中间变量
        for (int i = seq.length - 1, t = 0, position = 0; i > 0; i--) {
            position = r.nextInt(i);//在0~i范围内取值
            t = seq[position];
            seq[position] = seq[i];
            seq[i] = t;
        }
        return seq;
    }

    //单词序列号，传入参数(real)为正确单词的序列(0~14)，返回四个单词序列号
    private int[] getOption(int real) {
        Random r = new Random();
        int[] option = new int[4];
        int pos = r.nextInt(option.length);//pos为正确选项位置
        right = pos;
        ArrayList<Integer> temp = new ArrayList<Integer>();//用来保存已经赋值的选项的序列
        temp.add(real);
        option[pos] = real;
        int next = r.nextInt(wordlist.size());//错误选项的单词序列号
        for (int i = 0; i < option.length; i++) {
            if (i == pos) continue;
            while (temp.contains(next)) {//如果temp中包含next，即重复，重新再选
                next = r.nextInt(wordlist.size());
            }
            option[i] = next;
            temp.add(next);
        }
        return option;
    }

    private void showOption() {
        int[] option = getOption(wordseq[count]);
        tv_word.setText(wordlist.get(wordseq[count]).getWord() + "\n");

        for (int i = 0; i < textviews.length; i++) {
            String word = wordlist.get(option[i]).getWord();
            String meaning = dict.getDictBean(word).getMeaningForHtml();
            textviews[i].setText(Html.fromHtml(transToHtml(i, meaning)));
        }
        if (count >= 15) {
            Toast.makeText(getActivity(), "已通关", Toast.LENGTH_SHORT).show();
            getActivity().finish();
        }
    }

    @Override
    public void onClick(View v) {
        if (right != Integer.parseInt(v.getTag().toString())) {
            String word = wordlist.get(wordseq[count]).getWord();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.act_memorize, WordFragment.newInstance(word)).addToBackStack(null).commit();
        }
        count++;
        progressBar.setProgress(count);
        showOption();
    }
    /*public void showWordDetail(String){

    }*/

    private String transToHtml(int abcd, String meaning) {
        if (isDay) {
            return "<font color=\"blue\">" + getABCD(abcd) + " </font><font color=\"black\">" + meaning + "</font>";
        } else {
            return "<font color=\"blue\">" + getABCD(abcd) + " </font><font color=\"gray\">" + meaning + "</font>";
        }
    }

    private String getABCD(int pos) {
        String t = "";
        switch (pos) {
            case 0:
                t = "A";
                break;
            case 1:
                t = "B";
                break;
            case 2:
                t = "C";
                break;
            case 3:
                t = "D";
                break;
        }
        return t;
    }

    //被压入后退栈时调用
    @Override
    public void onDestroyView() {
        Bundle b = getArguments();
        b.putSerializable(WORD_SEQ, wordseq);
        super.onDestroyView();
    }


}
