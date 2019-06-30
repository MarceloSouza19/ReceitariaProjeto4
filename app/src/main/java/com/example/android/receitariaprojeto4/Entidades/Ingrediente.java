package com.example.android.receitariaprojeto4.Entidades;

import android.os.Parcel;
import android.os.Parcelable;

public class Ingrediente  implements Parcelable {

    private double quantidade;
    private String nomeIngrediente;
    private String unidadeMedida;

    public Ingrediente(String nomeIngrediente, double quantidade, String unidadeMedida) {
        this.nomeIngrediente = nomeIngrediente;
        this.quantidade = quantidade;
        this.unidadeMedida = unidadeMedida;
    }

    public double getQuantidade() {
        return quantidade;
    }

    public String getNomeIngrediente() {
        return nomeIngrediente;
    }

    public String getUnidadeMedida() {
        return unidadeMedida;
    }


    public Ingrediente(Parcel parcel) {
        nomeIngrediente = parcel.readString();
        unidadeMedida = parcel.readString();
        quantidade = parcel.readDouble();
    }

    public static final Parcelable.Creator
            CREATOR = new Parcelable.Creator() {

        public Ingrediente createFromParcel(Parcel in) {
            return new Ingrediente(in);
        }

        public Ingrediente[] newArray(int size) {
            return new Ingrediente[size];
        }
    };
    
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(nomeIngrediente);
        parcel.writeString(unidadeMedida);
        parcel.writeDouble(quantidade);
    }
}
