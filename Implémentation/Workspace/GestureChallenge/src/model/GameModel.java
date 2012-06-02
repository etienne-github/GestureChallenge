package model;

import java.beans.PropertyChangeSupport;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import org.jbox2d.dynamics.Body;
import org.mt4j.AbstractMTApplication;
import org.mt4j.components.MTComponent;
import org.mt4j.components.visibleComponents.shapes.MTEllipse;
import org.mt4j.components.visibleComponents.shapes.MTRoundRectangle;
import org.mt4j.sceneManagement.Iscene;
import org.mt4j.util.MTColor;
import org.mt4j.util.math.ToolsMath;
import org.mt4j.util.math.Vector3D;

import codeanticode.gsvideo.GSMovie;

import playerinterface.PlayerInterface;
import popup.PopUpCreator;
import popup.Popup;
import popup.PopupLogo;
import popup.RankingPopup;
import popup.touch.PopupNbPlayers;
import popup.video.PopupVideo;
import scene.GestureChallengeScene;
import scene.menu.GCSceneMenu;


public class GameModel implements PopUpCreator {
	PropertyChangeSupport support=new PropertyChangeSupport(this);
	int playerNumber;
	int levelNumber;
	boolean explanationActivated;
	MTColor[] playerColors;
	GestureChallengeScene myGCS;
	PlayerInterface[] myPI;
	int[] ranking;
	Timer myTimer;
	TimerTask endGame;
	TimerTask infoTimeleft;
	int numberOfFinishedTutos;
	GSMovie m1;
	GSMovie m2;
	GSMovie m3;
	GSMovie m4;
	ArrayList<RankingPopup> rankingPopup=new ArrayList<RankingPopup>();

	public GameModel(GestureChallengeScene gCS){

		playerColors= new MTColor[4];
		playerColors[0]=new MTColor(188f,140f,255f); //Purple
		playerColors[1]=new MTColor(255f,190f,0f);
		playerColors[2]=new MTColor(0f,217f,255f);
		playerColors[3]=new MTColor(136f,255f,89f);
		myGCS = gCS;
		
		if(!Constants.isOnMac){
			m1 = new GSMovie(gCS.getMTApplication(),"."+((String)File.separator)+"src"+((String)File.separator)+"popup"+((String)File.separator)+"video"+((String)File.separator)+"data"+((String)File.separator)+"envoie_projectile.avi",30);
			m2 = new GSMovie(gCS.getMTApplication(),"."+((String)File.separator)+"src"+((String)File.separator)+"popup"+((String)File.separator)+"video"+((String)File.separator)+"data"+((String)File.separator)+"bouclier.avi",30);
			m3 = new GSMovie(gCS.getMTApplication(),"."+((String)File.separator)+"src"+((String)File.separator)+"popup"+((String)File.separator)+"video"+((String)File.separator)+"data"+((String)File.separator)+"pan.avi",30);
			m4 = new GSMovie(gCS.getMTApplication(),"."+((String)File.separator)+"src"+((String)File.separator)+"popup"+((String)File.separator)+"video"+((String)File.separator)+"data"+((String)File.separator)+"pan2.avi",30);
		}
		

	}

