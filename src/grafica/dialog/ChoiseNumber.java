package grafica.dialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ChoiseNumber extends JDialog implements ActionListener{

    private int dimensione;
    private int x,y,sizex,sizey,locx,locy;
    private JPanel panel;
    private JButton[] numbers;
    private JButton remove;
    private JButton annulla;
    private String valore;

    public ChoiseNumber(JFrame owner,int dimensione){

        super(owner,true);
        setTitle("MODIFICA VALORE");
        panel= new JPanel();
        panel.setBackground(Color.white);
        setVisible(false);

        panel.setBorder(BorderFactory.createLineBorder(Color.BLUE,3));

        this.setUndecorated(true);
        this.dimensione=dimensione; //+ uno per cancellare
        setDimensioni();


        panel.setLayout(new GridLayout(dimensione+1,3));
        annulla= new JButton("Annulla");
        numbers= new JButton[dimensione];

        panel.add(annulla);
        annulla.addActionListener(this);
        panel.add(remove=new JButton("Remove"));
        remove.addActionListener(this);

        for(int i=0;i<numbers.length;i++){
            numbers[i]= new JButton(String.valueOf(i+1));
            //numbers[i].addActionListener(a);
            //numbers[i].setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
            panel.add(numbers[i]);
            numbers[i].addActionListener(this);

        }
        add(panel);

        valore= "";

        //setAscoltatori();
        setDefaultCloseOperation( JFrame.DO_NOTHING_ON_CLOSE );

        //setAlwaysOnTop(true);

        setVisible(true);
    }

    private void setDimensioni(){
        Point p=MouseInfo.getPointerInfo().getLocation();

        Dimension d=Toolkit.getDefaultToolkit().getScreenSize();

        x=d.width;
        y=d.height;

        sizex=(int)(x/8f);
        sizey=(int)(x/8f);

        locx=p.x;
        locy=p.y;

        setBounds(locx,locy,sizex,sizey);
    }

    public String getValore(){
        return valore;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==annulla){
            ChoiseNumber.this.dispose();
        }

        if(e.getSource() == remove){
            this.valore="";
            ChoiseNumber.this.dispose();
        }

        else{
            JButton button= (JButton)e.getSource();
            this.valore= button.getText();
            ChoiseNumber.this.dispose();
        }
    }



//funziona
    public static void main(String[] args) {
        JFrame f= new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setBounds(300, 300, 300, 300);
        f.setVisible(true);
        f.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                ChoiseNumber ch= new ChoiseNumber(f,6);
                System.out.println("valore: "+ch.getValore());

            }
        });

    }
}



