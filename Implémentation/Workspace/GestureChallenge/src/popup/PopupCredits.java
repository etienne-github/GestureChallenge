package popup;

import java.io.File;
import java.util.ArrayList;

import model.Constants;

import org.mt4j.components.TransformSpace;
import org.mt4j.components.visibleComponents.shapes.MTEllipse;
import org.mt4j.components.visibleComponents.shapes.MTRectangle;
import org.mt4j.components.visibleComponents.shapes.MTRoundRectangle;
import org.mt4j.components.visibleComponents.shapes.MTRectangle.PositionAnchor;
import org.mt4j.components.visibleComponents.widgets.MTTextField;
import org.mt4j.input.inputProcessors.IGestureEventListener;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapProcessor;
import org.mt4j.util.MTColor;
import org.mt4j.util.font.FontManager;
import org.mt4j.util.font.IFont;
import org.mt4j.util.math.Vector3D;

import processing.core.PApplet;
import processing.core.PImage;

import scene.GestureChallengeScene;

public class PopupCredits extends Popup {
	PImage etienneImage;
	PImage remyImage;
	PImage loicImage;
	PImage lucileImage;
	GestureChallengeScene myScene;
	ArrayList<CreditsItem> myItem=new ArrayList<CreditsItem>();
	IFont f;
	IFont f2;
	IFont f3;
	PopUpCreator myPC;
	String myName;
	String previousPopUpName;
	
	public PopupCredits(String name,String previousPopUpName,
			GestureChallengeScene s, PopUpCreator PC, Vector3D centerPosition,
			float radius) {
		super(MTEllipse.class, name, "        Credits", s, PC, centerPosition, radius);
		myScene=s;
		myPC = PC;
		myName=name;
		this.previousPopUpName=previousPopUpName;
		
		
		f = FontManager.getInstance().createFont(PopupCredits.this.myScene.getMTApplication(), "neuropolitical rg.otf", 30);
		f2 = FontManager.getInstance().createFont(PopupCredits.this.myScene.getMTApplication(), "neuropolitical rg.otf", 15);
		f3=f2;
		
		etienneImage=myScene.getMTApplication().loadImage("."+((String)File.separator)+"src"+((String)File.separator)+"popup"+((String)File.separator)+"data"+((String)File.separator)+"etienne_avatar.jpg");
		remyImage=myScene.getMTApplication().loadImage("."+((String)File.separator)+"src"+((String)File.separator)+"popup"+((String)File.separator)+"data"+((String)File.separator)+"remy_avatar.jpg");
		loicImage=myScene.getMTApplication().loadImage("."+((String)File.separator)+"src"+((String)File.separator)+"popup"+((String)File.separator)+"data"+((String)File.separator)+"loic_avatar.jpg");
		lucileImage=myScene.getMTApplication().loadImage("."+((String)File.separator)+"src"+((String)File.separator)+"popup"+((String)File.separator)+"data"+((String)File.separator)+"lucile_avatar.jpg");

		
		
		this.addCreditsItem(new CreditsItem(this.getMyShape().getCenterPointGlobal().x,this.getMyShape().getCenterPointGlobal().y,75, etienneImage,"Etienne Girot","etienne.girot@gmail.com - UTCompiègne",myScene.getMTApplication()));
		this.addCreditsItem(new CreditsItem(this.getMyShape().getCenterPointGlobal().x,this.getMyShape().getCenterPointGlobal().y,75, remyImage,"Rémy Frenoy","rfrenoy@gmail.com - UTCompiègne",myScene.getMTApplication()));
		this.addCreditsItem(new CreditsItem(this.getMyShape().getCenterPointGlobal().x,this.getMyShape().getCenterPointGlobal().y,75, loicImage,"Loic Picavet","loic.picavet@gmail.com - UTCompiègne",myScene.getMTApplication()));
		this.addCreditsItem(new CreditsItem(this.getMyShape().getCenterPointGlobal().x,this.getMyShape().getCenterPointGlobal().y,75, lucileImage,"Lucile Callebert","lucile.callebert@gmail.com - UTCompiègne",myScene.getMTApplication()));
		this.addCreditsItem(new CreditsItem(this.getMyShape().getCenterPointGlobal().x,this.getMyShape().getCenterPointGlobal().y,75, null,"Music : Hicham Chahidi","",myScene.getMTApplication()));

		
		IFont bf = FontManager.getInstance().createFont(PopupCredits.this.myScene.getMTApplication(), "REZ.ttf", 30);

		BackButton b = new BackButton(this.getMyShape().getCenterPointGlobal().x,this.getMyShape().getCenterPointGlobal().y,50,bf);
		b.setPositionRelativeToOther(this.getMyShape(), new Vector3D(this.getMyShape().getCenterPointLocal().x/*-this.getMyShape().getWidthXY(TransformSpace.GLOBAL)/2f+b.getWidthXY(TransformSpace.GLOBAL)/2f*/,this.getMyShape().getCenterPointLocal().x+100));
		this.getMyShape().addChild(b);
	}
	
	
	public void addCreditsItem(CreditsItem c){
		this.myItem.add(c);
		
		float x = this.getMyShape().getCenterPointLocal().x;
		float y = this.getMyShape().getCenterPointLocal().y+(myItem.size()-1)*c.getHeightXY(TransformSpace.GLOBAL)-135;		
		Vector3D v = new Vector3D(x,y);
		
		
		c.setPositionRelativeToOther(this.getMyShape(), v);
		this.getMyShape().addChild(c);
		c.setPickable(false);
		
	}
	
	
	class CreditsItem extends MTRectangle{

