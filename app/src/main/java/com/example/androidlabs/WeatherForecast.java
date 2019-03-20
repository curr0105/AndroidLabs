package com.example.androidlabs;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class WeatherForecast extends AppCompatActivity {

    private static String TEMPS = "http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=7e943c97096a9784391a981c4d878b22&mode=xml&units=metric";
    private static String UV = "http://api.openweathermap.org/data/2.5/uvi?appid=7e943c97096a9784391a981c4d878b22&lat=45.348945&lon=-75.759389";
    private static String IMAGE = "http://openweathermap.org/img/w/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);

        ForecastQuery networkThread = new ForecastQuery();
        networkThread.execute(TEMPS, UV);

        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
    }

    public class ForecastQuery extends AsyncTask<String, Integer, String> {

        private String min, max, curr, icon;
        private double uv;
        private Bitmap image;

        //this is to see the progress of the application running
        ProgressBar pb = findViewById(R.id.progressBar);

        @Override
        protected String doInBackground(String... args) {

            try {
                URL url = new URL(TEMPS);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                InputStream inputStream = conn.getInputStream();

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                //this is what talks to the xml on the website
                xpp.setInput(inputStream, "UTF-8");

                while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {

                    if (xpp.getEventType() == XmlPullParser.START_TAG) {
                        if (xpp.getName().equals("temperature")) {
                            curr = xpp.getAttributeValue(null, "value");
                            //tell android to call onProgressUpdate with 25 as parameter
                            publishProgress(25);
                            Log.i("Current Temp:", "" + curr);

                            min = xpp.getAttributeValue(null, "min");
                            publishProgress(50);
                            Log.i("Current Temp:", "" + min);

                            max = xpp.getAttributeValue(null, "max");
                            publishProgress(75);
                            Log.i("Current Temp:", "" + max);

                        } else if (xpp.getName().equals("weather")) {
                            icon = xpp.getAttributeValue(null, "icon");
                        }
                    }
                    xpp.next();
                }

                URL imageUrl = new URL(IMAGE+icon+".png");
                HttpURLConnection conn2 = (HttpURLConnection) imageUrl.openConnection();
                conn2.connect();
                int responseCode = conn2.getResponseCode();
                    if (responseCode == 200) {
                        image = BitmapFactory.decodeStream(conn2.getInputStream());
                    }

                FileOutputStream outputStream = openFileOutput(icon + ".png",
                        Context.MODE_PRIVATE);
                image.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                outputStream.flush();
                outputStream.close();

                if(fileExistance(icon)) {

                    FileInputStream fis = null;
                    try {
                        fis = openFileInput(icon + ".png");
                    } catch (FileNotFoundException e) {
                        Log.i("Download this file", e.getMessage());
                    }
                    image = BitmapFactory.decodeStream(fis);
                }

                //Start of JSON reading of UV factor:
                //create the network connection:
                URL uvUrl = new URL(UV);
                HttpURLConnection UVConnection = (HttpURLConnection)uvUrl.openConnection();
                inputStream = UVConnection.getInputStream();

                //create a JSON object from the response
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,
                        "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();

                String line = null;
                while ((line = reader.readLine()) != null)
                {
                    sb.append(line + "\n");
                }
                String result = sb.toString();

                //now a JSON table:
                JSONObject jObject = new JSONObject(result);
                double aDouble = jObject.getDouble("value");
                Log.i("UV is:", ""+ aDouble);
                uv = aDouble;

                publishProgress(100);

                Thread.sleep(2000); //pause for 2000 milliseconds to watch the progress bar grow

            } catch (Exception ex) {
            }
            return null;
        }
        public boolean fileExistance(String fname){
            File file = getBaseContext().getFileStreamPath(fname);
            return file.exists();

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            //this will make the progress bar update. The value in the array at index 0 gets
            //changed every time this method is called.
            pb.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(String s) {

            TextView currTemp = findViewById(R.id.currTemp);
            TextView minTemp = findViewById(R.id.minTemp);
            TextView maxTemp = findViewById(R.id.maxTemp);
            TextView uvRating = findViewById(R.id.uv);
            ImageView imageView = findViewById(R.id.weathericon);

            currTemp.setText(String.format(getResources().getString(R.string.currTemp), curr));
            minTemp.setText(String.format("Minimum Temp: %1$s", min));
            maxTemp.setText(String.format("Maximum Temp: %1$s", max));
            uvRating.setText(String.format("UV Rating: %1$s", uv));
            imageView.setImageBitmap(image);

            pb.setVisibility(View.INVISIBLE);
        }


    }

}











