package pro.oneredpixel.deflektorclassic;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;

public class LevelsState extends State {

	Button bLeft;
	Button bRight;
	Button bBack;
	
	int accumulated_code=0;
	
	int page=-1;
	int maxpage = -1;
	int savedUnlockedLevel = -1;
	int currentFrame = 0;
	boolean showingFinalCutAnimation;
	
	final int desiredFPS = 20;
	
	LevelsState(Deflektor defl) {
		super(defl);
		
		bLeft = new Button(8-4, 160/2-8-4,64,160);
		bLeft.box=false;
		bRight = new Button(240-8-16-4, 160/2-8-4, 80, 160);
		bRight.box=false;
		bBack = new Button(8-4, 160-8-16-4,96, 160);
		bBack.box=false;
		
	}
	
	void start() {
		accumulated_code=0;
		maxpage = (app.unlockedLevel-1)/20+1;
		if (maxpage>4) maxpage=4;
		if (savedUnlockedLevel!=app.unlockedLevel) {
			savedUnlockedLevel=app.unlockedLevel;
			page = app.unlockedLevel/20+1;

		};
		if (page>3) page=1;
		if (app.timeToShowFinalCut) {
			page=4;
			showingFinalCutAnimation=true;
			app.timeToShowFinalCut=false;
			currentFrame=0;
		} else {
			showingFinalCutAnimation=false;
			currentFrame=10000;
		}
	};
	
	public boolean touchDown(int x, int y, int pointer, int button) {
		int touchx=(int)(x-app.winX)/app.sprScale;
		int touchy=(int)(y-app.winY)/app.sprScale;
		if (page<=3) {
			if ((page>1) && bLeft.checkRegion(touchx,touchy)) {
				bLeft.touched=true;
				app.playSound(Deflektor.SND_TAP);
			}
			if ((page<maxpage) && bRight.checkRegion(touchx,touchy)) {
				bRight.touched = true;
				app.playSound(Deflektor.SND_TAP);
			}
		};
		if (bBack.checkRegion(touchx,touchy)) {
			bBack.touched = true;
			bBack.touched = true;
			app.playSound(Deflektor.SND_TAP);
		}
		return false;
	}
	
	public boolean tap(float x, float y, int tapCount, int button) {
		int ix=(int)((x-app.winX)/app.sprScale);
		int iy=(int)((y-app.winY)/app.sprScale);
		if (page<=3) {
			if ((ix>=0) && (ix<240) && (iy>=0) && (iy<160)) {
				if ((page>1) && bLeft.checkRegion(ix,iy)) {
					page--;
					bLeft.touched=false;
				};
				if ((page<maxpage) && bRight.checkRegion(ix,iy)) {
					page++;
					bRight.touched=false;
				}
				int lx=(ix-44)/8;
				int ly=(iy-20)/8;
				if ( ((ly&3)!=3) && (ix>=44) && (iy>=20)) {
					if ((lx&3)!=3) {
						lx=lx/4; ly=ly/4;
						int lev=ly*5+lx+(page-1)*20+1;
						if ((lx>=0) && (lx<5) && (ly>=0) && (ly<4) && (lev<=app.unlockedLevel)) {
							app.playingLevel = lev;
							app.gotoAppState(Deflektor.APPSTATE_GAME);
							app.playSound(Deflektor.SND_UNTAP);
						};
					} else {
						lx=lx/4; ly=ly/4;
						int code=ly*5+lx+1;
						if (code<=9 && code>0) {
							accumulated_code=accumulated_code*10+code;
							if (accumulated_code==28111997) {
								app.showingCheatControls=true;
								app.playSound(Deflektor.SND_GREMLINDEAD);
							}
						};
					}
				}
				if (bBack.checkRegion(ix,iy)) {
					app.gotoAppState(Deflektor.APPSTATE_MENU);
					bBack.touched=false;
				}
			}
		} else {
			//in final cut
			if (currentFrame>desiredFPS*10) {
				if (bBack.checkRegion(ix,iy)) {
					if (showingFinalCutAnimation) app.gotoAppState(Deflektor.APPSTATE_MENU);
					else page--;
					bBack.touched=false;
				}
			};
			
		}
		return false;
	}
	
	public boolean touchUp (int x, int y, int pointer, int button) {
		untouchButtons();
		return false;
	};
	
	void untouchButtons() {
		if (bLeft.touched||bRight.touched||bBack.touched) app.playSound(Deflektor.SND_UNTAP);
		bLeft.touched = false;
		bRight.touched = false;
		bBack.touched = false;
	};
	
	public boolean keyUp(int k) {
		if (k==Keys.BACK || k==Keys.MENU) {
			app.gotoAppState(Deflektor.APPSTATE_MENU);
			app.playSound(Deflektor.SND_TAP);
			return true;
		};
		return false;
	}
	
	boolean checkInBox(int x,int y, int bx, int by, int bwidth, int bheight) {
		return (x>=bx)&&(x<(bx+bwidth))&&(y>=by)&&(y<(by+bheight));
	};

	public void render(SpriteBatch batch) {
		batch.setProjectionMatrix(app.camera.combined);
		batch.begin();
		
		if (page<=3) {
			app.showString((240-8*14)/2, 8, "SELECT A LEVEL");
			int s=(page-1)*20+1;
			for (int i=0;i<4;i++) {
				for (int j=0;j<5;j++) {
					drawLevelBox(44+j*32,20+i*32,s++);
					if (s>app.countOfLevels) break;
				};
				if (s>app.countOfLevels) break;
			};
	
			if (page>1) app.drawButton(bLeft);
			if (page<maxpage) app.drawButton(bRight);			
			app.drawButton(bBack);
			
		} else {
			//final cut
			if (!((currentFrame<desiredFPS*4) && (((currentFrame&3)>>1)==1)))app.showString((240-8*15)/2, 8,  "CONGRATULATIONS");
			if (currentFrame>desiredFPS*4) {
				app.showString((240-8*13)/2, 32, "YOU ARE TRULY");
				app.showString((240-8*1 )/2, 40, "A");
				app.showString((240-8*16)/2, 48, "MASTER DEFLEKTOR");
			};
			if (currentFrame>desiredFPS*6) {
				app.showString((240-8*27)/2, 72, "THE CIRCUIT IS NOW COMPLETE");
				app.spr_putRegion(28, 80+50/2-8, 16, 16, 16, 64);
				app.spr_putRegion(188, 80+50/2-12, 24, 24, 104, 232);
			};
			if (currentFrame>=desiredFPS*7) {
				
				for (int i=28+16;i<188;i+=8) {
					app.spr_putRegion(i, 80+50/2-4, 8, 8, 56, 112);
				};
			};
			if (currentFrame>desiredFPS*9) {
				app.showString((240-8*21)/2, 130, "THE TEA WILL BE READY");
				app.showString((240-8*16)/2, 138,"IN FIVE MINUTES!");
			};
			if (currentFrame>desiredFPS*10) {
				app.drawButton(bBack);
			};
		}

		batch.end();

		if(TimeUtils.nanoTime() - app.lastFrameTime > (1000000000/desiredFPS)) {
			currentFrame++;			
			app.lastFrameTime = TimeUtils.nanoTime();
		};
		
	};
	
	void drawLevelBox(int x, int y, int levelNumber) {
		app.spr_putRegion(x, y, 24, 24, 0,32+144);
		app.showBigNumber(x+4,y+8,levelNumber);
		if (app.unlockedLevel<levelNumber) app.spr_putRegion(x+15, y+15, 8, 8, 48,192);
	};
	

	


}
