
package officinariparazioni;

import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
/**
 * Classe che estende il concetto di pagamento generico associandolo 
 * ad un pagamento tramite Carta di Credito
 */
public class PagamentoCCredito extends Pagamento{
     /**
     * Costruttore con parametri
     * @param quantita quantita di soldi erogata
     * @param codicePrenotazione  codice della prenotazione che si intende saldare
     */
    public PagamentoCCredito(Double quantita,String codicePrenotazione) {
        this.quantita = quantita*interessi;
        this.codicePrenotazione=codicePrenotazione;
        this.numeroCarta="";
        this.tipoPagamento="CARTA_CREDITO";
    }

    @Override
    public void paga() {
        
    try{    
            this.numeroCarta=JOptionPane.showInputDialog("Inserisci il numero di carta di credito");
            if(!this.numeroCarta.equals("")){
            String queryAggiornamento = String.format("UPDATE OPERAZIONE_ESEGUITA SET STATO_VEICOLO = '%s'"
                    + "WHERE CODICE_RICHIESTA= '%s'","RITIRATO",codicePrenotazione);
            DBOfficina.getIstance().getStm().executeUpdate(queryAggiornamento);
            DBOfficina.getIstance().inserisci(this); 
            JOptionPane.showMessageDialog(null,String.format("Pagamento di %s Euro con Carta Credito riuscito!",this.quantita ));            
            }else{
                JOptionPane.showMessageDialog(null,"ERRORE: Pagamento non riuscito,numero carta non valido");
            }
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
                      this.quantita,this.tipoPagamento,this.codicePrenotazione,"ND",this.numeroCarta);
        try {
            stm.executeUpdate(query);
        } catch (SQLException e) {
             e.printStackTrace();
            // JOptionPane.showMessageDialog(null, "ERRORE: Inserimento Pagamento con Carta di Credito non riuscito");
        }
    }

    @Override
    public void aggiorna(Statement stm) {
                        String query = String.format("UPDATE PAGAMENTO SET QUANTITA=%s,TIPO_PAGAMENTO='%s',"
                      + "NUM_BANCOMAT='%s',NUM_CARTA='%s' WHERE CODICE_RICHIESTA='%s'",
                      this.quantita,this.tipoPagamento,"ND",this.numeroCarta,this.codicePrenotazione);
        try {
            stm.executeUpdate(query);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERRORE: Aggirnamento Pagamento con Carta di Credito non riuscito");
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
    
    private static final Double interessi=1.05;  
    private String numeroCarta;
    
}
