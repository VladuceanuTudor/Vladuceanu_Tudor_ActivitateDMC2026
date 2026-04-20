package com.example.lab8;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "televizoare")
public class TV {

    @PrimaryKey(autoGenerate = true)
    private int id;

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

    // Getteri
    public int getId() { return id; }
    public String getMarca() { return marca; }
    public int getDiagonala() { return diagonala; }
    public boolean isEsteSmartTV() { return esteSmartTV; }
    public double getPret() { return pret; }
    public TipPanel getTipPanel() { return tipPanel; }
    public Date getDataAdaugarii() { return dataAdaugarii; }

    // Setteri
    public void setId(int id) { this.id = id; }
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
