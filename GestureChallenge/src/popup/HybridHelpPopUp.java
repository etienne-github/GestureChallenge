package popup;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import model.Constants;
import model.GameModel;

import org.mt4j.components.TransformSpace;
import org.mt4j.components.visibleComponents.shapes.MTRoundRectangle;
import org.mt4j.components.visibleComponents.shapes.MTRectangle.PositionAnchor;
import org.mt4j.components.visibleComponents.widgets.MTTextArea;
import org.mt4j.components.visibleComponents.widgets.MTTextField;
import org.mt4j.components.visibleComponents.widgets.buttons.MTImageButton;
import org.mt4j.input.inputProcessors.IGestureEventListener;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapProcessor;
import org.mt4j.sceneManagement.AbstractScene;
import org.mt4j.util.MTColor;
import org.mt4j.util.font.FontManager;
import org.mt4j.util.font.IFont;
import org.mt4j.util.math.Vector3D;
import org.mt4j.util.math.Vertex;

import popup.video.MTVideoTexture;
import popup.video.PopupVideo;
import processing.core.PImage;
import scene.GestureChallengeScene;
import codeanticode.gsvideo.GSMovie;

public class HybridHelpPopUp extends MTRoundRectangle {
	HashMap<String,String> MovieClips;
	HashMap<String,GSMovie> MovieClips2;
	HashMap<String, HelpSequence> HelpSequences;
	ArrayList<String> order;
	MTVideoTexture myPlayer;
	PImage nextButtonImage;
	PImage previousButtonImage;
	String currentSequenceName="";
	MTImageButton next;
	MTImageButton previous;
	float height;
	float width;
	MTTextArea helpMessage;
	MTTextField title;
	GestureChallengeScene myScene;
	PopUpCreator myPC;
	VideoButton videoButton;
	float myAngle;
	String myName;
	
	
	
	
	public HybridHelpPopUp(final GameModel gm, final AbstractScene scene, String name, Vector3D position, float angle, PopUpCreator PC) {
		super(position.x, position.y,0f,200f,150f,10f,10f,scene.getMTApplication());
		
		myName=name;
		
		myPC=PC;
		myAngle=angle;
		this.myScene=(GestureChallengeScene) scene;
		
		width = this.getWidthXY(TransformSpace.GLOBAL);
		height = this.getHeightXY(TransformSpace.GLOBAL);
		
		this.setStrokeColor(MTColor.BLACK);
		//this.setFillColor(MTColor.GREY);
		this.setNoFill(true);
		this.setPickable(false);
		this.setPositionGlobal(position);
		this.removeAllGestureEventListeners();
		
		
		//title
		IFont f = FontManager.getInstance().createFont(scene.getMTApplication(), "REZ.ttf", 30);
		title = new MTTextField(scene.getMTApplication(),this.getCenterPointGlobal().x,this.getCenterPointGlobal().y-this.height/2f+(f.getFontAbsoluteHeight()+2)*1.5f-5,width,f.getFontAbsoluteHeight()+2,f);
		this.addChild(title);
		title.setFontColor(MTColor.BLACK);
		title.setPickable(false);
		title.setNoFill(true);
		title.setText("");
		title.setNoStroke(true);
		
		
		
		//help message
		f = FontManager.getInstance().createFont(scene.getMTApplication(), "REZ.ttf", 15);
		helpMessage = new MTTextArea(scene.getMTApplication(),this.getCenterPointGlobal().x,this.getCenterPointGlobal().y,this.getWidthXY(TransformSpace.GLOBAL),this.getHeightXY(TransformSpace.GLOBAL),f);
		this.addChild(helpMessage);
		helpMessage.setFontColor(MTColor.AQUA);
		helpMessage.setPickable(false);
		helpMessage.setNoFill(true);
		helpMessage.setNoStroke(true);

		//video player
		order=new ArrayList<String>();
		MovieClips = new HashMap<String,String>();	
		MovieClips2 = new HashMap<String,GSMovie>();
		HelpSequences = new HashMap<String,HelpSequence>();
		nextButtonImage= scene.getMTApplication().loadImage("."+((String)File.separator)+"src"+((String)File.separator)+"popup"+((String)File.separator)+"video"+((String)File.separator)+"data"+((String)File.separator)+"arrow-right.png");
		previousButtonImage= scene.getMTApplication().loadImage("."+((String)File.separator)+"src"+((String)File.separator)+"popup"+((String)File.separator)+"video"+((String)File.separator)+"data"+((String)File.separator)+"arrow-left.png");
		next= new MTImageButton(scene.getMTApplication(), nextButtonImage);
		previous= new MTImageButton(scene.getMTApplication(), previousButtonImage);
		next.setVisible(false);
		previous.setVisible(false);
		next.setPickable(false);
		previous.setPickable(false);
		next.setNoStroke(true);
		previous.setNoStroke(true);
		
		
		
		next.setPositionRelativeToOther(this, new Vector3D(this.getCenterPointLocal().x+width/2f+width/2f*0.85f,this.getCenterPointLocal().y+height/2f));
		previous.setPositionRelativeToOther(this, new Vector3D(this.getCenterPointLocal().x+width/2f-width/2f*0.85f,this.getCenterPointLocal().y+height/2f));
		next.scale(0.5f, 0.5f, 0.5f, next.getCenterPointGlobal());
		previous.scale(0.5f, 0.5f, 0.5f, previous.getCenterPointGlobal());
		
		this.addChild(next);
		this.addChild(previous);
		
		
		next.addGestureListener(TapProcessor.class, new IGestureEventListener(){

			@Override
			public boolean processGestureEvent(MTGestureEvent arg0) {
				TapEvent te = (TapEvent)arg0;
				if (te.isTapped()){
					
					System.out.println("titre suivant : "+order.get(order.indexOf(HybridHelpPopUp.this.currentSequenceName)+1));
					System.out.println();
					HybridHelpPopUp.this.playSequence(order.get(order.indexOf(HybridHelpPopUp.this.currentSequenceName)+1));
				}

				return false;
			}
			
		});
		
		previous.addGestureListener(TapProcessor.class, new IGestureEventListener(){

			@Override
			public boolean processGestureEvent(MTGestureEvent arg0) {
				TapEvent te = (TapEvent)arg0;
				if (te.isTapped()){
					HybridHelpPopUp.this.playSequence(order.get(order.indexOf(HybridHelpPopUp.this.currentSequenceName)-1));
				}

				return false;
			}
			
		});
		
		f = FontManager.getInstance().createFont(scene.getMTApplication(), "REZ.ttf", 15);
		
		ReadyToPlayButton b = new ReadyToPlayButton(this.getCenterPointLocal().x, this.getCenterPointLocal().y, f.getFontAbsoluteHeight()+2, f);
		b.setPositionRelativeToOther(this, new Vector3D(this.getCenterPointLocal().x+width/2f-(width/4f),this.getCenterPointLocal().y+height/2f+height/2f*0.8f));
		this.addChild(b);
		
		videoButton = new VideoButton(this.getCenterPointLocal().x, this.getCenterPointLocal().y, f.getFontAbsoluteHeight()+2, f);
		videoButton.setPositionRelativeToOther(this, new Vector3D(this.getCenterPointLocal().x+width/2f+(width/4f),this.getCenterPointLocal().y+height/2f+height/2f*0.8f));
		this.addChild(videoButton);

		
		
		
		

		this.rotateZ(position, (float) Math.toDegrees(angle)-90);

	}
	
	
	public GestureChallengeScene getMyScene(){
		return this.myScene;
	}
	
