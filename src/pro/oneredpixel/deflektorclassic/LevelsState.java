package pro.oneredpixel.deflektorclassic;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class LevelsState extends State {

	int page=1;
	
	LevelsState(Deflektor defl) {
		super(defl);
		// TODO Auto-generated constructor stub
	}
	
	public boolean tap(float x, float y, int tapCount, int button) {
		app.gotoAppState(Deflektor.APPSTATE_GAME);
		return false;
	}

	public void render(SpriteBatch batch) {
		batch.setProjectionMatrix(app.camera.combined);
		batch.begin();
		
		int s=1;
		for (int i=0;i<4;i++) {
			for (int j=0;j<5;j++) {
				drawLevelBox(44+j*32,20+i*32,s++);
				if (s>app.countOfLevels) break;
			};
			if (s>app.countOfLevels) break;
		};

		if (page>1) app.menu_putRegion(8, 160/2-8, 16, 16, 32,32);
		if (page<3) app.menu_putRegion(240-8-16, 160/2-8, 16, 16, 48,32);
		
		batch.end();
	};
	
	void drawLevelBox(int x, int y, int levelNumber) {
		app.menu_putRegion(x, y, 24, 24, 0,32);
		int up=levelNumber/10;
		int lo=levelNumber-up*10;
		app.menu_putRegion(x+4, y+8, 8, 8, up*8,96);
		app.menu_putRegion(x+12, y+8, 8, 8, lo*8,96);
		if (app.unlockedLevel<levelNumber) app.menu_putRegion(x+15, y+15, 8, 8, 24,32);
	};
	


}
