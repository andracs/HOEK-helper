package com.example.hoek_helper.Model;

import org.junit.Test;

import static org.junit.Assert.*;

public class STOImplTest {
    double vaerdi = 200.3;

    @Test
    public void setVaerdi() {
        STO sto = new STOImpl() ;
        sto.setVaerdi(vaerdi);
        assertTrue(sto.getVaerdi() == vaerdi);

    }

    @Test
    public void getVaerdi() {

    }
}