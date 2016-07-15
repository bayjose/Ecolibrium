/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphics.font;

import Graphics.Display;
import Graphics.SpriteBinder;
import Graphics.SpriteSheet;
import Utils.StringUtils;
import World.TileConstants;
import java.awt.Color;
import java.awt.Graphics;
import java.util.HashMap;

/**
 *
 * @author Bailey
 */
public class Font {
    
    
    public SpriteSheet sheet;
    public int size = (3);
    
    public Font(String path, String[] data){
        sheet = new SpriteSheet("Fonts/"+path+"/font.png", data);
    }
    
    public void setColor(Color color){
       
    }
    
    public void drawString(String data, int x, int y, Graphics g){
        //can have alignment passed in after
        int totalLength = 0;
        int spacing = 1;
        
        for(int i = 0; i < data.length(); i++){
            Display d = this.sheet.get(data.charAt(i)+""); 
            int offset = 0;
            if(d.pHeight<8){
                offset = 8 - d.pHeight;
            }
            d.render((x+totalLength)*TileConstants.ppp, y+offset, g);
            totalLength+=d.pWidth+spacing;
        }
    }
    
}
