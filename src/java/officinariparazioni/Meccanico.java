
package officinariparazioni;

import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.*;
/**
 * Classe che definisce un modello di  meccanico,che dovrà essere poi inserito 
 * nel nostro Database
 * @author antonio
 */
public class Meccanico implements elementoDB{
    /**
     * Costruttore
     */
    public Meccanico(){
       
    }
    /**
     * Costruttore con parametri
     * @param matricola il codice univoco che identifica il meccanico
     * @param nome nome del meccanico
     * @param tipoMeccanico  il settore di cui si occupa
     */
    public Meccanico(String matricola,String nome,String tipoMeccanico) {
        this.nome = nome;
        this.matricola = matricola;
        this.tipoMeccanico = tipoMeccanico;
    }
    

    public String getNome() {
        return nome;
    }

    public String getMatricola() {
        return matricola;
    }

    public String getTipoMeccanico() {
        return tipoMeccanico;
    }
    
    //Setter
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setMatricola(String matricola) {
        this.matricola = matricola;
    }

    public void setTipoMeccanico(String tipoMeccanico) {
        this.tipoMeccanico = tipoMeccanico;
    }

    @Override
    public String toString() {
        return "Meccanico{" + "nome=" + nome + ", matricola=" + matricola + 
                ", tipoMeccanico=" + tipoMeccanico + '}';
    }
    /**
     * Funzione che ci permette di andare a settare correttamente quelle che sono 
     * le informazioni relative ad un meccanico mostrando a video varie finestre
     * l'utente dovrà opprtunamente riempire
     */
    public void nuovoMeccanico(){
        try{
        this.nome = JOptionPane.showInputDialog("Inserire nome meccanico");
        this.matricola = JOptionPane.showInputDialog("Inserire matricola meccanico");
        if(matricola.equals("")||nome.equals("")){
            Exception e= new Exception ();
            throw e;
        }
        String list[]={"ELETTRICO", "CARROZZERIA", "GOMME", "INTERNI"};
        JComboBox possibleType= new JComboBox(list);
        JOptionPane.showMessageDialog(null,possibleType,"Inserisci tipo meccanico",JOptionPane.QUESTION_MESSAGE);
        this.tipoMeccanico=(String)possibleType.getSelectedItem();
        
        DBOfficina.getIstance().inserisci(this);
        
        }
        
        catch (Exception e){
            JOptionPane.showMessageDialog(null,"ERRORE,inserimento nuovo meccanico non nriuscito");
        }
       
    }
    

    @Override
    public void inserisci(Statement stm) {
        String query = String.format("INSERT INTO MECCANICO(MATRICOLA,NOME,TIPO_MECCANICO)"
                + " VALUES ('%s','%s','%s')", this.matricola, this.nome, this.tipoMeccanico);
        try {
            stm.executeUpdate(query);
        } catch (SQLException e) {
             JOptionPane.showMessageDialog(null, "ERRORE: Inserimento Meccanico non riuscito");
        }

    }

    @Override
    public void aggiorna(Statement stm) {
        String query = String.format("UPDATE MECCANICO SET NOME = '%s',"
                + "TIPO_MECCANICO = '%s' WHERE MATRICOLA= '%s'", this.nome,
                this.tipoMeccanico, this.matricola);
        try {
            stm.executeUpdate(query);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERRORE: Aggiornamento Meccanico non riuscito");
        }
    }

    @Override
    public void cancella(Statement stm) {
       String query = String.format("DELETE FROM MECCANICO WHERE MATRICOLA = '%s'",this.matricola);
        try {
            stm.executeUpdate(query);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"ERRORE: Cancellazione Meccanico non riuscito");
        }
       
    }
   
    private String matricola;
    private String nome;
    private String tipoMeccanico;
    
}
