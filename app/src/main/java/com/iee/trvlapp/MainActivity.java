package com.iee.trvlapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.room.Room;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.iee.trvlapp.config.AppDatabase;
import com.iee.trvlapp.databinding.ActivityMainBinding;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    public static AppDatabase appDatabase;
    public static FirebaseFirestore appDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Database Reference and Initialization

        appDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "AppDb").allowMainThreadQueries().createFromAsset("db/AppDbv3.db").build();
        appDb = FirebaseFirestore.getInstance();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        setSupportActionBar(binding.appBarMain.toolbar);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;


        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_offices, R.id.nav_tours, R.id.nav_packages, R.id.nav_costumers, R.id.settings, R.id.nav_hotels, R.id.nav_about, R.id.nav_help)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        // Hide language change buttons when the device is in landscape orientation
        int orientation = this.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.changeLangEn.setVisibility(View.INVISIBLE);
            binding.changeLangGr.setVisibility(View.INVISIBLE);
        } else {
            binding.changeLangEn.setVisibility(View.VISIBLE);
            binding.changeLangGr.setVisibility(View.VISIBLE);
        }


        // Set up listener for changing language to Greek
        binding.changeLangGr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Locale locale = new Locale("gr");
                pushNotification("gr");
                Resources resources = MainActivity.this.getResources();
                Configuration configuration = resources.getConfiguration();
                configuration.setLocale(locale);
                resources.updateConfiguration(configuration, resources.getDisplayMetrics());
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });

        // Set up listener for changing language to English
        binding.changeLangEn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Locale locale = new Locale("");
                pushNotification("en");
                Resources resources = MainActivity.this.getResources();
                Configuration configuration = resources.getConfiguration();
                configuration.setLocale(locale);
                resources.updateConfiguration(configuration, resources.getDisplayMetrics());
                Intent intent = getIntent();
                finish();
                startActivity(intent);

            }
        });

    }

    // This method updates the app's locale and reloads the activity to apply changes.
    public void pushNotification(String locale) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("notificationId", "notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        if (locale.equals("gr")) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "notificationId")
                    .setContentText("TRVl")
                    .setSmallIcon(R.drawable.ic_baseline_language_24)
                    .setAutoCancel(true)
                    .setContentText("Η γλώσσα ορίσθηκε στα Ελληνικά!")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);


            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
            managerCompat.notify(999, builder.build());
        } else {
            Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "notificationId")
                    .setContentText("TRVl")
                    .setSmallIcon(R.drawable.ic_baseline_language_24)
                    .setAutoCancel(true)
                    .setContentText("Language set to English")
                    .setBadgeIconType(NotificationCompat.BADGE_ICON_LARGE)
                    .setSound(alarmSound)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);


            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
            managerCompat.notify(999, builder.build());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}