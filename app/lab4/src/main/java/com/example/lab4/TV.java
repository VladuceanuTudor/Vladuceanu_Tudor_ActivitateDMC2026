package com.example.lab4;

import java.io.Serializable;
import java.util.Date;

public class TV implements Serializable {
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

    public String getMarca() { return marca; }
    public int getDiagonala() { return diagonala; }
    public boolean isEsteSmartTV() { return esteSmartTV; }
    public double getPret() { return pret; }
    public TipPanel getTipPanel() { return tipPanel; }
    public Date getDataAdaugarii() { return dataAdaugarii; }

    @Override
    public String toString() {
        return marca + " | " + diagonala + "\" | " +
                tipPanel + " | " + (esteSmartTV ? "Smart" : "Basic") +
                " | " + pret + " RON | " + dataAdaugarii;
    }
}