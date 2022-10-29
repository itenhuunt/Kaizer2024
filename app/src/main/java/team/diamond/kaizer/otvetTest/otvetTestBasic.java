package team.diamond.kaizer.otvetTest;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import team.diamond.kaizer.kaizerActivity;
import team.diamond.kaizer.R;

public class otvetTestBasic extends AppCompatActivity {

    RecyclerView list_all_belka;   //  лист всех белок которые зарешались
    DatabaseReference database;   //устанавливаем базу данных
    otvetAdapter AdaptedReply;    // прописываем адаптер
    ArrayList<otvetModel> list2;   //  сама схема (короткая)


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.answer_belka);

        list_all_belka = findViewById(R.id.list_all_belka);
        database = FirebaseDatabase.getInstance().getReference("QuestBelka"); // прописываем базу в реальном времени
        list_all_belka.setHasFixedSize(true);
        list_all_belka.setLayoutManager(new LinearLayoutManager(this));

        list2 = new ArrayList<>();
        AdaptedReply = new otvetAdapter(this, list2);
        list_all_belka.setAdapter(AdaptedReply);  //устанавливаем адаптер

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    otvetModel Reply_shema = dataSnapshot.getValue(otvetModel.class);
                    list2.add(Reply_shema);
                }
                AdaptedReply.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    private void hooks() {
    }


    public void onBackPressed() {
        try {
            Intent intent = new Intent(otvetTestBasic.this, kaizerActivity.class); // try = попытка
            startActivity(intent);
            finish();
        } catch (Exception e) {  // catch = ловить
        }
    }
}