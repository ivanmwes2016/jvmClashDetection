package gui;

import com.formdev.flatlaf.FlatDarculaLaf;
import data_classes.Module;
import data_classes.Program;
import persistence.ProgramHandler;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.Locale;

public class GUI extends JFrame {

    private JPanel mainPanel;
    private JPanel add_innerPanel;
    private JTextField programNameInput;
    private JPanel ProgramNamePanel;
    private JRadioButton undergraduateRadioButton;
    private JRadioButton postgraduateRadioButton;
    private JPanel programTypePanel;
    private JList<String> programsJList;
    private JList<String> modulesJList;
    private JButton addProgramButton;
    private JTextField programIDInput;
    private JComboBox<String> yearComboBox;
    private JComboBox<String> termComboBox;
    private JButton addModuleButton;
    private JPanel CenterPanel;
    private JPanel lowerPanel_1;
    private JPanel lowerPanel_2;
    private JPanel topPanel;
    private JPanel addProgramTab;
    private JScrollPane programsScrollPane;
    private JScrollPane modulesScrollPane;
    private JPanel moduleIDPanel;
    private JTextField moduleIDInput;
    private JPanel moduleNamePanel;
    private JTextField moduleNameInput;
    private JPanel moduleTypePanel;
    private JRadioButton compulsoryRadioButton;
    private JRadioButton optionalRadioButton;
    private JPanel programIDPanel;
    private JPanel ManageTab;
    private JPanel manage_innerPanel;
    private JLabel pidLabel;
    private JButton editProgramButton;
    private JButton deleteProgramButton;
    private JButton deleteAllProgramButton;
    private JButton editModuleButton;
    private JButton deleteModuleButton;
    private JButton deleteAllModuleButton;
    private JPanel programsPanel;
    private JPanel ModulesPanel;
    private JTabbedPane manageMainJpanel;
    private JPanel manageTopJpanel;
    private JPanel mProgramJpanel;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JComboBox comboBox3;
    private JComboBox comboBox4;
    private JComboBox comboBox5;
    private JButton ADDButton;
    private JButton DELETEButton;
    private JButton EDITButton;
    private JTable showTable;
    private Module module;
    private ProgramHandler handler;
    private String gPid = "none";

