package pro.oneredpixel.deflektorclassic;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;

public class MainActivity extends Activity {

	final int field_width = 15;
	final int field_height = 9;
	int nodes[][];
	
	ImageView iv;
    Bitmap bm=null;
    Sprite spr;
	
	//коэффициенты шага луча
	int angleNodeSteps[][]={
		{0,-2}, //0
		{1,-2}, //1
		{2,-2}, //2
		{2,-1}, //3
		{2,0},  //4
		{2,1}, //5
		{2,2}, //6
		{1,2}, //7
		{0,2}, //8
		{-1,2}, //9
		{-2,2}, //10
		{-2,1}, //11
		{-2,0}, //12
		{-2,-1},//13
		{-2,-2},//14
		{-1,-2},//15
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		iv=(ImageView) findViewById(R.id.iv1);
        //iv.setOnClickListener(this);
		
		bm=Bitmap.createBitmap(field_width*16, field_height*16, Bitmap.Config.ARGB_8888);
		
		spr=new Sprite(R.drawable.defsprites);
		
		
		nodes=new int[field_width*4][field_height*4];
		for (int i=0;i<field_width*4;i++)
			for (int j=0;j<field_height*4;j++) {
				nodes[i][j]=0;
				bm.setPixel(i*4, j*4, 0xFF00FF00);
			}
		
		nodes[10][20]=0x101;
		nodes[11][18]=0x103;
		nodes[13][16]=0x110;
		nodes[11][14]=0x200;
		
		putMirror(2,5,4);
		putMirror(4,5,22);
		putLaser(2,7,0);
		//drawbeam (10,24,0);

		
		iv.setImageBitmap(bm);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	//angle=0..31
	void putMirror(int x, int y, int angle) {
		nodes[x*4+2][y*4+2]=0x100+(angle&0x1f);
		spr.putRegion(bm, x*16, y*16, 16, 16, (angle&7)*16, ((angle>>3)&1)*16);
	}
	
	//angle=0..3
	void putLaser(int x,int y, int angle) {
		spr.putRegion(bm, x*16, y*16, 16, 16, ((angle&3)*16), 4*16);
		drawbeam(x*4+2+angleNodeSteps[angle*4][0],y*4+2+angleNodeSteps[angle*4][1],(angle&3)*4);
	}
	
	void drawbeam(int beam_x, int beam_y, int beam_angle) {
		
		int new_beam_x;
		int new_beam_y;
		int new_beam_angle;
		int node, node_id, node_angle;
		boolean endBeam=false;
		
		while (!endBeam) {
			
			node=nodes[beam_x][beam_y];
			node_id=node>>8;
			node_angle=nodes[beam_x][beam_y]&0x1f;				//node_angle - угол поворота зеркала, 0-31.
			
			new_beam_angle=beam_angle;
			
			switch (node_id) {
			case 0: break;
			case 1: //mirror node - отражает луч
				new_beam_angle =((node_angle+node_angle-beam_angle-beam_angle)>>1)&0xf;
				break;
			case 2: //extinguish node - поглощает луч
				endBeam=true;
				continue;
			};
			
			new_beam_x = beam_x+angleNodeSteps[new_beam_angle][0];
			new_beam_y = beam_y+angleNodeSteps[new_beam_angle][1];
			if (new_beam_x>=field_width*4 || new_beam_x<0 || new_beam_y>=field_height*4 || new_beam_y<0) endBeam=true;
			else drawline (bm,beam_x*4,beam_y*4,new_beam_x*4,new_beam_y*4,0xFF000000);
				
			beam_x = new_beam_x;
			beam_y = new_beam_y;
			beam_angle = new_beam_angle;
			
		}
	};
	
	void drawline(Bitmap bitmap, int x1, int y1, int x2, int y2, int colour1)
	{
		int x0;
		int y0;
		int sx = Math.abs(x2-x1);
		int sy = Math.abs(y2-y1);
		int zx = ((x2>x1)?1:(-1));
		int zy = ((y2>y1)?1:(-1));
		int err=0;
		
		if (sx>=sy) {
			y0=y1;
			for (x0=x1; x0!=x2; x0+=zx) {
				bitmap.setPixel(x0, y0, colour1);
				err+=sy;
				if (2*err >= sx) {
					y0+=zy;
					err-=sx;
				};
			};
			//исправление глюка с неотображением последнего пискеля.
			bitmap.setPixel(x2, y2, colour1);
		} else if (sy>sx) {
			x0=x1;
			for (y0=y1; y0!=y2; y0+=zy) {
				bitmap.setPixel(x0, y0, colour1);
				err+=sx;
				if (2*err >= sy) {
					x0+=zx;
					err-=sy;
				};
			};
			//исправление глюка с неотображением последнего пискеля.
			bitmap.setPixel(x2, y2, colour1);
		};
	};
	
	class Sprite {
		int width=0;
		int height=0;
		int data[]=null;
		
		Sprite (int res_id) {
			Bitmap b=BitmapFactory.decodeResource(getResources(), res_id);
			if (b!=null) { 
				width = b.getWidth();
				height = b.getHeight();
				data = new int[width*height];
				b.getPixels(data, 0, width, 0, 0, width, height);
			};
		};
		
		//x,y,sx,sy - space in bitmap to put sprite in,
		//bx,by - coordinates in sprite where begins region
		void putRegion(Bitmap bitmap, int x, int y, int sx, int sy, int bx, int by) {
			if (width>0 && height>0 && data!=null) {
				bitmap.setPixels(data, by*width+bx, width, x, y, sx, sy);
			};
		};
	}

}
