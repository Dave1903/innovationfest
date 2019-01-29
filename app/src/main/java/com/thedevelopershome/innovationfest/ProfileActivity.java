package com.thedevelopershome.innovationfest;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.thedevelopershome.innovationfest.customfonts.MyTextView_Lato_Medium;
import com.thedevelopershome.innovationfest.customfonts.MyTextView_Roboto_Bold;

public class ProfileActivity extends AppCompatActivity {

    private MyTextView_Roboto_Bold name,branch,collagename,regId,email,rollNo,mobile;
    private TextView save;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        sharedPreferences=getSharedPreferences("userData", Activity.MODE_PRIVATE);


        name=(MyTextView_Roboto_Bold)findViewById(R.id.nameEdit);
        branch=(MyTextView_Roboto_Bold)findViewById(R.id.branch);
        collagename=(MyTextView_Roboto_Bold)findViewById(R.id.clgName);
        regId=(MyTextView_Roboto_Bold)findViewById(R.id.appid);
        email=(MyTextView_Roboto_Bold)findViewById(R.id.email);
        rollNo=(MyTextView_Roboto_Bold)findViewById(R.id.roll);
        mobile=(MyTextView_Roboto_Bold)findViewById(R.id.mobile);

        save=(TextView)findViewById(R.id.save);

        save.setText("EDIT");

        name.setText(sharedPreferences.getString("name","dave"));

        branch.setText(sharedPreferences.getString("branch","mining"));

        collagename.setText(sharedPreferences.getString("clgName","nitrr"));

        regId.setText(sharedPreferences.getString("regId","INCDAVE1159"));

        email.setText(sharedPreferences.getString("email","19davinder@gmail.com"));

        rollNo.setText(sharedPreferences.getString("rollNo","15121018"));

        mobile.setText(sharedPreferences.getString("userphoneno","7587001159"));


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ProfileActivity.this,AccountKitActivity.class);
                startActivity(intent);
            }
        });







    }
}
