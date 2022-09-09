package me.tapeline.hummingbird.windows.forms.editor;

import me.tapeline.hummingbird.Main;
import me.tapeline.hummingbird.configuration.Config;
import me.tapeline.hummingbird.expansions.filetype.AbstractFileType;
import me.tapeline.hummingbird.expansions.runcfg.RunConfiguration;
import me.tapeline.hummingbird.expansions.syntaxchecker.SyntaxTip;
import me.tapeline.hummingbird.filesystem.FS;
import me.tapeline.hummingbird.filesystem.project.Project;
import me.tapeline.hummingbird.menus.topmenu.FileTopMenu;
import me.tapeline.hummingbird.menus.topmenu.HelpTopMenu;
import me.tapeline.hummingbird.ui.*;
import me.tapeline.hummingbird.utils.MouseUtils;
import me.tapeline.hummingbird.utils.Utils;
import me.tapeline.hummingbird.windows.HFrame;
import me.tapeline.hummingbird.windows.dialog.wizards.common.Dialogs;
import me.tapeline.hummingbird.windows.dialog.wizards.runcfgeditor.RunCfgEditorDialog;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class EditorFrame extends HFrame {
    private JPanel root;
    private JButton refreshTree;
    private JTabbedPane tabbedPane1;
    private JList<String> problemList;
    private JList todoList;
    private JButton runButton;
    private JButton stopButton;
    private JButton flushButton;
    public JConsole console;
    private JLabel lastNotification;
    private JLabel versionLabel;
    private JClosableTabbedPane tabs;
    private JComboBox<RunConfiguration> runCfgCombo;
    private JScrollPane treePane;
    private JPanel crumbPanel;
    private JFileTree fileTree;
    private JButton runButton1;
    private JButton stopButton1;
    private JButton updateButton;
    private JLabel labelPath;
    private JButton editButton;
    private JPopupMenu treePopup;
    private List<EditorTab> editors = new ArrayList<>();

    public Project project;

    public EditorFrame(File projectRoot) {
        super("Hummingbird - " + projectRoot.getPath());
        EditorFrame that = this;

        project = new Project(projectRoot);

        versionLabel.setText(Main.fullVersion);

        fileTree.setProject(project);

        for (RunConfiguration cfg : Main.cfg.runConfigurations) {
            runCfgCombo.addItem(cfg);
        }

        labelPath.setText(projectRoot.getName() + " > ");

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RunCfgEditorDialog.dialog();
            }
        });

        runButton.addActionListener(e -> {
            RunConfiguration cfg = (RunConfiguration) runCfgCombo.getSelectedItem();
            if (cfg == null) return;
            Runtime r = Runtime.getRuntime();
            try {
                String cmd = placeholder(cfg.command);
                Process process = r.exec(cmd);
                console.bindToProcess(process);
            } catch (Exception ex) {
                Dialogs.error(
                        that,
                        "An error occured during execution of your run configuration",
                        ex.toString()
                );
            }
        });
        runButton1.addActionListener(runButton.getActionListeners()[0]);

        stopButton.addActionListener(e -> {
            console.boundProcess.destroy();
        });
        stopButton1.addActionListener(stopButton.getActionListeners()[0]);

        flushButton.addActionListener(e -> {
            console.setText("");
        });

        refreshTree.addActionListener(e -> {
            project.resolveFiles();
            fileTree.setModel(new DefaultTreeModel(project.fileSystem.toTree()));
        });
        fileTree.setComponentPopupMenu(treePopup);
        fileTree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (MouseUtils.isLeftDoubleClick(e)) {
                    File file = ((FileTreeNode) fileTree.getLastSelectedPathComponent()).file;
                    AbstractFileType aft = FS.getTypeForFile(file);
                    if (aft == null) return;
                    if (!aft.canOpen(file)) return;
                    boolean alreadyOpened = false;
                    for (Component tab : tabs.getComponents()) {
                        if (!(tab instanceof EditorTab)) continue;
                        if (((EditorTab) tab).file.getAbsolutePath().equals(file.getAbsolutePath())) {
                            alreadyOpened = true;
                            tabs.setSelectedComponent(tab);
                            break;
                        }
                    }
                    if (!alreadyOpened) {
                        if (aft.doCustomOpen(file)) {
                            aft.customOpen(that, fileTree, file);
                        } else {
                            String content = FS.readFile(file);
                            EditorTab tab = new EditorTab(that, file, content);
                            editors.add(tab);
                            tabs.addTab(file.getName(), new ImageIcon(aft.icon), tab);
                            tabs.setSelectedComponent(tab);
                            labelPath.setText(
                                    project.getRelativeToProjectRoot(file).toString().replace(
                                                    '/', '>'
                                    )
                            );
                        }
                    }
                }
            }
        });

        tabs.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (tabs.getTabCount() == 0)
                    labelPath.setText(
                            project.getRelativeToProjectRoot(projectRoot).toString().replace(
                                    '/', '>'
                            )
                    );
                else
                    labelPath.setText(
                            project.getRelativeToProjectRoot(
                                    ((EditorTab) tabs.getSelectedComponent()).file
                            ).toString().replace(
                                    '/', '>'
                            )
                    );
            }
        });

        new Thread(() -> {
            while (true) {
                Vector<String> problems = new Vector<>();
                for (Component c : tabs.getComponents()) {
                    if (c instanceof EditorTab) {
                        for (SyntaxTip syntaxTip : ((EditorTab) c).scrollPane.editor.tipList)
                            problems.add((syntaxTip.type == SyntaxTip.TipType.ERROR? "X  " :
                                syntaxTip.type == SyntaxTip.TipType.WARNING? "⚠  " :
                                syntaxTip.type == SyntaxTip.TipType.CODE_STYLE? "⚠✎ " :
                                syntaxTip.type == SyntaxTip.TipType.DEPRECATION? "⚠\uD83D\uDD6E " :
                                        "⚠ "
                            ) + syntaxTip.tip + " :" +
                                    ((EditorTab) c).file.getName() + ":"
                                    + syntaxTip.bounds.left);
                    }
                }
                that.problemList.setListData(problems);
                tabbedPane1.setTitleAt(1, "Problems (" + problems.size() + ")");
                try {
                    Thread.sleep(2500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

        JMenuBar topMenu = new JMenuBar();
        topMenu.add(new FileTopMenu(this, this.project.root));
        topMenu.add(new HelpTopMenu(this, this.project.root));
        setJMenuBar(topMenu);

        setContentPane(root);
        pack();
        setSize(640, 480);
        setVisible(true);
    }

    public void quit() {
        dispose();
        for (int i = 0; i < tabs.getTabCount(); i++) {
            JClosableTabbedPane.CloseButtonTab btn =
                    (JClosableTabbedPane.CloseButtonTab) tabs.getTabComponentAt(i);
            ((EditorTab) btn.tab).save();
        }
    }

    private String placeholder(String orig) throws Exception {
        orig = orig.replaceAll("%\\{projectFolder}%", project.root.getAbsolutePath());

        EditorTab tab = (EditorTab) tabs.getSelectedComponent();
        if (tab == null)
            orig = orig.replaceAll("%\\{filePath}%", "null");
        else
            orig = orig.replaceAll("%\\{filePath}%", tab.file.getAbsolutePath());

        try {
            Field[] fields = Main.cfg.getClass().getFields();
            for (Field field : fields) {
                Config annotation = field.getAnnotation(Config.class);
                if (annotation != null) {
                    String name = !annotation.configurationField().equals("")?
                            annotation.configurationField() :
                            field.getName();
                    orig = orig.replaceAll("%\\{cfg:"+name+"}%", field.get(Main.cfg).toString());
                }
            }
        } catch (IllegalArgumentException | IllegalAccessException exception) {
            throw new Exception("Cannot fulfill placeholders: \n\t" +
                    exception.getLocalizedMessage());
        }

        while (orig.contains("%{input:file}%")) {
            orig = orig.replace("%{input:file}%", Utils.safeToString(
                    Dialogs.file(this, project.root)
            ));
        }

        while (orig.contains("%{input:folder}%")) {
            orig = orig.replace("%{input:folder}%", Utils.safeToString(
                    Dialogs.directory(this, project.root)
            ));
        }

        while (orig.contains("%{input:string}%")) {
            orig = orig.replace("%{input:string}%", Utils.safeToString(
                    Dialogs.string(this, "Program Input")
            ));
        }

        orig = orig.replaceAll("%\\{cfg:[^}%]}%", "null");

        return orig;
    }

    @Override
    public void reloadFromConfig() {
        runCfgCombo.removeAllItems();
        for (RunConfiguration cfg : Main.cfg.runConfigurations) {
            runCfgCombo.addItem(cfg);
        }
    }
}
