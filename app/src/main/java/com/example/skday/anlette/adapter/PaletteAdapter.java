package com.example.skday.anlette.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.skday.anlette.R;

import java.util.List;

/**
 * Created by skday on 10/12/16.
 */

public class PaletteAdapter extends RecyclerView.Adapter<PaletteAdapter.ViewHolder> {

    private List<Bitmap> listPalette;
    private Context context;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.palette = listPalette.get(position);
        holder.pallet.setImageBitmap(holder.palette);
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
        Bitmap palette;

        public ViewHolder(View itemView){
            super(itemView);
            pallet = (ImageView) itemView.findViewById(R.id.palette);
        }
    }

    public PaletteAdapter (Context context, List<Bitmap> listPalette){
        this.context = context;
        this.listPalette = listPalette;
    }
}
