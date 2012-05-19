package model;

public class Constants {
	
	//Global positioning parameters
	public static int areaRadius=400;
	public static int radiusCenterGoals=290;
	public static int radiusGoals=45;
	public static int radiusGoalDisplay=70;
	
	//RotationShield parameters
	public static int shieldSmallRadius=100;
	public static int shieldSmallDef=25;
	public static int shieldBigRadius=120;
	public static int shieldBigDef=25;
	public static int shieldDistance = (int) ((shieldSmallRadius+shieldBigRadius)/2f);
	public static float shieldCoveredAngle= (float) Math.toRadians(50);
	
	//Bullet loader parameters
	public static int loaderBulletsNumber=4;
	public static int loaderReloadDelay_ms=2000;
	public static int loaderBulletDistance=  radiusGoals+(int) ((shieldSmallRadius-radiusGoals)/2f);
	
	//Movable shield parameters
	public static int movableShieldAreaRadius = areaRadius-radiusCenterGoals+shieldBigRadius+20;
	
	//Bullet parameters
	public static int bulletMaxRebound=4;
	public static int bulletScore=10;
	
	//Display parameters
	public static int displayFontSize=14;
	public static String displatFontName = new String("arial");
	
	//Game model parameters
	public static int gameTime=1*60+30;
}

