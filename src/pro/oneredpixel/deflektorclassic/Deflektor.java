package pro.oneredpixel.deflektorclassic;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;

public class Deflektor implements ApplicationListener {
	
	Texture spritesImage;
	private Sound burnCellSound;
	private Sound burnBombSound;
	private Sound exitOpenSound;
	private Sound laserFillInSound;
	private Sound laserOverheatSound;
	private Sound laserReadySound;
	private Sound levelCompletedSound;
	private Sound transferEnergySound;
	Music music;
	
	OrthographicCamera camera;
	SpriteBatch batch;
	
	int screenWidth;
	int screenHeight;
	int sprSize = 8;
	int sprScale=1;
	int winX;
	int winY;
	int winWidth;
	int winHeight;
	int panScale;
	
	int playingLevel = 1;
	int unlockedLevel = 60;
	final int countOfLevels = 60;


	final static int APPSTATE_STARTED = 0;
	final static int APPSTATE_LOADING = 1;
	final static int APPSTATE_MENU = 2;
	final static int APPSTATE_SELECTLEVEL = 3;
	final static int APPSTATE_GAME = 4;
	int appStateId = 0;
	State appState;
	
	GameState gameState;
	MenuState menuState;
	LevelsState levelsState;
	
	public long lastFrameTime = 0;
	
	//settings
	boolean soundEnabled = true;
	boolean controlsTapToRotate = true; //коснуться и отпустить зеркало для поворота на 1 угол
	boolean controlsTouchAndDrag = true; //коснуться зеркала и не отпуская потянуть для поворота
	boolean controlsTapThenDrag = true; //коснуться зеркала для выбора, потом отпустить и провести по экрану для поворота
	   
	@Override
	public void create() {
		
		screenWidth = Gdx.graphics.getWidth();
		screenHeight = Gdx.graphics.getHeight();
		
		sprSize = Math.min(screenWidth/GameState.field_width, screenHeight/(GameState.field_height+1));
		sprScale = sprSize/8/2;
		sprSize = 8;
		
		//sprScale =1;
		//sprSize=8;
		
		winWidth = GameState.field_width*sprSize*2*sprScale;
		winHeight = (GameState.field_height+1)*sprSize*2*sprScale;
		
		winX = (screenWidth-winWidth)/2;
		winY = (screenHeight-winHeight)/2;		
		
		panScale=sprSize*sprScale/1;
		
		spritesImage = new Texture(Gdx.files.internal("sprites.png"));
		  
		burnCellSound = Gdx.audio.newSound(Gdx.files.internal("burn-cell.wav"));
		burnBombSound = Gdx.audio.newSound(Gdx.files.internal("burn-bomb.wav"));
		exitOpenSound = Gdx.audio.newSound(Gdx.files.internal("exit-open.wav"));
		laserFillInSound = Gdx.audio.newSound(Gdx.files.internal("laser-fill-in.wav"));
		laserOverheatSound = Gdx.audio.newSound(Gdx.files.internal("laser-overheat.wav"));
		laserReadySound = Gdx.audio.newSound(Gdx.files.internal("laser-ready.wav"));
		levelCompletedSound = Gdx.audio.newSound(Gdx.files.internal("level-completed.wav"));
		transferEnergySound = Gdx.audio.newSound(Gdx.files.internal("transfer-energy.wav"));

		music = Gdx.audio.newMusic(Gdx.files.internal("zxmusic.ogg"));
		  
		// start the playback of the background music immediately
		music.setLooping(true);
		
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		batch = new SpriteBatch();
		
		//GestureDetector(float halfTapSquareSize, float tapCountInterval, float longPressDuration, float maxFlingDelay, GestureDetector.GestureListener listener) 
		//GestureDetector(GestureDetector.GestureListener listener)
		//Creates a new GestureDetector with default values: halfTapSquareSize=20, tapCountInterval=0.4f, longPressDuration=1.1f, maxFlingDelay=0.15f.

		InputMultiplexer multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(new GestureDetector(sprSize*sprScale, 0.4f, 1.1f, 0.15f, new MyGestureListener()));
		multiplexer.addProcessor(new MyInputProcessor());
		Gdx.input.setInputProcessor(multiplexer);
		
		gotoAppState(APPSTATE_MENU);

	   }

	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}
	
	public class MyGestureListener implements GestureListener {
		
		public boolean pinch (Vector2 initialFirstPointer, Vector2 initialSecondPointer, Vector2 firstPointer, Vector2 secondPointer) {
			return false;
		}

		@Override
		public boolean fling(float velocityX, float velocityY, int button) {
			return appState.fling(velocityX, velocityY, button);
		}

		@Override
		public boolean longPress(float arg0, float arg1) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean tap(float x, float y, int tapCount, int button) {
			return appState.tap(x, y, tapCount, button);
		}

		@Override
		public boolean touchDown(float x, float y, int pointer, int button) {
			return appState.touchDown(x, y, pointer, button);
		}

		@Override
		public boolean pan(float x, float y, float deltaX, float deltaY) {
			return appState.pan(x, y, deltaX, deltaY);
		}
		
		@Override
		public boolean zoom(float arg0, float arg1) {
			// TODO Auto-generated method stub
			return false;
		}
	}
	
	public class MyInputProcessor implements InputProcessor {

		@Override
		public boolean keyDown(int arg0) {
			// TODO Auto-generated method stub
			return appState.keyDown(arg0);
		}

		@Override
		public boolean keyTyped(char arg0) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean keyUp(int arg0) {
			// TODO Auto-generated method stub
			return appState.keyUp(arg0);
		}

		@Override
		public boolean mouseMoved(int arg0, int arg1) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean scrolled(int arg0) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean touchDown(int arg0, int arg1, int arg2, int arg3) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean touchDragged(int arg0, int arg1, int arg2) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean touchUp(int x, int y, int pointer, int button) {
			return appState.touchUp(x, y, pointer, button);
		}

	}
	
	void gotoAppState(int newState) {
		if (appState!=null) {
			appState.stop();
		};
		
		switch (newState) {
		case APPSTATE_STARTED:
			break;
		case APPSTATE_LOADING:
			break;
		case APPSTATE_MENU:
			if (menuState==null) {
				menuState = new MenuState(this);
				menuState.create();
			};
			appState = menuState;
			Gdx.input.setCatchBackKey(false);
			if (soundEnabled) music.play();
			break;
		case APPSTATE_SELECTLEVEL:
			if (levelsState==null) {
				levelsState = new LevelsState(this);
				levelsState.create();
			};
			appState = levelsState;
			Gdx.input.setCatchBackKey(false);
			break;
			
		case APPSTATE_GAME:
			if (gameState==null) {
				gameState = new GameState(this);
				gameState.create();
			};
			appState = gameState;
			
			Gdx.input.setCatchBackKey(true);
			music.stop();
			break;
		};
		appStateId = newState;
		appState.start();
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		camera.update();
		appState.render(batch);
	}

	@Override
	public void resize(int arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}
	
