package com.example.jjsminventoria;

import android.os.Bundle;
<<<<<<< Updated upstream

import com.google.android.material.bottomnavigation.BottomNavigationView;
=======
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jjsminventoria.database.FirebaseConnection;
import com.example.jjsminventoria.ui.dashboard.DashboardFragment;
import com.example.jjsminventoria.ui.inventory.InventoryViewFragment;
import com.example.jjsminventoria.ui.profile.ProfileFragment;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
>>>>>>> Stashed changes

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

public class MainMenuActivity extends AppCompatActivity {

<<<<<<< Updated upstream
    private ActivityMainMenuBinding binding;
=======
    private TextView tvLogout;
    private LinearLayout dashboardTab, inventoryTab, profileTab;
>>>>>>> Stashed changes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu); // <-- make sure this matches your XML file name

        // Setup toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

<<<<<<< Updated upstream
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main_menu_bottom_tabs);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

}
=======
        // Init logout
        tvLogout = findViewById(R.id.tvLogout);
        tvLogout.setOnClickListener(this);

        // Init tab buttons
        dashboardTab = findViewById(R.id.dashboardTab);
        inventoryTab = findViewById(R.id.inventoryTab);
        profileTab = findViewById(R.id.profileTab);

        // Set click listeners
        dashboardTab.setOnClickListener(this);
        inventoryTab.setOnClickListener(this);
        profileTab.setOnClickListener(this);

        // Load default fragment
        loadFragment(new DashboardFragment()); // <-- replace with your actual default fragment
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.tvLogout) {
            logout();
        } else if (id == R.id.dashboardTab) {
            loadFragment(new DashboardFragment());
        } else if (id == R.id.inventoryTab) {
            loadFragment(new InventoryViewFragment());
        } else if (id == R.id.profileTab) {
            loadFragment(new ProfileFragment());
        }
    }

    private void logout() {
        FirebaseConnection.getInstance().logout();
        GoogleSignIn.getClient(this, GoogleSignInOptions.DEFAULT_SIGN_IN).signOut();

        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.nav_host_fragment_activity_main_menu_bottom_tabs, fragment)
                .commit();
    }
}
>>>>>>> Stashed changes
