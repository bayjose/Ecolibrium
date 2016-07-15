/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphics.Gui;

import Core.Game;
import Core.Handler;
import Core.Input.Keyboard;
import Core.Input.MouseInput;
import Core.Input.MousePositionLocator;
import Graphics.Display;
import Graphics.SpriteBinder;
import World.TileConstants;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;
import javax.swing.JOptionPane;

/**
 *
 * @author Bailey
 */
public class SpitesheetMaker extends Gui{

    Display image;
    Rectangle[][] rect;
    
    LinkedList<String[]> subImages = new LinkedList<String[]>();
    Rectangle selected = new Rectangle(0,0,0,0);
    
    int moveCountdown = 0;
    int maxTicks = 5;
    
    
    public SpitesheetMaker(String image) {
        super(0, 0, 0, 0);
        this.image = SpriteBinder.loadSprite(image);
        super.bounds.width = this.image.pWidth * TileConstants.ppp;
        super.bounds.height = this.image.pHeight * TileConstants.ppp;
        this.image.width = super.bounds.width;
        this.image.height = super.bounds.height;
    }

    @Override
    public void tick() {
        if(MouseInput.IsPressed){
            this.selected.width = ((MousePositionLocator.MouseLocation.x - MouseInput.Mouse.x)/TileConstants.ppp)*TileConstants.ppp;
            this.selected.height = ((MousePositionLocator.MouseLocation.y - MouseInput.Mouse.y)/TileConstants.ppp)*TileConstants.ppp;
        }
        
        if(moveCountdown!=0){
            if(!Keyboard.UP && !Keyboard.DOWN && ! Keyboard.LEFT && ! Keyboard.RIGHT){
                moveCountdown = 0;
            }
        }
        
        if(moveCountdown<=0){
            if(Keyboard.UP){
                if(this.selected.y>0){
                    this.selected.y -= TileConstants.ppp;
                }
                moveCountdown = maxTicks;
            }

            if(Keyboard.DOWN){
                if(this.selected.y+this.selected.height<Game.HEIGHT){
                    this.selected.y += TileConstants.ppp;
                }
                moveCountdown = maxTicks;
            }

            if(Keyboard.LEFT){
                if(this.selected.x>0){
                    this.selected.x -= TileConstants.ppp;
                }
                moveCountdown = maxTicks;
            }

            if(Keyboard.RIGHT){
                if(this.selected.x+this.selected.width<Game.WIDTH){
                    this.selected.x += TileConstants.ppp;
                }
                moveCountdown = maxTicks;
            }
        }else{
            moveCountdown--;
        }
        
        if(this.selected.width > 0 && this.selected.height > 0){
            if(Keyboard.ENTER){
                String[] data = new String[]{
                  "define:"+JOptionPane.showInputDialog("Enter Sprite Name", "Define Sprite")+"{",
                  "x="+this.selected.x/TileConstants.ppp+";",
                  "y="+this.selected.y/TileConstants.ppp+";",
                  "width="+this.selected.width/TileConstants.ppp+";",
                  "height="+this.selected.height/TileConstants.ppp+";",
                  "}",
                };
                
                this.subImages.add(data);
                
                selected = new Rectangle(0,0,0,0);
            }
        }
    }
    
    @Override
    public void onClose(){
        System.out.println("\"//Sprite Sheet\"");
        
        for(int i = 0; i < this.subImages.size(); i++){
            for(int j = 0; j < this.subImages.get(i).length; j++){
                System.out.println("\""+this.subImages.get(i)[j]+"\",");
            }
        }
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.white);
        g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
        this.image.render(this.bounds.x+(this.image.width/2), this.bounds.y+(this.image.height/2), g);
        g.setColor(Color.orange);
        g.drawRect(selected.x, selected.y, selected.width, selected.height);
    }

    @Override
    public boolean onClick(Rectangle rect) {
        selected = new Rectangle((rect.x/TileConstants.ppp)*TileConstants.ppp, (rect.y/TileConstants.ppp)*TileConstants.ppp, rect.width, rect.height);
        return true;
    }
    
}
