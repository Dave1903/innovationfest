package com.thedevelopershome.innovationfest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.siyamed.shapeimageview.mask.PorterShapeImageView;
import com.thedevelopershome.innovationfest.customfonts.MyTextView_Lato_Medium;
import com.thedevelopershome.innovationfest.customfonts.MyTextView_Roboto_Medium;

public class DetailDescription extends AppCompatActivity {

    MyTextView_Roboto_Medium eveName;
    PorterShapeImageView postImg;
    MyTextView_Lato_Medium description;
    TextView activity;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_description);

        Bundle bundle = getIntent().getExtras();


        eveName=(MyTextView_Roboto_Medium)findViewById(R.id.eveName);
        postImg=(PorterShapeImageView) findViewById(R.id.postimage);
        description=(MyTextView_Lato_Medium) findViewById(R.id.description);
        activity=(TextView) findViewById(R.id.activity);

        eveName.setText(bundle.getString("name"));
        description.setText(bundle.getString("description"));
        Glide
                .with(this)
                .load(bundle.getString("url"))
                .dontAnimate()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(postImg);

        activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DetailDescription.this, "CLICK", Toast.LENGTH_SHORT).show();
            }
        });



    }
}
