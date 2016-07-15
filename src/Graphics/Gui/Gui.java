/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphics.Gui;

import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * @author Bailey
 */
public abstract class Gui {
    public Rectangle bounds;
    public boolean canBeOveridden = true;
        
    public Gui(int x, int y, int width, int height){
        this.bounds = new Rectangle(x, y, width, height);
    }
    
    public void onOpen(){
        return;
    }
    
    public void onClose(){
        return;
    }
    
    public abstract void tick();
    public abstract void render(Graphics g);
    public abstract boolean onClick(Rectangle rect);
}
