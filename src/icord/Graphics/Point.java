/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icord.Graphics;

/**
 *
 * @author USER
 */
class Point {
    public float x;
    public float y;
    public Point(float x, float y){
        this.x=x;
        this.y=y;
    }
    public float distanceX(Point p){
        return this.x-p.x;
    }
}
