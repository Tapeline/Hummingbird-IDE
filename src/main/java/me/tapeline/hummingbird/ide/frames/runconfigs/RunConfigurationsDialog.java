package me.tapeline.hummingbird.ide.frames.runconfigs;

import me.tapeline.carousellib.data.Pair;
import me.tapeline.carousellib.dialogs.Dialogs;
import me.tapeline.carousellib.elements.dynamiclist.CDynamicList;
import me.tapeline.hummingbird.ide.Registry;
import me.tapeline.hummingbird.ide.exceptions.InvalidFieldsInEditorProvidedWarning;
import me.tapeline.hummingbird.ide.expansion.runconfigs.AbstractConfigurationRunner;
import me.tapeline.hummingbird.ide.expansion.runconfigs.RunConfiguration;
import me.tapeline.hummingbird.ide.expansion.runconfigs.RunConfigurationEditorPanel;
import me.tapeline.hummingbird.ide.frames.editor.EditorWindow;
import me.tapeline.hummingbird.ide.ui.tools.RunConfigurationListRenderer;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class RunConfigurationsDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JSplitPane splitPane;
    private JButton applyButton;
    private CDynamicList<RunConfiguration> configurations;

    private EditorWindow editorWindow;
    private List<Pair<RunConfiguration, RunConfigurationEditorPanel>> armedPanels = new ArrayList<>();
    private boolean cancelled = false;

    public RunConfigurationsDialog(EditorWindow editorWindow, List<RunConfiguration> data) {
        this.editorWindow = editorWindow;

        setTitle("Edit run configurations");
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(applyButton);

        buttonOK.addActionListener(e -> onOK());
        buttonCancel.addActionListener(e -> onCancel());
        applyButton.addActionListener(e -> onApply());

        configurations = new CDynamicList<>();
        Vector<RunConfiguration> listData = new Vector<>();
        for (RunConfiguration configuration : data) listData.add(configuration.clone());
        configurations.getList().setListData(listData);
        configurations.setOnAddCall(list -> {
            RunnerChoiceDialog dialog = new RunnerChoiceDialog();
            AbstractConfigurationRunner choice = dialog.show(Registry.configurationRunners);
            if (choice == null)
                return null;
            else
                return choice.getBlankConfiguration();
        });
        configurations.setOnRemoveCall(configuration -> true);
        configurations.getList().setCellRenderer(new RunConfigurationListRenderer());
        splitPane.setLeftComponent(configurations);
        splitPane.setRightComponent(null);
        splitPane.setResizeWeight(0.5);
        configurations.getList().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        configurations.getList().addListSelectionListener(e -> {
            if (configurations.getList().getSelectedValue() == null) {
                splitPane.setRightComponent(null);
                return;
            }
            RunConfiguration selectedConfiguration = configurations.getList().getSelectedValue();
            RunConfigurationEditorPanel editorPanel = null;
            for (Pair<RunConfiguration, RunConfigurationEditorPanel> panelPair : armedPanels)
                if (panelPair.getA() == selectedConfiguration)
                    editorPanel = panelPair.getB();
            if (editorPanel == null) {
                AbstractConfigurationRunner runner = Registry.getConfigurationRunner(
                        selectedConfiguration.getRunner());
                if (runner == null) {
                    Dialogs.error(RunConfigurationsDialog.this, "Error",
                            "Unknown runner type for " + selectedConfiguration);
                    return;
                }
                editorPanel = runner.constructConfigurationEditor(selectedConfiguration);
                armedPanels.add(new Pair<>(selectedConfiguration, editorPanel));
            }
            splitPane.setRightComponent(editorPanel);
        });

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        contentPane.registerKeyboardAction(
                e -> onCancel(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT
        );
    }

    private void onOK() {
        onApply();
        dispose();
    }

    private void onCancel() {
        cancelled = true;
        dispose();
    }

    private void onApply() {
        for (Pair<RunConfiguration, RunConfigurationEditorPanel> panelPair : armedPanels) {
            AbstractConfigurationRunner runner = Registry.getConfigurationRunner(
                    panelPair.getA().getRunner());
            if (runner == null) {
                Dialogs.error(this, "Error", "Unknown runner type for " + panelPair.getA());
                return;
            }
            try {
                runner.applyEditorChanges(panelPair.getB(), panelPair.getA());
            } catch (InvalidFieldsInEditorProvidedWarning warning) {
                Dialogs.error(this, "Error", warning.toString());
                continue;
            }
            configurations.getList().updateUI();
        }
    }

    public List<RunConfiguration> getResult() {
        if (cancelled) return null;
        return new ArrayList<>(configurations.getListData());
    }

    public void dialog() {
        pack();
        setSize(640, 480);
        setLocationRelativeTo(null);
        setVisible(true);
    }

}
