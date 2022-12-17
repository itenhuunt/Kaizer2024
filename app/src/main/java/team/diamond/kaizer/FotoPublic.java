package team.diamond.kaizer;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import team.diamond.kaizer.adapters.fotoAdapter;
import team.diamond.kaizer.models.fotoModel;

public class FotoPublic extends AppCompatActivity {

    private StorageReference storageReference = null;
    private DatabaseReference databaseReference = null;

    private ImageView imagePreview, addFoto, downloadFoto;
    private Uri filePath = null;

    private final int PICK_IMAGE_GALLERY_CODE = 100;
    private final int CAMERA_PERMISSION_REQUEST_CODE = 200;
    private final int CAMERA_PICTURE_REQUEST_CODE = 300;

    public String inkognito;

    private SharedPreferences user_name_shared_preferences;

    private RecyclerView RvFreeAlbume;
    private ArrayList<fotoModel> imageModelArrayList;
    private fotoAdapter recyclerImageAdapter;
    String storagePatch = " Users_profile_Cover_Imgs/";

    //не удалять ! разобраться с камерой !!!
    //uri of picked image
    Uri image_uri;
    private static final int IMAGE_PICK_CAMERA_CODE = 400;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.foto_public2);

        user_name_shared_preferences = getSharedPreferences("teen_pref", MODE_PRIVATE); //обяъвляем приватный режим для ОЧКОВ + прописываем ИМЯ в xml (чxml так и будет называться) + приватный режим
        inkognito = user_name_shared_preferences.getString("teen_name", inkognito);// пишем ВПЕРЕДИ  т.к. код исполняется по порядку + в этом xml опять пишем наши очки под именем которое задаем save_key_count

        hooks();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();

        databaseReference = database.getReference().child(inkognito);   //  Realtime Database    Reference  == Ссылка
        storageReference = firebaseStorage.getReference();


        // кнопка выбрать фото начало
        addFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageSelectedDialog();
            }
        });
        // кнопка выбрать фото конец

        // кнопка загрузить  фото начало
        downloadFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });
        // кнопка загрузить  фото конец

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        RvFreeAlbume.setLayoutManager(linearLayoutManager);
        RvFreeAlbume.setAdapter(recyclerImageAdapter);
        imageModelArrayList = new ArrayList<>();

        clearAll();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(inkognito);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                clearAll();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    fotoModel imageModel = new fotoModel();
                    imageModel.setImageurl(snapshot.getValue().toString());
                    imageModelArrayList.add(imageModel);
                }
                recyclerImageAdapter = new fotoAdapter(getApplicationContext(), imageModelArrayList);
                RvFreeAlbume.setAdapter(recyclerImageAdapter);
                recyclerImageAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(FotoPublic.this, "Error" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void hooks() {
        RvFreeAlbume = findViewById(R.id.RvFreeAlbume);
        addFoto = findViewById(R.id.addFoto);
        downloadFoto = findViewById(R.id.downloadFoto);
        imagePreview = findViewById(R.id.imagePreview);
    }

    //---------------------------------------
    private void uploadImage() {
  //     if (filePath != null) {
       if (image_uri != null) {
            StorageReference ref = storageReference.child(inkognito + "/" + UUID.randomUUID().toString()); //инфу можно изменить на дату загрузки
            ref.putFile(image_uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            databaseReference.push().setValue(uri.toString());
                            Toast.makeText(FotoPublic.this, " обновление ", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    Toast.makeText(FotoPublic.this, "fail uploaded", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    // показать image  диалог
    private void showImageSelectedDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Image");
        builder.setMessage("Please select an option");
        builder.setPositiveButton("Camera", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                chekCameraPermission();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Gallery", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectFromGallery();
                dialog.dismiss();
            }
        });
        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    // проверка камеры +
    private void chekCameraPermission() {
        if (ContextCompat.checkSelfPermission(FotoPublic.this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(FotoPublic.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(FotoPublic.this, new String[]{
                    Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, CAMERA_PERMISSION_REQUEST_CODE);
        } else {
//            openCamera();
            pickFromCamera();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
//            openCamera();
            pickFromCamera();
        }
    }


    //открыть камеру начало+
    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {     // делаю кастом
            startActivityForResult(intent, CAMERA_PICTURE_REQUEST_CODE);
        }
    }

    private void pickFromCamera() {
        //Intent of picking image from device camera
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Temp Pic");
        values.put(MediaStore.Images.Media.DESCRIPTION, "Temp Description");
        //put image uri
        image_uri = FotoPublic.this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        //intent to start camera
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(cameraIntent, IMAGE_PICK_CAMERA_CODE);
    }


    // выбрать фото из галереи+
    private void selectFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_GALLERY_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == PICK_IMAGE_GALLERY_CODE && resultCode == Activity.RESULT_OK) {
            if (data == null || data.getData() == null)
                return;
            try {
                filePath = data.getData();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imagePreview.setImageBitmap(bitmap);
            } catch (Exception e) {

            }
        } else if (requestCode == CAMERA_PICTURE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            UpdateNewCameraPic(image_uri);

//            Bundle extras = data.getExtras();
//            Bitmap bitmap = (Bitmap) extras.get("data");
//            imagePreview.setImageBitmap(bitmap);
//            filePath = getImageUri(getApplicationContext(), bitmap);


        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void UpdateNewCameraPic(Uri uri) {
//        //show progress
//        pd.show();
//        //patch and name of image to be stored in firebase storage
//        //e.g. Users_Profile_Cover_Imgs/image_e124214214.jpg
//        //e.g. Users_Profile_Cover_Imgs/cover_e1h1423524.jpg
//        String filePatchAndName = storagePatch + "" + profileOnCoverPhoto + "" + "1";
//        StorageReference storageReference2nd = storageReference.child(filePatchAndName);
//        storageReference2nd.putFile(uri)
//                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                        //image is uploaded to storage, now get it's url and storage in user's database
//                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
//                        while (!uriTask.isSuccessful()) ;
//                        Uri downloadUri = uriTask.getResult();
//
//                        //check if image is uploaded or not and url is received
//                        if (uriTask.isSuccessful()) {
//                            //image uploaded
//                            //add/uploadate url in user's database
//                            HashMap<String, Object> results = new HashMap<>();
//
//                            results.put(profileOnCoverPhoto, downloadUri.toString());
//
//                        } else {
//                            //error
//                            pd.dismiss();
//                            Toast.makeText(FotoPublic.this, "Some error occured", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        pd.dismiss();
//                        Toast.makeText(FotoPublic.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//
//                    }
//                });
//

    }


    private Uri getImageUri(Context context, Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "title", null);
        return Uri.parse(path);


    }


    private void clearAll() {
        if (imageModelArrayList != null) {

            imageModelArrayList.clear();

            if (recyclerImageAdapter != null) {
                recyclerImageAdapter.notifyDataSetChanged();
            }
        }
        imageModelArrayList = new ArrayList<>();
    }


    public void onBackPressed() {
        try {
            Intent intent = new Intent(FotoPublic.this, profileId.class); // try = попытка
            startActivity(intent);
            finish();
        } catch (Exception e) {  // catch = ловить
        }
    }


}