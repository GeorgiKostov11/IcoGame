/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icord.drawnobjects;

import icord.Graphics.Cube;
import icord.player.Camera;
import java.util.ArrayList;
import javafx.scene.paint.Color;

/**
 *
 * @author USER
 */
public class Desk implements IDrawObj{
    float x;
    float y;
    float z;
    float size;
    float angleX;
    float angleY;
    Camera cam;
    Cube[] cubes;
    
    public Desk(float x,float y,float z,float size,Camera cam){
        this.x=x;
        this.y=y;
        this.z=z;
        this.size=size;
        this.cam = cam;
        angleX=0;
        angleY=0;
        makeModel();
    }
    
    private void makeModel(){
        ArrayList<Cube> cu = new ArrayList<Cube>();
        for(int i=0;i<16;i++){
            for(int k=0;k<8;k++){
                Cube c = new Cube(x+(i-8)*0.5f*size,y-size,z+(k-4)*0.5f*size,0.5f*size,cam);
                c.setCubeColor(Color.web("#BC9464"));
                c.setFaceVisibility(2,false);
                c.setFaceVisibility(3,false);
                c.setFaceVisibility(4,false);
                c.setFaceVisibility(5,false);
                c.setFaceColor(0,Color.web("#9C7444"));
                cu.add(c);
            }
        }
        for(int i=-1;i<2;i+=2){
            for(int j=-1;j<2;j+=2){
                for(int k=0;k<5;k++){
                    Cube c = new Cube(x+i*3.5f*size,y-size+k*0.5f*size,z+j*1.5f*size,0.25f*size,cam);
                    c.setCubeColor(Color.web("#000"));
                    c.setFaceVisibility(0,false);
                    c.setFaceVisibility(1,false);
                    cu.add(c);
                }
            }
        }
        Cube[] res=new Cube[cu.size()];
        res = cu.toArray(res);
        cubes=res;
    }
    
    @Override
    public Cube[] getModel(){
        return cubes;
    }
    
    @Override
    public void rotate(double angleX, double angleY){
        for(Cube c:cubes){
            c.rotate(angleX, angleY,this.x,this.y,this.z);
        }
        this.angleX+=angleX;
        this.angleY+=angleY;  
    }
}
