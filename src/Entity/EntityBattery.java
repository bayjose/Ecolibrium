/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import Graphics.SpriteBinder;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * @author Bailey
 */
public class EntityBattery extends EntityCircuit{

    
    public EntityBattery(int x, int y) {
        super(x, y, EnumEntity.BATTERY);
        super.display = SpriteBinder.loadSprite("item/battery.png");
        super.display.width = this.getBounds().width;
        super.display.height = this.getBounds().height;
        
        super.setConnections(new WireConnectionPoint[]{
            new WireConnectionPoint(this.display.width/2,0),
            new WireConnectionPoint(this.display.width/2,this.display.height)});
        
    }

    @Override
    public void extraTick() {
        super.connections[0].setPowered(true);
    }
    
    @Override
    public void onClick(Rectangle rect) {

    }

    @Override
    public void extraRender(Graphics g) {
        this.display.render(this.x+(this.display.width/2), this.y+(this.display.height/2), g);
    }
    
}
