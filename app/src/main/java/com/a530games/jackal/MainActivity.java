package com.a530games.jackal;

import android.os.Bundle;

import com.a530games.framework.AndroidGame;
import com.a530games.framework.FastRenderView;

import androidx.appcompat.app.AppCompatActivity;

import android.os.PowerManager;

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