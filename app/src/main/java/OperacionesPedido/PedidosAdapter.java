package OperacionesPedido;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestorinventario.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import Clases.Pedido;
import OperacionesProd.AdapterMostrar;

public class PedidosAdapter extends RecyclerView.Adapter<PedidosAdapter.PedidoViewHolder> {

    private Context context;
    private ArrayList<Pedido>listaPedidos;

    public PedidosAdapter (ArrayList<Pedido>listaPedidos){
        this.listaPedidos = listaPedidos;
    }

    @NonNull
    @Override
    public PedidoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_pedido, parent, false);
        return new PedidoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PedidoViewHolder holder, int position) {

        Pedido pedido = listaPedidos.get(position);
        holder.direccion.setText("Direccion del pedido: "+pedido.getDireccion());
        holder.nombreper.setText("Dueño del pedido: "+pedido.getNombreper());
        holder.nombreprod.setText("Nombre del producto: "+pedido.getNombreprod());
        holder.cantidad.setText("Cantidad del producto: "+pedido.getCantidad());
        holder.identificador.setText("Identificador: "+pedido.getId());
    }

    @Override
    public int getItemCount() {
        return listaPedidos.size();
    }

    public class PedidoViewHolder extends RecyclerView.ViewHolder {

        TextView nombreper, nombreprod, direccion, cantidad, identificador;

        public PedidoViewHolder(@NonNull View itemView) {
            super(itemView);

            nombreper = itemView.findViewById(R.id.tvnombrefrag);
            nombreprod = itemView.findViewById(R.id.tvnomprodfrag);
            direccion = itemView.findViewById(R.id.tvdirfrag);
            cantidad = itemView.findViewById(R.id.tvcantfrag);
            identificador = itemView.findViewById(R.id.tvidpedido);
        }
    }
}
