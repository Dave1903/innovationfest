package com.thedevelopershome.innovationfest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.text.DecimalFormat;
import java.util.ArrayList;

import es.dmoral.toasty.Toasty;


public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {
    Context context;

    SharedPreferences preferences;

    private ArrayList<MapModel> mapModels;
    int type;





    public ListAdapter(Context context, ArrayList<MapModel> maplist, int type) {

        this.context = context;

        preferences = context.getSharedPreferences("mypref",Context.MODE_PRIVATE);
        this.mapModels = maplist;
        this.type= type;
    }


    @Override
    public int getItemViewType(int position) {

        return type;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        if(viewType==1)
         v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pending, parent, false);
        else
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pending, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder vh, final int position) {

        if(type==1) {


        }else {
            vh.desc.setText(mapModels.get(position).getName());
            vh.lat.setText(mapModels.get(position).getPosition());
            Glide
                    .with(context)
                    .load(mapModels.get(position).getImgUrl())
                    .dontAnimate()
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .into(vh.imageView);
            vh.approve.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Uri uriUrl = Uri.parse(mapModels.get(position).getFbUrl());
                    Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                    context.startActivity(launchBrowser);
                }
            });

        }

        
        

    }

    @Override
    public int getItemCount() {
        return mapModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        TextView latitude,longitude,village,river,desc,lat;
        ImageView imageView;
        ImageView approve;
        ProgressBar progressBar;


        public MyViewHolder(View v) {
            super(v);



            desc= (TextView)itemView.findViewById(R.id.desc);
            lat=(TextView)itemView.findViewById(R.id.lat);
            imageView= (ImageView) itemView.findViewById(R.id.imageView);
            approve= (ImageView) itemView.findViewById(R.id.claim);
            progressBar= (ProgressBar) itemView.findViewById(R.id.prog);




        }
    }

    private void approvePost(String url, final int pos, final MyViewHolder vh) {
        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String p) {
                        // display response
                        Log.d("Response", p.toString());


                        try {

                            if (p.length() > 0) {

                                vh.progressBar.setVisibility(View.GONE);
                                vh.approve.setVisibility(View.VISIBLE);

                                mapModels.remove(pos);
                                notifyDataSetChanged();

                                Toasty.success(context,"Post Approved",Toast.LENGTH_LONG).show();




                            }


                        } catch (Exception e) {
                            // JSON error
                            e.printStackTrace();
                            vh.progressBar.setVisibility(View.GONE);
                            vh.approve.setVisibility(View.VISIBLE);



                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", "");
                        vh.progressBar.setVisibility(View.GONE);
                        vh.approve.setVisibility(View.VISIBLE);


                    }
                }
        );


        AppController.getInstance().addToRequestQueue(getRequest,"");
    }


}
