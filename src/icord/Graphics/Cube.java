/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icord.Graphics;

import icord.IcoRD;
import icord.maths.Matrix;
import icord.player.Camera;
import java.util.ArrayList;
import java.util.Comparator;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.Arrays;
import javafx.scene.shape.Polygon;
/**
 *
 * @author USER
 */
public class Cube {
    private Face[] faces;
    private float x;
    private float y;
    private float z;
    private float size;
    private Camera cam;
    private boolean display;
    private Vertex[] verts;
    
    public float getX(){
        return x;
    }
    public float getY(){
        return y;
    }
    public float getZ(){
        return z;
    }
    public void setDisplay(boolean display){
        this.display = display;
    }
    
    public Cube(float x, float y, float z,Camera cam){
        this.x=x;
        this.y=y;
        this.z=z;
        this.size=1;
        this.cam=cam;
        display = true;
        setFaces();
    }
    public Cube(float x, float y, float z,float size,Camera cam){
        this.x=x;
        this.y=y;
        this.z=z;
        this.size=size;
        this.cam=cam;
        display = true;
        setFaces();
    }
    private void setFaces(){
        verts = new Vertex[]{
                new Vertex(x+size, y+size, z+size,cam),
                new Vertex(x-size, y+size, z+size,cam),
                new Vertex(x-size, y+size, z-size,cam),
                new Vertex(x+size, y+size, z-size,cam),
                new Vertex(x-size, y-size, z-size,cam),
                new Vertex(x-size, y-size, z+size,cam),
                new Vertex(x+size, y-size, z+size,cam),
                new Vertex(x+size, y-size, z-size,cam)
        };
        
        faces = new Face[]{
            new Face(verts[0], verts[1], verts[2], verts[3], Color.BROWN, cam),//bottom
            new Face(verts[6], verts[5], verts[4], verts[7], Color.GREEN, cam),//top
            
            new Face(verts[0], verts[1], verts[5], verts[6], Color.LIME, cam),//back
            new Face(verts[2], verts[3], verts[7], verts[4], Color.DARKGOLDENROD, cam),//front
            
            new Face(verts[0], verts[3], verts[7], verts[6], Color.WHITE, cam),//right
            new Face(verts[2], verts[1], verts[5], verts[4], Color.TURQUOISE, cam),//left
        };
    }
    
    public void draw(GraphicsContext g){
        if(display){
            Arrays.sort(faces, new Comparator<Face>(){
                public int compare(Face a, Face b){ 
                    return b.getOrderReference() - a.getOrderReference()>0?1:-1; 
                } 
            });
            for(Face f:faces){//fa){
                f.draw(g);
            }
        }
    }
    
    public void setFaceVisibility(int index, boolean display){
        if(index<0 ||index>5){
            System.out.println("Error: The specified index of a face doesn't exist.Please check your index value again.  Line 96.");
        }else{
            faces[index].setDisplay(display);
        }
    }
    public void setFaceTexture(int index, Texture texture){
        if(index<0 ||index>5){
            System.out.println("Error: The specified index of a face doesn't exist.Please check your index value again.  Line 96.");
        }else{
            faces[index].setTexture(texture);
        }
    }
    public void setFaceColor(int index, Color color){
        if(index<0 ||index>5){
            System.out.println("Error: The specified index of a face doesn't exist.Please check your index value again.  Line 96.");
        }else{
            faces[index].setColor(color);
        }
    }
    public void setCubeColor(Color color){
        for(Face f:faces){
            f.setColor(color);
        }
    }
    public void setFaceEdges(int index, boolean display){
        if(index<0 ||index>5){
            System.out.println("Error: The specified index of a face doesn't exist.Please check your index value again.  Line 96.");
        }else{
            faces[index].setDisplayEdges(display);
        }
    }
    public void setCubeEdges(boolean display){
        for(Face f:faces){
            f.setDisplayEdges(display);
        }
    }
    
    public float getOrderReference(){
        float ex = this.x-(cam.getX());
        ex = ex*ex;
        float ey = this.y-(cam.getY());
        ey = ey*ey;
        float ez = this.z-(cam.getZ());
        ez = ez*ez;
        
        return ex+ey+ez;
    }
    
    public void move(float x, float y, float z){
        this.x+=x;
        this.y+=y;
        this.z+=z;
        for(Vertex v:verts){
            v.move(x,y,z);
        }
    }
    
    public void rotate(double angleX, double angleY,float x, float y,float z){
        for(int i=0;i<verts.length;i++){
            verts[i].rotate(angleX,angleY,x,y,z);
        }
        x=x-this.x;
        y=y-this.y;
        z=z-this.z;
        
        Matrix first = new Matrix(angleX,true);
        double[] res1 = first.MultiplyWithMatrix(new double[]{x,y,z});
        
        Matrix second = new Matrix(angleY,false);
        double[] res2 = second.MultiplyWithMatrix(res1);
        
        this.x+=x-res2[0];
        this.y+=y-res2[1];
        this.z+=z-res2[2];
    }
}