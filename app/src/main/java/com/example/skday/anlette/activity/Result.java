package com.example.skday.anlette.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.skday.anlette.ColorUtils;
import com.example.skday.anlette.R;
import com.example.skday.anlette.base.BaseActivity;
import com.example.skday.anlette.databinding.ActivityMainBinding;
import com.example.skday.anlette.databinding.ActivityResultBinding;
import com.example.skday.anlette.model.AnlPhoto;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Result extends BaseActivity {

    private ActivityResultBinding binding;
    private static final int CAMERA_REQUEST = 1888;
    private static final int GALLERY_REQUEST = 1889;
    private Bitmap image;
    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
    private ProgressDialog dialog;
    private String imageName;
    private String paletteName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_result);
        binding.setResult(this);
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle("Palette");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (getIntent().getExtras().getBoolean("Take")){
            dispatchTakePictureIntent();
        }else{
            galleryIntent();
        }
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.btn_save){
            dialog = new ProgressDialog(this);
            dialog.setTitle("Menyimpan palette");
            dialog.setMessage("Mohon tunggu sebentar ...");
            dialog.setCancelable(false);
            dialog.show();

            savePalette();

            Intent intent = new Intent();
            intent.putExtra("IMAGE_NAME", imageName);
            setResult(Activity.RESULT_OK, intent);

            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            image = BitmapFactory.decodeFile(mCurrentPhotoPath);
            binding.image.setImageBitmap(image);
        } else if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK) {
            onSelectFromGalleryResult(data);
        }
    }

    public void onSelectFromGalleryResult(Intent data) {
        Log.i("infotes", "onSelectFromGalleryResult: ");
        Bitmap bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                image = bm;
                binding.image.setImageBitmap(image);
                savePhotoFromGallery();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void savePhotoFromGallery(){
        imageName = timeStamp;
        storeImage(image,"PHO_");
        imageName = "PHO_" + timeStamp;
    }

    public void savePalette(){
        binding.linearLayout.setDrawingCacheEnabled(true);
        storeImage(binding.linearLayout.getDrawingCache(),"PAL_");
        binding.linearLayout.destroyDrawingCache();
    }

    private void storeImage(Bitmap image, String imgTAG) {
        File pictureFile = getOutputMediaFile(imgTAG);
        if (pictureFile == null) {
            Snackbar.make(binding.main,"Terjadi kesalahan", Snackbar.LENGTH_SHORT).show();
            return;
        }
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            image.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();
        } catch (FileNotFoundException e) {
            Snackbar.make(binding.main,"Gambar gagal disimpan", Snackbar.LENGTH_SHORT).show();
        } catch (IOException e) {
            Snackbar.make(binding.main,"Gambar gagal disimpan", Snackbar.LENGTH_SHORT).show();
        }
    }

    /** Create a File for saving an image or video
     */
    private  File getOutputMediaFile(String imgTAG){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        File mediaStorageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

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
                            + getExternalFilesDir(Environment.DIRECTORY_PICTURES))));
        }

        // Create a media file name
        File mediaFile;
        paletteName = imgTAG + imageName;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + paletteName);
        return mediaFile;
    }

    String mCurrentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String imageFileName = "PHO_" + timeStamp;
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        imageName = image.getName();
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, CAMERA_REQUEST);
            }
        }
    }

    public void galleryIntent() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, GALLERY_REQUEST);
    }
}
