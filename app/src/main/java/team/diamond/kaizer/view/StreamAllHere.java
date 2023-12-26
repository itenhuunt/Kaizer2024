package team.diamond.kaizer.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import team.diamond.kaizer.R;
import team.diamond.kaizer.kaizerActivity;
import team.diamond.kaizer.profileId;

public class StreamAllHere extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stream_all_here);

    }



    public void onBackPressed() {
        try {
            Intent intent = new Intent(StreamAllHere.this, kaizerActivity.class); // try = попытка
            startActivity(intent);
            finish();
        } catch (Exception e) {  // catch = ловить
        }
    }
}