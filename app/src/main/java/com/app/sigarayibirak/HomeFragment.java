package com.app.sigarayibirak;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewbinding.ViewBinding;

import android.renderscript.ScriptGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.app.sigarayibirak.databinding.FragmentHomeBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class HomeFragment extends Fragment {
    FragmentHomeBinding binding;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference ref;
    Long distance;
    Date nowDate;
    DateFormat sdf;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Binding işlemi yapıyoruz.
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        //Database bağlantısı yapıp kullanacağımız database'in referansını veriyoruz.
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("Users");

        //Giriş yapan kullanıcıyı çağırmak için burada Firebase Authentication nesnesini çağırıyoruz.
        mAuth = FirebaseAuth.getInstance();


        // Ana ekranımızda, kullanıcıya ait verilerin database üzerinden çekilmesini sağlayarak gösteren metot.
        ref.child(mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                //Databaseden verilerin gelmesi için gerekli kodumuz.
                DataSnapshot ds = task.getResult();
                String userDateString = ds.child("date").getValue().toString();

                //Kullanıcının bırakma tarihi ile şimdiki tarih arasında geçen farkı almak için oluşturduğumuz kod bloğu
                nowDate = new Date();
                Date userDate;
                sdf = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    userDate = sdf.parse(userDateString);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                //Database'den gelen tarih ile şu an olunan tarihin farkını bulmak için kullandığımız kod.
                distance = (nowDate.getTime()-userDate.getTime()) / (1000 * 60 * 60 * 24) + 1;

                binding.dayCount.setText(String.valueOf(distance));
                binding.savedTimeCount.setText(String.valueOf((distance) * 15 * 5));
                binding.savedMoneyCount.setText(String.valueOf((distance) * 45));
                binding.cigaretteCount.setText(String.valueOf((distance) * 15));
            }
        });


        /*
        //Bu kod bloğu farklı bir amaçla test için yazılmıştır, işlevi uygulama bitince sona ermiştir.

        binding.dayCountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref.child(mAuth.getCurrentUser().getUid()).child("dayCount").setValue(String.valueOf((distance + 1)));
                distance = distance +1;
                binding.dayCount.setText(String.valueOf(distance));

                binding.savedTimeCount.setText(String.valueOf((distance + 1) * 15 * 5));
                binding.savedMoneyCount.setText(String.valueOf((distance + 1) * 45));
                binding.cigaretteCount.setText(String.valueOf((distance + 1) * 15));
            }
        });
         */

        //Kullanıcının ilerlemeyi sıfırlaması için oluşturduğumuz buton
        binding.resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Gün sayısı ve tarih bilgilerimizi sıfırlayıp database'imizi güncelliyoruz.
                ref.child(mAuth.getCurrentUser().getUid()).child("dayCount").setValue("1");
                ref.child(mAuth.getCurrentUser().getUid()).child("date").setValue(sdf.format(nowDate).toString());

                binding.dayCount.setText(String.valueOf(1));
                binding.savedTimeCount.setText(String.valueOf(15 * 5));
                binding.savedMoneyCount.setText(String.valueOf(45));
                binding.cigaretteCount.setText(String.valueOf(15));
            }
        });

        return binding.getRoot();
    }
}