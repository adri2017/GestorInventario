package OperacionesPedido;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gestorinventario.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;

import OperacionesProd.ActualizarProd;
import OperacionesProd.OperacionesProd;

public class ActualizarPed extends AppCompatActivity {


    EditText edtactdirecped, edtactnomprodped, edtactnomperped, edtactcantped, edtactestado;
    Button btnactped;
    String direc, nomprod, nomper, cant, estado, idact;
    TextView tvdirped, tvnomperped, tvnomprodped, tvcantped, tvestadped;

    private DatabaseReference ref;
    private FirebaseDatabase database;
    private StorageReference mStorageReference;
    private FirebaseStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_ped);

        //edt, textview y boton
        edtactdirecped = findViewById(R.id.edtactdirecped);
        edtactnomprodped = findViewById(R.id.edtactnomprodped);
        edtactnomperped = findViewById(R.id.edtactnomperped);
        edtactcantped = findViewById(R.id.edtactcantped);
        edtactestado = findViewById(R.id.edtactestado);
        btnactped = findViewById(R.id.btnactped);
        tvdirped = findViewById(R.id.tvdirped);
        tvnomperped = findViewById(R.id.tvnomperped);
        tvnomprodped = findViewById(R.id.tvnomprodped);
        tvcantped = findViewById(R.id.tvcantped);
        tvestadped = findViewById(R.id.tvestadped);

        //referencias base
        storage = FirebaseStorage.getInstance();
        mStorageReference = storage.getReference();
        database = FirebaseDatabase.getInstance();
        ref = database.getReference();


        //Dialog para el id del pedido a actualizar
        EditText actped = new EditText(this);
        AlertDialog.Builder actdialog = new AlertDialog.Builder(this);
        actdialog.setTitle("Actualizar pedido");
        actdialog.setMessage("Introduce el identificador del pedido\n" +
                "que desee actualizar.");
        actdialog.setView(actped);

        actdialog.setPositiveButton("Siguiente", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                idact = actped.getText().toString().trim();
                Toast.makeText(ActualizarPed.this, "Ingrese los datos nuevos.", Toast.LENGTH_SHORT).show();
                cargarDatosPedidos(idact);
            }
        }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent i2 = new Intent(ActualizarPed.this, OperacionesPed.class);
                startActivity(i2);
            }
        });
        AlertDialog alert = actdialog.create();
        alert.show();



        btnactped.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                direc = edtactdirecped.getText().toString().trim();
                nomprod = edtactnomprodped.getText().toString().trim();
                nomper = edtactnomperped.getText().toString().trim();
                cant = edtactcantped.getText().toString().trim();
                estado = edtactestado.getText().toString().trim();

                actualizarPedido(idact, direc, nomprod, nomper, cant, estado);
            }
        });
    }

    private void actualizarPedido(String idact,String direc, String nomprod, String nomper, String cant, String estado) {

        HashMap Pedido = new HashMap();

        if(!direc.isEmpty()){
            Pedido.put("direccion", direc);
        }
        if(!nomprod.isEmpty()){
            Pedido.put("nombreprod", nomprod);
        }
        if(!nomper.isEmpty()){
            Pedido.put("nombreper", nomper);
        }
        if(!cant.isEmpty()){
            Pedido.put("cantidad", cant);
        }
        if(!estado.isEmpty()){
            Pedido.put("estado", estado);
        }

        database = FirebaseDatabase.getInstance("https://gestorinventario-41b55-default-rtdb.europe-west1.firebasedatabase.app/");
        ref = database.getReference("Pedidos");

        ref.child(idact).updateChildren(Pedido).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if(task.isSuccessful()){
                    Toast.makeText(ActualizarPed.this, "Producto actualizado con éxito.", Toast.LENGTH_SHORT).show();
                    Intent i2 = new Intent(ActualizarPed.this, OperacionesPed.class);
                    startActivity(i2);
                }else{
                    Toast.makeText(ActualizarPed.this, "Error actualizando el producto.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void cargarDatosPedidos(String id){

        database = FirebaseDatabase.getInstance("https://gestorinventario-41b55-default-rtdb.europe-west1.firebasedatabase.app/");
        ref = database.getReference().child("Pedidos").child(id);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){

                    String direc2 = snapshot.child("direccion").getValue(String.class);
                    String nomprod2 = snapshot.child("nombreprod").getValue(String.class);
                    String nomper2 = snapshot.child("nombreper").getValue(String.class);
                    String cant2 = snapshot.child("cantidad").getValue(String.class);
                    String estado2 = snapshot.child("estado").getValue(String.class);

                    tvdirped.setText("Direccion: "+direc2);
                    tvnomperped.setText("Dueño del pedido: "+nomper2);
                    tvnomprodped.setText("Producto a enviar: "+nomprod2);
                    tvcantped.setText("Cantidad de producto: "+cant2);
                    tvestadped.setText("Estado del pedido: "+estado2);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }




}