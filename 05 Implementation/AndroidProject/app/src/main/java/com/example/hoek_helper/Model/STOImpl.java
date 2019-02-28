package com.example.hoek_helper.Model;

public class STOImpl implements STO {

    private double vaerdi;


    public STOImpl() {
    }

    @Override
    public void setVaerdi(double vaerdi) {
        this.vaerdi = vaerdi;
    }

    @Override
    public double getVaerdi() {
        return vaerdi;
    }
}
