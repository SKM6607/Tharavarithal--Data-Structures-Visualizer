package Windows;

import MyShapes.MyArrow;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class LinkedList extends JPanel {
    private static int width = Toolkit.getDefaultToolkit().getScreenSize().width;
    private static final int height = Toolkit.getDefaultToolkit().getScreenSize().height;
    private final MyArrow arrow;
    private final int nodeLength = 350, nodeHeight = 200;
    private final int SPACING = 25;
    private final JButton[] button = new JButton[2];
    ArrayList<Node> nodes = new ArrayList<>();
    private JPanel mainPanel;
    private final JButton append;
    private  final JButton pop;
    private final JTextField inputText;
    public LinkedList() {
        nodes.add(new Node(0, width / 2 - nodeLength / 2, height / 2 - nodeHeight / 2, true));
        //setBlock
        {
            GridLayout layout = new GridLayout(1, 2,0,10);
            button[0] = new JButton("Append");
            button[1] = new JButton("Delete");
            arrow = new MyArrow(nodeLength / 2, 20);
            inputText = appendTextInput();
            append = new JButton("APPEND");
            pop = new JButton("POP");
            append.setFont(getFont().deriveFont(15f));
            pop.setFont(getFont().deriveFont(15f));
            JPanel sidePanel = new JPanel(layout);
            sidePanel.add(inputText);
            sidePanel.add(append);
            sidePanel.setBackground(Color.BLACK);
            mainPanel=new JPanel(new GridLayout(2,1,5,10));
            mainPanel.add(sidePanel);
            mainPanel.add(pop);
            mainPanel.setBackground(Color.BLACK);
            mainPanel.setPreferredSize(new Dimension(350,75));
        }
        append.addActionListener(_ -> {
            InputVerifier verifier = inputText.getInputVerifier();
            if (verifier.verify(inputText)) {
                addNode(Integer.parseInt(inputText.getText()));
                inputText.setText("");
            }
        });
        repaint();
        setBackground(Color.BLACK);
    }

    private static @NotNull JTextField appendTextInput() {
        JTextField textField = new JTextField();
        textField.setToolTipText("Value for the next node (Integer)");
        textField.setInputVerifier(new InputVerifier() {
            @Override
            public boolean verify(JComponent input) {
                try {
                    Integer.parseInt(((JTextField) input).getText());
                    return true;
                } catch (NumberFormatException e) {
                    return false;
                }
            }
        });
        textField.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
        return textField;
    }

    @Override
    protected void paintComponent(Graphics g1) {
        super.paintComponent(g1);
        Graphics2D g = (Graphics2D) g1;
        drawGrid(g);
        for (Node node : nodes) {
            drawNode(g, node);
        }
    }

    void drawGrid(Graphics2D g) {
        g.setStroke(new BasicStroke(1f));
        for (int i = 0; i < height; i += SPACING) {
            g.drawLine(0, i, width, i);
        }
        for (int i = 0; i < width; i += SPACING) {
            g.drawLine(i, 0, i, height);
        }
    }

    void drawNode(Graphics2D g, Node node) {
        int x = node.x;
        int y = node.y;
        int value = node.data;
        g.setColor(Color.WHITE);
        g.fillRect(x, y, nodeLength, nodeHeight);
        g.setStroke(new BasicStroke(4f));
        g.setColor(Color.BLACK);
        g.drawLine(x + nodeLength / 2, y, x + nodeLength / 2, y + nodeHeight);
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
        g.drawString(String.valueOf(value), x + (float) nodeLength / 4, y + nodeLength / 3.5f);
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        g.drawString((node.nextAddr == null) ? "NULL" : node.nextAddr, x + nodeLength / 1.5f, y + nodeLength / 3.5f);
        g.setColor(Color.WHITE);
        g.drawString(node.ownAddress, x + nodeLength / 3, y - SPACING);
        if (!node.isLast) arrow.draw(g, x + nodeLength, y + nodeHeight / 2, Color.YELLOW);
    }

    void addNode(int value) {
        int x = nodes.getLast().x + 3*nodeLength/2 +SPACING;
        int y = nodes.getLast().y;
        Node node = new Node(value, x, y, true);
        nodes.getLast().isLast = false;
        nodes.getLast().next = node;
        nodes.getLast().nextAddr = node.ownAddress;
        nodes.add(node);
        int startX=nodes.getLast().x+ 2*nodeLength;
        if (startX > getPreferredSize().width) {
            width=startX;
            setPreferredSize(new Dimension(startX, getPreferredSize().height));
        }
        SwingUtilities.invokeLater(()-> {
            Rectangle newNormal = new Rectangle(node.x, node.y, 2 * nodeLength, nodeHeight);
            newNormal.x = node.x - (getParent().getWidth() - nodeLength) / 2;
            newNormal.width = getParent().getWidth();
            scrollRectToVisible(newNormal);
        });
        try {
            Thread.sleep(200);
            repaint();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    @NotNull
    public JPanel returnPanel(){
        return mainPanel;
    }
    private static final class Node {
        public final String ownAddress;
        public int x, y;
        public boolean isLast = false;
        public String nextAddr;
        public Node next;
        private int data;

        Node(int data, int x, int y, boolean isLast) {
            this.data = data;
            this.x = x;
            this.y = y;
            this.next = null;
            nextAddr = null;
            this.isLast = isLast;
            ownAddress = "0x" + Integer.toHexString(System.identityHashCode(this));
        }
    }
}
