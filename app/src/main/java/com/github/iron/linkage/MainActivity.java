package com.github.iron.linkage;

import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.github.iron.library.linkage.adapter.BaseLinkageItemAdapter;
import com.github.iron.library.linkage.LinkageDialog;
import com.github.iron.library.linkage.LinkageItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intTestBtn();

        initCityBtn();
        
        initAdapterBtn();

    }

    private void intTestBtn() {
        //创建对话框
        final LinkageDialog dialog = new LinkageDialog.Builder(MainActivity.this, 3).setLinkageData(getData())
                .setOnLinkageSelectListener(new LinkageDialog.IOnLinkageSelectListener() {
                    @Override
                    public void onLinkageSelect(LinkageItem... items) {
                        toastLinkageItem(items);
                    }
                }).build();

        //按钮点击
        findViewById(R.id.btn_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });
    }

    private List<LinkageItem> getData(){
        List<LinkageItem> items = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Data d1 = new Data("A " + i);

            for (int j = 0; j < 10; j++) {
                Data d2 = new Data("B " + i + j);
                d1.getChild().add(d2);

                for (int k = 0; k < 10; k++) {
                    Data d3 = new Data("C " + i + j + k);
                    d2.getChild().add(d3);
                }
            }
            items.add(d1);
        }
        return items;
    }

    private void initCityBtn() {
        //创建对话框
        final LinkageDialog dialog = new LinkageDialog.Builder(MainActivity.this, 3).setLinkageData(getCityList())
                .setOnLinkageSelectListener(new LinkageDialog.IOnLinkageSelectListener() {
                    @Override
                    public void onLinkageSelect(LinkageItem... items) {
                        toastLinkageItem(items);
                    }
                }).build();

        //按钮点击
        findViewById(R.id.btn_city).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });
    }

    private List<LinkageItem> getCityList() {
        String json = getJson("city.json");
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<City>>() {}.getType();
        List<City> citys = gson.fromJson(json, type);
        List<LinkageItem> cityList = new ArrayList<>();
        cityList.addAll(citys);
        return cityList;
    }

    public String getJson(String fileName) {
        //将json数据变成字符串
        StringBuilder stringBuilder = new StringBuilder();
        try {
            //获取assets资源管理器
            AssetManager assetManager = getAssets();
            //通过管理器打开文件并读取
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    private void initAdapterBtn() {
        //创建对话框
        final LinkageDialog dialog = new LinkageDialog.Builder(MainActivity.this, 3).setLinkageData(getCityList())
                .setTitle("配送至").setTabIndicatorColor(Color.parseColor("#EA6F5A"))
                .setTabTitleColor(Color.BLACK, Color.parseColor("#EA6F5A"))
                .setTabIndicatorHeight((int) getResources().getDimension(R.dimen.indicator_height))
                .setContentHeight((int) (getResources().getDimension(R.dimen.item_city_height) * 4))
                .setOnLinkageAdapterListener(new LinkageDialog.IOnLinkageAdapterListener() {
                    @Override
                    public BaseLinkageItemAdapter getAdapter() {
                        return new MyLinkageItemAdapter(MainActivity.this);
                    }
                })
                .setOnLinkageSelectListener(new LinkageDialog.IOnLinkageSelectListener() {
                    @Override
                    public void onLinkageSelect(LinkageItem... items) {
                        toastLinkageItem(items);
                    }
                }).build();

        //按钮点击
        findViewById(R.id.btn_adapter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });
    }

    private void toastLinkageItem(LinkageItem... items){
        StringBuilder str = new StringBuilder(" ");
        for (int i = 0; i < items.length && items[i] != null; i++) {
            str.append(items[i].getLinkageName());
            str.append(" ");
        }
        Toast.makeText(this, str.toString(), Toast.LENGTH_SHORT).show();
    }

}
