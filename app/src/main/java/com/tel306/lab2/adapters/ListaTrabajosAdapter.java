package com.tel306.lab2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.tel306.lab2.R;
import com.tel306.lab2.entidades.Trabajo;

public class ListaTrabajosAdapter extends RecyclerView.Adapter<ListaTrabajosAdapter.TrabajoViewHolder> {

    Trabajo[] listaTrabajos;
    Context contexto;

    public ListaTrabajosAdapter(Trabajo[] lista, Context c){
        this.listaTrabajos = lista;
        this.contexto = c;
    }

    public static class TrabajoViewHolder extends RecyclerView.ViewHolder{
        TextView txtViewNombreValor;
        TextView txtSalarioMaxValor;
        TextView txtSalarioMinValor;
        TextView txtDepartamento;

        public TrabajoViewHolder(@NonNull View itemView) {
            super(itemView);
            txtViewNombreValor = itemView.findViewById(R.id.txtNombreTrabajoValor);
            txtSalarioMaxValor = itemView.findViewById(R.id.txtSalarioMaxValor);
            txtSalarioMinValor = itemView.findViewById(R.id.txtSalarioMinValor);
            txtDepartamento = itemView.findViewById(R.id.txtDepartamentoValor);
        }
    }

    @NonNull
    @Override
    public TrabajoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(contexto).inflate(R.layout.single_trabajo, parent, false);
        TrabajoViewHolder trabajoViewHolder = new TrabajoViewHolder(itemView);
        return trabajoViewHolder;
    }

    @Override
    public void onBindViewHolder(TrabajoViewHolder holder, int position) {
        Trabajo trabajo = listaTrabajos[position];
        holder.txtViewNombreValor.setText(trabajo.getJobTitle());
        holder.txtSalarioMaxValor.setText(String.valueOf(trabajo.getMaxSalary()));
        holder.txtSalarioMinValor.setText(String.valueOf(trabajo.getMinSalary()));
        holder.txtDepartamento.setText("");
    }

    @Override
    public int getItemCount() {
        return listaTrabajos.length;
    }
}
