package example.rosesandskull.player;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/************************
 * 勝敗を判定するクラス *
 ************************/
public class Judge {

	// 初期化
	private Paint paint = new Paint();
	private Bitmap main;
	private Bitmap draw;

	/******************
	 * コンストラクタ *
	 ******************/
	public Judge() {
	}

	public void set(Bitmap bitmap) {
		draw = bitmap;
	}

	/*****************
	 * drawメソッド  * 
	 * 描画の処理    *
	 *               *
	 *****************/
	public void draw(Canvas canvas) {
		canvas.drawBitmap(draw, 300, 320, paint);
	}
}
