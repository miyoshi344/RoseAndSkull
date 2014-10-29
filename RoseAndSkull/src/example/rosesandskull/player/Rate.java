package example.rosesandskull.player;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

/************************************
 * 判定をする前の数を決定するクラス *
 **********************************/
public class Rate {
	/**********
	 * 初期化 *
	 **********/
	private Paint     paint     = new Paint();
	private Collision collision = new Collision();
	
	/********
	 * 定数 *
	 ********/
	private static final float BUTTON_X     = 450.0f;  // ボタンのX座標配置
	private static final float BUTTON_Y     = 1320.0f; // ボタンのY座標配置
	private static final float SPACE_BUTTON = 350.0f;  // ボタンの間隔
	
	/********
	 * 変数 *
	 ********/
	private int   count;       // 押された回数のカウント用
	private int   totalCald;   // 場の合計カード数
	private float buttonRight; // 右ボタンの座標設定用

	/******************
	 * コンストラクタ *
	 ******************/
	public Rate() {
		reset();
		
		// 座標の設定
		buttonRight = BUTTON_X + SPACE_BUTTON;
		
		// 文字の設定
		paint.setColor(Color.WHITE);
	}
	
	/*************************************
	 * resetメソッド                     *
	 * 内容 : カウントの初期化を行います *
	 *************************************/
	public void reset() {
		
		// カウントの初期化
		count = 1;
	}
	
	/****************************************
	 * totalCaldメソッド                    *
	 * 引数 : int cald 場のカード合計       *
	 * 内容 :場の合計カード枚数を取得します *
	 ****************************************/
	public void totalCald(int cald) {
		totalCald = cald;
	}
	
	/*******************************************************
	 * statusDrawメソッド                                  *
	 * 引数 : Canvas canvas int player                     *
	 * 内容 : カードの合計数と　                           *
	 * 		  現在一番レートを上げたプレイヤーを表示します *
	 *******************************************************/
	public void statusDraw(Canvas canvas, int player) {
		paint.setTextSize(50);
		canvas.drawText("場の合計数 :" + totalCald + " 枚", 600, 50, paint);
		canvas.drawText("宣言した人 :" + (player + 1) + " プレイヤー", 600, 100, paint);
	}

	/***************************************
	 * countDrawメソッド                   *
	 * 引数 : Canvas canvas                *
	 * 内容 : 現在のレート枚数を表示します *
	 ***************************************/
	public void countDraw(Canvas canvas) {
		paint.setTextSize(100);
		canvas.drawText("現在" + count + "枚", 480, 580, paint); // カウントの描画
	}
	
	/****************
	 * drawメソッド * 
	 * 描画処理     *
	 ****************/
	public void draw(Canvas canvas) {		
		// ボタンの描画
		canvas.drawBitmap(Texture.ARROW_RIGHT, buttonRight,BUTTON_Y, paint);
		canvas.drawBitmap(Texture.BUTTON, BUTTON_X, BUTTON_Y, paint);
	}

	/******************
	 * touchメソッド  *
	 * タッチ入力処理 *
	 * @param event  *
	 * @return       *
	 ******************/
	public boolean touch(MotionEvent event) {
		
		// ボタンの当たり判定をセットします
		Rect buttonRect   = new Rect();
		buttonRect.top    = (int) BUTTON_Y;
		buttonRect.left   = (int) BUTTON_X;
		buttonRect.bottom = buttonRect.top  + Texture.BUTTON.getHeight();
		buttonRect.right  = buttonRect.left + Texture.BUTTON.getWidth();
	
		// ボタンの当たり判定をチェックします
		if(collision.touchCheck(event, buttonRect, MotionEvent.ACTION_UP)) {
			Sound.BUTTON_SE.start();
			return true; // trueを返して次の処理へ
		}
		
		// 右矢印の当たり判定をセットします
		Rect rightRect   = new Rect();
		rightRect.top    = (int) BUTTON_Y;
		rightRect.left   = (int) buttonRight;
		rightRect.bottom = rightRect.top  + Texture.ARROW_RIGHT.getHeight();
		rightRect.right  = rightRect.left + Texture.ARROW_RIGHT.getWidth();
		
		// 右矢印の当たり判定をチェックします
		if(collision.touchCheck(event, rightRect, MotionEvent.ACTION_UP)) {
			if(totalCald > count) {
				count++; // 押されたらカウントを増やす
				Sound.BUTTON_SE.start();
			}
		}
		
		return false;
	}

	/***************************************
	 * numberSheetsメソッド                *
	 * 内容 : 入力されたカウントを返します *
	 * 戻り値 : count                      *
	 ***************************************/
	public int numberSheets() {
		return count; // カウントを戻り値として返します
	}
}
