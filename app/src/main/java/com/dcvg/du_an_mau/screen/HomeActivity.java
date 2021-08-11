package com.dcvg.du_an_mau.screen;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import com.dcvg.du_an_mau.R;
import com.dcvg.du_an_mau.fragment.AddUserFragment;
import com.dcvg.du_an_mau.fragment.BookFragment;
import com.dcvg.du_an_mau.fragment.CardFragment;
import com.dcvg.du_an_mau.fragment.CategoryFragment;
import com.dcvg.du_an_mau.fragment.ChangePasswordFragment;
import com.dcvg.du_an_mau.fragment.MemberFragment;
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
                fragment = new MemberFragment();
                drawerLayout.closeDrawers();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout, fragment).commit();
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
                fragment = new AddUserFragment();
                drawerLayout.closeDrawers();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout, fragment).commit();
                return true;
            case R.id.change_password:
                fragment = new ChangePasswordFragment();
                drawerLayout.closeDrawers();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout, fragment).commit();
                return true;
            case R.id.log_out:
                SharedPreferences myPrefs = HomeActivity.this.getSharedPreferences("My Data", MODE_PRIVATE);
                SharedPreferences.Editor editor = myPrefs.edit();
                editor.putString("username", "");
                editor.putString("password", "");
                editor.commit();
                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                return true;
        }
        return false;
    }

    private void initView() {
        drawerLayout = findViewById(R.id.my_drawer_layout);
        navView = (NavigationView) findViewById(R.id.nav_view);
        fragmentLayout = (FrameLayout) findViewById(R.id.fragment_layout);
    }

    @Override
    public void onBackPressed() {
        return;
    }
}