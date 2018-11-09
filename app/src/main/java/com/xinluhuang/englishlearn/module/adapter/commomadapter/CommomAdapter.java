package com.xinluhuang.englishlearn.module.adapter.commomadapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public abstract class CommomAdapter<T> extends BaseAdapter {
    private Context context;
    //T为数据源类型
    private List<T> datas;
    private int layoutId;

    public CommomAdapter(Context context, List<T> datas, int layoutId) {
        this.context = context;
        this.datas = datas;
        this.layoutId = layoutId;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public T getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        //与item对应的id，用position
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(layoutId,null);
        }
        T t=getItem(position);
        convertView(convertView,t);
        return convertView;
    }

    protected abstract void convertView(@Nullable View convertView, T t);
}
