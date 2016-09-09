package com.example.skday.anlette;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.util.Log;
import android.view.View;

import com.example.skday.anlette.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private static final int CAMERA_REQUEST = 1888;
    private Bitmap image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        binding.setMain(this);
        binding.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (image != null){
            binding.palette1.setVisibility(View.VISIBLE);
            binding.palette2.setVisibility(View.VISIBLE);
            binding.palette3.setVisibility(View.VISIBLE);
            binding.palette4.setVisibility(View.VISIBLE);
            binding.palette5.setVisibility(View.VISIBLE);
            binding.palette6.setVisibility(View.VISIBLE);
            Palette.from(image).generate(new Palette.PaletteAsyncListener(){
                @Override
                public void onGenerated(Palette palette) {
                    Palette.Swatch swatch = palette.getVibrantSwatch();
                    String hexValue;
                    if (swatch != null){
                        binding.palette1.setBackgroundColor(swatch.getRgb());
                        hexValue = "#"+Integer.toHexString(swatch.getRgb()).substring(2);
                        binding.textPalette1.setText(hexValue);
                        binding.textPalette1.setTextColor(swatch.getTitleTextColor());
                    }else {
                        binding.palette1.setVisibility(View.GONE);
                    }

                    swatch = palette.getMutedSwatch();
                    if (swatch != null){
                        binding.palette2.setBackgroundColor(swatch.getRgb());
                        hexValue = "#"+Integer.toHexString(swatch.getRgb()).substring(2);
                        binding.textPalette2.setText(hexValue);
                        binding.textPalette2.setTextColor(swatch.getTitleTextColor());
                    }else {
                        binding.palette2.setVisibility(View.GONE);
                    }

                    swatch = palette.getLightVibrantSwatch();
                    if (swatch != null){
                        binding.palette3.setBackgroundColor(swatch.getRgb());
                        hexValue = "#"+Integer.toHexString(swatch.getRgb()).substring(2);
                        binding.textPalette3.setText(hexValue);
                        binding.textPalette3.setTextColor(swatch.getTitleTextColor());
                    }else {
                        binding.palette3.setVisibility(View.GONE);
                    }

                    swatch = palette.getLightMutedSwatch();
                    if (swatch != null){
                        binding.palette4.setBackgroundColor(swatch.getRgb());
                        hexValue = "#"+Integer.toHexString(swatch.getRgb()).substring(2);
                        binding.textPalette4.setText(hexValue);
                        binding.textPalette4.setTextColor(swatch.getTitleTextColor());
                    }else {
                        binding.palette4.setVisibility(View.GONE);
                    }

                    swatch = palette.getDarkVibrantSwatch();
                    if (swatch != null){
                        binding.palette5.setBackgroundColor(swatch.getRgb());
                        hexValue = "#"+Integer.toHexString(swatch.getRgb()).substring(2);
                        binding.textPalette5.setText(hexValue);
                        binding.textPalette5.setTextColor(swatch.getTitleTextColor());
                    }else {
                        binding.palette5.setVisibility(View.GONE);
                    }

                    swatch = palette.getDarkMutedSwatch();
                    if (swatch != null){
                        binding.palette6.setBackgroundColor(swatch.getRgb());
                        hexValue = "#"+Integer.toHexString(swatch.getRgb()).substring(2);
                        binding.textPalette6.setText(hexValue);
                        binding.textPalette6.setTextColor(swatch.getTitleTextColor());
                    }else {
                        binding.palette6.setVisibility(View.GONE);
                    }
                    swatch = palette.getDominantSwatch();
                    if (swatch != null){
                        binding.paletteDominan.setBackgroundColor(swatch.getRgb());
                        hexValue = "#"+Integer.toHexString(swatch.getRgb()).substring(2);
                        binding.paletteDominan.setText(hexValue);
                        binding.paletteDominan.setTextColor(swatch.getTitleTextColor());
                    }
                }
            });
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            image = (Bitmap) data.getExtras().get("data");
            binding.image.setImageBitmap(image);
        }
    }
}
