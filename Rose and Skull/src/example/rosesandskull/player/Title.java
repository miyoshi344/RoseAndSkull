package example.rosesandskull.player;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.view.MotionEvent;

/**********************
 * タイトル処理クラス *
 **********************/
public class Title {

	// 初期化
	Paint paint = new Paint();
	Texture texture = new Texture();
	Sound sound = new Sound();

	private MediaPlayer titleSound;

	/******************
	 * コンストラクタ *
	 ******************/
	public Title() {
	}

	/*****************
	 * drawメソッド  * 
	 * 描画処理      *
	 *****************/
	public void draw(Canvas canvas) {
		
		// 描画
		canvas.drawBitmap(Texture.TITLE, 0, 0, paint);
	}

	/*****************
	 * soundメソッド * 音の再生処理 *
	 *****************/
	public void sound() {
		titleSound = Sound.TITLESOUND; // タイトルのサウンドを取得

		// 音の再生
		// titleSound.start();
	}

	/*****************
	 * touchメソッド * 
	 * タッチ処理    *
	 *****************/
	public boolean touch(MotionEvent event) {

		// タッチされた時
		if (event.getAction() == MotionEvent.ACTION_DOWN) {

			// titleSound.stop();

			return true;
		}

		return false;
	}
}
