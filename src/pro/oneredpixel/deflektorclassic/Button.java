package pro.oneredpixel.deflektorclassic;

public class Button {
	int bx;
	int by;
	int bwidth;
	int bheight;
	int bimg;
	String btxtEng;
	String btxtRus;
	
	Button(int x, int y, int width, int height, boolean centered, String txtEng, String txtRus) {
		bx=x;
		by=y;
		bwidth=width;
		bheight=height;
		btxtEng=txtEng;
		btxtRus=txtRus;
	};
	
	Button(int x, int y, int width, int height, boolean centered) {
		bx=x;
		by=y;
		bwidth=width;
		bheight=height;
	};
	
	void correctXYWidthHeight(boolean centered) {
		if (bwidth<=0) {
			if (btxtEng!=null) bwidth=btxtEng.length()*8+16;
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
