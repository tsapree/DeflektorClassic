package pro.oneredpixel.deflektorclassic;

public class Button {
	int bx;
	int by;
	int bwidth;
	int bheight;
	int bimgx;
	int bimgy;
	boolean touched;
	boolean box;
	String btxt;
	
	Button(int x, int y, int width, int height, boolean centered, String txt) {
		bx=x;
		by=y;
		bwidth=width;
		bheight=height;
		btxt=txt;
		bimgx=-1;
		bimgy=-1;
		touched=false;
		box=true;
		correctXYWidthHeight(centered);
	};
	
	Button(int x, int y, int width, int height, boolean centered) {
		bx=x;
		by=y;
		bwidth=width;
		bheight=height;
		bimgx=-1;
		bimgy=-1;
		touched=false;
		box=true;
		correctXYWidthHeight(centered);
	};
	
	Button(int x, int y, int imgx, int imgy) {
		bx=x;
		by=y;
		bwidth=16+8;
		bheight=18+8;
		bimgx=imgx;
		bimgy=imgy;
		btxt=null;
		touched=false;
		box=true;
	};
	
	void correctXYWidthHeight(boolean centered) {
		if (bwidth<=0) {
			if (btxt!=null) bwidth=btxt.length()*8+16;
			if (bwidth<=0) bwidth=24;
		};
		if (bheight==0) bheight=24;
		if (centered) {
			bx=bx-bwidth/2;
			by=by-bheight/2;
		};
	};
	
	boolean checkRegion(int testx, int testy) {
		return testx>=bx && testx<bx+bwidth && testy>=by && testy<=by+bheight;
	};
	
}
