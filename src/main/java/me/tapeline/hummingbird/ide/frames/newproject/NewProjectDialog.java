package me.tapeline.hummingbird.ide.frames.newproject;

import me.tapeline.carousellib.dialogs.Dialogs;
import me.tapeline.hummingbird.ide.Registry;
import me.tapeline.hummingbird.ide.expansion.project.AbstractProjectGenerator;
import me.tapeline.hummingbird.ide.expansion.project.ProjectCreationResult;
import me.tapeline.hummingbird.ide.expansion.project.ProjectGeneratorParamPanel;
import me.tapeline.hummingbird.ide.ui.tools.ProjectGeneratorsListRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

public class NewProjectDialog extends JDialog {

    private static String[] pages = {"chooseType", "paramSetup", "locationSetup"};

    private JPanel contentPane;
    private JButton buttonForward;
    private JButton buttonCancel;
    private JList<AbstractProjectGenerator> projectTypesList;
    private JTabbedPane paramPanelsPane;
    private JTextField projectNameField;
    private JTextField projectLocationField;
    private JLabel projectNameLabel;
    private JButton chooseButton;
    private JPanel slider;

    private int currentPage = 0;
    private HashMap<String, Object> params = new HashMap<>();
    private List<ProjectGeneratorParamPanel> addedPanels = new ArrayList<>();
    private AbstractProjectGenerator generator;
    private File result = null;

    public NewProjectDialog() {
        setContentPane(contentPane);
        setTitle("New project");
        setModal(true);
        getRootPane().setDefaultButton(buttonForward);
        buttonForward.addActionListener(e -> onOK());
        buttonCancel.addActionListener(e -> onCancel());
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
        contentPane.registerKeyboardAction(e -> onCancel(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        ((CardLayout) slider.getLayout()).show(slider, pages[currentPage]);
        buttonForward.setEnabled(false);

        projectTypesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        projectTypesList.setCellRenderer(new ProjectGeneratorsListRenderer());
        projectTypesList.addListSelectionListener(e ->
                buttonForward.setEnabled(projectTypesList.getSelectedValue() != null));
        projectTypesList.setListData(new Vector<>(Registry.projectGenerators));

        chooseButton.addActionListener(e -> {
            File projectHome = Dialogs.directory(NewProjectDialog.this, null);
            if (projectHome != null)
                projectLocationField.setText(projectHome.getAbsolutePath());
        });
        projectNameField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                projectNameLabel.setText(projectNameField.getText());
            }
            public void keyPressed(KeyEvent e) {
                projectNameLabel.setText(projectNameField.getText());
            }
            public void keyReleased(KeyEvent e) {
                projectNameLabel.setText(projectNameField.getText());
            }
        });
    }

    private void onOK() {
        switch (currentPage++) {
            case 0:
                generator = projectTypesList.getSelectedValue();
                paramPanelsPane.removeAll();
                ProjectGeneratorParamPanel[] panels = generator.getParamPanels();
                if (panels == null) {
                    currentPage = 2;
                    buttonForward.setText("Create");
                } else {
                    addedPanels.clear();
                    for (ProjectGeneratorParamPanel panel : panels) {
                        paramPanelsPane.addTab(panel.name(), panel);
                        addedPanels.add(panel);
                    }
                }
                ((CardLayout) slider.getLayout()).show(slider, pages[currentPage]);
                break;
            case 1:
                params.clear();
                for (ProjectGeneratorParamPanel panel : addedPanels)
                    params.putAll(panel.getConfiguredValues());
                buttonForward.setText("Create");
                ((CardLayout) slider.getLayout()).show(slider, pages[currentPage]);
                break;
            case 2:
                create();
                break;
        }
    }

    public void create() {
        ProjectCreationResult result = generator.createProject(projectNameField.getText(),
                new File(projectLocationField.getText()), params);
        if (!result.succeeded()) {
            Dialogs.warn(this, "Error on project creation", result.getMessage());
            return;
        }
        this.result = Paths.get(projectLocationField.getText(), projectNameField.getText()).toFile();
        dispose();
    }

    private void onCancel() {
        dispose();
    }

    public File getResult() {
        return result;
    }

}
