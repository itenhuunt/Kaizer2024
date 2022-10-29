package team.diamond.kaizer.allusers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import team.diamond.kaizer.R;
import team.diamond.kaizer.kaizerActivity;
import team.diamond.kaizer.otvetTest.otvetAdapter;
import team.diamond.kaizer.otvetTest.otvetModel;
import team.diamond.kaizer.profileId;

public class AllUsers extends AppCompatActivity {

    RecyclerView recently_rv;
    DatabaseReference database;
    allUserAdapter allUserAdapter;
    ArrayList<AllUserModel> list2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_users);

        hooks();

        database = FirebaseDatabase.getInstance().getReference("users"); // прописываем базу в реальном времени
        recently_rv.setHasFixedSize(true);
        recently_rv.setLayoutManager(new LinearLayoutManager(this));

        list2 = new ArrayList<>();
        allUserAdapter = new allUserAdapter(this, list2);
        recently_rv.setAdapter(allUserAdapter);  //устанавливаем адаптер

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    AllUserModel user_shema = dataSnapshot.getValue(AllUserModel.class);
                    list2.add(user_shema);
                }
                allUserAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


    private void hooks() {

        recently_rv = findViewById(R.id.recently_rv);
    }


    public void onBackPressed() {
        try {
            Intent intent = new Intent(AllUsers.this, kaizerActivity.class); // try = попытка
            startActivity(intent);
            finish();
        } catch (Exception e) {  // catch = ловить
        }
    }
}