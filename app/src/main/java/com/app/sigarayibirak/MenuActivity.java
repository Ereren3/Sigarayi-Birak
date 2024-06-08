package com.app.sigarayibirak;

import android.app.Activity;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.LayoutInflater;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.LayoutInflaterCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.app.sigarayibirak.databinding.ActivityMainBinding;
import com.app.sigarayibirak.databinding.ActivityMenuBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.internal.NavigationMenu;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MenuActivity extends AppCompatActivity {
    ActivityMenuBinding binding;
    FloatingActionButton profileBtn;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu);

        binding = ActivityMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        replaceFragment(new HomeFragment());
        binding.bottomNavigatonView.setBackground(null);

        //Menüdeki bastığımız butonun hangisi olduğunu ve ne işlem yaptığını seçtiğimiz listener.
        binding.bottomNavigatonView.setOnItemSelectedListener(item ->{
            //Home butonuna bastığımızda home fragmentini çağıran kod bloğu
            if (R.id.home == item.getItemId()){
                replaceFragment(new HomeFragment());
            }
            //Çıkış butonuna bastığımızda çıkış işlemini gerçekleştiren kod bloğu
            else if(R.id.logout == item.getItemId()){
                //Çıkış işlemi için Firebase Authentication nesnesini çağırıyoruz.
                mAuth = FirebaseAuth.getInstance();
                //Bize sunulan metodu kullanarak çıkış yapıyoruz.
                mAuth.signOut();

                Intent go = new Intent(MenuActivity.this, LoginActivity.class);
                startActivity(go);
                finish();
            }
            return true;
        });

        //Profil butonuna tıkladığında bizi profil sayfasına götüren listener.
        binding.profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profile = new Intent(MenuActivity.this, ProfileActivity.class);
                startActivity(profile);
                finish();
            }
        });

    }

    //Fragmentler arası geçiş sağlamak ve fragment çağırmak için kullandığımız metot.
    private void replaceFragment(Fragment fragment){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frame_layout, fragment);
        ft.commit();
    }

}