		public CreditsItem(float x, float y, float height, PImage image, String name, String email, PApplet pApplet) {
			super(x, y,0f,500f,height,scene.getMTApplication());
			
			
			float w = this.getWidthXY(TransformSpace.GLOBAL);
			float h = this.getHeightXY(TransformSpace.GLOBAL);
			
			//photo
			
			float xP;
			float yP;
			float wP;
			float hP;
			Vector3D P;
			
			if(image!=null){
				MTRectangle photo = new MTRectangle(image,PopupCredits.this.myScene.getMTApplication());
				
				this.setAnchor(PositionAnchor.UPPER_LEFT);
				photo.setAnchor(PositionAnchor.UPPER_LEFT);
				photo.scale(0.3f, 0.3f, 0.3f, photo.getCenterPointGlobal());
				
				wP = photo.getWidthXY(TransformSpace.GLOBAL);
				hP = photo.getHeightXY(TransformSpace.GLOBAL);
				
				xP = this.getCenterPointLocal().x-w/2f+wP/2f;
				yP = this.getCenterPointLocal().y;

				P = new Vector3D(xP,yP);
				photo.setStrokeColor(MTColor.BLACK);
				photo.setPickable(false);
				photo.setPositionRelativeToOther(this, P);
				this.addChild(photo);
				
				System.out.println("photo : "+xP+" "+yP+" "+wP+" "+hP);
				
			}else{

				wP=60.000004f;
				hP=60.000004f;
				xP = this.getCenterPointLocal().x-w/2f+wP/2f;
				yP = this.getCenterPointLocal().y;
				P = new Vector3D(xP,yP);

			}

			
			
			
			
			
			//name
			MTTextField tFname;
			if(image!=null){
				tFname = new MTTextField(PopupCredits.this.myScene.getMTApplication(), 0, 0, 400, PopupCredits.this.f.getFontAbsoluteHeight()+5, f);
			}else{
				tFname = new MTTextField(PopupCredits.this.myScene.getMTApplication(), 0, 0, 400, PopupCredits.this.f.getFontAbsoluteHeight()+5, f3);
			}
			
			tFname.setText(name);
			
			//tFname.setStrokeColor(MTColor.FUCHSIA);
			
			float wN = tFname.getWidthXY(TransformSpace.GLOBAL);
			float hN= tFname.getHeightXY(TransformSpace.GLOBAL);
			
			
			float xN = xP+wN/2f+wP/2f;
			float yN = yP-hP/2f+hN/2f;

			P = new Vector3D(xN,yN);
			tFname.setPositionRelativeToOther(this, P);

			

			
			
			//email
			MTTextField tFemail = new MTTextField(PopupCredits.this.myScene.getMTApplication(), 0, 0, 400, f2.getFontAbsoluteHeight()+5, f2);
			tFemail.setText(email);
			
			float wE = tFemail.getWidthXY(TransformSpace.GLOBAL);
			float hE= tFemail.getHeightXY(TransformSpace.GLOBAL);
			
			float xE = xP+wE/2f+wP/2f;
			float yE = yP+hP/2f-hE/2f;
			
			P = new Vector3D(xE,yE);
			tFemail.setPositionRelativeToOther(this, P);
			//tFemail.setStrokeColor(MTColor.RED);
			
			
			tFname.setPickable(false);
			tFemail.setPickable(false);
			
			
			this.addChild(tFname);
			this.addChild(tFemail);
			
			
			//this.setStrokeColor(MTColor.BLACK);
			this.setNoFill(true);
			this.setNoStroke(true);
			tFemail.setNoFill(true);
			tFemail.setNoStroke(true);
			tFname.setNoFill(true);
			tFname.setNoStroke(true);

			
		}
		
	}
	
class BackButton extends MTRoundRectangle{
		
		MTTextField tF;
		
		public BackButton(float x, float y, float height,IFont f) {
			super(x, y,0f,PopupCredits.this.getMyShape().getWidthXY(TransformSpace.GLOBAL)/2f,height,10f,10f,myScene.getMTApplication());

			tF = new MTTextField(myScene.getMTApplication(), 0, 0, PopupCredits.this.getMyShape().getWidthXY(TransformSpace.GLOBAL)/2f, f.getFontAbsoluteHeight()+2, f);


			tF.setInnerPadding(0);
			tF.setText("                               Back");
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
						
						
						myPC.reactToPopUpResponse(myName,previousPopUpName);

						PopupCredits.this.getMyShape().removeFromParent();
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
