package GameModel;

public class Constants {
	public static int radiusCenterGoals=290;
	public static int radiusGoals=45;
	
	
	public static int shieldSmallRadius=100;
	public static int shieldSmallDef=25;
	public static int shieldBigRadius=120;
	public static int shieldBigDef=25;
	public static int shieldDistance = (int) ((shieldSmallRadius+shieldBigRadius)/2f);
	public static float shieldCoveredAngle= (float) Math.toRadians(50);
	public static int loaderBulletsNumber=4;
	public static int loaderReloadDelay_ms=2000;
	public static int loaderBulletDistance=  radiusGoals+(int) ((shieldSmallRadius-radiusGoals)/2f);
}

