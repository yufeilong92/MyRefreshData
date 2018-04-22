package com.example.dell.myrefreshdata;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshViewFooter;
import com.example.dell.myrefreshdata.adapter.RecyclerAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRlvContent;
    private XRefreshView mXrfv;

    private ArrayList mArrary;
    private RecyclerAdapter adapter;
    private boolean isRefresh = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }

    private void initRrefresh(ArrayList list) {
        adapter = new RecyclerAdapter(list, this);
        GridLayoutManager manager = new GridLayoutManager(this, 1);
        manager.setOrientation(GridLayoutManager.VERTICAL);
        mRlvContent.setLayoutManager(manager);
        mRlvContent.setAdapter(adapter);

    }

    private ArrayList initData() {
        ArrayList<String> list = new ArrayList<>();
        int a = 20;
        for (int i = 0; i < a; i++) {
            list.add("测试内容" + i);
        }
        return list;
    }

    public static long lastRefreshTime;

    private void initView() {
        mRlvContent = (RecyclerView) findViewById(R.id.rlv_content);
        mXrfv = (XRefreshView) findViewById(R.id.xrfv);
        ArrayList list = initData();
        clearData();
        addListData(list);
        initRrefresh(mArrary);
        mRlvContent.setHasFixedSize(true);
//        int lastRefreshTime = 0;
        mXrfv.restoreLastRefreshTime(lastRefreshTime);
//        mXrfv.setMoveForHorizontal(true);
        mXrfv.setPullLoadEnable(true);
        mXrfv.setAutoLoadMore(true);
//        mXrfv.enableReleaseToLoadMore(true);
//        mXrfv.enableRecyclerViewPullUp(true);
//        mXrfv.enablePullUpWhenLoadCompleted(true);
        adapter.setCustomLoadMoreView(new XRefreshViewFooter(this));
        mXrfv.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {

            @Override
            public void onRefresh(boolean isPullDown) {
                isRefresh = false;
                lastRefreshTime = mXrfv.getLastRefreshTime();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (isRefresh) {
                            return;
                        }
                        isRefresh = true;
                        ArrayList list = getloMore();
                        clearData();
                        addListData(list);
                        adapter.notifyDataSetChanged();
                        mXrfv.stopRefresh();
                        mXrfv.stopLoadMore(true);
                    }
                }, 2000);


            }

            @Override
            public void onLoadMore(boolean isSilence) {
                isRefresh = false;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (isRefresh) {
                            return;
                        }
                        isRefresh = true;
                        ArrayList list = initData();
//                        clearData();
                        if (list != null && !list.isEmpty()) {
                            addListData(list);
                        }
                        adapter.notifyDataSetChanged();
                        mXrfv.setLoadComplete(false);
                        mXrfv.stopLoadMore(true);
                    }
                }, 2000);
            }

        });
    }

    private void clearData() {
        if (mArrary == null) {
            mArrary = new ArrayList();
        } else {
            mArrary.clear();
        }
    }

    private void addListData(ArrayList list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        if (mArrary == null) {
            clearData();
        }
        mArrary.addAll(list);
    }

    private ArrayList getloMore() {
        ArrayList<String> list = new ArrayList<>();
        int a = 20;
        for (int i = 0; i < a; i++) {
            list.add("刷仙数据" + i);
        }
        return list;
    }
}
