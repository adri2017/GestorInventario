package OperacionesPedido;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gestorinventario.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import Clases.Pedido;

public class PendienteFragment extends Fragment {

    FirebaseDatabase database;
    DatabaseReference ref;

    View v;
    RecyclerView recyclerpendiente;
    ArrayList<Pedido> listaPedidosPendientes;
    PedidosAdapter pedidosAdapter;


    public PendienteFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_enviados, container,false);
        listaPedidosPendientes = new ArrayList<>();
        recyclerpendiente =  v.findViewById(R.id.recyclerenviados);
        recyclerpendiente.setLayoutManager(new LinearLayoutManager(getContext()));

        llenarListaPendientes();
        return v;
    }

    private void llenarListaPendientes() {

            ref = database.getInstance("https://gestorinventario-41b55-default-rtdb.europe-west1.firebasedatabase.app/")
                    .getReference("Pedidos");

            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){

                        Pedido pedido = new Pedido();
                        pedido.setEstado(snapshot.child("estado").getValue().toString());

                        if(pedido.getEstado().equals("pendiente")){

                            pedido.setId(snapshot.child("id").getValue().toString());
                            pedido.setNombreper(snapshot.child("nombreper").getValue().toString());
                            pedido.setDireccion(snapshot.child("direccion").getValue().toString());
                            pedido.setNombreprod(snapshot.child("nombreprod").getValue().toString());
                            pedido.setCantidad(snapshot.child("cantidad").getValue().toString());
                            listaPedidosPendientes.add(pedido);
                        }
                    }
                    pedidosAdapter = new PedidosAdapter(listaPedidosPendientes);
                    pedidosAdapter.notifyDataSetChanged();
                    recyclerpendiente.setAdapter(pedidosAdapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

    }
}