package team.diamond.kaizer.startTest;

import static team.diamond.kaizer.startTest.LoadingQuestver2.list;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Collections;
import java.util.List;

import team.diamond.kaizer.R;
import team.diamond.kaizer.creatTest.testModel;
import team.diamond.kaizer.kaizerActivity;

public class startTestver2 extends AppCompatActivity {


    public String inkognito;
    private SharedPreferences user_name_shared_preferences;
    private SharedPreferences pay;
    int payments = 0;


    private SharedPreferences Oneraz;
    int Oneraz1 = 0;


    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://kaizerver3-default-rtdb.firebaseio.com/");

    List<testModel> allQeustionsList;
    testModel modelclass;
    int index = 0;
    TextView universal_question, answer1, answer2, answer3, answer4;
    TextInputLayout txtanswer4;
    EditText txtanswer4ver2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_testver2);

        user_name_shared_preferences = getSharedPreferences("teen_pref", MODE_PRIVATE); //обяъвляем приватный режим для ОЧКОВ + прописываем ИМЯ в xml (чxml так и будет называться) + приватный режим
        inkognito = user_name_shared_preferences.getString("teen_name", inkognito);// пишем ВПЕРЕДИ  т.к. код исполняется по порядку + в этом xml опять пишем наши очки под именем которое задаем save_key_count


        pay = getSharedPreferences("pay", MODE_PRIVATE); // объявляем переменную pay
        payments = pay.getInt("payment_for_basic_test", 0); //  в переменной pay  пишем  "payment_for_basic_test"


        Oneraz = getSharedPreferences("Oneraz", MODE_PRIVATE); // объявляем переменную pay
        Oneraz1 = Oneraz.getInt("Oneraz1", 0); //  в переменной pay  пишем  "payment_for_basic_test"


        // а это название нужно payment_for_basic_test
        // для того чтобы выводить его на экране
        //из текста же приватного нельзя показать и ненадо показывать т.к. безопасность вен дела

        Hooks();

        allQeustionsList = list;  //сначала устанавливаем  list
        Collections.shuffle(allQeustionsList); // перемешиваем все вопросы малолеткам
        modelclass = list.get(index); // потом номеруем все свои 3 главных вопроса

        // и только потом присваиваем вопросы  по id
        setAllData();   // почему потом ?  СУКА ЭТО ПИШЕТСЯ ПОСЛЕ !!!


        answer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    //  как прописать вопрос вместо  "quest1"
                    // работает но с ошибками  может ве в ручную написать ?    отдельно каждый элемент
                    final String otvet1 = answer1.getText().toString();
                    final String vopros = universal_question.getText().toString();
                    databaseReference.child("OTVET").child(vopros).child(inkognito).setValue(otvet1);
                    Correct();
                } catch (Exception e) {
                    // пусто
                }
            }
        });

        answer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    final String otvet2 = answer2.getText().toString();
                    final String vopros = universal_question.getText().toString();
                    databaseReference.child("OTVET").child(vopros).child(inkognito).setValue(otvet2);
                    Correct();
                } catch (Exception e) {
                    // пусто
                }
            }
        });

        answer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    final String otvet3 = answer3.getText().toString();
                    final String vopros = universal_question.getText().toString();
                    databaseReference.child("OTVET").child(vopros).child(inkognito).setValue(otvet3);
                    Correct();
                } catch (Exception e) {
                    // пусто
                }
            }
        });


        answer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    txtAnswer4();  // прописасили отдельно
                } catch (Exception e) {
                    // пусто
                }
            }
        });


    }

    //  ПЕРЕХОД СПИСОК ВОПРОСОВ   хз как но написал наугад с 1ого раза   как дописать корректно хз
    private void Correct() {
        if (index <= list.size() - 1) {
            index++;
            modelclass = list.get(index);
            setAllData();
            txtanswer4.setError(null);   // убираем ошибки в касстомном ответе
            txtanswer4ver2.setText("");  // чистим кастомный ответ
        } else {

            SharedPreferences.Editor editor = pay.edit();
            editor.putInt("payment_for_basic_test", 50);
            editor.commit();
            payments = pay.getInt("payment_for_basic_test", 0);

            SharedPreferences.Editor editor2 = Oneraz.edit();
            editor2.putInt("Oneraz1", 1);
            editor2.commit();
            Oneraz1 = pay.getInt("Oneraz1", 0);

            Intent intent = new Intent(startTestver2.this, AllTests.class);
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


    // ЧТО МОЖНО ПИСАТЬ А ЧТО НЕТ      ПРАВИЛА!     логика текста  txtanswer4 варианта ответа
    private boolean validateTxtAnswer4() {
        String val = txtanswer4.getEditText().getText().toString();
        // String noWhiteSpace = "=\\s+$";
        String noWhiteSpace = "готова";  // то что нельзя писать
        if (val.isEmpty()) {
            txtanswer4.setError("поле не должно быть пустым");
            return false;
        } else if (val.length() <= 2) {  // если ответ меньше или равен 2-ум символам
            txtanswer4.setError("ответ слишком короткий");
            return false;
        } else if (val.matches(noWhiteSpace)) {
            txtanswer4.setError("готова нельзя писать и вообще причем тут нет пробела... типо я закастомил ? ");
            return false;
        } else {
            txtanswer4.setError(null);
            return true;
        }
    }


    private void txtAnswer4() {
        if (!validateTxtAnswer4()) {   //проверка того что она там написала
            return;
        }
        String myanswer = txtanswer4ver2.getText().toString();
        final String vopros = universal_question.getText().toString();
        databaseReference.child("OTVET").child(vopros).child(inkognito).setValue(myanswer);
        Correct();
    }


    private void Hooks() {
        universal_question = findViewById(R.id.universal_question);
        answer1 = findViewById(R.id.answer1);
        answer2 = findViewById(R.id.answer2);
        answer3 = findViewById(R.id.answer3);
        answer4 = findViewById(R.id.answer4);
        txtanswer4 = findViewById(R.id.txtanswer4);
        txtanswer4ver2 = findViewById(R.id.txtanswer4ver2);
    }


    public void onBackPressed() {
        try {
            Intent intent = new Intent(startTestver2.this, kaizerActivity.class); // try = попытка
            startActivity(intent);
            finish();
        } catch (Exception e) {  // catch = ловить
        }
    }
}