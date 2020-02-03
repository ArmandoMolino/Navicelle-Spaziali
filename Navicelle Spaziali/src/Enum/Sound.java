package Enum;

import Screen.Factory.AbstractScreenFactory;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

/**
 * Quest'enum contiene i file audio permette anche avviare le
 * tracce audio per il gioco.
 * @see Clip
 * @see File
 * @see AudioSystem
 */
public enum Sound {

    EXPLOSION(new File("resurce\\Sounds\\explosion.aiff"), .1D),
    GAME_OVER(new File("resurce\\Sounds\\GameOver.wav"),.1D),
    TITLE_MUSIC(new File("resurce\\Sounds\\TitleMusic.wav"),.1D),
    PICKUP_FUEL(new File("resurce\\Sounds\\PickUpFuel.wav"),.5D);

    //clip audio
    private Clip clip;

    /**
     * Costruttore dell'enum Sound; crea una clip
     * contenente l'audio del file.
     * @param file file audio.
     * @param volume volume dell'audio.
     */
    Sound(File file, double volume){
        try {
            //creazione della clip audio
            clip = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(file);
            clip.open(inputStream);

            //modifica volume del suono
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            double gain = volume;
            float dB = (float) (Math.log(gain) / Math.log(10.0) * 20.0);
            gainControl.setValue(dB);

        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * avvia la clip audio.
     * @param loop numero di ripetizioni della clip audio
     */
    public void playSound(int loop){
        //avvia il suono solo se i suoni sono abilitati
        if( AbstractScreenFactory.sound ){
            clip.setFramePosition(0);   //imposta la clip all'inizio della traccia audio
            clip.loop(loop);            //imposta quante volte la traccia audio deve avviarsi
            clip.start();               //avvia la traccia audio
        }
    }

    /**
     * ferma la clip audio.
     */
    public void stopSound(){
        clip.stop(); //ferma la traccia audio
    }

}
