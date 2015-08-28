package com.lnyp.listviewoptimize;

import android.app.Activity;
import android.os.Bundle;
import android.widget.AbsListView;
import android.widget.ListView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.nostra13.universalimageloader.core.assist.PauseOnScrollListener;


public class MainActivity extends Activity implements IOAuthCallBack {

    @ViewInject(R.id.listview)
    private ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewUtils.inject(this);
        qryDataFromServer();

//        //pauseOnScroll控制我们缓慢滑动ListView，GridView是否停止加载图片，pauseOnFling 控制猛的滑动ListView，GridView是否停止加载图片
//        actualListView.setOnScrollListener(new PauseOnScrollListener(ImageLoaderUtil.getInstance().getImageLoader(), false, true, onScrollListener));
        listview.setOnScrollListener(new PauseOnScrollListener(ImageLoaderUtil.getInstance().getImageLoader(), false, true, onScrollListener));
    }

    private void qryDataFromServer() {
        HttpUtil.sendRequest(this, HttpRequest.HttpMethod.GET, null, this);
    }

    @Override
    public void getIOAuthCallBack(String result) {

        RspData rspData = GsonUtil.getGson().fromJson(result, RspData.class);
        // 更新UI列表

        KechengAdapter mAdapter = new KechengAdapter(this, rspData.data);
        listview.setAdapter(mAdapter);
    }

    private AbsListView.OnScrollListener onScrollListener = new AbsListView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            switch (scrollState) {
                case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                    // 触摸后滚动
                    break;

                case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
                    // 滚动状态
                    break;

                case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                    // 空闲状态
                    if (view.getLastVisiblePosition() == view.getCount() - 1) {
                        System.out.println("************滚动到了最后一个***************");
                    }
                    break;
            }
        }
        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        }
    };
}
