package popup;

import model.Constants;

import org.jbox2d.dynamics.World;
import org.mt4j.components.TransformSpace;
import org.mt4j.components.visibleComponents.shapes.MTEllipse;
import org.mt4j.components.visibleComponents.widgets.MTTextField;
import org.mt4j.util.MTColor;
import org.mt4j.util.font.FontManager;
import org.mt4j.util.math.ToolsMath;
import org.mt4j.util.math.Vector3D;


import physic.shape.PhysicsCircle;
import processing.core.PApplet;
import scene.GestureChallengeScene;

public class FakePlayerAimGoal extends PhysicsCircle {
	

	IntermediateHelpSequence myHS;
	
	
	public FakePlayerAimGoal(PApplet applet, Vector3D centerPoint,
			World world,
			float worldScale, MTColor color, float angle, GestureChallengeScene s, IntermediateHelpSequence hS) {
		super(applet, centerPoint, Constants.radiusGoals, world, 0f, 0f, 0f,
				worldScale);
		myHS=hS;
		//MTColor col1 = new MTColor(ToolsMath.getRandom(60, 255),ToolsMath.getRandom(60, 255),ToolsMath.getRandom(60, 255));
		this.setName("FakePlayerAimGoal");
		this.setFillColor(color);
		//color = MTColor.WHITE;
		MTColor darker = new MTColor(color.getR()/2f, color.getG()/2f, color.getB()/2f);

		
		//color.setColor(color.getR()-50, color.getG()-30, color.getB()-30);
		this.setStrokeColor(darker);
		this.setStrokeWeight(4);
		
		MTTextField jdisp = new MTTextField(applet, 0,0, 30f, 10f, FontManager.getInstance().createFont(applet, "SansSerif", 10));
		jdisp.scale(0.1f, 0.1f, 0.1f, jdisp.getCenterPointLocal());
		jdisp.setInnerPadding(0);
		
		MTEllipse c1 = new MTEllipse(applet,new Vector3D(this.getCenterPointGlobal().x*worldScale,this.getCenterPointGlobal().y*worldScale),(Constants.radiusGoals*7/10f)/worldScale,(Constants.radiusGoals*7/10f)/worldScale);
		c1.setNoStroke(true);
		c1.setFillColor(darker);
		c1.removeAllGestureEventListeners();
		c1.setPickable(false);
		
		MTEllipse c2 = new MTEllipse(applet,new Vector3D(this.getCenterPointGlobal().x*worldScale,this.getCenterPointGlobal().y*worldScale),(Constants.radiusGoals*5/10f)/worldScale,(Constants.radiusGoals*5/10f)/worldScale);
		c2.setNoStroke(true);
		c2.setFillColor(color);
		c2.removeAllGestureEventListeners();
		c2.setPickable(false);
		
		MTEllipse c3 = new MTEllipse(applet,new Vector3D(this.getCenterPointGlobal().x*worldScale,this.getCenterPointGlobal().y*worldScale),(Constants.radiusGoals*2.75f/10f)/worldScale,(Constants.radiusGoals*2.75f/10f)/worldScale);
		c3.setNoStroke(true);
		c3.setFillColor(darker);
		c3.removeAllGestureEventListeners();
		c3.setPickable(false);
		

		
		this.addChild(c1);
		c1.setPositionRelativeToOther(this, this.getCenterPointLocal());
		
		this.addChild(c2);
		c2.setPositionRelativeToOther(this, this.getCenterPointLocal());
		
		this.addChild(c3);
		c3.setPositionRelativeToOther(this, this.getCenterPointLocal());
		
		
		this.addChild(jdisp);
		jdisp.setPositionGlobal(new Vector3D(this.getCenterPointGlobal().x,this.getCenterPointGlobal().y));
	//	System.out.println("disp = "+jdisp.getPosition(TransformSpace.GLOBAL));
		jdisp.setFontColor(MTColor.WHITE);
		jdisp.setNoFill(true);
		jdisp.setNoStroke(true);
		jdisp.setText("GOAL");
		jdisp.setPickable(false);
		jdisp.rotateZ(jdisp.getCenterPointGlobal(), (float) Math.toDegrees(angle));
		//jdisp.rotateZGlobal(jdisp.getCenterPointGlobal(), angle);
		jdisp.removeAllGestureEventListeners();
		
		//s.getCanvas().addChild(c1);
		//c1.setPositionGlobal(new Vector3D(this.getCenterPointGlobal().x*worldScale,this.getCenterPointGlobal().y*worldScale-70));
	}

}
