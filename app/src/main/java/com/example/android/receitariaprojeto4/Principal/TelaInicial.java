package com.example.android.receitariaprojeto4.Principal;

import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.example.android.receitariaprojeto4.Adaptador.AdaptadorReceitaRecyclerView;
import com.example.android.receitariaprojeto4.Entidades.Receita;
import com.example.android.receitariaprojeto4.Loader.ReceitaLoader;
import com.example.android.receitariaprojeto4.R;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TelaInicial extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Receita>> {

    private List<Receita> mListaDeReceita = new ArrayList<>();
    private static int PRIMEIRA_POSICAO_LOADER = 0;

    @BindView(R.id.recyclerViewReceitas)
    RecyclerView mRecyclerViewReceitas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_inicial);
        getSupportActionBar().hide();

        ButterKnife.bind(this);

        getSupportLoaderManager().initLoader(PRIMEIRA_POSICAO_LOADER,null,this).forceLoad();
    }

    @NonNull
    @Override
    public Loader<List<Receita>> onCreateLoader(int i, @Nullable Bundle bundle) {
        try {
            return new ReceitaLoader(getApplicationContext(), new URL(getResources().getString(R.string.urlAPIBase)));
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Receita>> loader, List<Receita> receitas) {
        mListaDeReceita = receitas;

        mRecyclerViewReceitas.setAdapter(new AdaptadorReceitaRecyclerView(getApplicationContext(),mListaDeReceita));
        mRecyclerViewReceitas.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayout.VERTICAL,false));
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Receita>> loader) {
        loader.reset();
    }


    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {

        super.onSaveInstanceState(outState, outPersistentState);

    }
}
