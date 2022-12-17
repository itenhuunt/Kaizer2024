package team.diamond.kaizer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;

public class youOtvetTest extends AppCompatActivity {

    TextView otbvet1;
    LottieAnimationView animationView;

    public String inkognito = "no_name"; // присваиваем no name по умолчанию
    private SharedPreferences user_name_shared_preferences;   // обяъвляем  SharedPreferences


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.you_otvet_test);


        user_name_shared_preferences = getSharedPreferences("teen_pref", MODE_PRIVATE); //обяъвляем приватный режим + прописываем ИМЯ в xml (.xml так и будет называться) + приватный режим
        inkognito = user_name_shared_preferences.getString("teen_name", inkognito);//  в этом xml опять пишем имя нашей белки  +  которое задаем значение teen_name

        hooks();




    }


    //_________________________________________________________________________________________________________________________


    private void hooks() {
      //  animationView = findViewById(R.id.animationView);

    }


    public void onBackPressed() {
        try {
            Intent intent = new Intent(youOtvetTest.this, profileId.class); // try = попытка
            startActivity(intent);
            finish();
        } catch (Exception e) {  // catch = ловить
        }
    }


}