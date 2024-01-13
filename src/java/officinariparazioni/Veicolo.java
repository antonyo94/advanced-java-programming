package officinariparazioni;

import java.io.*;
import java.sql.*;
import javax.swing.*;
/**
 * Classe che astre il concetto di veicolo,con la possibilita dello stesso di essere
 * inserito nel Database
 * 
 */

public class Veicolo implements elementoDB,Serializable{
    
    /**
     * Costruttore con parametri
     * @param matricola id univoco del nostro veicolo
     * @param proprietario proprietario del veicolo 
     * @param tipoVeicolo tipo del veicolo
     * @param marca  marca del veicolo
     * @param cilindrata  cilindrata del veicolo
     * @param tipoCarburante tipo di carburante che lo alimenta
     * @param statoVeicolo stato che caratterizza il veicolo durante le operazioni 
     *                     cui esso è soggetto
     */

    public Veicolo(String matricola, Proprietario proprietario, String tipoVeicolo, String marca, 
            int cilindrata, String tipoCarburante,String statoVeicolo) {
        this.matricola = matricola;
        this.proprietario = proprietario;
        this.tipoVeicolo = tipoVeicolo;
        this.marca = marca;
        this.cilindrata = cilindrata;
        this.tipoCarburante = tipoCarburante;
        this.statoVeicolo=statoVeicolo;
    }

    public String getMatricola() {
        return matricola;
    }

    public Proprietario getProprietario() {
        return proprietario;
    }

    public String getTipoVeicolo() {
        return tipoVeicolo;
    }

    public String getMarca() {
        return marca;
    }

    public int getCilindrata() {
        return cilindrata;
    }

    public String getTipoCarburante() {
        return tipoCarburante;
    }

    public String getStatoVeicolo() {
        return statoVeicolo;
    }
    

    public void setMatricola(String matricola) {
        this.matricola = matricola;
    }

    public void setProprietario(Proprietario proprietario) {
        this.proprietario = proprietario;
    }

    public void setTipoVeicolo(String tipoVeicolo) {
        this.tipoVeicolo = tipoVeicolo;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setCilindrata(int cilindrata) {
        this.cilindrata = cilindrata;
    }

    public void setTipoCarburante(String tipoCarburante) {
        this.tipoCarburante = tipoCarburante;
    }

    public void setStatoVeicolo(String statoVeicolo) {
        this.statoVeicolo = statoVeicolo;
    }

    @Override
    public String toString() {
        return "Veicolo{" + "matricola=" + matricola + ", proprietario=" + proprietario +
                ", tipoVeicolo=" + tipoVeicolo + ", marca=" + marca + ", cilindrata=" +
                cilindrata + ", tipoCarburante=" + tipoCarburante + ", statoVeicolo=" +
                statoVeicolo + '}';
    }
    
    @Override
    public void inserisci(Statement stm) {
        String query = String.format("INSERT INTO VEICOLO "
                + "(MATRICOLA,CF_PROPRIETARIO,TIPO_VEICOLO,MARCA,"
                + "TIPO_CARBURANTE,CILINDRATA,STATO_VEICOLO) VALUES ('%s','%s','%s','%s','%s',%s,'%s')",
                this.matricola,this.proprietario.getCF(),this.marca,this.tipoVeicolo,
                this.tipoCarburante,this.cilindrata,this.statoVeicolo);
        try {
            stm.executeUpdate(query);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Attenzione,veicolo già presente nel DB");
        }
    }

    @Override
    public void aggiorna(Statement stm) {
       String query = String.format("UPDATE VEICOLO SET CF_PROPRIETARIO='%s' , "
                + "TIPO_VEICOLO='%s',"
                + "MARCA='%s',"
                + "CILINDRATA=%s,"
                + "TIPO_CARBURANTE='%s',"
                + "STATO_VEICOLO='%s'"
                + " WHERE MATRICOLA='%s'",this.proprietario.getCF(),this.tipoVeicolo,
                this.marca,this.cilindrata,this.tipoCarburante,this.statoVeicolo,this.matricola);
        try {
            stm.executeUpdate(query);
        } catch (SQLException e) {

            JOptionPane.showMessageDialog(null,"ERRORE: Aggiornamento veicolo non riuscito");
        }
    }

    @Override
    public void cancella(Statement stm) {
         String query = String.format("DELETE FROM VEICOLO WHERE MATRICOLA='%s'", this.matricola);
        try {
            stm.executeUpdate(query);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERRORE: Cancellazione veicolo non riuscita");
        }
    }

    public static void serializzaVeicolo(Veicolo v){
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
			output.writeObject(v);	
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
   
   public static Veicolo deserializzaVeicolo(){
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream("dati.dat"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Veicolo v = null;
        try {
            v = (Veicolo) ois.readObject();
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }

        return v;
}
    
    private static final long serialVersionUID = 1L;
    private String matricola;
    private Proprietario proprietario;
    private String tipoVeicolo;
    private String marca;
    private int cilindrata;
    private String tipoCarburante;
    private String statoVeicolo;   
    
}
