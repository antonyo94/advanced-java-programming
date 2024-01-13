package officinariparazioni;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.event.*;

/**
 * Classe accessibile da un superutente,permette ad esso  di interagire con
 * gli elementi 'veicolo' nel nostro DB,in particola
 * 
 */
public class IVeicolo extends JFrame {

    public IVeicolo() {
        super("Veicolo");
        initComponents();
        setLocationRelativeTo(null);
        this.fillInListaVeicolo();
        this.setResizable(false);
        this.setVisible(true);
    }

    private void fillInListaVeicolo() {
        try {
            String query = "SELECT * FROM VEICOLO";
            ResultSet rs = DBOfficina.getIstance().getStm().executeQuery(query);

            DefaultListModel DLM = new DefaultListModel();

            while (rs.next()) {
                DLM.addElement(rs.getString(1));
            }
            listaVeicoli.setModel(DLM);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Inserimenti in lista non riuscito");
        }
    }

    private void initComponents() {

        matricolaVeicoloText.setPreferredSize(new Dimension(180, 40));
        CFProprietarioText.setPreferredSize(new Dimension(180, 40));
        tipoVeicoloText.setPreferredSize(new Dimension(180, 40));
        marcaText.setPreferredSize(new Dimension(180, 40));
        tipoCarburanteText.setPreferredSize(new Dimension(180, 40));
        cilindrataText.setPreferredSize(new Dimension(180, 40));
        statoVeicoloText.setPreferredSize(new Dimension(180, 40));
        scrollVeicoli.setViewportView(listaVeicoli);
        scrollVeicoli.setPreferredSize(new Dimension(100, 300));
        aggVeicoloButton.setPreferredSize(new Dimension(180, 35));
        delVeicoloButton.setPreferredSize(new Dimension(180, 35));

        matricolaVeicoloText.setEditable(false);

        listaVeicoli.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent evt) {
                listaVeicoliValueChanged(evt);
            }
        });

        aggVeicoloButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                aggVeicoloButtonActionPerformed(evt);
            }
        });
        delVeicoloButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                delVeicoloButtonActionPerformed(evt);
            }
        });

        Container CPane = this.getContentPane();
        JPanel westPanel = new JPanel();
        JPanel centerPanel = new JPanel();
        JPanel southPanel = new JPanel();

        westPanel.add(scrollVeicoli);
        centerPanel.setLayout(new GridLayout(7, 2));
        centerPanel.add(matricolaVeicoloLabel);
        centerPanel.add(matricolaVeicoloText);
        centerPanel.add(CFProprietarioLabel);
        centerPanel.add(CFProprietarioText);
        centerPanel.add(tipoVeicoloLabel);
        centerPanel.add(tipoVeicoloText);
        centerPanel.add(marcaLabel);
        centerPanel.add(marcaText);
        centerPanel.add(tipoCarburanteLabel);
        centerPanel.add(tipoCarburanteText);
        centerPanel.add(cilindrataLabel);
        centerPanel.add(cilindrataText);
        centerPanel.add(statoVeicoloLabel);
        centerPanel.add(statoVeicoloText);
        southPanel.setLayout(new GridLayout(1, 2));
        southPanel.add(aggVeicoloButton);
        southPanel.add(delVeicoloButton);

        CPane.add(westPanel, BorderLayout.WEST);
        CPane.add(centerPanel, BorderLayout.CENTER);
        CPane.add(southPanel, BorderLayout.SOUTH);

        pack();
    }

    private void listaVeicoliValueChanged(ListSelectionEvent evt) {
        try {
            String query = String.format("SELECT * FROM VEICOLO WHERE MATRICOLA='%s'", listaVeicoli.getSelectedValue());
            ResultSet rs = DBOfficina.getIstance().getStm().executeQuery(query);
            while (rs.next()) {
                matricolaVeicoloText.setText(rs.getString(1));
                CFProprietarioText.setText(rs.getString(2));
                tipoVeicoloText.setText(rs.getString(3));
                marcaText.setText(rs.getString(4));
                tipoCarburanteText.setText(rs.getString(5));
                cilindrataText.setText(rs.getString(6));
                statoVeicoloText.setText(rs.getString(7));
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Attenzione,nessun veicolo nel database");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERRORE nel database");
        }
    }

    private void aggVeicoloButtonActionPerformed(ActionEvent evt) {
        String matricola = matricolaVeicoloText.getText();
        String cf = CFProprietarioText.getText();
        String tipo = tipoVeicoloText.getText();
        String marca = marcaText.getText();
        String carb = tipoCarburanteText.getText();
        int cilindr = Integer.parseInt(cilindrataText.getText());
        String stato = statoVeicoloText.getText();
        Veicolo v1 = new Veicolo(matricola, new Proprietario(cf), tipo, marca, cilindr, carb, stato);
        DBOfficina.getIstance().aggiorna(v1);

        matricolaVeicoloText.setText("");
        CFProprietarioText.setText("");
        tipoVeicoloText.setText("");
        marcaText.setText("");
        tipoCarburanteText.setText("");
        cilindrataText.setText("");
        statoVeicoloText.setText("");
    }

    private void delVeicoloButtonActionPerformed(ActionEvent evt) {
        String matricola = matricolaVeicoloText.getText();
        String cf = CFProprietarioText.getText();
        String tipo = tipoVeicoloText.getText();
        String marca = marcaText.getText();
        String carb = tipoCarburanteText.getText();
        int cilindr = Integer.parseInt(cilindrataText.getText());
        String stato = statoVeicoloText.getText();
        Veicolo v1 = new Veicolo(matricola, new Proprietario(cf), tipo, marca, cilindr, carb, stato);
        DBOfficina.getIstance().cancella(v1);

        matricolaVeicoloText.setText("");
        CFProprietarioText.setText("");
        tipoVeicoloText.setText("");
        marcaText.setText("");
        tipoCarburanteText.setText("");
        cilindrataText.setText("");
        statoVeicoloText.setText("");
    }

    private static JList listaVeicoli = new JList();
    private static JScrollPane scrollVeicoli = new JScrollPane();
    private static final JLabel matricolaVeicoloLabel = new JLabel("Matricola Veicolo");
    private static final JLabel CFProprietarioLabel = new JLabel("CF Proprietario");
    private static final JLabel tipoVeicoloLabel = new JLabel("Tipo Veicolo");
    private static final JLabel marcaLabel = new JLabel("Marca");
    private static final JLabel tipoCarburanteLabel = new JLabel("Tipo Carburante");
    private static final JLabel cilindrataLabel = new JLabel("Cilindrata");
    private static final JLabel statoVeicoloLabel = new JLabel("Stato Veicolo");
    private static final JTextField matricolaVeicoloText = new JTextField();
    private static final JTextField CFProprietarioText = new JTextField();
    private static final JTextField tipoVeicoloText = new JTextField();
    private static final JTextField marcaText = new JTextField();
    private static final JTextField tipoCarburanteText = new JTextField();
    private static final JTextField cilindrataText = new JTextField();
    private static final JTextField statoVeicoloText = new JTextField();
    private static final JButton aggVeicoloButton = new JButton("Update Veicolo");
    private static final JButton delVeicoloButton = new JButton("Delete Veicolo");
}
