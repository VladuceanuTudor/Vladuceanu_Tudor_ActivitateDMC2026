package com.example.lab4;

import java.io.Serializable;

public class TV implements Serializable {
    private String marca;
    private int diagonala;
    private boolean esteSmartTV;
    private double pret;
    private TipPanel tipPanel;

    public TV(String marca, int diagonala, boolean esteSmartTV, double pret, TipPanel tipPanel) {
        this.marca = marca;
        this.diagonala = diagonala;
        this.esteSmartTV = esteSmartTV;
        this.pret = pret;
        this.tipPanel = tipPanel;
    }

    public String getMarca() { return marca; }
    public int getDiagonala() { return diagonala; }
    public boolean isEsteSmartTV() { return esteSmartTV; }
    public double getPret() { return pret; }
    public TipPanel getTipPanel() { return tipPanel; }
}
