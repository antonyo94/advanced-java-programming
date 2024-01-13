/**
 * @author: Antonio Abate
 * @version: 1.0 Marzo 2016
 */

package officinariparazioni;
import java.awt.event.*;
import javax.swing.JOptionPane;
/**
 * Classe che contiene il metodo main,rispetto cui lanciare il programma 
 * @author antonio
 */
public class OfficinaRiparazioniTest {


    public static void main(String[] args) {
        
 
        OfficinaRiparazioni of = new OfficinaRiparazioni ();
        Reception.getIstance().deserializzaReception();
        
        of.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                DBOfficina.getIstance().chiudiDatabase();
                of.dispose();
                System.exit(0);
            }
        });


    }
}

