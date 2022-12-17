package team.diamond.kaizer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import team.diamond.kaizer.adapters.customAdapter;
import team.diamond.kaizer.models.UploadCustom;

//  не забываем писать  ???  типо расширенная хуйня
public class storyCustom extends AppCompatActivity implements customAdapter.OnItemClickListener {

    //SharedPreferences
    private SharedPreferences user_name_shared_preferences;
    public String inkognito;
    //FirebaseDatabase  --  Database
    FirebaseDatabase firebaseDatabase;
    //DatabaseReference  --  Reference
    private DatabaseReference StoryRef;
    private ValueEventListener mDBListener;
    private DatabaseReference mDatabaseRef;
    //progress dialog
    ProgressDialog pd;
    // тут пишем что у нас вприницпе есть на layout
    private ImageView addStory;
    private ProgressBar mProgressBar;
    private RecyclerView storyCustomeRv;
    // кастомный адаптер
    private customAdapter mAdapter;
    //private Uri mImageUri;   // хз  фото не собираюсь длбавлсять

    private List<UploadCustom> mUploads;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.story_custom);

        user_name_shared_preferences = getSharedPreferences("teen_pref", MODE_PRIVATE); //обяъвляем приватный режим для ОЧКОВ + прописываем ИМЯ в xml (чxml так и будет называться) + приватный режим
        inkognito = user_name_shared_preferences.getString("teen_name", inkognito);// пишем ВПЕРЕДИ  т.к. код исполняется по порядку + в этом xml опять пишем наши очки под именем которое задаем save_key_count

        hooks();
        //init firebase
        firebaseDatabase = FirebaseDatabase.getInstance();
        StoryRef = firebaseDatabase.getReference("Story");
        //init progress dialog
        pd = new ProgressDialog(this);
        //RecyclerView
        storyCustomeRv.setHasFixedSize(true);
        storyCustomeRv.setLayoutManager(new LinearLayoutManager(this));

        mUploads = new ArrayList<>();

        mAdapter = new customAdapter(storyCustom.this, mUploads);

        storyCustomeRv.setAdapter(mAdapter);

        mAdapter.setOnClickListener(storyCustom.this);

        // указываем путь к историям
        StoryRef = FirebaseDatabase.getInstance().getReference("Story").child(inkognito);


        //без этого кода в RV ничего не будет отображатсья
        mDBListener = StoryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mUploads.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    UploadCustom upload = postSnapshot.getValue(UploadCustom.class);
                    upload.setKey(postSnapshot.getKey());
                    mUploads.add(upload);
                }

                mAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(storyCustom.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();  // 3:10
            }
        });


        addStory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadMyStory();
            }
        });


    }
    //______________________________


    private void uploadMyStory() {
    }


    private void hooks() {
        mProgressBar = findViewById(R.id.progress_bar);
        storyCustomeRv = findViewById(R.id.storyCustomeRv);
        addStory = findViewById(R.id.addStory);

    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(this, "нажмите и держите чтобы удалить историю " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onWhatEverClick(int position) {
        Toast.makeText(this, "Whatever click at position" + position, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onDeleteClick(int position) {
        //берем ключ от позиции
        UploadCustom selectedItem = mUploads.get(position);
        String selectedKey = selectedItem.getKey();
        //удаляем историю
        StoryRef.child(selectedKey).removeValue();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

       // mDatabaseRef.removeEventListener(mDBListener);
        //тут была ошибка возвращалось хз хуета какая то

    }

    //кнопка назад
    public void onBackPressed() {
        try {
            Intent intent = new Intent(storyCustom.this, kaizerActivity.class); // try = попытка
            startActivity(intent);
            finish();
        } catch (Exception e) {  // catch = ловить
        }
    }

}