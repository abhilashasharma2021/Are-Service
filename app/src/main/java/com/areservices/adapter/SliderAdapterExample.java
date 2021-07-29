package com.areservices.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.areservices.R;
import com.areservices.model.SliderModel;
import com.bumptech.glide.Glide;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.List;


public class SliderAdapterExample extends SliderViewAdapter<SliderAdapterExample.SliderAdapterVH> {

    private Context context;

    List<SliderModel> dataAdapters;

    public SliderAdapterExample(List<SliderModel> getDataAdapter, Context context) {
        this.context = context;
        this.dataAdapters = getDataAdapter;
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_layout_item, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, final int position) {
     final SliderModel dataAdapterOBJ = dataAdapters.get(position);

     if (!dataAdapterOBJ.equals("")){

        /*   if(position==0){
            Picasso.get().load(R.drawable.gym).into(viewHolder.imageViewBackground);
        }*/


         try {
             Glide.with(viewHolder.itemView)
                     .load(dataAdapterOBJ.getPath()+dataAdapterOBJ.getImageBanner())
                     .into(viewHolder.imageViewBackground);
         } catch (Exception e) {
             e.printStackTrace();
         }


     }



    }



    @Override
    public int getCount() {
        //slider view count could be dynamic size
        return dataAdapters.size();
    }

    class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        View itemView;
        ImageView imageViewBackground;
        TextView textViewDescription;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.iv_auto_image_slider);
           // textViewDescription = itemView.findViewById(R.id.txt_details);
            this.itemView = itemView;
        }
    }


}