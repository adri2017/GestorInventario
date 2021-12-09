package com.example.gestorinventario;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

import Clases.Usuario;

public class Registro extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private DatabaseReference ref;
    private FirebaseDatabase database;
    TextInputEditText edtcorreo, edtcontrasena, edtnombre, edttlf, edtconfcontrasena;
    Button btnregis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        edtcontrasena = findViewById(R.id.edtContrasena);
        edtcorreo = findViewById(R.id.edtCorreo);
        btnregis = findViewById(R.id.btnConfRegis);
        edtnombre = findViewById(R.id.edtNombre);
        edttlf = findViewById(R.id.edttlf);
        edtconfcontrasena = findViewById(R.id.edtConfContra);

        //intancia de la base de datos
        firebaseAuth = FirebaseAuth.getInstance();


        btnregis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String correo = edtcorreo.getText().toString().trim();
                String contrasena = edtcontrasena.getText().toString().trim();
                String nombre = edtnombre.getText().toString().trim();
                String tlf = edttlf.getText().toString().trim();

                if(correo.isEmpty() || contrasena.isEmpty() || nombre.isEmpty() || tlf.isEmpty()){
                    Toast.makeText(Registro.this, "Introduce todos los datos para continuar.", Toast.LENGTH_SHORT).show();
                }
                else if(!edtcontrasena.getText().toString().equals(edtconfcontrasena.getText().toString())){
                    Toast.makeText(Registro.this, "Las 2 contraseñas deben de ser iguales.", Toast.LENGTH_SHORT).show();
                }
                else{

                    if(validarPass(contrasena) == false){
                        Toast.makeText(Registro.this, "La contraseña debe de tener 6 caracteres, una mayuscula y una minuscula.", Toast.LENGTH_LONG).show();
                    }else{
                        Registro();
                    }



                }
            }
        });
    }

    public void Registro(){

        String nombre = edtnombre.getText().toString().trim();
        String correo = edtcorreo.getText().toString().trim();
        String contrasena = edtcontrasena.getText().toString().trim();
        String tlf = edttlf.getText().toString().trim();

        //metodo de firebase para crear usuarios
        firebaseAuth.createUserWithEmailAndPassword(correo, contrasena).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                    //enviamos correo de verificacion
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(@NonNull Void unused) {
                            Toast.makeText(Registro.this, "Correo de verificación enviado.", Toast.LENGTH_LONG).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Registro.this, "Correo de verificación no enviado.", Toast.LENGTH_SHORT).show();
                        }
                    });

                    btnregis.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) { }
                    });

                    //Agregar datos a realtime database
                    database = FirebaseDatabase.getInstance("https://gestorinventario-41b55-default-rtdb.europe-west1.firebasedatabase.app/");
                    ref = database.getReference("Usuarios");
                    Usuario usuario = new Usuario(nombre, correo, tlf);
                    ref.child(firebaseAuth.getUid()).setValue(usuario);

                    Toast.makeText(Registro.this, "Registro completo.", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Registro.this, InicioSesion.class);
                    startActivity(i);
                }
                else{
                    Toast.makeText(Registro.this, "No se pudo completar el registro\nContraseña o correo inválido.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static boolean validarPass(String passwordhere) {

        Pattern UpperCasePatten = Pattern.compile("[A-Z ]");
        Pattern lowerCasePatten = Pattern.compile("[a-z ]");
        Pattern digitCasePatten = Pattern.compile("[0-9 ]");

        boolean flag=true;

        if (passwordhere.length() < 6) {
            flag=false;
        }
        if (!UpperCasePatten.matcher(passwordhere).find()) {
            flag=false;
        }
        if (!lowerCasePatten.matcher(passwordhere).find()) {
            flag=false;
        }
        if (!digitCasePatten.matcher(passwordhere).find()) {
            flag=false;
        }
        return flag;
    }



}