/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import Core.Input.MouseInput;
import Graphics.Display;
import Graphics.SpriteBinder;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * @author Bailey
 */
public class EntityButton extends EntityCircuit{

    boolean isPressed = false;
    Display pressed;
    
    public EntityButton(int x, int y) {
        super(x, y, EnumEntity.BUTTON);
        super.display = SpriteBinder.loadSprite("entity/button_core.png");
        super.display.width = this.getBounds().width;
        super.display.height = this.getBounds().height;
        
        this.pressed = SpriteBinder.loadSprite("entity/button_pressed.png");
        this.pressed.width = this.getBounds().width;
        this.pressed.height = this.getBounds().height;
        
        super.setConnections(new WireConnectionPoint[]{
            new WireConnectionPoint(this.display.width/2,0),
            new WireConnectionPoint(this.display.width/2,this.display.height)});

    }

    @Override
    public void extraTick() {
        if(this.isPressed){
            if(MouseInput.IsPressed){
                this.canConduct = true;
                if(super.connections[0].isPowered()){
                    super.connections[1].setPowered(true);
                    return;
                }
                if(super.connections[1].isPowered()){
                    super.connections[0].setPowered(true);
                    return;
                }
                
            }else{
                this.isPressed = false;
                this.canConduct = false;
            }
        }
    }
    
    @Override
    public void onClick(Rectangle rect) {
        isPressed = true;
    }

    @Override
    public void extraRender(Graphics g) {
        if(!super.canConduct){
            this.display.render(this.x+(this.display.width/2), this.y+(this.display.height/2), g);
        }else{
            this.pressed.render(this.x+(this.display.width/2), this.y+(this.display.height/2), g);
        }
    }
    
}
