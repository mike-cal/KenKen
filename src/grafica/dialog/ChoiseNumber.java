package grafica.dialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ChoiseNumber extends JDialog {

    private int dimensione;
    private int x,y,sizex,sizey,locx,locy;
    private int nrows;

    private JButton[] numbers;
    private JPanel[] jPanels = new JPanel[2];
    private JPanel content=new JPanel();

    private int pressed=-1;


    public ChoiseNumber(int dimensione){

        super();
        setVisible(false);

        this.setUndecorated(true);
        this.dimensione=dimensione+1; //uno per cancellare
        setDimensioni();

        modificaComponenti();

        setAscoltatori();
        setDefaultCloseOperation(ChoiseNumber.DO_NOTHING_ON_CLOSE);

        setAlwaysOnTop(true);

        setVisible(true);
    }

    private void setAscoltatori(){
        Ascoltatore a=new Ascoltatore();

        this.addWindowListener(a);
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

    public int getPressed(){
        return pressed;
    }

    private void modificaComponenti(){

        Ascoltatore a = new Ascoltatore();


        numbers= new JButton[dimensione];

        for(int i=0;i<numbers.length;i++){
            String label=""+i;
            if(i==0){
                numbers[i]= new JButton("Remove");


            }
            else numbers[i]= new JButton(label);

            numbers[i].setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
            numbers[i].setOpaque(false);
            numbers[i].setFont(
                    numbers[i].getFont().deriveFont(
                            (i!=0)?18f:54f/dimensione
                    )
            );
            numbers[i].setName(label);
            numbers[i].addActionListener(a);
        }
        for(int i=0;i<jPanels.length;i++){
            jPanels[i]=new JPanel();
            jPanels[i].setBackground(Color.WHITE);
        }

        Container curr= getContentPane();


        //content.setLayout(new GridLayout(2,1));
        content.setBorder(BorderFactory.createLineBorder(Color.BLUE,2));
        content.setLayout(new GridLayout());

        //aggiunta tasto Ripristina (riazzera)
        jPanels[0].add(numbers[0]);
        content.add(jPanels[0]);
        jPanels[1].setLayout(new FlowLayout());
        //jPanels[1].setLayout(new GridLayout());
        for(int i=1;i<dimensione;i++){
            jPanels[1].add(numbers[i]);
        }
        content.add(jPanels[1]);

        curr.add(content);








    }

    private void annulla(){
        pressed=-1;
    }

    private class Ascoltatore extends WindowAdapter implements ActionListener {

        @Override
        public void windowDeactivated(WindowEvent e) {

            //annulla();
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton sorg=(JButton)e.getSource();
            pressed= Integer.parseInt(sorg.getName());
            dispose();
        }
    }

    public static void main(String[] args) {
        JFrame f= new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setBounds(300, 300, 300, 300);
        f.setVisible(true);
        f.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int pressed= new ChoiseNumber((9)).getPressed();
                System.out.println(pressed);
            }
        });
        //ChoiseNumber cn=new ChoiseNumber(9);



    }
}



