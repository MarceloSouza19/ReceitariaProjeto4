package com.example.android.receitariaprojeto4.Entidades;

import android.os.Parcel;
import android.os.Parcelable;

public class Etapa implements Parcelable {

    private String descricao;
    private String descricaoCurta;
    private String videoURL;
    private String thumbnailURL;

    public Etapa(String descricao, String descricaoCurta, String videoURL, String thumbnailURL) {
        this.descricao = descricao;
        this.descricaoCurta = descricaoCurta;
        this.videoURL = videoURL;
        this.thumbnailURL = thumbnailURL;
    }

    protected Etapa(Parcel in) {
        descricao = in.readString();
        descricaoCurta = in.readString();
        videoURL = in.readString();
        thumbnailURL = in.readString();
    }

    public static final Creator<Etapa> CREATOR = new Creator<Etapa>() {
        @Override
        public Etapa createFromParcel(Parcel in) {
            return new Etapa(in);
        }

        @Override
        public Etapa[] newArray(int size) {
            return new Etapa[size];
        }
    };

    public String getDescricao() {
        return descricao;
    }

    public String getDescricaoCurta() {
        return descricaoCurta;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(descricao);
        parcel.writeString(descricaoCurta);
        parcel.writeString(videoURL);
        parcel.writeString(thumbnailURL);
    }
}
