/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Item;

import Core.Handler;
import static Core.Handler.cam;
import Core.Input.MouseInput;
import Entity.Entity;
import Entity.EntityChest;
import Entity.EntityManager;
import Entity.ItemEntity;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * @author Bailey
 */
public class ItemChest extends Item{
    
    public ItemChest() {
        super("Chest", "chest.png");
    }

    @Override
    public void tick() {
        
    }

    @Override
    public void render(int x, int y, Graphics g) {
        this.display.render(x, y, g);
    }

    @Override
    public int onClick(Rectangle rect, int quantity) {
        EntityManager.addEntity(new EntityChest(rect.x+Handler.cam.x, rect.y+Handler.cam.y));
        return quantity-1;
    }

    @Override
    public void onInteract(Rectangle rect) {
//        EntityManager.addEntity(new EntityChest(rect.x+Handler.cam.x, rect.y+Handler.cam.y));
        
    }
    
}
