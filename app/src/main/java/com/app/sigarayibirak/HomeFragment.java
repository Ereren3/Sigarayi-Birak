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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class HomeFragment extends Fragment {

    FragmentHomeBinding binding;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference ref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

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
                if (task.isSuccessful()) {

                    DataSnapshot ds = task.getResult();

                    String dayCount = ds.child("dayCount").getValue().toString();
                    String date = ds.child("date").getValue().toString();

                    binding.dayCount.setText(dayCount);
                    binding.savedTimeCount.setText(String.valueOf((Integer.parseInt(dayCount)+1) * 15 * 5));
                    binding.savedMoneyCount.setText(String.valueOf((Integer.parseInt(dayCount)+1) * 45));
                    binding.cigaretteCount.setText(String.valueOf((Integer.parseInt(dayCount)+1) * 15));

                }
                else {

                }
            }
        });

        return binding.getRoot();
    }
}