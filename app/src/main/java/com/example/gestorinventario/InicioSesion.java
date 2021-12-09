package com.example.gestorinventario;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class InicioSesion extends AppCompatActivity {

    TextInputEditText edtcontrainicio, edtcorreoinicio;
    Button btniniciar;
    TextView tvolvcontra;
    CheckBox cbrecuerdame;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference ref;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);

        //ActionBar actionBar = getSupportActionBar();
        //actionBar.hide();

        //intancia de la base de datos
        firebaseAuth = FirebaseAuth.getInstance();

        edtcontrainicio = findViewById(R.id.edtcontrainicio);
        edtcorreoinicio = findViewById(R.id.edtcorreoinicio);
        btniniciar = findViewById(R.id.btniniciar);
        tvolvcontra = findViewById(R.id.tvolvcontra);
        cbrecuerdame = findViewById(R.id.cbRecuerdame);


        //COMPRUEBO SI GUARDO LA CUENTA
        SharedPreferences preferences = getSharedPreferences("cbRecuerdame", MODE_PRIVATE);
        String cb = preferences.getString("remember", "");
        if(cb.equals("true")){
            Intent i = new Intent(InicioSesion.this, MenuPrincipal.class);
            startActivity(i);
        }else if(cb.equals("false")){
            Toast.makeText(InicioSesion.this, "Por favor inicie sesion.", Toast.LENGTH_SHORT).show();
        }



        btniniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String correo = edtcorreoinicio.getText().toString().trim();
                String contra = edtcontrainicio.getText().toString().trim();

                if(correo.isEmpty() || contra.isEmpty()){
                    Toast.makeText(InicioSesion.this, "Introduzca los datos para continuar.", Toast.LENGTH_SHORT).show();
                }
                else{
                    InicioSesion();
                }
            }
        });

        //olvidar contraseña
        tvolvcontra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText correoreset = new EditText(view.getContext());
                AlertDialog.Builder contraResetDialog = new AlertDialog.Builder(view.getContext());
                contraResetDialog.setTitle("¿Olvidaste tu contraseña?");
                contraResetDialog.setMessage("Introduce tu correo para recibir\n" +
                                        "el enlace para cambiar la contraseña");
                contraResetDialog.setView(correoreset);

                contraResetDialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String correo = correoreset.getText().toString();
                        firebaseAuth.sendPasswordResetEmail(correo).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(@NonNull Void unused) {
                                Toast.makeText(InicioSesion.this, "Enlace enviado a \n"+correo, Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(InicioSesion.this, "No se ha podido enviar el enlace.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                contraResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                contraResetDialog.create().show();

            }
        });



        cbrecuerdame.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked()){
                    SharedPreferences preferences = getSharedPreferences("cbRecuerdame", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember", "true");
                    editor.apply();

                }else if(!compoundButton.isChecked()){
                    SharedPreferences preferences = getSharedPreferences("cbRecuerdame", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember", "false");
                    editor.apply();
                }
            }
        });



    }

    //metodo para iniciar sesion
    public void InicioSesion(){
        String correo = edtcorreoinicio.getText().toString().trim();
        String contra = edtcontrainicio.getText().toString().trim();

        firebaseAuth.signInWithEmailAndPassword(correo, contra).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(InicioSesion.this, "¡Bienvenido!", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(InicioSesion.this, MenuPrincipal.class);
                    startActivity(i);
                }else{
                    Toast.makeText(InicioSesion.this, "Correo o contraseña incorrectos.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}