package officinariparazioni;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
/**
 * Frame che viene mostrato nel momento in cui un meccanico si identifica nell'accesso
 * al programma,mediante una opportuna finestra che permette di selezionarlo come tale
 * 
 */

public class ReceptionMeccanico extends  JFrame {


    public ReceptionMeccanico() {
        super("Reception Meccanico");
        initComponents();
        setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void initComponents() {

        LogoLabel = new  JLabel();
        EseguiOperazioneButton = new  JButton("Esegui Operazione");
        NuovoMeccanicoButton = new  JButton("Nuovo Meccanico");

        setResizable(false);

        LogoLabel.setIcon(new  ImageIcon(getClass().getResource("/images/LOGO.jpg")));

        EseguiOperazioneButton.setPreferredSize(new Dimension(120,40));
        NuovoMeccanicoButton.setPreferredSize(new Dimension(120,40));

        Container CPane = this.getContentPane();
        JPanel westPanel = new JPanel();
        JPanel southPanel = new JPanel();

        westPanel.add(LogoLabel);
        southPanel.setLayout(new GridLayout(1,2));
        southPanel.add(EseguiOperazioneButton);
        southPanel.add(NuovoMeccanicoButton);

        CPane.add(westPanel,BorderLayout.WEST);
        CPane.add(southPanel,BorderLayout.SOUTH);

        EseguiOperazioneButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                EseguiOperazioneButtonActionPerformed(evt);
            }
        });

        NuovoMeccanicoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                NuovoMeccanicoButtonActionPerformed(evt);
            }
        });

        pack();

        
    }

    private void EseguiOperazioneButtonActionPerformed(ActionEvent evt) {

        EseguiOperazione op = new EseguiOperazione();
        op.setVisible(true);

    }

    private void NuovoMeccanicoButtonActionPerformed(ActionEvent evt) {

        Meccanico m = new Meccanico();
        m.nuovoMeccanico();
        
    }

    private  JButton EseguiOperazioneButton;
    private  JLabel LogoLabel;
    private  JButton NuovoMeccanicoButton;

}
