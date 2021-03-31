package com.bit.turit;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import java.util.Arrays;
import java.util.List;


public class MainActivity<uid> extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseAuth mfirebaseAuth;
    FirebaseAuth.AuthStateListener mAuthListener;

    String Uid_menutienda1 = "75mN78rBatWiFdZI2rq8akHE2xj2";



    public static final int REQUEST_CODE = 5454;

    List<AuthUI.IdpConfig> provider = Arrays.asList(
            new AuthUI.IdpConfig.FacebookBuilder().build(),
            new AuthUI.IdpConfig.EmailBuilder().build(),
            new AuthUI.IdpConfig.GoogleBuilder().build()
    );

    //Variables
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        mfirebaseAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user !=null){
                    Toast.makeText(MainActivity.this, "Sesión Iniciada", Toast.LENGTH_SHORT).show();
                    alertaAdmin();
                }else  {
                    startActivityForResult(AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(provider)
                            .setIsSmartLockEnabled(false)
                            .setTheme(R.style.Theme_AppCompat_DayNight)
                            .build(), REQUEST_CODE
                    );
                }
            }
        };

        /*----------Hooks----------*/
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        /*----------Toolbar----------*/
        setSupportActionBar(toolbar);

        /*----------Navigation Drawer Menu----------*/
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        /*----------Bottom Navigation Bar----------*/
        bottomNavigationView = findViewById(R.id.bottom_NavigationView);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_NavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:

                        break;
                    case R.id.ticket:
                        Intent intent1 = new Intent(MainActivity.this, TicketActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.agregar:
                        Intent intent2 = new Intent(MainActivity.this, AgregarActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.lugares:
                        Intent intent3 = new Intent(MainActivity.this, LugaresActivity.class);
                        startActivity(intent3);
                        break;
                    case R.id.tiendas:
                        Intent intent4 = new Intent(MainActivity.this, TiendasActivity.class);
                        startActivity(intent4);
                        break;
                }
                return false;
            }
        });



    }// FINAL DEL ON-CREATE!!!!!!!!!!!
    
    public void alertaAdmin() {
        String uid = new String (FirebaseAuth.getInstance().getUid());

        if (uid.equals(Uid_menutienda1)) {
            Toast.makeText(this, "Eres User de Tienda",Toast.LENGTH_SHORT).show();
            Intent menutienda = new Intent(getApplicationContext(),MenuTiendaActivity.class);
            startActivity(menutienda);
            finish();
        }
    }

    /*Para que no se cierre la app entera cuando abrimos el drawer y apretamos botón para atrás.*/
    @Override
    public void onBackPressed() {

        if(drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        return true;
    }


    @Override
    protected void onResume() {
        super.onResume();
        mfirebaseAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onPause(){
        super.onPause();
        mfirebaseAuth.removeAuthStateListener(mAuthListener);
    }


    public void cerrarsesion (MenuItem item) {
        AuthUI.getInstance().signOut(this).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(MainActivity.this, "Sesión Finalizada", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

}


