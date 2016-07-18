/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sound;

import beads.AudioContext;
import beads.Gain;
import java.util.LinkedList;

/**
 *
 * @author Bailey
 */
public class SoundManager {
    private static AudioContext ac;
    private static LinkedList<Music> musicObjects = new LinkedList<Music>();
    
    private float volume = 1.0f;
    private Gain gain;
    
    public SoundManager(){
	this.ac = new AudioContext();
        this.gain = new Gain(ac, 1, volume);
        this.ac.start();
    }
    
    public void setVolume(float volume){
        if(volume>=0 && volume<=2){
            this.volume = volume;
        }
        this.gain.setGain(volume);
    }
		
    public AudioContext getAudioContext(){
        return this.ac;
    }
    
    public static void add(Music music){
        musicObjects.add(music);
        music.start();
    }
    
    public static void tick(){
        for(int i = 0; i < musicObjects.size(); i++){
            Music music = musicObjects.get(i);
            if(music.isTerminated()){
                musicObjects.remove(music);
                i--;
            }else{
                music.tick();
            }
        }
    }
    
    //sets the volume of music1 to (float) and volume of (music2) to 1-(float)
    public static void crossfade(){
        
    }
}
