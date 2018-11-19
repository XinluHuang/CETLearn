package com.xinluhuang.englishlearn.module.memorize;

import android.os.Bundle;
import android.text.Spanned;

import com.xinluhuang.englishlearn.greendao.entity.CETWord;

public interface MemorizingView {
    void refreshOption(String word, Spanned[] option);
    CETWord[] getWordList();
    void refreshProgress(int progress);
    void showWordDetail(String word);
    void pass();
}
