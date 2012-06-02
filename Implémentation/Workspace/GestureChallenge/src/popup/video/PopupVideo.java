package popup.video;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import model.Constants;

import org.mt4j.components.TransformSpace;
import org.mt4j.components.visibleComponents.shapes.MTEllipse;
import org.mt4j.components.visibleComponents.shapes.MTRoundRectangle;
import org.mt4j.components.visibleComponents.shapes.MTRectangle.PositionAnchor;
import org.mt4j.components.visibleComponents.widgets.MTTextField;
import org.mt4j.components.visibleComponents.widgets.buttons.MTImageButton;
import org.mt4j.input.inputProcessors.IGestureEventListener;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapProcessor;
import org.mt4j.util.MTColor;
import org.mt4j.util.font.FontManager;
import org.mt4j.util.font.IFont;
import org.mt4j.util.math.Vector3D;
import org.mt4j.util.math.Vertex;

import codeanticode.gsvideo.GSMovie;

import popup.PopUpCreator;
import popup.Popup;
import processing.core.PApplet;
import processing.core.PImage;
import scene.GestureChallengeScene;


public class PopupVideo extends Popup {

	HashMap<String,String> MovieClips;
	HashMap<String,GSMovie> MovieClips2;
	ArrayList<String> order;
	MTVideoTexture myPlayer;
	PImage nextButtonImage;
	PImage previousButtonImage;
	String currentClip;
	MTImageButton next;
	MTImageButton previous;
	float radius;
	
	
	public PopupVideo(Class sprite, String name,
			GestureChallengeScene s, PopUpCreator PC, Vector3D centerPosition,
			float radius, float scale) {
		super(sprite,name , "", s, PC, centerPosition, radius);
		this.radius=radius;
		order=new ArrayList<String>();
		MovieClips = new HashMap<String,String>();	
		MovieClips2 = new HashMap<String,GSMovie>();
		nextButtonImage= s.getMTApplication().loadImage("."+((String)File.separator)+"src"+((String)File.separator)+"popup"+((String)File.separator)+"video"+((String)File.separator)+"data"+((String)File.separator)+"arrow-right.png");
		previousButtonImage= s.getMTApplication().loadImage("."+((String)File.separator)+"src"+((String)File.separator)+"popup"+((String)File.separator)+"video"+((String)File.separator)+"data"+((String)File.separator)+"arrow-left.png");
		next= new MTImageButton(s.getMTApplication(), nextButtonImage);
		previous= new MTImageButton(s.getMTApplication(), previousButtonImage);
		next.setVisible(false);
		previous.setVisible(false);
		next.setPickable(false);
		previous.setPickable(false);
		next.setNoStroke(true);
		previous.setNoStroke(true);
		
		next.setPositionRelativeToOther(this.getMyShape(), new Vector3D(this.getMyShape().getCenterPointLocal().x+radius*0.5f,this.getMyShape().getCenterPointLocal().y+radius*0.60f));
		previous.setPositionRelativeToOther(this.getMyShape(), new Vector3D(this.getMyShape().getCenterPointLocal().x-radius*0.5f,this.getMyShape().getCenterPointLocal().y+radius*0.60f));

		this.getMyShape().addChild(next);
		this.getMyShape().addChild(previous);
		
		
		next.addGestureListener(TapProcessor.class, new IGestureEventListener(){

			@Override
			public boolean processGestureEvent(MTGestureEvent arg0) {
				TapEvent te = (TapEvent)arg0;
				if (te.isTapped()){
					
					System.out.println("titre suivant : "+order.get(order.indexOf(PopupVideo.this.currentClip)+1));
					System.out.println();
					PopupVideo.this.playMovie2(order.get(order.indexOf(PopupVideo.this.currentClip)+1));
				}

				return false;
			}
			
		});
		
		previous.addGestureListener(TapProcessor.class, new IGestureEventListener(){

			@Override
			public boolean processGestureEvent(MTGestureEvent arg0) {
				TapEvent te = (TapEvent)arg0;
				if (te.isTapped()){
					PopupVideo.this.playMovie2(order.get(order.indexOf(PopupVideo.this.currentClip)-1));
				}

				return false;
			}
			
		});
		
		IFont f = FontManager.getInstance().createFont(scene.getMTApplication(), "REZ.ttf", 30);
		
		IGotItButton b = new IGotItButton(this.getMyShape().getCenterPointLocal().x-100, this.getMyShape().getCenterPointLocal().y-100, f.getFontAbsoluteHeight()+2, f);
		b.setPositionRelativeToOther(this.getMyShape(), new Vector3D(this.getMyShape().getCenterPointLocal().x,this.getMyShape().getCenterPointLocal().y+radius*0.6f));
		this.getMyShape().addChild(b);
		
		
		this.getMyShape().scale(scale, scale, scale, this.getMyShape().getCenterPointGlobal());
		
	}
	
