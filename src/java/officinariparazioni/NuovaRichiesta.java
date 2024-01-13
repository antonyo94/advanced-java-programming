package officinariparazioni;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Frame che permette all'utente di inserire quelle che sono le informazioni 
 * legate ad una nuova richiesta presso l'officina.In particolare esso inserirà
 * le informazioni legate a se stesso,poi al proprio veicolo,ed infine selezionerà 
 * attraverso la pressione del relativ o bottone quella che è l'operaione desiderata
 * settabile tra riparazione,rettifica o aggiunta di componenti.
 * 
 */
public class NuovaRichiesta extends JFrame {
    /**
     * Costruttore che setta il titolo,imposta i componenti e la locazione della
     * nostra finestrs,oltre a renderla visibile
     */
    public NuovaRichiesta() {
        super("Nuova Richiesta");
        initComponents();
        setLocationRelativeTo(null);
        this.setVisible(true);
    }

    /**
     * Funzione che setta ed inizializza i componenti del nostro frame
     */
    private void initComponents() {

        NomeLabel = new JLabel("Nome");
        CFLabel = new JLabel("CF");
        EmailLabel = new JLabel("Email");
        NomeTextField = new JTextField();
        CFTextField = new JTextField();
        EmailTextField = new JTextField();
        MatricolaVeicoloLabel = new JLabel("Matricola Veicolo");
        TipoVeicoloLabel = new JLabel("Tipo Veicolo");
        MarcaLabel = new JLabel("Marca");
        CilindrataLabel = new JLabel("Cilindrata");
        TipoCarburanteLabel = new JLabel("Tipo Carburante");
        MatricolaVeicoloTextField = new JTextField();
        String listaVeicoli[]={"Automobile","Ciclomotore","MotoVeicolo","Barca"};
        TipoVeicoloComboBox = new JComboBox(listaVeicoli);
        MarcaTextField = new JTextField();
        CilindrataTextField = new JTextField();
        String listaCarburante[]={"Benzina","Diesel","GPL","Metano","Altro"};
        TipoCarburanteComboBox = new JComboBox(listaCarburante);
        RiparazioneButton = new JButton("Riparazione");
        RettificaButton = new JButton("Rettifica");
        AggiuntaComponentiButton = new JButton("Aggiunta Componenti");

        RiparazioneButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                RiparazioneButtonActionPerformed(evt);
            }
        });

        RettificaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                RettificaButtonActionPerformed(evt);
            }
        });

        AggiuntaComponentiButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                AggiuntaComponentiButtonActionPerformed(evt);
            }
        });

        NomeTextField.setPreferredSize(new Dimension(200, 35));
        CFTextField.setPreferredSize(new Dimension(200, 35));
        EmailTextField.setPreferredSize(new Dimension(200, 35));
        MatricolaVeicoloTextField.setPreferredSize(new Dimension(200, 35));
        TipoVeicoloComboBox.setPreferredSize(new Dimension(200, 35));
        MarcaTextField.setPreferredSize(new Dimension(200, 35));
        CilindrataTextField.setPreferredSize(new Dimension(200, 35));
        TipoCarburanteComboBox.setPreferredSize(new Dimension(200, 35));

        RiparazioneButton.setPreferredSize(new Dimension(150, 40));
        RettificaButton.setPreferredSize(new Dimension(150, 40));
        AggiuntaComponentiButton.setPreferredSize(new Dimension(200, 40));

        this.setResizable(false);
        Container CPane = this.getContentPane();
        JPanel westPanel = new JPanel();
        JPanel southPanel = new JPanel();

        westPanel.setLayout(new GridLayout(8, 2));

        westPanel.add(NomeLabel);
        westPanel.add(NomeTextField);
        westPanel.add(CFLabel);
        westPanel.add(CFTextField);
        westPanel.add(EmailLabel);
        westPanel.add(EmailTextField);
        westPanel.add(MatricolaVeicoloLabel);
        westPanel.add(MatricolaVeicoloTextField);
        westPanel.add(TipoVeicoloLabel);
        westPanel.add(TipoVeicoloComboBox);
        westPanel.add(MarcaLabel);
        westPanel.add(MarcaTextField);
        westPanel.add(CilindrataLabel);
        westPanel.add(CilindrataTextField);
        westPanel.add(TipoCarburanteLabel);
        westPanel.add(TipoCarburanteComboBox);

        southPanel.add(RiparazioneButton);
        southPanel.add(RettificaButton);
        southPanel.add(AggiuntaComponentiButton);

        CPane.add(westPanel, BorderLayout.WEST);
        CPane.add(southPanel, BorderLayout.SOUTH);

        pack();

    }

    private void RettificaButtonActionPerformed(ActionEvent evt) {
        try {
            String nome = NomeTextField.getText();
            String CF = CFTextField.getText();
            String email = EmailTextField.getText();
            String matricolaVeicolo = MatricolaVeicoloTextField.getText();
            String tipoVeicolo = (String)TipoVeicoloComboBox.getSelectedItem();
            String marca = MarcaTextField.getText();
            int cilindrata = Integer.parseInt(CilindrataTextField.getText());
            String tipoCarburante = (String)TipoCarburanteComboBox.getSelectedItem();

            if (CF.equals("") || email.equals("") || matricolaVeicolo.equals("")) {
                Exception e = new Exception();
                throw e;
            }
            Proprietario p = new Proprietario(nome, CF, email);
            Veicolo v = new Veicolo(matricolaVeicolo, p, tipoVeicolo, marca, cilindrata, tipoCarburante,"IN_ENTRATA");
            Richiesta r = new Richiesta(v);
            RichiestaRettifica richiestaret = new RichiestaRettifica(r);

            DBOfficina.getIstance().inserisci(p);
            DBOfficina.getIstance().inserisci(v);
            DBOfficina.getIstance().inserisci(r);

            Reception.getIstance().prenotaIntervento(r.getCodicePrenotazione(), richiestaret);

            JOptionPane.showMessageDialog(null, "Rettifica Prenotata!\nConservi il codice della ricevuta: " + r.getCodicePrenotazione());

            this.dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Attenzione,riempire tutti i campi con valori validi");
        }
    }

    private void RiparazioneButtonActionPerformed(java.awt.event.ActionEvent evt) {

        try {
            String nome = NomeTextField.getText();
            String CF = CFTextField.getText();
            String email = EmailTextField.getText();
            String matricolaVeicolo = MatricolaVeicoloTextField.getText();
            String tipoVeicolo = (String)TipoVeicoloComboBox.getSelectedItem();
            String marca = MarcaTextField.getText();
            int cilindrata = Integer.parseInt(CilindrataTextField.getText());
            String tipoCarburante = (String)TipoCarburanteComboBox.getSelectedItem();

            if (CF.equals("") || email.equals("") || matricolaVeicolo.equals("")) {
                Exception e = new Exception();
                throw e;
            }
            Proprietario p = new Proprietario(nome, CF, email);
            Veicolo v = new Veicolo(matricolaVeicolo, p, tipoVeicolo, marca, cilindrata, tipoCarburante,"IN_ENTRATA");
            Richiesta r = new Richiesta(v);
            RichiestaRiparazione richiestarip = new RichiestaRiparazione(r);

            DBOfficina.getIstance().inserisci(p);
            DBOfficina.getIstance().inserisci(v);
            DBOfficina.getIstance().inserisci(r);

            Reception.getIstance().prenotaIntervento(r.getCodicePrenotazione(), richiestarip);

            JOptionPane.showMessageDialog(null, "RiparazionePrenotata!\nConservi il codice della ricevuta: " + r.getCodicePrenotazione());
            this.dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Attenzione,riempire tutti i campi con valori validi");
        }

    }

    private void AggiuntaComponentiButtonActionPerformed(ActionEvent evt) {
        try {
            String nome = NomeTextField.getText();
            String CF = CFTextField.getText();
            String email = EmailTextField.getText();
            String matricolaVeicolo = MatricolaVeicoloTextField.getText();
            String tipoVeicolo =(String)TipoVeicoloComboBox.getSelectedItem();
            String marca = MarcaTextField.getText();
            int cilindrata = Integer.parseInt(CilindrataTextField.getText());
            String tipoCarburante = (String)TipoCarburanteComboBox.getSelectedItem();

            if (CF.equals("") || email.equals("") || matricolaVeicolo.equals("")) {
                Exception e = new Exception();
                throw e;
            }
            Proprietario p = new Proprietario(nome, CF, email);
            Veicolo v = new Veicolo(matricolaVeicolo, p, tipoVeicolo, marca, cilindrata, tipoCarburante,"IN_ENTRATA");
            Richiesta r = new Richiesta(v);
            RichiestaAggComponenti richiestaAgg = new RichiestaAggComponenti(r);

            DBOfficina.getIstance().inserisci(p);
            DBOfficina.getIstance().inserisci(v);
            DBOfficina.getIstance().inserisci(r);

            Reception.getIstance().prenotaIntervento(r.getCodicePrenotazione(), richiestaAgg);

            JOptionPane.showMessageDialog(null, "Aggiunta Componenti Prenotata!\nConservi il codice della ricevuta: " + r.getCodicePrenotazione());

            this.dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Attenzione,riempire tutti i campi con valori validi");
        }
    }


    private JButton AggiuntaComponentiButton;
    private JLabel CFLabel;
    private JTextField CFTextField;
    private JLabel CilindrataLabel;
    private JTextField CilindrataTextField;
    private JLabel EmailLabel;
    private JTextField EmailTextField;
    private JLabel MarcaLabel;
    private JTextField MarcaTextField;
    private JLabel MatricolaVeicoloLabel;
    private JTextField MatricolaVeicoloTextField;
    private JLabel NomeLabel;
    private JTextField NomeTextField;
    private JButton RettificaButton;
    private JButton RiparazioneButton;
    private JLabel TipoCarburanteLabel;
    private JComboBox TipoCarburanteComboBox;
    private JLabel TipoVeicoloLabel;
    private JComboBox TipoVeicoloComboBox;

}
