package com.example.skday.anlette.activity;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.skday.anlette.R;
import com.example.skday.anlette.base.BaseActivity;
import com.example.skday.anlette.databinding.ActivityMainBinding;
import com.example.skday.anlette.databinding.ActivityZoomBinding;
import com.example.skday.anlette.model.AnlPhoto;

import java.io.File;

public class ZoomActivity extends BaseActivity {

    private ActivityZoomBinding binding;
    private String photoName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_zoom);
        binding.setMain(this);
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle("Palette");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        photoName = getIntent().getStringExtra("PHOTO_NAME");
        findPalette();
    }

    public void findPalette(){
        File path = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (path.exists()){
            String[] paletteName = path.list();
            if (paletteName != null && paletteName.length != 0) {
                Bitmap img;
                Log.i("Path", "setListPalette: " + path.getPath() + "/" + paletteName[0] + ", length = " +paletteName.length);
                Log.i("Path", "finding: palette name = PAL_"+photoName);
                for (int i = 0; i < paletteName.length; i++) {
                    if (paletteName[i].substring(0, 4).equals("PAL_")) {
                        Log.i("Path", "finding: "+paletteName[i]);
                        if (paletteName[i].equals("PAL_"+photoName)) {
                            Log.i("Path", "finding: found");
                            img = BitmapFactory.decodeFile(path.getPath() + "/" + paletteName[i]);
                            if (img != null) {
                                binding.image.setImageBitmap(img);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }
}
