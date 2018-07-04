package com.github.iron.library.linkage;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.iron.library.R;
import com.github.iron.library.linkage.adapter.BaseLinkageItemAdapter;
import com.github.iron.library.linkage.adapter.LinkageItemAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author iron
 *         created at 2018/6/19
 *         联动对话框
 */
public class LinkageDialog extends Dialog implements BaseLinkageItemAdapter.IOnItemClickListener {

    private final Builder mBuilder;
    private TabLayout tlLinkageTab;
    private ViewPager vpLinkage;
    private int mLastShowTabIndex;

    private List<TextView> mTabTitles;
    private List<RecyclerView> mRecyclers;
    private List<BaseLinkageItemAdapter> mAdapters;

    private static final String WAIT_SELECT_TEXT = "请选择";

    private LinkageDialog(@NonNull Context context,Builder builder) {
        super(context, R.style.BottomDialog);

        mBuilder = builder;
        //初始化
        init();
    }

    /**
     * 初始化
     */
    private void init(){
        View contentView = LayoutInflater.from(mBuilder.context).inflate(R.layout.dialog_linkage, null);
        setContentView(contentView);

        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        layoutParams.width = mBuilder.context.getResources().getDisplayMetrics().widthPixels;
        contentView.setLayoutParams(layoutParams);
        Window window = getWindow();
        if(window != null) {
            window.setGravity(Gravity.BOTTOM);
        }

        //初始化控件
        initView();
    }

    /**
     * 初始化控件
     */
    private void initView(){
        tlLinkageTab = findViewById(R.id.tl_linkage_title);
        vpLinkage = findViewById(R.id.vp_linkage);
        //标题
        initTitle();
        //初始化viewPager
        initViewPager();
        //设置tabLayout颜色配置
        initTabLayoutConfig();
        //添加tab
        for (int i = 0; i < mBuilder.linkageCount; i++) {
            tlLinkageTab.addTab(tlLinkageTab.newTab());
        }
        //绑定
        tlLinkageTab.setupWithViewPager(vpLinkage);
        //初始化tabTitleView
        initTabTitleView();
        //除了第一个外无法点击
        resetLinkageTab(1);
        //显示第一个的数据
        mAdapters.get(0).setData(mBuilder.data);
        //默认为第一个
        mLastShowTabIndex = 0;
        setLinkageTabText(0, WAIT_SELECT_TEXT);
    }