	public void createInterfaces(){
		myPI = new PlayerInterface[playerNumber];
		ranking = new int[playerNumber];

		for(int i=0;i<playerNumber;i++){
			float angle =((float) ((i+1)*2*Math.PI/(playerNumber)+Math.PI/2f));		
			if(i<4){
				myPI[i]=new PlayerInterface(playerColors[i],i,angle,myGCS,playerNumber, levelNumber);
			}else{
				MTColor col = new MTColor(ToolsMath.getRandom(60, 255),ToolsMath.getRandom(60, 255),ToolsMath.getRandom(60, 255));
				myPI[i]=new PlayerInterface(col,i,angle,myGCS,playerNumber, levelNumber);
			}
			ranking[i]=playerNumber;
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

	public void updateRanking(){
		int[] scores = new int[playerNumber];
		int[] sortedscores = new int[playerNumber];
		for(int i=0;i<playerNumber;i++){
			scores[i]=myPI[i].getMyScore();
			sortedscores[i]=myPI[i].getMyScore();
		}

		Arrays.sort(sortedscores);
		int rank=playerNumber+1;
		int lastworse=-1;
		int equals=1;
		boolean[] assigned = new boolean[playerNumber];
		for(boolean i:assigned){
			i=false;
		}

		//System.out.println("scores are: "+Arrays.toString(scores));	
		//System.out.println("orderedScores are: "+Arrays.toString(sortedscores));

		for(int i=0;i<playerNumber;i++){
			for(int j=0;j<playerNumber;j++){
				if(!assigned[j]){
					if(sortedscores[i]==scores[j]){
						//System.out.println("score "+sortedscores[i]+" found for P"+j);
						if(scores[j]>lastworse){
							lastworse=scores[j];
							rank-=equals;
							equals=1;
						}else{
							equals++;
						}
						//System.out.println("assigning rank "+rank+" to P"+j);
						ranking[j]=rank;
						assigned[j]=true;
					}
				}

			}

		}

		//System.out.println("ranks are: "+Arrays.toString(ranking));
		fireRanks();
	}

	public PropertyChangeSupport getSupport() {
		return support;
	}

public void fireRanks(){
	for(int i=0;i<playerNumber;i++){
		//System.out.println("firing : ranking"+i);
		support.firePropertyChange("ranking"+i,null,String.valueOf(ranking[i]));
	}
}

	public void subscribeInterfaces() {
		for(PlayerInterface i:myPI){
			support.addPropertyChangeListener(i);
		}

	}

	public void emptyScene(){


		//Desactiver les pan encore sur la scene
				for(PlayerInterface PI:myPI){
					if(levelNumber>1){
						PI.getMyThreeFingersProcessor().unLockAllCursors();
						if(levelNumber>2){
							PI.getMyPanProcessor().unLockAllCursors();
						}
					}
				}


		//Empty world
		for (Body b=myGCS.getWorld().getBodyList();
			     b != null;
			     b = b.getNext()){

			final Body ba=b;
			myGCS.getMTApplication().invokeLater(new Runnable(){

				@Override
				public void run() {
					myGCS.getWorld().destroyBody(ba);

				}

			});


		}

		//Empty physic Container
		MTComponent[] m = myGCS.getPhysicsContainer().getChildren();
		for(MTComponent c:m){
			final MTComponent ca=c;
			myGCS.getMTApplication().invokeLater(new Runnable(){

				@Override
				public void run() {
					myGCS.getPhysicsContainer().removeChild(ca);

				}

			});

		}

		//remove texts from scene
		m = myGCS.getCanvas().getChildren();
		for(MTComponent c:m){
			final MTComponent ca=c;
			//System.out.println("Found : "+ca.getClass().getName().toString());
			if(ca.getClass().getName().compareTo("org.mt4j.components.visibleComponents.widgets.MTTextField")==0){
				myGCS.getMTApplication().invokeLater(new Runnable(){

					@Override
					public void run() {
						myGCS.getCanvas().removeChild(ca);

					}

				});
			}


		}


	}

	public void cancelGameTimers(){
		for(PlayerInterface PI:myPI){
			PI.cancelTimers();
		}
	}


	public void newGame(){




	/*	Popup p = new Popup<Integer>("player_number","Number of players ?", myGCS, this, new Vector3D(myGCS.getMTApplication().width/2f,myGCS.getMTApplication().height/2f), 300);
		
		p.addPopupItem("2 players", 2);
		p.addPopupItem("3 players", 3);
		p.addPopupItem("4 players", 4);*/

		
		popHomePopup();
		

	}
	
	
	
	
	
	public void popVideoPopup(){
		
		for(int i=0;i<playerNumber;i++){
			float angle =((float) ((i+1)*2*Math.PI/(playerNumber)+Math.PI/2f));	
			float x = myGCS.getMTApplication().width/2f +(float) (Math.cos(angle)*Constants.radiusCenterGoals);
			float y = myGCS.getMTApplication().height/2f +(float) (Math.sin(angle)*Constants.radiusCenterGoals);

			PopupVideo p = new PopupVideo(MTEllipse.class, "tuto_popup" ,myGCS,this,new Vector3D(myGCS.getMTApplication().width/2f,myGCS.getMTApplication().height/2f),300,0.3f);  
			
			if(!Constants.isOnMac){
				//TODO ADD other clips and give an option to increase perf
				p.addMovieClip("Drag","."+((String)File.separator)+"src"+((String)File.separator)+"popup"+((String)File.separator)+"video"+((String)File.separator)+"data"+((String)File.separator)+"envoie_projectile.avi",m1);
				//p.addMovieClip("Drag", new GSMovie(this.myGCS.getMTApplication(), "."+((String)File.separator)+"src"+((String)File.separator)+"popup"+((String)File.separator)+"video"+((String)File.separator)+"data"+((String)File.separator)+"envoie_projectile.avi"));
				if(levelNumber>1){
				    p.addMovieClip("Rotate","."+((String)File.separator)+"src"+((String)File.separator)+"popup"+((String)File.separator)+"video"+((String)File.separator)+"data"+((String)File.separator)+"bouclier.avi",m2);
					//p.addMovieClip("Rotate", new GSMovie(this.myGCS.getMTApplication(), "."+((String)File.separator)+"src"+((String)File.separator)+"popup"+((String)File.separator)+"video"+((String)File.separator)+"data"+((String)File.separator)+"bouclier.avi"));

				}
				
				if(levelNumber>2){
					p.addMovieClip("Pan","."+((String)File.separator)+"src"+((String)File.separator)+"popup"+((String)File.separator)+"video"+((String)File.separator)+"data"+((String)File.separator)+"pan.avi",m3);
					//p.addMovieClip("Pan", this.myGCS.getMTApplication(), "."+((String)File.separator)+"src"+((String)File.separator)+"popup"+((String)File.separator)+"video"+((String)File.separator)+"data"+((String)File.separator)+"pan.avi"));
					//p.addMovieClip("Pan2", new GSMovie(this.myGCS.getMTApplication(), "."+((String)File.separator)+"src"+((String)File.separator)+"popup"+((String)File.separator)+"video"+((String)File.separator)+"data"+((String)File.separator)+"pan2.avi"));


					p.addMovieClip("Pan2","."+((String)File.separator)+"src"+((String)File.separator)+"popup"+((String)File.separator)+"video"+((String)File.separator)+"data"+((String)File.separator)+"pan2.avi",m4);
				}

				p.playMovie2("Drag");
			}
			
			
			p.getMyShape().setPositionGlobal(new Vector3D(x,y));
			p.getMyShape().rotateZ(p.getMyShape().getCenterPointGlobal(), (float) Math.toDegrees(angle)-90);
			
		}
		
		
		
	}
	
	public void popEndGamePopup(){
		
		PopupLogo p = new PopupLogo<String>(MTEllipse.class,"endgame_popup",myGCS,this,new Vector3D(myGCS.getMTApplication().width/2f,myGCS.getMTApplication().height/2f),215);
		p.addPopupItem("New game", "new_game");
		p.addPopupItem("Quit", "quit");

	}
	
	
	
	
	public void popHomePopup(){
		
		PopupLogo p = new PopupLogo<String>(MTEllipse.class,"home_popup",myGCS,this,new Vector3D(myGCS.getMTApplication().width/2f,myGCS.getMTApplication().height/2f),300);
		p.addPopupItem("New game", "new_game");
		p.addPopupItem("Quit", "quit");

	}
	
	
	public void popActivateGameExplanations(){
		Popup p = new Popup<Boolean>(MTEllipse.class, "activate_explanations"," show  help", myGCS, this,new Vector3D(myGCS.getMTApplication().width/2f,myGCS.getMTApplication().height/2f),300);

		p.addPopupItem("yes", true);
		p.addPopupItem("no", false);

	}
	
	
	public void popNbPlayersPopup(){
		Popup p = new PopupNbPlayers("player_number","Number of players", myGCS, this, new Vector3D(myGCS.getMTApplication().width/2f,myGCS.getMTApplication().height/2f), 300);
	}
	
	public void popLevelPopup(){
		Popup p = new Popup<Integer>(MTEllipse.class, "level_number","Difficulty level", myGCS, this, new Vector3D(myGCS.getMTApplication().width/2f,myGCS.getMTApplication().height/2f), 300);

		p.addPopupItem("Beginner", 1);
		p.addPopupItem("Intermediate", 2);
		p.addPopupItem("Advanced", 3);
		
		//TODO  check that deleting the following line is harmless
		//p.addPopupItem("Free game", 4);
	}
	
	
	public void popRankingPopup(){
		rankingPopup.clear();
		rankingPopup = new ArrayList<RankingPopup>();
		for(int i=0;i<playerNumber;i++){
			float angle =((float) ((i+1)*2*Math.PI/(playerNumber)+Math.PI/2f));	
			float x = myGCS.getMTApplication().width/2f +(float) (Math.cos(angle)*Constants.radiusCenterGoals);
			float y = myGCS.getMTApplication().height/2f +(float) (Math.sin(angle)*Constants.radiusCenterGoals);
			rankingPopup.add(new  RankingPopup("Ranking", "Game results", myGCS, this, new Vector3D(x,y), 100,angle, myPI[i].getMyColor()));

		}

		//We fill the popup with sorted scores
		ArrayList<PlayerInterface> PIRanking = new ArrayList<PlayerInterface>();
		while(PIRanking.size()<myPI.length){
			PlayerInterface maxPI = null;
			for(int i=0;i<myPI.length;i++){
				if(!PIRanking.contains(myPI[i])){
					maxPI = myPI[i];
					break;
				}
			}
			//System.out.println("minPI : "+minPI.getMyScore());
			for(int i=0;i<myPI.length;i++){
				//System.out.println("Current PI : "+myPI[i].getMyScore());
				if(myPI[i].getMyScore()>maxPI.getMyScore() && !PIRanking.contains(myPI[i])){
					maxPI = myPI[i];
					//System.out.println("nouveau minPI : "+minPI.getMyScore());
				}								
			}
			//System.out.println("On ajoute "+minPI.getMyScore()+" au PIRanking");
			PIRanking.add(maxPI);
		}

		for(int j=0;j<rankingPopup.size();j++){
			for(int i=0;i<PIRanking.size();i++){
				rankingPopup.get(j).addPopupItem(String.valueOf(PIRanking.get(i).getMyScore()), i, PIRanking.get(i).getMyColor());
			}
		}
	}
	
	
	public void removeRankingPopup(){
		Iterator<RankingPopup> it = rankingPopup.iterator();
		while(it.hasNext()){
			RankingPopup p = it.next();
			p.getMyShape().removeFromParent();
			p.getMyShape().destroy();
			
		}
		rankingPopup.clear();
	}
	
	public void setSceneMenuVisible(boolean isVisible){
		

		
		if(isVisible){
			if(myGCS.getCentralMenu()==null){
				//GCSceneMenu(GameModel GM, AbstractMTApplication app, Iscene scene, float x, float y, String Imagepath, float scale) {

				GCSceneMenu m = new GCSceneMenu(this, myGCS.getMTApplication(),myGCS, myGCS.getMTApplication().width/2f, myGCS.getMTApplication().height/2f, "."+((String)File.separator)+"src"+((String)File.separator)+"scene"+((String)File.separator)+"menu"+((String)File.separator)+"data"+((String)File.separator)+"menu.png", 0.3f);
				myGCS.setCentralMenu(m);
			}else{
				myGCS.getCentralMenu().setVisible(true);
			}
		}else{
			if(myGCS.getCentralMenu()!=null){
				myGCS.getCentralMenu().setVisible(false);
			}
		}
	}
	
	public void endGameTimers(){
		infoTimeleft.cancel();
		endGame.cancel();
		myTimer.cancel();
		cancelGameTimers();
	}
	
	public void endGame(boolean showRanking){
		if(showRanking){
			System.err.println("TIME'UP");



			myGCS.getMTApplication().invokeLater(new Runnable(){

				/**
				 * Creation of ranking Popup
				 * Each player should have a popup in his area
				 */
				public void run() {
					//Creation of each popup
					

					popRankingPopup();
					setSceneMenuVisible(false);
					popEndGamePopup();
					
					endGameTimers();

				}

			});

			for(PlayerInterface PI:myPI){
				support.removePropertyChangeListener(PI);
			}

			emptyScene();
		}else{
			System.err.println("TIME'UP");



			myGCS.getMTApplication().invokeLater(new Runnable(){

				/**
				 * Creation of ranking Popup
				 * Each player should have a popup in his area
				 */
				public void run() {
					

					
					endGameTimers();

				}

			});

			for(PlayerInterface PI:myPI){
				support.removePropertyChangeListener(PI);
			}

			emptyScene();
		}
	}

	
	
	
	
	public void initGame(final GameModel gm){
		this.setSceneMenuVisible(true);
		fireRanks();
		myTimer = new Timer();
		endGame=new TimerTask(){

			@Override
			public void run() {
				

				endGame(true);


			}

		};
		
	

		infoTimeleft=new TimerTask(){

			@Override
			public void run() {
				int left = (int) (System.currentTimeMillis()-endGame.scheduledExecutionTime());
				int min = ((left/1000)/60)*-1;

				float sec = (left+min*60*1000)*-1;
				sec/=1000f;
				sec=Math.round(sec);
				int secI = (int) sec;
				if(secI==60){
					min++;
					secI=0;
				}
				if(secI/10==0){
					support.firePropertyChange("time", null, String.valueOf(min)+"'"+"0"+String.valueOf(secI)+"''");
					//System.out.println(String.valueOf(min)+"'"+"0"+String.valueOf(secI)+"''");

				}else{
					support.firePropertyChange("time", null, String.valueOf(min)+"'"+String.valueOf(secI)+"''");
					//System.out.println(String.valueOf(min)+"'"+String.valueOf(secI)+"''");

				}

			}

		};


		myTimer.schedule(endGame,(Constants.gameTime)*1000);
		myTimer.schedule(infoTimeleft,0, 1000);
	}

	@Override
	public void reactToPopUpResponse(String PopUpName, Object o) {

		
		if(PopUpName.equals("home_popup")){
			if(((String)o).compareTo("new_game")==0){
				//pop next popup
				popActivateGameExplanations();
			}else{
				//quit
				myGCS.getMTApplication().destroy();
			}
			
		
		}else if(PopUpName.equals("activate_explanations")){
			
			explanationActivated=(Boolean)o;
			popLevelPopup();
		
		}else if(PopUpName.equals("level_number")){
			
			setLevelNumber((Integer)o);
			System.out.println("levelNumber set");
			
			popNbPlayersPopup();
		}else if(PopUpName.equals("player_number")){
			
			setPlayerNumber(((Integer)o));
			System.out.println("playerNumber set");
			
			if(explanationActivated){
				numberOfFinishedTutos=0;
				popVideoPopup();
			}else{
				this.createInterfaces();
				this.subscribeInterfaces();
				
				this.initGame(this);
			}
			
					
		}else if(PopUpName.equals("tuto_popup")){
			numberOfFinishedTutos++;
			if(numberOfFinishedTutos>=playerNumber){
				this.createInterfaces();
				this.subscribeInterfaces();
				
				this.initGame(this);
			}
		}else if(PopUpName.equals("endgame_popup")){
			if(((String)o).compareTo("new_game")==0){
				this.removeRankingPopup();
				this.myGCS.createScreenBorders(this.myGCS.getPhysicsContainer());
				popActivateGameExplanations();
			}else{
				//quit
				myGCS.getMTApplication().destroy();
			}
		}
	}

}