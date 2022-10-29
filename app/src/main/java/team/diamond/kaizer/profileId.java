package team.diamond.kaizer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import team.diamond.kaizer.foto.FotoPublic;
import team.diamond.kaizer.otvetTest.youOtvetTest;
import team.diamond.kaizer.profile.profileBalance;

public class profileId extends AppCompatActivity {

    private SharedPreferences user_name_shared_preferences;
    public String inkognito;

    private SharedPreferences pay;
    int payments;


    TextView profile_id, balance;
    LinearLayout foto_public, stories, answerstest,profile_balance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_id);

        user_name_shared_preferences = getSharedPreferences("teen_pref", MODE_PRIVATE); //обяъвляем приватный режим для ОЧКОВ + прописываем ИМЯ в xml (чxml так и будет называться) + приватный режим
        inkognito = user_name_shared_preferences.getString("teen_name", inkognito);// пишем ВПЕРЕДИ  т.к. код исполняется по порядку + в этом xml опять пишем наши очки под именем которое задаем save_key_count

        pay = getSharedPreferences("pay", MODE_PRIVATE);
        payments = pay.getInt("payment_for_basic_test", 0);


        hooks();


        profile_id.setText(inkognito);
       // balance.setText(getString(R.string.pay)+" " + payment_for_basic_test);

        balance.setText(" "+ payments);


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
                Intent intent = new Intent(profileId.this, addStories.class);
                startActivity(intent);
                finish();
            }
        });

        answerstest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(profileId.this, youOtvetTest.class);
                startActivity(intent);
                finish();
            }
        });

        profile_balance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(profileId.this, profileBalance.class);
                startActivity(intent);
                finish();
            }
        });


    }

    private void hooks() {
        profile_id = findViewById(R.id.profile_id);
        foto_public = findViewById(R.id.foto_public);
        stories = findViewById(R.id.stories);
        answerstest = findViewById(R.id.answerstest);
        balance = findViewById(R.id.balance);
        profile_balance = findViewById(R.id.profile_balance);

    }


    public void onBackPressed() {
        try {
            Intent intent = new Intent(profileId.this, kaizerActivity.class); // try = попытка
            startActivity(intent);
            finish();
        } catch (Exception e) {  // catch = ловить
        }
    }


}