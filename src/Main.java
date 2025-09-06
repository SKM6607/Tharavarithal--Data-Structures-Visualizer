import Sound.BackgroundMusic;
import Windows.LegendDialog;
import Windows.LoadingPage;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

public class Main {
    private static final int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width,
            screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
    static void closeChildWindows(){
        for(Window window: Window.getWindows()){
            if( window instanceof LegendDialog){
                window.dispose();
            }
        }
    }
    static Thread backgroundMusicThread=new Thread(new BackgroundMusic("src/Sound/Granius.wav"));
    public static void main(String[] args) {
        JFrame jFrame = new JFrame("VAGAI");
        jFrame.setSize(screenWidth, screenHeight);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Loading screen first
        LoadingPage page = new LoadingPage();
        jFrame.add(page);
        backgroundMusicThread.start();
        AtomicReference<Sorting> sorting = new AtomicReference<>();

        // Menu setup (but don’t add Sorting yet!)
        JMenuBar bar = new JMenuBar();
        JMenu menu = new JMenu("Algorithms");
        bar.add(menu);
        menu.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));

        JMenuItem[] items = new JMenuItem[4];
        String[] names = {"Selection Sort", "Insertion Sort", "Bubble Sort", "Quick Sort"};
        final boolean[] isSelected = {true, false, false, false};

        for (int i = 0; i < items.length; i++) {
            items[i] = new JMenuItem(names[i]);
            items[i].setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
            menu.add(items[i]);

            int copy = i;
            items[i].addActionListener(e -> {
                if (sorting.get() == null) return; // do nothing until Start is pressed

                // Update colors
                Arrays.fill(isSelected, false);
                isSelected[copy] = true;
                for (int j = 0; j < items.length; j++) {
                    if (isSelected[j]) {
                        items[j].setBackground(new Color(241, 96, 96));
                        items[j].setForeground(new Color(21, 104, 213, 255));
                        items[j].setOpaque(false);
                    } else {
                        items[j].setBackground(Color.WHITE);
                        items[j].setForeground(Color.BLACK);
                        items[j].setOpaque(true);
                    }
                }

                // Replace sorting panel
                closeChildWindows();
                jFrame.remove(sorting.get());
                sorting.set(new Sorting(screenWidth, screenHeight, e.getActionCommand()));
                jFrame.add(sorting.get());
                jFrame.revalidate();
                jFrame.repaint();
            });
        }

        // Apply initial menu item style
        items[0].setBackground(new Color(241, 96, 96));
        items[0].setForeground(new Color(21, 104, 213, 255));
        items[0].setOpaque(false);

        jFrame.setJMenuBar(bar);

        // Only load Sorting after button press
        JButton controlButton = page.returnControlOfLoadButton();
        controlButton.addActionListener(_ -> {
            jFrame.remove(page);
            sorting.set(new Sorting(screenWidth, screenHeight, "Selection Sort"));
            jFrame.add(sorting.get());
            jFrame.revalidate();
            jFrame.repaint();
        });

        jFrame.setVisible(true);
    }


}