package officinariparazioni;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.event.*;

/**
 * Classe accessibile da un superutente,permette di visualizzare i pagamenti 
 * effettuati dai clienti immagazzinati nel Database dell'applicazione
 */

public class IPagamento extends JFrame {

    public IPagamento() {
        super("Pagamento");
        initComponents();
        setLocationRelativeTo(null);
        this.fillInListaPagamento();
        this.setResizable(false);
        this.setVisible(true);
    }

    private void fillInListaPagamento() {
        try {
            String query = "SELECT * FROM PAGAMENTO";
            ResultSet rs = DBOfficina.getIstance().getStm().executeQuery(query);

            DefaultListModel DLM = new DefaultListModel();

            while (rs.next()) {
                DLM.addElement(rs.getString(3));
            }
            listaPagamenti.setModel(DLM);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Inserimenti in lista non riuscito");
        }
    }

    private void initComponents() {
        scrollPagamenti.setViewportView(listaPagamenti);
        scrollPagamenti.setPreferredSize(new Dimension(100, 200));
        quantitaText.setPreferredSize(new Dimension(180, 40));
        tipoPagamentoText.setPreferredSize(new Dimension(180, 40));
        codiceRichiestaPText.setPreferredSize(new Dimension(180, 40));
        numBancomatText.setPreferredSize(new Dimension(180, 40));
        numCCreditoText.setPreferredSize(new Dimension(180, 40));

        codiceRichiestaPText.setEditable(false);

        listaPagamenti.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent evt) {
                listaPagamentiValueChanged(evt);
            }
        });

        Container CPane = this.getContentPane();
        JPanel westPanel = new JPanel();
        JPanel centerPanel = new JPanel();

        westPanel.add(scrollPagamenti);

        centerPanel.setLayout(new GridLayout(5, 2));
        centerPanel.add(quantitaLabel);
        centerPanel.add(quantitaText);
        centerPanel.add(tipoPagamentoLabel);
        centerPanel.add(tipoPagamentoText);
        centerPanel.add(codiceRichiestaPLabel);
        centerPanel.add(codiceRichiestaPText);
        centerPanel.add(numBancomatLabel);
        centerPanel.add(numBancomatText);
        centerPanel.add(numCCreditoLabel);
        centerPanel.add(numCCreditoText);

        CPane.add(westPanel, BorderLayout.WEST);
        CPane.add(centerPanel, BorderLayout.CENTER);

        pack();

    }

    private void listaPagamentiValueChanged(ListSelectionEvent evt) {
        try {
            String query = String.format("SELECT * FROM PAGAMENTO WHERE CODICE_RICHIESTA='%s'", listaPagamenti.getSelectedValue());
            ResultSet rs = DBOfficina.getIstance().getStm().executeQuery(query);
            while (rs.next()) {
                quantitaText.setText(rs.getString(1));
                tipoPagamentoText.setText(rs.getString(2));
                codiceRichiestaPText.setText(rs.getString(3));
                numBancomatText.setText(rs.getString(4));
                numCCreditoText.setText(rs.getString(5));
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Attenzione,nessun pagamento nel database");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERRORE nel database");
        }
    }

    private static JList listaPagamenti = new JList();
    private static JScrollPane scrollPagamenti = new JScrollPane();
    private static final JLabel quantitaLabel = new JLabel("Quantita");
    private static final JLabel tipoPagamentoLabel = new JLabel("Tipo Pagamento");
    private static final JLabel codiceRichiestaPLabel = new JLabel("Codice Richiesta");
    private static final JLabel numBancomatLabel = new JLabel("Numero Bancomat");
    private static final JLabel numCCreditoLabel = new JLabel("Numero Carta");
    private static final JTextField quantitaText = new JTextField();
    private static final JTextField tipoPagamentoText = new JTextField();
    private static final JTextField codiceRichiestaPText = new JTextField();
    private static final JTextField numBancomatText = new JTextField();
    private static final JTextField numCCreditoText = new JTextField();

}
