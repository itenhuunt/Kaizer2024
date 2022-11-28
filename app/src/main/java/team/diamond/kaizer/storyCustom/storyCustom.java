package team.diamond.kaizer.storyCustom;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import team.diamond.kaizer.R;
import team.diamond.kaizer.foto2.ImageAdapter;
import team.diamond.kaizer.foto2.Upload;
import team.diamond.kaizer.profileId;

public class storyCustom extends AppCompatActivity implements ImageAdapter.OnItemClickListener {

    private SharedPreferences user_name_shared_preferences;
    public String inkognito;

    FirebaseDatabase firebaseDatabase;

    //progress dialog
    ProgressDialog pd;

    //____________________________ 1


    private ImageView addStory;
    private ProgressBar mProgressBar;

    private Uri mImageUri;
    private DatabaseReference mDatabaseRef;

    //_____________________________   2
    private RecyclerView storyCustomeRv;
    private ImageAdapter mAdapter;

    private DatabaseReference mDatabaseRef2;
    private ValueEventListener mDBListener;

    private List<Upload> mUploads;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.story_custom);

        user_name_shared_preferences = getSharedPreferences("teen_pref", MODE_PRIVATE); //обяъвляем приватный режим для ОЧКОВ + прописываем ИМЯ в xml (чxml так и будет называться) + приватный режим
        inkognito = user_name_shared_preferences.getString("teen_name", inkognito);// пишем ВПЕРЕДИ  т.к. код исполняется по порядку + в этом xml опять пишем наши очки под именем которое задаем save_key_count

        hooks();
        //init firebase
        firebaseDatabase = FirebaseDatabase.getInstance();

        //init progress dialog
        pd = new ProgressDialog(this);


        storyCustomeRv.setHasFixedSize(true);
        storyCustomeRv.setLayoutManager(new LinearLayoutManager(this));

        mUploads = new ArrayList<>();

        mAdapter = new ImageAdapter(storyCustom.this, mUploads);

        storyCustomeRv.setAdapter(mAdapter);

        mAdapter.setOnClickListener(storyCustom.this);

        // указываем путь к платному альбому
        mDatabaseRef2 = FirebaseDatabase.getInstance().getReference("paidAlbum").child(inkognito);

        mDBListener = mDatabaseRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mUploads.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Upload upload = postSnapshot.getValue(Upload.class);
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

    //нычка
//    private void nora() {
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.CANADA);
//        Date now = new Date();
//        String fileName = formatter.format(now);
//        // mStorageRefwtf = FirebaseStorage.getInstance().getReference("image3/" + fileName);
//        // mStorageRefwtf = FirebaseStorage.getInstance().getReference("wtf").child(inkognito + fileName);
//        mStorageRefwtf = FirebaseStorage.getInstance().getReference("wtf").child(inkognito).child("paid/" + fileName);
//        mStorageRefwtf.putFile(mImageUri)
//                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                        //  binding.firebaseimage.setImageURI(null);
//                        Toast.makeText(storyCustom.this, " ", Toast.LENGTH_SHORT).show();
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(storyCustom.this, " ", Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }


    private void addPaid() {
        //  option show dialog
        String options[] = {"Указать цену альбома"};
        //alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //set title
        builder.setTitle("Выберите действие");
        // set item to dialog
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //handle dialog item clicks
                if (which == 0) {
                    // edit profile clicked
                    pd.setMessage("Укажи цену альбома в кристалах");
                    //    addPaid2("paid_album");
                }
            }
        });
        //create and show dialog
        builder.create().show();
    }


//    private void addPaid2(String key) {
//        //custom dialog
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Цена альбома");
//        //set layout of dialog
//        LinearLayout linearLayout = new LinearLayout(this);
//        linearLayout.setOrientation(LinearLayout.VERTICAL);
//        linearLayout.setPadding(10, 10, 10, 10);
//        //add edit test
//        EditText editText = new EditText(this);
//        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
//        editText.setHint("укажи цену в кристаллах");//hint e.g. Edit name OR Edit phone
//        linearLayout.addView(editText);
//
//        builder.setView(linearLayout);
//
//        //add Buttons in dialog to Update
//        builder.setPositiveButton("добавить", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                //input text from edit text
//                String value = editText.getText().toString().trim();
//                //validate if user has entered something or not
//                if (!TextUtils.isEmpty(value)) {
//                    pd.show();
//                    HashMap<String, Object> result = new HashMap<>();
//                    result.put(key, value);
//
//                    databaseReference.child(inkognito).updateChildren(result)
//                            .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                @Override
//                                public void onSuccess(Void unused) {
//                                    //updated, dismiss progress
//                                    pd.dismiss();
//                                    Toast.makeText(storyCustom.this, "обновление", Toast.LENGTH_SHORT).show();
//                                }
//                            })
//                            .addOnFailureListener(new OnFailureListener() {
//                                @Override
//                                public void onFailure(@NonNull Exception e) {
//                                    //failed, dismiss progress, get and show error message
//                                    pd.dismiss();
//                                    Toast.makeText(storyCustom.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
//                                }
//                            });
//                } else {
//                    Toast.makeText(storyCustom.this, "Update", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//        //add Buttons in dialog to Cancel
//        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        });
//        //create and show dialog
//        builder.create().show();
//
//    }

    private void hooks() {

        mProgressBar = findViewById(R.id.progress_bar);
        storyCustomeRv = findViewById(R.id.storyCustomeRv);


    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(this, "нажмите и держите чтобы удалить фото " + position, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onWhatEverClick(int position) {
        Toast.makeText(this, "Whatever click at position" + position, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onDeleteClick(int position) {

        Upload selectedItem = mUploads.get(position);
        String selectedKey = selectedItem.getKey();

//        StorageReference imageRef = mStorage.getReferenceFromUrl(selectedItem.getImageUrl());
//        imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void aVoid) {
//                mDatabaseRef.child(selectedKey).removeValue();
//                Toast.makeText(storyCustom.this, "Item deleted", Toast.LENGTH_SHORT).show();
//            }
//        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDatabaseRef.removeEventListener(mDBListener);
    }

    public void onBackPressed() {
        try {
            Intent intent = new Intent(storyCustom.this, profileId.class); // try = попытка
            startActivity(intent);
            finish();
        } catch (Exception e) {  // catch = ловить
        }
    }

}