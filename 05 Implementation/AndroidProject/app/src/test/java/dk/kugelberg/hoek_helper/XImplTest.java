package dk.kugelberg.hoek_helper;

import org.junit.Test;

import dk.kugelberg.hoek_helper.model.DOMK;
import dk.kugelberg.hoek_helper.model.KO;
import dk.kugelberg.hoek_helper.model.STO;
import dk.kugelberg.hoek_helper.model.VE;
import dk.kugelberg.hoek_helper.model.VO;
import dk.kugelberg.hoek_helper.model.X;
import dk.kugelberg.hoek_helper.model.XImpl;

import static org.junit.Assert.*;

public class XImplTest {
    //Kontrollere om setVaerdi fungere og ændre tallet til 45.
    @Test
    public void testSaetXTil45() {
        XImpl x = new XImpl();
        x.setVaerdi(45);
        assertEquals(x.getVaerdi(),45);
    }
    //Kontrollere at Init ikke ændre værdien.
    @Test
    public void TestInitAfX() {
        XImpl x = new XImpl();
        assertEquals(x.getVaerdi(), Double.NaN);
    }

    /*!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    !!!!!!!!!!!!!!!!!!!!!!!!!!!!! MANGLER TEST AF INIT !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*/
    //Hvis nedenstående fungere vil resultatet være 2. da 10/5 = 2
    @Test
    public void testBeregnXmedVEogVO() {
        XImpl x = new XImpl();
        x.init(new VOMock(10),new VEMock(5),new DomkMock(Double.NaN));
        double resultat = 2;
        x.beregn();
        assertEquals(x.getVaerdi(),resultat);
    }
    //Hvis nedenstående fungere vil resultatet være 2. da 10/5 = 2
    @Test
    public void testBeregnXmedDOMKogVO() {
        XImpl x = new XImpl();
        x.init(new VOMock(10),new VEMock(Double.NaN),new DomkMock(5));
        double resultat = 2;
        x.beregn();
        assertEquals(x.getVaerdi(),resultat);
    }
//Nedenstående metode tjekker om den udregner med en formel der ikke findes.
    @Test
    public void testBeregnXmedDOMKogVE() {
        XImpl x = new XImpl();
        x.init(new VOMock(Double.NaN),new VEMock(10),new DomkMock(5));
        double resultat = Double.NaN;
        x.beregn();
        assertEquals(x.getVaerdi(),resultat);
    }
    @Test
    public void testBeregnXMedDOMKogNegativVO() {
        XImpl x = new XImpl();
        x.init(new VOMock(-10),new VEMock(Double.NaN),new DomkMock(5));
        double resultat = Double.NaN;
        x.beregn();
        assertEquals(x.getVaerdi(),resultat);
    }

    @Test
    public void testBeregnXMedNegativDOMKogVO() {
        XImpl x = new XImpl();
        x.init(new VOMock(10),new VEMock(Double.NaN),new DomkMock(-5));
        double resultat = Double.NaN;
        x.beregn();
        assertEquals(x.getVaerdi(),resultat);
    }


    //Opretter Mock klasser for at undgå at det er fejlen i de benyttede klasser der gør at testen ikke lykkes.
    //Mock klasser skal være så simple at man fjerner/mindsker risikoen for at fejlen er i den klasse.
class VOMock implements VO{
        double vaerdi;

        public VOMock(double vaerdi){
            this.vaerdi = vaerdi;
        }

    @Override
    public void setVaerdi(double vaerdi) {
    }

    @Override
    public double getVaerdi() {
        return vaerdi;
    }
}
class VEMock implements VE{
        double vaerdi;

        public VEMock(double vaerdi) {
            this.vaerdi = vaerdi;
        }
    @Override  public void setVaerdi(double Vaerdi) {}

    @Override public double getVaerdi() {
        return vaerdi;
    }
}
class DomkMock implements DOMK{
        double vaerdi;

    public DomkMock(double vaerdi) {
        this.vaerdi = vaerdi;
    }

    @Override
    public void setVaerdi(double vaerdi) {

    }

    @Override
    public double getVaerdi() {
        return vaerdi;
    }

    @Override
    public void beregn() {

    }

    @Override
    public void init(VO vo1, VO vo2, STO sto, KO ko, VE ve, X x1, X x2, DOMK domk1, DOMK domk2) {

    }

    @Override
    public boolean erBeregnet() {
        return false;
    }
}
}