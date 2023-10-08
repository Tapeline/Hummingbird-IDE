package me.tapeline.hummingbird.ide.ui.projectcard;

import me.tapeline.carousellib.configuration.exceptions.FieldNotFoundException;
import me.tapeline.carousellib.dialogs.Dialogs;
import me.tapeline.carousellib.dialogs.ExceptionDialog;
import me.tapeline.carousellib.elements.card.CCard;
import me.tapeline.carousellib.elements.cardlist.CCardList;
import me.tapeline.carousellib.elements.link.linkpanel.CLinkPanel;
import me.tapeline.carousellib.elements.link.linkpanel.LinkPanelActionEvent;
import me.tapeline.carousellib.elements.link.linkpanel.LinkPanelActionListener;
import me.tapeline.hummingbird.ide.Application;
import me.tapeline.hummingbird.ide.exceptions.ProjectDirectoryException;
import me.tapeline.hummingbird.ide.expansion.files.standard.ProjectFileType;
import me.tapeline.hummingbird.ide.frames.editor.EditorWindow;
import me.tapeline.hummingbird.ide.frames.welcome.WelcomeWindow;
import me.tapeline.hummingbird.ide.project.Project;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class HProjectCard extends CCard implements LinkPanelActionListener {

    private CLinkPanel linkPanel;
    private File root;
    private CCardList cardList;
    private WelcomeWindow welcomeWindow;

    public HProjectCard(WelcomeWindow welcomeWindow, CCardList cardList, File project) {
        super(ProjectFileType.getIcon(), project.getName());
        this.root = project;
        this.welcomeWindow = welcomeWindow;
        this.cardList = cardList;
        this.linkPanel = new CLinkPanel("Open", "Delete from history");
        linkPanel.addLinkListener(this);
        add(linkPanel, BorderLayout.CENTER);
        linkPanel.setBorder(new EmptyBorder(4, 0, 10, 0));
    }

    @Override
    public void action(LinkPanelActionEvent event) {
        if (event.getLinkIndex() == 0) {
            welcomeWindow.openProject(root);
        } else if (event.getLinkIndex() == 1) {
            cardList.removeCard(this);
            cardList.repaint();
            try {
                List<String> lastProjects = (List<String>) Application.instance.getConfiguration()
                        .latestRun().getList("projectHistory");
                int index = -1;
                for (int i = 0; i < lastProjects.size(); i++)
                    if (new File(lastProjects.get(i)).getAbsoluteFile().equals(root.getAbsoluteFile())) {
                        index = i;
                        break;
                    }
                if (index != -1)
                    lastProjects.remove(index);
                Application.instance.getConfiguration().latestRun().set("projectHistory", lastProjects);
            } catch (FieldNotFoundException ignored) {}
        }
    }

}
