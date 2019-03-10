/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icord.Graphics;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

/**
 *
 * @author USER
 */
public class Texture {
    private Image img;
    private PixelReader pixelReader;
    private Color[][] colors;
    
    public Texture(Image img, int x,int y){
        PixelReader reader = img.getPixelReader();
        //System.out.println(x+"   "+y);
        this.img = new WritableImage(reader,x,y,8,8);
        pixelReader = this.img.getPixelReader();
        colors = new Color[8][8];
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                colors[i][j]=pixelReader.getColor(j,i);
            }
        }
    }
    public Texture(Texture t){
        this.colors = t.getColors();
    }
    //  0  0  0
    //  1  1  1
    //  2  2  2
    
    
    //  0  1  2
    //  0  1  2
    //  0  1  2
    
    //  2  1  0
    //  2  1  0
    //  2  1  0
    public void rotateLeft(int numberOfRotations){
        for(int k=0;k<numberOfRotations;k++){
            Color[][] cols=new Color[8][8];
            for(int i=0;i<8;i++){
                cols[i] = getColumn(i);
            }
            colors = cols;
        }
    }
    public void rotateRight(int numberOfRotations){
        for(int k=0;k<numberOfRotations;k++){
            Color[][] cols=new Color[8][8];
            for(int i=0;i<8;i++){
                cols[7-i] = getColumn(i);
            }
            colors = cols;
        }
    }
    public void flipVerical(){
        for(int i=0;i<8;i++){
            colors[i]= flipArray(colors[i]);
        }
    }
    private Color[] flipArray(Color[] arr){
        Color[] newarr = new Color[arr.length];
        for(int i=0;i<8;i++){
            newarr[7-i] = arr[i];
        }
        return newarr;
    }
    private Color[] getColumn(int id){
        Color[] column = new Color[8];
        for(int i=0;i<8;i++){
            column[i]=colors[i][id];
        }
        return column;
    }
    
    public Color getColor(int x,int y){
        return colors[x][y];
    }
    public Color[][] getColors(){
        return colors;
    }
    
}
