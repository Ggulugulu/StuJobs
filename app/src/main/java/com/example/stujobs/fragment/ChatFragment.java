package com.example.stujobs.fragment;

import com.example.stujobs.R;
import com.example.stujobs.base.BaseFragment;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xui.widget.actionbar.TitleBar;

import androidx.fragment.app.FragmentTransaction;
import io.rong.imkit.conversationlist.ConversationListFragment;
import io.rong.imlib.RongIMClient;

@Page(name = "消息")
public class ChatFragment extends BaseFragment {
    private ConversationListFragment mConversationListFragment;

    @Override
    protected TitleBar initTitle() {
        TitleBar titleBar = super.initTitle();
        titleBar.disableLeftView();
        titleBar.removeAllActions();
        return titleBar;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_chat;
    }
    @Override
    protected void initViews() {
        mConversationListFragment = new ConversationListFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.conversation_list_container, mConversationListFragment).commit();
        initChat();
    }

    private void initChat() {
        String token = "Qe/hmuzxvbFgx0gb5QVVHMHhYM5YC37G@8xeh.cn.rongnav.com;8xeh.cn.rongcfg.com";
        RongIMClient.connect(token, new RongIMClient.ConnectCallback() {
            @Override
            public void onSuccess(String userId) {
                // 连接成功，处理业务逻辑
            }

            @Override
            public void onError(RongIMClient.ConnectionErrorCode errorCode) {
                // 连接失败，处理错误情况
            }

            @Override
            public void onDatabaseOpened(RongIMClient.DatabaseOpenStatus databaseOpenStatus) {

            }
        });

    }

}