package com.example.arshad.myparking;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class reservationActivity extends AppCompatActivity {
    private CheckBox cheapestCheck, closestCheck, bestCheck;
    boolean cheapest, closest, best = false;
    public Boolean show_arrival_time = false;
    public String CHEAPEST_INTENT = "cheapest";
    public String CLOSEST_INTENT = "closest";
    public String BEST_INTENT = "best";
    private static final String LOCAL_URL = "http://ec2-52-15-220-86.us-east-2.compute.amazonaws.com:8000/reservation";
    //private static final String SPOT_URL = "http://ec2-3-17-208-112.us-east-2.compute.amazonaws.com:8080/spot?spotId=5c4b7dba70a1d00ef70e8d8a";
    Handler mainHandler;
    public String reservation_id="not set";
   private String user_id,closest_id,cheapest_id,best_id;
   private String parking_id = "5c4b7e1570a1d00ef70e8d8c";
   public Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        cheapestCheck = (CheckBox) findViewById(R.id.checkBox);
        closestCheck = (CheckBox) findViewById(R.id.checkBox2);
        bestCheck = (CheckBox) findViewById(R.id.checkBox3);

        final Button res_ = (Button) findViewById(R.id.reserve);
        final Button can_ = (Button) findViewById(R.id.cancel);
        Bundle extras = getIntent().getExtras();
        user_id=extras.getString("userId");
        closest_id=extras.getString("closestId");
        cheapest_id=extras.getString("cheapestId");
        best_id=extras.getString("bestId");
        intent  = new Intent(reservationActivity.this,mapActivity.class);

//        Log.e(" userId " , user_id);
//        Log.e(" cheapestId " , cheapest_id);



        res_.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(closestCheck.isChecked())
                {

                    whenAsynchronousGetRequest_thenCorrect(closest_id);
                    Toast.makeText(getApplicationContext(), "Reservation Created! You have 10 minutes to arrive. Go back to map", Toast.LENGTH_SHORT).show();

                    final String cheapest_URL = "http://ec2-52-15-220-86.us-east-2.compute.amazonaws.com:8000/spot?spotId="+cheapest_id;
                    final String best_URL = "http://ec2-52-15-220-86.us-east-2.compute.amazonaws.com:8000/spot?spotId="+best_id;
                    String putBody = "{\"available\": true}";
                        try {
                            putReservation(cheapest_URL,putBody);
                            putReservation(best_URL,putBody);

                        } catch (IOException e) {
                        e.printStackTrace();


                }



                    //Intent intent = new Intent(reservationActivity.this,mapActivity.class);
                    intent.putExtra("intent_string", CLOSEST_INTENT);
                   // intent.putExtra("reservationId", reservation_id);
//                    //Log.e(" test2" , reservation_id);
                    setResult(RESULT_OK, intent);
                    //startActivity(intent);

                }
                else if(cheapestCheck.isChecked())
                {whenAsynchronousGetRequest_thenCorrect(cheapest_id);
                    Toast.makeText(getApplicationContext(), "Reservation Created! You have 10 minutes to arrive. Go back to map", Toast.LENGTH_SHORT).show();

                    final String closest_URL = "http://ec2-52-15-220-86.us-east-2.compute.amazonaws.com:8000/spot?spotId="+closest_id;
                    final String best_URL = "http://ec2-52-15-220-86.us-east-2.compute.amazonaws.com:8000/spot?spotId="+best_id;
                    String putBody = "{\"available\": true}";
                    try {
                        putReservation(closest_URL,putBody);
                        putReservation(best_URL,putBody);

                    } catch (IOException e) {
                        e.printStackTrace();


                    }
                    //Intent intent = new Intent(reservationActivity.this,mapActivity.class);
                    intent.putExtra("intent_string", CHEAPEST_INTENT);
                    //intent.putExtra("reservationId", reservation_id);
  //                  Log.e(" test" , CHEAPEST_INTENT);
                    //changed
                    setResult(RESULT_OK, intent);
                    //startActivity(intent);





                }
                else if(bestCheck.isChecked())
                {whenAsynchronousGetRequest_thenCorrect(best_id);

                    Toast.makeText(getApplicationContext(), "Reservation Created! You have 10 minutes to arrive. Go back to map", Toast.LENGTH_SHORT).show();




                    final String cheapest_URL = "http://ec2-52-15-220-86.us-east-2.compute.amazonaws.com:8000/spot?spotId="+cheapest_id;
                    final String closest_URL = "http://ec2-52-15-220-86.us-east-2.compute.amazonaws.com:8000/spot?spotId="+closest_id;
                    String putBody = "{\"available\": true}";
                    try {
                        putReservation(closest_URL,putBody);
                        putReservation(cheapest_URL,putBody);

                    } catch (IOException e) {
                        e.printStackTrace();


                    }
                   // Intent intent = new Intent(reservationActivity.this,mapActivity.class);
                    intent.putExtra("intent_string", BEST_INTENT);
                    //intent.putExtra("reservationId", reservation_id);
                    //setResult(RESULT_OK, intent);
    //                Log.e(" test" , BEST_INTENT);
                    setResult(RESULT_OK, intent);
                    //startActivity(intent);

















                }










                else{Toast.makeText(getApplicationContext(), "Please choose one option", Toast.LENGTH_SHORT).show();}



        }
        });
       can_.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                Toast.makeText(getApplicationContext(), "Reservation Cancelled", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(reservationActivity.this,mapActivity.class);
                startActivity(intent);
            }

        });
    }
    public void onCheckboxClicked(View view) {
        switch(view.getId()) {
            case R.id.checkBox:

                closestCheck.setChecked(false);
                bestCheck.setChecked(false);
                break;
            case R.id.checkBox2:
                bestCheck.setChecked(false);
                cheapestCheck.setChecked(false);
                break;
            case R.id.checkBox3:
                cheapestCheck.setChecked(false);
                closestCheck.setChecked(false);
                break;
        }
    }
    public void whenAsynchronousGetRequest_thenCorrect(String parkingId) {
        JSONObject jsonObject = new JSONObject();


        try {
            jsonObject.put("user_id", user_id);
            jsonObject.put("spot_id", parkingId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        RequestBody body = RequestBody.create(JSON, jsonObject.toString());

        Request request = new Request.Builder()
                .url(LOCAL_URL)
                .post(body)
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            public void onResponse(Call call, Response response)
                    throws IOException
            {

                if (response.code() == 200){
                    try (ResponseBody responseBody = response.body()) {

                        String jsonData = response.body().string();
                        JSONObject Jobject = new JSONObject(jsonData);
                        String start_time = Jobject.getString("start_time");
                        String cancelled = Jobject.getString("cancelled");
                        reservation_id =Jobject.getString("_id");
                        String customer = Jobject.getString("customer");
                        String spot = Jobject.getString("spot");

                        final String final_string = "Start time: " + start_time;


                        Handler mainHandler = new Handler(Looper.getMainLooper());
                        Runnable myRunnable = new Runnable() {
                            @Override
                            public void run() {
                                //Toast.makeText(getApplicationContext(), reservation_id, Toast.LENGTH_SHORT).show();
                      //          Intent intent = new Intent(reservationActivity.this,mapActivity.class);
                                intent.putExtra("reservation_id", reservation_id);
                                // intent.putExtra("reservationId", reservation_id);
      //                          Log.e(" test2" , reservation_id);
                                //Log.e(" test2" , reservation_id);
                           //     setResult(RESULT_OK, intent);
                            } // This is your code
                        };
                        mainHandler.post(myRunnable);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            public void onFailure(Call call, IOException e) {
                //response_.setText(e.toString());
                //fail();
                //Toast.makeText(getApplicationContext(), "Incorrect Username/Password", Toast.LENGTH_SHORT).show();
            }

        });
    }



    public void putReservation(String putUrl, String putBody) throws IOException {

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, putBody);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(putUrl)
                .put(body) //PUT
                .build();

        client.newCall(request).enqueue(new Callback() {

        @Override
        public void onFailure(Call call, IOException e) {
            call.cancel();
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            Log.e(" responseCode " , String.valueOf(response.code()));

        }
    });
}

}








