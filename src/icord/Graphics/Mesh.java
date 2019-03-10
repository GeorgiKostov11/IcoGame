/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icord.Graphics;

import icord.drawnobjects.IDrawObj;
import icord.human.Human;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javafx.scene.canvas.GraphicsContext;

/**
 *
 * @author USER
 */
public class Mesh {
    ArrayList<Cube> cubes;
    float size;
    
    public Mesh(){
        cubes = new ArrayList<Cube>();
        size=2;
    }
    public void setSize(float size){
        this.size = size;
    }
    public void draw(GraphicsContext g){
        for(Cube c:cubes){
            c.draw(g);
        }
    }
    
    
    public void orderCubes(){
        Collections.sort(cubes, new Comparator<Cube>(){
        public int compare(Cube a, Cube b){
                if(b.getOrderReference() - a.getOrderReference()<0){
                    return  -1;
                }else if(b.getOrderReference() - a.getOrderReference()>0){
                    return  1;
                }
                return 0;
            } 
        });
    }
    public void addCube(Cube cube){
        for(float i=-size;i<size+1;i+=size*2){
            Cube nextTo = getCube(cube.getX()+i,cube.getY(),cube.getZ());
            if(nextTo!=null){
                if(i==-size){
                    cube.setFaceVisibility(5, false);
                    nextTo.setFaceVisibility(4, false);
                }else{
                    cube.setFaceVisibility(4, false);
                    nextTo.setFaceVisibility(5, false);
                }
            }
            nextTo = getCube(cube.getX(),cube.getY()+i,cube.getZ());
            if(nextTo!=null){
                if(i==-size){
                    cube.setFaceVisibility(1, false);
                    nextTo.setFaceVisibility(0, false);
                }else{
                    cube.setFaceVisibility(0, false);
                    nextTo.setFaceVisibility(1, false);
                }
            }
            nextTo = getCube(cube.getX(),cube.getY(),cube.getZ()+i);
            if(nextTo!=null){
                if(i==-size){
                    cube.setFaceVisibility(3, false);
                    nextTo.setFaceVisibility(2, false);
                }else{
                    cube.setFaceVisibility(2, false);
                    nextTo.setFaceVisibility(3, false);
                }
            }
        }
        cubes.add(cube);
    }
    public void addCubes(Cube[] arr){
        for(Cube c:arr){
            this.cubes.add(c);
        }
    }
    public void addHuman(Human h){
        addCubes(h.getModel());
    }
    public void addObject(IDrawObj h){
        addCubes(h.getModel());
    }
    
    
    private Cube getCube(float x, float y, float z){
        for(int i=0;i<cubes.size();i++){
            if(cubes.get(i).getX() == x && cubes.get(i).getY()==y && cubes.get(i).getZ()==z){
                return cubes.get(i);
            }
        }
        return null;
    }
    
    public void setVisibility(int cap){
        for(Cube c:cubes){
            if(c.getOrderReference()>cap){
                c.setDisplay(false);
            }else{
                c.setDisplay(true);
            }
        }
    }
}
