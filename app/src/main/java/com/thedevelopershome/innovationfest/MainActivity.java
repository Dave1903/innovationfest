package com.thedevelopershome.innovationfest;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.Arrays;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.thedevelopershome.innovationfest.menu.DrawerAdapter;
import com.thedevelopershome.innovationfest.menu.DrawerItem;
import com.thedevelopershome.innovationfest.menu.SimpleItem;
import com.thedevelopershome.innovationfest.menu.SpaceItem;
import com.yarolegovich.slidingrootnav.SlideGravity;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements DrawerAdapter.OnItemSelectedListener {

    private ExpandableHeightListView listview;

    private ListBaseAdapter baseAdapter;

    private static final int POS_DASHBOARD = 0;
    private static final int POS_ACCOUNT = 1;
    private static final int POS_MESSAGES = 3;
    private static final int POS_CART = 4;
    private static final int POS_LOGOUT = 5;

    private String[] screenTitles;
    private Drawable[] screenIcons;


    ImageView imageView;



    private ArrayList<PostModel> postModelArrayList;

    SlidingRootNav slidingRootNavBuilder;

    private SharedPreferences sharedPreferences;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar) findViewById(R.id.progb);

        progressBar.setVisibility(View.VISIBLE);

        progressBar.setVisibility(View.VISIBLE);
        getData(All_urls.values.eventUrl);


        sharedPreferences=getSharedPreferences("userData", Activity.MODE_PRIVATE);

        if (sharedPreferences.getString("name", "").equals("")){
            Intent intent=new Intent(MainActivity.this,AccountKitActivity.class);
            startActivity(intent);

        }


        listview = (ExpandableHeightListView)findViewById(R.id.listview);
        imageView=(ImageView)findViewById(R.id.drawmenu);

          slidingRootNavBuilder=   new SlidingRootNavBuilder(this)
                .withMenuOpened(false) //Initial menu opened/closed state. Default == false
                .withMenuLocked(false) //If true, a user can't open or close the menu. Default == false.
                .withDragDistance(140) //Horizontal translation of a view. Default == 180dp
                .withRootViewScale(0.7f) //Content view's scale will be interpolated between 1f and 0.7f. Default == 0.65f;
                .withRootViewElevation(10) //Content view's elevation will be interpolated between 0 and 10dp. Default == 8.
                .withRootViewYTranslation(4)
                .withMenuLayout(R.layout.menu_left_drawer)
                .withGravity(SlideGravity.LEFT) //If LEFT you can swipe a menu from left to right, if RIGHT - the direction is opposite.
                .withSavedState(savedInstanceState) //If you call the method, layout will restore its opened/closed state
                .withContentClickableWhenMenuOpened(true)
                .inject();//Pretty self-descriptive. Builder Default == true

        postModelArrayList = new ArrayList<>();




        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slidingRootNavBuilder.openMenu();
            }
        });



        screenIcons = loadScreenIcons();
        screenTitles = loadScreenTitles();

        DrawerAdapter adapter = new DrawerAdapter(Arrays.asList(
                createItemFor(POS_DASHBOARD).setChecked(true),
                createItemFor(POS_ACCOUNT),
                createItemFor(2),
                createItemFor(POS_MESSAGES),
                createItemFor(POS_CART),
                createItemFor(POS_LOGOUT)));
        adapter.setListener(this);


        RecyclerView list = findViewById(R.id.list);
        list.setNestedScrollingEnabled(false);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);

        adapter.setSelected(POS_DASHBOARD);

    }

    @Override
    protected void onResume() {
        super.onResume();
        sharedPreferences=getSharedPreferences("userData", Activity.MODE_PRIVATE);

        if (sharedPreferences.getString("name", "").equals("")){
            Intent intent=new Intent(MainActivity.this,AccountKitActivity.class);
            startActivity(intent);

        }
    }

    @Override
    public void onItemSelected(int position) {

        if (position == POS_LOGOUT) {
            finish();
        }
        else
            if(position==POS_CART){
                Intent intent=new Intent(MainActivity.this,MyAboutView.class);
                intent.putExtra("flag","1");
                startActivity(intent);
                }
            else
            if(position==POS_ACCOUNT){
                Intent intent=new Intent(MainActivity.this,ProfileActivity.class);
                 startActivity(intent);
            }
            else
            if(position==2){
                Intent intent=new Intent(MainActivity.this,MyAboutView.class);
                intent.putExtra("flag","2");
                startActivity(intent);
            }
            else
            if(position==POS_MESSAGES){
                Intent intent=new Intent(MainActivity.this,aboutTeam.class);

                startActivity(intent);
            }

        slidingRootNavBuilder.closeMenu();
    }


    private DrawerItem createItemFor(int position) {
        return new SimpleItem(screenIcons[position], screenTitles[position])
                .withIconTint(color(R.color.textColorSecondary))
                .withTextTint(color(R.color.textColorPrimary))
                .withSelectedIconTint(color(R.color.colorAccent))
                .withSelectedTextTint(color(R.color.colorAccent));
    }

    private String[] loadScreenTitles() {
        return getResources().getStringArray(R.array.ld_activityScreenTitles);
    }

    private Drawable[] loadScreenIcons() {
        TypedArray ta = getResources().obtainTypedArray(R.array.ld_activityScreenIcons);
        Drawable[] icons = new Drawable[ta.length()];
        for (int i = 0; i < ta.length(); i++) {
            int id = ta.getResourceId(i, 0);
            if (id != 0) {
                icons[i] = ContextCompat.getDrawable(this, id);
            }
        }
        ta.recycle();
        return icons;
    }

    @ColorInt
    private int color(@ColorRes int res) {
        return ContextCompat.getColor(this, res);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    private void getData(String url) {
        final ArrayList<MapModel> mapModels = new ArrayList<>();
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject p) {
                        // display response
                        Log.d("Response", p.toString());

                        progressBar.setVisibility(View.GONE);
                        try {

                            JSONArray arr= p.getJSONArray("posts");


                            if (arr.length() > 0) {

                                for (int i=0;i<arr.length();i++){
                                    PostModel mapModel = new PostModel();

                                    JSONObject post = arr.getJSONObject(i).getJSONObject("post");
                                    mapModel.setName(post.getString("eventName"));
                                    mapModel.setDescription(post.getString("description"));
                                    mapModel.setImgUrl(post.getString("imageUrl"));


                                    postModelArrayList.add(mapModel);


                                }

                                baseAdapter = new ListBaseAdapter(MainActivity.this, postModelArrayList) {
                                };

                                listview.setAdapter(baseAdapter);

                            }


                        } catch (Exception e) {
                            // JSON error
                            e.printStackTrace();
                            progressBar.setVisibility(View.VISIBLE);


                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", "");
                        progressBar.setVisibility(View.VISIBLE);

                    }
                }
        );


        AppController.getInstance().addToRequestQueue(getRequest);
    }

}
