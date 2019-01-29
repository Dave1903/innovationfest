package com.thedevelopershome.innovationfest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.siyamed.shapeimageview.mask.PorterShapeImageView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by apple on 01/04/16.
 */
public class ListBaseAdapter extends BaseAdapter {


    Context context;

    ArrayList<PostModel> postModels;
    Typeface fonts1,fonts2;





    public ListBaseAdapter(Context context, ArrayList<PostModel> postModels) {


        this.context = context;
        this.postModels = postModels;
    }


    @Override
    public int getCount() {
        return postModels.size();
    }

    @Override
    public Object getItem(int position) {
        return postModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        fonts1 =  Typeface.createFromAsset(context.getAssets(),
                "fonts/Lato-Light.ttf");

        fonts2 = Typeface.createFromAsset(context.getAssets(),
                "fonts/Lato-Regular.ttf");

        ViewHolder viewHolder = null;

        if (convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list,null);

            viewHolder = new ViewHolder();


            viewHolder.post = convertView.findViewById(R.id.postimage);
            viewHolder.profile = convertView.findViewById(R.id.post_profile_image);
            viewHolder.name = convertView.findViewById(R.id.txtpostname);

           // viewHolder.profile.setImageResource(postModels.get(position).getProfile());
            viewHolder.name.setText(postModels.get(position).getName());

            Glide
                    .with(context)
                    .load(postModels.get(position).getImgUrl())
                    .dontAnimate()
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .into(viewHolder.post);




            convertView.setTag(viewHolder);


        }else {

            viewHolder = (ViewHolder)convertView.getTag();
        }




          convertView.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  Intent intent=new Intent((MainActivity)context,DetailDescription.class);
                  intent.putExtra("name",postModels.get(position).getName());
                  intent.putExtra("description",postModels.get(position).getDescription());
                  intent.putExtra("url",postModels.get(position).getImgUrl());

                  context.startActivity(intent);
              }
          });







      //  viewHolder.profile.setImageResource(postModels.get(position).getProfile());
        viewHolder.name.setText(postModels.get(position).getName());
        Glide
                .with(context)
                .load(postModels.get(position).getImgUrl())
                .dontAnimate()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(viewHolder.post);







        return convertView;
    }

    private class ViewHolder{


        ImageView more;

        TextView time;

        PorterShapeImageView post;
        CircleImageView profile;
        TextView name ;







    }
}

