package gui;

import data_classes.Program;

import javax.swing.*;
import java.util.Locale;

public class UpdateProgram extends JDialog{
    private JPanel mainPanel;
    private JTextField programNameInput;
    private JTextField programIDInput;
    private JRadioButton UNDERGRADUATERadioButton;
    private JRadioButton POSTGRADUATERadioButton;
    private JButton saveButton;
    private JButton cancelButton;
    private String name, id, type;
    private Program program;
    

    public UpdateProgram(String id, String name, String type) {

        setModal(true);
        getRootPane().setDefaultButton(saveButton);
        programNameInput.setText(name);
        programIDInput.setText(id);
        if(type.equalsIgnoreCase("POSTGRADUATE")){
            POSTGRADUATERadioButton.setSelected(true);
        }else{
            UNDERGRADUATERadioButton.setSelected(true);
        }

        program = new Program(id, name, type);

        saveButton.addActionListener( e -> {
            getProgram();
        });

        cancelButton.addActionListener(e -> {
            onCancel();
        });

        setContentPane(mainPanel);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

    }

    public Program getProgram(){

        setVisible(true);
        this.name = programNameInput.getText();
        this.id = programIDInput.getText().toUpperCase(Locale.ROOT);
        if(POSTGRADUATERadioButton.isSelected()) {
            this.type = "POSTGRADUATE";
        }else{
            this.type = "UNDERGRADUATE";
        }

        program = new Program(this.id, this.name, this.type);
        dispose();
        return program;

    }
    private void onCancel() {
        dispose();
    }

}
