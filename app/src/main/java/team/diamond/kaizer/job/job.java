package team.diamond.kaizer.job;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

import team.diamond.kaizer.R;
import team.diamond.kaizer.addStories;
import team.diamond.kaizer.kaizerActivity;
import team.diamond.kaizer.storiesFullShema.StoriesAdapter;
import team.diamond.kaizer.storiesFullShema.StoriesModel;
//import StoriesAdapter;
//import StoriesModel;

public class job extends AppCompatActivity {


    RecyclerView jobrv;
    JobAdapter jobAdapter;
    FloatingActionButton addjob;

    private SharedPreferences user_name_shared_preferences;
    public String inkognito;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job);

        user_name_shared_preferences = getSharedPreferences("teen_pref", MODE_PRIVATE); //обяъвляем приватный режим для ОЧКОВ + прописываем ИМЯ в xml (чxml так и будет называться) + приватный режим
        inkognito = user_name_shared_preferences.getString("teen_name", inkognito);// пишем ВПЕРЕДИ  т.к. код исполняется по порядку + в этом xml опять пишем наши очки под именем которое задаем save_key_count

        hooks();


        jobrv.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<jobModel> options =
                new FirebaseRecyclerOptions.Builder<jobModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("job"), jobModel.class)
                        .build();


        jobAdapter = new JobAdapter(options);
        jobrv.setAdapter(jobAdapter);


        addjob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(job.this, jobAdd.class); // try = попытка
                startActivity(intent);
                finish();
            }
        });


    }


    @Override
    protected void onStart() {
        super.onStart();
        jobAdapter.startListening();
    }


    @Override
    protected void onStop() {
        super.onStop();
        jobAdapter.stopListening();
    }


    private void hooks() {
        jobrv = findViewById(R.id.jobrv);
        addjob = findViewById(R.id.addjob);
    }


    public void onBackPressed() {
        try {
            Intent intent = new Intent(job.this, kaizerActivity.class); // try = попытка
            startActivity(intent);
            finish();
        } catch (Exception e) {  // catch = ловить
        }
    }


}