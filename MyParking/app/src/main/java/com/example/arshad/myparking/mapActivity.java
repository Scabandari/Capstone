package com.example.arshad.myparking;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PointOfInterest;

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


public class mapActivity extends AppCompatActivity implements
        OnMapReadyCallback {
    private String user_id;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private static final String LOCAL_URL = "http://ec2-52-15-220-86.us-east-2.compute.amazonaws.com:8000/selection";
    public Boolean show_arrival_time = false;
    public Boolean has_arrived = false;
    public String CHEAPEST_INTENT = "cheapest";
    public String CLOSEST_INTENT = "closest";
    public String BEST_INTENT = "best";
    private GoogleMap mMap;
    public JSONObject closest;
    public String closest_distance;
    public JSONObject closest_spot;
    public String closest_price;
    public String closest_lat;
    public String closest_long;
    public int number;
    String closest_id;
    public double closest_lat_; // returns double primitive
    public double closest_long_;
    public boolean timerRunning = false;
    public JSONObject cheapest;
    public String cheapest_distance;
    public JSONObject cheapest_spot;
    public String cheapest_price;
    public String cheapest_lat;
    public Boolean cheapest_available;
    public Boolean closest_available;
    public Boolean best_available;
    public String cheapest_long;
    String cheapest_id;
    public double cheapest_lat_; // returns double primitive
    public double cheapest_long_;
    public Boolean occupied;
    public double userLatitude;
    public double userLongitude;
    public JSONObject best;
    public String best_distance;
    public JSONObject best_spot;
    public String best_price;
    public String best_lat;
    public String best_long;
    String best_id;
    public double best_lat_; // returns double primitive
    public double best_long_;
    public ImageButton click;
    public ImageButton click3;
    public ImageButton click2;
    public ImageButton click4;
    LocationManager locationManager;
    Context mContext;
    public Marker a;
    public Marker b;
    public Marker c;
    public String intent_string;
    public String reservation_id;
    private int mRequestCode = 100;
    public String postBody;
    public String purchase_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle extras = getIntent().getExtras();
        user_id = extras.getString("userId");
        //Bundle extra = getIntent().getExtras();


        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_map);
        mContext = this;
        locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10,
                10, locationListenerGPS);
        isLocationEnabled();
        click3 = findViewById(R.id.imageButton3);
        click3.setVisibility(View.INVISIBLE);
        click = findViewById(R.id.imageButton);
        click2 = findViewById(R.id.imageButton2);
        click2.setVisibility(View.INVISIBLE);
        click4 = findViewById(R.id.imageButton4);
        click4.setVisibility(View.INVISIBLE);
        click.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //has to do the post request
                //Intent intent = new Intent(mapActivity.this,userActivity.class);
                //startActivity(intent);

                //   double user_lat_float = Double.parseDouble(userLatitude); // returns double primitive
                //  double user_long_float = Double.parseDouble(userLongitude);
                // locationListenerGPS


                try {
                    postRequest(LOCAL_URL, postBody);
                    //mMap.addMarker(new MarkerOptions().position(new LatLng(45.494447,-73.579582)).title("Closest Parking").snippet("Price:0.25$/min Distance:10m spot 1"));
                    //mMap.addMarker(new MarkerOptions().position(new LatLng(45.497501,-73.578515)).title("Best Parking").snippet("Price:0.45$/min Distance:1m spot 2"));
                    //mMap.addMarker(new MarkerOptions().position(new LatLng(45.496629,-73.580361)).title("Cheapest Parking").snippet("Price:0.2$/min Distance:20m spot 3"));
                    //cheapest_distance=Strin g.format("%.2f", cheapest_distance);
                    //mMap.addMarker(new MarkerOptions().position(new LatLng(cheapest_lat_,cheapest_long_)).title("Cheapest Parking").snippet("Price: "+cheapest_price+" $/min"+ " Distance: "+cheapest_distance+"km" ));
                    //mMap.addMarker(new MarkerOptions().position(new LatLng(closest_lat_,closest_long_)).title("Closest Parking").snippet("Price: "+closest_price+" $/min"+ " Distance: "+closest_distance+"km"));
                    //mMap.addMarker(new MarkerOptions().position(new LatLng(best_lat_,best_long_)).title("Third Option").snippet("Price: "+best_price+" $/min"+ " Distance: "+best_distance+"km"));
                    //Toast.makeText(getApplicationContext(), cheapest_lat, Toast.LENGTH_SHORT).show();

                } catch (IOException e) {
                    e.printStackTrace();
                }

                click3.setVisibility(View.VISIBLE);
                click.setVisibility(View.INVISIBLE);

            }
        });
        //
        click3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mapActivity.this, reservationActivity.class);
                intent.putExtra("userId", user_id);
                intent.putExtra("closestId", closest_id);
                intent.putExtra("cheapestId", cheapest_id);
                intent.putExtra("bestId", best_id);

                //changed
                startActivityForResult(intent, mRequestCode);
            }
        });


        click4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // has_arrived = true;
                whenAsynchronousGetRequest_thenCorrect();


            }
        });


        ImageButton click2 = findViewById(R.id.imageButton2);
        click2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mapActivity.this, userActivity.class);
                startActivity(intent);
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.current_location);
        mapFragment.getMapAsync(this);
    }

    //changed
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == mRequestCode && resultCode == RESULT_OK) {
            intent_string = data.getStringExtra("intent_string");
            reservation_id=data.getStringExtra("reservation_id");

        }

