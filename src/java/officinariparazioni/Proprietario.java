package officinariparazioni;

import java.sql.*;
import javax.swing.*;
import java.io.*;
/**
 * Classe che modella il concetto di Proprietario del veicolo 
 *
 */
public class Proprietario implements elementoDB,Serializable{

    public Proprietario(String CF) {
        this.CF = CF;
    }
    
    /**
     * Costruttore con parametri
     * @param nome nome del proprietario 
     * @param CF identificativo univoco
     * @param email  email del proprietario tramite cui è possibile contattarlo
     */
    public Proprietario(String nome, String CF, String email) {
        this.nome = nome;
        this.CF = CF;
        this.email = email;
    }

    //Getter
    public String getNome() {
        return nome;
    }

    public String getCF() {
        return CF;
    }

    public String getEmail() {
        return email;
    }
    
    //Setter
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCF(String CF) {
        this.CF = CF;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    //ToString
    @Override
    public String toString() {
        return "Proprietario{" + "nome=" + nome + ", CF=" + CF + ", email=" + 
                email + '}';
    }

    @Override
    public void inserisci(Statement stm) {
        String query = String.format("INSERT INTO PROPRIETARIO (NOME,CF,EMAIL) VALUES ('%s' , '%s' , '%s')", this.nome, this.CF, this.email);
        try {
            stm.executeUpdate(query);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Attenzione,proprietario già presente nel DB");
        }
    }

    @Override
    public void aggiorna(Statement stm) {
        String query = String.format("UPDATE PROPRIETARIO SET NOME='%s' , EMAIL='%s' WHERE CF='%s'", this.nome, this.email, this.CF);
        try {
            stm.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println("ERRORE: Aggiornamento proprietario non riuscito");
        }
    }

    @Override
    public void cancella(Statement stm) {
               String query = String.format("DELETE FROM PROPRIETARIO WHERE CF='%s'", this.CF);
        try {
            stm.executeUpdate(query);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERRORE: Cancellazione proprietario non riuscita");
        }
    }
   
   public static void serializzaProprietario(Proprietario p){
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
			output.writeObject(p);	
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			output.close();
		} 
		catch (IOException e2) {
                        e2.printStackTrace();
		}
	System.out.println("Serializzazione completata.");
	}
   
   public static Proprietario deserializzaProprietario(){
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream("dati.dat"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Proprietario p = null;
        try {
            p = (Proprietario) ois.readObject();
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }

        return p;
}
   
    private String nome;
    private String CF;
    private String email;
    private static final long serialVersionUID = 1L;
    
}
