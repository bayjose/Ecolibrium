/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphics.Gui;

import Core.Game;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * @author Bailey
 */
public class GuiChest extends Gui{
    
    Container container;
    
    public GuiChest() {
        super(0, 0, Game.WIDTH, Game.HEIGHT);
        container = new Container((59 * 6), (40 * 6), 6, 1); 
    }

    @Override
    public void tick() {
        container.tick();
    }

    @Override
    public void render(Graphics g) {
        container.render(g);
    }

    @Override
    public boolean onClick(Rectangle rect) {
        return container.onClick(rect);
    }
    
}
