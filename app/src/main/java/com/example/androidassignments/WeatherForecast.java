package com.example.androidassignments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class WeatherForecast extends AppCompatActivity {
    private final String ACTIVITY_NAME ="WeatherForecast.java";
    public ProgressBar bar;
    TextView minTemp;
    TextView maxTemp;
    TextView currentTemp;
    ImageView imageView;
    List <String> cityList;
    TextView cityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);
        bar=findViewById(R.id.progressBar);
        maxTemp=findViewById(R.id.maxTemp);
        minTemp=findViewById(R.id.minTemp);
        imageView=findViewById(R.id.currentWeather);
        currentTemp=findViewById(R.id.currentTemp);
        Log.i(ACTIVITY_NAME,"in"+ACTIVITY_NAME+"OnCreate()");
        cityName=findViewById(R.id.cityName);
        bar.setVisibility(View.VISIBLE);

//        ForecastQuery f = new ForecastQuery();
//        f.execute();
        get_a_city();


    }

    public void get_a_city() {
        // Get the list of cities.
        cityList = Arrays.asList(getResources().getStringArray(R.array.cities));
        // Make a handler for the city list.
        final Spinner citySpinner = findViewById(R.id.CityPicker);
        ArrayAdapter <CharSequence> adapter =
                ArrayAdapter.createFromResource(
                        this, R.array.cities, android.R.layout.simple_spinner_dropdown_item);
        //  adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //  android.R.layout.simple_spinner_item
        citySpinner.setAdapter(adapter);
        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView <?> adapterView,
                                       View view, int i, long l) {
                new ForecastQuery(cityList.get(i)).execute();
                cityName.setText(cityList.get(i) + " Weather");
            }
            @Override
            public void onNothingSelected(AdapterView <?> adapterView) {
            }
        });
    }

    private class ForecastQuery extends AsyncTask<String,Integer,String> {
        protected String city;
        private String min;
        private String max;
        private String current_temperature;
        private Bitmap picture_weather;

        public ForecastQuery(String city) {
            this.city=city;
        }


        @Override
        protected String doInBackground(String... strings) {
            try{
                URL url = new URL(
                        "https://api.openweathermap.org/" +
                                "data/2.5/weather?q=" + this.city + "," +
                                "ca&APPID=79cecf493cb6e52d25bb7b7050ff723c&" +
                                "mode=xml&units=metric");


                HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();
                InputStream inputStream = conn.getInputStream();
                try{

                    XmlPullParser parser = Xml.newPullParser();
                    parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                    parser.setInput(inputStream, null);

                    int type;


                    while((type = parser.getEventType()) != XmlPullParser.END_DOCUMENT){
                        if (parser.getEventType() == XmlPullParser.START_TAG){
                            if (parser.getName().equals("temperature")){
                                current_temperature = parser.getAttributeValue(null, "value");
                                publishProgress(25);
                                min = parser.getAttributeValue(null, "min");
                                publishProgress(50);
                                max = parser.getAttributeValue(null, "max");
                                publishProgress(75);
                            } else if (parser.getName().equals("weather")){
                                String iconName = parser.getAttributeValue(null, "icon");
                                String fileName = iconName + ".png";

                                Log.i("WeatherForecast", "Looking for file" + fileName);

                                if (fileExistance(fileName)){
                                    FileInputStream fileInputStream = null;
                                    try{
                                        fileInputStream = openFileInput(fileName);
                                    } catch (FileNotFoundException e){
                                        e.printStackTrace();
                                    }
                                    Log.i("WeatherForecast", "Found File Locally");
                                    picture_weather = BitmapFactory.decodeStream(fileInputStream);
                                    Log.i(ACTIVITY_NAME,"HELLO");

                                }else{
                                    String iconURL = "https://openweathermap.org/img/w/" + fileName;
                                    picture_weather = getImage(new URL(iconURL));
                                    FileOutputStream outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
                                    picture_weather.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                                    Log.i("WeatherForecast", "Downloaded File From Internet");
                                    outputStream.flush();
                                    outputStream.close();
                                }
                                publishProgress(100);

                            }
                        }
                        parser.next();
                    }

                } finally {
                    inputStream.close();
                    Log.i(ACTIVITY_NAME,"closed inputStream");
                }
            } catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }

        public boolean fileExistance(String fname) {
            File file = getBaseContext().getFileStreamPath(fname);
            return file.exists();
        }
        public Bitmap getImage(URL url) {
            HttpsURLConnection connection = null;
            try {
                connection = (HttpsURLConnection) url.openConnection();
                connection.connect();
                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    return BitmapFactory.decodeStream(connection.getInputStream());
                } else
                    return null;
            } catch (Exception e) {
                return null;
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }
        @Override
        protected void onProgressUpdate(Integer... values) {
            bar.setProgress(values[0]);
        }
        @Override
        protected void onPostExecute(String a) {
            bar.setVisibility(View.INVISIBLE);
            imageView.setImageBitmap(picture_weather);
            currentTemp.setText(current_temperature + "C\u00b0");
            minTemp.setText(min + "C\u00b0");
            maxTemp.setText(max + "C\u00b0");
       }


        }
}