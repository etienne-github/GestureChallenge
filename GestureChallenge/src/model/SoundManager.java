package model;


import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import javax.swing.*;
import javax.sound.sampled.*;


import java.io.BufferedInputStream;
import java.io.FileInputStream;

import javazoom.jl.player.Player;




public class SoundManager {
	HashMap<String,Sound> soundLibrairy=new HashMap<String,Sound>();
	
	public void addSoundinSoundLibrary(String soundName, String path,boolean hasToBePlayedInLoop){
		
		
		try {
			URL u = new URL("File://"+path);
			System.err.println("url : "+u);
			File f = new File(path);
			System.err.println("file f : "+f.getAbsolutePath());
			if(hasToBePlayedInLoop){
				soundLibrairy.put(soundName, new LoopSound(f));
			}else{
				soundLibrairy.put(soundName, new OneShotSound(f));
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void playSound(String SoundName){
		soundLibrairy.get(SoundName).play();
	}
	
	
	interface Sound{
		public abstract void play();
	}
	
	
	class OneShotSound implements Sound{
		File f;
		
		public OneShotSound(File f){
			this.f=f;
		}
		
		
		

		public void play(){
				//System.err.println("play called !");
				new Thread(new Runnable() { // the wrapper thread is unnecessary, unless it blocks on the Clip finishing, see comments
				      public void run() {
				    	  Clip clip;
				    	  	try {
								clip = AudioSystem.getClip();
								AudioInputStream ais = AudioSystem.getAudioInputStream(f);
								clip.open(ais);
								clip.start();
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

				      }
				}).start();

				

			
		}

		
		
	}
	
	
	class LoopSound implements Sound{
		private AudioInputStream ais;
		
		public LoopSound(File f){
			try {
				ais = AudioSystem.
				        getAudioInputStream(f);
			} catch (UnsupportedAudioFileException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public LoopSound(URL u) {
			try {
				ais = AudioSystem.
				        getAudioInputStream(u);
			} catch (UnsupportedAudioFileException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public void play(){
			Clip clip;
			try {
				clip = AudioSystem.getClip();
				clip.open(ais);
				clip.loop(Clip.LOOP_CONTINUOUSLY);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

		
		
	}
	

	public MP3 MP3Player(String filename){
		return new MP3(filename);
	}


	public class MP3 {
	    private String filename;
	    private Player player; 

	    // constructor that takes the name of an MP3 file
	    public MP3(String filename) {
	        this.filename = filename;
	    }

	    public void close() { if (player != null) player.close(); }

	    // play the MP3 file to the sound card
	    public void play() {
	        try {
	            FileInputStream fis     = new FileInputStream(filename);
	            BufferedInputStream bis = new BufferedInputStream(fis);
	            player = new Player(bis);
	        }
	        catch (Exception e) {
	            System.out.println("Problem playing file " + filename);
	            System.out.println(e);
	        }

	        // run in new thread to play in background
	        new Thread() {
	            public void run() {
	                try { player.play();}
	                catch (Exception e) { System.out.println(e); }
	            }
	        }.start();




	    }
	}

	
	
	
}



