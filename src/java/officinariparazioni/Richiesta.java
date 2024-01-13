package officinariparazioni;

import java.sql.*;
import javax.swing.*;
import java.util.Random;
import java.io.*;
import java.util.*;
/**
 * Classe destinataria delle richieste relative al nostro command pattern e che
 * eseguirà materialmente le operazioni richieste.Viene messo a diposizione dell'utente
 * tre tipologie differenti di operazioni,oltre a consentire un annullamento 
 *delle stesse richieste
 * 
 */
public class Richiesta implements elementoDB,Serializable{

    public Richiesta(Veicolo veicolo) {
        this.veicolo = veicolo;      
        this.statoVeicolo = "IN_ENTRATA";
        this.codicePrenotazione = generaCodice(10);
        this.tipoRichiesta="ND";
    }

    public Veicolo getVeicolo() {
        return veicolo;
    }

    public String getStatoVeicolo() {
        return statoVeicolo;
    }

    public String getCodicePrenotazione() {
        return codicePrenotazione;
    }

    public String getTipoRichiesta() {
        return tipoRichiesta;
    }
    

    public void setVeicolo(Veicolo veicolo) {
        this.veicolo = veicolo;
    }

    public void setStatoVeicolo(String statoVeicolo) {
        this.statoVeicolo = statoVeicolo;
    }

    public void setCodicePrenotazione(String codicePrenotazione) {
        this.codicePrenotazione = codicePrenotazione;
    }

    public void setTipoRichiesta(String tipoRichiesta) {
        this.tipoRichiesta = tipoRichiesta;
    }

    @Override
    public String toString() {
        return "Richiesta{" + "veicolo=" + veicolo + ", statoVeicolo=" + statoVeicolo +
                ", codicePrenotazione=" + codicePrenotazione + ", tipoRichiesta=" + tipoRichiesta + '}';
    }


   /**
    * Operazione di riparazione di un singolo veicolo 
    */
    
