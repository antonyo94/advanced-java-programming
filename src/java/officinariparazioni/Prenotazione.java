package officinariparazioni;
/**
 * @interface
 * Interfaccia fondamentale per l'implementazione del nostro Command Pattern,che 
 * rende possibile la caratteristica dello stesso di differenziare l'oggetto che 
 * chiama l'azione dall'oggetto che materialmente la compie
 * 
 */

public interface Prenotazione {
    public void execute();
    public void undo();
}
