package ir.rastanco.manbank0;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import CustomersTabs.CustomersCards;
import CustomersTabs.CustomersLoans;
import CustomersTabs.CustomersMyCustomers;
import CustomersTabs.CustomersRequests;
import DepositTabs.DepositCards;
import DepositTabs.DepositLoans;
import DepositTabs.DepositRequests;
import DepositTabs.DepositWallet;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener , View.OnClickListener{

    private int nav_fragment_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.clearButtons();

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Button button;
        button = (Button) findViewById(R.id.wallet);
        button.setOnClickListener(this);
        button = (Button) findViewById(R.id.loans);
        button.setOnClickListener(this);
        button = (Button) findViewById(R.id.cards);
        button.setOnClickListener(this);
        button = (Button) findViewById(R.id.requests);
        button.setOnClickListener(this);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        this.nav_fragment_id = id;
        Fragment fragment = null;
        switch (id){
            case R.id.deposit_services:
                this.clearButtons();
                this.showButtons();
                this.setDepositButtons();
                fragment = new DepositWallet();
                break;
            case R.id.customers_services:
                this.clearButtons();
                this.showButtons();
                this.setCustomersButtons();
                fragment = new CustomersMyCustomers();
                break;
            case R.id.other_services:
                this.clearButtons();
                fragment = new OtherServices();
                break;
            case R.id.about_manbank:
                this.clearButtons();
                fragment = new AboutFragment();
                break;
            case R.id.my_profile:
                this.clearButtons();
                fragment = new ProfileFragment();
                break;
            case R.id.settings:
                this.clearButtons();
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;
        }

       if(id != R.id.settings) {
           FragmentManager fragmentManager = getFragmentManager();
           fragmentManager.beginTransaction()
                   .replace(R.id.content_frame, fragment)
                   .commit();
       }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        Fragment fragment = null;
        int id = v.getId();

        if(nav_fragment_id == R.id.deposit_services){
            switch (id){
                case R.id.wallet:
                    fragment = new DepositWallet();
                    break;
                case R.id.loans:
                    fragment = new DepositLoans();
                    break;
                case R.id.cards:
                    fragment = new DepositCards();
                    break;
                case R.id.requests:
                    fragment = new DepositRequests();
                    break;
            }
        }
        else if (nav_fragment_id == R.id.customers_services){
            switch (id){
                case R.id.wallet:
                    fragment = new CustomersMyCustomers();
                    break;
                case R.id.loans:
                    fragment = new CustomersLoans();
                    break;
                case R.id.cards:
                    fragment = new CustomersCards();
                    break;
                case R.id.requests:
                    fragment = new CustomersRequests();
                    break;
            }
        }
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
    }
    private void fadeoutButtons(final Button button){
        button.animate()
                .alpha(0f)
                .setDuration(0)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        button.setVisibility(View.GONE);
                    }
                });
    }
    private void clearButtons(){
        Button button;
        button = (Button) findViewById(R.id.wallet);
        fadeoutButtons(button);
        button = (Button) findViewById(R.id.loans);
        fadeoutButtons(button);
        button = (Button) findViewById(R.id.cards);
        fadeoutButtons(button);
        button = (Button) findViewById(R.id.requests);
        fadeoutButtons(button);

    }
    private void fadeInButtons(Button button){
        button.setAlpha(0f);
        button.setVisibility(View.VISIBLE);
        button.animate()
                .alpha(1f)
                .setDuration(0)
                .setListener(null);
    }
    private void showButtons(){
        Button button;
        button = (Button) findViewById(R.id.wallet);
        fadeInButtons(button);
        button = (Button) findViewById(R.id.loans);
        fadeInButtons(button);
        button = (Button) findViewById(R.id.cards);
        fadeInButtons(button);
        button = (Button) findViewById(R.id.requests);
        fadeInButtons(button);
    }
    private void setCustomersButtons(){
        Button button;
        button = (Button) findViewById(R.id.wallet);
        button.setText(R.string.myCustomers);
    }

    private void setDepositButtons(){
        Button button;
        button = (Button) findViewById(R.id.wallet);
        button.setText(R.string.wallet);
    }
}
