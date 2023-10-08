package me.tapeline.hummingbird.ide.frames.welcome;

import me.tapeline.carousellib.configuration.exceptions.FieldNotFoundException;
import me.tapeline.carousellib.dialogs.Dialogs;
import me.tapeline.carousellib.elements.card.CCard;
import me.tapeline.carousellib.elements.cardlist.CCardList;
import me.tapeline.carousellib.elements.link.linkpanel.CLinkPanel;
import me.tapeline.carousellib.elements.link.linkpanel.LinkPanelActionEvent;
import me.tapeline.carousellib.elements.link.linkpanel.LinkPanelActionListener;
import me.tapeline.carousellib.icons.navigation.CPackageIcon;
import me.tapeline.hummingbird.ide.Application;
import me.tapeline.hummingbird.ide.exceptions.ProjectDirectoryException;
import me.tapeline.hummingbird.ide.expansion.files.standard.ProjectFileType;
import me.tapeline.hummingbird.ide.frames.AppWindow;
import me.tapeline.hummingbird.ide.frames.editor.EditorWindow;
import me.tapeline.hummingbird.ide.frames.newproject.NewProjectDialog;
import me.tapeline.hummingbird.ide.project.Project;
import me.tapeline.hummingbird.ide.ui.projectcard.HProjectCard;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class WelcomeWindow extends AppWindow {

    private JPanel root;
    private JButton newProjectButton;
    private JButton openProjectButton;
    private JButton preferencesButton;
    private JButton exitButton;
    private CCardList projectsList;
    private JPanel projectsListContainer;

    public WelcomeWindow() {
        super("Hummingbird");

        setContentPane(root);

        projectsList = new CCardList();
        projectsListContainer.setLayout(new BoxLayout(projectsListContainer, BoxLayout.PAGE_AXIS));
        projectsListContainer.add(projectsList);

        List<String> lastProjects = new ArrayList<>();
        try {
            lastProjects = (List<String>) Application.instance.getConfiguration()
                    .latestRun().getList("projectHistory");
        } catch (FieldNotFoundException ignored) {}

        for (String projectRoot : lastProjects) {
            projectsList.addCard(new HProjectCard(this, projectsList, new File(projectRoot)));
        }

        exitButton.addActionListener(e -> dispose());
        openProjectButton.addActionListener(e -> {
            File projectsHome = new File("projects");
            try {
                projectsHome = new File((String) Application.instance.getConfiguration()
                        .latestRun().get("projectsHome"));
            } catch (FieldNotFoundException ignored) {}
            File root = Dialogs.directory(WelcomeWindow.this, projectsHome);
            if (root != null)
                openProject(root);
        });
        newProjectButton.addActionListener(e -> {
            NewProjectDialog dialog = new NewProjectDialog();
            dialog.setSize(600, 400);
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
            openProject(dialog.getResult());
        });

        setSize(600, 400);
        centerOnScreen();
        setVisible(true);
    }

    public void openProject(File file) {
        try {
            EditorWindow editorWindow = new EditorWindow(new Project(file));
            dispose();
        } catch (ProjectDirectoryException e) {
            Dialogs.exception("Error", "Exception during project opening", e);
        }
    }

}
