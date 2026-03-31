package com.example.lab4;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Date;

public class TV implements Parcelable {
    private String marca;
    private int diagonala;
    private boolean esteSmartTV;
    private double pret;
    private TipPanel tipPanel;
    private Date dataAdaugarii;

    public TV(String marca, int diagonala, boolean esteSmartTV, double pret, TipPanel tipPanel, Date dataAdaugarii) {
        this.marca = marca;
        this.diagonala = diagonala;
        this.esteSmartTV = esteSmartTV;
        this.pret = pret;
        this.tipPanel = tipPanel;
        this.dataAdaugarii = dataAdaugarii;
    }

    // Parcelable: citire din Parcel
    protected TV(Parcel in) {
        marca = in.readString();
        diagonala = in.readInt();
        esteSmartTV = in.readByte() != 0;
        pret = in.readDouble();
        tipPanel = TipPanel.valueOf(in.readString());
        dataAdaugarii = new Date(in.readLong());
    }

    // Parcelable: scriere în Parcel
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(marca);
        dest.writeInt(diagonala);
        dest.writeByte((byte) (esteSmartTV ? 1 : 0));
        dest.writeDouble(pret);
        dest.writeString(tipPanel.name());
        dest.writeLong(dataAdaugarii.getTime());
    }

    @Override
    public int describeContents() { return 0; }

    public static final Creator<TV> CREATOR = new Creator<TV>() {
        @Override
        public TV createFromParcel(Parcel in) { return new TV(in); }

        @Override
        public TV[] newArray(int size) { return new TV[size]; }
    };

    // Getteri
    public String getMarca() { return marca; }
    public int getDiagonala() { return diagonala; }
    public boolean isEsteSmartTV() { return esteSmartTV; }
    public double getPret() { return pret; }
    public TipPanel getTipPanel() { return tipPanel; }
    public Date getDataAdaugarii() { return dataAdaugarii; }

    // Setteri (necesari pentru modificare)
    public void setMarca(String marca) { this.marca = marca; }
    public void setDiagonala(int diagonala) { this.diagonala = diagonala; }
    public void setEsteSmartTV(boolean esteSmartTV) { this.esteSmartTV = esteSmartTV; }
    public void setPret(double pret) { this.pret = pret; }
    public void setTipPanel(TipPanel tipPanel) { this.tipPanel = tipPanel; }
    public void setDataAdaugarii(Date dataAdaugarii) { this.dataAdaugarii = dataAdaugarii; }

    @Override
    public String toString() {
        return marca + " | " + diagonala + "\" | " +
                tipPanel + " | " + (esteSmartTV ? "Smart" : "Basic") +
                " | " + pret + " RON";
    }
}