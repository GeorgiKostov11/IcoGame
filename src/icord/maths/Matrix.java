/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icord.maths;

/**
 *
 * @author USER
 */
public class Matrix {
    private double[][] matrix;
    
    public Matrix(double angle, boolean Xrot){
        double s = Math.sin(angle);
        double c = Math.cos(angle);
        if(Xrot){matrix = new double[][]{
                {c,0,s},
                {0,1,0},
                {-s,0,c},
            };
        }else{
            matrix = new double[][]{
                {1,0,0},
                {0,c,-s},
                {0,s,c},
            };
        }
    }
    
    public double[] MultiplyWithMatrix(double[] mult){
        double[] res = new double[3];
        for(int i=0;i<3;i++){
            res[i]=0;
            for(int j=0;j<3;j++){
                res[i]+=matrix[i][j]*mult[j];
            }
        }
        return res;
    }
}
