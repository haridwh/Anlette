package com.example.skday.anlette.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.skday.anlette.R;
import com.example.skday.anlette.adapter.PaletteAdapter;
import com.example.skday.anlette.base.BaseActivity;
import com.example.skday.anlette.databinding.ActivityMainBinding;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private ActivityMainBinding binding;
    private List<Bitmap> listPalette;
    private RecyclerView recyclerView;
    private PaletteAdapter adapter;
    private BitmapFactory.Options options;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setMain(this);
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle("Anlette");
        recyclerView = (RecyclerView) findViewById(R.id.list_palette);
        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void onResume() {
        super.onResume();
        options = new BitmapFactory.Options();
        options.inSampleSize = 8;
        listPalette = new ArrayList<>();
        setListPalette();
        if (listPalette==null){
            adapter = new PaletteAdapter(this, null);
        }else {
            adapter = new PaletteAdapter(this, listPalette);
        }
        recyclerView.setAdapter(adapter);
    }

    public void takePicture(View view){
        Intent intent = new Intent(this,Result.class);
        intent.putExtra("Take", true);
        startActivity(intent);
    }

    public void setListPalette(){
        File path = new File(Environment.getExternalStorageDirectory().getPath()+"/Pictures/Anlette");
        Log.i("Path", "setListPalette: "+path.getPath());
        if (path.exists()){
            String[] paletteName = path.list();
            Bitmap palette;
            Log.i("Path", "setListPalette: "+path.getPath()+"/"+paletteName[0]);
            for(int i=0; i<paletteName.length; i++)
            {
                palette = BitmapFactory.decodeFile(path.getPath()+"/"+paletteName[i]);
                listPalette.add(palette);
            }
        }
    }

}
