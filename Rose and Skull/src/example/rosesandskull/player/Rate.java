package example.rosesandskull.player;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

/************************************
 * 判定をする前の数を決定するクラス *
 **********************************/
public class Rate {

	/**********
	 * 初期化 *
	 **********/
	private Paint paint = new Paint();
	private Texture texture = new Texture();

	/********
	 * 定数 *
	 ********/
	private static final int BACK = 1020;

	/********
	 * 変数 *
	 ********/
	private int count;

	/******************
	 * コンストラクタ *
	 ******************/
	public Rate() {
		paint.setTextSize(30);
		count = 0;
	}

	/****************
	 * drawメソッド * 描画処理 *
	 ****************/
	public void draw(Canvas canvas) {
		canvas.drawText("" + count, BACK, 250, paint);
	}

	/******************
	 * touchメソッド * タッチ入力処理 *
	 * 
	 * @param event
	 *            *
	 * @return *
	 ******************/
	public boolean touch(MotionEvent event) {

		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			if (BACK < event.getX()) {
				if (250 < event.getY() && 500 > event.getY()) {
					count++;
				}
			}
		}

		return false;
	}

}
