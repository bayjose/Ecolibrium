/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import Core.Handler;
import Core.Input.MouseInput;
import Graphics.Display;
import Graphics.SpriteBinder;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * @author Bailey
 */
public class EntityWire extends EntityCircuit{

    private String color = "blue";
    
    private WireConnectionPoint pt1 = null;
    private WireConnectionPoint pt2 = null;
    
    public EntityWire(int x, int y) {
        super(x, y, EnumEntity.WIRE);
        super.display = SpriteBinder.loadSprite("entity/wire_"+color+"_core.png");
        super.display.width = this.getBounds().width;
        super.display.height = this.getBounds().height;
    }
    
    public void setPT1(WireConnectionPoint wcp){
        this.pt1 = wcp;
    }
    
    public void setPT2(WireConnectionPoint wcp){
        this.pt2 = wcp;
    }
    
    public WireConnectionPoint getPT1(){
        return this.pt1;
    }
    
    public WireConnectionPoint getPT2(){
        return this.pt2;
    }

    @Override
    public void extraTick() {
        if(pt1 != null && pt2 != null){
            if(pt1.isPowered()){
                pt2.setPowered(true);
                this.setPowered(true);
                return;
            }
            if(pt2.isPowered()){
                pt1.setPowered(true);
                this.setPowered(true);
                return;
            }
        }
    }
    
    @Override
    public void onClick(Rectangle rect) {

    }

    @Override
    public void extraRender(Graphics g) {
        this.display.render(this.x+(this.display.width/2), this.y+(this.display.height/2), g);
        if(pt1 != null && pt2 != null){
            g.drawLine(pt1.x+pt1.translationX, pt1.y+pt1.translationY, pt2.x+pt2.translationX, pt2.y+pt2.translationY);
        }
    }
    
}
