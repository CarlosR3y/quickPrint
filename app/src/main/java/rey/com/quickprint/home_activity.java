package rey.com.quickprint;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class home_activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    public Intent intent;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    TextView etCorreo, tv_HeaderCorreo;
    Button btn_cerrarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = findViewById(R.id.app_toolbar);
        setSupportActionBar(toolbar);
        tv_HeaderCorreo = findViewById(R.id.tv_CorreoHeader);

        btn_cerrarSesion = findViewById(R.id.btn_CerrarSesion);
        drawerLayout=findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.closed_nav);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


        btn_cerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(home_activity.this, "Sesion cerrada", Toast.LENGTH_SHORT).show();
                IraLogin();
            }
        });

    }

    private void IraLogin() {
        Intent i = new Intent(home_activity.this, loginActividad.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    public void onBackPressed(){
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.item1:
                intent = new Intent(home_activity.this, documentosActivity.class);
                startActivity(intent);
                break;
            case R.id.item2:
                intent = new Intent(home_activity.this, historialActivity.class);
                startActivity(intent);
                break;
            case R.id.item3:
                intent = new Intent(home_activity.this, estadoActivity.class);
                startActivity(intent);
                break;
            case R.id.item4:
                intent = new Intent(home_activity.this, PagoActivity.class);
                startActivity(intent);
                break;
            case R.id.item5:
                intent = new Intent(home_activity.this, ajustesActivity.class);
                startActivity(intent);
                break;
            case R.id.item6:
                intent = new Intent(home_activity.this, loginActividad.class);


        }

        return false;
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}