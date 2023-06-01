package me.tapeline.carousellib.test;

import com.formdev.flatlaf.FlatDarculaLaf;
import me.tapeline.carousellib.dialogs.Dialogs;
import me.tapeline.carousellib.elements.actionbar.CActionBar;
import me.tapeline.carousellib.elements.dynamiclist.CDynamicList;
import me.tapeline.carousellib.elements.link.linkpanel.CLinkPanel;
import me.tapeline.carousellib.elements.simpletree.CSimpleTree;
import me.tapeline.carousellib.elements.simpletree.model.CTreeBranch;
import me.tapeline.carousellib.elements.simpletree.model.CTreeLeaf;
import me.tapeline.carousellib.elements.simpletree.model.CTreeRoot;
import me.tapeline.carousellib.elements.slotpanel.CSimpleTab;
import me.tapeline.carousellib.elements.slotpanel.CSlotPanel;
import me.tapeline.carousellib.icons.CBundledIcon;
import me.tapeline.carousellib.icons.misc.CSymbolicIcon;
import me.tapeline.carousellib.icons.navigation.*;
import me.tapeline.carousellib.icons.state.CWarningIcon;
import me.tapeline.carousellib.utils.AdaptableColor;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {

        JFrame frame = new JFrame("Test frame");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel root = new JPanel();
        root.setLayout(new BoxLayout(root, BoxLayout.PAGE_AXIS));
        frame.add(root);
        FlatDarculaLaf.setup();
        CBundledIcon.darkMode = true;
        CBundledIcon.antialiasingByDefault = true;

        CActionBar navigationBar = new CActionBar();
        root.add(navigationBar);
        navigationBar.addButtonAction(
                null,
                new CLeftArrowIcon(16),
                ((action, component) -> {})
        );
        navigationBar.addButtonAction(
                null,
                new CRightArrowIcon(16),
                ((action, component) -> {})
        );
        navigationBar.addButtonAction(
                null,
                new CUpArrowIcon(16),
                ((action, component) -> {})
        );
        navigationBar.addButtonAction(
                null,
                new CDownArrowIcon(16),
                ((action, component) -> {})
        );
        navigationBar.addButtonAction(
                null,
                new CLeftIcon(16),
                ((action, component) -> {})
        );
        navigationBar.addButtonAction(
                null,
                new CRightIcon(16),
                ((action, component) -> {})
        );
        navigationBar.addButtonAction(
                null,
                new CUpIcon(16),
                ((action, component) -> {})
        );
        navigationBar.addButtonAction(
                null,
                new CDownIcon(16),
                ((action, component) -> {})
        );
        navigationBar.addButtonAction(
                null,
                new CPackageIcon(16),
                ((action, component) -> {})
        );
        navigationBar.addButtonAction(
                null,
                new CMenuIcon(16),
                ((action, component) -> {})
        );
        navigationBar.addButtonAction(
                null,
                new CCloseIcon(16, 16),
                ((action, component) -> {})
        );

        CActionBar stateBar = new CActionBar();
        root.add(stateBar);
        stateBar.addButtonAction(
                null,
                new CWarningIcon(16, 16),
                ((action, component) -> {})
        );

        CLinkPanel linkPanel = new CLinkPanel("Hello", "Action 2", "Close");
        linkPanel.addLinkListener(event -> {
            System.out.println(event.getLinkIndex());
        });
        root.add(linkPanel);

        CDynamicList<String> list = new CDynamicList<>(
                CDynamicList.CLEARABLE | CDynamicList.ASK_ON_CLEAR | CDynamicList.ORDERABLE
        );
        list.setOnAddCall(l -> Dialogs.string(root, "Input a string"));
        list.setOnRemoveCall(s -> !s.equals("persistent"));
        list.getListData().add("fff");
        root.add(list);

        /*CCardList cardList = new CCardList();
        cardList.addCard(new CCard(
                new CSymbolicIcon(
                        32, 32,
                        "IJ",
                        AdaptableColor.fromDark(
                                new Color(38, 93, 141)
                        ),
                        new Font("SansSerif", Font.BOLD, 20)
                ),
                "IntelliJ IDEA",
                new CLinkPanel(
                        "Open",
                        "Info",
                        "Delete"
                )
        ));
        cardList.addCard(new CCard(
                new CSymbolicIcon(
                        32, 32,
                        "PC",
                        AdaptableColor.fromDark(
                                new Color(32, 129, 51)
                        ),
                        new Font("SansSerif", Font.BOLD, 20)
                ),
                "PyCharm",
                new CLinkPanel(
                        "Open",
                        "Info",
                        "Delete"
                )
        ));
        cardList.addCard(new CCard(
                new CSymbolicIcon(
                        32, 32,
                        "RM",
                        AdaptableColor.fromDark(
                                new Color(141, 69, 38)
                        ),
                        new Font("SansSerif", Font.BOLD, 20)
                ),
                "RubyMine",
                new CLinkPanel(
                        "Open",
                        "Info",
                        "Delete"
                )
        ));
        cardList.addCard(new CCard(
                new CSymbolicIcon(
                        32, 32,
                        "RS",
                        AdaptableColor.fromDark(
                                new Color(119, 38, 141)
                        ),
                        new Font("SansSerif", Font.BOLD, 20)
                ),
                "ReSharper",
                new CLinkPanel(
                        "Open",
                        "Info",
                        "Delete"
                )
        ));
        root.add(cardList);*/

        CSlotPanel slotPanel = new CSlotPanel();
        root.add(slotPanel);
        slotPanel.addTab(new CWarningIcon(16, 16), "Problems", new CSimpleTab(
                new JLabel("No problems")
        ));
        slotPanel.addTab(new CSymbolicIcon(
                16, 16,
                "IJ",
                AdaptableColor.fromDark(
                        new Color(38, 93, 141)
                ),
                new Font("SansSerif", Font.BOLD, 12)
        ), "IntelliJ IDEA", new CSimpleTab(
                new JLabel("No intellij")
        ));

        CSimpleTree simpleTree = new CSimpleTree();
        simpleTree.setRootItem(new CTreeRoot(
                new CPackageIcon(16),
                "Packages",
                new CTreeBranch(
                        new CWarningIcon(16, 16),
                        "Problematic",
                        new CTreeLeaf("commons-io")
                )
        ));
        slotPanel.addTab(new CPackageIcon(16), "Packages", new CSimpleTab(
                simpleTree
        ));


        frame.setVisible(true);
    }

}
