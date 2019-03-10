/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icord.player;

import icord.human.Human;
import icord.maths.Matrix;
import javafx.scene.input.KeyCode;

/**
 *
 * @author USER
 */
public class Camera {
    private float x;
    private float y;
    private float z;
    private double angleX;
    private double angleY;
    
    private float dt;
    public void setDeltaTime(float dt){
        this.dt=dt;
    }
    
    
    public Camera(float x, float y, float z){
        this.x = x;
        this.y = y;
        this.z = z;
        this.angleX = Math.PI;
        this.angleY = Math.PI/8-0.1;
        this.dt=1/60.0f;
    }
    
    protected double[] rotate2d(double x, double y, double rad){
        double s = Math.sin(rad);
        double c = Math.cos(rad);
        
        return new double[]{x*c-y*s,y*c+x*s};
    }
    
    
    public void update(KeyCode k){
        float step = dt*10;//dt*10;//1;
        
        double[] movement = new double[3];
        
        if(k==KeyCode.W){
            movement = new double[]{0,0,step};
        }
        if(k==KeyCode.S){
            movement = new double[]{0,0,-step};
        }
        if(k==KeyCode.A){
            angleX-=step/10;
            //movement = new double[]{-step,0,0};
        }
        if(k==KeyCode.D){
            angleX+=step/10;
            //movement = new double[]{step,0,0};
        }
        if(k==KeyCode.Q){
            this.y-=step;
        }
        if(k==KeyCode.E){
            this.y+=step;
        }
        
        Matrix xz = new Matrix(angleX,true);
        double[] res1=xz.MultiplyWithMatrix(movement);
        
        Matrix yz = new Matrix(angleY,false);
        double[] res2=yz.MultiplyWithMatrix(res1);
        
        this.x+=res2[0];
        this.z+=res2[2];
        //if(k==KeyCode.W || k==KeyCode.S || k==KeyCode.A || k==KeyCode.D || k==KeyCode.Q || k==KeyCode.E)

    }
    protected void move(Human h, KeyCode k){
        
        float step = dt*10;//dt*10;//1;
        
        double[] movement = new double[3];
        
        if(k==KeyCode.W){
            movement = new double[]{0,0,step};
        }
        if(k==KeyCode.S){
            movement = new double[]{0,0,-step};
        }
        if(k==KeyCode.A){
            h.rotate(dt/10, 0);
        }
        if(k==KeyCode.D){
            h.rotate(-dt/10, 0);
        }
        if(k==KeyCode.Q){
            h.move(0,-step,0);
        }
        if(k==KeyCode.E){
            h.move(0,step,0);
        }
        
        Matrix xz = new Matrix(angleX,true);
        double[] res1=xz.MultiplyWithMatrix(movement);
        
        Matrix yz = new Matrix(angleY,false);
        double[] res2=yz.MultiplyWithMatrix(res1);
        
        h.move((float)res2[0], 0, (float)res2[2]);
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
    protected double getAngleX(){
        return angleX;
    }
    protected double getAngleY(){
        return angleY;
    }
    
}
