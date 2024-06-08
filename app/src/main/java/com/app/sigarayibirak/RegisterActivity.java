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
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {

    EditText editTextUserName, editTextEmail, editTextPassword, editTextAge;
    Button register;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference ref;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editTextEmail = findViewById(R.id.registerEmail);
        editTextPassword = findViewById(R.id.registerPassword);
        editTextAge = findViewById(R.id.registerAge);
        editTextUserName = findViewById(R.id.registerUserName);
        register = findViewById(R.id.loginBtnRegister);

        //Kayıt sayfamızda kayıt ol tuşuna basmamızı bekleyen listener.
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Kullanıcıyı kayıt edip Firebase Database'de verilerini tutmak için database objesini çağırıyoruz.
                database = FirebaseDatabase.getInstance();
                //Kullanacağımız database'i seçiyoruz.
                ref = database.getReference("Users");

                //Kullanıcının girdiği verileri string tipine dönüştürüyoruz.
                String email, password, userName, age;
                email = editTextEmail.getText().toString();
                password = editTextPassword.getText().toString();
                userName = editTextUserName.getText().toString();
                age = editTextAge.getText().toString();

                //Girilen verierin boş olup olmadığını kontrol ediyoruz.
                if (TextUtils.isEmpty(email) || !email.contains("mail")){
                    Toast.makeText(RegisterActivity.this, "E-mail giriniz.", Toast.LENGTH_SHORT)
                            .show();
                    return;
                }
                if (TextUtils.isEmpty(password) || password.length() < 7){
                    Toast.makeText(RegisterActivity.this, "Şifre giriniz, en az 7 karakterli olmalı.", Toast.LENGTH_SHORT)
                            .show();
                    return;
                }
                if (TextUtils.isEmpty(userName) || userName.length() < 4){
                    Toast.makeText(RegisterActivity.this, "Kullanıcı adı giriniz, en az 4 karakterli olmalı.", Toast.LENGTH_SHORT)
                            .show();
                    return;
                }
                if (TextUtils.isEmpty(age) || (Integer.parseInt(age) < 18)){
                    Toast.makeText(RegisterActivity.this, "Yaş giriniz, 18'den büyük olmalı.", Toast.LENGTH_SHORT)
                            .show();
                    return;
                }

                //Kayıt işlemi için Firebase Authentication nesnesini çağırıyoruz.
                mAuth = FirebaseAuth.getInstance();

                //Kayıt işlemi için Firebase Authentication tarafından sağlanan metodu kullanıyoruz.
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(RegisterActivity.this, "Kayıt başarılı.", Toast.LENGTH_SHORT)
                                            .show();

                                    //Girilen kullanıcı bilgilerinin database'imize aktarılması için oluşturduğumuz User sınıfının nesnesini yaratıyoruz.
                                    User user = new User(userName,email,age,password);
                                    //Firebase Authentication tarafından sağlanan benzersiz ID sayesinde User tablomuza kullanıcımızı ekliyoruz.
                                    ref.child(mAuth.getCurrentUser().getUid()).setValue(user);

                                    //Bu işlemlerden sonra giriş yapması için kullanıcıyı giriş sayfasına yönlendiriyoruz.
                                    Intent go = new Intent(RegisterActivity.this, LoginActivity.class);
                                    startActivity(go);
                                    finish();
                                }
                                else{
                                    Toast.makeText(RegisterActivity.this, "Kayıt başarısız.", Toast.LENGTH_SHORT)
                                            .show();
                                }
                            }
                        });

            }
        });

    }
}