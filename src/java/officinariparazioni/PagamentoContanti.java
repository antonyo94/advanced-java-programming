package officinariparazioni;

import java.sql.*;
import javax.swing.*;
/**
 * Classe che estende il concetto di pagamento generico associandolo 
 * ad un pagamento in contanti
 */
public class PagamentoContanti extends Pagamento{
    /**
     * Costruttore con parametri
     * @param quantita quantita di soldi erogata
     * @param codicePrenotazione  codice della prenotazione che si intende saldare
     */
    public PagamentoContanti(Double quantita,String codicePrenotazione) {
        this.quantita=quantita;
        this.codicePrenotazione=codicePrenotazione;
        this.tipoPagamento="CONTANTI";
    }

    public Double getQuantita() {
        return quantita;
    }

    public String getCodicePrenotazione() {
        return codicePrenotazione;
    }

    public String getTipoPagamento() {
        return tipoPagamento;
    }

    public void setQuantita(Double quantita) {
        this.quantita = quantita;
    }

    public void setCodicePrenotazione(String codicePrenotazione) {
        this.codicePrenotazione = codicePrenotazione;
    }

    public void setTipoPagamento(String tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }

    
    
    @Override
    public void paga() {
         try{

            String queryAggiornamento = String.format("UPDATE OPERAZIONE_ESEGUITA SET STATO_VEICOLO = '%s'"
                        + "WHERE CODICE_RICHIESTA= '%s'","RITIRATO",codicePrenotazione);
            DBOfficina.getIstance().getStm().executeUpdate(queryAggiornamento);
            DBOfficina.getIstance().inserisci(this); 
            JOptionPane.showMessageDialog(null,String.format("Pagamento di %s Euro in contanti riuscito!",this.quantita ));            
            }

       catch(SQLException e){
        e.printStackTrace();
         JOptionPane.showMessageDialog(null,"ERRORE: Pagamento non riuscito!");
               }
    }
     @Override
    public void inserisci(Statement stm) {
              String query = String.format("INSERT INTO PAGAMENTO (QUANTITA,TIPO_PAGAMENTO,"
                      + "CODICE_RICHIESTA,NUM_BANCOMAT,NUM_CARTA) VALUES (%s,'%s','%s','%s','%s')",
                      this.quantita,this.tipoPagamento,this.codicePrenotazione,"ND","ND");
        try {
            stm.executeUpdate(query);
        } catch (SQLException e) {
             e.printStackTrace();
            // JOptionPane.showMessageDialog(null, "ERRORE: Inserimento Pagamento non riuscito");
        }
    }

    @Override
    public void aggiorna(Statement stm) {
                      String query = String.format("UPDATE PAGAMENTO SET QUANTITA=%s,TIPO_PAGAMENTO='%s',"
                      + "NUM_BANCOMAT='%s',NUM_CARTA='%s' WHERE CODICE_RICHIESTA='%s'",
                      this.quantita,this.tipoPagamento,"ND","ND",this.codicePrenotazione,this.codicePrenotazione);
        try {
            stm.executeUpdate(query);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERRORE: Inserimento Pagamento non riuscito");
        }
    }

    @Override
    public void cancella(Statement stm) {
                      String query = String.format("DELETE FROM PAGAMENTO WHERE CODICE_RICHIESTA='%s'",this.codicePrenotazione);
        try {
            stm.executeUpdate(query);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERRORE: Inserimento Cancellazione non riuscito");
        }
    }
    
    
}
