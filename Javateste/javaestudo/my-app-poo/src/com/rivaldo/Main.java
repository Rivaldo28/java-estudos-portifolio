package com.rivaldo;

public class Main {
    public static void main(String[] args){
        Auto clio = new Auto();
        clio.setModelo("Renault");
        clio.setMarca("Clio");
        clio.setAno(2014);
        clio.setKm(0);
        clio.setDominio("PHN123");

        Auto megane = new Auto("Renault", "Megane", 2016, 0, "PJK653");
        Auto megane2 = new Auto("Renault", "Megane", 2016, 0, "PJK653");

        System.out.println(clio.getMarca() + " - " + clio.getModelo());
        System.out.println(megane.getMarca() + " - " + megane.getModelo());

        System.out.println(clio.equals(megane));
        System.out.println(megane.equals(megane2));
        System.out.println(megane.hashCode() + " - " + megane2.hashCode());
    }
}
