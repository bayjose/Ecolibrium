/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import beads.AudioContext;
import beads.Gain;
import beads.SampleManager;
import beads.SamplePlayer;




/**
 *
 * @author Bayjose
 */


public class SoundPlayer {
	private static AudioContext ac = new AudioContext();

        /*
         * Here's how to play back a sample.
         * 
         * The first line gives you a way to choose the audio file. The
         * (commented, optional) second line allows you to stream the audio
         * rather than loading it all at once. The third line creates a sample
         * player and loads in the Sample. SampleManager is a utility which
         * keeps track of loaded audio files according to their file names, so
         * you don't have to load them again.
         */
        public static void playSound(String audioFile){
//             SampleManager.setBufferingRegime(Sample.Regime.newStreamingRegime(1000));
            SamplePlayer player = new SamplePlayer(ac, SampleManager.sample("Sound/"+audioFile));
            Gain g = new Gain(ac, 1, 1);
            g.addInput(player);
            ac.out.addInput(g);
            ac.start();
        }
        
        public static void playSound(String audioFile, float ballance){
//             SampleManager.setBufferingRegime(Sample.Regime.newStreamingRegime(1000));
            SamplePlayer player = new SamplePlayer(ac, SampleManager.sample("Sound/"+audioFile));
            Gain g = new Gain(ac, 1, 1);

            g.addInput(player);
            ac.out.addInput(g);
            ac.start();
        }

}
