package Graphics;

import Utils.StringUtils;
import World.TileConstants;
import java.awt.Graphics;
import java.util.HashMap;




public class SpriteSheet {
    
    
    public final String name = "";
    Display sheet;
    HashMap<String, Display> sprites = new HashMap<String, Display>();
    String path;
    String[] data;
    
    public SpriteSheet(String path){
        data = StringUtils.loadData(path+"/spritesheet.txt");
        sheet = SpriteBinder.loadSprite(path+"/spritesheet.png");
        this.path = path;
        this.init();
    }
    
    public SpriteSheet(String path, String[] data){
        this.data = data;
        sheet = SpriteBinder.loadSprite(path);
        System.out.println(path);
        this.path = path;
        this.init();
    }
    
    public void init(){
        for(int i = 0; i < data.length; i++){
            String line = data[i];
            line = StringUtils.removeFrontSpacing(line);
//            System.out.println("["+i+"]"+data[i]);
            loop:{
                if(line.isEmpty()){
                    break loop;
                }
                //Comments
                if(line.startsWith("//")){
                    break loop;
                }
                //start
                if(line.contains("define:")){
                    String name = line.replaceAll("define:", "").replaceAll("\\{", "");
                    String variables = "";
                    detectCommands:{
                        for(int j = (i+1); j < data.length; j++){
                            if(data[j].contains("}")){
                                variables = variables + (StringUtils.removeFrontSpacing(data[j])).replaceAll("}", "");
                                break detectCommands;
                            }
                            variables = variables + StringUtils.removeFrontSpacing(data[j]);
                        }
                    }
                    this.processData(name, variables.split(";"));
                }
            }
        }
        for(Display display:sprites.values()){
            display.width = display.pWidth * TileConstants.ppp;
            display.height = display.pHeight * TileConstants.ppp;
        }
    }
    
    public void processData(String name, String[] data){
        int x = 0;
        int y = 0; 
        int width = 0;
        int height = 0;
        int centerX = 0;
        int centerY = 0;
        
        for(int i = 0; i < data.length; i++){
            String line = data[i];
            line = line.replaceAll(";", "");
            if(line.startsWith("x=")){
                x = Integer.parseInt(line.split("=")[1]);
            }
            if(line.startsWith("y=")){
                y = Integer.parseInt(line.split("=")[1]);
            }
            if(line.startsWith("width=")){
                width = Integer.parseInt(line.split("=")[1]);
            }
            if(line.startsWith("height=")){
                height = Integer.parseInt(line.split("=")[1]);
            }
        }
        
        System.out.println("Sprite:"+name + " x:"+x+" y:"+y+" width:"+width+" height:"+height);
        sprites.put(name, sheet.getSubDisplay(x, y, width, height));
    }
    
    public Display get(String id){
        if(sprites.containsKey(id)){
            return sprites.get(id);
        }
        return SpriteBinder.loadSprite("fileNotFound.png");
    }
    
    public void render(Graphics g){
        int index = 0;
        for(Display display:sprites.values()){
            display.render((index*5) * TileConstants.ppp, (index*5) * TileConstants.ppp, g);
            index ++;
        }
    }
    
    
}