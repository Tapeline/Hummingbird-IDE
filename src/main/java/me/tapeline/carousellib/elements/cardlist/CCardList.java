package me.tapeline.carousellib.elements.cardlist;

import me.tapeline.carousellib.elements.card.CCard;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CCardList extends JPanel {

    private List<CCard> cards;
    private JScrollPane scrollPane;
    private JPanel cardsPanel;
    private GridBagConstraints constraints;
    private GridBagConstraints fillerConstraints;

    public CCardList() {
        super();
        this.cards = new ArrayList<>();
        this.cardsPanel = new JPanel();
        //cardsPanel.setLayout(new BoxLayout(cardsPanel, BoxLayout.PAGE_AXIS));
        //cardsPanel.setAlignmentX(0);
        this.scrollPane = new JScrollPane(cardsPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(8);
        BorderLayout layout = new BorderLayout();
        setLayout(layout);

        cardsPanel.setLayout(new GridBagLayout());

        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.NORTH;
        constraints.weightx = 1;
        constraints.gridx = 0;

        fillerConstraints = new GridBagConstraints();
        fillerConstraints.fill = GridBagConstraints.VERTICAL;
        fillerConstraints.weighty = Integer.MAX_VALUE;

        add(scrollPane, BorderLayout.CENTER);
    }

    public void updateList() {
        for (Component component : cardsPanel.getComponents())
            cardsPanel.remove(component);
        for (CCard card : cards)
            cardsPanel.add(card, constraints);
        fillerConstraints.gridx = 0;
        fillerConstraints.gridy = cards.size();
        cardsPanel.add(Box.createVerticalGlue(), fillerConstraints);
    }

    public void addCard(CCard card) {
        cards.add(card);
        updateList();
    }

    public void removeCard(CCard card) {
        cards.remove(card);
        updateList();
    }

    public void clearCards() {
        cards.clear();
        updateList();
    }

    public List<CCard> getCards() {
        return cards;
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }

    public JPanel getCardsPanel() {
        return cardsPanel;
    }

}
