package com.rivaldo;

import java.util.Objects;

public class Auto {

    private String marca;
    private String modelo;
    private int ano;
    private int km;
    private String dominio;

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public int getKm() {
        return km;
    }

    public void setKm(int km) {
        this.km = km;
    }

    public String getDominio() {
        return dominio;
    }

    public void setDominio(String dominio) {
        this.dominio = dominio;
    }

    public Auto() {
    }

    public Auto(String marca, String modelo, int ano, int km, String dominio) {
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.km = km;
        this.dominio = dominio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Auto auto = (Auto) o;
        return ano == auto.ano && km == auto.km && marca.equals(auto.marca) && modelo.equals(auto.modelo) && dominio.equals(auto.dominio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(marca, modelo, ano, km, dominio);
    }
}
