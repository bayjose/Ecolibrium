/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import Core.Handler;
import Graphics.Gui.GuiCircuitboard;
import Graphics.Gui.GuiManager;
import Graphics.SpriteBinder;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * @author Bailey
 */
public class EntityCircuitboard extends Entity{

    private GuiCircuitboard gui;
    
    public EntityCircuitboard(int x, int y) {
        super(x, y, EnumEntity.CIRCUITBOARD);
        this.display = SpriteBinder.loadSprite("heightMap.png");
        this.gui = new GuiCircuitboard();
        this.width = (16 * 6);
        this.height = (16 * 6);
        this.display.width = this.width;
        this.display.height = this.height;
    }

    @Override
    public void tick() {
        
    }

    @Override
    public void render(Graphics g) {
        this.display.render(this.x+(-Handler.cam.x)-(this.display.width/2), this.y+(-Handler.cam.y)-(this.display.height/2), g);
    }
    
    @Override
    public void onClick(Rectangle rect) {
        if(rect.intersects(this.getBounds())){
            GuiManager.openThis(this.gui);
        }
    }
    
}
