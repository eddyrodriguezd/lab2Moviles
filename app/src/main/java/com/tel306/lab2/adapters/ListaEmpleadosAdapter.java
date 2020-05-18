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
import com.tel306.lab2.entidades.Empleado;

public class ListaEmpleadosAdapter extends RecyclerView.Adapter<ListaEmpleadosAdapter.EmpleadoViewHolder> {

    Empleado[] listaEmpleados;
    Context contexto;

    public ListaEmpleadosAdapter(Empleado[] lista, Context c) {
        this.listaEmpleados = lista;
        this.contexto = c;
    }

    public static class EmpleadoViewHolder extends RecyclerView.ViewHolder {
        TextView txtViewNombreValor;
        TextView txtViewApellidoValor;
        TextView txtEmailValor;
        TextView txtNumeroValor;
        TextView txtTituloTrabajoValor;
        TextView txtSalarioValor;
        TextView txtDepartamento;

        public EmpleadoViewHolder(@NonNull View itemView) {
            super(itemView);
            txtViewNombreValor = itemView.findViewById(R.id.txtNombreEmpleadoValor);
            txtViewApellidoValor = itemView.findViewById(R.id.txtViewApellidoValor);
            txtEmailValor = itemView.findViewById(R.id.txtEmailValor);
            txtNumeroValor = itemView.findViewById(R.id.txtNumeroValor);
            txtTituloTrabajoValor = itemView.findViewById(R.id.txtTituloTrabajoValor);
            txtSalarioValor = itemView.findViewById(R.id.txtSalarioValor);
            txtDepartamento = itemView.findViewById(R.id.txtDepartamentoValor);
        }
    }

    @NonNull
    @Override
    public EmpleadoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(contexto).inflate(R.layout.single_empleado, parent, false);
        EmpleadoViewHolder empleadoViewHolder = new EmpleadoViewHolder(itemView);
        return empleadoViewHolder;
    }

    @Override
    public void onBindViewHolder(EmpleadoViewHolder holder, int position) {
        Empleado empleado = listaEmpleados[position];
        holder.txtViewNombreValor.setText(empleado.getFirstName());
        holder.txtViewApellidoValor.setText(empleado.getLastName());
        holder.txtEmailValor.setText(empleado.getEmail());
        holder.txtNumeroValor.setText(String.valueOf(empleado.getPhoneNumber()));
        holder.txtTituloTrabajoValor.setText(empleado.getJobId().getJobTitle());
        holder.txtSalarioValor.setText(String.valueOf(empleado.getSalary()));
        holder.txtDepartamento.setText(empleado.getDepartmentId().getDepartmentName());
    }

    @Override
    public int getItemCount() {
        return listaEmpleados.length;
    }
}