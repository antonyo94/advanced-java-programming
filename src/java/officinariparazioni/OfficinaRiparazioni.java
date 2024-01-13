package officinariparazioni;

import java.awt.*;
import java.awt.event.*;
import  javax.swing.*;
import  javax.swing.event.*;
/**
 * Frame che viene mostrato ad un utente qualsiasi,sia un meccanico che ad un cliente
 * nel momento in cui accede al nostro programma,con la possibilità di identificarsi
 * attraverso la pressione dei rispettivi bottoni
 * @author antonio
 */
public class OfficinaRiparazioni extends JFrame {
    

    public OfficinaRiparazioni() {
        super("Benvenuto");
        initComponents();
        setLocationRelativeTo(null);
        this.setVisible(true);
    }


    private void initComponents() {

        this.setTitle("Benvenuto!");
        ClienteButton = new  JButton("Cliente");
        MeccanicoButton = new  JButton("Meccanico");
        LogoLabel = new  JLabel();
        AdminButton = new JButton("Admin");

        //setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE);

        ClienteButton.setPreferredSize(new Dimension(120,40));
        MeccanicoButton.setPreferredSize(new Dimension(120,40));
        AdminButton.setPreferredSize(new Dimension(120,40));
        
        Container CPane = this.getContentPane();
        JPanel westPanel = new JPanel();
        JPanel southPanel = new JPanel();
        
        westPanel.add(LogoLabel);
        
        southPanel.setLayout(new GridLayout(1,3));
        southPanel.add(ClienteButton);
        southPanel.add(MeccanicoButton);
        southPanel.add(AdminButton);

        CPane.add(westPanel,BorderLayout.WEST);
        CPane.add(southPanel,BorderLayout.SOUTH);
        
        AdminButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent evt){
                AdminButtonActionPerformed(evt);           
            }
        });
        
        ClienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                ClienteButtonActionPerformed(evt);
            }
        });


        MeccanicoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                MeccanicoButtonActionPerformed(evt);
            }
        });

        LogoLabel.setIcon(new  ImageIcon(getClass().getResource("/images/LOGO.jpg")));

        pack();
    }
    
    private void AdminButtonActionPerformed(ActionEvent evt){
        JPasswordField pwd = new JPasswordField(10);
        JOptionPane.showConfirmDialog(null, pwd,"Inserire Password",JOptionPane.OK_CANCEL_OPTION);
        if(DBOfficina.getIstance().controlPassword(pwd.getText())){
            AdminLocation ad = new AdminLocation();
        }
        else{
            JOptionPane.showMessageDialog(null,"ERRORE: La password da lei inserita non è valida");
        }
        
    }
    
    private void ClienteButtonActionPerformed(ActionEvent evt) {
      
        ReceptionCliente rc = new ReceptionCliente();
   
    }

    
    private void MeccanicoButtonActionPerformed(ActionEvent evt) {
        ReceptionMeccanico rc = new ReceptionMeccanico ();
 
    }

    private  JButton ClienteButton;
    private  JLabel LogoLabel;
    private  JButton MeccanicoButton;
    private JButton AdminButton;

}