	public void addMovieClipPath(String movieClipName, String movieClipPath){
		this.MovieClips.put(movieClipName, movieClipPath);
		order.add(movieClipName);
		
		
		
		
		
		if(order.indexOf(currentClip)>0){
			previous.setVisible(true);
			previous.setPickable(true);
		}else{
			previous.setVisible(false);
			previous.setPickable(false);
		}
		
		if(order.indexOf(currentClip)<(order.size()-1)){
			next.setVisible(true);
			next.setPickable(true);
		}else{
			next.setVisible(false);
			next.setPickable(false);
		}
	}
	
	
	public void addMovieClip(String movieClipName, String movieClipPath, GSMovie preloadedMovie){
		
		GSMovie movieClip;
		
		if(preloadedMovie==null){
			movieClip  = new GSMovie(scene.getMTApplication(),movieClipPath,30);

		}else{
			movieClip=preloadedMovie;
		}
		
		this.MovieClips.put(movieClipName, movieClipPath);
		this.MovieClips2.put(movieClipName, movieClip);
		order.add(movieClipName);
		
		
		
		
		
		if(order.indexOf(currentClip)>0){
			previous.setVisible(true);
			previous.setPickable(true);
		}else{
			previous.setVisible(false);
			previous.setPickable(false);
		}
		
		if(order.indexOf(currentClip)<(order.size()-1)){
			next.setVisible(true);
			next.setPickable(true);
		}else{
			next.setVisible(false);
			next.setPickable(false);
		}
	}
	
	
	public void playMovie(String movieClipName){
		
		float x=getMyShape().getCenterPointGlobal().x;
		float y=getMyShape().getCenterPointGlobal().y;
		
		if(myPlayer!=null){
			
			x = myPlayer.getPosition(TransformSpace.GLOBAL).x;
			y = myPlayer.getPosition(TransformSpace.GLOBAL).y;
			
			myPlayer.stop();
			myPlayer.removeFromParent();
			myPlayer.destroyComponent();
			myPlayer.destroy();
			
		}
		myPlayer = new MTVideoTexture(MovieClips.get(movieClipName),new Vertex(0,0),this.scene.getMTApplication());
		//this.setTitle(movieClipName);
		myPlayer.setVolume(0);
		myPlayer.setAnchor(PositionAnchor.CENTER);
		myPlayer.setNoStroke(true);
		float w = myPlayer.getWidthXY(TransformSpace.GLOBAL);
		float h = myPlayer.getHeightXY(TransformSpace.GLOBAL);
		
		float a = (float) Math.sqrt((Math.pow(2*radius, 2))/(Math.pow(w, 2)+Math.pow(h, 2)));
		
		
		//TODO Rotate
		this.getMyShape().addChild(myPlayer);
		myPlayer.scale(a, a, a, myPlayer.getCenterPointGlobal());
		myPlayer.setPositionGlobal(new Vector3D(x,y));
	//	myPlayer.setPositionRelativeToOther(this.getMyShape(), new Vector3D(x,y));
		
		currentClip=movieClipName;
		myPlayer.loop();
		if(order.indexOf(currentClip)>0){
			previous.setVisible(true);
			previous.setPickable(true);
		}else{
			previous.setVisible(false);
			previous.setPickable(false);
		}
		
		if(order.indexOf(currentClip)<(order.size()-1)){
			next.setVisible(true);
			next.setPickable(true);
		}else{
			next.setVisible(false);
			next.setPickable(false);
		}
	}
	
	
public void playMovie2(String movieClipName) {
	//Optimization of playMovie1;	
	if(myPlayer==null){
		float x=getMyShape().getCenterPointGlobal().x;
		float y=getMyShape().getCenterPointGlobal().y;
		myPlayer = new MTVideoTexture(MovieClips.get(movieClipName),new Vertex(0,0),this.scene.getMTApplication());
		myPlayer.setVolume(0);
		myPlayer.setAnchor(PositionAnchor.CENTER);
		myPlayer.setNoStroke(true);
		float w = myPlayer.getWidthXY(TransformSpace.GLOBAL);
		float h = myPlayer.getHeightXY(TransformSpace.GLOBAL);
		
		float a = (float) Math.sqrt((Math.pow(2*radius, 2))/(Math.pow(w, 2)+Math.pow(h, 2)));
		
		
		//TODO Rotate
		this.getMyShape().addChild(myPlayer);
		myPlayer.scale(a, a, a, myPlayer.getCenterPointGlobal());
		myPlayer.setPositionGlobal(new Vector3D(x,y));
		myPlayer.removeAllGestureEventListeners();
		myPlayer.setPickable(false);
		
		currentClip=movieClipName;
		myPlayer.loop();
		myPlayer.setVolume(0);
		
		if(order.indexOf(currentClip)>0){
			previous.setVisible(true);
			previous.setPickable(true);
		}else{
			previous.setVisible(false);
			previous.setPickable(false);
		}
		
		if(order.indexOf(currentClip)<(order.size()-1)){
			next.setVisible(true);
			next.setPickable(true);
		}else{
			next.setVisible(false);
			next.setPickable(false);
		}
		
		
	}else{
		System.err.println("get null ? :"+this.MovieClips2.get(movieClipName));
		myPlayer.stop();
		myPlayer.setMovie(this.MovieClips2.get(movieClipName));			
		myPlayer.loop();
		myPlayer.setVolume(0);
		currentClip=movieClipName;
	}

	if(order.indexOf(currentClip)>0){
		previous.setVisible(true);
		previous.setPickable(true);
	}else{
		previous.setVisible(false);
		previous.setPickable(false);
	}
	
	if(order.indexOf(currentClip)<(order.size()-1)){
		next.setVisible(true);
		next.setPickable(true);
	}else{
		next.setVisible(false);
		next.setPickable(false);
	}
	
	
	/*
		float x=getMyShape().getCenterPointGlobal().x;
		float y=getMyShape().getCenterPointGlobal().y;
		
		if(myPlayer!=null){
			
			x = myPlayer.getPosition(TransformSpace.GLOBAL).x;
			y = myPlayer.getPosition(TransformSpace.GLOBAL).y;
			
			myPlayer.stop();
			myPlayer.removeFromParent();
			myPlayer.destroyComponent();
			myPlayer.destroy();
			
		}
		myPlayer = new MTVideoTexture(MovieClips.get(movieClipName),new Vertex(0,0),this.scene.getMTApplication());
		//this.setTitle(movieClipName);
		myPlayer.setVolume(0);
		myPlayer.setAnchor(PositionAnchor.CENTER);
		myPlayer.setNoStroke(true);
		float w = myPlayer.getWidthXY(TransformSpace.GLOBAL);
		float h = myPlayer.getHeightXY(TransformSpace.GLOBAL);
		
		float a = (float) Math.sqrt((Math.pow(2*radius, 2))/(Math.pow(w, 2)+Math.pow(h, 2)));
		
		
		//TODO Rotate
		this.getMyShape().addChild(myPlayer);
		myPlayer.scale(a, a, a, myPlayer.getCenterPointGlobal());
		myPlayer.setPositionGlobal(new Vector3D(x,y));
	//	myPlayer.setPositionRelativeToOther(this.getMyShape(), new Vector3D(x,y));
		
		currentClip=movieClipName;
		myPlayer.loop();
		if(order.indexOf(currentClip)>0){
			previous.setVisible(true);
			previous.setPickable(true);
		}else{
			previous.setVisible(false);
			previous.setPickable(false);
		}
		
		if(order.indexOf(currentClip)<(order.size()-1)){
			next.setVisible(true);
			next.setPickable(true);
		}else{
			next.setVisible(false);
			next.setPickable(false);
		}
		*/
	}

	

