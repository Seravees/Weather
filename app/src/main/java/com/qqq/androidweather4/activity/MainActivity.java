package com.qqq.androidweather4.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.qqq.androidweather4.R;
import com.qqq.androidweather4.WeatherApplication;
import com.qqq.androidweather4.db.CityDao;
import com.qqq.androidweather4.db.DBHelper;
import com.qqq.androidweather4.model.WeatherInfo;
import com.qqq.androidweather4.model.WeatherInfoNow;
import com.qqq.androidweather4.service.AutoUpdateWidgetService;
import com.qqq.androidweather4.util.HttpCallbackListener;
import com.qqq.androidweather4.util.HttpUtil;
import com.qqq.androidweather4.util.SharePreferencesUtil;
import com.qqq.androidweather4.util.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private List<String> cities = new ArrayList();

    private TextView date_y, date;
    private TextView cityField;
    private TextView todayTemp;
    private TextView currentWeather, currentTemp, currentWind;
    private TextView weather02_day, weather02_night, temp02, wind02;
    private TextView weather03_day, weather03_night, temp03, wind03;
    private ImageView weather_icon01, weather_icon02_day, weather_icon02_night, weather_icon03_day, weather_icon03_night;
    private PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String cityId = SharePreferencesUtil.get("city_id", "");

        if (cityId == null || cityId == "") {
            Intent intent = new Intent(this, SelectCityActivity.class);
            startActivity(intent);
            // finish();
        } else {
            WeatherApplication.setCity_id(cityId);
        }

        date_y = (TextView) findViewById(R.id.date_y);
        date_y.setText(new SimpleDateFormat("yyyy年MM月dd日(EEE)").format(new Date()));


        date = (TextView) findViewById(R.id.date);
        cityField = (TextView) findViewById(R.id.cityField);
        currentWeather = (TextView) findViewById(R.id.currentWeather);
        todayTemp = (TextView) findViewById(R.id.todayTemp);
        currentTemp = (TextView) findViewById(R.id.currentTemp);
        currentWind = (TextView) findViewById(R.id.currentWind);
        weather02_day = (TextView) findViewById(R.id.weather02_day);
        weather02_night = (TextView) findViewById(R.id.weather02_night);
        temp02 = (TextView) findViewById(R.id.temp02);
        wind02 = (TextView) findViewById(R.id.wind02);
        weather03_day = (TextView) findViewById(R.id.weather03_day);
        weather03_night = (TextView) findViewById(R.id.weather03_night);
        temp03 = (TextView) findViewById(R.id.temp03);
        wind03 = (TextView) findViewById(R.id.wind03);

        weather_icon01 = (ImageView) findViewById(R.id.weather_icon01);
        weather_icon02_day = (ImageView) findViewById(R.id.weather_icon02_day);
        weather_icon02_night = (ImageView) findViewById(R.id.weather_icon02_night);
        weather_icon03_day = (ImageView) findViewById(R.id.weather_icon03_day);
        weather_icon03_night = (ImageView) findViewById(R.id.weather_icon03_night);

        //showWeather();

        cityField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(MainActivity.this, SelectCityActivity.class);
                //startActivity(intent);
                showPopupWindow(v);
            }
        });


    }

    private void showPopupWindow(View v) {
        View view = LayoutInflater.from(this).inflate(R.layout.popup_window_city, null);
        popupWindow = new PopupWindow(view);
        popupWindow.setFocusable(true);
        popupWindow.setWidth(cityField.getWidth());
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
        // popupWindow.setContentView(view);
        ListView cityListView = (ListView) view.findViewById(R.id.list_view_city);
        Button addCity = (Button) view.findViewById(R.id.add_city);

        cities.clear();
        for (String s : SharePreferencesUtil.get("cities", new HashSet<String>())) {
            cities.add(s);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, cities);
        cityListView.setAdapter(adapter);
        addCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SelectCityActivity.class);
                startActivity(intent);
                popupWindow.dismiss();
            }
        });
        cityListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                popupWindow.dismiss();
                SQLiteDatabase db = DBHelper.getInstance(MainActivity.this).getReadableDatabase();
                Cursor cursor = db.rawQuery("select " + CityDao.NAME_ENGLISH + " from " + CityDao.TABLE_NAME + " where " + CityDao.NAME + " = ?", new String[]{cities.get(position)});
                while (cursor.moveToNext()) {
                    String city_id = cursor.getString(cursor.getColumnIndex(CityDao.NAME_ENGLISH));
                    WeatherApplication.setCity_id(city_id);
                    SharePreferencesUtil.put("city_id", city_id);
                }
                onResume();
            }
        });
        popupWindow.showAsDropDown(v);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = new Intent(this, AutoUpdateWidgetService.class);
        startService(intent);
        date.setText("正在更新...");

        HttpUtil.sendHttpRequest(WeatherApplication.getAddress1(), new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                WeatherInfoNow weatherInfoNow = WeatherApplication.getGson().fromJson(response, WeatherInfoNow.class);
                Bundle bundle = new Bundle();
                bundle.putString("date", Utils.getUpdateTime(weatherInfoNow.getResults().get(0).getLast_update().substring(0, 19)));
                bundle.putString("cityField", weatherInfoNow.getResults().get(0).getLocation().getName());
                bundle.putString("currentWeather", weatherInfoNow.getResults().get(0).getNow().getText());
                bundle.putString("todayTemp", weatherInfoNow.getResults().get(0).getNow().getTemperature() + "℃");
                bundle.putString("weatherCode", weatherInfoNow.getResults().get(0).getNow().getCode());
                Message message = new Message();
                message.what = 1;
                message.obj = bundle;
                handler.sendMessage(message);
                SharePreferencesUtil.put("date", weatherInfoNow.getResults().get(0).getLast_update().substring(0, 19));
                SharePreferencesUtil.put("cityField", weatherInfoNow.getResults().get(0).getLocation().getName());
                SharePreferencesUtil.put("currentWeather", weatherInfoNow.getResults().get(0).getNow().getText());
                SharePreferencesUtil.put("todayTemp", weatherInfoNow.getResults().get(0).getNow().getTemperature() + "℃");
                SharePreferencesUtil.put("weatherCode", weatherInfoNow.getResults().get(0).getNow().getCode());
            }

            @Override
            public void onError(Exception e) {
                Message message = new Message();
                message.what = 3;
                handler.sendMessage(message);
            }


        });

        HttpUtil.sendHttpRequest(WeatherApplication.getAddress2(), new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                WeatherInfo weatherInfo = WeatherApplication.getGson().fromJson(response, WeatherInfo.class);
                Bundle bundle = new Bundle();
                bundle.putString("currentTemp", weatherInfo.getResults().get(0).getDaily().get(0).getHigh() + "℃~" + weatherInfo.getResults().get(0).getDaily().get(0).getLow() + "℃");
                bundle.putString("currentWind", weatherInfo.getResults().get(0).getDaily().get(0).getWind_direction() + "风" + weatherInfo.getResults().get(0).getDaily().get(0).getWind_scale() + "级");
                bundle.putString("weather02_day", weatherInfo.getResults().get(0).getDaily().get(1).getText_day());
                bundle.putString("weather02_night", weatherInfo.getResults().get(0).getDaily().get(1).getText_night());
                bundle.putString("weather02Code_day", weatherInfo.getResults().get(0).getDaily().get(1).getCode_day());
                bundle.putString("weather02Code_night", weatherInfo.getResults().get(0).getDaily().get(1).getCode_night());
                bundle.putString("temp02", weatherInfo.getResults().get(0).getDaily().get(1).getHigh() + "℃~" + weatherInfo.getResults().get(0).getDaily().get(1).getLow() + "℃");
                bundle.putString("wind02", weatherInfo.getResults().get(0).getDaily().get(1).getWind_direction() + "风" + weatherInfo.getResults().get(0).getDaily().get(1).getWind_scale() + "级");
                bundle.putString("weather03_day", weatherInfo.getResults().get(0).getDaily().get(2).getText_day());
                bundle.putString("weather03_night", weatherInfo.getResults().get(0).getDaily().get(2).getText_night());
                bundle.putString("weather03Code_day", weatherInfo.getResults().get(0).getDaily().get(2).getCode_day());
                bundle.putString("weather03Code_night", weatherInfo.getResults().get(0).getDaily().get(2).getCode_night());
                bundle.putString("temp03", weatherInfo.getResults().get(0).getDaily().get(2).getHigh() + "℃~" + weatherInfo.getResults().get(0).getDaily().get(2).getLow() + "℃");
                bundle.putString("wind03", weatherInfo.getResults().get(0).getDaily().get(2).getWind_direction() + "风" + weatherInfo.getResults().get(0).getDaily().get(2).getWind_scale() + "级");
                Message message = new Message();
                message.what = 2;
                message.obj = bundle;
                handler.sendMessage(message);
                SharePreferencesUtil.put("currentTemp", weatherInfo.getResults().get(0).getDaily().get(0).getHigh() + "℃~" + weatherInfo.getResults().get(0).getDaily().get(0).getLow() + "℃");
                SharePreferencesUtil.put("currentWind", weatherInfo.getResults().get(0).getDaily().get(0).getWind_direction() + "风" + weatherInfo.getResults().get(0).getDaily().get(0).getWind_scale() + "级");
                SharePreferencesUtil.put("weather02_day", weatherInfo.getResults().get(0).getDaily().get(1).getText_day());
                SharePreferencesUtil.put("weather02_night", weatherInfo.getResults().get(0).getDaily().get(1).getText_night());
                SharePreferencesUtil.put("weather02Code_day", weatherInfo.getResults().get(0).getDaily().get(1).getCode_day());
                SharePreferencesUtil.put("weather02Code_night", weatherInfo.getResults().get(0).getDaily().get(1).getCode_night());
                SharePreferencesUtil.put("temp02", weatherInfo.getResults().get(0).getDaily().get(1).getHigh() + "℃~" + weatherInfo.getResults().get(0).getDaily().get(1).getLow() + "℃");
                SharePreferencesUtil.put("wind02", weatherInfo.getResults().get(0).getDaily().get(1).getWind_direction() + "风" + weatherInfo.getResults().get(0).getDaily().get(1).getWind_scale() + "级");
                SharePreferencesUtil.put("weather03_day", weatherInfo.getResults().get(0).getDaily().get(2).getText_day());
                SharePreferencesUtil.put("weather03_night", weatherInfo.getResults().get(0).getDaily().get(2).getText_night());
                SharePreferencesUtil.put("weather03Code_day", weatherInfo.getResults().get(0).getDaily().get(2).getCode_day());
                SharePreferencesUtil.put("weather03Code_night", weatherInfo.getResults().get(0).getDaily().get(2).getCode_night());
                SharePreferencesUtil.put("temp03", weatherInfo.getResults().get(0).getDaily().get(2).getHigh() + "℃~" + weatherInfo.getResults().get(0).getDaily().get(2).getLow() + "℃");
                SharePreferencesUtil.put("wind03", weatherInfo.getResults().get(0).getDaily().get(2).getWind_direction() + "风" + weatherInfo.getResults().get(0).getDaily().get(2).getWind_scale() + "级");
            }

            @Override
            public void onError(Exception e) {

            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        /*Intent intent = new Intent(this, AutoUpdateReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);*/
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Bundle bundle = (Bundle) msg.obj;
                    date.setText(bundle.getString("date", ""));
                    cityField.setText(bundle.getString("cityField", ""));
                    currentWeather.setText(bundle.getString("currentWeather", ""));
                    todayTemp.setText(bundle.getString("todayTemp", ""));
                    weather_icon01.setImageResource(Utils.getIcon(Integer.parseInt(bundle.getString("weatherCode", "0"))));
                    break;
                case 2:
                    Bundle bundle1 = (Bundle) msg.obj;
                    currentTemp.setText(bundle1.getString("currentTemp", ""));
                    currentWind.setText(bundle1.getString("currentWind", ""));
                    weather02_day.setText(bundle1.getString("weather02_day", ""));
                    weather02_night.setText(bundle1.getString("weather02_night", ""));
                    weather_icon02_day.setImageResource(Utils.getIcon(Integer.parseInt(bundle1.getString("weather02Code_day", "0"))));
                    weather_icon02_night.setImageResource(Utils.getIcon(Integer.parseInt(bundle1.getString("weather02Code_night", "0"))));
                    temp02.setText(bundle1.getString("temp02", ""));
                    wind02.setText(bundle1.getString("wind02", ""));
                    weather03_day.setText(bundle1.getString("weather03_day", ""));
                    weather03_night.setText(bundle1.getString("weather03_night", ""));
                    weather_icon03_day.setImageResource(Utils.getIcon(Integer.parseInt(bundle1.getString("weather03Code_day", "0"))));
                    weather_icon03_night.setImageResource(Utils.getIcon(Integer.parseInt(bundle1.getString("weather03Code_night", "0"))));
                    temp03.setText(bundle1.getString("temp03", ""));
                    wind03.setText(bundle1.getString("wind03", ""));
                    break;
                case 3:
                    showWeather();
                default:
                    break;
            }
        }
    };

    private void showWeather() {
        //Log.d("MainActivity2", SharePreferencesUtil.get("date", ""));
        date.setText(Utils.getUpdateTime(SharePreferencesUtil.get("date", "")));
        cityField.setText(SharePreferencesUtil.get("cityField", ""));
        currentWeather.setText(SharePreferencesUtil.get("currentWeather", ""));
        todayTemp.setText(SharePreferencesUtil.get("todayTemp", ""));
        weather_icon01.setImageResource(Utils.getIcon(Integer.parseInt(SharePreferencesUtil.get("weatherCode", "0"))));
        currentTemp.setText(SharePreferencesUtil.get("currentTemp", ""));
        currentWind.setText(SharePreferencesUtil.get("currentWind", ""));
        weather02_day.setText(SharePreferencesUtil.get("weather02_day", ""));
        weather02_night.setText(SharePreferencesUtil.get("weather02_night", ""));
        weather_icon02_day.setImageResource(Utils.getIcon(Integer.parseInt(SharePreferencesUtil.get("weather02Code_day", "0"))));
        weather_icon02_night.setImageResource(Utils.getIcon(Integer.parseInt(SharePreferencesUtil.get("weather02Code_night", "0"))));
        temp02.setText(SharePreferencesUtil.get("temp02", ""));
        wind02.setText(SharePreferencesUtil.get("wind02", ""));
        weather03_day.setText(SharePreferencesUtil.get("weather03_day", ""));
        weather03_night.setText(SharePreferencesUtil.get("weather03_night", ""));
        weather_icon03_day.setImageResource(Utils.getIcon(Integer.parseInt(SharePreferencesUtil.get("weather03Code_day", "0"))));
        weather_icon03_night.setImageResource(Utils.getIcon(Integer.parseInt(SharePreferencesUtil.get("weather03Code_night", "0"))));
        temp03.setText(SharePreferencesUtil.get("temp03", ""));
        wind03.setText(SharePreferencesUtil.get("wind03", ""));
    }


}
