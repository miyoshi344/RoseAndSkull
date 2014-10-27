package example.rosesandskull.player;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.view.MotionEvent;

/************************
 * 背景を操作するクラス * 主に背景の描画と * 判定ボタンを管理する *
 ************************/
public class Back {

	private static final float BUTTON_X = 300.0f;
	private static final float BUTTON_Y = 320.0f;

	// 初期化
	private Paint paint = new Paint();
	private Matrix matrix = new Matrix();
	private Collision collision = new Collision();

	/******************
	 * コンストラクタ *
	 ******************/
	public Back() {

		// 文字の初期化
		paint.setColor(Color.RED);
		paint.setTextSize(50);

		matrix.setTranslate(0, 835);
	}

	/**********************
	 * drawメソッド * 描画処理 * 引数 : Canvas * 内容 :メイン画面の * 背景の描画を行う *
	 **********************/
	public void draw(Canvas canvas) {

		// 背景を描画する
		canvas.drawBitmap(Texture.BOARDBACK, 0, 0, paint);
	}

	/*******************************
	 * buttonメソッド * 描画処理 * 引数 : Canvas * 内容 : 判定に移行するための * ボタンを描画する *
	 *******************************/
	public void button(Canvas canvas) {
		// ボタンの描画を行う
		//canvas.drawBitmap(Texture.ROSES1, BUTTON_X, BUTTON_Y, paint);
	}

	/********************************************
	 * buttonTouchメソッド * タッチイベント処理 * 引数 : MotionEvent * 戻り値 : 当たったらtrueを返す * 内容
	 * : 描画したボタンの位置を * 取得しそこから当たり判定を作って判定する *
	 ********************************************/
	public boolean buttonTouch(MotionEvent event) {

		// 当たり判定を取得
		float targetX = BUTTON_X + Texture.ROSES1.getWidth();
		float targetY = BUTTON_Y + Texture.ROSES1.getHeight();

		collision.checkSet(BUTTON_X, BUTTON_Y, targetX, targetY);

		// ボタンがタッチされたか判定
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			if (collision.check(event)) {
				return true;
			}
		}

		return false;
	}
}