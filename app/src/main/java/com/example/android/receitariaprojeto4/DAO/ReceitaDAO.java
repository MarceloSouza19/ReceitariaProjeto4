package com.example.android.receitariaprojeto4.DAO;

import android.content.Context;
import android.widget.Toast;

import com.example.android.receitariaprojeto4.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

public class ReceitaDAO {

    private static Context context;

    private String jsonResponse = "";
    private static final String METODO_REQUISICAO_GET = "GET";
    public static final int TIMEOUT_LEITURA = 10000;
    public static final int TIMEOUT_CONEXAO = 15000;
    public static final int CODIGO_RESPOSTA_OK = 200;

    public ReceitaDAO(Context context) {
        this.context = context;
    }

    public String receitaConexaoAPI(URL url){

        if (url == null) {
            return jsonResponse;
        }

        try {
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(TIMEOUT_CONEXAO);
            urlConnection.setRequestMethod(METODO_REQUISICAO_GET);
            urlConnection.setReadTimeout(TIMEOUT_LEITURA);
            urlConnection.connect();

            if (urlConnection.getResponseCode() == CODIGO_RESPOSTA_OK) {

                InputStream inputStream = urlConnection.getInputStream();

                jsonResponse = parserObjetoString(inputStream);

                if (!jsonResponse.isEmpty()) {
                    return jsonResponse;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonResponse;
    }


    public static String parserObjetoString (InputStream inputStream) throws IOException {

        StringBuilder stringBuilder = new StringBuilder();


        if(inputStream!=null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream,Charset.forName(context.getResources().getString(R.string.utf_8)));
            BufferedReader reader = new BufferedReader(inputStreamReader);

            String line = reader.readLine();


            while(line!=null){
                stringBuilder.append(line);
                line = reader.readLine();
            }
        }
        return stringBuilder.toString();
    }
}