    public void riparaVeicolo(){
               try {
            String codiceRichiesta = this.codicePrenotazione;
            String tipoOperazione = this.tipoRichiesta;
            ArrayList listMatricola=new ArrayList();
            String query = "SELECT * FROM MECCANICO";
            ResultSet result = DBOfficina.getIstance().getStm().executeQuery(query);
            while(result.next()){
                String matricola=result.getString(1);
                String nome=result.getString(2);
                listMatricola.add(matricola + "-" + nome);
            }
            JComboBox lista = new JComboBox(listMatricola.toArray());
            
            JOptionPane.showMessageDialog(null,lista,"Selezionare meccanico",JOptionPane.QUESTION_MESSAGE);
            String matricola = (String)lista.getSelectedItem();
            String matricolaMeccanico[] = matricola.split("-");
            String tipoProblema="ND";
            if (matricolaMeccanico.equals(null)) {
                IOException e = new IOException();
                throw e;
            }
            String query2 = String.format("SELECT * FROM MECCANICO WHERE MATRICOLA = '%s'", matricolaMeccanico[0]);
            ResultSet rs = DBOfficina.getIstance().getStm().executeQuery(query2);
            if (rs.next()) {
                tipoProblema = rs.getString(3);
            }
            else{
                
                throw new SQLException ();
            }
        Double costo=Double.parseDouble(JOptionPane.showInputDialog("Inserisci il costo dell'operazione"));
        String componenti="";
        String matricolaVeicolo =this.veicolo.getMatricola();
        String statoVeicolo = "IN_USCITA";
        this.veicolo.setStatoVeicolo(statoVeicolo);
        DBOfficina.getIstance().aggiorna(this.veicolo);
        String[] possibleResult = {"POSITIVO","NEGATIVO"};
        JComboBox comboBoxEsito = new JComboBox(possibleResult);
        JOptionPane.showMessageDialog( null, comboBoxEsito, "seleziona esito operazione", JOptionPane.QUESTION_MESSAGE);
        String esito=(String)comboBoxEsito.getSelectedItem();
        
        OperazioneEseguita op1 = new OperazioneEseguita(codiceRichiesta,tipoOperazione,
            costo,componenti,tipoProblema,matricolaMeccanico[0],matricolaVeicolo,
            statoVeicolo,esito);
        String oggettoEmail="Riparazione Eseguita";
        String testoEmail = "Gentile cliente, 'OfficinaRiparazioni srl' la informa\n"
        + "che le operazioni da lei richieste per il suo veicolo\n"
        + "sono terminate.\n"+
        "Tipo Operazione: " + tipoOperazione +
        "\nTipo Problema: " + tipoProblema +        
        "\nCosto: " + costo +
        "\nMatricola Meccanico: " + matricolaMeccanico[0] +
        "\nMatricola Veicolo: " + matricolaVeicolo +
        "\nEsito: " + esito +
        "\nVenga a ritirarlo al più presto,Saluti.";
        String destinatarioEmail = veicolo.getProprietario().getEmail();
        
        Email.sendEmail(destinatarioEmail,oggettoEmail,testoEmail);
        DBOfficina.getIstance().inserisci(op1);
        JOptionPane.showMessageDialog(null,"Operazione correttamente inserita nel sistema");
        DBOfficina.getIstance().cancella(this);
        }
        catch(IOException e){
            JOptionPane.showMessageDialog(null,"Attenzione,Matricola meccanico non valida");
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Attenzione,Meccanico non presente nel DB");
        }
        catch(Exception e){
        JOptionPane.showMessageDialog(null,"Attenzione,riempire tutti i campi con valori validi");
       }
       
    }
    /**
     * Operazione di rettifica del veicolo
     */
    public void rettificaVeicolo(){
                       try {
            String codiceRichiesta = this.codicePrenotazione;
            String tipoOperazione = this.tipoRichiesta;
            ArrayList listMatricola=new ArrayList();
            String query = "SELECT * FROM MECCANICO";
            ResultSet result = DBOfficina.getIstance().getStm().executeQuery(query);
            while(result.next()){
                String matricola=result.getString(1);
                String nome=result.getString(2);
                listMatricola.add(matricola + "-" + nome);
            }
            JComboBox lista = new JComboBox(listMatricola.toArray());
            
            JOptionPane.showMessageDialog(null,lista,"Selezionare meccanico",JOptionPane.QUESTION_MESSAGE);
            String matricola = (String)lista.getSelectedItem();
            String matricolaMeccanico[] = matricola.split("-");
            String tipoProblema="ND";
            if (matricolaMeccanico.equals(null)) {
                IOException e = new IOException();
                throw e;
            }
            String query2 = String.format("SELECT * FROM MECCANICO WHERE MATRICOLA = '%s'", matricolaMeccanico[0]);
            ResultSet rs = DBOfficina.getIstance().getStm().executeQuery(query2);
            if (rs.next()) {
                tipoProblema = rs.getString(3);
            }
            else{
                
                throw new SQLException ();
            }
        Double costo=Double.parseDouble(JOptionPane.showInputDialog("Inserisci il costo dell'operazione"));
        String componenti="";
        String matricolaVeicolo =this.veicolo.getMatricola();
        String statoVeicolo = "IN_USCITA";
        this.veicolo.setStatoVeicolo(statoVeicolo);
        DBOfficina.getIstance().aggiorna(this.veicolo);
        String[] possibleResult = {"POSITIVO","NEGATIVO"};
        JComboBox comboBoxEsito = new JComboBox(possibleResult);
        JOptionPane.showMessageDialog( null, comboBoxEsito, "seleziona esito operazione", JOptionPane.QUESTION_MESSAGE);
        String esito=(String)comboBoxEsito.getSelectedItem();
        
        OperazioneEseguita op1 = new OperazioneEseguita(codiceRichiesta,tipoOperazione,
            costo,componenti,tipoProblema,matricolaMeccanico[0],matricolaVeicolo,
            statoVeicolo,esito);
        String oggettoEmail="Riparazione Eseguita";
        String testoEmail = "Gentile cliente, 'OfficinaRiparazioni srl' la informa\n"
        + "che le operazioni da lei richieste per il suo veicolo\n"
        + "sono terminate.\n"+
        "Tipo Operazione: " + tipoOperazione +      
        "\nCosto: " + costo +
        "\nMatricola Meccanico: " + matricolaMeccanico[0] +
        "\nMatricola Veicolo: " + matricolaVeicolo +
        "\nEsito: " + esito +
        "\nVenga a ritirarlo al più presto,Saluti.";
        String destinatarioEmail = veicolo.getProprietario().getEmail();
        
        Email.sendEmail(destinatarioEmail,oggettoEmail,testoEmail);
        DBOfficina.getIstance().inserisci(op1);
        JOptionPane.showMessageDialog(null,"Operazione correttamente inserita nel sistema");
        DBOfficina.getIstance().cancella(this);
        }
        catch(IOException e){
            JOptionPane.showMessageDialog(null,"Attenzione,Matricola meccanico non valida");
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Attenzione,Meccanico non presente nel DB");
        }
        catch(Exception e){
        JOptionPane.showMessageDialog(null,"Attenzione,riempire tutti i campi con valori validi");
       }
       
    }
    /**
     * Aggiunta componenti ad un singolo veicolo
     */
    public void aggComponentiVeicolo(){
        try {
            String codiceRichiesta = this.codicePrenotazione;
            String tipoOperazione = this.tipoRichiesta;
            ArrayList listMatricola=new ArrayList();
            String query = "SELECT * FROM MECCANICO";
            ResultSet result = DBOfficina.getIstance().getStm().executeQuery(query);
            while(result.next()){
                String matricola=result.getString(1);
                String nome=result.getString(2);
                listMatricola.add(matricola + "-" + nome);
            }
            JComboBox lista = new JComboBox(listMatricola.toArray());
            
            JOptionPane.showMessageDialog(null,lista,"Selezionare meccanico",JOptionPane.QUESTION_MESSAGE);
            String matricola = (String)lista.getSelectedItem();
            String matricolaMeccanico[] = matricola.split("-");
            String tipoProblema="ND";
            if (matricolaMeccanico.equals(null)) {
                IOException e = new IOException();
                throw e;
            }
            String query2 = String.format("SELECT * FROM MECCANICO WHERE MATRICOLA = '%s'", matricolaMeccanico[0]);
            ResultSet rs = DBOfficina.getIstance().getStm().executeQuery(query2);
            if (rs.next()) {
                tipoProblema = rs.getString(3);
            }
            else{
                
                throw new SQLException ();
            }
        Double costo=Double.parseDouble(JOptionPane.showInputDialog("Inserisci il costo dell'operazione"));
        String componenti=JOptionPane.showInputDialog("Inserisci componenti aggiunti");
        String matricolaVeicolo =this.veicolo.getMatricola();
        String statoVeicolo = "IN_USCITA";
        this.veicolo.setStatoVeicolo(statoVeicolo);
        DBOfficina.getIstance().aggiorna(this.veicolo);
        String[] possibleResult = {"POSITIVO","NEGATIVO"};
        JComboBox comboBoxEsito = new JComboBox(possibleResult);
        JOptionPane.showMessageDialog( null, comboBoxEsito, "seleziona esito operazione", JOptionPane.QUESTION_MESSAGE);
        String esito=(String)comboBoxEsito.getSelectedItem();
        
        OperazioneEseguita op1 = new OperazioneEseguita(codiceRichiesta,tipoOperazione,
            costo,componenti,tipoProblema,matricolaMeccanico[0],matricolaVeicolo,
            statoVeicolo,esito);
        String oggettoEmail="Aggiunta Componenti Eseguita";
        String testoEmail = "Gentile cliente, 'OfficinaRiparazioni srl' la informa\n"
        + "che le operazioni da lei richieste per il suo veicolo\n"
        + "sono terminate.\n"+
        "Tipo Operazione: " + tipoOperazione +
        "\nTipo Problema: " + tipoProblema +        
        "\nCosto: " + costo +
        "\nComponenti Aggiunti: " + componenti +
        "\nMatricola Meccanico: " + matricolaMeccanico[0] +
        "\nMatricola Veicolo: " + matricolaVeicolo +
        "\nEsito: " + esito +
        "\nVenga a ritirarlo al più presto,Saluti.";
        String destinatarioEmail = veicolo.getProprietario().getEmail();
        
        Email.sendEmail(destinatarioEmail,oggettoEmail,testoEmail);
        DBOfficina.getIstance().inserisci(op1);
        JOptionPane.showMessageDialog(null,"Operazione correttamente inserita nel sistema");
        DBOfficina.getIstance().cancella(this);
        }
        catch(IOException e){
            JOptionPane.showMessageDialog(null,"Attenzione,Matricola meccanico non valida");
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Attenzione,Meccanico non presente nel DB");
        }
        catch(Exception e){
        JOptionPane.showMessageDialog(null,"Attenzione,riempire tutti i campi con valori validi");
       }
  
    }
    /**
     * Annullamento di una determinata richiesta
     */
    public void annullaRichiesta(){
       
        String destinatarioEmail = this.getVeicolo().getProprietario().getEmail();
        String oggettoEmail = "Operazione Annullata";

        String testoEmail = "Gentile cliente, 'OfficinaRiparazioni srl' la informa\n"
                + "che le operazioni da lei richieste per il suo veicolo\n"
                + "sono state Annullate.\n"
                + "\nVenga a ritirarlo al più presto,Saluti.";
        Email.sendEmail(destinatarioEmail, oggettoEmail, testoEmail);
        DBOfficina.getIstance().cancella(this);
    }

