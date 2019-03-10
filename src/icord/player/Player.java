/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icord.player;

import icord.human.Human;
import javafx.scene.input.KeyCode;

/**
 *
 * @author USER
 */
public class Player {
    private Camera cam;
    private Human model;
    
    
    public Player(Camera camera, Human model){
        this.cam = camera;
        this.model = model;
    }
    
    public void update(KeyCode k){
        cam.update(k);
        cam.move(model,k);
    }
    
    public void setDeltaTime(float dt){
        cam.setDeltaTime(dt);
    }
    
    public Camera getCamera(){
        return cam;
    }
    public double[] rotate2dX(double x, double y){
        return cam.rotate2d(x, y, cam.getAngleX());
    }
    public double[] rotate2dY(double x, double y){
        return cam.rotate2d(x, y, cam.getAngleY());
    }
    
    public Human getModel(){
        return model;
    }
}
