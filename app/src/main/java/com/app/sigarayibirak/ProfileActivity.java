package com.app.sigarayibirak;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.app.sigarayibirak.databinding.ActivityProfileBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference ref;
    ActivityProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Binding işlemi yapıyoruz.
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Database bağlantısını çağırıyoruz
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("Users");
        mAuth = FirebaseAuth.getInstance();

        //Database'imizden mevcut kullanıcıya ait verilerimizi çekiyoruz.
        ref.child(mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {

                    //Databaseden verilerin gelmesi için gerekli kodumuz.
                    DataSnapshot ds = task.getResult();

                    //Gelen verileri değişkenlere atıyoruz.
                    String mail = ds.child("eMail").getValue().toString();
                    String name = ds.child("name").getValue().toString();
                    String age = ds.child("age").getValue().toString();

                    binding.profileNameArea.setText(name);
                    binding.profileEmailArea.setText(mail);
                    binding.profileAgeArea.setText(age);
                    binding.profileTextUserName.setText("Merhaba, " + name.toUpperCase());
                }
                else {
                    Toast.makeText(ProfileActivity.this,"Bilgiler yüklenirken hata meydana geldi.",Toast.LENGTH_LONG)
                            .show();
                }
            }
        });

        //Geri gelme butonu için yazdığımız listener
        binding.profileBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go = new Intent(ProfileActivity.this, MenuActivity.class);
                startActivity(go);
                finish();
            }
        });

    }
}