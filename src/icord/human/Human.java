/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icord.human;

import icord.Graphics.Cube;
import icord.player.Camera;

/**
 *
 * @author USER
 */
public abstract class Human {
    protected Cube[] cubes;
    protected float x;
    protected float y;
    protected float z;
    protected Camera cam;
    
    protected double angleX;
    protected double angleY;
    
    public Human(float x, float y, float  z, Camera cam){
        this.x = x;
        this.y = y;
        this.z = z;
        this.angleX=0;
        this.angleY=0;
        this.cam=cam;
    }
    
    public void move(float x, float y, float z){
        this.x+=x;
        this.y+=y;
        this.z+=z;
        
        for(Cube c:cubes){
            c.move(x, y, z);
        }
    }
    
    public void rotate(double angleX, double angleY){
        for(Cube c:cubes){
            c.rotate(angleX, angleY,this.x,this.y,this.z);
        }
        this.angleX+=angleX;
        this.angleY+=angleY;  
    }
    public float getX(){
        return x;
    }
    public float getY(){
        return y;
    }
    public float getZ(){
        return z;
    }
    
    public Cube[] getModel(){
        return cubes;
    }
    
}
