package com.example.stujobs.base;

import android.content.res.Configuration;
import android.view.View;
import android.view.ViewGroup;

import com.example.stujobs.activity.MainActivity;
import com.example.stujobs.R;
import com.xuexiang.xaop.annotation.SingleClick;
import com.xuexiang.xpage.model.PageInfo;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;
import com.xuexiang.xui.utils.DensityUtils;
import com.xuexiang.xui.widget.actionbar.TitleBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * 基础主页面
 *
 * @author xuexiang
 * @since 2018/12/29 上午11:18
 */
public abstract class BaseHomeFragment extends BaseFragment implements RecyclerViewHolder.OnItemClickListener<PageInfo> {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @Override
    protected TitleBar initTitle() {
        TitleBar titleBar = super.initTitle();
        titleBar.disableLeftView();
        titleBar.addAction(new TitleBar.ImageAction(R.drawable.icon_action_query) {
            @Override
            @SingleClick
            public void performAction(View view) {
                //openNewPage(SearchComponentFragment.class);
            }

            @Override
            public int rightPadding() {
                return DensityUtils.dp2px(getContext(), 10);
            }
        });
        return titleBar;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_container;
    }

    @Override
    @SingleClick
    public void onItemClick(View itemView, PageInfo widgetInfo, int pos) {
        if (widgetInfo != null) {
            openNewPage(widgetInfo.getName());
        }
    }

    public MainActivity getContainer() {
        return (MainActivity) getActivity();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        //屏幕旋转时刷新一下title
        super.onConfigurationChanged(newConfig);
        ViewGroup root = (ViewGroup) getRootView();
        if (root.getChildAt(0) instanceof TitleBar) {
            root.removeViewAt(0);
            initTitle();
        }
    }

}
