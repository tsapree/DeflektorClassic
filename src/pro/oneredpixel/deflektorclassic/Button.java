package pro.oneredpixel.deflektorclassic;

public class Button {
	int bx;
	int by;
	int bwidth;
	int bheight;
	int bimg;
	String btxt;
	
	Button(int x, int y, int width, int height, boolean centered, String txt) {
		bx=x;
		by=y;
		bwidth=width;
		bheight=height;
		btxt=txt;
		correctXYWidthHeight(centered);
	};
	
	Button(int x, int y, int width, int height, boolean centered) {
		bx=x;
		by=y;
		bwidth=width;
		bheight=height;
		correctXYWidthHeight(centered);
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
