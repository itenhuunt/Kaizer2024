package team.diamond.kaizer.foto1;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import team.diamond.kaizer.R;

public class NewActivityFree extends AppCompatActivity {


    private ImageView fullImageView;
//    private Button download,share;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);


        fullImageView = findViewById(R.id.fullImageView);

        // загружаем изображение в fullImageView
        Glide.with(this).load(getIntent().getStringExtra("image@#"))
                .into(fullImageView);


    }
}