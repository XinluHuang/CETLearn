package com.xinluhuang.englishlearn.module.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.xinluhuang.englishlearn.R;
import com.xinluhuang.englishlearn.dict.DictBean;
import com.xinluhuang.englishlearn.greendao.entity.CET6Word;
import com.xinluhuang.englishlearn.greendao.entity.CETWord;
import com.xinluhuang.englishlearn.module.adapter.commomadapter.CommomAdapter;
import com.xinluhuang.englishlearn.module.adapter.commomadapter.CommomViewHolder;
import com.xinluhuang.englishlearn.util.LogUtil;

import java.util.List;

public class CETWordListAdapter extends CommomAdapter<CETWord> {
    public CETWordListAdapter(Context context, List<CETWord> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    @Override
    protected void convertView(@Nullable View convertView, CETWord cetWord) {
        TextView head= CommomViewHolder.get(convertView, R.id.tv_word);
        head.setText(cetWord.getWord());
        TextView phonetic= CommomViewHolder.get(convertView,R.id.tv_phonetic);
        phonetic.setText(cetWord.getSpell());
        TextView detail= CommomViewHolder.get(convertView,R.id.tv_detail);
        detail.setText(cetWord.getMeaning());
    }
}
