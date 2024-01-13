package officinariparazioni;

import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
/**
* Classe che modella una operazione eseguita da parte di uno dei meccanici del DB.
*Essa implementa l'interfaccia elementoDB al fine di far in modo che essa sia perfettamente
* integrabile con il nse stesso.
*/
public class OperazioneEseguita implements elementoDB{

    
    public OperazioneEseguita() {
    }
    /**
     * Costruttore con parametri
     * @param codicePrenotazione codice di prenotazione cui è associata l'operaione
     * @param tipoOperazione tipo di operazione compiuta
     * @param costo costo dell'operazione stessa
     * @param componentiAggiunti componenti eventualmente aggiunti
     * @param tipoProblema tipo di problema riscontrato rispetto al veicolo
     * @param matricolaMeccanico id del meccanico che ha eseguito l'operaizone
     * @param matricolaVeicolo matricola del veicolo soggetto all'operaione
     * @param statoVeicolo settaggio dello stato del veicolo 
     * @param esito  esito dell'operazione 
     */
    public OperazioneEseguita(String codicePrenotazione, String tipoOperazione, Double costo, String componentiAggiunti, String tipoProblema, String matricolaMeccanico, String matricolaVeicolo, String statoVeicolo, String esito) {
        this.codicePrenotazione = codicePrenotazione;
        this.tipoOperazione = tipoOperazione;
        this.costo = costo;
        this.componentiAggiunti = componentiAggiunti;
        this.tipoProblema = tipoProblema;
        this.matricolaMeccanico = matricolaMeccanico;
        this.matricolaVeicolo = matricolaVeicolo;
        this.statoVeicolo = statoVeicolo;
        this.esito = esito;
    }

    public String getCodicePrenotazione() {
        return codicePrenotazione;
    }

    public String getTipoOperazione() {
        return tipoOperazione;
    }

    public Double getCosto() {
        return costo;
    }

    public String getComponentiAggiunti() {
        return componentiAggiunti;
    }

    public String getTipoProblema() {
        return tipoProblema;
    }

    public String getMatricolaMeccanico() {
        return matricolaMeccanico;
    }

    public String getMatricolaVeicolo() {
        return matricolaVeicolo;
    }

    public String getStatoVeicolo() {
        return statoVeicolo;
    }

    public String getEsito() {
        return esito;
    }

    public void setCodicePrenotazione(String codicePrenotazione) {
        this.codicePrenotazione = codicePrenotazione;
    }

    public void setTipoOperazione(String tipoOperazione) {
        this.tipoOperazione = tipoOperazione;
    }

    public void setCosto(Double costo) {
        this.costo = costo;
    }

    public void setComponentiAggiunti(String componentiAggiunti) {
        this.componentiAggiunti = componentiAggiunti;
    }

    public void setTipoProblema(String tipoProblema) {
        this.tipoProblema = tipoProblema;
    }

    public void setMatricolaMeccanico(String matricolaMeccanico) {
        this.matricolaMeccanico = matricolaMeccanico;
    }

    public void setMatricolaVeicolo(String matricolaVeicolo) {
        this.matricolaVeicolo = matricolaVeicolo;
    }

    public void setStatoVeicolo(String statoVeicolo) {
        this.statoVeicolo = statoVeicolo;
    }

    public void setEsito(String esito) {
        this.esito = esito;
    }
 

    @Override
    public void inserisci(Statement stm) {
        
        String query = String.format("INSERT INTO OPERAZIONE_ESEGUITA(CODICE_RICHIESTA,TIPO_OPERAZIONE,"
                + "COSTO,COMPONENTI,TIPO_PROBLEMA,MATRICOLA_MECCANICO,MATRICOLA_VEICOLO,"
                + "STATO_VEICOLO,ESITO) VALUES ('%s','%s',%s,'%s','%s','%s','%s','%s','%s')",
                this.codicePrenotazione,this.tipoOperazione,this.costo,this.componentiAggiunti,
                this.tipoProblema,this.matricolaMeccanico,this.matricolaVeicolo,this.statoVeicolo,this.esito);
        
        try {
            stm.executeUpdate(query);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERRORE: Inserimento Operazione Eseguita non riuscito");
        }
    }

    @Override
    public void aggiorna(Statement stm) {
        
        String query = String.format("UPDATE OPERAZIONE_ESEGUITA SET "
                + "TIPO_OPERAZIONE= '%s',"
                + "COSTO = %s , COMPONENTI = '%s' , TIPO_PROBLEMA = '%s' , "
                + "MATRICOLA_MECCANICO = '%s' , MATRICOLA_VEICOLO = '%s' , "
                + "STATO_VEICOLO = '%s' , ESITO = '%s' WHERE CODICE_RICHIESTA= '%s'", 
                this.tipoOperazione,this.costo,this.componentiAggiunti,
                this.tipoProblema,this.matricolaMeccanico,this.matricolaVeicolo,this.statoVeicolo,
                this.codicePrenotazione,this.esito);
        try {
            stm.executeUpdate(query);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERRORE: Aggiornamento Operazione Eseguita non riuscito");
        }
    }

    @Override
    public void cancella(Statement stm) {
        String query = String.format("DELETE FROM OPERAZIONE_ESEGUITA WHERE CODICE_RICHIESTA = '%s'",this.codicePrenotazione);
        try {
            stm.executeUpdate(query);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERRORE: Cancellazione Operazione Eseguita non riuscito");
        }
    }

    private String codicePrenotazione;
    private String tipoOperazione;
    private Double costo;
    private String componentiAggiunti;
    private String tipoProblema;
    private String matricolaMeccanico;
    private String matricolaVeicolo;
    private String statoVeicolo;
    private String esito;
   
}
