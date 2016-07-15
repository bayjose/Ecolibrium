/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Item;

import Entity.EntityCircuit;
import Entity.EntityWire;
import Entity.EnumEntity;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * @author Bailey
 */
public class ItemBattery extends ItemCircuit{

    public ItemBattery() {
        super("Battery", EnumEntity.BATTERY, "item/battery.png");
    }

    @Override
    public void tick() {
        
    }

    @Override
    public void render(int x, int y, Graphics g) {
        this.display.render(x, y, g);
    }

    @Override
    public void onInteract(Rectangle rect) {
        
    }
    
}
