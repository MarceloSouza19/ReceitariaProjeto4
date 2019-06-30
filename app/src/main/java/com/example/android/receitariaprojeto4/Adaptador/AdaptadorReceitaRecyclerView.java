package com.example.android.receitariaprojeto4.Adaptador;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.receitariaprojeto4.Entidades.Receita;
import com.example.android.receitariaprojeto4.Principal.TelaDetalhes;
import com.example.android.receitariaprojeto4.R;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorReceitaRecyclerView extends RecyclerView.Adapter<AdaptadorReceitaRecyclerView.ViewHolder> {

    Context mContext;
    private List<Receita> mListaDeReceita;

    public AdaptadorReceitaRecyclerView(Context mContext, List<Receita> mListaDeReceita) {
        this.mContext = mContext;
        this.mListaDeReceita = mListaDeReceita;
    }

    @NonNull
    @Override
    public AdaptadorReceitaRecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_recycler_receita, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorReceitaRecyclerView.ViewHolder viewHolder, final int numeroLinha) {

        final Receita itemReceita = mListaDeReceita.get(numeroLinha);

        viewHolder.tituloReceita.setText(itemReceita.getNomeReceita());
        viewHolder.rendimentoPorcao.setText(mContext.getResources().getString(R.string.rende) + " " +
                itemReceita.getPorcao() + " " +
                (itemReceita.getPorcao() > 1 ?
                        mContext.getResources().getString(R.string.porcoes) :
                        mContext.getResources().getString(R.string.porcao)));

        viewHolder.itemReceitaCompleto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Capturando o objeto de acordo com a posição clicada
                Receita itemReceita = mListaDeReceita.get(numeroLinha);

                Bundle bundle = new Bundle();
                Intent intent = new Intent(mContext, TelaDetalhes.class);

                //Setando os valores em um pacote para serem transferidos como Lista para a outra Activity
                bundle.putParcelableArrayList(mContext.getResources().getString(R.string.listaIngredientes), (ArrayList<? extends Parcelable>) itemReceita.getListaDeIngredientes());
                bundle.putParcelableArrayList(mContext.getResources().getString(R.string.listaEtapas), (ArrayList<? extends Parcelable>) itemReceita.getListaDeEtapas());


                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                //Setando os valores do pacoteBundle e das variáveis da receita comum
                intent.putExtra(mContext.getResources().getString(R.string.pacoteBundle), bundle);
                intent.putExtra(mContext.getResources().getString(R.string.nomeReceita), itemReceita.getNomeReceita());
                intent.putExtra(mContext.getResources().getString(R.string.rendimentoPorcao), itemReceita.getPorcao());

                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mListaDeReceita.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView itemReceitaCompleto;
        TextView tituloReceita;
        TextView rendimentoPorcao;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tituloReceita = itemView.findViewById(R.id.tituloReceita);
            rendimentoPorcao = itemView.findViewById(R.id.rendimentoPorcao);
            itemReceitaCompleto = itemView.findViewById(R.id.itemReceitaCompleto);
        }
    }
}
