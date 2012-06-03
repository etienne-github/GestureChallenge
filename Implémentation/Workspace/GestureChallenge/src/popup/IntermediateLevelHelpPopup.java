package popup;

import gesture.threefingersgesture.ThreeFingersAverageGestureEvent;
import gesture.threefingersgesture.ThreeFingersAverageGestureProcessor;
import gesture.threefingersgesture.ThreeFingersGestureEvent;
import gesture.threefingersgesture.ThreeFingersGestureProcessor;
import model.GameModel;

import org.mt4j.MTApplication;
import org.mt4j.components.TransformSpace;
import org.mt4j.components.visibleComponents.shapes.AbstractShape;
import org.mt4j.components.visibleComponents.shapes.MTRectangle;
import org.mt4j.components.visibleComponents.widgets.MTTextArea;
import org.mt4j.input.inputProcessors.IGestureEventListener;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.sceneManagement.AbstractScene;
import org.mt4j.util.MTColor;
import org.mt4j.util.font.FontManager;
import org.mt4j.util.math.Vector3D;

public class IntermediateLevelHelpPopup extends HelpPopup{

	private MTRectangle myPlot;
	public IntermediateLevelHelpPopup(GameModel gm, AbstractScene scene,
			String name, Vector3D position, float angle) {
		super(gm, scene, name, position, angle);
		AbstractShape.createDefaultGestures = false;
		MTApplication app = (MTApplication) scene.getMTApplication();
		myPlot = new MTRectangle(120, 120, app);
		//myPlot.setStrokeColor(MTColor.GREEN);
		myPlot.setStrokeWeight(4);
		myPlot.setPositionGlobal(new Vector3D(getCenterPointGlobal().x+this.getWidthXYGlobal()/2, getCenterPointGlobal().y+this.getHeightXYGlobal()/2));
		myPlot.setStrokeColor(MTColor.WHITE);
		myPlot.setFillColor(MTColor.LIME);
		this.addChild(myPlot);
		MTTextArea tA = new MTTextArea(app, position.x, position.y, myPlot.getWidthXY(TransformSpace.GLOBAL), myPlot.getHeightXY(TransformSpace.GLOBAL), FontManager.getInstance().createFont(app, "arial", 15));
		tA.setText("Rotate me with 3 fingers !");
		tA.setNoFill(true);
		tA.removeAllGestureEventListeners();
		tA.setPickable(false);
		tA.setNoStroke(true);
		myPlot.addChild(tA);
		myPlot.registerInputProcessor(new ThreeFingersAverageGestureProcessor(app));
		myPlot.addGestureListener(ThreeFingersAverageGestureProcessor.class, new IGestureEventListener() {
			public boolean processGestureEvent(MTGestureEvent ge) {
				ThreeFingersAverageGestureEvent evt = (ThreeFingersAverageGestureEvent) ge;
				float angle = evt.getRotationAngleDegree();
				System.out.println(angle);
				myPlot.rotateZ(new Vector3D(getCenterPointGlobal().x+getWidthXYGlobal()/2, getCenterPointGlobal().y+getHeightXYGlobal()/2), angle);
				return false;
			}
		});
	}

}