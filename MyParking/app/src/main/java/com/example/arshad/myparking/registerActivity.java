package com.example.arshad.myparking;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class registerActivity extends AppCompatActivity {
    private static final String LOCAL_URL = "http://ec2-52-15-220-86.us-east-2.compute.amazonaws.com:8000/register";
    private EditText firstName;
    private EditText lastName;
    private EditText username;
    private EditText confirmPassword;
    private EditText password;
    private EditText email;
    private EditText phoneNumber;
    private Button btnSignup;
    private String user_name;
    private String pwrd;
    private String first_Name;
    private String last_Name;
    private String con_pwrd;
    private String e_mail;
    private String p_num;
    private String url_send;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        firstName = (EditText) findViewById(R.id.firstName);
        lastName = (EditText) findViewById(R.id.lastName);
        username = (EditText) findViewById(R.id.user);
        password = (EditText) findViewById(R.id.pass);
        confirmPassword = (EditText) findViewById(R.id.conPass);
        email = (EditText) findViewById(R.id.email);
        phoneNumber = (EditText) findViewById(R.id.phone);
        btnSignup = (Button) findViewById(R.id.signup);
        btnSignup.setOnClickListener(new View.OnClickListener() {
                                         public void onClick(View v) {
                                             first_Name=firstName.getText().toString();
                                             last_Name=lastName.getText().toString();
                                             user_name = username.getText().toString();
                                             pwrd = password.getText().toString();
                                             con_pwrd=confirmPassword.getText().toString();
                                             e_mail=email.getText().toString();
                                             p_num=phoneNumber.getText().toString();


                                             if (TextUtils.isEmpty(user_name)) {
                                                 Toast.makeText(getApplicationContext(), "Enter username!", Toast.LENGTH_SHORT).show();
                                                 return;
                                             }

                                             if (TextUtils.isEmpty(pwrd)) {
                                                 Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                                                 //return;
                                             }

                                             if (TextUtils.isEmpty(first_Name)) {
                                                 Toast.makeText(getApplicationContext(), "Enter First Name!", Toast.LENGTH_SHORT).show();
                                                 return;
                                             }

                                             if (TextUtils.isEmpty(last_Name)) {
                                                 Toast.makeText(getApplicationContext(), "Enter Last Name!", Toast.LENGTH_SHORT).show();
                                                 //return;
                                             }

                                             if (TextUtils.isEmpty(con_pwrd)) {
                                                 Toast.makeText(getApplicationContext(), "Enter password confirmation!", Toast.LENGTH_SHORT).show();
                                                 return;
                                             }

                                             if (TextUtils.isEmpty(e_mail)) {
                                                 Toast.makeText(getApplicationContext(), "Enter email!", Toast.LENGTH_SHORT).show();
                                                 //return;
                                             }


                                             if (TextUtils.isEmpty(p_num)) {
                                                 Toast.makeText(getApplicationContext(), "Enter Phone Number!", Toast.LENGTH_SHORT).show();
                                                 //return;
                                             }

                                             if (con_pwrd.equals(pwrd)& !TextUtils.isEmpty(p_num)& !TextUtils.isEmpty(first_Name)&!TextUtils.isEmpty(last_Name)&!TextUtils.isEmpty(p_num)&!TextUtils.isEmpty(e_mail)&!TextUtils.isEmpty(user_name))  {
                                                 //String postBody = "{\"user_name\": \"" + user_name + "\", \"password\": \"" + pwrd + "\"}";
                                                 String postBody = "{\"first_name\": \"" + first_Name + "\", \"last_name\":\""+ last_Name +"\"," +
                                                                    "\"phone\": \"" + p_num + "\", \"email\":\"" + e_mail +"\", " +
                                                                    "\"user_name\":\"" + user_name +"\",\"password\":\"" + pwrd +"\"}";
                                                 try {
                                                     postRequest(LOCAL_URL, postBody);

                                                     Intent intent = new Intent(registerActivity.this, loginActivity.class);
                                                     //intent.putExtra("userId", id);
                                                     startActivity(intent);
                                                     finish();
                                                     Toast.makeText(getApplicationContext(), "Account Created", Toast.LENGTH_SHORT).show();

                                                 } catch (IOException e) {
                                                     e.printStackTrace();
                                                 }
                                             }

                                             else {
                                                 Toast.makeText(getApplicationContext(), "Password do not match!", Toast.LENGTH_SHORT).show();
                                             }

                                         }
                                     }
        );


    }


    void postRequest(String postUrl,String postBody) throws IOException {

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
                Log.d("TAG",response.body().string());
            }
        });
    }



}

