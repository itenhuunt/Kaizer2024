package team.diamond.kaizer.job;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import team.diamond.kaizer.R;
import team.diamond.kaizer.kaizerActivity;
import team.diamond.kaizer.profileId;

public class jobAdd extends AppCompatActivity {

    private SharedPreferences user_name_shared_preferences;
    public String inkognito;

    Button jobSave, btnBack;
    EditText txtjob, descriptionjob, rewardjob, nameboss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job_add);

        user_name_shared_preferences = getSharedPreferences("teen_pref", MODE_PRIVATE); //обяъвляем приватный режим для ОЧКОВ + прописываем ИМЯ в xml (чxml так и будет называться) + приватный режим
        inkognito = user_name_shared_preferences.getString("teen_name", inkognito);// пишем ВПЕРЕДИ  т.к. код исполняется по порядку + в этом xml опять пишем наши очки под именем которое задаем save_key_count

        hooks();


        jobSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertData();
                clearall();

            }
        });


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(jobAdd.this, job.class);
                startActivity(intent);
                finish();
            }
        });


    }


//________________________________________________________________________


    private void hooks() {
        txtjob = findViewById(R.id.txtjob);
        descriptionjob = findViewById(R.id.descriptionjob);
        rewardjob = findViewById(R.id.rewardjob);
        nameboss = findViewById(R.id.nameboss);
        jobSave = findViewById(R.id.jobSave);
        btnBack = findViewById(R.id.btnBack);
    }


    private void insertData() {
        Map<String, Object> map = new HashMap<>();
        map.put("job", txtjob.getText().toString());
        map.put("descriptionjob", descriptionjob.getText().toString());
        map.put("reward", rewardjob.getText().toString());
        map.put("nameboss", inkognito);

        FirebaseDatabase.getInstance().getReference().child("job").push()
                .setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(jobAdd.this, "data succesfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(jobAdd.this, "error", Toast.LENGTH_SHORT).show();

                    }
                });
    }

    private void clearall() {
        txtjob.setText("");
        descriptionjob.setText("");
        rewardjob.setText("");
        nameboss.setText("");

        //    txtturl.setText("");

    }



    public void onBackPressed() {
        try {
            Intent intent = new Intent(jobAdd.this, job.class); // try = попытка
            startActivity(intent);
            finish();
        } catch (Exception e) {  // catch = ловить
        }
    }

}