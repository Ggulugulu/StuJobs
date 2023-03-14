package com.example.stujobs.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.stujobs.R;
import com.example.stujobs.adapter.entity.SendInfo;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;
import com.xuexiang.xui.widget.button.roundbutton.RoundButton;
import com.xuexiang.xui.widget.textview.supertextview.SuperTextView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SendAdapter extends RecyclerView.Adapter<RecyclerViewHolder>  {
    // 要显示的数据的集合
    private List<SendInfo> data;
    // 接受上下文
    private Context context;
    private int remark;

    public SendAdapter(Context context, List<SendInfo> data,int remark) {
        this.context = context;
        this.data = data;
        this.remark = remark;
    }

    public interface ItemCellClicker{
        void onItemClick(View view ,int position);
        void onIntroduceClick(View view ,int position,String url);
    }
    // 流式布局标签点击事件
    private SendAdapter.ItemCellClicker itemCellClicker;
    // 设置点击事件回调
    public void setItemCellClicker(SendAdapter.ItemCellClicker itemCellClicker){
        this.itemCellClicker = itemCellClicker;
    }
    @NonNull
    @NotNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_send, parent, false);
        return new RecyclerViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerViewHolder holder, int position) {
        holder.text(R.id.introduce_title,this.data.get(position).getPosition());
        holder.text(R.id.send_company,this.data.get(position).getCompany());

        TextView result =holder.itemView.findViewById(R.id.send_result);
        result.setText(this.data.get(position).getResult());

        SuperTextView introduce = holder.itemView.findViewById(R.id.send_introduce);
        introduce.setLeftString(this.data.get(position).getTitle());
        introduce.setRightString(this.data.get(position).getName());
        introduce.setOnClickListener(view -> itemCellClicker.onIntroduceClick(introduce,position,this.data.get(position).getUrl()));

        RoundButton pass = holder.itemView.findViewById(R.id.send_pass);
        RoundButton out = holder.itemView.findViewById(R.id.send_out);
        if(remark == 1){
            pass.setVisibility(View.GONE);
            out.setVisibility(View.GONE);
        }else if(remark == 2){
            result.setVisibility(View.GONE);
            pass.setOnClickListener(view -> itemCellClicker.onItemClick(pass,position));
            out.setOnClickListener(view -> itemCellClicker.onItemClick(out,position));
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
