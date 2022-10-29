package team.diamond.kaizer.startTest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import team.diamond.kaizer.R;
import team.diamond.kaizer.addStories;
import team.diamond.kaizer.creatTest.creatTest;
import team.diamond.kaizer.creatTest.testModel;
import team.diamond.kaizer.kaizerActivity;
import team.diamond.kaizer.otvetTest.otvetAdapter;


public class AllTests extends AppCompatActivity {


    RecyclerView CustomTestRv;   //  лист всех белок которые зарешались
    DatabaseReference database;   //устанавливаем базу данных
    addTestadapter AdapterCustomTest;    // прописываем адаптер
    ArrayList<testModel> list2;   //  сама схема (короткая)

    TextView startbasictest, test2;
    Button creattestbtn;

    private SharedPreferences Oneraz;
    int Oneraz1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_tests);

        Oneraz = getSharedPreferences("Oneraz", MODE_PRIVATE); // объявляем переменную pay
        Oneraz1 = Oneraz.getInt("Oneraz1", 0); //  в переменной pay  пишем  "payment_for_basic_test";

        hooks();
        database = FirebaseDatabase.getInstance().getReference("user"); // прописываем базу в реальном времени

        CustomTestRv.setHasFixedSize(true);
        CustomTestRv.setLayoutManager(new LinearLayoutManager(this));
        list2 = new ArrayList<>();
        AdapterCustomTest = new addTestadapter(this, list2);
        CustomTestRv.setAdapter(AdapterCustomTest);  //устанавливаем адаптер

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    //    String documentId = dataSnapshot.get();

                    testModel add_test_shema = dataSnapshot.getValue(testModel.class);
                    list2.add(add_test_shema);
                }
                AdapterCustomTest.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        startbasictest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Oneraz1 == 2) {
                    Toast.makeText(AllTests.this, R.string.testhasalreadypassed, Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        Intent intent = new Intent(AllTests.this, LoadingQuest.class);
                        startActivity(intent);
                        finish();

                    } catch (Exception e) {
                        //empty
                    }
                }
            }
        });

        test2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Oneraz1 == 3) {
                    Toast.makeText(AllTests.this, R.string.testhasalreadypassed, Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        Intent intent = new Intent(AllTests.this, LoadingQuestver2.class);
                        startActivity(intent);
                        finish();

                    } catch (Exception e) {
                        //empty
                    }
                }
            }
        });


        creattestbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Oneraz1 == 3) {
                    Toast.makeText(AllTests.this, R.string.testhasalreadypassed, Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        Intent intent = new Intent(AllTests.this, creatTest.class);
                        startActivity(intent);
                        finish();

                    } catch (Exception e) {
                        //empty
                    }
                }
            }
        });

    }

    private void hooks() {
        startbasictest = findViewById(R.id.startbasictest);
        test2 = findViewById(R.id.test2);
        creattestbtn = findViewById(R.id.creattestbtn);
        CustomTestRv = findViewById(R.id.CustomTestRv);
    }


    public void onBackPressed() {
        try {
            Intent intent = new Intent(AllTests.this, kaizerActivity.class); // try = попытка
            startActivity(intent);
            finish();
        } catch (Exception e) {  // catch = ловить
        }
    }


}