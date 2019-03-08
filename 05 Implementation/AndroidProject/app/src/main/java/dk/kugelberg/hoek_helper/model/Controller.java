package dk.kugelberg.hoek_helper.model;

public interface Controller {


    Tabel getTabel();
    void lavRaekke();
    Raekke hentRaekke(int raekkenummer);
    void sletRaekke(int raekkenummer);

    int hentTabelStr();

    // Opdaterer værdier og beregner mulige udregninger
    void opdaterRaekke(int raekkenummer);




}
