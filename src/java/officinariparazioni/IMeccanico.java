package officinariparazioni;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.event.*;
/**
 * Finestra che permette all'ammnistratore del sistema di interagire con gli 
 * elementi 'Meccanico' del Database,in particolar modo consentendone l'aggiornamento
 * oppure la cancellazione
 */

public class IMeccanico extends JFrame {

    public IMeccanico() {
        super("Meccanico");
        initComponents();
        setLocationRelativeTo(null);
        this.fillInListaMeccanico();
        this.setResizable(false);
        this.setVisible(true);
    }

    private void fillInListaMeccanico() {
        try {
            String query = "SELECT * FROM MECCANICO";
            ResultSet rs = DBOfficina.getIstance().getStm().executeQuery(query);

            DefaultListModel DLM = new DefaultListModel();

            while (rs.next()) {
                DLM.addElement(rs.getString(1));
            }
            listaMeccanici.setModel(DLM);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Inserimenti in lista non riuscito");
        }
    }

    private void initComponents() {
        matricolaMeccanicoText.setPreferredSize(new Dimension(180, 35));
        nomeMeccanicoText.setPreferredSize(new Dimension(180, 35));
        tipoMeccanicoText.setPreferredSize(new Dimension(180, 35));
        scrollMeccanici.setViewportView(listaMeccanici);
        scrollMeccanici.setPreferredSize(new Dimension(100, 150));
        aggMeccanicoButton.setPreferredSize(new Dimension(180, 35));
        delMeccanicoButton.setPreferredSize(new Dimension(180, 35));

        matricolaMeccanicoText.setEditable(false);

        listaMeccanici.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent evt) {
                listaMeccaniciValueChanged(evt);
            }
        });

        aggMeccanicoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                aggMeccanicoButtonActionPerformed(evt);
            }
        });
        delMeccanicoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                delMeccanicoButtonActionPerformed(evt);
            }
        });

        Container CPane = this.getContentPane();
        JPanel westPanel = new JPanel();
        JPanel centerPanel = new JPanel();
        JPanel southPanel = new JPanel();

        westPanel.add(scrollMeccanici);
        centerPanel.setLayout(new GridLayout(3, 2));
        centerPanel.add(matricolaMeccanicoLabel);
        centerPanel.add(matricolaMeccanicoText);
        centerPanel.add(nomeMeccanicoLabel);
        centerPanel.add(nomeMeccanicoText);
        centerPanel.add(tipoMeccanicoLabel);
        centerPanel.add(tipoMeccanicoText);
        southPanel.setLayout(new GridLayout(1, 2));
        southPanel.add(aggMeccanicoButton);
        southPanel.add(delMeccanicoButton);

        CPane.add(westPanel, BorderLayout.WEST);
        CPane.add(centerPanel, BorderLayout.CENTER);
        CPane.add(southPanel, BorderLayout.SOUTH);

        pack();
    }

    private void listaMeccaniciValueChanged(ListSelectionEvent evt) {
        try {
            String query = String.format("SELECT * FROM MECCANICO WHERE MATRICOLA='%s'", listaMeccanici.getSelectedValue());
            ResultSet rs = DBOfficina.getIstance().getStm().executeQuery(query);
            while (rs.next()) {
                matricolaMeccanicoText.setText(rs.getString(1));
                nomeMeccanicoText.setText(rs.getString(2));
                tipoMeccanicoText.setText(rs.getString(3));
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Attenzione,nessun meccanico nel database");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERRORE nel database");
        }

    }

    private void aggMeccanicoButtonActionPerformed(ActionEvent evt) {
        String matricola = matricolaMeccanicoText.getText();
        String nome = nomeMeccanicoText.getText();
        String tipo = tipoMeccanicoText.getText();
        Meccanico m1 = new Meccanico(matricola, nome, tipo);
        DBOfficina.getIstance().aggiorna(m1);
        matricolaMeccanicoText.setText("");
        nomeMeccanicoText.setText("");
        tipoMeccanicoText.setText("");

    }

    private void delMeccanicoButtonActionPerformed(ActionEvent evt) {
        String matricola = matricolaMeccanicoText.getText();
        String nome = nomeMeccanicoText.getText();
        String tipo = tipoMeccanicoText.getText();
        Meccanico m1 = new Meccanico(matricola, nome, tipo);
        DBOfficina.getIstance().cancella(m1);
        matricolaMeccanicoText.setText("");
        nomeMeccanicoText.setText("");
        tipoMeccanicoText.setText("");
    }

    private static JList listaMeccanici = new JList();
    private static JScrollPane scrollMeccanici = new JScrollPane();
    private static final JLabel matricolaMeccanicoLabel = new JLabel("Matricola Meccanico");
    private static final JLabel nomeMeccanicoLabel = new JLabel("Nome Meccanico");
    private static final JLabel tipoMeccanicoLabel = new JLabel("Tipo Meccanico");
    private static final JTextField matricolaMeccanicoText = new JTextField();
    private static final JTextField nomeMeccanicoText = new JTextField();
    private static final JTextField tipoMeccanicoText = new JTextField();
    private static final JButton aggMeccanicoButton = new JButton("Update Meccanico");
    private static final JButton delMeccanicoButton = new JButton("Delete Meccanico");
}
