/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import Core.Input.Keyboard;
import Utils.StringUtils;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * @author Bailey
 */
public abstract class EntityCircuit extends Entity{

    private boolean powered = false;
    protected WireConnectionPoint[] connections = new WireConnectionPoint[]{};
    
    protected boolean canConduct = true;
    
    private float voltage    = 0.0f;
    private float amperage   = 0.0f;
    private float resistance = 0.0f;
    
    public EntityCircuit(int x, int y, EnumEntity entity) {
        super(x, y, entity);
        super.setID(StringUtils.genRandomExtension(10));
    }
    
    public EntityCircuit setConnections(WireConnectionPoint[] points){
        this.connections = points;
        this.updateConnectionPoints();
        return this;
    }
    
    public boolean isPowered(){
        return this.powered;
    }
    
    
    public boolean canConduct(){
        return this.canConduct;
    }
    
    public void setPowered(boolean powered){
        this.powered = powered;
    }
    
    public WireConnectionPoint[] getConnections(){
        return this.connections;
    }
    
    public float getVoltage(){
        if(this.resistance>0){
            return this.amperage/this.resistance;
        }else
        return 0.0f;
    }
    
    public abstract void extraRender(Graphics g);
    public abstract void extraTick();
    
    
    public void resetPower(){
        powered = false;
        for(WireConnectionPoint wcp : this.connections){
            wcp.resetPower();
        }
    }
    
    @Override
    public void tick(){
        for(WireConnectionPoint wcp : this.connections){
            wcp.tick() ;
        }
        this.extraTick();
    }
    
    @Override
    public Rectangle getBounds(){
        return new Rectangle(x, y, this.height, this.width);
    }
    
    public void render(Graphics g){
        if(Keyboard.bool_3){
            g.fillRect(this.getBounds().x, this.getBounds().y, this.getBounds().height, this.getBounds().width);
        }
        for(WireConnectionPoint wcp : this.connections){
            wcp.render(g);
        }
        if(this.isPowered()){
            g.setColor(Color.red);
        }else{
            g.setColor(Color.black);
        }
        extraRender(g);
    }
    
    public WireConnectionPoint canConnect(Rectangle rect){
        for(WireConnectionPoint wcp : this.connections){
            if(rect.intersects(wcp.getBounds())){
                return wcp;
            }
        }
        return null;
    }

    public void updateConnectionPoints() {
        for(WireConnectionPoint pt : this.connections){
            pt.translationX = this.getBounds().x;
            pt.translationY = this.getBounds().y;
        }
    }
}