	public void addSequence(String name, HelpSequence sequence){


		HelpSequences.put(name, sequence);
		order.add(name);
		
		
		if(order.size()>1){
			next.setVisible(true);
			next.setPickable(true);
		}else{
			next.setVisible(false);
			next.setPickable(false);
		}
		
	}

	public void playSequence(String string) {
		
		videoButton.setPushed(false);
		
		if(!currentSequenceName.equals("")){
			HelpSequences.get(currentSequenceName).remove();
		}		
		HelpSequences.get(string).setUp();
		this.setTitle(string, MTColor.BLACK);
		
		currentSequenceName=string;
		
		if(!Constants.isOnMac){
			if(myPlayer==null){
				float x=HybridHelpPopUp.this.getCenterPointGlobal().x-width/2f;
				float y=HybridHelpPopUp.this.getCenterPointGlobal().y-height/2f;
				

				System.err.println("string : "+string);
				System.err.println("movie path : "+HelpSequences.get(string).moviePath);
				myPlayer = new MTVideoTexture(HelpSequences.get(string).moviePath,new Vertex(0,0),this.myScene.getMTApplication());
				myPlayer.setVolume(0);
				myPlayer.setAnchor(PositionAnchor.CENTER);
				myPlayer.setNoStroke(true);
				
				float w = myPlayer.getWidthXY(TransformSpace.GLOBAL);
				float h = myPlayer.getHeightXY(TransformSpace.GLOBAL);
				
				float a;
				
				
				if(HybridHelpPopUp.this.width>HybridHelpPopUp.this.height){
					a = HybridHelpPopUp.this.width*0.8f/(float)w;
				}else{
					a = HybridHelpPopUp.this.height*0.8f/(float)h;
				}
				
				System.err.println("scale a : "+a);
				//float a = (float) Math.sqrt((Math.pow(2*radius, 2))/(Math.pow(w, 2)+Math.pow(h, 2)));
				
				
				//TODO Rotate
				this.addChild(myPlayer);
				
				myPlayer.scale(a, a, a, myPlayer.getCenterPointGlobal());
				//myPlayer.setPositionGlobal(new Vector3D(x+(w*a/2f),(y+h*a/2f)));
				
				myPlayer.setPositionGlobal(this.getCenterPointGlobal());
				myPlayer.removeAllGestureEventListeners();
				myPlayer.setPickable(false);
				
				currentSequenceName=string;
				myPlayer.stop();
				myPlayer.setVolume(0);
				myPlayer.setVisible(false);
				
				if(order.indexOf(currentSequenceName)>0){
					previous.setVisible(true);
					previous.setPickable(true);
				}else{
					previous.setVisible(false);
					previous.setPickable(false);
				}
				
				if(order.indexOf(currentSequenceName)<(order.size()-1)){
					next.setVisible(true);
					next.setPickable(true);
				}else{
					next.setVisible(false);
					next.setPickable(false);
				}
				
				
				
			}else{
				myPlayer.stop();
				myPlayer.setMovie(HelpSequences.get(string).movieClip);			
				myPlayer.stop();
				myPlayer.setVolume(0);
				currentSequenceName=string;
				myPlayer.setVisible(false);
			

			if(order.indexOf(currentSequenceName)>0){
				previous.setVisible(true);
				previous.setPickable(true);
			}else{
				previous.setVisible(false);
				previous.setPickable(false);
			}
			
			if(order.indexOf(currentSequenceName)<(order.size()-1)){
				next.setVisible(true);
				next.setPickable(true);
			}else{
				next.setVisible(false);
				next.setPickable(false);
			}

		}
	}	
		
		if(order.indexOf(currentSequenceName)>0){
			previous.setVisible(true);
			previous.setPickable(true);
		}else{
			previous.setVisible(false);
			previous.setPickable(false);
		}
		
		if(order.indexOf(currentSequenceName)<(order.size()-1)){
			next.setVisible(true);
			next.setPickable(true);
		}else{
			next.setVisible(false);
			next.setPickable(false);
		}
}


