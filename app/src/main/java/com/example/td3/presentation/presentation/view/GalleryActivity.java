package com.example.td3.presentation.presentation.view;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.td3.R;
import com.squareup.picasso.Picasso;


public class GalleryActivity extends AppCompatActivity {

    private static final String TAG = "GalleryActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        getIncommingIntent();
    }

    private void getIncommingIntent (){

        if(getIntent().hasExtra("Disc_image_URL")
                && getIntent().hasExtra("Disc_Title")
                && getIntent().hasExtra("Disc_Date")
                && getIntent().hasExtra("Disc_Type")
                && getIntent().hasExtra("Disc_Duree")
                && getIntent().hasExtra("Disc_Genre")
                && getIntent().hasExtra("Disc_Description")
                && getIntent().hasExtra("Disc_StyleImg")){

            String DiscUrl = getIntent().getStringExtra("Disc_image_URL");
            String DiscTitle = getIntent().getStringExtra("Disc_Title");
            String DiscDate = getIntent().getStringExtra("Disc_Date");
            String DiscType = getIntent().getStringExtra("Disc_Type");
            String DiscDuree = getIntent().getStringExtra("Disc_Duree");
            String DiscGenre = getIntent().getStringExtra("Disc_Genre");
            String DiscDescription = getIntent().getStringExtra("Disc_Description");
            String DiscStyleImg = getIntent().getStringExtra("Disc_StyleImg");

            setImage(DiscUrl, DiscTitle, DiscDate, DiscType, DiscDuree, DiscGenre, DiscDescription, DiscStyleImg);
        }
    }

    private void setImage(String DiscUrl,
                          String DiscTitle,
                          String DiscDate,
                          String DiscType,
                          String DiscDuree,
                          String DiscGenre,
                          String DiscDescription,
                          String DiscStyleImg){

        TextView name = findViewById(R.id.Disc_name);
        name.setText(DiscTitle);

        TextView date = findViewById(R.id.Disc_date);
        date.setText(DiscDate);

        TextView type = findViewById(R.id.Disc_type);
        type.setText(DiscType);

        TextView duree = findViewById(R.id.Disc_duree);
        duree.setText(DiscDuree);

        TextView genre = findViewById(R.id.Disc_genre);
        genre.setText(DiscGenre);

        TextView description = findViewById(R.id.Disc_description);
        description.setText(DiscDescription);

        ImageView cover = findViewById(R.id.Cover);
        Picasso.with(this)
                .load(DiscUrl)
                .into(cover);

        ImageView Style = findViewById(R.id.Style);
        Picasso.with(this)
                .load(DiscStyleImg)
                .into(Style);
    }
}
