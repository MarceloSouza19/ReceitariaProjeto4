package com.example.android.receitariaprojeto4.Loader;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import com.example.android.receitariaprojeto4.Entidades.Receita;
import com.example.android.receitariaprojeto4.Negocios.ReceitaNegocios;

import java.net.URL;
import java.util.List;

public class ReceitaLoader extends AsyncTaskLoader<List<Receita>> {

    URL mUrl;
    private List<Receita> mListaDeReceitas;

    public ReceitaLoader(Context context, URL url) {
        super(context);
        this.mUrl = url;
    }

    @Nullable
    @Override
    public List<Receita> loadInBackground() {

        ReceitaNegocios receitaNegocios = new ReceitaNegocios(getContext());
        mListaDeReceitas = receitaNegocios.buscarListaDeReceitas(mUrl);

        return mListaDeReceitas;
    }
}