//	void menu_putRegion(int x, int y, int srcWidth, int srcHeight, int srcX, int srcY) {
//		batch.draw(menuImage, winX+x*sprScale, screenHeight-winY-y*sprScale-srcHeight*sprScale, srcWidth*sprScale,srcHeight*sprScale, srcX, srcY, srcWidth,srcHeight,false,false);
//	};
	
	void spr_putRegion(int x, int y, int srcWidth, int srcHeight, int srcX, int srcY) {
		batch.draw(spritesImage, winX+x*sprScale, screenHeight-winY-y*sprScale-srcHeight*sprScale, srcWidth*sprScale,srcHeight*sprScale, srcX, srcY, srcWidth,srcHeight,false,false);
	};
	
	void spr_putRegionSafe(int x, int y, int srcWidth, int srcHeight, int srcX, int srcY) {
		batch.draw(spritesImage, winX+x*sprScale, screenHeight-winY-y*sprScale-srcHeight*sprScale, srcWidth*sprScale,srcHeight*sprScale, srcX, srcY, srcWidth,srcHeight,false,false);
	};
		
	void showBigNumber(int x, int y, int num) {
		int up=num/10;
		int lo=num-up*10;
		spr_putRegion(x, y, 8, 8, up*8,96+144);
		spr_putRegion(x+8, y, 8, 8, lo*8,96+144);
	}
	
	void showChar(int x, int y, char c) {
		if (c>='0' && c<='9') spr_putRegion(x, y, 8, 8, (c-'0')*8,72+144);
		if (c>='A' && c<='P') spr_putRegion(x, y, 8, 8, (c-'A')*8,80+144);
		if (c>='Q' && c<='Z') spr_putRegion(x, y, 8, 8, (c-'Q')*8,88+144);
	}
	
	void showString(int x, int y, String s) {
		for (int i=0;i<s.length();i++) {
			showChar(x+i*8,y,s.charAt(i));
		};
	}
	
	void drawBox(int x, int y, int width, int height, int srcx, int srcy) {
		//TODO: переделать, если надо рисовать box не кратный 8.
		height=((int)(height/8))*8;
		width=((int)(width/8))*8;
		spr_putRegion(x,y,8,8,srcx,srcy);
		for (int i=8;i<width-8;i+=8) spr_putRegion(x+i,y,8,8,srcx+8,srcy);
		spr_putRegion(x+width-8,y,8,8,srcx+16,srcy);
		
		for (int j=8;j<height-8;j+=8) {
			spr_putRegion(x,        y+j,8,8,srcx,srcy+8);
			for (int i=8;i<width-8;i+=8) spr_putRegion(x+i,y+j,8,8,srcx+8,srcy+8);
			spr_putRegion(x+width-8,y+j,8,8,srcx+16,srcy+8);
		};
		
		spr_putRegion(x,        y+height-8,8,8,srcx,srcy+16);
		for (int i=8;i<width-8;i+=8) spr_putRegion(x+i,y+height-8,8,8,srcx+8,srcy+16);
		spr_putRegion(x+width-8,y+height-8,8,8,srcx+16,srcy+16);
	};
	
	void drawButton (int x, int y, String text, boolean centered) {
		int width=text.length()*8+16;
		if (centered) {
			x=x-width/2;
			y=y-24/2;
		};
		drawBox(x,y,width,24,0,176);
		showString(x+8,y+8,text);
	};
	
	void drawButton (Button b) {
		drawBox(b.bx,b.by,b.bwidth,b.bheight,0,176);
		if (b.btxt!=null) showString(b.bx+8,b.by+8,b.btxt);
	}
	
	void drawButton (Button b, String text) {
		drawBox(b.bx,b.by,b.bwidth,b.bheight,0,176);
		if (text!=null) showString(b.bx+8,b.by+8,text);
	}
	
	void unlockLevel(int level) {
		if (unlockedLevel<level)
			unlockedLevel=level;
	};
	
	
	final static int SND_BURNCELL = 1;
	final static int SND_LASERBOMB_LOOP =2;
	final static int SND_EXITOPEN =3;
	final static int SND_LASERFILLIN_LOOP=4;
	final static int SND_LASEROVERHEAT_LOOP=5;
	final static int SND_LASERREADY=6;
	final static int SND_LEVELCOMPLETED=7;
	final static int SND_TRANSFERNRG_LOOP=8;
	
	int playing_LOOP_id = 0;
	
	void playSound(int id) {
		if ((playing_LOOP_id!=0) && (playing_LOOP_id!=id)) stopSound(playing_LOOP_id);
		
		switch (id) { 
		case SND_BURNCELL:
			burnCellSound.play();
			break;
		case SND_EXITOPEN:
			exitOpenSound.play();
			break;

		case SND_LASERREADY:
			laserReadySound.play();
			break;
		case SND_LEVELCOMPLETED:
			levelCompletedSound.play();
			break;
			
		case SND_LASERFILLIN_LOOP:
			if (playing_LOOP_id==0) laserFillInSound.loop();
			playing_LOOP_id=SND_LASERFILLIN_LOOP;
			break;
		case SND_LASEROVERHEAT_LOOP:
			if (playing_LOOP_id==0) laserOverheatSound.loop();
			playing_LOOP_id=SND_LASEROVERHEAT_LOOP;
			break;
		case SND_LASERBOMB_LOOP:
			if (playing_LOOP_id==0) burnBombSound.loop();
			playing_LOOP_id=SND_LASERBOMB_LOOP;
			break;
		case SND_TRANSFERNRG_LOOP:
			if (playing_LOOP_id==0) transferEnergySound.loop();
			playing_LOOP_id=SND_TRANSFERNRG_LOOP;
			break;
		};
	};
	
	void stopContinuousSound() {
		if (playing_LOOP_id!=0) 
			stopSound(playing_LOOP_id);
	};
	
	private void stopSound(int id) {
		switch (id) {
		case SND_BURNCELL:
		case SND_LEVELCOMPLETED:
		case SND_LASERREADY:
		case SND_EXITOPEN:
			break;
		case SND_LASEROVERHEAT_LOOP:
			laserOverheatSound.stop();
			break;
		case SND_LASERBOMB_LOOP:
			burnBombSound.stop();
			break;
		case SND_LASERFILLIN_LOOP:
			laserFillInSound.stop();
			break;
		case SND_TRANSFERNRG_LOOP:
			transferEnergySound.stop();
			break;
		};
		playing_LOOP_id=0;
	};
	
	void playMelody(int id) {
		
	};
	
	void stopMelody(int id) {
		
	};

}

