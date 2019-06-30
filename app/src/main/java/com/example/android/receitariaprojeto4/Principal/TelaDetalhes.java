package com.example.android.receitariaprojeto4.Principal;

import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.android.receitariaprojeto4.Entidades.Etapa;
import com.example.android.receitariaprojeto4.Entidades.Ingrediente;
import com.example.android.receitariaprojeto4.Fragmentos.FragmentoEtapa;
import com.example.android.receitariaprojeto4.Fragmentos.FragmentoDetalhesReceita;
import com.example.android.receitariaprojeto4.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TelaDetalhes extends AppCompatActivity implements View.OnClickListener {

    private List<Ingrediente> mListaDeIngredientes;
    private List<Etapa> mListaDeEtapas;
    private String mNomeReceita;
    private int mPorcao;
    private boolean mVersaoTablet;
    private boolean mTelaEtapasAtiva;

    @BindView(R.id.frameUniversal)
    FrameLayout frameUniversal;

    @BindView(R.id.etapas)
    @Nullable
    FloatingActionButton botaoEtapas;

    @BindView(R.id.frameEtapas)
    @Nullable
    FrameLayout frameEtapas;

    @BindView(R.id.nomeReceita)
    TextView nomeReceita;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.tela_detalhes);
        ButterKnife.bind(this);

        getSupportActionBar().hide();

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getBundleExtra(getResources().getString(R.string.pacoteBundle));

            mListaDeIngredientes = extras.getParcelableArrayList(getResources().getString(R.string.listaIngredientes));
            mListaDeEtapas = extras.getParcelableArrayList(getResources().getString(R.string.listaEtapas));

            mNomeReceita = getIntent().getExtras().getString(getResources().getString(R.string.nomeReceita));
            mPorcao = getIntent().getExtras().getInt(getResources().getString(R.string.rendimentoPorcao));

            if (frameEtapas != null) {
                mVersaoTablet = true;

                this.exibirFrameEtapasReceita();
            } else {
                mVersaoTablet = false;
                botaoEtapas.setOnClickListener(this);
            }

            this.exibirFrameDetalhesReceita(false);

        } else {
            mListaDeIngredientes = savedInstanceState.getParcelableArrayList(getResources().getString(R.string.listaIngredientes));
            mListaDeEtapas = savedInstanceState.getParcelableArrayList(getResources().getString(R.string.listaEtapas));

            mNomeReceita = savedInstanceState.getString(getResources().getString(R.string.nomeReceita));
            mPorcao = savedInstanceState.getInt(getResources().getString(R.string.rendimentoPorcao));
            mVersaoTablet = savedInstanceState.getBoolean(getResources().getString(R.string.versaoTablet));
            mTelaEtapasAtiva = savedInstanceState.getBoolean(getResources().getString(R.string.telaEtapasReceitaAtiva));

            if (mTelaEtapasAtiva && !mVersaoTablet) {
                nomeReceita.setVisibility(View.GONE);
                botaoEtapas.hide();
            }

            if (mVersaoTablet)
                this.exibirFrameDetalhesReceita(true);
        }
    }

    public void exibirFrameDetalhesReceita(boolean atualizarPainel) {

        FragmentoDetalhesReceita fragmentoDetalhesReceita = new FragmentoDetalhesReceita();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(getResources().getString(R.string.pacoteBundle), (ArrayList<? extends Parcelable>) mListaDeIngredientes);
        fragmentoDetalhesReceita.setArguments(bundle);

        if (atualizarPainel) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frameUniversal, fragmentoDetalhesReceita).commit();
        } else {
            getSupportFragmentManager().beginTransaction().add(R.id.frameUniversal, fragmentoDetalhesReceita).commit();
        }

        mTelaEtapasAtiva = false;

        if (!mTelaEtapasAtiva) {
            nomeReceita.setVisibility(View.VISIBLE);
            nomeReceita.setText(mNomeReceita);
        }
    }

    public void exibirFrameEtapasReceita() {

        nomeReceita.setVisibility(View.GONE);

        if (botaoEtapas != null)
            botaoEtapas.hide();

        FragmentoEtapa fragmentoEtapa = new FragmentoEtapa();
        Bundle bundleEtapas = new Bundle();

        bundleEtapas.putInt(getResources().getString(R.string.posicao), 0);
        bundleEtapas.putBoolean(getResources().getString(R.string.versaoTablet), mVersaoTablet);
        bundleEtapas.putParcelableArrayList(getResources().getString(R.string.pacoteBundle), (ArrayList<? extends Parcelable>) mListaDeEtapas);
        bundleEtapas.putString(getResources().getString(R.string.nomeReceita), mNomeReceita);

        fragmentoEtapa.setArguments(bundleEtapas);

        if (mVersaoTablet) {
            getSupportFragmentManager().beginTransaction().add(R.id.frameEtapas, fragmentoEtapa).commit();
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.frameUniversal, fragmentoEtapa).commit();
        }
        mTelaEtapasAtiva = true;

        if (mTelaEtapasAtiva && mVersaoTablet) {
            nomeReceita.setVisibility(View.VISIBLE);
            nomeReceita.setText(mNomeReceita);
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList(getApplicationContext().getResources().getString(R.string.listaIngredientes), (ArrayList<? extends Parcelable>) mListaDeIngredientes);
        outState.putParcelableArrayList(getApplicationContext().getResources().getString(R.string.listaEtapas), (ArrayList<? extends Parcelable>) mListaDeEtapas);
        outState.putString(getApplicationContext().getResources().getString(R.string.nomeReceita), mNomeReceita);
        outState.putInt(getApplicationContext().getResources().getString(R.string.rendimentoPorcao), mPorcao);
        outState.putBoolean(getApplicationContext().getResources().getString(R.string.versaoTablet), mVersaoTablet);
        outState.putBoolean(getResources().getString(R.string.telaEtapasReceitaAtiva), mTelaEtapasAtiva);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.etapas:
                if (!mTelaEtapasAtiva)
                    this.exibirFrameEtapasReceita();
                else
                    this.exibirFrameDetalhesReceita(true);

        }
    }

    @Override
    public void onBackPressed() {
        if (mTelaEtapasAtiva) {
            this.exibirFrameDetalhesReceita(true);
            botaoEtapas.show();
        } else {
            super.onBackPressed();
        }
    }
}
