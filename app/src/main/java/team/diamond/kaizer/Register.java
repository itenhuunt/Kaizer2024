package team.diamond.kaizer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Register extends AppCompatActivity {


    public String inkognito = "no_name"; // присваиваем no name по умолчанию
    private SharedPreferences user_name_shared_preferences;   // обяъвляем  SharedPreferences


    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://kaizerver3-default-rtdb.firebaseio.com/");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);


        user_name_shared_preferences = getSharedPreferences("teen_pref", MODE_PRIVATE); //обяъвляем приватный режим + прописываем ИМЯ в xml (.xml так и будет называться) + приватный режим
        inkognito = user_name_shared_preferences.getString("teen_name", inkognito);//  в этом xml опять пишем имя нашей белки  +  которое задаем значение teen_name


        final EditText name = findViewById(R.id.r_nametxt);
        final EditText mobile = findViewById(R.id.r_phonetxt);
        final EditText email = findViewById(R.id.r_emailtxt);
        final AppCompatButton registerBtn = findViewById(R.id.r_registredBtn);

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");


        //chek if user already logged in
        if (!team.diamond.kaizer.MemoryData.getData(this).isEmpty()) {

            Intent intent = new Intent(Register.this, team.diamond.kaizer.MainActivity.class);
            intent.putExtra("mobile", team.diamond.kaizer.MemoryData.getData(this));    //зачем вытаскиваем ??? телефон / имя / почту
            intent.putExtra("name", team.diamond.kaizer.MemoryData.getName(this));    //  указываем ТОЧНОЕ ЗНАЧЕНИЕ
            //    intent.putExtra("email","emailTxt");      //  т.к. оно будет вытаскиваться в MainActivity.class
            intent.putExtra("email", "");      //  35 18  правильно ?
            startActivity(intent);
            finish();

        }

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog.show();

                final String nameTxt = name.getText().toString();
                final String mobileTxt = mobile.getText().toString();
                final String emailTxt = email.getText().toString();

                if (nameTxt.isEmpty() || mobileTxt.isEmpty() || emailTxt.isEmpty()) {
                    Toast.makeText(Register.this, "All Fields Required!!!", Toast.LENGTH_SHORT).show();


                    progressDialog.dismiss();

                } else {
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            progressDialog.dismiss();

                            if (snapshot.child("users").hasChild(mobileTxt)) {    // если имеет снимок  mobileTxt  в  users  показывает сообщение
                                Toast.makeText(Register.this, "Mobile alredy exists", Toast.LENGTH_SHORT).show();
                            } else {
                                databaseReference.child("users").child(mobileTxt).child("email").setValue(emailTxt);
                                databaseReference.child("users").child(mobileTxt).child("name").setValue(nameTxt);
                                databaseReference.child("users").child(mobileTxt).child("profile_pic").setValue("");

                                inkognito = nameTxt;

                                SharedPreferences.Editor editor = user_name_shared_preferences.edit(); //SharedPreferens edit редактируем ОЧКИ (счет/count)
                                editor.putString("teen_name", inkognito); // положить целое число в ...
                                editor.commit();//каммит фиксируем ) кам кам  сак   кам

                                //save mobile to memory
                                team.diamond.kaizer.MemoryData.saveData(mobileTxt, Register.this);

                                //save name to memory
                                team.diamond.kaizer.MemoryData.saveName(nameTxt, Register.this);

                                Toast.makeText(Register.this, "Success", Toast.LENGTH_SHORT).show();


                                Intent intent = new Intent(Register.this, team.diamond.kaizer.MainActivity.class);
                                intent.putExtra("mobile", mobileTxt);    //зачем вытаскиваем ??? телефон / имя / почту
                                intent.putExtra("name", nameTxt);    //  указываем ТОЧНОЕ ЗНАЧЕНИЕ
                                intent.putExtra("email", emailTxt);      //  т.к. оно будет вытаскиваться в MainActivity.class


                                startActivity(intent);
                                finish();

                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                            progressDialog.dismiss();
                        }
                    });
                }


            }
        });


    }
}