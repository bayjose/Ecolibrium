/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import Core.Game;
import Core.Handler;
import static Core.Handler.cam;
import Graphics.Gui.GuiChest;
import Graphics.SpriteBinder;
import Item.Item;
import java.awt.Graphics;

/**
 *
 * @author Bailey
 */
public class EntityTree extends Entity{

    public EntityTree(int x, int y) {
        super(x, y, EnumEntity.TREE);
        this.display = SpriteBinder.loadSprite("chest.png");
        this.width = (32 * 6);
        this.height = (48 * 6);
        this.display.width = this.width;
        this.display.height = this.height;
    }

    @Override
    public void tick(){
        EntityManager.addEntity(new ItemEntity(cam.x+(Game.WIDTH/2), cam.y+(Game.HEIGHT/2), Item.stick));
    }

    @Override
    public void render(Graphics g) {
        this.display.render(this.x+(-Handler.cam.x)-(this.display.width/2), this.y+(-Handler.cam.y)-(this.display.height/2), g);
    }
    
}
