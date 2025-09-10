import Sound.BackgroundMusic;
import Windows.LegendDialog;
import Windows.LinkedList;
import Windows.LoadingPage;
import Windows.Sorting;

import javax.swing.*;
import java.awt.*;

public class Main {
    static final Thread backgroundMusicThread = new Thread(new BackgroundMusic("src/Sound/Granius.wav"));
    private static final int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width, screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
    private static final String DEFAULT = "LoadingPage";
    private static final String LINKED_LIST = "Linked List";
    private static final String INSERTION_SORTING = "Insertion Sort";
    private static final String SELECTION_SORTING = "Selection Sort";
    private static final String BUBBLE_SORT = "Bubble Sort";
    private static final String QUICK_SORT = "Quick Sort";
    private static final String[] iterator = {SELECTION_SORTING, INSERTION_SORTING, BUBBLE_SORT, QUICK_SORT};
    private static final JMenuBar menuBarMain = new JMenuBar();
    private static final JMenu sortingMenu = new JMenu("Sorting");
    private static final JMenu linkedListMenu = new JMenu("Linked List");
    private static final JMenuItem[] sortingMenuItems = new JMenuItem[4];
    private static final JMenuItem linkedListMenuItem = new JMenuItem(LINKED_LIST);
    private static final CardLayout cardLayout = new CardLayout();
    private static final JPanel cardPanel = new JPanel(cardLayout);
    private static final JPanel linkedListPanel = new LinkedList();
    private static final LoadingPage loadingPage = new LoadingPage();
    static {
        menuBarMain.setVisible(false);
        for (int i = 0; i < sortingMenuItems.length; i++) {
            sortingMenuItems[i] = new JMenuItem(iterator[i]);
            cardPanel.add(new Sorting(screenWidth, screenHeight, iterator[i]), iterator[i]);
            sortingMenu.add(sortingMenuItems[i]);
            closeChildWindows();
        }
        cardPanel.add(linkedListPanel, LINKED_LIST);
        cardPanel.add(loadingPage, DEFAULT);
        linkedListMenu.add(linkedListMenuItem);
        menuBarMain.add(sortingMenu);
        menuBarMain.add(linkedListMenu);
    }
    static {
        for (JMenuItem item : sortingMenuItems) {
            item.addActionListener(_ -> cardLayout.show(cardPanel, item.getText()));
        }
        linkedListMenuItem.addActionListener(_ -> cardLayout.show(cardPanel, linkedListMenu.getText()));
        loadingPage.returnControlOfLoadButton().addActionListener(_ -> {
            menuBarMain.setVisible(true);
            cardLayout.show(cardPanel, SELECTION_SORTING);
        });
    }
    private static void closeChildWindows() {
        for (Window window : Window.getWindows()) {
            if (window instanceof LegendDialog) {
                window.dispose();
            }
        }
        try {
            Thread.sleep(10);
        } catch (InterruptedException _) {
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame jFrame = new JFrame("VAGAI");
            jFrame.setSize(screenWidth, screenHeight);
            jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            backgroundMusicThread.start();
            jFrame.add(cardPanel, BorderLayout.CENTER);
            jFrame.setLayout(cardLayout);
            jFrame.setJMenuBar(menuBarMain);
            cardLayout.show(cardPanel, DEFAULT);
            jFrame.setVisible(true);
        });
    }

}