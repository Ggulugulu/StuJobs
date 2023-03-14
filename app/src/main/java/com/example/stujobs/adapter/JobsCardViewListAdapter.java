package com.example.stujobs.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.stujobs.R;
import com.example.stujobs.adapter.entity.JobInfo;
import com.google.android.flexbox.FlexboxLayout;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class JobsCardViewListAdapter extends RecyclerView.Adapter<JobsCardViewListAdapter.MyViewHolder> {

    private List<JobInfo> data;
    private Context myContext;

    public JobsCardViewListAdapter(Context context, List<JobInfo> data) {
        this.myContext = context;
        this.data = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_job_card_list_item, parent, false);
        return new MyViewHolder(inflate);
    }

    public interface ItemCellClicker{
        void onItemClick(String tag);
        void onCardClick(View view ,int position);
    }

    // 流式布局标签点击事件
    public ItemCellClicker itemCellClicker;
    // 设置点击事件回调
    public void setItemCellClicker(ItemCellClicker itemCellClicker){
        this.itemCellClicker = itemCellClicker;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.text(R.id.tv_company,  this.data.get(position).getCompany());
        holder.text(R.id.tv_tag, this.data.get(position).getPrice());
        holder.text(R.id.tv_title, this.data.get(position).getPosition());
        holder.text(R.id.tv_praise, this.data.get(position).getLike() == "0" ? "点赞" : this.data.get(position).getLike());
        holder.text(R.id.tv_comment, this.data.get(position).getPingjia() == "0" ? "评论" : this.data.get(position).getPingjia());

        FlexboxLayout flexboxLayout = holder.itemView.findViewById(R.id.flowlayout_normal_select);

        JobInfo data = this.data.get(position);
        List<String> tags = data.getTags();

        flexboxLayout.removeAllViews();
        // flexbox布局动态添加标签
        for (int i = 0; i < tags.size(); i++) {
            String temp = tags.get(i);
            View tagView = LayoutInflater.from(myContext).inflate(R.layout.adapter_item_tag, null, false);
            TextView tag = tagView.findViewById(R.id.tv_tag);
            tag.setText(temp);
            // 设置标签点击事件
            tag.setOnClickListener(view -> itemCellClicker.onItemClick(temp));
            flexboxLayout.addView(tagView);
        }

        FrameLayout card = holder.itemView.findViewById(R.id.click_card);
        card.setOnClickListener(view -> itemCellClicker.onCardClick(card,position));

    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class MyViewHolder extends RecyclerViewHolder{

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}