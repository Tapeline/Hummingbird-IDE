package me.tapeline.hummingbird.ide.frames.welcome;

import me.tapeline.carousellib.elements.card.CCard;
import me.tapeline.carousellib.elements.cardlist.CCardList;
import me.tapeline.carousellib.elements.link.linkpanel.CLinkPanel;
import me.tapeline.carousellib.icons.navigation.CPackageIcon;
import me.tapeline.hummingbird.ide.frames.AppWindow;

import javax.swing.*;

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

        CCard testCard = new CCard(new CPackageIcon(24), "Test",
                new CLinkPanel("Open", "Delete"));
        testCard.getTitle().setFont(testCard.getTitle().getFont().deriveFont(20F));
        projectsList.addCard(testCard);

        setSize(600, 400);
        centerOnScreen();
        setVisible(true);
    }

}
