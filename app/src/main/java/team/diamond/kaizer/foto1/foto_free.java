package team.diamond.kaizer.foto1;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import team.diamond.kaizer.R;
import team.diamond.kaizer.foto2.ImageAdapter;
import team.diamond.kaizer.foto2.Upload;
import team.diamond.kaizer.profileId;

public class foto_free extends AppCompatActivity implements ImageAdapterFree.OnItemClickListener {

    //берем имя из shared preferences
    private SharedPreferences user_name_shared_preferences;
    public String inkognito;
    //указываем firebase
    FirebaseDatabase firebaseDatabase;

    //progress dialog
    ProgressDialog pd;

    private static final int PICK_image_Request = 1;

    private ImageView imagePreview, addFoto, downloadFoto;
    private ProgressBar mProgressBar;

    private Uri mImageUri;
    //указываем ссылки
    private StorageReference mStorageRef;
    //указываем хранилище
    private DatabaseReference mDatabaseRef;

    private StorageTask mUploadTask;

    //_____________________________   2    отображение
    private RecyclerView RvFreeAlbume;
    private ImageAdapterFree mAdapterFree;
    //указываем ссылки
    private DatabaseReference mDatabaseRef2;
    //указываем хранилище
    private FirebaseStorage mStorage;
    //   база которая обновляется в реальном врмени
    private ValueEventListener mDBListener;
    //  то что будет загружаться на сервер
    private List<UploadFree> mUploads;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.foto_public2);

        user_name_shared_preferences = getSharedPreferences("teen_pref", MODE_PRIVATE); //обяъвляем приватный режим для ОЧКОВ + прописываем ИМЯ в xml (чxml так и будет называться) + приватный режим
        inkognito = user_name_shared_preferences.getString("teen_name", inkognito);// пишем ВПЕРЕДИ  т.к. код исполняется по порядку + в этом xml опять пишем наши очки под именем которое задаем save_key_count

        hooks();
        //init firebase
        firebaseDatabase = FirebaseDatabase.getInstance();

        //указываем путь в fire storage
        mStorageRef = FirebaseStorage.getInstance().getReference("FreeAlbum").child(inkognito);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("FreeAlbum").child(inkognito);

        //init progress dialog
        pd = new ProgressDialog(this);

        RvFreeAlbume.setHasFixedSize(true);
        RvFreeAlbume.setLayoutManager(new LinearLayoutManager(this));

        mUploads = new ArrayList<>();

        mAdapterFree = new ImageAdapterFree(foto_free.this, mUploads);
        //  для RV устанавливаем адаптер
        RvFreeAlbume.setAdapter(mAdapterFree);

        mAdapterFree.setOnClickListener(foto_free.this);

        mStorage = FirebaseStorage.getInstance();
        // указываем путь к платному альбому
        mDatabaseRef2 = FirebaseDatabase.getInstance().getReference("FreeAlbum").child(inkognito);

        mDBListener = mDatabaseRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mUploads.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    UploadFree upload = postSnapshot.getValue(UploadFree.class);
                    upload.setKey(postSnapshot.getKey());
                    mUploads.add(upload);
                }

                mAdapterFree.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(foto_free.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();  // 3:10
            }
        });

        // кнопка выбрать изображение  начало
        addFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();

            }
        });

        // кнопка выбрать изображение  начало
        downloadFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadFileFreeAlbum();

            }
        });




    }
    //______________________________

    //описание команды выбери фото
    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_image_Request);
    }


    // хз как оно относиться к кому но оно работает  начала
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_image_Request && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();
            Picasso.get().load(mImageUri).into(imagePreview);
        }
    }
    // хз как оно относиться к кому но оно работает   конец


    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }


    //загружаем фото на сервер
    private void uploadFileFreeAlbum() {
        if (mImageUri != null) {

            StorageReference ref = mStorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(mImageUri));

            ref.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            mProgressBar.setProgress(0);
                                        }
                                    }, 5000);


                                    Upload upload = new Upload(uri.toString(), uri.toString());
                                    mDatabaseRef.push().setValue(upload);
                                    Toast.makeText(foto_free.this, "successful upload", Toast.LENGTH_SHORT).show();

                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(foto_free.this, "fail uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            mProgressBar.setProgress((int) progress);

                        }
                    });
        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }

    }


    private void hooks() {
        imagePreview = findViewById(R.id.imagePreview);
        addFoto = findViewById(R.id.addFoto);
        downloadFoto = findViewById(R.id.downloadFoto);
        mProgressBar = findViewById(R.id.progress_bar);
        RvFreeAlbume = findViewById(R.id.RvFreeAlbume);

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

        UploadFree selectedItem = mUploads.get(position);
        String selectedKey = selectedItem.getKey();

        StorageReference imageRef = mStorage.getReferenceFromUrl(selectedItem.getImageUrl());
        imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                mDatabaseRef.child(selectedKey).removeValue();
                Toast.makeText(foto_free.this, "Item deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDatabaseRef.removeEventListener(mDBListener);
    }

    public void onBackPressed() {
        try {
            Intent intent = new Intent(foto_free.this, profileId.class); // try = попытка
            startActivity(intent);
            finish();
        } catch (Exception e) {  // catch = ловить
        }
    }

}