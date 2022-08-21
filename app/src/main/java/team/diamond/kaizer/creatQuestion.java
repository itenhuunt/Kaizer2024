package team.diamond.kaizer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class creatQuestion extends AppCompatActivity {


    //creat object of DatabaseReference class to access firebase Realtime Database
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://kaizerver3-default-rtdb.firebaseio.com/");

    //  добавляем например счетчик количества JOB/заданий
  //    private int availabeleUsers = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.creat_question);

        final EditText Question = findViewById(R.id.Question);
        final EditText oA = findViewById(R.id.oA);
        final EditText oB = findViewById(R.id.oB);
        final EditText oC = findViewById(R.id.oC);

        final Button registerBtn = findViewById(R.id.registerNewQuestion);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //get data from EditTexts into String variables
                final String QuestionTxt = Question.getText().toString();
                final String oATxt = oA.getText().toString();
                final String oBTxt = oB.getText().toString();
                final String oCTxt = oC.getText().toString();

                //chek if user all the fields before sending data in Firebase
                if(QuestionTxt.isEmpty()  || oATxt.isEmpty()|| oCTxt.isEmpty()  || oBTxt.isEmpty()){
                    Toast.makeText(creatQuestion.this," Fill all fields",Toast.LENGTH_SHORT).show();
                }

                //chek if passwords are matching with each other
                //if not matching with each other then swo a toast message
//                else if (!oA.equals(oB)){
//                    Toast.makeText(creatQuestion.this,"pas not matching",Toast.LENGTH_SHORT).show();
//                }

                else{



                    databaseReference.child("QuestBelka").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            //chek if phone no registered before

                            if(snapshot.hasChild(QuestionTxt)){
                                Toast.makeText(creatQuestion.this,"Question is already is used",Toast.LENGTH_SHORT).show();
                            }
                            else{

                                //  прописываем  целое число как начало
                              //    availabeleUsers = (int)snapshot.child("QuestBelka").getChildrenCount();

                                //sending data to firebase Realtime Database
                                //we are using phone number as unique identity of every user
                                //so all the ather datails of user comes under phone

                                //   databaseReference.child("PedoUsers").child(String.valueOf(availabeleUsers + 1)).child("fullname").setValue(fullnameTxt);
                                //    прописываем  целое число

                                databaseReference.child("QuestBelka").child(QuestionTxt).child("Question").setValue(QuestionTxt);
                                databaseReference.child("QuestBelka").child(QuestionTxt).child("quest1").setValue(QuestionTxt);

                                databaseReference.child("QuestBelka").child(QuestionTxt).child("oA").setValue(oATxt);
                                databaseReference.child("QuestBelka").child(QuestionTxt).child("oB").setValue(oBTxt);
                                databaseReference.child("QuestBelka").child(QuestionTxt).child("oC").setValue(oCTxt);

                                //show message
                                Toast.makeText(creatQuestion.this,"Вопрос зарегестрирован успешно",Toast.LENGTH_SHORT).show();

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });


//        loginNowBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });

    }

    public void onBackPressed(){
        try{
            Intent intent = new Intent(creatQuestion.this,MainActivity.class); // try = попытка
            startActivity(intent);
            finish();
        }catch (Exception e){  // catch = ловить
        }
    }
}