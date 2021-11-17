package gui;

import data_classes.Module;

import javax.swing.*;
import java.util.Locale;

public class UpdateModule extends JDialog {
    private JTextField moduleNameInput;
    private JTextField moduleIDInput;
    private JRadioButton COMPULSORYRadioButton;
    private JRadioButton OPTIONALRadioButton;
    private JButton saveButton;
    private JButton cancelButton;
    private JPanel mainPanel;
    private JComboBox yearComboBox;
    private JComboBox termComboBox;
    private JLabel pidLabel;
    private String name, mid, type, pid, term, year;
    private Module module;


    public UpdateModule(String pid, String mid, String name, String type, String year, String term) {

        setTitle("Editing Module");
        setModal(true);
        getRootPane().setDefaultButton(saveButton);

        this.pid = pid;
        moduleNameInput.setText(name);
        moduleIDInput.setText(mid);
        pidLabel.setText(pid);

        if(type.equalsIgnoreCase("OPTIONAL")){
            OPTIONALRadioButton.setSelected(true);
        }else{
            COMPULSORYRadioButton.setSelected(true);
        }

        if(year.contains("1")){
            yearComboBox.setSelectedIndex(0);
        }else if (year.contains("2")){
            yearComboBox.setSelectedIndex(1);
        }else{
            yearComboBox.setSelectedIndex(2);
        }

        if(term.contains("1")){
            termComboBox.setSelectedIndex(0);
        }else{
            termComboBox.setSelectedIndex(1);
        }

        saveButton.addActionListener( e -> {
            getModule();
        });

        cancelButton.addActionListener(e -> {
            onCancel();
        });

        setContentPane(mainPanel);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

    }

    public Module getModule(){

        setVisible(true);
        this.name = moduleNameInput.getText();
        this.mid = moduleIDInput.getText().toUpperCase(Locale.ROOT);
        if(OPTIONALRadioButton.isSelected()) {
            this.type = "OPTIONAL";
        }else{
            this.type = "COMPULSORY";
        }

        this.year = (String) yearComboBox.getSelectedItem();
        this.term = (String) termComboBox.getSelectedItem();

        module = new Module(pid, mid, name, type, year, term);
        dispose();
        return module;

    }
    private void  onCancel() {
        dispose();
    }
    
}
