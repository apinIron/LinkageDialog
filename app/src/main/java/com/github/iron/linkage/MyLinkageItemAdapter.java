package com.github.iron.linkage;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.iron.library.linkage.adapter.BaseLinkageItemAdapter;
import com.github.iron.library.linkage.LinkageItem;


/**
 * @author iron
 *         created at 2018/6/20
 */
public class MyLinkageItemAdapter extends BaseLinkageItemAdapter<MyLinkageItemAdapter.ViewHolder> {

    public MyLinkageItemAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_linkage_my, parent,false));
    }

    @Override
    protected void bindView(ViewHolder holder, LinkageItem item, boolean isSelect) {
        holder.textView.setText(item.getLinkageName());
        holder.textView.setTextColor(isSelect ? Color.BLACK : Color.GRAY);
        holder.imageView.setVisibility(isSelect ? View.VISIBLE : View.INVISIBLE);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.tv_linkage_title);
            imageView = itemView.findViewById(R.id.iv_select);
        }
    }
}
