package example.rosesandskull.player;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.view.MotionEvent;

/**************************************************
 * 判定に移行するかのボタンの管理を行うクラスです *
 **************************************************/
public class Button {
	// 初期化
	private Paint     paint       = new Paint();
	private Collision collisition = new Collision();
	
	// 定数
	private static final float LEFT_BUTTON  = 100.0f; // 左のボタン配置
	private static final float RIGHT_BUTTON = 700.0f; // 右のボタン配置
	private static final float BUTTON_X     = 400.0f; // ボタンのX座標
	private static final float BUTTON_Y     = 600.0f; // ボタンのY座標
	
	// 変数
	
	/******************
	 * コンストラクタ *
	 ******************/
	public Button() {
	}
	
	/*************************************
	 * waitDrawメソッド                  *
	 * 引数 : Canvas canvas int player   *
	 * 内容 : 次のプレイヤーを表示します *
	 *************************************/
	public void waitDraw(Canvas canvas, int player) {
		paint.setColor(Color.WHITE);
		paint.setTextSize(80);
		canvas.drawBitmap(Texture.YES, BUTTON_X, BUTTON_Y, paint);
		canvas.drawText("次はプレイヤー" + player + "です", 250, 450, paint);
	}

	/*********************************
	 * drawメソッド                  *
	 * 引数 : Canvas canvas          *
	 * 内容 : ボタンの描画を行います *
	 *********************************/
	public void draw(Canvas canvas) {
		paint.setTextSize(100);
		paint.setColor(Color.WHITE);
		paint.setTypeface(Typeface.MONOSPACE);
		canvas.drawText("チャレンジしますか", LEFT_BUTTON + 50, BUTTON_Y - 350, paint);
		
		canvas.drawBitmap(Texture.YES, LEFT_BUTTON, BUTTON_Y, paint);
		canvas.drawBitmap(Texture.NO, RIGHT_BUTTON, BUTTON_Y, paint);
	}
	
	/*********************************************
	 * touchメソッド                             *
	 * 引数 : MotionEvent event                  *
	 * 内容 : タッチされたらtrueを返します       *
	 * 戻り値 : タッチの入力があったらtureを返す *
	 *********************************************/
	public boolean touch(MotionEvent event) {
		// YESボタンの判定用矩形を作成します
		Rect yes   = new Rect();
		yes.top    = (int) BUTTON_Y;
		yes.left   = (int) BUTTON_X;
		yes.bottom = yes.top  + Texture.YES.getHeight();
		yes.right  = yes.left + Texture.YES.getWidth();
		
		// YESボタンに触れているかの判定
		if(collisition.touchCheck(event, yes, MotionEvent.ACTION_DOWN)) {
			Sound.BUTTON_SE.start();
			return true;
		}
		
		return false;
	}
	
	/***********************************
	 * touchメソッド                   *
	 * 引数 : MotionEvent event        *
	 * 内容 : ボタンのタッチイベントの *
	 * 		  判定を行います           *
	 ***********************************/
	public int buttonTouch(MotionEvent event) {
		int judgeFlag = 0;
		
		// YESボタンの判定用矩形を作成します
		Rect yes   = new Rect();
		yes.top    = (int) BUTTON_Y;
		yes.left   = (int) LEFT_BUTTON;
		yes.bottom = yes.top  + Texture.YES.getHeight();
		yes.right  = yes.left + Texture.YES.getWidth();
		
		// YESボタンに触れているかの判定
		if(collisition.touchCheck(event, yes, MotionEvent.ACTION_DOWN)) {
			Sound.BUTTON_SE.start();
			judgeFlag = 1; // 判定フラグをtrueにする
		}
		
		// NOボタンの判定用矩形を作成します
		Rect no   = new Rect();
		no.top    = (int) BUTTON_Y;
		no.left   = (int) RIGHT_BUTTON;
		no.bottom = no.top  + Texture.NO.getHeight();
		no.right  = no.left + Texture.NO.getWidth();
		
		// NOボタンに触れているかの判定
		if(collisition.touchCheck(event, no, MotionEvent.ACTION_DOWN)) {
			Sound.BUTTON_SE.start();
			judgeFlag = 2; // 判定フラグをオフにする
		}
		
		return judgeFlag;
	}
}
