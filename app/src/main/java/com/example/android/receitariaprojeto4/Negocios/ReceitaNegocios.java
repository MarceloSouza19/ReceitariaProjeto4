package com.example.android.receitariaprojeto4.Negocios;

import android.content.Context;

import com.example.android.receitariaprojeto4.DAO.ReceitaDAO;
import com.example.android.receitariaprojeto4.Entidades.Etapa;
import com.example.android.receitariaprojeto4.Entidades.Ingrediente;
import com.example.android.receitariaprojeto4.Entidades.Receita;
import com.example.android.receitariaprojeto4.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ReceitaNegocios {

    Context mContext;
    List<Receita> mListaDeReceitas;

    public ReceitaNegocios(Context context) {
        this.mContext = context;
    }

    public List<Receita> buscarListaDeReceitas(URL url){

        ReceitaDAO receitaDAO = new ReceitaDAO(mContext);

        String resposta = receitaDAO.receitaConexaoAPI(url);

        try {
            return this.parserListaDeReceitas(resposta);
        } catch (JSONException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<Receita> parserListaDeReceitas(String listaReceitasEmString) throws JSONException {

        mListaDeReceitas = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(listaReceitasEmString);

        if (jsonArray.length() > 0) {

            for (int linhaReceitas = 0; linhaReceitas < jsonArray.length(); linhaReceitas++) {

                JSONObject itemReceitaJson = jsonArray.getJSONObject(linhaReceitas);

                String nomeReceita;
                int porcao;
                List<Ingrediente> listaDeIngredientes;
                List<Etapa> listaDeEtapas;

                nomeReceita = itemReceitaJson.getString(mContext.getResources().getString(R.string.name));
                porcao = itemReceitaJson.getInt(mContext.getResources().getString(R.string.servings));
                listaDeIngredientes = parserListaDeIngredientes(itemReceitaJson);
                listaDeEtapas = parserListaDeEtapas(itemReceitaJson);

                Receita receita = new Receita(nomeReceita,porcao,listaDeIngredientes,listaDeEtapas);

                mListaDeReceitas.add(receita);
            }
        }
        return mListaDeReceitas;
    }

    public List<Ingrediente> parserListaDeIngredientes(JSONObject itemReceitaJson) throws JSONException {

        List<Ingrediente> listaDeIngredientes = new ArrayList<>();
        JSONArray listaIngredientesJson = itemReceitaJson.getJSONArray(mContext.getResources().getString(R.string.ingredients));

        for (int linhaIngredientes = 0; linhaIngredientes < listaIngredientesJson.length(); linhaIngredientes++) {

            JSONObject ingredienteJson = listaIngredientesJson.getJSONObject(linhaIngredientes);

            String nomeIngrediente;
            double quantidade;
            String unidadeMedida;

            nomeIngrediente = ingredienteJson.getString(mContext.getResources().getString(R.string.ingredient));
            quantidade = ingredienteJson.getDouble(mContext.getResources().getString(R.string.quantity));
            unidadeMedida = ingredienteJson.getString(mContext.getResources().getString(R.string.measure));

            Ingrediente ingrediente = new Ingrediente(nomeIngrediente, quantidade, unidadeMedida);

            listaDeIngredientes.add(ingrediente);
        }

        return listaDeIngredientes;
    }

    public List<Etapa> parserListaDeEtapas(JSONObject itemReceitaJson) throws JSONException {

        List<Etapa> listaDeEtapas = new ArrayList<>();
        JSONArray listaIngredientesJson = itemReceitaJson.getJSONArray(mContext.getResources().getString(R.string.steps));

        for (int linhaEtapa = 0; linhaEtapa < listaIngredientesJson.length(); linhaEtapa++) {

            JSONObject etapaJson = listaIngredientesJson.getJSONObject(linhaEtapa);

            String descricao;
            String descricaoCurta;
            String videoURL;
            String thumbnailURL;

            descricao = etapaJson.getString(mContext.getResources().getString(R.string.description));
            descricaoCurta = etapaJson.getString(mContext.getResources().getString(R.string.shortDescription));
            videoURL = etapaJson.getString(mContext.getResources().getString(R.string.videourl));
            thumbnailURL = etapaJson.getString(mContext.getResources().getString(R.string.thumbnailURL));

            Etapa etapa = new Etapa(descricao, descricaoCurta, videoURL, thumbnailURL);

            listaDeEtapas.add(etapa);
        }

        return listaDeEtapas;
    }
}
