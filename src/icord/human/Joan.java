/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icord.human;

import icord.IcoRD;
import icord.Graphics.Cube;
import icord.Graphics.Texture;
import icord.player.Camera;
import java.util.ArrayList;
import javafx.scene.paint.Color;

/**
 *
 * @author USER
 */
public class Joan extends Human{
    public Joan(float x, float y, float z, Camera cam){
        super(x,y,z,cam);
        makeModel();
    }
    private void makeModel(){
        float x=super.x;
        float y=super.y;
        float z=super.z;
        
        ArrayList<Cube> cubes = new ArrayList<>();
        cubes.add(createHead(x,y-2,z,1));
        cubes.add(createBody(x,y,z,1));
        cubes.add(createLegs(x,y+2,z,1));
        
        Cube armTop1 =createArm(x+1.5f,y+0.5f,z,0.5f);
        Cube armBottom1 = createArm(x+1.5f,y-0.5f,z,0.5f);
        
        armTop1.setFaceVisibility(0, true);
        armBottom1.setFaceVisibility(1, true);
        armTop1.setFaceVisibility(5, false);
        armBottom1.setFaceVisibility(5, false);
        
        
        Cube armTop2 = createArm(x-1.5f,y+0.5f,z,0.5f);
        Cube armBottom2 = createArm(x-1.5f,y-0.5f,z,0.5f);
        
        
        armTop2.setFaceVisibility(0, true);
        armBottom2.setFaceVisibility(1, true);
        armTop2.setFaceVisibility(4, false);
        armBottom2.setFaceVisibility(4, false);
        
        cubes.add(armTop1);
        cubes.add(armBottom1);
        cubes.add(armTop2);
        cubes.add(armBottom2);
        
        Cube[] arr = new Cube[cubes.size()];
        arr = cubes.toArray(arr);
        super.cubes = arr;
    }
    
    private Cube createHead(float x, float y, float z, float size){
        Cube head = new Cube(x,y,z,size,super.cam);
        
        Texture sa6o1 = new Texture(IcoRD.textures[16+7]);//+32
        Texture sa6o2 = new Texture(IcoRD.textures[16+8]);//+32
        Texture sa6o3 = new Texture(IcoRD.textures[16+8]);//+32
        Texture sa6o4 = new Texture(IcoRD.textures[16+9]);//+32
        Texture sa6o5 = new Texture(IcoRD.textures[16+10]);//+32
        sa6o1.rotateLeft(1);
        head.setFaceTexture(3, sa6o1);
        sa6o2.rotateLeft(1);
        head.setFaceTexture(4, sa6o2);
        sa6o3.rotateRight(1);
        head.setFaceTexture(5, sa6o3);
        sa6o4.rotateLeft(1);
        head.setFaceTexture(2, sa6o4);
        sa6o5.rotateLeft(1);
        head.setFaceTexture(1, sa6o5);
        head.setFaceVisibility(0, false);
        
        //head.setCubeEdges(true);
        
        return head;
    }
    private Cube createBody(float x, float y, float z, float size){
        Cube body = new Cube(x,y,z,size,super.cam);        
        body.setCubeColor(Color.web("#6CC4EE"));
        body.setFaceVisibility(0, false);
        body.setFaceVisibility(1, false);
        
        body.setCubeEdges(true);
        
        return body;
    }
    private Cube createLegs(float x, float y, float z, float size){
        Cube legs = new Cube(x,y,z,size,super.cam);
        legs.setCubeColor(Color.web("#05c"));
        legs.setFaceVisibility(0, false);
        legs.setFaceVisibility(1, false);
        
        legs.setCubeEdges(true);
        
        return legs;
    }
    private Cube createArm(float x, float y, float z, float size){
        Cube arm = new Cube(x,y,z,size,super.cam);
        arm.setCubeColor(Color.web("#5CB4DE"));
        arm.setFaceVisibility(0, false);
        arm.setFaceVisibility(1, false);
        
        arm.setCubeEdges(true);
        
        return arm;
    }
}
