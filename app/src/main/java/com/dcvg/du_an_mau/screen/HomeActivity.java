package com.dcvg.du_an_mau.screen;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import com.dcvg.du_an_mau.R;
import com.dcvg.du_an_mau.fragment.BookFragment;
import com.dcvg.du_an_mau.fragment.CardFragment;
import com.dcvg.du_an_mau.fragment.CategoryFragment;
import com.dcvg.du_an_mau.fragment.RevenueFragment;
import com.dcvg.du_an_mau.fragment.TopBookFragment;
import com.dcvg.du_an_mau.model.Book;
import com.google.android.material.navigation.NavigationView;
import org.jetbrains.annotations.NotNull;


public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navView;
    private FrameLayout fragmentLayout;
    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout, new CardFragment()).commit();
        navView.bringToFront();
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        actionBarDrawerToggle.onOptionsItemSelected(item);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.card_management:
                fragment = new CardFragment();
                drawerLayout.closeDrawers();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout, fragment).commit();
                return true;
            case R.id.category_management:
                fragment = new CategoryFragment();
                drawerLayout.closeDrawers();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout, fragment).commit();
                return true;
            case R.id.book_management:
                fragment = new BookFragment();
                drawerLayout.closeDrawers();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout, fragment).commit();
                return true;
            case R.id.member_management:
                System.out.println("ccc");
                drawerLayout.closeDrawers();
                return true;
            case R.id.top_book:
                fragment = new TopBookFragment();
                drawerLayout.closeDrawers();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout, fragment).commit();
                return true;
            case R.id.revenue:
                fragment = new RevenueFragment();
                drawerLayout.closeDrawers();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout, fragment).commit();
                return true;
            case R.id.add_user:
                System.out.println("ccc");
                drawerLayout.closeDrawers();
                return true;
            case R.id.change_password:
                System.out.println("ccc");
                drawerLayout.closeDrawers();
                return true;
            case R.id.log_out:
                finish();
                return true;
        }
        return false;
    }

    private void initView() {
        drawerLayout = findViewById(R.id.my_drawer_layout);
        navView = (NavigationView) findViewById(R.id.nav_view);
        fragmentLayout = (FrameLayout) findViewById(R.id.fragment_layout);
    }
}