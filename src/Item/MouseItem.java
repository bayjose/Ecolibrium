/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Item;

import Core.Input.MouseInput;
import Core.Input.MousePositionLocator;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * @author Bailey
 */
public class MouseItem {
    public static Item item = null;
    public static int count = 1;
    
    public static void tick(){
        if(item!=null){
            item.tick();
        }
    }
    
    public static void render(Graphics g){
        if(item!=null){
            item.render(MousePositionLocator.MouseLocation.x, MousePositionLocator.MouseLocation.y, g);
        }
        g.setColor(Color.white);
        g.drawString(""+count, MousePositionLocator.MouseLocation.x, MousePositionLocator.MouseLocation.y);
    }
    
    public static void onClick(Rectangle rect){
        if(item!=null){
           count = item.onClick(rect, count);
           if(count<=0){
               count = 0;
               item = null;
           }
        }
    }
}
