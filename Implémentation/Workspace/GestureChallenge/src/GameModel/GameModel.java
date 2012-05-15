package GameModel;

import org.mt4j.util.MTColor;
import org.mt4j.util.math.ToolsMath;

import GestureChallengeScene.GestureChallengeScene;
import PlayerInterface.PlayerInterface;

public class GameModel {
	int playerNumber;
	int levelNumber;
	MTColor[] playerColors;
	GestureChallengeScene myGCS;
	PlayerInterface[] myPI;
	
	public GameModel(GestureChallengeScene gCS){
		
		playerColors= new MTColor[4];
		playerColors[0]=new MTColor(188f,140f,255f); //Purple
		playerColors[1]=new MTColor(255f,190f,0f);
		playerColors[2]=new MTColor(0f,217f,255f);
		playerColors[3]=new MTColor(136f,255f,89f);
		myGCS = gCS;
	}
	
	public void createInterfaces(){
		myPI = new PlayerInterface[playerNumber];
		for(int i=0;i<playerNumber;i++){
			float angle =((float) ((i+1)*2*Math.PI/(playerNumber)+Math.PI/2f));		
			if(i<4){
				myPI[i]=new PlayerInterface(playerColors[i],i,angle,myGCS,playerNumber);
			}else{
				MTColor col = new MTColor(ToolsMath.getRandom(60, 255),ToolsMath.getRandom(60, 255),ToolsMath.getRandom(60, 255));
				myPI[i]=new PlayerInterface(col,i,angle,myGCS,playerNumber);
			}
			
		}
	}
	
	public int getPlayerNumber() {
		return playerNumber;
	}
	public void setPlayerNumber(int playerNumber) {
		this.playerNumber = playerNumber;
	}
	public int getLevelNumber() {
		return levelNumber;
	}
	public void setLevelNumber(int levelNumber) {
		this.levelNumber = levelNumber;
	}
	
}
