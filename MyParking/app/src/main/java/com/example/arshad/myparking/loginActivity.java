package com.example.arshad.myparking;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class loginActivity extends AppCompatActivity {
    private static final String LOCAL_URL = "http://ec2-52-15-220-86.us-east-2.compute.amazonaws.com:8000/login";
    private String user_name;
    private String pwrd;
    private String url_send;
    public String user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final EditText username = (EditText) findViewById(R.id.userName);
        final EditText password = (EditText) findViewById(R.id.passWord);
        Button login=findViewById(R.id.login);
        TextView register=findViewById(R.id.Register);
        TextView forgotPassword = findViewById(R.id.passwordReset);
        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                user_name = username.getText().toString();
                pwrd = password.getText().toString();

                if (TextUtils.isEmpty(user_name)) {
                    Toast.makeText(getApplicationContext(), "Enter username!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(pwrd)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();

                }
                url_send = getLoginURL(LOCAL_URL, user_name, pwrd);
                whenAsynchronousGetRequest_thenCorrect();
            }
        });


        register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                Intent intent = new Intent(loginActivity.this,registerActivity.class);
                startActivity(intent);
            }
        });


        forgotPassword.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                Intent intent = new Intent(loginActivity.this,resetPasswordActivity.class);
                startActivity(intent);
            }
        });


    }

    protected String getLoginURL(String url_base, String username, String password) {
                String url = url_base + "?user_name=" + username + "&pwrd=" + password;
                return url;
    }


    public void whenAsynchronousGetRequest_thenCorrect() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url_send).build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            public void onResponse(Call call, Response response)
                    throws IOException
            {

                if (response.code() == 200){
                    try (ResponseBody responseBody = response.body()) {
                        String jsonData = response.body().string();
                        JSONObject Jobject = new JSONObject(jsonData);
                        String id = Jobject.getString("_id");
                        //response_.setText(id);

                        Intent intent = new Intent(loginActivity.this, mapActivity.class);
                        intent.putExtra("userId", id);
                        startActivity(intent);
                        finish();

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





}