	public void setHelpMessage(String text, MTColor color){
		this.helpMessage.setText(text);
		this.helpMessage.setFontColor(color);
	}
	
	public void setTitle(String text, MTColor color){
		this.title.setText(text);
		this.title.setFontColor(color);
	}
	
	
	
	
class VideoButton extends MTRoundRectangle{
		
		MTTextField tF;
		boolean isPushed=false;
		
		public void setPushed(boolean pushed){
			if(pushed){
				tF.setText("      I got it");
			}else{
				tF.setText("      Show me");
			}
			isPushed=pushed;
		}
		
		public VideoButton(float x, float y, float height,
				 IFont f) {
			super(x, y,0f,HybridHelpPopUp.this.width/2f-5,height,10f,10f,myScene.getMTApplication());

			tF = new MTTextField(myScene.getMTApplication(), 0, 0, HybridHelpPopUp.this.width/2f-5, f.getFontAbsoluteHeight()+2, f);


			tF.setInnerPadding(0);
			tF.setText("      Show me");
			tF.setStrokeColor(MTColor.BLACK);
			tF.setNoStroke(true);
			tF.setNoFill(true);
			tF.removeAllGestureEventListeners();
			this.removeAllGestureEventListeners();
			tF.registerInputProcessor(new TapProcessor(myScene.getMTApplication()));
			tF.addGestureListener(TapProcessor.class, new IGestureEventListener() {

				@Override
				public boolean processGestureEvent(MTGestureEvent ge) {
					TapEvent te = (TapEvent)ge;
					if (te.isTapped()){
						myPC.reactToPopUpResponse(getName(),1);
						
						if(!Constants.isOnMac){
							if(myPlayer!=null){
								if(!isPushed){
									isPushed=true;
									myPlayer.setVisible(true);
									myPlayer.loop();
									myPlayer.setVolume(0);
									tF.setText("      I got it");
								}else{
									isPushed=false;
									myPlayer.stop();
									myPlayer.setVolume(0);
									myPlayer.setVisible(false);	
									tF.setText("      Show me");
								}
								
							}							
						}
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
	
	
	
class ReadyToPlayButton extends MTRoundRectangle{
		
		MTTextField tF;
		
		public ReadyToPlayButton(float x, float y, float height,
				 IFont f) {
			super(x, y,0f,HybridHelpPopUp.this.width/2f-5,height,10f,10f,myScene.getMTApplication());

			tF = new MTTextField(myScene.getMTApplication(), 0, 0, HybridHelpPopUp.this.width/2f-5, f.getFontAbsoluteHeight()+2, f);


			tF.setInnerPadding(0);
			tF.setText("       Ready to play");
			tF.setStrokeColor(MTColor.BLACK);
			tF.setNoStroke(true);
			tF.setNoFill(true);
			tF.removeAllGestureEventListeners();
			this.removeAllGestureEventListeners();
			tF.registerInputProcessor(new TapProcessor(myScene.getMTApplication()));
			tF.addGestureListener(TapProcessor.class, new IGestureEventListener() {

				@Override
				public boolean processGestureEvent(MTGestureEvent ge) {
					TapEvent te = (TapEvent)ge;
					if (te.isTapped()){
						
						
						myPC.reactToPopUpResponse(myName,1);
						
						if(!Constants.isOnMac){
							if(myPlayer!=null){
								myPlayer.stop();
								myPlayer.destroy();
							}							
						}
						//System.err.println("current name : "+currentSequenceName);
						//System.err.println("help seq : "+HybridHelpPopUp.this.HelpSequences);
						HybridHelpPopUp.this.HelpSequences.get(currentSequenceName).remove();
						HybridHelpPopUp.this.removeFromParent();
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
