/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * @author Bailey
 */
public class WireConnectionPoint extends EntityCircuit{

    public int translationX = 0;
    public int translationY = 0;
    
    public WireConnectionPoint(int x, int y) {
        super(x, y, EnumEntity.WIRE_CONNECTION_POINT);
        super.width = 16;
        super.height = 16;
    }

    @Override
    public void extraRender(Graphics g) {
        g.fillRect(x+this.translationX, y+this.translationY, width, height);
    }

    @Override
    public Rectangle getBounds(){
        return new Rectangle(x+this.translationX, y+this.translationY, this.height, this.width);
    }
    
    @Override
    public void extraTick() {
        
    }
    
}
