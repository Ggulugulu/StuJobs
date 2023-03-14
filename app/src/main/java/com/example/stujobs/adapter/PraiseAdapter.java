package com.example.stujobs.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.stujobs.R;
import com.example.stujobs.adapter.entity.JobInfo;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PraiseAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {
    // 要显示的数据的集合
    private List<JobInfo> data;
    // 接受上下文
    private Context context;
    public PraiseAdapter(Context context, List<JobInfo> data) {
        this.context = context;
        this.data = data;
    }

    public interface ItemCellClicker{
        void onItemClick(View view ,int position);
    }
    // 流式布局标签点击事件
    private PraiseAdapter.ItemCellClicker itemCellClicker;
    // 设置点击事件回调
    public void setItemCellClicker(PraiseAdapter.ItemCellClicker itemCellClicker){
        this.itemCellClicker = itemCellClicker;
    }
    @NonNull
    @NotNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_praise, parent, false);
        return new RecyclerViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerViewHolder holder, int position) {
        holder.text(R.id.comment_text,this.data.get(position).getMore_praise());
        holder.text(R.id.comment_time,this.data.get(position).getMore_time());
        holder.text(R.id.like_count,String.valueOf(this.data.get(position).getMore_count()));

        ImageView praise = holder.itemView.findViewById(R.id.click_praise);
        praise.setOnClickListener(view -> itemCellClicker.onItemClick(praise,position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
