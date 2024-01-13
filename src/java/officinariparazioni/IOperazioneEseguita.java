package officinariparazioni;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.event.*;
/**
 * Classe cui può accedere un amministratore di sistema per interagire con gli elementi 
 * 'operazione eseguita' della nostra applicazione,consentendone la cancellazione o 
 * l'aggiornamento
 */

public class IOperazioneEseguita extends JFrame {

    public IOperazioneEseguita() {
        super("Operazione Eseguita");
        initComponents();
        setLocationRelativeTo(null);
        this.fillInListaOpEseguite();
        this.setResizable(false);
        this.setVisible(true);
    }

    private void fillInListaOpEseguite() {
        try {
            String query = "SELECT * FROM OPERAZIONE_ESEGUITA";
            ResultSet rs = DBOfficina.getIstance().getStm().executeQuery(query);

            DefaultListModel DLM = new DefaultListModel();

            while (rs.next()) {
                DLM.addElement(rs.getString(1));
            }
            listaOperazioniEseguite.setModel(DLM);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Inserimenti in lista non riuscito");
        }
    }

    private void initComponents() {
        scrollOperazioniEseguite.setViewportView(listaOperazioniEseguite);
        scrollOperazioniEseguite.setPreferredSize(new Dimension(100, 350));
        codiceRichiestaText.setPreferredSize(new Dimension(180, 40));
        tipoOperazioneText.setPreferredSize(new Dimension(180, 40));
        costoText.setPreferredSize(new Dimension(180, 40));
        componentiText.setPreferredSize(new Dimension(180, 40));
        tipoProblemaText.setPreferredSize(new Dimension(180, 40));
        matricolaMeccanicoOpText.setPreferredSize(new Dimension(180, 40));
        matricolaVeicoloOpText.setPreferredSize(new Dimension(180, 40));
        statoVeicoloOpText.setPreferredSize(new Dimension(180, 40));
        esitoText.setPreferredSize(new Dimension(180, 40));
        aggOperazioneEseguitaButton.setPreferredSize(new Dimension(180, 35));
        delOperazioneEseguitaButton.setPreferredSize(new Dimension(180, 35));

        codiceRichiestaText.setEditable(false);

        listaOperazioniEseguite.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent evt) {
                listaOperazioniEseguiteValueChanged(evt);
            }
        });

        aggOperazioneEseguitaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                aggOperazioneEseguitaButtonActionPerformed(evt);
            }
        });
        delOperazioneEseguitaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                delOperazioneEseguitaButtonActionPerformed(evt);
            }
        });

        Container CPane = this.getContentPane();
        JPanel westPanel = new JPanel();
        JPanel centerPanel = new JPanel();
        JPanel southPanel = new JPanel();

        westPanel.add(scrollOperazioniEseguite);

        centerPanel.setLayout(new GridLayout(9, 2));
        centerPanel.add(codiceRichiestaLabel);
        centerPanel.add(codiceRichiestaText);
        centerPanel.add(tipoOperazioneLabel);
        centerPanel.add(tipoOperazioneText);
        centerPanel.add(costoLabel);
        centerPanel.add(costoText);
        centerPanel.add(componentiLabel);
        centerPanel.add(componentiText);
        centerPanel.add(tipoProblemaLabel);
        centerPanel.add(tipoProblemaText);
        centerPanel.add(matricolaMeccanicoOpLabel);
        centerPanel.add(matricolaMeccanicoOpText);
        centerPanel.add(matricolaVeicoloOpLabel);
        centerPanel.add(matricolaVeicoloOpText);
        centerPanel.add(statoVeicoloOpLabel);
        centerPanel.add(statoVeicoloOpText);
        centerPanel.add(esitoLabel);
        centerPanel.add(esitoText);

        southPanel.setLayout(new GridLayout(1, 2));
        southPanel.add(aggOperazioneEseguitaButton);
        southPanel.add(delOperazioneEseguitaButton);

        CPane.add(westPanel, BorderLayout.WEST);
        CPane.add(centerPanel, BorderLayout.CENTER);
        CPane.add(southPanel, BorderLayout.SOUTH);

        pack();
    }

    private void listaOperazioniEseguiteValueChanged(ListSelectionEvent evt) {
        try {
            String query = String.format("SELECT * FROM OPERAZIONE_ESEGUITA WHERE CODICE_RICHIESTA='%s'", listaOperazioniEseguite.getSelectedValue());
            ResultSet rs = DBOfficina.getIstance().getStm().executeQuery(query);
            while (rs.next()) {
                codiceRichiestaText.setText(rs.getString(1));
                tipoOperazioneText.setText(rs.getString(2));
                costoText.setText(rs.getString(3));
                componentiText.setText(rs.getString(4));
                tipoProblemaText.setText(rs.getString(5));
                matricolaMeccanicoOpText.setText(rs.getString(6));
                matricolaVeicoloOpText.setText(rs.getString(7));
                statoVeicoloOpText.setText(rs.getString(8));
                esitoText.setText(rs.getString(9));
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Attenzione,nessuna Operazione Eseguita nel database");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERRORE nel database");
        }
    }
    
    private void aggOperazioneEseguitaButtonActionPerformed(ActionEvent evt) {
        String codice = codiceRichiestaText.getText();
        String tipo = tipoOperazioneText.getText();
        Double costo = Double.parseDouble(costoText.getText());
        String componenti = componentiText.getText();
        String problm = tipoProblemaText.getText();
        String matrm = matricolaMeccanicoOpText.getText();
        String matrv = matricolaVeicoloOpText.getText();
        String stato = statoVeicoloOpText.getText();
        String esito = esitoText.getText();

        OperazioneEseguita op = new OperazioneEseguita(codice, tipo, costo, componenti, problm, matrm, matrv, stato, esito);
        DBOfficina.getIstance().aggiorna(op);
        codiceRichiestaText.setText("");
        tipoOperazioneText.setText("");
        costoText.setText("");
        componentiText.setText("");
        tipoProblemaText.setText("");
        matricolaMeccanicoOpText.setText("");
        matricolaVeicoloOpText.setText("");
        statoVeicoloOpText.setText("");
        esitoText.setText("");

    }

    private void delOperazioneEseguitaButtonActionPerformed(ActionEvent evt) {
        String codice = codiceRichiestaText.getText();
        String tipo = tipoOperazioneText.getText();
        Double costo = Double.parseDouble(costoText.getText());
        String componenti = componentiText.getText();
        String problm = tipoProblemaText.getText();
        String matrm = matricolaMeccanicoOpText.getText();
        String matrv = matricolaVeicoloOpText.getText();
        String stato = statoVeicoloOpText.getText();
        String esito = esitoText.getText();

        OperazioneEseguita op = new OperazioneEseguita(codice, tipo, costo, componenti, problm, matrm, matrv, stato, esito);
        DBOfficina.getIstance().cancella(op);
        codiceRichiestaText.setText("");
        tipoOperazioneText.setText("");
        costoText.setText("");
        componentiText.setText("");
        tipoProblemaText.setText("");
        matricolaMeccanicoOpText.setText("");
        matricolaVeicoloOpText.setText("");
        statoVeicoloOpText.setText("");
        esitoText.setText("");
    }

    private JList listaOperazioniEseguite = new JList();
    private static JScrollPane scrollOperazioniEseguite = new JScrollPane();
    private static final JLabel codiceRichiestaLabel = new JLabel("Codice Richiesta");
    private static final JLabel tipoOperazioneLabel = new JLabel("Tipo Operazione");
    private static final JLabel costoLabel = new JLabel("Costo Operazione");
    private static final JLabel componentiLabel = new JLabel("Componenti Aggiunti");
    private static final JLabel tipoProblemaLabel = new JLabel("Tipo Problema");
    private static final JLabel matricolaMeccanicoOpLabel = new JLabel("Matricola Meccanico");
    private static final JLabel matricolaVeicoloOpLabel = new JLabel("Matricola Veicolo");
    private static final JLabel statoVeicoloOpLabel = new JLabel("Stato Veicolo");
    private static final JLabel esitoLabel = new JLabel("Esito");
    private static final JTextField codiceRichiestaText = new JTextField();
    private static final JTextField tipoOperazioneText = new JTextField();
    private static final JTextField costoText = new JTextField();
    private static final JTextField componentiText = new JTextField();
    private static final JTextField tipoProblemaText = new JTextField();
    private static final JTextField matricolaMeccanicoOpText = new JTextField();
    private static final JTextField matricolaVeicoloOpText = new JTextField();
    private static final JTextField statoVeicoloOpText = new JTextField();
    private static final JTextField esitoText = new JTextField();
    private static final JButton aggOperazioneEseguitaButton = new JButton("Update Op.Eseguita");
    private static final JButton delOperazioneEseguitaButton = new JButton("Delete Op.Eseguita");
}
