/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icord.drawnobjects;

import icord.Graphics.Cube;

/**
 *
 * @author USER
 */
public interface IDrawObj {
    public Cube[] getModel();
    public void rotate(double angleX, double angleY);
}
