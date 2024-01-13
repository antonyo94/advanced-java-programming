package officinariparazioni;
/**
 * Classe che astra il concetto ancor troppo generale di pagamento,associato ad 
 * una singola prenotazione di cui se ne riporta il codice univoco
 */
public abstract class Pagamento implements elementoDB{

    public abstract void paga();
    
    Double quantita;
    String codicePrenotazione;
    String tipoPagamento;
}
