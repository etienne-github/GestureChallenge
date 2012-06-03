package popup;

import java.applet.Applet;

import model.Constants;

import processing.core.PApplet;

import codeanticode.gsvideo.GSMovie;

public abstract class HelpSequence {
	String moviePath;
	GSMovie movieClip;
	HybridHelpPopUp myHHP;
	
	
	
	


public HelpSequence(String moviePath,GSMovie preloadedMovieClip, PApplet app, HybridHelpPopUp h){
		
		myHHP=h;
		this.moviePath=moviePath;
		if(!Constants.isOnMac){
			if(preloadedMovieClip==null){
				movieClip  = new GSMovie(app,moviePath,30);

			}else{
				movieClip=preloadedMovieClip;
			}
		}
		
		
		
	}

abstract protected void remove();

abstract protected void setUp();


public String getMoviePath() {
	return moviePath;
}

public void setMoviePath(String moviePath) {
	this.moviePath = moviePath;
}

public GSMovie getMovieClip() {
	return movieClip;
}

public void setMovieClip(GSMovie movieClip) {
	this.movieClip = movieClip;
}

public HybridHelpPopUp getMyHHP() {
	return myHHP;
}

public void setMyHHP(HybridHelpPopUp myHHP) {
	this.myHHP = myHHP;
}

	
}