	class IGotItButton extends MTRoundRectangle{
		
		MTTextField tF;
		
		public IGotItButton(float x, float y, float height,
				 IFont f) {
			super(x, y,0f,200f,height,10f,10f,scene.getMTApplication());

			tF = new MTTextField(scene.getMTApplication(), 0, 0, 150, f.getFontAbsoluteHeight()+2, f);


			tF.setInnerPadding(0);
			tF.setText("Ready to play");
			tF.setStrokeColor(MTColor.BLACK);
			tF.setNoStroke(true);
			tF.setNoFill(true);
			tF.removeAllGestureEventListeners();
			this.removeAllGestureEventListeners();
			tF.registerInputProcessor(new TapProcessor(scene.getMTApplication()));
			tF.addGestureListener(TapProcessor.class, new IGestureEventListener() {

				@Override
				public boolean processGestureEvent(MTGestureEvent ge) {
					TapEvent te = (TapEvent)ge;
					if (te.isTapped()){
						PC.reactToPopUpResponse(name,1);
						
						if(!Constants.isOnMac){
							myPlayer.stop();
							myPlayer.destroy();
						}

						PopupVideo.this.getMyShape().removeFromParent();
					}
					return false;
				}
			});

			tF.setAnchor(PositionAnchor.CENTER);
			tF.setPositionRelativeToOther(this, this.getCenterPointLocal());
			this.setStrokeColor(MTColor.PURPLE);
			this.setNoFill(true);
			
			this.addChild(tF);
		}

	}
	}
		
	
	
	
	

