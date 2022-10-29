package team.diamond.kaizer.creatTest;

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

import team.diamond.kaizer.R;
import team.diamond.kaizer.startTest.AllTests;


public class creatTest extends AppCompatActivity {


    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://kaizerver3-default-rtdb.firebaseio.com/");

    EditText testnamever2,Question,oA,oB,oC;
    Button registerBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.creat_test);

        hooks();


        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //get data from EditTexts into String variables
                String nametest = testnamever2.getText().toString();
                final String QuestionTxt = Question.getText().toString();
                final String oATxt = oA.getText().toString();
                final String oBTxt = oB.getText().toString();
                final String oCTxt = oC.getText().toString();

                //chek if user all the fields before sending data in Firebase
                if (QuestionTxt.isEmpty() || oATxt.isEmpty() || oCTxt.isEmpty() || oBTxt.isEmpty() || nametest.isEmpty()) {
                    Toast.makeText(creatTest.this, "все поля должны быть заполнены / Fill all fields", Toast.LENGTH_SHORT).show();
                } else {


                    databaseReference.child("random").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            //chek if phone no registered before

                            //  не работает проверка вопроса
                            if (snapshot.child(nametest).hasChild(QuestionTxt)) {
                                Toast.makeText(creatTest.this, "Question is already is used", Toast.LENGTH_SHORT).show();
                            } else {

                                //  прописываем  целое число как начало
                                //    availabeleUsers = (int)snapshot.child("QuestBelka").getChildrenCount();

                                //sending data to firebase Realtime Database
                                //we are using phone number as unique identity of every user
                                //so all the ather datails of user comes under phone

                                //   databaseReference.child("PedoUsers").child(String.valueOf(availabeleUsers + 1)).child("fullname").setValue(fullnameTxt);
                                //    прописываем  целое число

//                                databaseReference.child("custom test").child(nametest).child("CustomTest").setValue(nametest);
//                                databaseReference.child("custom test").child(nametest).child(QuestionTxt).child("Question").setValue(QuestionTxt);
//                                databaseReference.child("custom test").child(nametest).child(QuestionTxt).child("quest1").setValue(QuestionTxt);
//                                databaseReference.child("custom test").child(nametest).child(QuestionTxt).child("oA").setValue(oATxt);
//                                databaseReference.child("custom test").child(nametest).child(QuestionTxt).child("oB").setValue(oBTxt);
//                                databaseReference.child("custom test").child(nametest).child(QuestionTxt).child("oC").setValue(oCTxt);


                                databaseReference.child("user").child(QuestionTxt).child("Question").setValue(QuestionTxt);
                                databaseReference.child("user").child(QuestionTxt).child("quest1").setValue("резерв хз под что");
                                databaseReference.child("user").child(QuestionTxt).child("oA").setValue(oATxt);
                                databaseReference.child("user").child(QuestionTxt).child("oB").setValue(oBTxt);
                                databaseReference.child("user").child(QuestionTxt).child("oC").setValue(oCTxt);
                                databaseReference.child("user").child(QuestionTxt).child("CustomTest").setValue(nametest);  //  ошибка со стиранием значенпия



                                //show message
                                Toast.makeText(creatTest.this, "Вопрос зарегестрирован успешно", Toast.LENGTH_SHORT).show();

                                clear();

                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }
            }
        });


    }

    private void clear() {
        Question.setText("");
        oA.setText("");
        oB.setText("");
        oC.setText("");
    }


    private void hooks() {
        testnamever2 = findViewById(R.id.testnamever2);
        Question = findViewById(R.id.Question);
        oA = findViewById(R.id.oA);
        oB = findViewById(R.id.oB);
        oC = findViewById(R.id.oC);
        registerBtn = findViewById(R.id.registerNewQuestion);
    }


    public void onBackPressed() {
        try {
            Intent intent = new Intent(creatTest.this, AllTests.class); // try = попытка
            startActivity(intent);
            finish();
        } catch (Exception e) {  // catch = ловить
        }
    }
}