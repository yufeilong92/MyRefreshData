package com.example.dell.myrefreshdata.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.example.dell.myrefreshdata.R;

import java.util.ArrayList;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: RecyclerAdapter.java
 * @Package com.example.dell.myrefreshdata.adapter
 * @Description: todo
 * @author: YFL
 * @date: 2018/4/22 10:31
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/4/22 星期日
 * 注意：本内容仅限于学川教育有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class RecyclerAdapter extends BaseRecyclerAdapter<RecyclerAdapter.ViewHolder> implements View.OnClickListener {
    private ArrayList<String> mData;
    private Context mContext;
    private final LayoutInflater mInflater;
    private TextView mTvTitel;

    public onClickItemListener onClickItemListener;

    @Override
    public void onClick(View v) {
        if (onClickItemListener!=null){
            onClickItemListener.onClickItem(v.getTag(),v.getId());
        }
    }

    public interface onClickItemListener {
        public void onClickItem(Object obj,int position);
    }

    public void setOnClickItemListener(RecyclerAdapter.onClickItemListener onClickItemListener) {
        this.onClickItemListener = onClickItemListener;
    }

    public RecyclerAdapter(ArrayList mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public ViewHolder getViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        View view = mInflater.inflate(R.layout.item_recycler, null);
        ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position, boolean isItem) {
        String vo = mData.get(position);
        holder.mTvTitel.setText(vo);
        holder.itemView.setTag(vo);
        holder.itemView.setId(position);
    }

    @Override
    public int getAdapterItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTvTitel;

        public ViewHolder(View itemView) {
            super(itemView);
            this.mTvTitel = (TextView) itemView.findViewById(R.id.tv_titel);

        }
    }
}
