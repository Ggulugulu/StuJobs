package com.example.stujobs.fragment;

import android.content.Intent;
import android.view.View;

import com.example.stujobs.R;
import com.example.stujobs.activity.IntroduceActivity;
import com.example.stujobs.activity.SendActivity;
import com.example.stujobs.base.BaseFragment;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xui.widget.textview.supertextview.SuperTextView;

import butterknife.BindView;

@Page(name = "我的")
public class PersonalFragment extends BaseFragment {

    @BindView(R.id.click_introduce)
    SuperTextView introduce;
    @BindView(R.id.click_send)
    SuperTextView send;

    @Override
    protected TitleBar initTitle() {
        TitleBar titleBar = super.initTitle();
        titleBar.disableLeftView();
        titleBar.removeAllActions();
        return titleBar;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_personal;
    }

    @Override
    protected void initViews() {
        introduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), IntroduceActivity.class));
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SendActivity.class));
            }
        });

    }
}
