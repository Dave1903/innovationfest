package com.thedevelopershome.innovationfest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;

import com.vansuita.materialabout.builder.AboutBuilder;
import com.vansuita.materialabout.views.AboutView;

public class MyAboutView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_about_view);

        Bundle bundle = getIntent().getExtras();


        String flag = bundle.getString("flag");




        ViewGroup.LayoutParams linLayoutParam = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT );

        if(flag.equals("1")) {

            AboutView view = AboutBuilder.with(this)
                    .setPhoto(R.drawable.dave)
                    .setCover(R.mipmap.profile_cover)
                    .setName("Davinder singh")
                    .setSubTitle("Software Developer")
                    .setBrief("I design with Code. \n" +
                            "Pretentious? Let me tone that down for you. \n" +
                            "I'm Davinder, currently an Undergrad student, taking strides in the field of Application development. \n" +
                            "With a passion for Programming and a knack for solving problems with Code, I'm looking to make a career out of this! \n" +
                            "See you in my next App and the others that come thereafter, Peace!")
                    .setAppIcon(R.mipmap.ic_launcher)
                    .setAppName(R.string.app_name)
                    .addGooglePlayStoreLink("6268492173877335137")
                    .addGitHubLink("Dave1903")
                    .addFacebookLink("100003699012025")
                    .addInstagramLink("dave_x97")
                    .addLinkedInLink("in/davinder-singh-34003b13a")
                    .addFiveStarsAction()
                    .setVersionNameAsAppSubTitle()
                    .addShareAction(R.string.app_name)
                    .addFeedbackAction("19davinder@gmail.com")
                    .addUpdateAction()
                    .setWrapScrollView(true)
                    .setLinksAnimated(true)
                    .setShowAsCard(true)
                    .build();

            addContentView(view, linLayoutParam);

        }

        else
            if(flag.equals("2")){
                AboutView view = AboutBuilder.with(this)
                        .setPhoto(R.drawable.innov)
                        .setCover(R.drawable.cover)
                        .setName("Innovation Cell")
                        .setSubTitle("NITRR")
                        .setBrief("The purpose of this organisation is to provide a common ground where the students of this institution can work on there innovative ideas .\n" +
                                "Objective:-\n" +
                                "- To facilitate students from Ideas to working prototype which can further be patented.\n" +
                                "- To inculcate a culture of innovation driven entrepreneurship.\n Our Location \n" + "NIT Raipur, Amanaka,, Raipur, Chhattisgarh 492001, IN")

                        .addFacebookLink("innovationcell.nitrr")
                        .addInstagramLink("explore/locations/1510712609155068/innovation-cell-nit-raipur")
                        .addLinkedInLink("/company/innovation-cell-nit-raipur/about")
                        .setAppIcon(R.drawable.mhrd)
                        .setAppName("powered by \nMHRD")
                        .setWrapScrollView(true)
                        .setLinksAnimated(true)
                        .setShowAsCard(true)
                        .build();

                addContentView(view, linLayoutParam);

            }









    }
}
