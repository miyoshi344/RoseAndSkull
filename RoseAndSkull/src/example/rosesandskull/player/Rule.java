package example.rosesandskull.player;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

public class Rule {
	
	private Paint paint = new Paint();
	
	private int page;
	private Bitmap loadPage;
	
	public Rule() {
		page = 1;
	}
	
	public void draw(Canvas canvas) {
		switch (page) {
		
		case 1:
			loadPage = Texture.RULE1;
			break;
			
		case 2:
			loadPage = Texture.RULE2;
			break;
			
		case 3:
			loadPage = Texture.RULE3;
			break;
			
		case 4:
			loadPage = Texture.RULE4;
			break;
			
		case 5:
			loadPage = Texture.RULE5;
			break;
			
		case 6:
			loadPage = Texture.RULE6;
			break;
			
		case 7:
			loadPage = Texture.RULE7;
			break;
			
		case 8:
			loadPage = Texture.RULE8;
			break;
		}
		
		canvas.drawBitmap(loadPage, 0,0, paint);
	}

	public boolean touch(MotionEvent event) {
		
		if(event.getAction() == MotionEvent.ACTION_DOWN) {
			if(page >= 8) {
				page = 1;
				return true;
			}
			else page++;
			Sound.BUTTON_SE.start();
		}
		
		return false;
	}
}
