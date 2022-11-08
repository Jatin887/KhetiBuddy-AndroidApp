package com.agriculturalapp.activities;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/*
import com.agriculturalapp.Weather.DetailActivity;
import com.agriculturalapp.Weather.sunmain;
*/
import com.agriculturalapp.weather.sunmain;
import com.agriculturalapp.modals.MainListItem;
import com.agriculturalapp.R;
import com.agriculturalapp.adapters.MainAdapter;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ArrayList<MainListItem> list;
    private RecyclerView recyclerView;
    private MainAdapter adapter;

    private Integer[] imageUrls={
            R.raw.crop_variety,
            R.raw.crop_production_opt,
            R.raw.treat,
            R.raw.shc2,
            R.drawable.horticulture_main,
            R.raw.govp};

    private Integer[] hindiTexts={
            R.string.crop_variety_recommeneder_hi,
            R.string.crop_production_card_title_hi,
            R.string.treatment_card_title_hi,
            R.string.storage_card_title_hi,
            R.string.horticulture_card_title_hi,
            R.string.policy_card_title_hi};

    private Integer[] englishTexts={R.string.crop_variety_recommeneder_en,
            R.string.crop_production_card_title_en,
            R.string.treatment_card_title_en, R.string.storage_card_title_en,
            R.string.horticulture_card_title_en,R.string.policy_card_title_en};

    private String[] backgroundColors={"#e9f6a7",
            "#a3ef22","#a4f075",
            "#ffff4d","#cef63c","#ff9f80"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//
//        OpenWeatherMapHelper helper = new OpenWeatherMapHelper(getString(R.string.OPEN_WEATHER_MAP_API_KEY));

        Intent[] links={
                new Intent(MainActivity.this, CropVarietyRecommender.class),
                new Intent(MainActivity.this, CropProductionActivity.class),
                new Intent(MainActivity.this, SelectProblem.class),
                new Intent(MainActivity.this, SoilHealthActivity.class),
                new Intent(MainActivity.this, HorticultureActivity.class),
                new Intent(MainActivity.this, Select_Policy.class)
        };

        list = new ArrayList<>();
        for(int i=0;i<imageUrls.length;i++){
            MainListItem item=new MainListItem();
            item.setImageUrl(imageUrls[i]);
            item.setHindiText(hindiTexts[i]);
            item.setEnglishText(englishTexts[i]);
            item.setBackgroundColor(backgroundColors[i]);
            item.setIntent(links[i]);
            list.add(item);
        }

        adapter = new MainAdapter(this,list);

        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        Log.v("version", Build.VERSION.SDK_INT + "");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        findViewById(R.id.progress).setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.bazar_rates_nav) {
            Log.v("###","hello");
            startActivity(new Intent(MainActivity.this, Select_State_Bazaar.class));

        }

        else if(id == R.id.youtube_videos) {
            startActivity(new Intent(MainActivity.this, YouTubeVideo.class));
        }
        else if(id == R.id.about_nav){

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(String.format("%1$s", getString(R.string.about)));
            builder.setMessage(getResources().getText(R.string.about_text));
            builder.setPositiveButton("OK", null);
            builder.setIcon(R.mipmap.ic_launcher);
            AlertDialog welcomeAlert = builder.create();
            welcomeAlert.show();
            ((TextView) welcomeAlert.findViewById(android.R.id.message)).setMovementMethod(LinkMovementMethod.getInstance());

        }else if(id == R.id.contactus_nav){
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            String uriText = "mailto:" + Uri.encode("manvi.aggarwal2020a@vitstudent.ac.in") + "?subject=" +
                    Uri.encode("Feedback") + "&body=" + Uri.encode("");

            Uri uri = Uri.parse(uriText);
            intent.setData(uri);
            startActivity(Intent.createChooser(intent, "Send Email"));


        }else if(id == R.id.call_link){
            if(Build.VERSION.SDK_INT<23){
                Intent in=new Intent(Intent.ACTION_CALL, Uri.parse("tel:+91" + "18001801551"));

                try{
                    startActivity(in);
                }catch (android.content.ActivityNotFoundException ex){
                    Toast.makeText(getApplicationContext(), "yourActivity is not founded", Toast.LENGTH_SHORT).show();
                }

            }else{
                int REQUEST_CODE_ASK_PERMISSIONS = 123;

                int hasWriteContactsPermission = checkSelfPermission(Manifest.permission.CALL_PHONE);
                if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[] {Manifest.permission.CALL_PHONE},
                            REQUEST_CODE_ASK_PERMISSIONS);

                }

                Intent in=new Intent(Intent.ACTION_CALL, Uri.parse("tel:+91" + "18001801551"));

                try{
                    startActivity(in);
                }catch (Exception ex){
                    Toast.makeText(getApplicationContext(), "Permissions required", Toast.LENGTH_SHORT).show();
                }

            }

            return true;

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
