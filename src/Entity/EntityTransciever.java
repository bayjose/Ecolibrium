/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.awt.Graphics;

/**
 *
 * @author Bailey
 */
public class EntityTransciever extends EntityCircuit{
    
    public EntityTransciever(int x, int y) {
       super(x, y, EnumEntity.RADIO_TRANSCIEVER);
       
       super.setConnections(new WireConnectionPoint[]{
            new WireConnectionPoint(x,y+this.display.height/2),
            new WireConnectionPoint(x+this.display.width,y+this.display.height/2)});

    }

    @Override
    public void extraTick() {
                if(super.connections[0].isPowered()){
            super.connections[1].setPowered(true);
            return;
        }
        if(super.connections[1].isPowered()){
            super.connections[0].setPowered(true);
            return;
        }
    }
    
    @Override
    public void extraRender(Graphics g) {

    }

}