    /**
     * 初始化标题
     */
    private void initTitle(){
        //标题文字
        ((TextView)findViewById(R.id.tv_title)).setText(mBuilder.title);
        //关闭按钮
        findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    /**
     * 初始化分页
     */
    private void initViewPager() {
        mRecyclers = new ArrayList<>();
        mAdapters = new ArrayList<>();
        for (int i = 0; i < mBuilder.linkageCount; i++) {
            RecyclerView view = new RecyclerView(mBuilder.context);
            view.setLayoutManager(new LinearLayoutManager(mBuilder.context));
            view.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            view.setOverScrollMode(View.OVER_SCROLL_NEVER);
            //显示数据
            BaseLinkageItemAdapter adapter;
            if(mBuilder.adapterListener != null){
                adapter = mBuilder.adapterListener.getAdapter();
            }else {
                adapter = new LinkageItemAdapter(mBuilder.context);
            }
            adapter.setPageIndex(i);
            adapter.setOnItemClickListener(this);
            view.setAdapter(adapter);

            mRecyclers.add(view);
            mAdapters.add(adapter);
        }
        //设置高度
        if(mBuilder.contentHeight != 0) {
            ViewGroup.LayoutParams layoutParams = vpLinkage.getLayoutParams();
            layoutParams.height = mBuilder.contentHeight;
        }
        //设置适配器
        vpLinkage.setAdapter(new PagerAdapter() {

            @Override
            public int getCount() {
                return mRecyclers.size();
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(mRecyclers.get(position));
                return mRecyclers.get(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position,
                                    Object object) {
                container.removeView(mRecyclers.get(position));
            }

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0 == arg1;
            }
        });
    }

    /**
     * 初始化tabLayout配置
     */
    private void initTabLayoutConfig() {
        //指标颜色
        if(mBuilder.colorTabIndicator != 0) {
            tlLinkageTab.setSelectedTabIndicatorColor(mBuilder.colorTabIndicator);
        }
        //指标高度
        if(mBuilder.tabIndicatorHeight != 0){
            tlLinkageTab.setSelectedTabIndicatorHeight(mBuilder.tabIndicatorHeight);
        }
    }

    /**
     * 初始化tab标题view
     */
    private void initTabTitleView() {
        mTabTitles = new ArrayList<>();
        //遍历设置标题
        for (int i = 0; i < tlLinkageTab.getTabCount(); i++) {
            TextView tv = new TextView(mBuilder.context);
            tv.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            tv.setGravity(Gravity.CENTER);
            TabLayout.Tab tab = tlLinkageTab.getTabAt(i);
            if (tab != null) {
                tab.setCustomView(tv);
            }
            mTabTitles.add(tv);
        }
    }

    /**
     * 设置指定tab文字
     */
    private void setLinkageTabText(int index, String text) {
        if(index < mTabTitles.size()) {
            //设置文字
            TextView tv = mTabTitles.get(index);
            tv.setText(text);
            //如果是请选择
            if (WAIT_SELECT_TEXT.equals(text)) {
                tv.setTextColor(mBuilder.colorTabWaitSelectTitleColor);
            } else {
                tv.setTextColor(mBuilder.colorTabSelectTitleColor);
            }
        }
    }

    /**
     * 选中tab
     */
    private void selectLinkageTab(int index){
        TabLayout.Tab tab = tlLinkageTab.getTabAt(index);
        if (tab != null) {
            tab.select();
        }
    }

    /**
     * 重置position之后的tab状态(包含位置)
     */
    private void resetLinkageTab(int position){
        LinearLayout tabStrip = ((LinearLayout)tlLinkageTab.getChildAt(0));
        for(int i = position; i < tabStrip.getChildCount(); i++) {
            tabStrip.getChildAt(i).setClickable(false);
            setLinkageTabText(i,"");
        }
    }

    /**
     * 条目点击事件
     */
    @Override
    public void onItemClick(int pageIndex, int position, LinkageItem item) {
        //设置标题
        setLinkageTabText(pageIndex, item.getLinkageName());
        //如果没有下级
        List<LinkageItem> items = item.getChild();
        if(items == null || items.size() == 0){
            confirmSelect();
            return;
        }
        //如果是最后一页
        if (pageIndex == mBuilder.linkageCount - 1) {
            confirmSelect();
        } else {
            //如果点击不是当前也，而是最前面的页面
            if(pageIndex < mLastShowTabIndex){
                //重置状态
                resetLinkageTab(pageIndex + 1);
            }
            //页数加一
            mLastShowTabIndex = pageIndex + 1;
            //切换到下一个
            vpLinkage.setCurrentItem(mLastShowTabIndex);
            selectLinkageTab(mLastShowTabIndex);
            //设置为能点击
            ((LinearLayout) tlLinkageTab.getChildAt(0)).getChildAt(mLastShowTabIndex).setClickable(true);
            //设置标题
            setLinkageTabText(mLastShowTabIndex, WAIT_SELECT_TEXT);
            //显示数据
            mAdapters.get(mLastShowTabIndex).setData(item.getChild());
        }
    }

    /**
     * 提交选中
     */
    private void confirmSelect(){
        //回调选择
        if(mBuilder.clickListener != null){
            LinkageItem[] items = new LinkageItem[mBuilder.linkageCount];
            for (int i = 0; i < mAdapters.size(); i++) {
                items[i] = mAdapters.get(i).getSelectLinkageItem();
            }
            mBuilder.clickListener.onLinkageSelect(items);
        }
        //关闭自己
        dismiss();
    }

    /**
     * 构建者
     */
    public static class Builder {

        private Context context;
        private int linkageCount;
        private String title;
        private int contentHeight;
        private int colorTabIndicator;
        private int tabIndicatorHeight;
        private int colorTabSelectTitleColor;
        private int colorTabWaitSelectTitleColor;
        private List<LinkageItem> data;
        private IOnLinkageSelectListener clickListener;
        private IOnLinkageAdapterListener adapterListener;

        public Builder(Context context,int linkageCount){
            this.context = context;
            this.linkageCount = linkageCount;
        }

        public Builder setTitle(String title){
            this.title = title;
            return this;
        }

        public Builder setContentHeight(int contentHeight){
            this.contentHeight = contentHeight;
            return this;
        }

        public Builder setLinkageData(List<LinkageItem> data){
            this.data = data;
            return this;
        }

        public Builder setTabIndicatorColor(int colorTabIndicator){
            this.colorTabIndicator = colorTabIndicator;
            return this;
        }

        public Builder setTabIndicatorHeight(int tabIndicatorHeight){
            this.tabIndicatorHeight = tabIndicatorHeight;
            return this;
        }

        public Builder setTabTitleColor(int colorTabSelectTitleColor,int colorTabWaitSelectTitleColor){
            this.colorTabSelectTitleColor = colorTabSelectTitleColor;
            this.colorTabWaitSelectTitleColor = colorTabWaitSelectTitleColor;
            return this;
        }

        public Builder setOnLinkageSelectListener(IOnLinkageSelectListener listener){
            this.clickListener = listener;
            return this;
        }

        public Builder setOnLinkageAdapterListener(IOnLinkageAdapterListener listener){
            this.adapterListener = listener;
            return this;
        }

        public LinkageDialog build() {
            //默认标题
            if(TextUtils.isEmpty(title)){
                title = "请选择地址";
            }
            //默认颜色
            if(colorTabSelectTitleColor == 0 || colorTabWaitSelectTitleColor == 0){
                colorTabSelectTitleColor = Color.BLACK;
                colorTabWaitSelectTitleColor = Color.GRAY;
            }

            return new LinkageDialog(context, this);
        }
    }

    public interface IOnLinkageSelectListener {

        void onLinkageSelect(LinkageItem... items);
    }

    public interface IOnLinkageAdapterListener {

        BaseLinkageItemAdapter getAdapter();
    }

}
