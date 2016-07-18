/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

//import Base.BayScript;
import Base.BayScript;
import Core.Input.Keyboard;
import Core.Input.MouseInput;
import Core.Input.MousePositionLocator;
import Graphics.font.FontBook;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferStrategy;

/**
 *
 * @author Bayjose
 */
public class Game extends Canvas implements Runnable{
    
    boolean running = false;
    Thread thread;
    
    public static final String NAME = "Scripting Engine";
    public static int WIDTH = 1280;
    public static int HEIGHT = 720;
    
    public static float renderRate = 144.0f - 60.0f;
    public static final boolean VSynch = false;
    
    private Handler handler;

    public synchronized void start(){
        running = true;
        thread = new Thread(this);
        thread.start();
    }
    
    public void run() {
        init();
        long last = System.nanoTime();
        final float ticksPerSecond = 60.0f;
        int frames = 0;
        int ticks = 0;
        long age = System.currentTimeMillis();
        long extra = 0;
        
        while(this.running == true){
            long now = System.nanoTime();
            while((now-last)+extra>=(1000000000.0/ticksPerSecond)){
                ticks++;
                tick();
                if(VSynch){
                   render();
                   frames++;
                }
                extra += (now-last);
                extra -=(1000000000.0/ticksPerSecond);
                last = now;
            }
            if(!VSynch){
                render();
                frames++;
            }

            if(System.currentTimeMillis() - age > 1000){
                age = System.currentTimeMillis();
                System.out.println("Ticks:"+ticks+" Frames:"+frames);
                ticks = 0;
                frames = 0;
            }
        }
    }
    
    public void init(){
        this.createBufferStrategy(2);
        this.handler = new Handler();
        this.handler.init();
        //all utilities like fonts
        FontBook f = new FontBook();
        this.addMouseListener(new MouseInput());
        this.addKeyListener(new Keyboard());
        this.addMouseMotionListener(new MousePositionLocator());
        
        BayScript.invoke(this.getClass());
        
    }
    
    public void tick(){
        this.handler.tick();
        if(Keyboard.ENTER){
            FontBook.font.setColor(Color.yellow);
        }
    }
    
    public void render(){
        BufferStrategy buffer = this.getBufferStrategy();
        Graphics g = buffer.getDrawGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
        this.handler.render(g);
        
//        Graphics2D g2d = (Graphics2D) g;
//        Point2D center = new Point2D.Float(Game.WIDTH/2,Game.HEIGHT/2);
////        Point2D center2 = new Point2D.Float(WIDTH/2,HEIGHT/2);
//        float[] dist = new float[]{0.0f, 1.0f};
//        Color[] colors = new Color[]{new Color(0.0f, 0.0f, 0.0f, 0.0f), Color.BLACK};
//        
//        RadialGradientPaint p = new RadialGradientPaint(center, 800, dist, colors);
////        RadialGradientPaint p2 = new RadialGradientPaint(center2, 800, dist, colors);
//        
//        
//        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.15f));
//        g2d.setPaint(p);
//        g2d.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
//        
//        
        g.dispose();
        buffer.show();
    }
    
    public static Rectangle getScreen(){
        return new Rectangle(0, 0, Game.WIDTH, Game.HEIGHT);
    }
    
    public static void main(String[] args) {
        Window window = new Window(new Game());
    }
}
