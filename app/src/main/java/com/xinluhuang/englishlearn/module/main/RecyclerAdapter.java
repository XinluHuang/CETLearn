package com.xinluhuang.englishlearn.module.main;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.xinluhuang.englishlearn.R;
import com.xinluhuang.englishlearn.module.memorize.WordMemorizingActivity;
import com.xinluhuang.englishlearn.util.LogUtil;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private Context context;
    private int count;
    private int type = 0;
    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int order = Integer.parseInt(v.getTag().toString());
            Intent i = new Intent(context, WordMemorizingActivity.class);
            i.putExtra("order", order);
            i.putExtra("type", type);
            i.putExtra("name", ((Button) v).getText());
            context.startActivity(i);
        }
    };


    public RecyclerAdapter(Context context, int count, int type) {
        this.context = context;
        this.count = count;
        this.type = type;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.button_custom, parent, false);
        view.setOnClickListener(listener);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.button.setText("第" + (position + 1) + "关");
        holder.button.setTag(position + 1);
    }

    @Override
    public int getItemCount() {
        return count;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private Button button;

        public ViewHolder(View itemView) {
            super(itemView);
            button = (Button) itemView;
        }
    }
}
