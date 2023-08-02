package com.example.travelapp;
        import androidx.annotation.NonNull;
        import androidx.annotation.Nullable;
        import androidx.appcompat.app.ActionBarDrawerToggle;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.appcompat.widget.Toolbar;
        import androidx.cardview.widget.CardView;
        import androidx.core.view.GravityCompat;
        import androidx.drawerlayout.widget.DrawerLayout;
        import androidx.recyclerview.widget.DefaultItemAnimator;
        import androidx.recyclerview.widget.LinearLayoutManager;
        import androidx.recyclerview.widget.RecyclerView;

        import android.Manifest;
        import android.app.AlertDialog;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.net.Uri;
        import android.os.Bundle;
        import android.os.Handler;
        import android.provider.Settings;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.ScrollView;
        import android.widget.Toast;

        import com.google.android.material.navigation.NavigationView;
        import com.google.firebase.auth.FirebaseAuth;

        import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout dlMain;
    ArrayList<Options> options;
    RecyclerView recyclerView;
    NavigationView navigation;
    MenuItem item_out;
    MenuItem item_in;
    Menu menu;
    CardView cvBerlin, cvMunchen, cvHamburg, cvKoln, cvLiepzig, cvDresden;
    MenuItem item_prof;
    Toolbar tool;
    RecyclerView.LayoutManager manager;
    Login login;
    ArrayList<CardView>cities=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login=new Login();
        recyclerView=findViewById(R.id.recyclerView);
        dlMain=findViewById(R.id.dlMain);
        navigation=findViewById(R.id.navigation);
        tool=findViewById(R.id.tool);
        cvBerlin=findViewById(R.id.cvBerlin);
        cvMunchen=findViewById(R.id.cvMunchen);
        cvHamburg=findViewById(R.id.cvHamburg);
        cvKoln=findViewById(R.id.cvKoln);
        cvLiepzig=findViewById(R.id.cvLiepzig);
        cvDresden=findViewById(R.id.cvDresden);
        cities.add(cvMunchen);
        cities.add(cvHamburg);
        cities.add(cvKoln);
        cities.add(cvLiepzig);
        cities.add(cvDresden);
        setSupportActionBar(tool);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,dlMain,tool,R.string.drawer_open,R.string.drawer_close);
        dlMain.setDrawerListener(toggle);
        toggle.syncState();
        cvBerlin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,City.class));
            }
        });
        for(CardView city : cities){
            city.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MainActivity.this,WorkInProgressActivity.class));
                }
            });
        }
        navigation.setItemIconSize(90);
        navigation.bringToFront();
        navigation.setNavigationItemSelectedListener(this);
        navigation.setCheckedItem(R.id.nav_home);

        createOptions();
        manager=new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false);
        recyclerView.setAdapter(new OptionsRecyclerAdapter(options, new OptionsRecyclerAdapter.ItemClickListener() {
            @Override
            public void onItemClick(Options options) {
                switch(options.name){
                    case "Galery":
                        startActivity(new Intent(getApplicationContext(),Gallery.class));
                        break;
                    case "Hotels":
                        open("https://www.booking.com/country/de.html?aid=1610684;label=de-rHE7KXrebQfxHKE2myBhVwS380966224289:pl:ta:p1:p2:ac:ap:neg:fi:tikwd-299925943839:lp9061070:li:dec:dm:ppccp=UmFuZG9tSVYkc2RlIyh9YfqnDqqG8nt10AsofPfvtt0;ws=&gad=1&gclid=CjwKCAjwt52mBhB5EiwA05YKo6FvgNEGHPtHbO43vcql6J_kanjbquErxuML3EzoLiyGzXI_rAS1zxoC8noQAvD_BwE");
                        break;
                    case "Travel Tips":
                        startActivity(new Intent(getApplicationContext(),TravelTips.class));
                        break;
                    case "Food":
                        open("https://www.tripadvisor.com/Restaurants-g187275-Germany.html");
                        break;

                }
            }
        }));
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        menu =navigation.getMenu();
        item_prof=menu.findItem(R.id.nav_profile);
        item_out=menu.findItem(R.id.nav_logout);
        item_in=menu.findItem(R.id.nav_login);
        item_prof.setVisible(false);
        item_out.setVisible(false);
        Intent i=getIntent();
        Bundle b=i.getExtras();
        if(b!=null){
            boolean result=b.getBoolean("logged");
            if(result){
                item_out.setVisible(true);
                item_prof.setVisible(true);
                item_in.setVisible(false);
            }
        }
        if(login.logged){
            item_out.setVisible(true);
            item_prof.setVisible(true);
            item_in.setVisible(false);
        }
    }
    public void createOptions(){
        options=new ArrayList<>();
        options.add(new Options("Hotels",R.drawable.hotel));
        options.add(new Options("Travel Tips",R.drawable.notes));
        options.add(new Options("Galery",R.drawable.galery));
        options.add(new Options("Food",R.drawable.rest_menu));
    }

    @Override
    public void onBackPressed() {
        if(dlMain.isDrawerOpen(GravityCompat.START)){
            dlMain.close();
        }else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_profile:
                startActivity(new Intent(getApplicationContext(),Profile.class));
                break;
            case R.id.nav_notif:
                Intent settingsIntent = new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        .putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName())
                        .putExtra(Settings.EXTRA_CHANNEL_ID, 0);
                startActivity(settingsIntent);
                break;
            case R.id.nav_checklist:
                startActivity(new Intent(getApplicationContext(),Checklist.class));
                break;
            case R.id.nav_login:
                startActivity(new Intent(getApplicationContext(),Login.class));
                break;
            case R.id.nav_logout:
                submit();
                break;
            case R.id.nav_settings:
                Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(Uri.parse("package:" + getPackageName()));
                startActivity(intent);
                break;
            case R.id.nav_support:
                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{"marcelineabadir527@gmail.com"});
                email.putExtra(Intent.EXTRA_SUBJECT, "Travel Guide - User Support");
                email.putExtra(Intent.EXTRA_TEXT, "Hello! I have some questions about...");
                email.setType("message/rfc822");
                startActivity(Intent.createChooser(email, "Choose an Email client :"));
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        openDialog();
                    }
                }, 2000);
                break;
            case R.id.nav_info:
                break;
        }
        dlMain.closeDrawer(GravityCompat.START);
        return true;
    }
    private void openDialog() {
        androidx.appcompat.app.AlertDialog alertDialog=new androidx.appcompat.app.AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle("Support");
        alertDialog.setMessage("Thank you for your question! We will try to contact you as fast as possible.");
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }
    public void submit(){
        AlertDialog alertDialog=new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle("Logout");
        alertDialog.setMessage("Are you sure you want to logout?");
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialog.dismiss();
                FirebaseAuth.getInstance().signOut();
                item_prof.setVisible(false);
                item_out.setVisible(false);
                item_in.setVisible(true);
            }
        });
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }
    public void open(String link){
        Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(link));
        startActivity(intent);
    }
}