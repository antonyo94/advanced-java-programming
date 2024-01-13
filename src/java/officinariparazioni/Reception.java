package officinariparazioni;

import java.util.HashMap;
import javax.swing.JOptionPane;
import java.io.*;

/**
 *
 * Interfaccia a disposizione del client relativa Command Pattern,permette di aggiungere una nuova richiesta
 * (Ovvero una prenotazione) in coda,ovvero di incapsulare all'interno di
 * un'oggetto differenti tipologie di richieste da parte dei client.Mediante
 * tale meccanico si separa il mittente della richiesta dal destinatario che
 * andraà ad eseguirla. Si noti che essa è stata realizzata mediante il Pattern
 * Singleton ed inoltre dà la possibilità non solo di inserire nuove operazioni
 * da compiere,ma anche eventualmente di annullarle.
 *
 */

public class Reception implements Serializable {

    /**
     * Costruttore
     */
    private Reception() {
        this.listaRichieste = new HashMap<>();
        this.deserializzaReception();
    }

    /**
     *
     * @return l'unica istanza di reception disponibile
     */
    public static Reception getIstance() {
        return istance;
    }

    /**
     * Permette di aggiungere una nuova prenotazione in coda,aggiungendo
     * l'elemento all'hashmap
     *
     * @param codice codice univoco della richiesta
     * @param prenotazione elemento di tipo prenotazione,ovvero appartenente ad 
     *                      una classe che implementerà la relativa interfaccia
     */
    public void prenotaIntervento(String codice, Prenotazione prenotazione) {

        this.listaRichieste.put(codice, prenotazione);
        this.serializzaReception();
        this.deserializzaReception();
    }

    /**
     * Seleziona l'elemento con il corrispettivo codice dalla nostra hashmap e
     * di esso sfrutta il meotodo execute,opportunamente ridefinito in una
     * classe che andrà ad implementare l'interfaccia prenotazione
     *
     * @param codice codice univoco della richiesta da eseguire
     */
    public void eseguiIntervento(String codice) {

        if (this.listaRichieste.containsKey(codice)) {
            this.listaRichieste.get(codice).execute();
            this.listaRichieste.remove(codice);
            this.serializzaReception();
        } else {
            JOptionPane.showMessageDialog(null, "Attenzione,il codice da lei inserito non è valido!");
        }

    }

    /**
     * Analogamente alla funzione di inserimento anche in questo caso si fa
     * riferimento al codice univoco al fine di selezionare una determinata
     * richiesta e per essa invocare il metodo di annullamento opportunamente
     * ridefinito
     *
     * @param codice codice univoco che identifica la nostra richiesta
     */
    public void ritiraIntervento(String codice) {

        if (this.listaRichieste.containsKey(codice)) {
            this.listaRichieste.get(codice).undo();
            this.listaRichieste.remove(codice);
            this.serializzaReception();
            //  JOptionPane.showMessageDialog(null,"Operazione Annullata correttamente");
        } else {
            JOptionPane.showMessageDialog(null, "Attenzione,il codice da lei inserito non è valido!");
        }

    }

    /**
     * Function che crea una nuova finestra dando la possibilita al cliente di
     * ritirare il proprio veicolo
     */
    public void ritiraVeicolo() {
        //Se il codice è identico e lo stato richiesta è in uscita....
        //TODO
        RitiroVeicolo rv = new RitiroVeicolo();
        rv.setVisible(true);
    }

    public void stampaReception() {
        for (HashMap.Entry<String, Prenotazione> entry : this.listaRichieste.entrySet()) {

            System.out.println(entry.getKey() + " : " + entry.getValue().toString());

        }
        System.out.println("Non ci sono altre richieste il lista!");
    }

    public void serializzaReception() {

        ObjectOutputStream output = null;
        try {
            output = new ObjectOutputStream(new FileOutputStream("datiReception.dat"));
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "ERRORE: Dati reception file non trovato");
        } catch (IOException ioe) {
            JOptionPane.showMessageDialog(null, "ERRORE: IO reception file non riuscito");
        }
        try {
            output.writeObject(this.listaRichieste);
        } catch (IOException e1) {
            JOptionPane.showMessageDialog(null, "ERRORE:Scrittura Dati reception file non riuscita");
        }
        try {
            output.close();
        } catch (IOException e2) {
            JOptionPane.showMessageDialog(null, "ERRORE: Chiusura Dati reception file non riuscita");
        }


    }

    public void deserializzaReception() {
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream("datiReception.dat"));
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "ERRORE: Dati reception file non trovato");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "ERRORE: IO reception file non riuscito");
        }
        try {
            this.listaRichieste = (HashMap<String, Prenotazione>) ois.readObject();
        } catch (IOException e1) {
            JOptionPane.showMessageDialog(null, "ERRORE: lettura Dati reception file non riuscita");
        } catch (ClassNotFoundException e1) {
            JOptionPane.showMessageDialog(null, "ERRORE: Chiusura Dati reception file non riuscita");
        }

    }

    private static Reception istance = new Reception();
    private HashMap<String, Prenotazione> listaRichieste;
    private static final long serialVersionUID = 1L;
}
