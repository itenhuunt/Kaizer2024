package team.diamond.kaizer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    Button startQuestion,answerQuestion,creatQuestion;
    ImageView profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        find();







        startQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(MainActivity.this, LoadingQuest.class);
                    startActivity(intent);
                    finish();

                }catch (Exception e){
                    //empty
                }
            }
        });


        answerQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(MainActivity.this,answerBelka.class);
                    startActivity(intent);
                    finish();

                }catch (Exception e){
                    //empty
                }
            }
        });


        creatQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(MainActivity.this,creatQuestion.class);
                    startActivity(intent);
                    finish();

                }catch (Exception e){
                    //empty
                }
            }
        });


        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(MainActivity.this, profileId.class);
                    startActivity(intent);
                    finish();

                }catch (Exception e){
                    //empty
                }
            }
        });


    }

    private void find() {

        startQuestion = findViewById(R.id.startQuestion);
        answerQuestion = findViewById(R.id.answer_belka);
        creatQuestion = findViewById(R.id.creatQuestion);
        profile = findViewById(R.id.profile);
    }


}