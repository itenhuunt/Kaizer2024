package team.diamond.kaizer.startTest;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import team.diamond.kaizer.R;
import team.diamond.kaizer.creatTest.testModel;

public class LoadingQuest extends AppCompatActivity {


    public static ArrayList<testModel> list;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_quest);

        list = new ArrayList<>();   // объявляем множество листов

        //         тут прописываем ТОЖЕ самое что на сайте
     //  databaseReference = FirebaseDatabase.getInstance().getReference("BasicTest");
        databaseReference = FirebaseDatabase.getInstance().getReference("random");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    testModel modelclass = dataSnapshot.getValue(testModel.class);
                    list.add(modelclass);
                }
                Intent intent = new Intent(LoadingQuest.this, startTest.class);
                startActivity(intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        //главная страница + код задержки через некоторое время
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //     startActivity(new Intent(MainActivity.this,universal_qestions.class));
            }
        }, 850);

    }
}