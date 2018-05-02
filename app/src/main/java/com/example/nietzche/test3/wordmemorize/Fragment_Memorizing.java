package com.example.nietzche.test3.wordmemorize;


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

import com.example.nietzche.test3.R;
import com.example.nietzche.test3.dict.NormalDict;

import java.util.ArrayList;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Memorizing extends Fragment implements View.OnClickListener {
    private ArrayList<String> wordlist;
    private ProgressBar progressBar;
    private int[] wordseq;
    private int count = 0;
    private TextView word;
    private TextView[] textviews;
    private NormalDict dict;
    private int right;

    public Fragment_Memorizing() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_memorizing, container, false);
        wordlist = (ArrayList<String>) getArguments().getSerializable("wordlist");

        dict = new NormalDict(getActivity().getFilesDir().getParent() + "/databases/dict.db");
        word = view.findViewById(R.id.word);
        textviews = new TextView[4];
        progressBar=view.findViewById(R.id.progressbar);
        progressBar.setProgress(count);
        TextView a = view.findViewById(R.id.word);
        textviews[0] = view.findViewById(R.id.A);
        textviews[1] = view.findViewById(R.id.B);
        textviews[2] = view.findViewById(R.id.C);
        textviews[3] = view.findViewById(R.id.D);
        for (int i = 0; i < textviews.length; i++) {
            textviews[i].setOnClickListener(this);
        }
        if(getArguments().getSerializable("wordseq")==null) {
            wordseq = getRandomArray(wordlist.size());//后退会刷新,需要保存状态
        }else {
            IntList list=(IntList) getArguments().getSerializable("wordseq");
            wordseq=new int[list.size()];
            for (int i = 0; i < list.size(); i++) {
                wordseq[i]=list.get(i);
            }
        }

        fillOption();
        return view;
    }

    private int[] getRandomArray(int size) {
        Random r = new Random();
        int t;
        int position;
        int[] seq = new int[size];
        for (int i = 0; i < seq.length; i++) {
            seq[i] = i;
        }
        for (int i = seq.length - 1; i > 0; i--) {
            position = r.nextInt(i);
            t = seq[position];
            seq[position] = seq[i];
            seq[i] = t;
        }
        return seq;
    }

    private int[] getOption(int real) {//单词序列号，传入参数为正确单词的序列0~14，返回四个单词序列号
        Random r = new Random();
        int[] option = new int[4];
        int pos = r.nextInt(option.length);//正确选项位置
        right = pos;
        ArrayList<Integer> temp = new ArrayList<Integer>();
        temp.add(real);
        option[pos] = real;
        int next = r.nextInt(wordlist.size());//错误选项的单词序列号
        for (int i = 0; i < option.length; i++) {
            if (i == pos) continue;
            while (temp.contains(next)) {
                next = r.nextInt(wordlist.size());
            }
            option[i] = next;
            temp.add(next);
        }
        return option;
    }

    private void fillOption() {
        int[] option = getOption(wordseq[count]);
        word.setText(wordlist.get(wordseq[count])+"\n");

        for (int i = 0; i < textviews.length; i++) {
            textviews[i].setText(Html.fromHtml(transInHtml(i, dict.getMeaning(wordlist.get(option[i])))));
        }
        if (count >= 15) {
            Toast.makeText(getActivity(), "已通关", Toast.LENGTH_SHORT).show();
            getActivity().finish();
        }
    }

    @Override
    public void onClick(View v) {
        if (right != Integer.parseInt(v.getTag().toString())) {
            Fragment_wordDetail f = new Fragment_wordDetail();
            Bundle b = new Bundle();
            b.putString("word", wordlist.get(wordseq[count]));
            f.setArguments(b);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.act_memorize, f).addToBackStack(null).commit();
        }
        count++;
        progressBar.setProgress(count);
        fillOption();
    }

    private String transInHtml(int abcd, String meaning) {
        return new String("<font color=\"blue\">" + getABCD(abcd) + " </font><font color=\"black\">" + meaning + "</font>");
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


    @Override
    public void onDestroyView() {
        Bundle b=getArguments();
        IntList list=new IntList();
        for (int i = 0; i < wordseq.length; i++) {
            list.add(wordseq[i]);
        }
        b.putSerializable("wordseq",list);
        super.onDestroyView();
    }
}
