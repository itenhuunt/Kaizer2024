package team.diamond.kaizer;

import static team.diamond.kaizer.LoadingQuest.list;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Collections;
import java.util.List;

public class startBelka extends AppCompatActivity {

    public String inkognito;

    private SharedPreferences user_name_shared_preferences;


    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://kaizerver3-default-rtdb.firebaseio.com/");


    List<Modelclass> allQeustionsList;
    Modelclass modelclass;
    int index = 0;
    TextView universal_question,answer1,answer2,answer3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_belka);


        user_name_shared_preferences = getSharedPreferences("teen_pref",MODE_PRIVATE); //обяъвляем приватный режим для ОЧКОВ + прописываем ИМЯ в xml (чxml так и будет называться) + приватный режим
        inkognito = user_name_shared_preferences.getString("teen_name",inkognito);// пишем ВПЕРЕДИ  т.к. код исполняется по порядку + в этом xml опять пишем наши очки под именем которое задаем save_key_count



        Hooks(); //ЗАВИСИМОСТИ !!! где оно находится на layout   ID

        allQeustionsList=list;  //сначала устанавливаем  list
        Collections.shuffle(allQeustionsList); // перемешиваем все вопросы малолеткам
        modelclass = list.get(index); // потом номеруем все свои 3 главных вопроса

        // и только потом присваиваем вопросы  по id
        setAllData();   // почему потом ?  СУКА ЭТО ПИШЕТСЯ ПОСЛЕ !!!

        answer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {


                    Correct();

                    //  как прописать вопрос вместо  "quest1"
                    // работает но с ошибками  может ве в ручную написать ?    отдельно каждый элемент
                    final String oATxt = answer1.getText().toString();
                    final String oBTxt = universal_question.getText().toString();
                    databaseReference.child("OTVET").child(oBTxt).child(inkognito).setValue(oATxt);

                }catch (Exception e){
                    // пусто
                }

            }
        });
   }

   //  ПЕРЕХОД СПИСОК ВОПРОСОВ   хз как но написал наугад с 1ого раза
    private void Correct() {
        if(index< list.size()-1){
            index++;
            modelclass = list.get(index);
            setAllData();


        }

        else{
            Intent intent = new Intent(startBelka.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    }


    private void setAllData() {
        universal_question.setText(modelclass.getQuestion());
        answer1.setText(modelclass.getoA());
        answer2.setText(modelclass.getoB());
        answer3.setText(modelclass.getoC());


    }

    private void Hooks() {
        universal_question = findViewById(R.id.universal_question);
        answer1 = findViewById(R.id.answer1);
        answer2 = findViewById(R.id.answer2);
        answer3 = findViewById(R.id.answer3);
    }


    public void onBackPressed(){
        try{Intent intent = new Intent(startBelka.this,MainActivity.class); // try = попытка
            startActivity(intent);
            finish();
        }catch (Exception e){  // catch = ловить
        }
    }
}