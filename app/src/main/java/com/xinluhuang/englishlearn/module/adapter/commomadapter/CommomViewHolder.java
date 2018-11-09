package com.xinluhuang.englishlearn.module.adapter.commomadapter;

import android.util.SparseArray;
import android.view.View;

public class CommomViewHolder {
    public static <T extends View> T get(View view, int id) {
        //id作为key
        SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
        if (viewHolder == null) {
            viewHolder = new SparseArray<View>();
            view.setTag(viewHolder);
        }
        View childView = viewHolder.get(id);
        if (childView == null) {
            childView = view.findViewById(id);
            viewHolder.put(id, childView);
        }
        //自动转类型
        return (T) childView;
    }


    private CommomViewHolder(){}
}
