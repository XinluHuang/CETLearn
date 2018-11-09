package com.xinluhuang.englishlearn.module.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.xinluhuang.englishlearn.R;
import com.xinluhuang.englishlearn.dict.DictBean;
import com.xinluhuang.englishlearn.greendao.entity.CET6Word;
import com.xinluhuang.englishlearn.module.adapter.commomadapter.CommomAdapter;
import com.xinluhuang.englishlearn.module.adapter.commomadapter.CommomViewHolder;

import java.util.List;

public class NormalWordListAdapter extends CommomAdapter<DictBean> {
    public NormalWordListAdapter(Context context, List<DictBean> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    @Override
    protected void convertView(@Nullable View convertView, DictBean dictBean) {
        TextView head= CommomViewHolder.get(convertView, R.id.tv_word);
        head.setText(dictBean.getWord());
        TextView phonetic= CommomViewHolder.get(convertView,R.id.tv_phonetic);
        phonetic.setText(dictBean.getUk());
        TextView detail= CommomViewHolder.get(convertView,R.id.tv_detail);
        detail.setText(dictBean.getSingleLineMeaning());
    }
}
