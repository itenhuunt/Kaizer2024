package team.diamond.kaizer.foto;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

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

import team.diamond.kaizer.R;

public class showFoto extends AppCompatActivity {

    public String inkognito;
    private SharedPreferences user_name_shared_preferences;

    private RecyclerView recyclerView;
    private ArrayList<fotoModel> imageModelArrayList;
    private fotoAdapter recyclerImageAdapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        user_name_shared_preferences = getSharedPreferences("teen_pref", MODE_PRIVATE); //обяъвляем приватный режим для ОЧКОВ + прописываем ИМЯ в xml (чxml так и будет называться) + приватный режим
        inkognito = user_name_shared_preferences.getString("teen_name", inkognito);// пишем ВПЕРЕДИ  т.к. код исполняется по порядку + в этом xml опять пишем наши очки под именем которое задаем save_key_count


        recyclerView = findViewById(R.id.recyclerView);

//        recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this,2));
//        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(recyclerImageAdapter);

        imageModelArrayList = new ArrayList<>();

        clearAll();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(inkognito);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                clearAll();

                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    fotoModel imageModel = new fotoModel();
                    imageModel.setImageurl(snapshot.getValue().toString());

                    imageModelArrayList.add(imageModel);

                }

                recyclerImageAdapter = new fotoAdapter(getApplicationContext(),imageModelArrayList);
                recyclerView.setAdapter(recyclerImageAdapter);
                recyclerImageAdapter.notifyDataSetChanged();

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(showFoto.this, "Error" + error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }


    private void clearAll() {
        if (imageModelArrayList != null){

            imageModelArrayList.clear();

            if (recyclerImageAdapter != null){
                recyclerImageAdapter.notifyDataSetChanged();
            }
        }
        imageModelArrayList = new ArrayList<>();
    }



}