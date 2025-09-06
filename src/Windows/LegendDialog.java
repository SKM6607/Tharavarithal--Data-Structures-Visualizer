package Windows;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class LegendDialog extends JDialog {
    public LegendDialog(Window parent, String title, Map<String, Color> legend){
        super(parent,title);
        getContentPane().setBackground(Color.WHITE);
        setLayout(new GridLayout(legend.size(), 1,2,2));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        for (Map.Entry<String,Color> entry:legend.entrySet()){
            add(createDialogBox(entry.getKey(),entry.getValue()));
        }
        pack();
        setLocation(1800,600);
        setResizable(false);
        setAlwaysOnTop(true);
        setLocationRelativeTo(parent);
    }
    private @NotNull JPanel  createDialogBox(String text, Color color){
        JPanel field=new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel colorText=new JLabel(text);
        colorText.setFont(new Font(Font.SANS_SERIF,Font.BOLD,20));
        colorText.setBackground(Color.BLACK);
        colorText.setForeground(color);
        field.setBackground(Color.BLACK);
        field.add(colorText);
        return field;
    }
}
