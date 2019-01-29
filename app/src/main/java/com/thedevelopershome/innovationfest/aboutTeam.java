package com.thedevelopershome.innovationfest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class aboutTeam extends AppCompatActivity {


    RecyclerView recyclerView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_team);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        progressBar = (ProgressBar) findViewById(R.id.progd);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        progressBar.setVisibility(View.VISIBLE);

        progressBar.setVisibility(View.VISIBLE);
        getData(All_urls.values.pendingData);
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
                                    MapModel mapModel = new MapModel();

                                    JSONObject post = arr.getJSONObject(i).getJSONObject("post");
                                    mapModel.setName(post.getString("name"));
                                    mapModel.setPosition(post.getString("position"));
                                    mapModel.setFbUrl(post.getString("fbUrl"));
                                 //   mapModel.setVillage(post.getString("locality"));
                                    mapModel.setImgUrl(post.getString("imgUrl"));
                                 //   mapModel.setId(post.getString("id"));

                                    mapModels.add(mapModel);


                                }

                                ListAdapter adapter = new ListAdapter(aboutTeam.this,mapModels,2);
                                recyclerView.setAdapter(adapter);

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
