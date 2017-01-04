package com.qqq.androidweather4.activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.qqq.androidweather4.R;
import com.qqq.androidweather4.WeatherApplication;
import com.qqq.androidweather4.adapter.MyDecoration;
import com.qqq.androidweather4.adapter.SelectCityAdapter;
import com.qqq.androidweather4.db.CityDao;
import com.qqq.androidweather4.db.DBHelper;
import com.qqq.androidweather4.util.SharePreferencesUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectCityActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    List<Map<String, Object>> data = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_city);
        initData();
        recyclerView = (RecyclerView) findViewById(R.id.select_city_recycler_view);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        ((LinearLayoutManager) layoutManager).setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.addItemDecoration(new MyDecoration(this));

        SelectCityAdapter adapter = new SelectCityAdapter(this, data);
        adapter.setOnItemClickListener(new SelectCityAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                WeatherApplication.setCity_id((String) data.get(position).get("cityNameEnglish"));
                SharePreferencesUtil.put("city_id", (String) data.get(position).get("cityNameEnglish"));
                //Toast.makeText(SelectCityActivity.this, WeatherApplication.getAddress1(), Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        recyclerView.setAdapter(adapter);

    }

    public void initData() {
        SQLiteDatabase db = DBHelper.getInstance(WeatherApplication.getContext()).getReadableDatabase();
        Cursor cursor = db.rawQuery("select " + CityDao.NAME + "," + CityDao.NAME_ENGLISH + " from " + CityDao.TABLE_NAME, null);
        while (cursor.moveToNext()) {
            String cityName = cursor.getString(cursor.getColumnIndex(CityDao.NAME));
            String cityNameEnglish = cursor.getString(cursor.getColumnIndex(CityDao.NAME_ENGLISH));
            Map<String, Object> map = new HashMap();
            map.put("cityName", cityName);
            map.put("cityNameEnglish", cityNameEnglish);
            data.add(map);
        }
        cursor.close();
        db.close();
    }
}
