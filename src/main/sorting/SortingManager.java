package main.sorting;

import javax.swing.*;
import java.awt.*;

import static main.interfaces.MacroInterface.*;

public final class SortingManager extends JPanel {
    private int currentNumberOfElements = 10;
    private final String currentAlgorithm;
    private JButton startButton;
    private JLabel label;
    private JSlider slider;
    private boolean isStart = true;
    private Thread thread;
    private Sorting sortingAlgorithm;

    public SortingManager(String menuOption) {
        currentAlgorithm = menuOption;
        switch (menuOption) {
            case INSERTION_SORTING -> setSortingAlgorithm(new InsertionSort(this));
            case BUBBLE_SORTING -> setSortingAlgorithm(new BubbleSort(this));
            case QUICK_SORTING -> setSortingAlgorithm(new QuickSort(this));
            default -> setSortingAlgorithm(new SelectionSort(this));
        }
        add(getUserPanel(), BorderLayout.WEST);
        setBackground(new Color(0xA0F29));
    }

    private void setSortingAlgorithm(Sorting sortingAlgorithm) {
        this.sortingAlgorithm = sortingAlgorithm;
    }

    private JPanel getUserPanel() {
        GridLayout layout = new GridLayout(1, 4);
        label = new JLabel("Current Array Elements: 5");
        label.setOpaque(true);
        label.setForeground(Color.WHITE);
        label.setBackground(Color.BLACK);
        int LIMIT = 50;
        slider = new JSlider(0, LIMIT, currentNumberOfElements);
        slider.addChangeListener(e -> {
            isStart = true;
            label.setText(String.format("Current Array Elements: %d", slider.getValue()));
            sortingAlgorithm.setBlocksByN(currentNumberOfElements = slider.getValue());
            sortingAlgorithm.initAnimation();
        });
        slider.setOpaque(false);
        slider.setBackground(Color.BLACK);
        JLabel algorithmName = new JLabel(currentAlgorithm);
        JPanel returnPanel = new JPanel();
        startButton = getButton(slider);
        returnPanel.setLayout(layout);
        returnPanel.add(label);
        returnPanel.add(slider);
        algorithmName.setForeground(Color.WHITE);
        algorithmName.setBackground(Color.BLACK);
        algorithmName.setOpaque(false);
        algorithmName.setPreferredSize(new Dimension(250, 50));
        label.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
        algorithmName.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
        returnPanel.add(algorithmName);
        returnPanel.add(startButton);
        returnPanel.setPreferredSize(new Dimension(1000, 50));
        returnPanel.setBackground(Color.BLACK);
        return returnPanel;
    }

    private JButton getButton(JSlider slider) {
        JButton startButton = new JButton("Start");
        startButton.addActionListener(e -> {
            if (isStart) {
                thread = new Thread(sortingAlgorithm::sort);
                slider.setEnabled(false);
                startButton.setEnabled(false);
                isStart = false;
                thread.start();
            }
        });
        startButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
        startButton.setSize(150, 50);
        return startButton;
    }

    void returnCtrl() {
        startButton.setEnabled(true);
        slider.setEnabled(true);
    }
}
