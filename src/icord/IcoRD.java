/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icord;

import icord.Graphics.Texture;
import icord.Graphics.Cube;
import icord.player.Camera;
import icord.Graphics.Mesh;
import icord.drawnobjects.Desk;
import icord.human.*;
import icord.player.Player;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.FillRule;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 *
 * @author USER
 */
public class IcoRD extends Application{
    //Cube[] cubes;
    Mesh mesh;
    private Scene scene;
    public static int Width=500;
    public static int Height=500;
    public static Player player;
    private Human[] people;
    
    
    public static Texture[] textures;
    private int visibility;
    
    @Override
    public void start(Stage primaryStage) {
        visibility=3000;
        StackPane root = new StackPane();
        Canvas c = new Canvas(Width, Height);
        scene = new Scene(root, Width, Height);        
        root.getChildren().add(c);
        primaryStage.setTitle("ri100 4erede");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        
        Camera cam = new Camera(0,-10,0);
        //Camera pc = new Camera(0,-10,5);
        player = new Player(cam,new Hristo(0,-2.1f,0,cam));
        initMesh(cam);
        mesh.orderCubes();
        if(visibility>300){
            mesh.setVisibility(visibility);
        }
        ChangeListener<Number> stageSizeListener = (observable, oldValue, newValue) ->{
            Width = (int)scene.getWidth();
            Height= (int)scene.getHeight();
            
            c.setWidth(Width);    
            c.setHeight(Height);
            System.out.println("Height: " + scene.getHeight() + " Width: " + scene.getWidth());
            //coment;
        };

        scene.widthProperty().addListener(stageSizeListener);
        scene.heightProperty().addListener(stageSizeListener); 
        scene.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                player.update(event.getCode());
                mesh.orderCubes();
                if(visibility>300){
                    mesh.setVisibility(visibility);
                }
            }
        });
        AnimationTimer timer = new AnimationTimer() {
            long before=0;
            long dt;
            double sec=0;
            int frames=0;
            int prevFrames=0;
            @Override
            public void handle(long now) {
                if(before!=0){
                    dt = now-before;
                }
                before=now;
                frames++;
                sec+= dt/1000000000.0;
                
                GraphicsContext g = c.getGraphicsContext2D();
                g.setFillRule(FillRule.NON_ZERO);//not sure but I think this one's nice
                if(sec>1){
                    prevFrames=frames;
                    player.setDeltaTime(dt/1000000000.0f);
                    sec=0;
                    frames=0;
                }
                draw(g);
                drawFPS(g,prevFrames);
            }
        };
        timer.start();
    }

    
    private void draw(GraphicsContext g){
        g.setFill(Color.rgb(185, 185, 185, 0.4));
        g.fillRect(0, 0, Width, Height);
        mesh.draw(g);
    }
    private void drawFPS(GraphicsContext g, int frames){
        g.setFill(Color.BLACK);
        g.fillRect(30, 5, 100, 30);
        g.setFill(Color.WHITESMOKE);
        g.setFont(new Font("Verdana", 25));
        g.fillText("FPS:"+frames, 30, 30);
    }
    
    private static void initTextures(){
        Image img = null;
        try {
            img = new Image(new FileInputStream("../res/texture.png"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(IcoRD.class.getName()).log(Level.SEVERE, null, ex);
        }
        textures = new Texture[256];
        
        for(int i=0;i<16;i++){
            for(int j=0;j<16;j++){
                textures[i*16+j]=new Texture(img,j*8,i*8);
            }
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        initTextures();
        launch(args);
    }
    private void initMesh(Camera cam){
        
        mesh = new Mesh();
        for(int i=-20;i<20;i++){
            for(int j=-20;j<20;j++){
                Cube cub = new Cube(i*2,4,j*2,cam);
                cub.setCubeColor(Color.BURLYWOOD);
                //cub.setCubeEdges(true);
                mesh.addCube(cub);
            }
        }
        for(int i=-20;i<20;i++){
            for(int j=-20;j<20;j++){
                Cube cub = new Cube(i*2,-20,j*2,cam);
                cub.setCubeColor(Color.WHITE);
                //cub.setCubeEdges(true);
                mesh.addCube(cub);
            }
        }
        
        for(int i=-20;i<20;i++){
            for(int k=1;k>-10;k--){
                Cube cub = new Cube(-40,k*2,i*2,cam);
                cub.setCubeColor(Color.GRAY);
                if(k>-1){
                    cub.setCubeColor(Color.LIGHTGRAY);
                }
                //cub.setCubeEdges(true);
                mesh.addCube(cub);
            }
        }
        for(int i=-20;i<20;i++){
            for(int k=1;k>-10;k--){
                Cube cub = new Cube(40,k*2,i*2,cam);
                cub.setCubeColor(Color.GRAY);
                if(k>-1){
                    cub.setCubeColor(Color.LIGHTGRAY);
                }
                if(k<=-3 && k>-8 && (20-i)<=36 && (20-i)>=18){
                    cub.setCubeColor(Color.WHITESMOKE); 
                }
                if(k==-5 && i>=-11 && i<=-9){
                    Texture t=new Texture(textures[31+(-10-i)]);
                    t.rotateRight(1);
                    cub.setFaceTexture(5,t);
                }
                
                if(k==-4 && i>=-13 && i<=-11){
                    Texture t=new Texture(textures[34+(-12-i)]);
                    t.rotateRight(1);
                    cub.setFaceTexture(5,t);
                }
                //cub.setCubeEdges(true);
                mesh.addCube(cub);
            }
        }
        for(int i=-20;i<20;i++){
            for(int k=1;k>-10;k--){
                Cube cub = new Cube(i*2,k*2,-40,cam);
                cub.setCubeColor(Color.GRAY);
                if(k>-1){
                    cub.setCubeColor(Color.LIGHTGRAY);
                }
                if(k>=-4 && (20-i)<=11 && (20-i)>=8){
                    cub.setCubeColor(Color.WHITESMOKE);
                }
                //cub.setCubeEdges(true);
                mesh.addCube(cub);
            }
        }
        for(int i=-20;i<20;i++){
            for(int k=1;k>-10;k--){
                Cube cub = new Cube(i*2,k*2,40,cam);
                cub.setCubeColor(Color.GRAY);
                if(k>-1){
                    cub.setCubeColor(Color.LIGHTGRAY);
                }
                if(k<=-4 && k>-7 && ((i+20)%4==0 || (i+20)%4==1)){
                    cub.setCubeColor(Color.CYAN);
                }
                if(k==-3 && ((i+20)%4==0 || (i+20)%4==1)){
                    cub.setCubeColor(Color.LIME);
                }
                //cub.setCubeEdges(true);
                mesh.addCube(cub);
            }
        }
        
        mesh.addHuman(player.getModel());
        
        
        Hristo s = new Hristo(18,-2.1f,-18,cam);
        s.rotate(Math.PI,0);//Math.PI
        Joan j = new Joan(18,-2.1f,-6,cam);
        
        Sasho s2 = new Sasho(22,-2.1f,-12,cam);
        s2.rotate(Math.PI/2, 0);
        
        Petko p = new Petko(8,-2.1f,-6,cam);
        Boci b = new Boci(8,-2.1f,-18,cam);
        b.rotate(Math.PI, 0);
        
        Dani dani = new Dani(13,-2.1f,-6,cam);
       
        Gogo gogo = new Gogo(13,-2.1f,-18,cam);
        gogo.rotate(Math.PI, 0);
        
        people = new Human[]{s,s2,p,j,dani,b,gogo};
        for(Human h:people){
            mesh.addHuman(h);
        }
        
        
        Desk mainDesk = new Desk(24,0,24,3,cam);
        mainDesk.rotate(Math.PI/2, 0);
        mesh.addObject(mainDesk);
        
        for(int k=-1;k<2;k++){
            for(int i=0;i<4;i++){
                Desk d = new Desk(4-i*10,0,k*24,1,cam);
                d.rotate(Math.PI/2, 0);
                mesh.addObject(d);
            }
        }
    }
}