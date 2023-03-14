package com.example.stujobs.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.stujobs.R;
import com.example.stujobs.adapter.entity.IntroInfo;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;
import com.xuexiang.xui.widget.button.roundbutton.RoundButton;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class IntroduceAdapter extends RecyclerView.Adapter<IntroduceAdapter.MyViewHolder> {

    // 要显示的数据的集合
    private List<IntroInfo> data;
    // 接受上下文
    private Context context;

    public IntroduceAdapter(Context context, List<IntroInfo> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_introduce, parent, false);
        return new MyViewHolder(inflate);
    }


    public interface ItemCellClicker{
        void onItemClick(View view ,int position);
    }
    // 流式布局标签点击事件
    private ItemCellClicker itemCellClicker;
    // 设置点击事件回调
    public void setItemCellClicker(ItemCellClicker itemCellClicker){
        this.itemCellClicker = itemCellClicker;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
        holder.text(R.id.introduce_title,this.data.get(position).getTitle());

        RoundButton see = holder.itemView.findViewById(R.id.introduce_see);
        RoundButton delete = holder.itemView.findViewById(R.id.introduce_delete);
        see.setOnClickListener(view -> itemCellClicker.onItemClick(see,position));
        delete.setOnClickListener(view -> itemCellClicker.onItemClick(delete,position));


    }

    // 返回的id
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public static class MyViewHolder extends RecyclerViewHolder {
        public MyViewHolder(View itemView) {
            super(itemView);
        }

    }
}
