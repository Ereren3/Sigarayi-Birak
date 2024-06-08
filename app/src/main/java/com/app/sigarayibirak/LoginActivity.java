package com.app.sigarayibirak;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText editTextEmail, editTextPassword;
    Button register, login;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        register = findViewById(R.id.loginRegisterBtn);
        login = findViewById(R.id.loginBtnLogin);
        editTextEmail = findViewById(R.id.loginEmail);
        editTextPassword = findViewById(R.id.loginPassword);

        //Kayıt sayfasına gitmek için kullandığımız listener.
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register = new Intent( LoginActivity.this, RegisterActivity.class);
                startActivity(register);
                finish();
            }
        });

        //Login işlemi gerçekleştirmek için kullandığımız listener.
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Kullanıcının girdiği verileri string tipine dönüştürüp oluşturduğumuz referanslara atıyoruz.
                String email, password;
                email = editTextEmail.getText().toString();
                password = editTextPassword.getText().toString();

                //Verilerin girildiğinden emin olmak için kontrol ediyoruz.
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(LoginActivity.this, "E-posta boş olamaz.",Toast.LENGTH_SHORT)
                            .show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(LoginActivity.this,"Şifre boş olamaz.",Toast.LENGTH_SHORT)
                            .show();
                    return;
                }

                //Giriş işlemi için Firebase Authentication nesnesini çağırıyoruz.
                mAuth = FirebaseAuth.getInstance();

                //Firebase tarafından sağlanan giriş metodu.
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            Toast.makeText(LoginActivity.this,"Giriş başarılı.",Toast.LENGTH_SHORT)
                                    .show();
                            Intent go = new Intent(LoginActivity.this, MenuActivity.class);
                            startActivity(go);
                            finish();
                        }
                        else{
                            Toast.makeText(LoginActivity.this,"Giriş başarısız, tekrar deneyiniz.",Toast.LENGTH_SHORT)
                                    .show();
                        }

                    }
                });
            }
        });

    }
}