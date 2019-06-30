package com.example.android.receitariaprojeto4.Fragmentos;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.receitariaprojeto4.Entidades.Etapa;
import com.example.android.receitariaprojeto4.R;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentoEtapa extends Fragment implements View.OnClickListener {


    private List<Etapa> mListaDeEtapas;
    private int mPosicao;
    private boolean mVersaoTablet;
    private SimpleExoPlayer player;
    private long mPosicaoVideoAtual;
    private boolean mTocarVideo;
    private String nomeDaReceita;

    @BindView(R.id.descricaoCurta)
    TextView descricaoCurta;
    @BindView(R.id.descricao)
    TextView descricao;
    @BindView(R.id.playerDeVideo)
    SimpleExoPlayerView playerDeVideo;
    @BindView(R.id.semVideo)
    ImageView semVideo;
    @BindView(R.id.framePlayerVideo)
    FrameLayout frameLayoutVideo;
    @BindView(R.id.linearFragmentoDetalhesEtapa)
    LinearLayout linearFragmentoDetalhesEtapa;
    @BindView(R.id.proxima_etapa)
    FloatingActionButton proximaEtapa;
    @BindView(R.id.etapa_anterior)
    FloatingActionButton etapaAnterior;
    @BindView(R.id.nomeReceita)
    TextView nomeReceita;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragmento_detalhes_etapa, container, false);

        ButterKnife.bind(this, view);

        Bundle bundle = getArguments();

        if (savedInstanceState != null) {
            mListaDeEtapas = savedInstanceState.getParcelableArrayList(getContext().getResources().getString(R.string.listaEtapas));
            mPosicao = savedInstanceState.getInt(getContext().getResources().getString(R.string.posicao));
            mVersaoTablet = savedInstanceState.getBoolean(getContext().getResources().getString(R.string.versaoTablet));
            mPosicaoVideoAtual = savedInstanceState.getLong(getContext().getResources().getString(R.string.posicaoAtualVideo));
            nomeDaReceita = savedInstanceState.getString(getContext().getResources().getString(R.string.nomeReceita));
            mTocarVideo = false;
        } else {
            mListaDeEtapas = bundle.getParcelableArrayList(getContext().getResources().getString(R.string.pacoteBundle));
            mPosicao = bundle.getInt(getContext().getResources().getString(R.string.posicao));
            mVersaoTablet = bundle.getBoolean(getContext().getResources().getString(R.string.versaoTablet));
            nomeDaReceita = bundle.getString(getContext().getResources().getString(R.string.nomeReceita));
            mPosicaoVideoAtual = 0;
            mTocarVideo = true;
        }

        proximaEtapa.setOnClickListener(this);
        etapaAnterior.setOnClickListener(this);

        atualizarLayout();

        return view;
    }

    @SuppressLint("RestrictedApi")
    public void atualizarLayout() {

        Etapa etapa = mListaDeEtapas.get(mPosicao);

        if(mVersaoTablet){
            nomeReceita.setVisibility(View.GONE);
        }

        if (getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE && mVersaoTablet) {

            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);

            frameLayoutVideo.setLayoutParams(new LinearLayout.LayoutParams(layoutParams));
        } else if (getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT && !mVersaoTablet) {
            descricao.setText(etapa.getDescricao());
            descricaoCurta.setText(etapa.getDescricaoCurta());

            nomeReceita.setVisibility(View.VISIBLE);
            nomeReceita.setText(nomeDaReceita);
        } else if (getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE && !mVersaoTablet) {

            descricao.setVisibility(View.GONE);
            descricaoCurta.setVisibility(View.GONE);
            nomeReceita.setVisibility(View.GONE);

            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);

            frameLayoutVideo.setLayoutParams(new LinearLayout.LayoutParams(layoutParams));

            playerDeVideo.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }

        descricao.setText(etapa.getDescricao());
        descricaoCurta.setText(etapa.getDescricaoCurta());

        if (etapa.getVideoURL().length() > 0) {
            popularVideoPlayer(etapa.getVideoURL());

            semVideo.setVisibility(View.GONE);
            playerDeVideo.setVisibility(View.VISIBLE);
        } else if (etapa.getThumbnailURL().length() > 0) {
            popularVideoPlayer(etapa.getThumbnailURL());

            semVideo.setVisibility(View.GONE);
            playerDeVideo.setVisibility(View.VISIBLE);
        } else {
            semVideo.setVisibility(View.GONE);
            playerDeVideo.setVisibility(View.GONE);
        }

        if (mPosicao == 0) {
            etapaAnterior.setVisibility(View.INVISIBLE);
            proximaEtapa.setVisibility(View.VISIBLE);
        } else if (mPosicao + 1 == mListaDeEtapas.size()) {
            proximaEtapa.setVisibility(View.INVISIBLE);
            etapaAnterior.setVisibility(View.VISIBLE);
        } else {
            etapaAnterior.setVisibility(View.VISIBLE);
            proximaEtapa.setVisibility(View.VISIBLE);
        }
    }

    public void popularVideoPlayer(String url) {

        Uri uri = Uri.parse(url);

        player = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(getContext()),
                new DefaultTrackSelector(), new DefaultLoadControl());

        playerDeVideo.setPlayer(player);
        player.setPlayWhenReady(mTocarVideo);
        player.seekTo(mPosicaoVideoAtual);

        try {
            MediaSource mediaSource = new ExtractorMediaSource(uri,
                    new DefaultHttpDataSourceFactory("ua"),
                    new DefaultExtractorsFactory(), null, null);

            player.prepare(mediaSource, true, false);
        } catch (Exception e) {
            Toast.makeText(getContext(), getContext().getResources().getString(R.string.verificar_rede), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.proxima_etapa:
                mPosicaoVideoAtual = 0;
                mTocarVideo = true;
                if (mListaDeEtapas.size() > mPosicao)
                    mPosicao++;
                break;
            case R.id.etapa_anterior:
                if (mPosicao > 0)
                    mPosicao--;
                break;
        }

        if (player != null) {
            mPosicaoVideoAtual = 0;
            mTocarVideo = true;
            player.release();
        }

        atualizarLayout();
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBoolean(getContext().getResources().getString(R.string.versaoTablet), mVersaoTablet);
        outState.putParcelableArrayList(getContext().getResources().getString(R.string.listaEtapas), (ArrayList<? extends Parcelable>) mListaDeEtapas);
        outState.putInt(getContext().getResources().getString(R.string.posicao), mPosicao);
        outState.putLong(getContext().getResources().getString(R.string.posicaoAtualVideo), player.getCurrentPosition());
        outState.putString(getContext().getResources().getString(R.string.nomeReceita), nomeDaReceita);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        player.release();
    }
}
