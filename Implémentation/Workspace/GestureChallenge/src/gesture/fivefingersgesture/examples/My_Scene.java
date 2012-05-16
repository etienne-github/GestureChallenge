package gesture.fivefingersgesture.examples;

import gesture.fivefingersgesture.FiveFingersGestureEvent;
import gesture.fivefingersgesture.FiveFingersGestureProcessor;

import org.mt4j.AbstractMTApplication;
import org.mt4j.components.visibleComponents.shapes.AbstractShape;
import org.mt4j.components.visibleComponents.shapes.MTEllipse;
import org.mt4j.components.visibleComponents.shapes.MTRectangle;
import org.mt4j.input.inputProcessors.IGestureEventListener;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.globalProcessors.CursorTracer;
import org.mt4j.sceneManagement.AbstractScene;
import org.mt4j.util.MTColor;
import org.mt4j.util.logging.ILogger;
import org.mt4j.util.logging.MTLoggerFactory;
import org.mt4j.util.math.Vector3D;
import org.mt4j.util.opengl.GLFBO;


public class My_Scene extends AbstractScene{


	/** The app. */
	private AbstractMTApplication app;

	/** The has fbo. */
	private boolean hasFBO;

	/** The switch directly to scene. */
	private boolean switchDirectlyToScene = false;

	private MTRectangle myPlot;
	private MTEllipse myEllipse;

	public My_Scene(final AbstractMTApplication mtApplication, String name) {
		super(mtApplication, name);
		this.app = mtApplication;
		this.hasFBO = GLFBO.isSupported(app);
		this.switchDirectlyToScene = !this.hasFBO || switchDirectlyToScene;
		this.registerGlobalInputProcessor(new CursorTracer(app, this));
		AbstractShape.createDefaultGestures = false;

		myPlot = new MTRectangle(700, 700, mtApplication);
		myPlot.setStrokeColor(MTColor.GREEN);
		this.getCanvas().addChild(myPlot);




		myPlot.translate(new Vector3D(200, 300));
		myPlot.registerInputProcessor(new FiveFingersGestureProcessor(mtApplication));
		myPlot.addGestureListener(FiveFingersGestureProcessor.class, new IGestureEventListener() {
			public boolean processGestureEvent(MTGestureEvent ge) {
				FiveFingersGestureEvent evt = (FiveFingersGestureEvent) ge;
				float angle = evt.getRotationAngleDegree();
				System.out.println(angle);
				myPlot.rotateZ(myPlot.getCenterPointGlobal(), angle);
				return false;
			}
		});
	}

}