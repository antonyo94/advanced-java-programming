package officinariparazioni;

import java.sql.*;
import javax.swing.*;
/**
 * Classe che ci permette di andare ad eseguire varie operazioni sul 
 * Database da noi definito.In particolare fa uso di parametri di tipo elementoDB
 * ovvero una interfaccia.Implementando dunque  tale interfaccia si rende possibile
 *mediante il polirmorfismo aggiungere nel tempo elementi che possono appartenere 
 * al nostro DB ed eseguire su di essi le operazioni CRUD opprtunamente ridefinite.
 */

public class DBOfficina {
    /**
     * Costruttore della nostra classe
     */
    private DBOfficina() {
        creaDatabase();
    }
        
    
        /**Nuova connessione al Database che assegna un valore alla variabile conn,
         * caricando opportunamente i driver.
         * 
         * @throws SQLException,lanciata quando i driver non sono trovati o il 
         * DB non è disponibile.Essa è catturata in parte differente del codice
         * @see creaDatabase
         */
    
        private  void nuovaConnessione() throws SQLException{
 
            if( (conn = DriverManager.getConnection(url,username,password)) == null)
                throw new SQLException(); 
    }


        
        /**
         * Funzione che invoca la chiamata ad una nuovaConnessione() ed assegna 
         * un valore ad stm.In tale funzione viene anche opportunamente catturata
         * l'SQLExcpetion del metodo precedente
         */
        private  void creaDatabase(){
        try{
            nuovaConnessione();
            stm = conn.createStatement();
            
        }
        catch (SQLException e){
            JOptionPane.showMessageDialog(null,"ERRORE: Creazione Database non riuscita");
        }
                catch(Exception e){
            JOptionPane.showMessageDialog(null,"ERRORE: Accesso al Database non riuscito");
        }
    }

        public static DBOfficina getIstance(){
            if(conn==null || stm==null){
                istance = new DBOfficina();
            }
            return istance;
        }
     /**
     * Accessor Method per la variabile stm
     * @return stm,ovverro l'oggetto che ci permette di eseguire interrogazoni sul DB 
     */
    public  Statement getStm() {
       
            return stm;
            
    }

    public  void chiudiDatabase() {
        try {

            if (stm != null) {
                stm.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"ERRORE: Chiusura Database non riuscita");
        }
    }
    public boolean controlPassword(String pswd){
        if(pswd.equals(password)){
            return true;
        }
            
        return false;
    }
    /**
     * Inserimento di un elemento nel Database
     * @param elem oggetto di tipo elementoDB,ovvero una interfaccia che dovrà 
     *  essere implementata al fine di riscrivere opportunamente il metodo di 
     *  inserimento
     */
    public  void inserisci(elementoDB elem){
        elem.inserisci(stm);
    }
    /**
     * Aggiornamento di un elemento nel nostro DB
     * @param elem 
     */
    public  void aggiorna(elementoDB elem){
        elem.aggiorna(stm);
    }
    /**
     * Cancellazione di un elemento dal Database
     * @param elem 
     */
    public  void cancella(elementoDB elem){
        elem.cancella(stm);
    }
    
    
    
    private static final String url="jdbc:derby://localhost:1527/officinaRiparazioniDB;create=true";
    private static final String username="officinaAdmin";
    private static final String password="password";
    private static  Connection conn=null;
    private static  Statement stm=null;
    private  static DBOfficina istance = new DBOfficina();
}
