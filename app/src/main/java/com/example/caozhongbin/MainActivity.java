package com.example.caozhongbin;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.caozhongbin.adapter.MyAdapter;
import com.example.caozhongbin.app.App;
import com.example.caozhongbin.bean.Bean;
import com.google.gson.Gson;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements OnBannerListener {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private MyAdapter myAdapter;
    private OkHttpClient okHttpClient;
    private List<Bean.TopStoriesBean> list = new ArrayList<>();
    private Banner banner;
    private ArrayList<String> list_path;
    private ArrayList<String> list_title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_bin);
        banner = (Banner) findViewById(R.id.banner);
        //banner轮播
        //// // TODO: 2017/9/20 setBannerStyle()方法  设置轮播的样式：是否有小圆点，标题，以及小圆点的样式、位置
        initBannerLunBo();
        //okhttp加载数据，并设置条目点击事件，点击吐司对应的title
        loadBean();

    }

    private void loadBean() {
        okHttpClient = App.okHttpClient();

        Request request = new Request.Builder()
                .url("http://news-at.zhihu.com/api/4/news/latest")
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.isSuccessful()) {
                    String json = response.body().string();
                    Gson gson = new Gson();
                    Bean bean = gson.fromJson(json, Bean.class);
                    List<Bean.TopStoriesBean> top_stories = bean.getTop_stories();
                    list.addAll(top_stories);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            linearLayoutManager = new LinearLayoutManager(MainActivity.this);
                            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                            recyclerView.setLayoutManager(linearLayoutManager);
                            myAdapter = new MyAdapter(MainActivity.this, list);
                            recyclerView.setAdapter(myAdapter);
                            myAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, String position) {
                                    Toast.makeText(MainActivity.this, "" + position.toString().trim(), Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    });
                }
            }
        });
    }


    private void initBannerLunBo() {
        banner = (Banner) findViewById(R.id.banner);
        //放图片地址的集合
        list_path = new ArrayList<>();
        //放标题的集合
        list_title = new ArrayList<>();

        list_path.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic21363tj30ci08ct96.jpg");
        list_path.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic259ohaj30ci08c74r.jpg");
        list_path.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic2b16zuj30ci08cwf4.jpg");
        list_path.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic2e7vsaj30ci08cglz.jpg");
        list_title.add("好好学习");
        list_title.add("天天向上");
        list_title.add("热爱劳动");
        list_title.add("不搞对象");
        //设置内置样式，共有六种可以点入方法内逐一体验使用。
        banner.setBannerStyle(BannerConfig.NOT_INDICATOR);
        //设置图片加载器，图片加载器在下方
        banner.setImageLoader(new MyLoader());
        //设置图片网址或地址的集合
        banner.setImages(list_path);
        //设置轮播的动画效果，内含多种特效，可点入方法内查找后内逐一体验
        banner.setBannerAnimation(Transformer.Default);
        //设置轮播图的标题集合
        banner.setBannerTitles(list_title);
        //设置轮播间隔时间
        banner.setDelayTime(3000);
        //设置是否为自动轮播，默认是“是”。
        banner.isAutoPlay(true);
        //设置指示器的位置，小点点，左中右。
        banner.setIndicatorGravity(BannerConfig.CENTER)
                //以上内容都可写成链式布局，这是轮播图的监听。比较重要。方法在下面。
                .setOnBannerListener(this)
                //必须最后调用的方法，启动轮播图。
                .start();


    }

    //轮播图的监听方法
    @Override
    public void OnBannerClick(int position) {
        Log.i("tag", "你点了第" + position + "张轮播图");
    }

    //自定义的图片加载器
    private class MyLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context).load((String) path).into(imageView);
        }
    }
}
