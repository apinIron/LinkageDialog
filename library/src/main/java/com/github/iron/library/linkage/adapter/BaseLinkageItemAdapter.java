package com.github.iron.library.linkage.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.View;

import com.github.iron.library.linkage.LinkageItem;

import java.util.ArrayList;
import java.util.List;

/**
 * @author iron
 *         created at 2018/6/19
 *         适配器
 */
public abstract class BaseLinkageItemAdapter<T extends RecyclerView.ViewHolder> extends Adapter<T> {

    protected Context mContext;
    private int mPageIndex;
    private int mSelectPosition;
    private LinkageItem mSelectLinkageItem;
    private List<LinkageItem> mItems;
    private IOnItemClickListener mListener;

    public BaseLinkageItemAdapter(Context context){
        mContext = context;
        mItems = new ArrayList<>();
    }

    public void setPageIndex(int pageIndex){
        mPageIndex = pageIndex;
    }

    @Override
    public void onBindViewHolder(final T holder, int position) {
        //绑定数据
        bindView(holder, mItems.get(position), position == mSelectPosition);
        //点击事件
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectPosition = holder.getAdapterPosition();
                mSelectLinkageItem = mItems.get(mSelectPosition);
                if (mListener != null) {
                    mListener.onItemClick(mPageIndex, mSelectPosition, mItems.get(mSelectPosition));
                }
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void setData(List<LinkageItem> items){
        mItems = items;
        mSelectPosition = -1;
        mSelectLinkageItem = null;
        notifyDataSetChanged();
    }

    public LinkageItem getSelectLinkageItem(){
        return mSelectLinkageItem;
    }

    public void setOnItemClickListener(IOnItemClickListener listener){
        mListener = listener;
    }

    protected abstract void bindView(T holder,LinkageItem item,boolean isSelect);

    public interface IOnItemClickListener {

        void onItemClick(int pageIndex, int position, LinkageItem item);
    }
}
