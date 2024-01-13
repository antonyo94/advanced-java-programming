package officinariparazioni;
import java.io.*;
/**
 * Una delle classi che implementano l'astrazione prenotazione,secondo l'architettura
 * del Command.In particolare tali classi appresentano quelle che sono le tipologie di
 * richieste che un determinato client può richiedere.Esse inoltre immagazzinano al loro
 * interno quello che è il destinatario della richiesta stessa come variabile d'istanza,
 * al fine di renderlo esecutore materiale della specifica richiesta attraverso il 
 * concetto di destinatario/azione.
 */
public class RichiestaRiparazione implements Prenotazione,Serializable{

    public RichiestaRiparazione(Richiesta nuovaRichiesta) {
        this.nuovaRichiesta = nuovaRichiesta;
        this.nuovaRichiesta.setTipoRichiesta("RIPARAZIONE");
    }
    
        /**
     * Implementazione del metodo execute della classe Prenotazione,invocandi 
     * il corrispettivo metodo dell'oggeto destinatario della richiesta
     */
    @Override
    public void execute() {
        nuovaRichiesta.riparaVeicolo();
    }
    /**
     * Supporto dell'operazione di annullamento,anche in questo caso implementando 
     * l'interfaccia Prenotazione ed invocando il rispettivo mettodo dell'oggetto
     * destinatario della richiesta
     */
    @Override
    public void undo() {
        nuovaRichiesta.annullaRichiesta();
    }

    @Override
    public String toString() {
        return "RichiestaRiparazione{"+ nuovaRichiesta.toString() + '}';
    }
    
    
    
    private Richiesta nuovaRichiesta;
}
