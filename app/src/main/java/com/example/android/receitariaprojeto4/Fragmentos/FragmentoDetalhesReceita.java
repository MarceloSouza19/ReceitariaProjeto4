package com.example.android.receitariaprojeto4.Fragmentos;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.receitariaprojeto4.Adaptador.AdaptadorIngredienteRecyclerView;
import com.example.android.receitariaprojeto4.Entidades.Ingrediente;
import com.example.android.receitariaprojeto4.Entidades.Receita;
import com.example.android.receitariaprojeto4.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentoDetalhesReceita extends Fragment {

    @BindView(R.id.recyclerViewIngredientes)
    RecyclerView mRecyclerViewIngredientes;

    private List<Ingrediente> mListaDeIngrediente;

    private String mNomeReceita;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragmento_detalhes_receita, container, false);

        ButterKnife.bind(this, view);

        Bundle bundle = getArguments();

        List<Ingrediente> listaDeIngredientes;

        listaDeIngredientes = bundle.getParcelableArrayList(getContext().getResources().getString(R.string.pacoteBundle));

        mListaDeIngrediente = listaDeIngredientes;

        mRecyclerViewIngredientes.setAdapter(new AdaptadorIngredienteRecyclerView(getContext(), mListaDeIngrediente));

        mRecyclerViewIngredientes.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        return view;

    }
}
