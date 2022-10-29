package team.diamond.kaizer.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import team.diamond.kaizer.R;
import team.diamond.kaizer.kaizerActivity;
import team.diamond.kaizer.profileId;

public class profileBalance extends AppCompatActivity {


    private SharedPreferences pay;
    int payments;

    TextView profile_id, balance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_balance);

        pay = getSharedPreferences("pay", MODE_PRIVATE);
        payments = pay.getInt("payment_for_basic_test", 0);

        hooks();

        balance.setText(" " + payments);


    }


    private void hooks() {
        profile_id = findViewById(R.id.profile_id);
        balance = findViewById(R.id.balance);

    }


    public void onBackPressed() {
        try {
            Intent intent = new Intent(profileBalance.this, profileId.class); // try = попытка
            startActivity(intent);
            finish();
        } catch (Exception e) {  // catch = ловить
        }
    }


}

