/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Camera;

/**
 *
 * @author Bailey
 */
public class Camera {
    public int x = 0;
    public int y = 0;
    public int rot = 0;
    public float scale = 1.0f;
    
    public Camera(int x, int y, int rot){
        this.x = x;
        this.y = y;
        this.rot = rot;
    }
    
}