//        Log.e(" intent_string ", intent_string);
  //      Log.e(" reservation_id ", reservation_id);

    }


    @Override
    protected void onStart() {
        super.onStart();
        //click.setVisibility(View.VISIBLE);
        //a.remove();
        //b.remove();
        //c.remove();
        //mMap.clear();
    }


    public void postRequest(String postUrl, String postBody) throws IOException {
        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, postBody);
        Request request = new Request.Builder()
                .url(postUrl)
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String response_string = response.body().string();
                JSONObject response_body = null;
                try {
                    response_body = new JSONObject(response_string);
                    closest = response_body.getJSONObject("closest");
                    closest_distance = closest.getString("distance");
                    closest_spot = closest.getJSONObject("spot");
                    closest_price = closest_spot.getString("price_minute");
                    closest_lat = closest_spot.getString("latitude");
                    closest_long = closest_spot.getString("longitude");
                    closest_id = closest_spot.getString("_id");
                    final String whole_string = "Closest: " + closest_id + "\ndistance: " + closest_distance + "\n" +
                            "lat: " + closest_lat + "\nlong: " + closest_long + "\nprice: " +
                            closest_price;
                    closest_lat_ = Double.parseDouble(closest_lat); // returns double primitive
                    closest_long_ = Double.parseDouble(closest_long);
                    closest_available = closest_spot.getBoolean("available");


                    cheapest = response_body.getJSONObject("cheapest");
                    cheapest_distance = cheapest.getString("distance");
                    cheapest_spot = cheapest.getJSONObject("spot");
                    cheapest_price = cheapest_spot.getString("price_minute");
                    cheapest_lat = cheapest_spot.getString("latitude");
                    cheapest_long = cheapest_spot.getString("longitude");
                    cheapest_id = cheapest_spot.getString("_id");
                    final String whole_string2 = "cheapest: " + cheapest_id + "\ndistance: " + cheapest_distance + "\n" +
                            "lat: " + cheapest_lat + "\nlong: " + cheapest_long + "\nprice: " +
                            cheapest_price;
                    cheapest_lat_ = Double.parseDouble(cheapest_lat); // returns double primitive
                    cheapest_long_ = Double.parseDouble(cheapest_long);
                    cheapest_available = cheapest_spot.getBoolean("available");

                    best = response_body.getJSONObject("best");
                    best_distance = best.getString("distance");
                    best_spot = best.getJSONObject("spot");
                    best_price = best_spot.getString("price_minute");
                    best_lat = best_spot.getString("latitude");
                    best_long = best_spot.getString("longitude");
                    best_id = best_spot.getString("_id");
                    final String whole_string3 = "best: " + best_id + "\ndistance: " + best_distance + "\n" +
                            "lat: " + best_lat + "\nlong: " + best_long + "\nprice: " +
                            best_price;
                    best_lat_ = Double.parseDouble(best_lat); // returns double primitive
                    best_long_ = Double.parseDouble(best_long);
                    best_available = best_spot.getBoolean("available");
                    Handler mainHandler = new Handler(getApplicationContext().getMainLooper());
                    Runnable myRunnable = new Runnable() {
                        @Override
                        public void run() {
                            addMarker();
                        } // This is your code
                    };
                    mainHandler.post(myRunnable);

//                    Intent intent = new Intent(mapActivity.this,reservationActivity.class);
//                    startActivity(intent);
//
                    //mMap.addMarker(new MarkerOptions().position(new LatLng(45.494447,-73.579582)).title("Closest Parking"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("TAG", "\n.\n.\n.\n.\n.\n.\n.\n.\n.\n.\n.\n.\n.\n.\n.\n.\n.\n.");

            }
        });
    }

    public void addMarker() {
        a = mMap.addMarker(new MarkerOptions().position(new LatLng(cheapest_lat_, cheapest_long_)).title("Cheapest Parking").snippet("Price: " + cheapest_price + " $/min" + " Distance: " + cheapest_distance + "km"));
        b = mMap.addMarker(new MarkerOptions().position(new LatLng(closest_lat_, closest_long_)).title("Closest Parking").snippet("Price: " + closest_price + " $/min" + " Distance: " + closest_distance + "km"));
        c = mMap.addMarker(new MarkerOptions().position(new LatLng(best_lat_, best_long_)).title("Third Option").snippet("Price: " + best_price + " $/min" + " Distance: " + best_distance + "km"));

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnMyLocationButtonClickListener(onMyLocationButtonClickListener);
        mMap.setOnMyLocationClickListener(onMyLocationClickListener);
        //mMap.addMarker(new MarkerOptions().position(new LatLng(best_lat_,best_long_)).title("Third Option").snippet("Price: "+best_price+" $/min"+ " Distance: "+best_distance+"km"));
        // mMap.addMarker(new MarkerOptions().position(new LatLng(45.494447,-73.579582)).title("Closest Parking"));
        //mMap.addMarker(new MarkerOptions().position(new LatLng(45.494447,-73.579582)).title("Closest Parking"));

        enableMyLocationIfPermitted();

        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setMinZoomPreference(11);
        //setPoiClick(googleMap);
    }

    private void enableMyLocationIfPermitted() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else if (mMap != null) {
            mMap.setMyLocationEnabled(true);
        }
    }

    private void showDefaultLocation() {
        Toast.makeText(this, "Location permission not granted, " +
                        "showing default location",
                Toast.LENGTH_SHORT).show();
        LatLng redmond = new LatLng(47.6739881, -122.121512);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(redmond));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    enableMyLocationIfPermitted();
                } else {
                    showDefaultLocation();
                }
                return;
            }

        }
    }

    private GoogleMap.OnMyLocationButtonClickListener onMyLocationButtonClickListener =
            new GoogleMap.OnMyLocationButtonClickListener() {
                @Override
                public boolean onMyLocationButtonClick() {
                    mMap.setMinZoomPreference(15);
                    return false;
                }
            };

    private GoogleMap.OnMyLocationClickListener onMyLocationClickListener =
            new GoogleMap.OnMyLocationClickListener() {
                @Override
                public void onMyLocationClick(@NonNull Location location) {

                    mMap.setMinZoomPreference(12);

                    CircleOptions circleOptions = new CircleOptions();
                    circleOptions.center(new LatLng(location.getLatitude(),
                            location.getLongitude()));

                    circleOptions.radius(200);
                    circleOptions.fillColor(Color.RED);
                    circleOptions.strokeWidth(6);

                    mMap.addCircle(circleOptions);
                    postBody = "{\"lat\": " + location.getLatitude() + ", \"long\": " + location.getLongitude() + "}";
                }
            };


    public void setPoiClick(final GoogleMap map) {

        map.setOnPoiClickListener(new GoogleMap.OnPoiClickListener() {
            @Override
            public void onPoiClick(PointOfInterest poi) {
                Marker poiMarker = mMap.addMarker(new MarkerOptions()
                        .position(poi.latLng)
                        .title(poi.name));
                poiMarker.showInfoWindow();

            }

        });


    }


    LocationListener locationListenerGPS = new LocationListener() {
        @Override
        public void onLocationChanged(android.location.Location location) {
            userLatitude = location.getLatitude();
            userLongitude = location.getLongitude();
            //postBody = "{\"lat\": " + userLatitude + ", \"long\": " + userLongitude + "}";
            String msg = "New Latitude: " + userLatitude + "New Longitude: " + userLongitude;
            Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        isLocationEnabled();


    }

    @Override
    protected void onRestart() {
        super.onRestart();

        //mMap.clear();
        //Bundle extras = getIntent().getExtras();
        //intent_string = extras.getString("intent_string");
//        Log.e(" intent_string " , intent_string);
        //Toast.makeText(getApplicationContext(), reservation_id, Toast.LENGTH_SHORT).show();

        try {
            if (intent_string.equals(CHEAPEST_INTENT)) {
    //            //REMOVE THE OTHER TWO
    //            //marke.setVisible(false);
                click4.setVisibility(View.VISIBLE);
                click.setVisibility(View.INVISIBLE);
                startTimer();
                b.remove();
                c.remove();
            } else if (intent_string.equals(CLOSEST_INTENT)) {
    //            //REMOVE THE OTHER TWO
                click4.setVisibility(View.VISIBLE);
                click.setVisibility(View.INVISIBLE);
                startTimer();
                a.remove();
                c.remove();
            } else {
                //REMOVE THE OTHER TWO
                click4.setVisibility(View.VISIBLE);
                click.setVisibility(View.INVISIBLE);
                startTimer();
                a.remove();
                b.remove();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void startTimer() {

        if (!timerRunning) {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Do something after 100ms
                   // Toast.makeText(mContext, "timer function working", Toast.LENGTH_LONG).show();
                   // Toast.makeText(mContext, String.valueOf(has_arrived), Toast.LENGTH_LONG).show();


                    if (has_arrived == false) {
                        click.setVisibility(View.VISIBLE);
                        click4.setVisibility(View.INVISIBLE);
                        click3.setVisibility(View.INVISIBLE);
                        new AlertDialog.Builder(mContext)
                                .setTitle("Reservation Cancelled")
                                .setMessage("Ten minutes are up, Please make another reservation")

                                // Specifying a listener allows you to take an action before dismissing the dialog.
                                // The dialog is automatically dismissed when a dialog button is clicked.
                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // Continue with delete operation
                                    }
                                })

                                // A null listener allows the button to dismiss the dialog and take no further action.
                                //.setNegativeButton(android.R.string.no, null)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                     //   Toast.makeText(getApplicationContext(), reservation_id, Toast.LENGTH_SHORT).show();
                        String cancel_URL = "http://ec2-52-15-220-86.us-east-2.compute.amazonaws.com:8000/reservation?reservationId=" + reservation_id;
                        String putBody_ = "{\"cancelled\": true}";
                        try {
                            putReservation(cancel_URL, putBody_);


                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        a.remove();
                        b.remove();
                        c.remove();
                        if (intent_string.equals(CHEAPEST_INTENT)) {
//            //REMOVE THE OTHER TWO
                            //whenAsynchronousGetRequest_thenCorrect(cheapest_id);
                            String cheapest_URL = "http://ec2-52-15-220-86.us-east-2.compute.amazonaws.com:8000/spot?spotId=" + cheapest_id;
                            String putBody = "{\"available\": true}";
                            try {
                                putReservation(cheapest_URL, putBody);


                            } catch (IOException e) {
                                e.printStackTrace();
                            }
//
                        } else if (intent_string.equals(CLOSEST_INTENT)) {
//            //REMOVE THE OTHER TWO
                            //whenAsynchronousGetRequest_thenCorrect(closest_id);

                            String closest_URL = "http://ec2-52-15-220-86.us-east-2.compute.amazonaws.com:8000/spot?spotId=" + closest_id;
                            String putBody = "{\"available\": true}";
                            try {
                                putReservation(closest_URL, putBody);


                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            //REMOVE THE OTHER TWO
                            //whenAsynchronousGetRequest_thenCorrect(best_id);
                            String best_URL = "http://ec2-52-15-220-86.us-east-2.compute.amazonaws.com:8000/spot?spotId=" + best_id;
                            String putBody = "{\"available\": true}";
                            try {

                                putReservation(best_URL, putBody);


                            } catch (IOException e) {
                                e.printStackTrace();
                            }


                        }


                    }


                }

            }, 60000); // should be 100 * 1000 * 60 * 10 for 10 min" />}
        }



    }


    private void isLocationEnabled() {

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
            alertDialog.setTitle("Enable Location");
            alertDialog.setMessage("Your locations setting is not enabled. Please enabled it in settings menu.");
            alertDialog.setPositiveButton("Location Settings", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                }
            });
            alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog alert = alertDialog.create();
            alert.show();
        } else {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
            alertDialog.setTitle("Confirm Location");
            alertDialog.setMessage("Your Location is enabled, please enjoy");
            alertDialog.setNegativeButton("Back to interface", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog alert = alertDialog.create();
            alert.show();
        }
    }


    public void whenAsynchronousGetRequest_thenCorret(String parkingId) {
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
                    throws IOException {

                if (response.code() == 200) {
                    try (ResponseBody responseBody = response.body()) {

                        String jsonData = response.body().string();
                        JSONObject Jobject = new JSONObject(jsonData);
                        String start_time = Jobject.getString("start_time");
                        String cancelled = Jobject.getString("cancelled");
                        String customer = Jobject.getString("customer");
                        String spot = Jobject.getString("spot");

                        final String final_string = "Start time: " + start_time;


                        Handler mainHandler = new Handler(Looper.getMainLooper());
                        Runnable myRunnable = new Runnable() {
                            @Override
                            public void run() {

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
                Log.e(" responseCode ", String.valueOf(response.code()));

            }
        });
    }

    public void whenAsynchronousGetRequest_thenCorrect() {
        OkHttpClient client = new OkHttpClient();

        String selectedId;


        if (intent_string.equals(CHEAPEST_INTENT)) {
//            //REMOVE THE OTHER TWO
            //whenAsynchronousGetRequest_thenCorrect(cheapest_id);
            String cheapest_URL = "http://ec2-52-15-220-86.us-east-2.compute.amazonaws.com:8000/spot?spotId=" + cheapest_id;
          selectedId=cheapest_URL;
//
        } else if (intent_string.equals(CLOSEST_INTENT)) {
//            //REMOVE THE OTHER TWO
            //whenAsynchronousGetRequest_thenCorrect(closest_id);

            String closest_URL = "http://ec2-52-15-220-86.us-east-2.compute.amazonaws.com:8000/spot?spotId=" + closest_id;
            selectedId=closest_URL;
        } else {
            //REMOVE THE OTHER TWO
            //whenAsynchronousGetRequest_thenCorrect(best_id);
            String best_URL = "http://ec2-52-15-220-86.us-east-2.compute.amazonaws.com:8000/spot?spotId=" + best_id;
            selectedId=best_URL;
        }

        Request request = new Request.Builder().url(selectedId).build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            public void onResponse(Call call, Response response)
                    throws IOException {

                if (response.code() == 200) {
                    try (ResponseBody responseBody = response.body()) {
                        String jsonData = response.body().string();
                        JSONObject Jobject = new JSONObject(jsonData);
                        occupied = Jobject.getBoolean("occupied");
                        number=    Jobject.getInt("number");
                        //reservation_cancelled= JSONOb






                        if (occupied == false) {




                            Handler mainHandler = new Handler(getApplicationContext().getMainLooper());

                            Runnable myRunnable = new Runnable() {
                                @Override
                                public void run() {
                                    // Toast.makeText(mContext, String.valueOf(occupied), Toast.LENGTH_LONG).show();
                                    Log.e(" occupied" , String.valueOf(occupied));
                                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
                                    alertDialog.setTitle("Alert");
                                    alertDialog.setMessage("We haven't detected your car. Please arrive at spot --- and press arrival button again ")
                                            .setIcon(android.R.drawable.ic_dialog_alert)
                                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    // Continue with delete operation
                                                }
                                            })
                                            .show();

                                } // This is your code
                            };
                            mainHandler.post(myRunnable);

                        }


                        else{


                            Handler mainHandler = new Handler(getApplicationContext().getMainLooper());

                            Runnable myRunnable = new Runnable() {
                                @Override
                                public void run() {
                                    // Toast.makeText(mContext, String.valueOf(occupied), Toast.LENGTH_LONG).show();
                                    Log.e(" occupied" , String.valueOf(occupied));
                                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
                                    alertDialog.setTitle("Alert");
                                    alertDialog.setMessage("Great. Are you in spot "+number+"?")
                                            .setIcon(android.R.drawable.ic_dialog_alert)
                                            .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    // Continue with delete operation
                                                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
                                                    alertDialog.setTitle("Billing Period Started");
                                                    alertDialog.setMessage("Enjoy").show();
                                                    has_arrived=true;
                                                    click.setVisibility(View.INVISIBLE);
                                                    click4.setVisibility(View.INVISIBLE);
                                                    try {
                                                        Log.e("test",reservation_id);
                                                        String postBody__ = "{\"res_id\":\"" +reservation_id+ "\"}";
                                                        purchaseRequest("http://ec2-52-15-220-86.us-east-2.compute.amazonaws.com:8000/purchase",postBody__);

                                                    } catch (IOException e) {
                                                        e.printStackTrace();
                                                    }

                                                }
                                            })
                                            .setNegativeButton("no", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    // Continue with delete operation
                                                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
                                                    alertDialog.setTitle("Alert");
                                                    alertDialog.setMessage("Your spot seems occuppied. Cancelling reservation. Please make another").show();
                                                    String cancel_URL = "http://ec2-52-15-220-86.us-east-2.compute.amazonaws.com:8000/reservation?reservationId=" + reservation_id;
                                                    String putBody_ = "{\"cancelled\": true}";
                                                    try {
                                                        putReservation(cancel_URL, putBody_);


                                                    } catch (IOException e) {
                                                        e.printStackTrace();
                                                    }
                                                    click.setVisibility(View.VISIBLE);
                                                    click3.setVisibility(View.INVISIBLE);
                                                    //click4.setVisibility(View.VISIBLE);
                                                    if (intent_string.equals(CHEAPEST_INTENT)) {
//            //REMOVE THE OTHER TWO
                                                        a.remove();
                                                        //whenAsynchronousGetRequest_thenCorrect(cheapest_id);
                                                        String cheapest_URL = "http://ec2-52-15-220-86.us-east-2.compute.amazonaws.com:8000/spot?spotId=" + cheapest_id;
                                                        String putBody = "{\"available\": true}";
                                                        try {
                                                            putReservation(cheapest_URL, putBody);


                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        }
//
                                                    } else if (intent_string.equals(CLOSEST_INTENT)) {
//            //REMOVE THE OTHER TWO
                                                        //whenAsynchronousGetRequest_thenCorrect(closest_id);
                                                        b.remove();
                                                        String closest_URL = "http://ec2-52-15-220-86.us-east-2.compute.amazonaws.com:8000/spot?spotId=" + closest_id;
                                                        String putBody = "{\"available\": true}";
                                                        try {
                                                            putReservation(closest_URL, putBody);


                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        }
                                                    } else {
                                                        //REMOVE THE OTHER TWO
                                                        //whenAsynchronousGetRequest_thenCorrect(best_id);
                                                       c.remove();
                                                        String best_URL = "http://ec2-52-15-220-86.us-east-2.compute.amazonaws.com:8000/spot?spotId=" + best_id;
                                                        String putBody = "{\"available\": true}";
                                                        try {

                                                            putReservation(best_URL, putBody);


                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        }


                                                    }


                                                }
                                            })
                                            .show();

                                } // This is your code
                            };
                            mainHandler.post(myRunnable);


                        }
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
    public void purchaseRequest(String postUrl, String postBody) throws IOException {
        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, postBody);
        Request request = new Request.Builder()
                .url(postUrl)
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                Log.d("TAG", "\n.\n.\n.\n.\n.\n.\n.\n.\n.\n.\n.\n.\n.\n.\n.\n.\n.\n.");

            }
        });
    }



}
