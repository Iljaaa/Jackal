package com.a530games.jackal;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.a530games.framework.AndroidGame;
import com.a530games.framework.FastRenderView;
import com.a530games.framework.RenderView;

import androidx.appcompat.app.AppCompatActivity;

import android.os.PowerManager;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.a530games.jackal.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {


    private AndroidGame game;


    private FastRenderView renderView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //
        PowerManager powerManager = (PowerManager) this.getSystemService(POWER_SERVICE);
        PowerManager.WakeLock wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "My Lock");





        // /home/ilja/Загрузки/photo_2022-07-01_22-54-25.jpg

        this.renderView = new FastRenderView(this);
        this.setContentView(this.renderView);


        /*try {
            this.setContentView(new RenderView(this));
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        /*binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    protected void onResume() {
        super.onResume();
        this.renderView.resume();
    }

    protected void onPause() {
        super.onPause();
        this.renderView.pause();
    }



}