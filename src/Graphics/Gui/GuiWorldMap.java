/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphics.Gui;

import Core.Game;
import Core.Handler;
import World.TileConstants;
import World.World;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * @author Bailey
 */
public class GuiWorldMap extends Gui{

    
    public GuiWorldMap() {
        super(0,0,Game.WIDTH, Game.HEIGHT);
    }

    @Override
    public void tick() {
        //find player
    }

    @Override
    public void render(Graphics g) {
        ///Map      
        Handler.world.renderMap(g);
        g.setColor(Color.WHITE);
        g.drawRect((Handler.cam.x)/(TileConstants.size/6)-7, (Handler.cam.y)/(TileConstants.size/6)-6, (Game.WIDTH/TileConstants.size)*6, (Game.HEIGHT/TileConstants.size)*6);
    }

    @Override
    public boolean onClick(Rectangle rect) {
        return false;
    }
}
