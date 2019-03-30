package com.example.arshad.myparking;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class resetPasswordActivity extends AppCompatActivity {

    private EditText password,confirmPassword;
    private String pass,con_pass;
    private Button reset_;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        password = (EditText) findViewById(R.id.passReset);
        confirmPassword = (EditText) findViewById(R.id.conpassReset);
        reset_ =(Button) findViewById(R.id.Reset);

        reset_.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                pass = password.getText().toString();
                con_pass=confirmPassword.getText().toString();

                if (con_pass.equals(pass)) {
                    //do on click put request
                    Toast.makeText(getApplicationContext(), "Password  match!", Toast.LENGTH_SHORT).show();

                }
                else{
                    Toast.makeText(getApplicationContext(), "Password do not match!", Toast.LENGTH_SHORT).show();

                }
           }

    });
}

}