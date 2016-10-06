package com.example.skday.anlette;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.skday.anlette.databinding.ActivityMainBinding;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

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
                    ColorUtils colorUtils = new ColorUtils();
                    String hexValue;
                    int hex;
                    if (swatch != null){
                        binding.palette1.setBackgroundColor(swatch.getRgb());
                        hexValue = "#"+Integer.toHexString(swatch.getRgb()).substring(2);
                        hex = Integer.parseInt(Integer.toHexString(swatch.getRgb()).substring(2),16);
                        binding.textPalette1.setText(hexValue+"\n"+colorUtils.getColorNameFromHex(hex));
                        binding.textPalette1.setTextColor(swatch.getTitleTextColor());
                    }else {
                        binding.palette1.setVisibility(View.GONE);
                    }

                    swatch = palette.getMutedSwatch();
                    if (swatch != null){
                        binding.palette2.setBackgroundColor(swatch.getRgb());
                        hexValue = "#"+Integer.toHexString(swatch.getRgb()).substring(2);
                        hex = Integer.parseInt(Integer.toHexString(swatch.getRgb()).substring(2),16);
                        binding.textPalette2.setText(hexValue+"\n"+colorUtils.getColorNameFromHex(hex));
                        binding.textPalette2.setTextColor(swatch.getTitleTextColor());
                    }else {
                        binding.palette2.setVisibility(View.GONE);
                    }

                    swatch = palette.getLightVibrantSwatch();
                    if (swatch != null){
                        binding.palette3.setBackgroundColor(swatch.getRgb());
                        hexValue = "#"+Integer.toHexString(swatch.getRgb()).substring(2);
                        hex = Integer.parseInt(Integer.toHexString(swatch.getRgb()).substring(2),16);
                        binding.textPalette3.setText(hexValue+"\n"+colorUtils.getColorNameFromHex(hex));
                        binding.textPalette3.setTextColor(swatch.getTitleTextColor());
                    }else {
                        binding.palette3.setVisibility(View.GONE);
                    }

                    swatch = palette.getLightMutedSwatch();
                    if (swatch != null){
                        binding.palette4.setBackgroundColor(swatch.getRgb());
                        hexValue = "#"+Integer.toHexString(swatch.getRgb()).substring(2);
                        hex = Integer.parseInt(Integer.toHexString(swatch.getRgb()).substring(2),16);
                        binding.textPalette4.setText(hexValue+"\n"+colorUtils.getColorNameFromHex(hex));
                        binding.textPalette4.setTextColor(swatch.getTitleTextColor());
                    }else {
                        binding.palette4.setVisibility(View.GONE);
                    }

                    swatch = palette.getDarkVibrantSwatch();
                    if (swatch != null){
                        binding.palette5.setBackgroundColor(swatch.getRgb());
                        hexValue = "#"+Integer.toHexString(swatch.getRgb()).substring(2);
                        hex = Integer.parseInt(Integer.toHexString(swatch.getRgb()).substring(2),16);
                        binding.textPalette5.setText(hexValue+"\n"+colorUtils.getColorNameFromHex(hex));
                        binding.textPalette5.setTextColor(swatch.getTitleTextColor());
                    }else {
                        binding.palette5.setVisibility(View.GONE);
                    }

                    swatch = palette.getDarkMutedSwatch();
                    if (swatch != null){
                        binding.palette6.setBackgroundColor(swatch.getRgb());
                        hexValue = "#"+Integer.toHexString(swatch.getRgb()).substring(2);
                        hex = Integer.parseInt(Integer.toHexString(swatch.getRgb()).substring(2),16);
                        binding.textPalette6.setText(hexValue+"\n"+colorUtils.getColorNameFromHex(hex));
                        binding.textPalette6.setTextColor(swatch.getTitleTextColor());
                    }else {
                        binding.palette6.setVisibility(View.GONE);
                    }
                    swatch = palette.getDominantSwatch();
                    if (swatch != null){
                        binding.paletteDominan.setBackgroundColor(swatch.getRgb());
                        hexValue = "#"+Integer.toHexString(swatch.getRgb()).substring(2);
                        hex = Integer.parseInt(Integer.toHexString(swatch.getRgb()).substring(2),16);
                        binding.paletteDominan.setText(hexValue+"\n"+colorUtils.getColorNameFromHex(hex));
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

    public void save(View view){
        binding.linearLayout.setDrawingCacheEnabled(true);
        storeImage(binding.linearLayout.getDrawingCache());
        binding.linearLayout.destroyDrawingCache();
    }

    private void storeImage(Bitmap image) {
        File pictureFile = getOutputMediaFile();
        if (pictureFile == null) {
            Toast.makeText(this,"Terjadi Error",Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            image.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();
            Toast.makeText(this,"Gambar Berhasil Disimpan",Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            Toast.makeText(this,"Gambar Gagal Disimpan",Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this,"Gambar Gagal Disimpan",Toast.LENGTH_SHORT).show();
        }
    }

    /** Create a File for saving an image or video */
    private  File getOutputMediaFile(){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/Pictures/Anlette");

        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                return null;
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Intent mediaScanIntent = new Intent(
                    Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri contentUri = Uri.fromFile(mediaStorageDir);
            mediaScanIntent.setData(contentUri);
            this.sendBroadcast(mediaScanIntent);
        } else {
            sendBroadcast(new Intent(
                    Intent.ACTION_MEDIA_MOUNTED,
                    Uri.parse("file://"
                            + Environment.getExternalStorageDirectory()
                            + "/Pictures/Anlette")));
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("HHmmddMMyyyy").format(new Date());
        File mediaFile;
        String mImageName="ANL_"+ timeStamp +".jpg";
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        return mediaFile;
    }
}
