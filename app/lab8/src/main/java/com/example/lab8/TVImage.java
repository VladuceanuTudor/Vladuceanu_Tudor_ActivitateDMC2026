package com.example.lab8;

public class TVImage {
    private String titlu;
    private String descriere;
    private String imageUrl;
    private String webUrl;

    public TVImage(String titlu, String descriere, String imageUrl, String webUrl) {
        this.titlu = titlu;
        this.descriere = descriere;
        this.imageUrl = imageUrl;
        this.webUrl = webUrl;
    }

    public String getTitlu() { return titlu; }
    public String getDescriere() { return descriere; }
    public String getImageUrl() { return imageUrl; }
    public String getWebUrl() { return webUrl; }
}
