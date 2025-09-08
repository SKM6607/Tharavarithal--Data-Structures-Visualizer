package MyShapes;

import java.awt.*;

public class MyArrow {
    private int x, y,length, size;
    private Color color;
    public MyArrow(int length,int size) {
        this.x=this.y=0;
        this.size=size;
        this.length=length;
    }
    public void draw(Graphics2D g,int x,int y,Color color) {
        g.setColor(color);
        g.drawLine(x,y,x+length,y);
        g.fillPolygon(new Polygon(new int[]{x+length,x+length+size,x+length+size,x+length+2*size},new int[]{y,y+size,y-size,y},4));
    }
}
