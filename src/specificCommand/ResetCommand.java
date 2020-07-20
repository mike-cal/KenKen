package specificCommand;

import command.Command;

import javax.swing.*;

public class ResetCommand implements Command {

    private JTextField[][] griglia;
    private JTextField[][] oldGriglia;


    public ResetCommand(JTextField[][] griglia){
        this.griglia=griglia;
        oldGriglia = new JTextField[griglia.length][griglia.length];
        for(int i=0;i<griglia.length;i++) {
            for (int j = 0; j < griglia.length; j++) {
                oldGriglia[i][j] = new JTextField(griglia[i][j].getText());

            }
        }
    }

    @Override
    public boolean doIt() {

        for(int i=0;i<griglia.length;i++){
            for(int j=0;j<griglia.length;j++){
                griglia[i][j].setText("");
            }
        }
        return true;
    }

    @Override
    public boolean undoIt() {
        System.out.println(oldGriglia.length);
        for(int i=0;i<griglia.length;i++){
            for(int j=0;j<griglia.length;j++){
                griglia[i][j].setText(oldGriglia[i][j].getText());
            }
        }
        return true;
    }
}
