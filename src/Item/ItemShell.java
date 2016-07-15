/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Item;

import Core.Game;
import static Core.Handler.cam;
import Entity.EntityManager;
import Entity.ItemEntity;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * @author Bailey
 */
public class ItemShell extends Item{

    public ItemShell(String image) {
        super("Shell", image);
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
        System.out.println("Im a shell");
    }
    
}
