package Windows;
import javax.swing.*;
import java.awt.*;
import MyShapes.MyArrow;
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
    private Node head;
    private MyArrow arrow;
    private static final int width=Toolkit.getDefaultToolkit().getScreenSize().width;
    private static final int height=Toolkit.getDefaultToolkit().getScreenSize().height;
    private final int nodeLength=150,nodeHeight=100;
    private final int SPACING=25;
    private final JButton[] button=new JButton[2];
    public LinkedList(){
        //setBlock
        {
            button[0]=new JButton("Append");
            button[1]=new JButton("Delete");
        }
        repaint();
        setBackground(Color.BLACK);
    }
    @Override
    protected void paintComponent(Graphics g1){
        super.paintComponent(g1);
        Graphics2D g=(Graphics2D) g1;
        drawGrid(g);
        drawNode(g,new Node(0,width/2,height/2));
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
        arrow=new MyArrow(nodeLength/2,2);
        g.setColor(Color.WHITE);
        g.fillRect(x,y,nodeLength,nodeHeight);
        g.setStroke(new BasicStroke(4f));
        g.setColor(Color.BLACK);
        g.drawLine((x+nodeLength)/2,y,(x+nodeLength)/2,y+nodeHeight);
        g.drawString(String.valueOf(value),(x+nodeLength)/4,(y+nodeLength)/2);
        arrow.draw(g,x+nodeLength,(y+nodeHeight)/2,Color.WHITE);
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
