package team.diamond.kaizer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class writeGeneralChat extends AppCompatActivity {

    private SharedPreferences user_name_shared_preferences;
    private String inkognito, inkognitoid;

    ImageView help, profileImg;

    TextView costWriteGeneralCat;

    //все что касается ЧАТА
    ImageButton sendBtn;
    EditText commentEt;


    ProgressDialog progressDialog;

    FirebaseDatabase database; //  сама Firebase

    ProgressDialog pd;

    String postId = "random";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_general_chat);

        user_name_shared_preferences = getSharedPreferences("teen_pref", MODE_PRIVATE); //обяъвляем приватный режим для ОЧКОВ + прописываем ИМЯ в xml (чxml так и будет называться) + приватный режим
        inkognito = user_name_shared_preferences.getString("teen_name", inkognito);// пишем ВПЕРЕДИ  т.к. код исполняется по порядку + в этом xml опять пишем наши очки под именем которое задаем save_key_count
        inkognitoid = user_name_shared_preferences.getString("teen_id", inkognitoid);//  в этом xml опять пишем имя нашей белки  +  которое задаем значение teen_name


        hooks();
        //init database
        database = FirebaseDatabase.getInstance();


        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");


        // ДОБАВЛЯЕМ КОМЕНТАРИЯ К ЧАТУ по нажатию
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newCommentChat();
            }
        });


    }


    //______________________________________________________________________________________________________


    //  добавление теста по нажатию на кнопку   ___ начало
    private void newCommentChat() {
        pd = new ProgressDialog(this);
        pd.setMessage("adding comment...");

        //get data from comment edit text
        String comment = commentEt.getText().toString().trim();
        //validate
        if (TextUtils.isEmpty(comment)) {
            //no value is entered
            Toast.makeText(this, "Comment is empty...", Toast.LENGTH_SHORT).show();
            return;
        }

        String timeStamp = String.valueOf(System.currentTimeMillis());

        //each post will have a child "Comments" tha will contain comments of that post
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Posts").child("Comments");

        HashMap<String, Object> hashMap = new HashMap<>();
        //put info in hashmap
        hashMap.put("cId", timeStamp);
        hashMap.put("comment", comment);
        hashMap.put("timestamp", timeStamp);
        hashMap.put("uid", inkognitoid);
        hashMap.put("uName", inkognito);  // типо имя    от которого сообщение в чате будет отображаться

        //put this data in db
        ref.child(timeStamp).setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        //added
                        pd.dismiss();
                        Toast.makeText(writeGeneralChat.this, " Added...", Toast.LENGTH_SHORT).show();
                        commentEt.setText("");
                        //  updateCommentCount();

                        Intent intent = new Intent(writeGeneralChat.this, kaizerActivity.class); // try = попытка
                        startActivity(intent);
                        finish();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //failed, not added
                        pd.dismiss();
                        Toast.makeText(writeGeneralChat.this, "Fail  Added...", Toast.LENGTH_SHORT).show();


                    }
                });



    }
    //  добавление теста по нажатию на кнопку   ___ конец


    private void hooks() {
        sendBtn = findViewById(R.id.sendBtn);
        commentEt = findViewById(R.id.commentEt);
        costWriteGeneralCat = findViewById(R.id.costWriteGeneralCat);

    }

    public void onBackPressed() {
        try {
            Intent intent = new Intent(writeGeneralChat.this, kaizerActivity.class); // try = попытка
            startActivity(intent);
            finish();
        } catch (Exception e) {  // catch = ловить
        }
    }



}