package OperacionesProd;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gestorinventario.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.UUID;

public class AgregarProd extends AppCompatActivity {

    EditText edtnombreprod, edtprecioprod, edtcantiprod, edtdescprod;
    Button btnanadirprod, btnimg;
    ImageView imageprod;
    public Uri imageUri;
    TextView tvidimagen;

    private FirebaseStorage storage;
    private StorageReference mStorageReference;
    private FirebaseDatabase database;
    private DatabaseReference ref;

    public static final int PICK_IMAGE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_prod);

        edtnombreprod = findViewById(R.id.edtagreNombre);
        edtprecioprod = findViewById(R.id.edtagrprecio);
        edtcantiprod = findViewById(R.id.edtagrcantidad);
        edtdescprod = findViewById(R.id.edtagrdescrip);
        btnanadirprod = findViewById(R.id.btnagrprod);
        btnimg = findViewById(R.id.btnimg);
        imageprod = findViewById(R.id.imageprod);
        tvidimagen = findViewById(R.id.tvidimagen);


        storage = FirebaseStorage.getInstance();
        mStorageReference = storage.getReference();
        database = FirebaseDatabase.getInstance();

        btnimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }
        });

        btnanadirprod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subirProducto();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
                imageUri = data.getData();
                imageprod.setImageURI(imageUri);
        }else{
            Toast.makeText(AgregarProd.this, "Por favor, seleccione una imagen",Toast.LENGTH_LONG).show();
        }
    }

    private void subirProducto() {

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Subiendo el producto...");
        pd.show();

        //codigo para subir imagen
        final String ramdomKey = UUID.randomUUID().toString();
        StorageReference refs = mStorageReference.child("ImagenProducto/"+ramdomKey);
        refs.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(@NonNull UploadTask.TaskSnapshot taskSnapshot) {

                pd.dismiss();
                Snackbar.make(findViewById(R.id.imageprod), "Imagen subida", Snackbar.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(AgregarProd.this, "Error al subir la imagen",Toast.LENGTH_LONG).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progresspercent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                pd.setMessage("Progreso: "+ (int) progresspercent + "%");
            }
        });

        //subir url de la imagen a realtime y los demas datos
        StorageReference nombreimg = mStorageReference.child("ImagenProducto/"+ramdomKey);
        nombreimg.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                nombreimg.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(@NonNull Uri uri) {
                        database = FirebaseDatabase.getInstance("https://gestorinventario-41b55-default-rtdb.europe-west1.firebasedatabase.app/");
                        ref = database.getReference("Inventario/"+ramdomKey);

                        String nombre = edtnombreprod.getText().toString();
                        String precio = edtprecioprod.getText().toString();
                        String cantidad = edtcantiprod.getText().toString();
                        String descripcion = edtdescprod.getText().toString();

                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put("urlimagen", String.valueOf(uri));
                        hashMap.put("nombre", nombre);
                        hashMap.put("precio", precio);
                        hashMap.put("cantidad", cantidad);
                        hashMap.put("descripcion", descripcion);

                        ref.setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(@NonNull Void unused) {
                                Toast.makeText(AgregarProd.this, "Datos subidos con éxito.",Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });
            }
        });
        /*
        //codigo para subir los demas datos
        String nombre = edtnombreprod.getText().toString();
        String precio = edtprecioprod.getText().toString();
        String cantidad = edtcantiprod.getText().toString();
        String descripcion = edtdescprod.getText().toString();

        Producto producto = new Producto(cantidad,descripcion,nombre,precio);

        database = FirebaseDatabase.getInstance("https://gestorinventario-41b55-default-rtdb.europe-west1.firebasedatabase.app/");
        ref = database.getReference("Inventario");
        ref.child(ramdomKey).setValue(producto);
        */

        Intent i = new Intent(AgregarProd.this, OperacionesProd.class);
        startActivity(i);

    }


}