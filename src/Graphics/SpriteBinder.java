/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Graphics;

import Utils.StringUtils;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.LinkedList;
import javax.swing.ImageIcon;

/**
 *this is a file that contains all the loaded sprites 
 * @author Bayjose
 */
public class SpriteBinder {
    //current resources.png file in the dir you are looking at
    
    public static Image preview;
    public static HashMap<String, Display> displays = new HashMap<String, Display>();
    public static LinkedList<RegisteredImage> loadedImages = new LinkedList<RegisteredImage>();
    
    public static void init(){
        System.out.println("Creating Sprite Binder");
        SpriteBinder.loadedImages.add(new RegisteredImage("Core/fileNotFound.png"));
        System.out.println("--------------------------------------------------");
    }
    
    public static void render(Graphics g){
        for(int i = 0; i< SpriteBinder.loadedImages.size(); i++){
            g.drawImage(SpriteBinder.loadedImages.get(i).image, i*100, i*100, 100, 100,null);
        }
    }
    
    public static Image checkImage(String id){
        for(int i=0; i<SpriteBinder.loadedImages.size(); i++){
            if(SpriteBinder.loadedImages.get(i).id.equals(id)){
//                System.out.println("Image:"+id+" already exists.");
                return SpriteBinder.loadedImages.get(i).image;
            }
        }
        
        RegisteredImage temp = new RegisteredImage(id);
        if(!temp.broken){
            SpriteBinder.loadedImages.add(temp);
            return temp.image;
        }else{
            System.out.println("Image:"+id+" not found");
            return SpriteBinder.loadedImages.getFirst().image;
        }
    }
    
    public static String getResourcePath(int id){
        if(id>=0 && id<SpriteBinder.loadedImages.size()){
            return SpriteBinder.loadedImages.get(id).id;
        }
        return "";
    }
    
    public static int checkImageID(String id){
        if(id.equals("null")||id.equals("invisible")||id.equals("clear")){
            return -2;
        }
        checkImage(id);
        for(int i=0; i<SpriteBinder.loadedImages.size(); i++){
            if(SpriteBinder.loadedImages.get(i).id.equals(id)){
//                System.out.println("Image:"+id+" already exists.");
                return i;
            }
        }
        RegisteredImage temp = new RegisteredImage(id);
        SpriteBinder.loadedImages.add(temp);
        return loadedImages.size();
//        return new RegisteredImage("Core/developer.png").image;
    }
    
    public static Display loadSprite(String image){
        if(displays.containsKey(image)){
            return new Display(displays.get(image).width, displays.get(image).height, displays.get(image).pWidth, displays.get(image).pHeight, displays.get(image).pixels);
        }
        
        Image img = SpriteBinder.checkImage(image);
        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();
        
        Pixel[] pixels = new Pixel[bimage.getWidth() * bimage.getHeight()];
        for(int j = 0; j<bimage.getHeight(); j++){
            for(int i = 0; i<bimage.getWidth(); i++){
                pixels[(i + (j * bimage.getWidth()))] = new ColorPixel(bimage.getRGB(i, j));
            }
        }
        
        Display out = new Display(bimage.getWidth(), bimage.getHeight(), bimage.getWidth(), bimage.getHeight(), pixels);
        displays.put(image, out);
        return displays.get(image);
    }
    
    public static Display loadSpriteDirect(String image){
        if(displays.containsKey(image)){
            return new Display(displays.get(image).width, displays.get(image).height, displays.get(image).pWidth, displays.get(image).pHeight, displays.get(image).pixels);
        }
        
        ImageIcon imgcon = new ImageIcon(StringUtils.getAbsPath()+image);
        Image img = imgcon.getImage();
        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();
        
        Pixel[] pixels = new Pixel[bimage.getWidth() * bimage.getHeight()];
        for(int j = 0; j<bimage.getHeight(); j++){
            for(int i = 0; i<bimage.getWidth(); i++){
                pixels[(i + (j * bimage.getWidth()))] = new ColorPixel(bimage.getRGB(i, j));
            }
        }
        
        Display out = new Display(bimage.getWidth(), bimage.getHeight(), bimage.getWidth(), bimage.getHeight(), pixels);
        displays.put(image, out);
        return displays.get(image);
    }
    
    
    
}
