package Windows;
import javax.swing.*;
import java.awt.*;
import MyShapes.MyArrow;
import org.jetbrains.annotations.NotNull;

public class LinkedList extends JPanel {
    private static final class Node{
        private int data;
        public int x,y;
        Node(int data,int x,int y){
            this.data=data;
            this.x=x;
            this.y=y;
            this.next=null;
        }
        public Node next;
    }
    private final MyArrow arrow;
    private static final int width=Toolkit.getDefaultToolkit().getScreenSize().width;
    private static final int height=Toolkit.getDefaultToolkit().getScreenSize().height;
    private final int nodeLength=150,nodeHeight=100;
    private final int SPACING=25;
    private final JButton[] button=new JButton[2];
    public LinkedList(){
            GridLayout layout=new GridLayout(2,1);
            JPanel panel=new JPanel();       //setBlock
            JTextField textField = appendTextInput();
            JButton append=new JButton("Append");
            JButton pop=new JButton("Pop");
            JPanel sidePanel=new JPanel();
            GridLayout layoutSide=new GridLayout(1,2);
        sidePanel.setLayout(layoutSide);
        sidePanel.add(textField);
        sidePanel.add(append);
        panel.setLayout(layout);
        panel.add(sidePanel);
        panel.add(pop);
        panel.setPreferredSize(new Dimension(300,75));
        add(panel,BorderLayout.EAST);
        {
            button[0]=new JButton("Append");
            button[1]=new JButton("Delete");
            arrow=new MyArrow(nodeLength/2,20);
        }
        repaint();
        setBackground(Color.BLACK);
    }

    private static @NotNull JTextField appendTextInput() {
        JTextField textField=new JTextField();
        textField.setToolTipText("Enter the value for the next node(Integer): ");
        textField.setInputVerifier(new InputVerifier() {
            @Override
            public boolean verify(JComponent input) {
                try {
                    Integer.parseInt(((JTextField)input).getText());
                    return true;
                } catch (NumberFormatException e) {
                    return false;
                }
            }
        });
        textField.setFont(new Font(Font.SANS_SERIF,Font.BOLD,15));
        return textField;
    }

    @Override
    protected void paintComponent(Graphics g1){
        super.paintComponent(g1);
        Graphics2D g=(Graphics2D) g1;
        drawGrid(g);
        drawNode(g,new Node(0,width/2-nodeLength/2,height/2-nodeHeight));
    }
    void drawGrid(Graphics2D g){
        g.setStroke(new BasicStroke(1f));
        for (int i = 0; i < height; i+=SPACING) {
            g.drawLine(0,i,width,i);
        }
        for (int i = 0; i < width; i+=SPACING) {
            g.drawLine(i,0,i,height);
        }
    }
    void drawNode(Graphics2D g,Node node){
        int x=node.x;
        int y=node.y;
        int value=node.data;
        g.setColor(Color.WHITE);
        g.fillRect(x,y,nodeLength,nodeHeight);
        g.setStroke(new BasicStroke(4f));
        g.setColor(Color.BLACK);
        g.drawLine(x+nodeLength/2,y,x+nodeLength/2,y+nodeHeight);
        g.setFont(new Font(Font.SANS_SERIF,Font.BOLD,20));
        g.drawString(String.valueOf(value),(x+nodeLength)/4,(y+nodeLength)/2);
        arrow.draw(g,x+nodeLength,y+nodeHeight/2,Color.YELLOW);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(()->{
            JFrame frame=new JFrame();
            frame.add(new LinkedList());
            frame.setSize(new Dimension(width,height));
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}
