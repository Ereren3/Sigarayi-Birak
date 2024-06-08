package com.app.sigarayibirak;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onStart() {
        mAuth = FirebaseAuth.getInstance();
        super.onStart();
        //mAuth.signOut();
        if (mAuth.getCurrentUser() == null){
            Intent go = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(go);
            finish();
        }
        else{
            Intent go = new Intent(MainActivity.this, MenuActivity.class);
            startActivity(go);
            finish();
        }
    }
}