package dk.kugelberg.hoek_helper.model;

import androidx.lifecycle.MutableLiveData;
import static java.lang.Double.NaN;

public class XImpl implements X {

    private VO vo;
    private VE ve;
    private X xOver;
    private X xUnder;   //Jeg ved ikke om vi nogensinde får brug for denne?
    private KO ko;
    private KE ke;
    private VO voOver;
    private VO voUnder;   //Jeg ved ikke om vi nogensinde får brug for denne?
    private DOMK domk;
    private DOMK domkUnder;    //Jeg ved ikke om vi nogensinde får brug for denne?
    private STO sto;
    private SE se;
    private GROMK gromk;

    private MutableLiveData<Double> vaerdi = new MutableLiveData<>();
    private MutableLiveData<Boolean> erBeregnet = new MutableLiveData<>();

    @Override
    public void init(VO vo, VE ve, DOMK domk, STO sto, SE se, GROMK gromk, X xOver, VO voOver, X xUnder, VO voUnder, DOMK domkUnder) {
        this.vo = vo;
        this.ve = ve;
        this.domk = domk;
        this.sto = sto;
        this.se = se;
        this.gromk = gromk;
        vaerdi.setValue(NaN);
        erBeregnet.setValue(false);
        this.xOver = xOver;
        this.voOver = voOver;
        this.xUnder = xUnder;
        this.voUnder = voUnder;
        this.domkUnder = domkUnder;
    }

    @Override
    public void setBeregnet(boolean val){
        erBeregnet.setValue(val);
    }

    @Override
    public boolean getBeregnet(){
        return erBeregnet.getValue();
    }

    //start
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
    public double getVaerdi() {

        return vaerdi.getValue();
    }

    @Override
    public void beregn() {


        // X = VO / VE
        if (!Double.isNaN(vo.getVaerdi()) && !Double.isNaN(ve.getVaerdi())){
            setVaerdi(vo.getVaerdi() / ve.getVaerdi());
            setBeregnet(true);

            // X = KO / KE
        } else if (!Double.isNaN(ko.getVaerdi()) && !Double.isNaN(ke.getVaerdi())){
            setVaerdi(ko.getVaerdi() / ke.getVaerdi());
            setBeregnet(true);

            // X = STO / SE
        } else if (!Double.isNaN(sto.getVaerdi()) && !Double.isNaN(se.getVaerdi())){
            setVaerdi(sto.getVaerdi() / se.getVaerdi());
            setBeregnet(true);

            // X = STO / GROMK
        } else if (!Double.isNaN(sto.getVaerdi()) && !Double.isNaN(gromk.getVaerdi())){
            setVaerdi(sto.getVaerdi() * gromk.getVaerdi());
            setBeregnet(true);

            // X = (domk * ( vo - voOver)) + xOver
        } else if (!Double.isNaN(domk.getVaerdi()) && !Double.isNaN(vo.getVaerdi()) && !Double.isNaN(voOver.getVaerdi()) && !Double.isNaN(xOver.getVaerdi())) {
            setVaerdi( (domk.getVaerdi() * ( vo.getVaerdi() - voOver.getVaerdi())) + xOver.getVaerdi() );
            setBeregnet(true);
        }

        else if (getBeregnet()){
            setVaerdi(NaN);

    }
}
}