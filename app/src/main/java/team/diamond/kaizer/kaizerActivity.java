package team.diamond.kaizer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import team.diamond.kaizer.allusers.AllUsers;
import team.diamond.kaizer.job.job;
import team.diamond.kaizer.otvetTest.otvetTestBasic;
import team.diamond.kaizer.startTest.AllTests;
import team.diamond.kaizer.startTest.LoadingQuest;

public class kaizerActivity extends AppCompatActivity {

    Button answerQuestion;
    ImageView profile;



    LinearLayout basictest, readStories , add, job,recentlyJoined;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kaizer_main);


        hooks();


        basictest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    try {
                        Intent intent = new Intent(kaizerActivity.this, AllTests.class);
                        startActivity(intent);
                        finish();

                    } catch (Exception e) {
                        //empty
                    }

            }
        });


        answerQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(kaizerActivity.this, otvetTestBasic.class);
                    startActivity(intent);
                    finish();

                } catch (Exception e) {
                    //empty
                }
            }
        });




        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(kaizerActivity.this, profileId.class);
                    startActivity(intent);
                    finish();

                } catch (Exception e) {
                    //empty
                }
            }
        });

        readStories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(kaizerActivity.this, team.diamond.kaizer.storiesFullShema.readStories.class);
                    startActivity(intent);
                    finish();

                } catch (Exception e) {
                    //empty
                }
            }
        });


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(kaizerActivity.this, addStories.class);
                    startActivity(intent);
                    finish();

                } catch (Exception e) {
                    //empty
                }
            }
        });


        job.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(kaizerActivity.this, team.diamond.kaizer.job.job.class);
                    startActivity(intent);
                    finish();

                } catch (Exception e) {
                    //empty
                }
            }
        });


        recentlyJoined.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(kaizerActivity.this, AllUsers.class);
                    startActivity(intent);
                    finish();

                } catch (Exception e) {
                    //empty
                }
            }
        });




    }

    private void hooks() {

        answerQuestion = findViewById(R.id.answer_belka);
        profile = findViewById(R.id.profile);
        basictest = findViewById(R.id.basictest);
        job = findViewById(R.id.job);
        readStories = findViewById(R.id.readStories);
        add = findViewById(R.id.add);
        recentlyJoined = findViewById(R.id.recentlyJoined);



    }


}