/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icord.Graphics;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 *
 * @author USER
 */
public class Edge {
    private Vertex[] verts;
    
    public Edge(Vertex v1, Vertex v2){
        verts=new Vertex[2];
        verts[0]=v1;
        verts[1]=v2;
    }
    
    public void draw(GraphicsContext g){
        g.setStroke(Color.BLACK);
        g.strokeLine(verts[0].getRealX(), verts[0].getRealY(), verts[1].getRealX(),verts[1].getRealY());
    }
}
