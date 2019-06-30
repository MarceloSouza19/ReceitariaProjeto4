package com.example.android.receitariaprojeto4.Entidades;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Receita implements Parcelable {
    private String nomeReceita;
    private int porcao;
    private List<Ingrediente> listaDeIngredientes;
    private List<Etapa> listaDeEtapas;

    public Receita(String nomeReceita, int porcao, List<Ingrediente> listaDeIngredientes, List<Etapa> listaDeEtapas) {
        this.nomeReceita = nomeReceita;
        this.porcao = porcao;
        this.listaDeIngredientes = listaDeIngredientes;
        this.listaDeEtapas = listaDeEtapas;
    }

    protected Receita(Parcel in) {
        nomeReceita = in.readString();
        porcao = in.readInt();
        listaDeIngredientes = in.createTypedArrayList(Ingrediente.CREATOR);
    }

    public static final Creator<Receita> CREATOR = new Creator<Receita>() {
        @Override
        public Receita createFromParcel(Parcel in) {
            return new Receita(in);
        }

        @Override
        public Receita[] newArray(int size) {
            return new Receita[size];
        }
    };

    public String getNomeReceita() {
        return nomeReceita;
    }

    public int getPorcao() {
        return porcao;
    }

    public List<Ingrediente> getListaDeIngredientes() {
        return listaDeIngredientes;
    }

    public List<Etapa> getListaDeEtapas() {
        return listaDeEtapas;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(nomeReceita);
        parcel.writeInt(porcao);
        parcel.writeArray(listaDeIngredientes.toArray());
        parcel.writeArray(listaDeEtapas.toArray());
    }
}
