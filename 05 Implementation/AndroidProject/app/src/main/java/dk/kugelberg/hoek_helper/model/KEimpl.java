package dk.kugelberg.hoek_helper.model;

import androidx.lifecycle.MutableLiveData;
import static java.lang.Double.NaN;

public class KEimpl implements KE {

    private KO ko;
    private X x;
    private SE se;
    private VE ve;

    private MutableLiveData<Double> vaerdi = new MutableLiveData<>();
    private MutableLiveData<Boolean> erBeregnet = new MutableLiveData<>();

    @Override
    public void init(KO ko, X x, SE se, VE ve){
        this.ko = ko;
        this.x = x;
        this.se = se;
        this.ve = ve;
    }

      @Override
    public void setVaerdi(double x) {
        if (x < 0) {
            throw new NegativVaerdiException();
        } else {
            vaerdi.setValue(x);
            setBeregnet(false);
        }
    }

    @Override
    public double getVaerdi(){
        return vaerdi.getValue();
    }

    @Override
    public void setBeregnet(boolean val){
        erBeregnet.setValue(val);
    }

    @Override
    public boolean getBeregnet(){

        return erBeregnet.getValue();
    }

    @Override
    public void beregn() {

        if(ko.getVaerdi() != NaN && x.getVaerdi() != NaN){

            vaerdi.setValue(ko.getVaerdi() / x.getVaerdi());
            setBeregnet(true);

        }
        else if(se.getVaerdi() != NaN && ve.getVaerdi() != NaN){
            vaerdi.setValue(se.getVaerdi() - ve.getVaerdi());
            setBeregnet(true);

        }
        else if(getBeregnet()){
            setVaerdi(NaN);

        }
        if (this.vaerdi.getValue() == NaN) this.erBeregnet.setValue(false);
    }
}
