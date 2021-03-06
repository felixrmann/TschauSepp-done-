package TschauSepp.view;

import TschauSepp.controller.GameController;
import TschauSepp.model.*;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

/**
 * The type Game view.
 *
 * @author Felix Mann
 * @version 1.0
 * @since 2020 -Juni-04
 */
public class GameView extends JPanel {
    private MainFrame mainFrame;
    private Kartenstapel kartenstapel;
    private Ablagestapel ablagestapel;
    private Vector<Spieler> spielerListe;
    private JPanel mainPanel, topPanel, botPanel, centerPanel, centerRightPanel, centerRightCardPanel, centerLeftPanel, centerLeftCardPanel, westPanel, playerPanel, movePanel, movePanelContent, cardPanel, sayPanel, sayPanelContent;
    private JScrollPane scrollPane;
    private JList<ImageIcon> cardList;
    private DefaultListModel<ImageIcon> defaultListModel;
    private JLabel topLabel, messageLabel;
    private JTextArea textArea;
    private JButton legenButton, ziehenButton, tschauButton, seppButton, exitButton;
    private Spieler currentSpieler, winner;
    private int spielerCntr;
    private Modus modus;

    /**
     * Instantiates a new Game view.
     *
     * @param mainFrame    the main frame
     * @param spielerListe the spieler liste
     * @param modus        the modus
     */
    public GameView(MainFrame mainFrame, Vector<Spieler> spielerListe, Modus modus) {
        this.mainFrame = mainFrame;
        this.spielerListe = spielerListe;
        this.modus = modus;

        kartenstapel = new Kartenstapel();
        ablagestapel = new Ablagestapel();
        setPlayerCards(spielerListe, kartenstapel);

        defaultListModel = new DefaultListModel<>();
        cardList = new JList<>(defaultListModel);
        scrollPane = new JScrollPane(cardList);
        mainPanel = new JPanel();
        topPanel = new JPanel();
        botPanel = new JPanel();
        centerPanel = new JPanel();
        centerLeftPanel = new JPanel();
        centerRightPanel = new JPanel();
        centerLeftCardPanel = new JPanel();
        centerRightCardPanel = new JPanel();
        westPanel = new JPanel();
        playerPanel = new JPanel();
        movePanel = new JPanel();
        movePanelContent = new JPanel();
        cardPanel = new JPanel();
        sayPanel = new JPanel();
        sayPanelContent = new JPanel();
        topLabel = new JLabel();
        textArea = new JTextArea();
        legenButton = new JButton("Legen");
        ziehenButton = new JButton("Ziehen");
        tschauButton = new JButton("Tschau");
        seppButton = new JButton("Sepp");
        exitButton = new JButton("Exit");
        spielerCntr = 0;
        messageLabel = new JLabel();
        currentSpieler = spielerListe.get(spielerCntr);
        spielerCntr++;
        winner = null;
        ablagestapel.addKarte(kartenstapel.getRandomKarteAndRemove());

        init();


        //TODO modi volkommen implementieren
        if (modus.getModus() == 'k') {
            textArea.setVisible(false);
            mainFrame.setFrameSize(900, 880);
        }
        setAreaText();
        loadNetxtPlayerPanel();

    }

    private void setPlayerCards(Vector<Spieler> spielerListe, Kartenstapel kartenstapel) {
        for (Spieler spieler : spielerListe) {
            for (int j = 0; j < 7; j++) {
                spieler       .addKarte(kartenstapel.getRandomKarteAndRemove());
            }
        }
    }

