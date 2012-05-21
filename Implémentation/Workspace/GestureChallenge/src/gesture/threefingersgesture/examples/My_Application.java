package gesture.threefingersgesture.examples;

import org.mt4j.MTApplication;
import org.mt4j.input.inputSources.MacTrackpadSource;

public class My_Application extends MTApplication{
	/**
	 * 
	 */
	private static final long serialVersionUID = 408886680407648330L;
	public static void main(String[] args){
		initialize();
		
	}
	public void startUp() {
		getInputManager().registerInputSource(new MacTrackpadSource(this));
		this.addScene(new My_Scene(this, "Test RŽmy"));
	}

}