    @Override
    public void inserisci(Statement stm) {
       String query = String.format("INSERT INTO RICHIESTA (CODICE_RICHIESTA,"
               + "MATRICOLA_VEICOLO , STATO_VEICOLO,TIPO_RICHIESTA) VALUES ('%s','%s','%s','%s')",this.codicePrenotazione,
               this.veicolo.getMatricola(),this.statoVeicolo,this.tipoRichiesta);
       try {
            stm.executeUpdate(query);
        } catch (SQLException e) {
          JOptionPane.showMessageDialog(null, "ERRORE: Inserimento Richiesta non riuscito");
        }
    }

    @Override
    public void aggiorna(Statement stm) {
      String query= String.format("UPDATE RICHIESTA SET MATRICOLA_VEICOLO = '%s',"
              + "STATO_VEICOLO = '%s',TIPO_RICHIESTA = '%s' WHERE CODICE_RICHIESTA = '%s'",this.veicolo.getMatricola(),
              this.statoVeicolo,this.codicePrenotazione,this.tipoRichiesta);
        try {
            stm.executeUpdate(query);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERRORE: Aggiornamento Richiesta non riuscito");
        }
    }

    @Override
    public void cancella(Statement stm) {
        String query = String.format("DELETE FROM RICHIESTA WHERE CODICE_RICHIESTA = '%s'",this.codicePrenotazione);
     try {
            stm.executeUpdate(query);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERRORE: Cancellazione Richiesta non riuscito");
        }
    }
    