    private void init() {
        add(mainPanel);

        mainPanel.setBackground(new Color(30,87,216));
        topPanel.setBackground(new Color(30,87,216));
        botPanel.setBackground(new Color(30,87,216));
        centerPanel.setBackground(new Color(30,87,216));
        centerLeftPanel.setBackground(new Color(30,87,216));
        centerRightPanel.setBackground(new Color(30,87,216));
        centerLeftCardPanel.setBackground(new Color(30,87,216));
        centerRightCardPanel.setBackground(new Color(30,87,216));
        westPanel.setBackground(new Color(30,87,216));
        playerPanel.setBackground(new Color(30,87,216));
        movePanel.setBackground(new Color(30,87,216));
        movePanelContent.setBackground(new Color(30,87,216));
        cardPanel.setBackground(new Color(30,87,216));
        sayPanelContent.setBackground(new Color(30,87,216));


        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setPreferredSize(new Dimension(890,840));
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(westPanel, BorderLayout.WEST);
        mainPanel.add(botPanel, BorderLayout.SOUTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        topPanel.setLayout(new BorderLayout());
        topPanel.setPreferredSize(new Dimension(900, 30));
        topPanel.add(textArea, BorderLayout.EAST);
        topPanel.add(messageLabel, BorderLayout.CENTER);

        textArea.setPreferredSize(new Dimension(383, 35));

        topLabel.setText(currentSpieler.getName());
        topLabel.setBackground(currentSpieler.getFarbe());
        topLabel.setOpaque(true);

        westPanel.setLayout(new BorderLayout(10, 10));
        JPanel panel1 = new JPanel();
        panel1.setPreferredSize(new Dimension(100, 50));
        panel1.setBackground(new Color(30,87,216));
        westPanel.add(panel1, BorderLayout.NORTH);
        westPanel.add(playerPanel, BorderLayout.CENTER);
        westPanel.add(panel1, BorderLayout.SOUTH);

        GridLayout grid = new GridLayout(spielerListe.size() - 1, 1);
        grid.setVgap(20);
        playerPanel.setLayout(grid);
        playerPanel.setBorder(BorderFactory.createEmptyBorder(10,0,10,0));

        botPanel.setLayout(new BorderLayout());
        botPanel.add(movePanel, BorderLayout.WEST);
        botPanel.add(cardPanel, BorderLayout.CENTER);
        botPanel.add(sayPanel, BorderLayout.EAST);

        movePanel.setLayout(new BorderLayout(5, 5));
        movePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        movePanel.add(movePanelContent, BorderLayout.CENTER);

        GridLayout moveGrid = new GridLayout(3, 1);
        moveGrid.setVgap(30);
        movePanelContent.setLayout(moveGrid);
        movePanelContent.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        movePanelContent.add(topLabel);
        movePanelContent.add(legenButton);
        movePanelContent.add(ziehenButton);

        cardPanel.setPreferredSize(new Dimension(650, 200));
        cardPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        cardPanel.add(scrollPane);

        cardList.setVisibleRowCount(1);
        cardList.setLayoutOrientation(JList.HORIZONTAL_WRAP);

        scrollPane.setPreferredSize(new Dimension(640, 190));
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        paintCurrentPlayerCards();

        sayPanel.setLayout(new BorderLayout(5, 5));
        sayPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        sayPanel.add(sayPanelContent, BorderLayout.CENTER);
        GridLayout sayGrid = new GridLayout(3, 1);
        sayGrid.setVgap(30);
        sayPanelContent.setLayout(sayGrid);
        sayPanelContent.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        sayPanelContent.add(tschauButton);
        sayPanelContent.add(seppButton);
        sayPanelContent.add(exitButton);

        centerPanel.setLayout(new GridLayout(1, 2));
        centerPanel.setPreferredSize(new Dimension(300, 580));
        centerPanel.add(centerLeftPanel);
        centerPanel.add(centerRightPanel);

        centerLeftPanel.setLayout(new BorderLayout(10, 10));
        centerLeftPanel.setBorder(BorderFactory.createEmptyBorder(80, 40, 80, 40));
        centerLeftPanel.add(centerLeftCardPanel, BorderLayout.CENTER);

        loadAblagestapel();

        centerRightPanel.setLayout(new BorderLayout(10, 10));
        centerRightPanel.setBorder(BorderFactory.createEmptyBorder(80, 40, 80, 40));
        centerRightPanel.add(centerRightCardPanel, BorderLayout.CENTER);

        URL path2 = getClass().getResource("../img/rueckseite.jpg");
        ImageIcon imageIcon2 = new ImageIcon(path2);
        Image image2 = imageIcon2.getImage();
        Image newimg2 = image2.getScaledInstance(275, 410, java.awt.Image.SCALE_SMOOTH);
        imageIcon2 = new ImageIcon(newimg2);
        JLabel img2 = new JLabel();
        img2.setIcon(imageIcon2);
        centerRightCardPanel.add(img2);

        legenButton.addActionListener(e -> {
            if (kartenstapel.getAnzKarten() > 5){
                ablagestapel.getAlleKartenAndEmpty();
            }

            if (!cardList.isSelectionEmpty()) {
                GameController.legenButtonController(currentSpieler, ablagestapel, currentSpieler.getHandkarten().get(cardList.getSelectedIndex()), this, mainFrame);
            } else {
                setMessageLabel("Du hasst keine Karte ausgewählt");
            }
        });

        ziehenButton.addActionListener(e -> GameController.ziehenButtonController(currentSpieler, kartenstapel, ablagestapel, this));

        tschauButton.addActionListener(e -> {
            GameController.tschauButtonController(currentSpieler);
        });

        seppButton.addActionListener(e -> {
            GameController.seppButtonController(currentSpieler);
        });

        exitButton.addActionListener(e -> GameController.exitButtonController(mainFrame));
    }

    /**
     * Load ablagestapel.
     */
    public void loadAblagestapel() {
        centerLeftCardPanel.removeAll();
        URL path1 = ablagestapel.getObersteKarte().getPath();
        ImageIcon imageIcon1 = new ImageIcon(path1);
        Image image1 = imageIcon1.getImage();
        Image newimg1 = image1.getScaledInstance(275, 410, java.awt.Image.SCALE_SMOOTH);
        imageIcon1 = new ImageIcon(newimg1);
        JLabel img1 = new JLabel();
        img1.setIcon(imageIcon1);
        centerLeftCardPanel.add(img1);
    }

    /**
     * Paint current player cards.
     */
    public void paintCurrentPlayerCards() {
        defaultListModel.removeAllElements();
        topLabel.setText(currentSpieler.getName());
        topLabel.setBackground(currentSpieler.getFarbe());
        topLabel.setOpaque(true);
        movePanel.setBackground(currentSpieler.getFarbe());
        cardPanel.setBackground(currentSpieler.getFarbe());
        sayPanel.setBackground(currentSpieler.getFarbe());
        for (int i = 0; i < currentSpieler.getHandkarten().size(); i++) {
            URL path = currentSpieler.getHandkarten().get(i).getPath();
            ImageIcon imageIcon = new ImageIcon(path);
            Image image = imageIcon.getImage();
            Image newimg = image.getScaledInstance(115, 170, java.awt.Image.SCALE_SMOOTH);
            imageIcon = new ImageIcon(newimg);
            defaultListModel.addElement(imageIcon);
        }
    }

    /**
     * Load netxt player panel.
     */
    public void loadNetxtPlayerPanel() {
        playerPanel.removeAll();
        for (int i = spielerListe.size() - 2; i >= 0; i--) {
            JPanel panel = new JPanel(new BorderLayout());
            JPanel namePanel = new JPanel();
            JLabel name = new JLabel(spielerListe.get(getNextPlayers(i)).getName() + ":    " + spielerListe.get(getNextPlayers(i)).getHandkarten().size());
            GridLayout grid = new GridLayout(2,1);
            grid.setVgap(10);
            JPanel midPanel = new JPanel(grid);
            JButton tschauButton = new JButton("Tschau");
            JButton sepButton = new JButton("Sepp");

            panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            panel.add(namePanel, BorderLayout.NORTH);
            panel.add(midPanel, BorderLayout.CENTER);

            namePanel.setBackground(spielerListe.get(getNextPlayers(i)).getFarbe());
            namePanel.add(name);

            midPanel.add(tschauButton);
            midPanel.add(sepButton);
            midPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

            tschauButton.addActionListener(e -> {
                GameController.tschauButtonController(currentSpieler);
            });

            sepButton.addActionListener(e -> {
                GameController.seppButtonController(currentSpieler);
            });

            playerPanel.add(panel);
        }
    }

    private void setAreaText() {
        StringBuilder s = new StringBuilder();
        for (Spieler spieler : spielerListe) {
            s.append(spieler.getName()).append("\t");
        }
        s.append("\n");
        for (Spieler spieler : spielerListe) {
            s.append(spieler.getPunktestand()).append("\t");
        }
        textArea.setText(s.toString());
    }

    /**
     * Gets spieler liste.
     *
     * @return the spieler liste
     */
    public Vector<Spieler> getSpielerListe() {
        return spielerListe;
    }

    /**
     * Next spieler.
     */
    public void nextSpieler() {
        ziehenButton.setEnabled(true);
        currentSpieler = spielerListe.get(spielerCntr);
        if (spielerCntr == (spielerListe.size() - 1)) {
            spielerCntr = 0;
        } else {
            spielerCntr++;
        }
    }

    /**
     * Skip spieler.
     */
    public void skipSpieler(){
        if (spielerCntr == (spielerListe.size() - 1)) {
            spielerCntr = 0;
        } else {
            spielerCntr++;
        }
    }

    /**
     * Get next spieler spieler.
     *
     * @return the spieler
     */
    public Spieler getNextSpieler(){
        int temp = spielerCntr;
        if ((temp + 1) >= (spielerListe.size() - 1)){
            temp = 0;
        } else {
            temp++;
        }
        return spielerListe.get(temp);
    }

    /**
     * Sets disabled.
     */
    public void setDisabled() {
        ziehenButton.setEnabled(false);
    }

    /**
     * Sets message label.
     *
     * @param message the message
     */
    public void setMessageLabel(String message) {
        messageLabel.setText(message);
        messageLabel.setFont(new Font(messageLabel.getFont().getName(), Font.PLAIN, 26));
        messageLabel.setForeground(Color.RED);
        java.util.Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                messageLabel.setText("");
            }
        }, 3000);
    }

    /**
     * Gets next players.
     *
     * @param index the index
     * @return the next players
     */
    public int getNextPlayers(int index) {
        Vector<Integer> nextNumbers = new Vector<>();
        int tempCntr = spielerCntr;
        nextNumbers.add(tempCntr);
        for (int i = 0; i < spielerListe.size() - 2; i++) {
            if (tempCntr == (spielerListe.size() - 1)) {
                tempCntr = 0;
            } else {
                tempCntr++;
            }
            nextNumbers.add(tempCntr);
        }
        return nextNumbers.get(index);
    }

    /**
     * Get kartenstapel kartenstapel.
     *
     * @return the kartenstapel
     */
    public Kartenstapel getKartenstapel(){
        return kartenstapel;
    }

    /**
     * Get modus modus.
     *
     * @return the modus
     */
    public Modus getModus(){return modus;}

    /**
     * Get main frame main frame.
     *
     * @return the main frame
     */
    public MainFrame getMainFrame(){return mainFrame;}

    /**
     * Set top card.
     *
     * @param karte the karte
     */
    public void setTopCard(Karte karte){
        ablagestapel.setObersteKarte(karte);
    }
}
