package com.example.skday.anlette.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.skday.anlette.R;
import com.example.skday.anlette.activity.ZoomActivity;
import com.example.skday.anlette.model.AnlPhoto;

import java.util.List;

/**
 * Created by skday on 10/12/16.
 */

public class PaletteAdapter extends RecyclerView.Adapter<PaletteAdapter.ViewHolder> {

    private List<AnlPhoto> listPalette;
    private Context context;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Bitmap img = listPalette.get(position).getBitmap();
        holder.pallet.setImageBitmap(img);
        holder.pallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ZoomActivity.class);
                intent.putExtra("PHOTO_NAME", listPalette.get(position).getName());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (listPalette==null){
            return 0;
        }else {
            return listPalette.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView pallet;

        public ViewHolder(View itemView){
            super(itemView);
            pallet = (ImageView) itemView.findViewById(R.id.palette);
        }
    }

    public PaletteAdapter (Context context, List<AnlPhoto> listPalette){
        this.context = context;
        this.listPalette = listPalette;
    }
}