    public static String generaCodice(int numeroCaratteriRandom){
         String[] Caratteri = { "a", "b", "c", "d", "e", "f", "g", "h", "i",
			"l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "z", "y",
			"j", "k", "x", "w", "A", "B", "C", "D", "E", "F", "G", "H", "I",
			"L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "Z", "Y",
			"J", "K", "X", "W"};
 
	// array dei numeri
	 String[] Numeri = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "0" };
         Random rand = new Random();
         int lunghezzaCaratteri = Caratteri.length;
	 int lunghezzaNumeri = Numeri.length;
         // istanzio la variabile che conterrà il prodotto finale
	 String stringaRandom = "";
         
         while (stringaRandom.length() < numeroCaratteriRandom) {
 
			// ottengo un elemento casuale per ogni array
			int c = rand.nextInt(lunghezzaCaratteri);
			int n = rand.nextInt(lunghezzaNumeri);
			
 
			// aggiungo una lettera casuale
			stringaRandom += Caratteri[c];
			// aggiungo un numero random
			stringaRandom += Numeri[n];
			// se l'opzione conSpeciali è true aggiungo un carattere speciale
         }
         if (stringaRandom.length() > numeroCaratteriRandom) {
			stringaRandom = stringaRandom.substring(0, numeroCaratteriRandom);
		}
         return stringaRandom;
    }
    
        public static void serializzaRichiesta(Richiesta r){
		ObjectOutputStream output=null;
		try{
			output=new ObjectOutputStream(new FileOutputStream("dati.dat"));
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch(IOException ioe){
			ioe.printStackTrace();
		}
		try {
			output.writeObject(r);	
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			output.close();
		} 
		catch (IOException e2) {
                        e2.printStackTrace();
		}

	}
   
   public static Richiesta deserializzaRichiesta(){
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream("dati.dat"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Richiesta r = null;
        try {
            r = (Richiesta) ois.readObject();
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }

        return r;
}
    
    
    
    
    private Veicolo veicolo;
    private String statoVeicolo;
    private String codicePrenotazione;
    private String tipoRichiesta;
    private static final long serialVersionUID = 1L;
    
}
