package com.xinluhuang.englishlearn.module.memorize;

import android.text.Html;
import android.text.Spanned;

import com.xinluhuang.englishlearn.dict.NormalDict;
import com.xinluhuang.englishlearn.greendao.entity.CETWord;
import com.xinluhuang.englishlearn.util.LogUtil;
import com.xinluhuang.englishlearn.util.SPUtil;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class MemorizingPresenter {
    private MemorizingView memorizingView;
    private CETWord[] wordlist;
    private NormalDict dict = NormalDict.getInstance();

    private static final int OPTION_SIZE = 4;
    //单词乱序
    private int[] wordDisorder;
    //当前进度
    private int progress = 0;
    //当前正确选项(ABCD)的位置
    private int right = 0;
    private int[] options;
    private boolean isDay = true;
    //当前正确单词
    private String word;
    private Set<Integer> set = new HashSet<>();

    public MemorizingPresenter(MemorizingView memorizingView) {
        this.memorizingView = memorizingView;
    }

    public void init() {
        wordlist = memorizingView.getWordList();
        wordDisorder = getRandomArray(wordlist.length);
        isDay = (Boolean) SPUtil.get(SPUtil.isDay, true);
        progress = 0;
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

    private Spanned[] getOption(int[] options) {
        String word;
        String meaning;
        Spanned[] spanneds = new Spanned[OPTION_SIZE];
        for (int i = 0; i < OPTION_SIZE; i++) {
            word = wordlist[options[i]].getWord();
            meaning = dict.getDictBean(word).getMeaningForHtml();
            spanneds[i] = Html.fromHtml(transToHtml(i, meaning));
        }
        return spanneds;
    }

    //单词序列号，传入参数(real)为正确单词的序列(0~14)，返回四个单词序列号
    private int[] getOptionKey() {
        Random r = new Random();
        int[] option = new int[4];
        right = r.nextInt(option.length);//right为正确选项位置,wordDisorder[progress]
        set.add(wordDisorder[progress]);//用来保存已经赋值的选项的序列
        option[right] = wordDisorder[progress];
        int next = r.nextInt(wordlist.length);//错误选项的单词序列号
        for (int i = 0; i < option.length; i++) {
            if (i == right) continue;//正确选项已赋值
            while (set.contains(next)) {//如果temp中包含next，即重复，重新再选
                next = r.nextInt(wordlist.length);
            }
            option[i] = next;
            set.add(next);
        }
        set.clear();//清空
        return option;
    }

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

    public boolean isRightOption(int option) {
        return right == option;
    }

    public void refresh(){
        if(progress>=wordlist.length){
            memorizingView.pass();
            return;
        }
        word=wordlist[wordDisorder[progress]].getWord();
        refreshOption();
        refreshProgress();
    }


    public void refreshOption() {
        options=getOptionKey();
        memorizingView.refreshOption(word,getOption(options));
    }

    public void refreshProgress() {
        LogUtil.d("progress++");
        memorizingView.refreshProgress(progress++);
    }
    public void showWordDetail(){
        memorizingView.showWordDetail(word);
    }

    public void destroy() {
        memorizingView=null;
    }
}
