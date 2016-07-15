/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import Core.Game;
import Core.Handler;
import Utils.DistanceCalculator;
import java.awt.Color;
import java.awt.Graphics;
import beads.AudioContext;
import beads.SampleManager;
import beads.Gain;
import beads.SamplePlayer;

/**
 *
 * @author Bailey
 */
public class EntitySpeaker extends EntityCircuit{

    float count = 0;
    int frequency = 0;
    int output = 0;
    boolean lastTick = false;
    
    private AudioContext ac;
    Gain gain ;
    
    int spacialX = 0;
    int spacialy = 0;
    
    public EntitySpeaker(int x, int y) {
        super(x, y, EnumEntity.SPEAKER);
        ac = new AudioContext();
        
        SamplePlayer player = new SamplePlayer(ac, SampleManager.sample("Sound/Wind.wav"));

        gain = new Gain(ac, 1, (float) 0.1);
        gain.addInput(player);
        ac.out.addInput(gain);
    }

    @Override
    public void extraTick() {
        if(count<60){
            count++;
        }else{
            count = 0;
            output = frequency;
            frequency = 0;
        }
        if(lastTick!=this.isPowered()){
            frequency++;
        }
        lastTick = this.isPowered();
        
        if(this.isPowered()){
            ac.start();
        }else{
            ac.stop();
        }
    }
    
    @Override
    public void tickInSpace(int x, int y){
        float volume = 1.0f-((DistanceCalculator.CalculateDistanceF(x, y, Handler.player.x, Handler.player.y)/(float)Game.WIDTH)/ 2);
        spacialX = x;
        spacialy = y;
//        System.out.println(volume);
        
        if(volume>0.4f){
            volume = 0.4f;
        }
        if(volume<0){
            volume = 0;
        }
        
//        System.out.println(volume);
        
        gain.setGain(volume);
    }

    @Override
    public void extraRender(Graphics g) {
        super.display.render(x+this.display.width/2, y+this.display.height/2, g);
        g.setColor(Color.GREEN);
        g.drawLine(spacialX, spacialy, Handler.player.x, Handler.player.y);
    }
    
}


