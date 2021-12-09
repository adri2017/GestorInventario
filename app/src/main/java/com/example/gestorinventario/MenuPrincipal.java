package com.example.gestorinventario;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import Clases.Usuario;
import OperacionesPedido.ActualizarPed;
import OperacionesPedido.HistorialPedidos;
import OperacionesPedido.OperacionesPed;
import OperacionesProd.OperacionesProd;

public class MenuPrincipal extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Button btnopprod, btnopped, btnhistorial;
    private FirebaseAuth firebaseAuth;
    TextView tvusernamehead, tvcorreohead, tvtlfhead;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    FirebaseDatabase database;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        btnopprod = findViewById(R.id.btnopprod);
        btnopped = findViewById(R.id.btnoppedid);
        btnhistorial = findViewById(R.id.btnhistorial);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        tvusernamehead = findViewById(R.id.tvusername);
        tvcorreohead = findViewById(R.id.tvcorreo);
        tvtlfhead = findViewById(R.id.tvtlfhead);


        firebaseAuth = FirebaseAuth.getInstance();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //navigation drawer menu
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.nav_drawer_open, R.string.nav_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //metodo para setear el listener de cerrar sesion
        navigationView.setNavigationItemSelectedListener(this);
        //cargo datos del usuario en el navigation drawer
        cargarDatosUsuario();

        //COMPRUEBO SI EL CORREO ESTA VERIFICADO
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(!user.isEmailVerified()){
            Toast.makeText(MenuPrincipal.this, "Por favor, verifique su correo", Toast.LENGTH_SHORT).show();
            AlertDialog.Builder builderverif = new AlertDialog.Builder(MenuPrincipal.this);
            builderverif.setTitle("Por favor, verifique su correo para continuar");

            builderverif.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent i2 = new Intent(MenuPrincipal.this, InicioSesion.class);
                    startActivity(i2);
                }
            });
            AlertDialog alert = builderverif.create();
            alert.show();
        }

        btnopprod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MenuPrincipal.this, OperacionesProd.class);
                startActivity(i);
            }
        });

        btnopped.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i2 = new Intent(MenuPrincipal.this, OperacionesPed.class);
                startActivity(i2);
            }
        });

        btnhistorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i2 = new Intent(MenuPrincipal.this, HistorialPedidos.class);
                startActivity(i2);
            }
        });

    }




    private void cargarDatosUsuario() {

        database = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        ref = database.getInstance("https://gestorinventario-41b55-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference("Usuarios").child(firebaseAuth.getUid());

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Usuario usuario = dataSnapshot.getValue(Usuario.class);
                NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                View header = navigationView.getHeaderView(0);

                tvusernamehead = (TextView) header.findViewById(R.id.tvusername);
                tvtlfhead = (TextView) header.findViewById(R.id.tvtlfhead);
                tvcorreohead = (TextView) header.findViewById(R.id.tvcorreo);

                tvusernamehead.setText(usuario.getNombre());
                tvtlfhead.setText(usuario.getTlf());
                tvcorreohead.setText(usuario.getCorreo());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.nav_cerrar){
            SharedPreferences preferences = getSharedPreferences("cbRecuerdame", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("remember", "false");
            editor.apply();
            firebaseAuth.signOut();
            finish();
        }

        return true;
    }








}