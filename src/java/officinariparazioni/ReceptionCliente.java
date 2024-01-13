package officinariparazioni;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
/**
 * Frame che viene mostrato nel momento in cui un cliente si identifica nell'accesso
 * al programma,mediante una opportuna finestra che permette di selezionarlo come tale
 * 
 */

public class ReceptionCliente extends  JFrame {


    public ReceptionCliente() {
        super("Reception Cliente");
        initComponents();
        setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void initComponents() {

        NuovaRichiestaButton = new  JButton("Nuova Richiesta");
        AnnullaRichiestaButton = new  JButton("Annulla Richiesta");
        RitiraVeicoloButton = new  JButton("Ritira Veicolo");
        LogoLabel = new  JLabel();

        NuovaRichiestaButton.setPreferredSize(new Dimension(120,40));
        AnnullaRichiestaButton.setPreferredSize(new Dimension(120,40));
        RitiraVeicoloButton.setPreferredSize(new Dimension(120,40));

        setResizable(false);


        NuovaRichiestaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                NuovaRichiestaButtonActionPerformed(evt);
            }
        });


        AnnullaRichiestaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                AnnullaRichiestaButtonActionPerformed(evt);
            }
        });

        RitiraVeicoloButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                RitiraVeicoloButtonActionPerformed(evt);
            }
        });

        LogoLabel.setIcon(new  ImageIcon(getClass().getResource("/images/LOGO.jpg")));

        Container CPane = this.getContentPane();
        JPanel westPanel = new JPanel();
        JPanel southPanel = new JPanel();

        westPanel.add(LogoLabel);
        southPanel.setLayout(new GridLayout(1,3));
        southPanel.add(NuovaRichiestaButton);
        southPanel.add(AnnullaRichiestaButton);
        southPanel.add(RitiraVeicoloButton);

        CPane.add(westPanel,BorderLayout.WEST);
        CPane.add(southPanel,BorderLayout.SOUTH);

        pack();
    }

    private void NuovaRichiestaButtonActionPerformed(ActionEvent evt) {

        NuovaRichiesta nr =new NuovaRichiesta();
        nr.setVisible(true);
 
    }

    private void AnnullaRichiestaButtonActionPerformed(ActionEvent evt) {
    	
        /*AnnullaRichiesta ann = new AnnullaRichiesta();
        ann.setVisible(true);
        */
        String codice; 
        codice = JOptionPane.showInputDialog("Inserisci il codice della richiesta da annullare");
        Reception.getIstance().ritiraIntervento(codice);
    }

    private void RitiraVeicoloButtonActionPerformed(ActionEvent evt) {

        RitiroVeicolo rv = new RitiroVeicolo();
        rv.setVisible(true);

    }



    private  JButton AnnullaRichiestaButton;
    private  JLabel LogoLabel;
    private  JButton NuovaRichiestaButton;
    private  JButton RitiraVeicoloButton;

}
