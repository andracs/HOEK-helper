package dk.kugelberg.hoek_helper.model;

import static java.lang.Double.NaN;

public class KEimpl extends KE {

    private double vaerdi;

    private KO ko;
    private X x;

    private SE se;
    private VE ve;

    private boolean erBeregnet = false;void init(KO ko, X x, SE se, VE ve);

    @Override
    public void init(KO ko, X x, SE se, VE ve){
        this.ko = ko;
        this.x = x;
        this.se = se;
        this.ve = ve;
    }

    @Override
    public void setVaerdi(double vaerdi){

        if (vaerdi < 0) {
            throw new NegativVaerdiException();
        } else {
            this.vaerdi = vaerdi;
            erBeregnet = false;
        }

    }

    @Override
    public double getVaerdi(){

        return vaerdi;

    }

    @Override
    private void setBeregnet(boolean val){
        erBeregnet = val;
    }

    @Override
    public boolean getBeregnet(){
        return erBeregnet;
    }

    @Override
    public void beregn() {

        if(ko.getVaerdi() != NaN && x.getVaerdi() != NaN){

            this.vaerdi = ko.getVaerdi() / x.getVaerdi();
            setBeregnet(true);

        }
        else if(se.getVaerdi() != NaN && ve.getVaerdi() != NaN){

            this.vaerdi = se.getVaerdi() - ve.getVaerdi();
            setBeregnet(true);

        }
        else if(getBeregnet()){

            this.vaerdi = NaN;

        }

    }

}
