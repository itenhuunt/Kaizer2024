package team.diamond.kaizer.storiesFullShema;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

import team.diamond.kaizer.kaizerActivity;
import team.diamond.kaizer.R;
import team.diamond.kaizer.addStories;
//import StoriesAdapter;
//import StoriesModel;

public class readStories extends AppCompatActivity {


    RecyclerView rv_all_stories;
    StoriesAdapter storiesAdapter;
    FloatingActionButton addstorybtn;

    private SharedPreferences user_name_shared_preferences;
    public String inkognito;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.read_stories);

        user_name_shared_preferences = getSharedPreferences("teen_pref", MODE_PRIVATE); //обяъвляем приватный режим для ОЧКОВ + прописываем ИМЯ в xml (чxml так и будет называться) + приватный режим
        inkognito = user_name_shared_preferences.getString("teen_name", inkognito);// пишем ВПЕРЕДИ  т.к. код исполняется по порядку + в этом xml опять пишем наши очки под именем которое задаем save_key_count


        hooks();

        rv_all_stories.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<StoriesModel> options =
                new FirebaseRecyclerOptions.Builder<StoriesModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("рассказы маленьких сучек2").child(inkognito), StoriesModel.class)
                        .build();


        storiesAdapter = new StoriesAdapter(options);
        rv_all_stories.setAdapter(storiesAdapter);

        addstorybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(readStories.this, addStories.class); // try = попытка
                startActivity(intent);
                finish();

            }
        });


    }

    private void hooks() {
        rv_all_stories = findViewById(R.id.rv_all_stories);
        addstorybtn = findViewById(R.id.addstorybtn);
    }


    @Override
    protected void onStart() {
        super.onStart();
        storiesAdapter.startListening();
    }


    @Override
    protected void onStop() {
        super.onStop();
        storiesAdapter.stopListening();
    }



    public void onBackPressed() {
        try {
            Intent intent = new Intent(readStories.this, kaizerActivity.class); // try = попытка
            startActivity(intent);
            finish();
        } catch (Exception e) {  // catch = ловить
        }
    }


}