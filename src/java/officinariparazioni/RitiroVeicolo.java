package officinariparazioni;
import java.sql.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
/**
 * Frame che viene mostrato nel momento in cui il cliente seleziona di ritirare
 * il proprio veicolo,verificando se il suo stato sia attualmente come in uscita,
 * ovvero è gia stata per esso eseguita la coorispettiva operazione richiesta.
 * Il frame permette infine di andare a selezionare il metodo di pagamento che
 * il nostro cliente desidera
 * @author antonio
 */
public class RitiroVeicolo extends  JFrame {


    public RitiroVeicolo() {
        super("Ritiro Veicolo");
        initComponents();
        setLocationRelativeTo(null);
        this.setVisible(true);
    }


    private void initComponents() {

        CodicePrenotazioneLabel = new  JLabel("Codice Prenotazione");
        ContantiButton = new  JButton("Paga in Contanti");
        CodicePrenotazioneText = new  JTextField();
        CCreditoButton = new  JButton("Paga con C.Credito");
        BancomatButton = new  JButton("Paga con Bancomat");

        setDefaultCloseOperation( JFrame.HIDE_ON_CLOSE);

        CodicePrenotazioneLabel.setText("Codice Prenotazione");

       this.setResizable(false);
       CodicePrenotazioneText.setPreferredSize(new Dimension(300,40));
       ContantiButton.setPreferredSize(new Dimension(150,40));
       CCreditoButton.setPreferredSize(new Dimension(150,40));
       BancomatButton.setPreferredSize(new Dimension(150,40));


        ContantiButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                ContantiButtonActionPerformed(evt);
            }
        });

        CCreditoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                CCreditoButtonActionPerformed(evt);
            }
        });

        BancomatButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                BancomatButtonActionPerformed(evt);
            }
        });

        Container CPane = this.getContentPane();
        JPanel westPanel = new JPanel ();
        JPanel southPanel = new JPanel ();

        westPanel.setLayout(new GridLayout(1,2));
        southPanel.setLayout(new GridLayout(1,3));

        westPanel.add(CodicePrenotazioneLabel);
        westPanel.add(CodicePrenotazioneText);

        southPanel.add(ContantiButton);
        southPanel.add(CCreditoButton);
        southPanel.add(BancomatButton);

        CPane.add(westPanel,BorderLayout.WEST);
        CPane.add(southPanel,BorderLayout.SOUTH);

        pack();
        this.setVisible(true);
    }

    private void ContantiButtonActionPerformed(ActionEvent evt) {

        String codiceRichiesta=CodicePrenotazioneText.getText();
        String statoVeicolo="";
        Double quantita=0.0;
       try{
            String queryOperazione = String.format("SELECT * FROM OPERAZIONE_ESEGUITA WHERE CODICE_RICHIESTA = '%s'",codiceRichiesta );
            ResultSet rs = DBOfficina.getIstance().getStm().executeQuery(queryOperazione);
            
            while(rs.next()){
               statoVeicolo = rs.getString(8);
               quantita=rs.getDouble(3);
            }
            if(statoVeicolo.equals("IN_USCITA")){
            Pagamento p=new PagamentoContanti(quantita,codiceRichiesta);
            p.paga();
            this.dispose();
            }
            else{
                JOptionPane.showMessageDialog(null,"ERRORE: Impossibile procedere al pagamento,codice inserito non valido");
                CodicePrenotazioneText.setText("");
            }
       }
       
       catch(SQLException e){
               JOptionPane.showMessageDialog(null,"ERRORE: Impossibile procedere al pagamento,codice inserito non valido");
                CodicePrenotazioneText.setText("");
               }
               
    }

private void CCreditoButtonActionPerformed(ActionEvent evt) {      
                                    
        String codiceRichiesta=CodicePrenotazioneText.getText();
        String statoVeicolo="";
        Double quantita=0.0;
       try{
            String queryOperazione = String.format("SELECT * FROM OPERAZIONE_ESEGUITA WHERE CODICE_RICHIESTA = '%s'",codiceRichiesta );
            ResultSet rs = DBOfficina.getIstance().getStm().executeQuery(queryOperazione);
            
            while(rs.next()){
               statoVeicolo = rs.getString(8);
               quantita=rs.getDouble(3);
            }
            if(statoVeicolo.equals("IN_USCITA")){
            Pagamento p=new PagamentoCCredito(quantita,codiceRichiesta);
            p.paga();
            this.dispose();
            }
            else{
                JOptionPane.showMessageDialog(null,"ERRORE: Impossibile procedere al pagamento,codice inserito non valido");
                CodicePrenotazioneText.setText("");
            }
       }
       
       catch(SQLException e){
               JOptionPane.showMessageDialog(null,"ERRORE: Impossibile procedere al pagamento,codice inserito non valido");
                CodicePrenotazioneText.setText("");
               }

    } 

private void BancomatButtonActionPerformed(ActionEvent evt) {   
                                          
                String codiceRichiesta=CodicePrenotazioneText.getText();
        String statoVeicolo="";
        Double quantita=0.0;
       try{
            String queryOperazione = String.format("SELECT * FROM OPERAZIONE_ESEGUITA WHERE CODICE_RICHIESTA = '%s'",codiceRichiesta );
            ResultSet rs = DBOfficina.getIstance().getStm().executeQuery(queryOperazione);
            
            while(rs.next()){
               statoVeicolo = rs.getString(8);
               quantita=rs.getDouble(3);
            }
            if(statoVeicolo.equals("IN_USCITA")){
            Pagamento p=new PagamentoBancomat(quantita,codiceRichiesta);
            p.paga();
            this.dispose();
            }
            else{
                JOptionPane.showMessageDialog(null,"ERRORE: Impossibile procedere al pagamento,codice inserito non valido");
                CodicePrenotazioneText.setText("");
            }
       }
       
       catch(SQLException e){
               JOptionPane.showMessageDialog(null,"ERRORE: Impossibile procedere al pagamento,codice inserito non valido");
                CodicePrenotazioneText.setText("");
               }
               
    } 

    private  JButton BancomatButton;
    private  JButton CCreditoButton;
    private  JLabel CodicePrenotazioneLabel;
    private  JTextField CodicePrenotazioneText;
    private  JButton ContantiButton;

}
