package com.example.android.receitariaprojeto4.Adaptador;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.receitariaprojeto4.Entidades.Ingrediente;
import com.example.android.receitariaprojeto4.R;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorIngredienteRecyclerView extends RecyclerView.Adapter<AdaptadorIngredienteRecyclerView.ViewHolder> {

    Context mContext;
    private List<Ingrediente> mListaDeIngredientes = new ArrayList<>();

    public AdaptadorIngredienteRecyclerView(Context mContext, List<Ingrediente> listaDeIngredientes) {
        this.mContext = mContext;
        this.mListaDeIngredientes = listaDeIngredientes;
    }

    @NonNull
    @Override
    public AdaptadorIngredienteRecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_recycler_ingrediente, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorIngredienteRecyclerView.ViewHolder viewHolder, int numeroLinha) {

        Ingrediente itemIngrediente = mListaDeIngredientes.get(numeroLinha);

        viewHolder.nomeIngrediente.setText(itemIngrediente.getNomeIngrediente());
        viewHolder.quantidadeIngrediente.setText(itemIngrediente.getQuantidade() + "");
        viewHolder.unidadeMedida.setText(itemIngrediente.getUnidadeMedida());

        viewHolder.itemIngredienteCompleto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return mListaDeIngredientes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView itemIngredienteCompleto;
        TextView nomeIngrediente;
        TextView quantidadeIngrediente;
        TextView unidadeMedida;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nomeIngrediente = itemView.findViewById(R.id.nomeIngrediente);
            quantidadeIngrediente = itemView.findViewById(R.id.quantidade);
            itemIngredienteCompleto = itemView.findViewById(R.id.itemIngredienteCompleto);
            unidadeMedida = itemView.findViewById(R.id.unidadeMedida);
        }
    }
}
