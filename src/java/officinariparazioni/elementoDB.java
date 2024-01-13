
package officinariparazioni;

import java.sql.*;
/**
 * @interface
 * Interfaccia che astrae il concetto di elemento del nostro database,utile
 * per permette di creare nuove classi,che implementano tale interfaccia,di eseguire
 * le operazioni fondamentali da essa definite,a condizione che tali classi siano 
 * compatibili con il nostro database
 */

public interface elementoDB {
    
    public void inserisci(Statement stm);
    public void aggiorna(Statement stm);
    public void cancella(Statement stm);
}