    public GUI(ProgramHandler handler) {

        super("Scheduling System");
        this.handler = handler;
        createTable(); //appends table to the GUI

        addProgramButton.setBorderPainted(true);

        DefaultListModel<String> programsListModel = new DefaultListModel<>();
        DefaultListModel<String> moduleListModel = new DefaultListModel<>();

        modulesJList.setModel(moduleListModel);
        programsJList.setModel(programsListModel);

        if(!handler.getLoadPrograms().invoke().isEmpty()){
            ArrayList<String> programs = handler.getLoadPrograms().invoke();
            programsListModel.addAll(programs);
        }

        ButtonGroup programTypeGroup = new ButtonGroup();
        programTypeGroup.add(undergraduateRadioButton);
        programTypeGroup.add(postgraduateRadioButton);
        undergraduateRadioButton.setSelected(true);

        ButtonGroup moduleTypeGroup = new ButtonGroup();
        moduleTypeGroup.add(compulsoryRadioButton);
        moduleTypeGroup.add(optionalRadioButton);
        compulsoryRadioButton.setSelected(true);

        addProgramButton.addActionListener(e -> {

            if(programNameInput.getText().isBlank() && programIDInput.getText().isBlank()){
                JOptionPane.showMessageDialog(this, "Name and ID cannot be blank");
            }else if (programNameInput.getText().isBlank() && !programIDInput.getText().isBlank()){
                JOptionPane.showMessageDialog(this, "Name cannot be blank");
            }else if (programIDInput.getText().isBlank() && !programNameInput.getText().isBlank()){
                JOptionPane.showMessageDialog(this, "ID cannot be blank");
            }else {
                String name = programNameInput.getText();
                String id = programIDInput.getText().toUpperCase(Locale.ROOT);
                String type = "UNDERGRADUATE";

                if (postgraduateRadioButton.isSelected()) {
                    type = "POSTGRADUATE";
                }

                Boolean saveSuccessful = handler.getSaveProgram().invoke(id, name, type);
                if (saveSuccessful) {
                    programsListModel.addElement("[" + id + "] " + name + " (" + type + ")");
                }

                programNameInput.setText("");
                programIDInput.setText("");
            }

        });


        addModuleButton.addActionListener(e -> {

            if(programsJList.isSelectionEmpty()){
                JOptionPane.showMessageDialog(this, "please select a program first to add modules into it");
            }
            else if(moduleNameInput.getText().isBlank() && moduleIDInput.getText().isBlank()){
                JOptionPane.showMessageDialog(this, "Name and ID cannot be blank");
            }else if (moduleNameInput.getText().isBlank() && !moduleIDInput.getText().isBlank()){
                JOptionPane.showMessageDialog(this, "Name cannot be blank");
            }else if (moduleIDInput.getText().isBlank() && !moduleNameInput.getText().isBlank()){
                JOptionPane.showMessageDialog(this, "ID cannot be blank");
            }else if (yearComboBox.getSelectedIndex() == 0 && termComboBox.getSelectedIndex() != 0){
                JOptionPane.showMessageDialog(this, "Please select year");
            }
            else if (yearComboBox.getSelectedIndex() != 0 && termComboBox.getSelectedIndex() == 0){
                JOptionPane.showMessageDialog(this, "Please select term");
            }else if (yearComboBox.getSelectedIndex() == 0 && termComboBox.getSelectedIndex() != 0){
                JOptionPane.showMessageDialog(this, "Please select year");
            }
            else if (yearComboBox.getSelectedIndex() == 0 && termComboBox.getSelectedIndex() == 0){
                JOptionPane.showMessageDialog(this, "Please select year and term");
            }
            else {
                String pid = pidLabel.getText();
                String mid = moduleIDInput.getText().toUpperCase(Locale.ROOT);
                String name = moduleNameInput.getText();
                String type = "COMPULSORY";
                String year = (String) yearComboBox.getSelectedItem(); //Casting, helps to convert Object to String, To string helps but causes a warning
                String term = (String) termComboBox.getSelectedItem();

                if (optionalRadioButton.isSelected()) {
                    type = "OPTIONAL";
                }

                if (moduleListModel.getElementAt(0).contains("Selected")) {
                    moduleListModel.clear();
                }

                assert year != null;
                assert term != null;

                Boolean saveSuccessful = handler.getSaveModule().invoke(pid, mid, name, type, year, term);
                if (saveSuccessful) {
                    moduleListModel.addElement(String.format("[%s-%s] %s_%s_%s (%s)", pid, mid, name, year, term, type));
                }

                moduleNameInput.setText("");
                moduleIDInput.setText("");
                yearComboBox.setSelectedIndex(0);
                termComboBox.setSelectedIndex(0);

            }

        });



        programsJList.addListSelectionListener(e -> {

            assert programsListModel.getSize() != 0;
            String pid = "PID";
            if(programsJList.getSelectedValue() != null) {
                pid = programsJList.getSelectedValue();
                int start = pid.indexOf("[") + 1;
                int end = pid.indexOf("]");
                pid = (String) pid.subSequence(start, end);

            pidLabel.setText(pid);
            gPid = pid;


            if(!handler.getLoadModules().invoke(pid).isEmpty()) {
                moduleListModel.clear();
                moduleListModel.addAll(handler.getLoadModules().invoke(pid));
            }
            else{
                moduleListModel.clear();
                moduleListModel.addElement("Selected Program has no modules associated with it yet.");
            }
            } else{
                moduleListModel.clear();
                pidLabel.setText(pid);
            }

        });




        programsJList.setSelectionModel(new DefaultListSelectionModel() {
            public void setSelectionInterval(int index0, int index1) {
                if (index0 == index1) {
                    if (isSelectedIndex(index0)) {
                        removeSelectionInterval(index0, index0);
                        return;
                    }
                }
                super.setSelectionInterval(index0, index1);
            }

            @Override
            public void addSelectionInterval(int index0, int index1) {
                if (index0 == index1) {
                    if (isSelectedIndex(index0)) {
                        removeSelectionInterval(index0, index0);
                        return;
                    }
                    super.addSelectionInterval(index0, index1);
                }
            }

        });


        // EDIT PROGRAM =================================================
        editProgramButton.addActionListener(e -> {

            if(!programsJList.isSelectionEmpty()) {

                int start, end; String name, pid, type;
                String str = programsJList.getSelectedValue();

                start = str.indexOf("[") + 1;
                end = str.indexOf("]");
                pid = (String) str.subSequence(start, end);

                start = str.indexOf("]")+2;
                end = str.indexOf(" (");
                name = (String) str.subSequence(start,end);

                start = str.indexOf("(")+1;
                end = str.indexOf(")");
                type = (String) str.subSequence(start,end);

                UpdateProgram editor = new UpdateProgram(pid, name, type);

                Program program = editor.getProgram();

                handler.getUpdateProgram().invoke(program, pid);
                int index = programsJList.getSelectedIndex();
                programsListModel.removeElementAt(index);
                programsListModel.add(index,
                        String.format("[%s] %s (%s)",
                                program.getId(), program.getName(), program.getType()));
                programsJList.setSelectedIndex(index);
            }
            else{
                JOptionPane.showMessageDialog(this, "Please select a program from the list above");
            }
        });


//        DELETE PROGRAM================================================

        deleteProgramButton.addActionListener(e -> {

            if(!programsJList.isSelectionEmpty()) {
                String pid = programsJList.getSelectedValue();
                int start = pid.indexOf("[") + 1;
                int end = pid.indexOf("]");
                pid = (String) pid.subSequence(start, end);

                handler.getDeleteProgram().invoke(pid);
                int index = programsJList.getSelectedIndex();
                programsListModel.removeElementAt(index);

            }else{
                JOptionPane.showMessageDialog(this, "Please select a program from the list above");
            }

        });
        deleteAllProgramButton.addActionListener(e -> {

            handler.getDeleteAllPrograms().invoke();
            programsListModel.clear();

        });


//        EDIT BUTTON===========================================================
        editModuleButton.addActionListener(e -> {

            if(!modulesJList.isSelectionEmpty() && !modulesJList.getSelectedValue().contains("Selected")) {
                int start, end;
                String name, pid, type, mid, term, year;
                String str = modulesJList.getSelectedValue();

                start = str.indexOf("[") + 1;
                end = str.indexOf("-");
                pid = (String) str.subSequence(start, end);

                start = str.indexOf("-") + 1;
                end = str.indexOf("]");
                mid = (String) str.subSequence(start, end);

                start = str.indexOf("]") + 2;
                end = str.indexOf("_");
                name = (String) str.subSequence(start, end);

                start = str.indexOf("_") + 1;
                end = start + 5;
                year = (String) str.subSequence(start, end);

                start = end + 2;
                end = start + 6;
                term = (String) str.subSequence(start, end);
                System.out.println(term);

                start = str.indexOf("(") + 1;
                end = str.indexOf(")");
                type = (String) str.subSequence(start, end);

                UpdateModule editor = new UpdateModule(pid, mid, name, type, year, term);
                module = editor.getModule();
                handler.getUpdateModule().invoke(module, mid);
                int index = modulesJList.getSelectedIndex();
                moduleListModel.removeElementAt(index);
                moduleListModel.add(index,
                        String.format("[%s-%s] %s_%s_%s (%s)",
                                module.getPid(), module.getMid(),
                                module.getName(), module.getYear(),
                                module.getTerm(), module.getType()));
            }else{
                JOptionPane.showMessageDialog(this, "Please select a module from the list above");
            }
        });

//        DELETE MODULE BUTTON EVENT LISTENER===============================================================
        deleteModuleButton.addActionListener(e -> {

            if(!modulesJList.isSelectionEmpty() && !modulesJList.getSelectedValue().contains("Selected")) {
                int start, end;
                String mid;
                String str = modulesJList.getSelectedValue();

                start = str.indexOf("-") + 1;
                end = str.indexOf("]");
                mid = (String) str.subSequence(start, end);

                handler.getDeleteModule().invoke(mid);
                int index = modulesJList.getSelectedIndex();
                moduleListModel.removeElementAt(index);

                if(handler.getLoadModules().invoke(gPid).isEmpty()) {
                    moduleListModel.addElement("Selected Program has no modules associated with it yet.");
                }
            }else{
                JOptionPane.showMessageDialog(this, "Please Select a module from the list above");
            }

        });

        deleteAllModuleButton.addActionListener(e -> {

            if(!programsJList.isSelectionEmpty()) {
                if(!moduleListModel.getElementAt(0).toLowerCase().contains("selected")){
                    int start, end;
                    String pid;
                    String str = programsJList.getSelectedValue();

                    start = str.indexOf("[") + 1;
                    end = str.indexOf("]");
                    pid = (String) str.subSequence(start, end);

                    handler.getDeleteAllModules().invoke(pid);
                    moduleListModel.clear();
                    moduleListModel.addElement("Selected Program has no modules associated with it yet.");
                }else{
                    JOptionPane.showMessageDialog(this, "Selected program has no modules");
                }

            }else{
                JOptionPane.showMessageDialog(this, "Please Select a program you wish to delete modules from");
            }

        });
    }

    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel( new FlatDarculaLaf());
        } catch( Exception ex ) {
            System.err.println( "Failed to initialize LaF" );
        }
        SwingUtilities.invokeLater(() -> {

                GUI app = new GUI(new ProgramHandler());
                app.setContentPane(app.mainPanel);
                app.pack();
                app.setLocationRelativeTo(null);
                app.setDefaultCloseOperation(EXIT_ON_CLOSE);
                app.setVisible(true);


        });
    }

    private void createTable() {
        showTable.setModel(new DefaultTableModel(null,
                new String[]{"Day", "0800", "0900", "1000", "1100", "1200", "1300", "1400", "1500"}));

    }
}
