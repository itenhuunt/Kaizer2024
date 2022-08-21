package team.diamond.kaizer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class profileId extends AppCompatActivity {

    private SharedPreferences user_name_shared_preferences;
    public String inkognito;

    TextView profile_id;
    LinearLayout foto_public, stories;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_id);

        user_name_shared_preferences = getSharedPreferences("teen_pref", MODE_PRIVATE); //обяъвляем приватный режим для ОЧКОВ + прописываем ИМЯ в xml (чxml так и будет называться) + приватный режим
        inkognito = user_name_shared_preferences.getString("teen_name", inkognito);// пишем ВПЕРЕДИ  т.к. код исполняется по порядку + в этом xml опять пишем наши очки под именем которое задаем save_key_count


        find();


        profile_id.setText(inkognito);


        foto_public.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(profileId.this, FotoPublic.class);
                startActivity(intent);
                finish();
            }
        });

        stories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(profileId.this, stories.class);
                startActivity(intent);
                finish();
            }
        });


    }

    private void find() {
        profile_id = findViewById(R.id.profile_id);
        foto_public = findViewById(R.id.foto_public);
        stories = findViewById(R.id.stories);

    }


    public void onBackPressed() {
        try {
            Intent intent = new Intent(profileId.this, MainActivity.class); // try = попытка
            startActivity(intent);
            finish();
        } catch (Exception e) {  // catch = ловить
        }
    }


}