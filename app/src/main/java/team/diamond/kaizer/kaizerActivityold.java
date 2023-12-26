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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import team.diamond.kaizer.adapters.AdapterGeneralChat;
import team.diamond.kaizer.models.ModelGeneralChat;
import team.diamond.kaizer.view.StreamAllHere;

public class kaizerActivityold extends AppCompatActivity {

    private SharedPreferences user_name_shared_preferences;
    private String inkognito, inkognitoid;

    ImageView help, profileImg;
    LottieAnimationView animationView2;
    TextView infAll, addPost, allpost;

    //все что касается ЧАТА
    RecyclerView generalChatRv;
    ImageButton sendBtn;
    EditText commentEt;
    List<ModelGeneralChat> modelGeneralChatList;
    AdapterGeneralChat adapterGeneralChat;


    LinearLayout basictest, readStories, job, recentlyJoined;
    ProgressDialog progressDialog;

    FirebaseDatabase database; //  сама Firebase
    DatabaseReference refImgProfile, userDbRef;    //ссылки в Firebase on Img profile pic + ключ

    ProgressDialog pd;

    String postId = "random";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kaizer_main);

        user_name_shared_preferences = getSharedPreferences("teen_pref", MODE_PRIVATE); //обяъвляем приватный режим для ОЧКОВ + прописываем ИМЯ в xml (чxml так и будет называться) + приватный режим
        inkognito = user_name_shared_preferences.getString("teen_name", inkognito);// пишем ВПЕРЕДИ  т.к. код исполняется по порядку + в этом xml опять пишем наши очки под именем которое задаем save_key_count
        inkognitoid = user_name_shared_preferences.getString("teen_id", inkognitoid);//  в этом xml опять пишем имя нашей белки  +  которое задаем значение teen_name


        hooks();
        //init database
        database = FirebaseDatabase.getInstance();
        //loading Image to profile pic
        loadImgProfile();
        //загрузка инфы в случае чего
        loadInfAll();
        //загружаем главный чаи
        loadGeneralChat();


        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");


        basictest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(kaizerActivityold.this, AllTests.class);
                    startActivity(intent);
                    finish();
                } catch (Exception e) {
                    //empty
                }
            }
        });


        profileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(kaizerActivityold.this, profileId.class);
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
                    Intent intent = new Intent(kaizerActivityold.this, TrueStory.class);
                    startActivity(intent);
                    finish();
                } catch (Exception e) {
                    //empty
                }
            }
        });

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(kaizerActivityold.this, Help_standart.class);
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
                Intent intent = new Intent(kaizerActivityold.this, job.class);
                startActivity(intent);
                finish();
            }
        });


        recentlyJoined.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(kaizerActivityold.this, AllUsers.class);
                    startActivity(intent);
                    finish();
                } catch (Exception e) {
                    //empty
                }
            }
        });


        animationView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(kaizerActivityold.this, StreamAllHere.class);
                    startActivity(intent);
                    finish();
                } catch (Exception e) {
                    //empty
                }
            }
        });


        // ДОБАВЛЯЕМ КОМЕНТАРИЯ К ЧАТУ по нажатию
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newCommentChat();
            }
        });


    }


    //______________________________________________________________________________________________________


    //  ЧАТ КАСТОМНЫЙ КОТОРЫЙ Я ДОБАВИЛ С FIREBASE NEW LIFE   ___ начало
    // описание: тут идет загрузка чата   т.е.  все что пишут
    private void loadGeneralChat() {

        //layout (Liner) for recyclerview
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        //задом наперед  РАБОТАЕТ !!! ВСЕГО 2 СТРОКИ
        //layoutManager.setReverseLayout(true);
        // layoutManager.setStackFromEnd(true);


        //set layout to recyclerview
        generalChatRv.setLayoutManager(layoutManager);

        //init comments list
        modelGeneralChatList = new ArrayList<>();

        //patch of thr post, to get it's comments
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Posts").child("Comments");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                modelGeneralChatList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    ModelGeneralChat modelGeneralChat = ds.getValue(ModelGeneralChat.class);

                    modelGeneralChatList.add(modelGeneralChat);

                    //pass myUid and postId as of constructor of Comment  Adapter

                    //setup adapter
                    adapterGeneralChat = new AdapterGeneralChat(getApplicationContext(), modelGeneralChatList, inkognitoid, postId);
                    //set adapter
                    generalChatRv.setAdapter(adapterGeneralChat);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
    //  ЧАТ КАСТОМНЫЙ КОТОРЫЙ Я ДОБАВИЛ С FIREBASE NEW LIFE   ___ конец


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
                        Toast.makeText(kaizerActivityold.this, " Added...", Toast.LENGTH_SHORT).show();
                        commentEt.setText("");
                        //  updateCommentCount();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //failed, not added
                        pd.dismiss();
                        Toast.makeText(kaizerActivityold.this, "Fail  Added...", Toast.LENGTH_SHORT).show();

                    }
                });


    }
    //  добавление теста по нажатию на кнопку   ___ конец


    private void loadInfAll() {
        DatabaseReference experients = database.getReference("Inf").child("all"); // вариант 3  типо прописали ссылку + родительский католог : что напротив него написано
        // чтение из базы ValueEventListener
        experients.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String m = snapshot.getValue(String.class);
                infAll.setText(m);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


    }

    private void loadImgProfile() {
        refImgProfile = database.getReference("users").child(inkognitoid).child("profile_pic"); // вариант 3  типо прописали ссылку + родительский католог : что напротив него написано
        // чтение из базы ValueEventListener   В РЕАЛЬНОМ ВРЕМЕНИ !!!  --->  addValueEventListener
        refImgProfile.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //read  profile_pic
                String imgProfile = snapshot.getValue(String.class);
                //load Img to profile_pic
                try {
                    //if image is received then set
                    Picasso.get().load(imgProfile).into(profileImg);
                } catch (Exception e) {
                    //if there is any exception while getting image then set default
                    Picasso.get().load(R.drawable.man).into(profileImg);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }


    private void hooks() {
        profileImg = findViewById(R.id.profile);
        basictest = findViewById(R.id.basictest);
        job = findViewById(R.id.job);
        readStories = findViewById(R.id.readStories);
        help = findViewById(R.id.help);
        recentlyJoined = findViewById(R.id.recentlyJoined);
        animationView2 = findViewById(R.id.animationView2);
        infAll = findViewById(R.id.Infall);
        addPost = findViewById(R.id.addPost);
        allpost = findViewById(R.id.allpost);
        generalChatRv = findViewById(R.id.generalChat);
        sendBtn = findViewById(R.id.sendBtn);
        commentEt = findViewById(R.id.commentEt);

    }


}