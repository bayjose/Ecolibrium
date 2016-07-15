/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity.Model;


/**
 *
 * @author Bailey
 */
public class Point{
    double x = 0;
    double y = 0;
    double z = 0;
    
    float textureRotation = 0;
    
    public Point(double x, double y){
        this.x = x;
        this.y = y;
    }
    
    public void setX(double x){
        this.x = x;
    }
    
    public void setY(double y){
        this.y = y;
    }
    
    public void setTextureRotation(float x){
        this.textureRotation = x;
    }
    
    public float getTextureRotation(){
        return this.textureRotation;
    }
    
    public double getX(){
        return this.x;
    }
    
    public double getY(){
        return this.y;
    }
    
}
