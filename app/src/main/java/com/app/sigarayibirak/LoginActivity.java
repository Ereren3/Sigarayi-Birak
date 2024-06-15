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

import com.app.sigarayibirak.databinding.ActivityLoginBinding;
import com.app.sigarayibirak.databinding.ActivityMenuBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    ActivityLoginBinding binding;

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

        //Binding işlemi yapıyoruz.
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Kayıt sayfasına gitmek için kullandığımız listener.
        binding.loginRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register = new Intent( LoginActivity.this, RegisterActivity.class);
                startActivity(register);
                finish();
            }
        });

        //Login işlemi gerçekleştirmek için kullandığımız listener.
        binding.loginBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Kullanıcının girdiği verileri string tipine dönüştürüp oluşturduğumuz referanslara atıyoruz.
                String email, password;
                email = binding.loginEmail.getText().toString();
                password = binding.loginPassword.getText().toString();

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


    //Kullanıcının daha önceden giriş yapıp yapmadığını kontrol eden metot.
    @Override
    protected void onStart() {
        mAuth = FirebaseAuth.getInstance();
        super.onStart();
        //mAuth.signOut();
        if (mAuth.getCurrentUser() != null){
            Intent go = new Intent(LoginActivity.this, MenuActivity.class);
            startActivity(go);
            finish();
        }
    }

}