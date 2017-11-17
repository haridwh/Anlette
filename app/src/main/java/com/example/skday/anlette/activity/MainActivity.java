package com.example.skday.anlette.activity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.skday.anlette.R;
import com.example.skday.anlette.adapter.PaletteAdapter;
import com.example.skday.anlette.base.BaseActivity;
import com.example.skday.anlette.databinding.ActivityMainBinding;
import com.example.skday.anlette.model.AnlPhoto;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private ActivityMainBinding binding;
    private List<AnlPhoto> listPalette;
    private RecyclerView recyclerView;
    private PaletteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setMain(this);
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle("Koleksi Anda");

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_DENIED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

        recyclerView = (RecyclerView) findViewById(R.id.list_palette);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, 1));
//        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
//        SnapHelper helper = new LinearSnapHelper();
//        helper.attachToRecyclerView(recyclerView);
        listPalette = new ArrayList<>();
        setListPalette();
        adapter = new PaletteAdapter(this, listPalette);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.btn_add){
            takePicture();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 125) {
            if (resultCode == Activity.RESULT_OK) {
                String photoname = data.getStringExtra("IMAGE_NAME");
                findPhoto(photoname);
            }
        }
    }

    public void takePicture() {
        final Intent intent = new Intent(this, Result.class);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        final CharSequence[] items1 = { "Ambil Foto", "Pilih dari Galeri"};

        builder.setTitle("Tambah Gambar");
        builder.setItems(items1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items1[item].equals("Ambil Foto")) {
                    if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
                        intent.putExtra("Take", true);
                        startActivityForResult(intent, 125);
                    } else {
                        errorCamera();
                    }
                } else  {
                    intent.putExtra("Choose", true);
                    startActivityForResult(intent, 125);
                }
            }
        });

        builder.show();
    }

    public void errorCamera() {
        AlertDialog.Builder cameraError = new AlertDialog.Builder(this);
        cameraError.setMessage("No Camera Apps");
        cameraError.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        cameraError.show();
    }

    public void setListPalette() {
        File path = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (path.exists()) {
            String[] paletteName = path.list();
            if (paletteName != null && paletteName.length != 0) {
                Bitmap palette, img;
                Log.i("Path", "setListPalette: " + path.getPath() + "/" + paletteName[0] + ", length = " + paletteName.length);
                for (int i = 0; i < paletteName.length; i++) {
                    if (paletteName[i].substring(0, 4).equals("PHO_")) {
                        Log.i("Path", "nama foto : " + paletteName[i]);
                        img = BitmapFactory.decodeFile(path.getPath() + "/" + paletteName[i]);
                        if (img != null) {
//                            Log.i("Path", "actual size : " + img.getWidth()+" x "+img.getHeight());
                            palette = ThumbnailUtils.extractThumbnail(img,
                                    img.getWidth() / 4, img.getHeight() / 4);
//                            Log.i("Path", "thumbnail size : " + palette.getWidth()+" x "+palette.getHeight());
                            listPalette.add(new AnlPhoto(palette, paletteName[i]));
                        }
                    }
                }
            }
        }
    }

    public void findPhoto(String photoName) {
        File path = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (path.exists()) {
            String[] paletteName = path.list();
            if (paletteName != null && paletteName.length != 0) {
                Bitmap palette, img;
                Log.i("Path", "findPhoto: " + path.getPath() + "/" + paletteName[0] + ", length = " + paletteName.length);
                Log.i("Path", "finding: photo name = " + photoName);
                for (int i = 0; i < paletteName.length; i++) {
                    Log.i("Path", "finding: " + paletteName[i]);
                    if (paletteName[i].equals(photoName)) {
                        Log.i("Path", "finding: found");
                        img = BitmapFactory.decodeFile(path.getPath() + "/" + paletteName[i]);
                        if (img != null) {
                            palette = ThumbnailUtils.extractThumbnail(img,
                                    img.getWidth() / 4, img.getHeight() / 4);
                            listPalette.add(new AnlPhoto(palette, paletteName[i]));
                            adapter.notifyItemInserted(listPalette.size());
                            break;
                        }
                    }
                }
            }
        }
    }

}
