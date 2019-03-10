/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icord.Graphics;

import icord.IcoRD;
import icord.maths.Matrix;
import icord.player.Camera;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 *
 * @author USER
 */
class Vertex {
    private float x;
    private float y;
    private float z;
    private Camera cam;
    public float getX(){
        return x;
    }
    public float getY(){
        return y;
    }
    public float getZ(){
        return z;
    }
    
    public Vertex(float x, float y, float z, Camera cam){
        this.x=x;
        this.y=y;
        this.z=z;
        this.cam= cam;
    }
    public void draw(GraphicsContext g){
        g.setStroke(Color.LIGHTGOLDENRODYELLOW);
        g.strokeLine(getRealX(), getRealY(), getRealX()+1, getRealY()+1);
    }
    
    public int getRealX(){
        double[] values = rotateStuff();
        return (int)IcoRD.Width/2+(int)(values[0]*values[2]);
    }
    public int getRealY(){
        double[] values = rotateStuff();
        return (int)IcoRD.Height/2+(int)(values[1]*values[2]);
    }
    public double getSemiRealZ(){
        double[] values = rotateStuff();
        return 1/(values[2]/(3*(IcoRD.Height+IcoRD.Width)));
    }
    
    private double[] rotateStuff(){
        double newx=x - cam.getX();
        double newy=y - cam.getY();
        double newz=z - cam.getZ();
        
        double[] rot1 = IcoRD.player.rotate2dX(newx, newz);
        newx=rot1[0];
        newz=rot1[1];

        double[] rot2 = IcoRD.player.rotate2dY(newy, newz);
        newy=rot2[0];
        newz=rot2[1];
        
        if(newz>0){
            newz = (IcoRD.Height+IcoRD.Width)/(newz*3);
        }
        return new double[]{newx,newy,newz};
    }
    public void move(float x, float y, float z){
        this.x+=x;
        this.y+=y;
        this.z+=z;
    }
    protected void rotate(double angleX, double angleY,float x, float y, float z){
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
