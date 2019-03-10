/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icord.Graphics;

import icord.IcoRD;
import icord.player.Camera;
import java.awt.geom.AffineTransform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;
/**
 *
 * @author USER
 */
public class Face {
    private Vertex[] verts;
    private Edge[] edges;
    private Color c;
    private boolean display;
    private boolean displayEdges;
    private Texture t;
    private Camera cam;
    
	
    public void setDisplay(boolean display){
        this.display = display;
    }
    public void setDisplayEdges(boolean display){
        this.displayEdges = display;
    }
    public void setColor(Color c){
        this.c=c;
    }
    public void setTexture(Texture t){
        this.t=t;
    }        
    public Face(Vertex v1, Vertex v2, Vertex v3, Vertex v4, Color c, Camera cam){
        init(v1,v2,v3,v4);
        this.c = c;
        this.cam = cam;
    }
	public Face(Vertex v1, Vertex v2, Vertex v3, Vertex v4, Camera cam){
        init(v1,v2,v3,v4);
        this.cam = cam;
    }
	public Face(Vertex v1, Vertex v2, Vertex v3, Vertex v4, Texture t, Camera cam){
        init(v1,v2,v3,v4);
        this.t = t;
        this.cam = cam;
    }
	private void init(Vertex v1, Vertex v2, Vertex v3, Vertex v4){
            verts = new Vertex[4];
            verts[0]=v1;//          v1------------p2
            verts[1]=v2;//          |
            verts[2]=v3;//                        |
            verts[3]=v4;//          v4------------v3
            display = true;  
            displayEdges=false;
            edges = new Edge[]{
                new Edge(v1,v2),
                new Edge(v2,v3),
                new Edge(v3,v4),
                new Edge(v1,v4),            
            };
            this.c = Color.DARKGOLDENROD;
	}
	
    public void draw(GraphicsContext g){
        if(display && onScreen()){
            if(t==null){
                g.setFill(c);
                g.fillPolygon(getXPoints(), getYPoints(), 4);
            }else{
                Point p1 = new Point(verts[0].getRealX(),verts[0].getRealY()); 
                Point p2 = new Point(verts[1].getRealX(),verts[1].getRealY());
                Point p3 = new Point(verts[2].getRealX(),verts[2].getRealY());
                Point p4 = new Point(verts[3].getRealX(),verts[3].getRealY());


                //Point[] edge1=breakDownLines(p1,p2);//          p1------------p2
                //Point[] edge2=breakDownLines(p4,p3);//          |
                Point[] edge3=breakDownLines(p1,p4);//                        |
                Point[] edge4=breakDownLines(p2,p3);//          p4------------p3

                g.setStroke(Color.BLACK);
                g.setFill(Color.LIGHTGREEN);
                Point[] prev=breakDownLines(edge3[0],edge4[0]);

                for(int i=1;i<9;i++){
                    //g.strokeLine(edge1[i].x, edge1[i].y, edge2[i].x,edge2[i].y);
                    //g.strokeLine(edge3[i].x, edge3[i].y, edge4[i].x,edge4[i].y);
                    Point[] curr=breakDownLines(edge3[i],edge4[i]);
                    for(int j=0;j<8;j++){
                        Color col = t.getColor(j,8-i);
                        if(col!=null && !col.equals(IcoRD.textures[15].getColor(0,0))){
                            g.setFill(col);
                            g.fillPolygon(new double[]{ prev[j].x,prev[j+1].x,curr[j+1].x,curr[j].x }, new double[]{ prev[j].y,prev[j+1].y,curr[j+1].y,curr[j].y}, 4);
                            //g.fillPolygon(new double[]{ prev[j].x-1,prev[j+1].x+1,curr[j+1].x+1,curr[j].x-1 }, new double[]{ prev[j].y+1,prev[j+1].y+1,curr[j+1].y-1,curr[j].y-1}, 4);
                        }
                    }
                    prev = curr;
                }
            }
            if(this.displayEdges){
                for(Edge e:edges){
                    e.draw(g);
                }
            }
        }
    }
    private Point[] breakDownLines(Point p1,Point p2){
        Point[] points = new Point[9];

        float stepX=(p2.x-p1.x)/8;
        float stepY=(p2.y-p1.y)/8;
        
        for(int i=0;i<9;i++){
            points[i]=new Point(p1.x+stepX*i,p1.y+stepY*i);
        }
        return points;
    }
    
    public double[] getXPoints(){
        double[] ret = new double[4];
        for(int i=0;i<4;i++){
            ret[i] = verts[i].getRealX();
        }
        return ret;
    }
    public double[] getYPoints(){
        double[] ret = new double[4];
        for(int i=0;i<4;i++){
            ret[i] = verts[i].getRealY();
        }
        return ret;
    }
    public Vertex[] getVerts(){
        return verts;
    }
    public boolean onScreen(){
        boolean on=true;
        for(Vertex v:verts){
            if(v.getSemiRealZ()<=0.1){
                on=false;
                break;
            }
        }
        return on;
    }
    
    public float getOrderReference(){
        float xes = 0;
        for(int i=0;i<4;i++){
            xes += verts[i].getX()-cam.getX();
        }
        xes *= xes;
        
        float yes = 0;
        for(int i=0;i<4;i++){
            yes += verts[i].getY()-cam.getY();
        }
        yes *= yes;
        
        float zes = 0;
        for(int i=0;i<4;i++){
            zes += verts[i].getZ()-cam.getZ();
        }
        zes *= zes;
        
        return xes+yes+zes;
    }
}
