package example.rosesandskull.player;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

/**********************
 * タイトル処理クラス *
 **********************/
public class Title {
	// 初期化 
	private Paint       paint       = new Paint();
	private Collision   collision   = new Collision();
	
	// 定数
	private final static float LEFT_BUTTON        = 150.0f;
	private final static float RIGHT_BUTTON       = 650.0f;
	private final static float BUTTON_Y           = 1350.0f;
	private final static float PLAYER_TOP_BUTTON  = 1000.0f;
	private final static float PLAYER_DOWN_BUTTON = 1400.0f;
	private final static float TEXT_X             = 280.0f;
	private final static float TEXT_Y             = 250.0f;

	/******************
	 * コンストラクタ *
	 ******************/
	public Title() {
	}

	/***********************************
	 * drawメソッド                    *
	 * 引数 : Canvas canvas            *
	 * 内容 : タイトルの描画を行います *
	 ***********************************/
	public void backDraw(Canvas canvas) {
		// 描画
		canvas.drawBitmap(Texture.TITLE, 0, 0, paint);
	}
	
	/*********************************
	 * gameButtonメソッド            *
	 * 引数 : Canvas canvas          *
	 * 内容 : ボタンの描画を行います *
	 *********************************/
	public void gameButton(Canvas canvas) {
		canvas.drawBitmap(Texture.START, LEFT_BUTTON,BUTTON_Y, paint); // スタートボタン
		canvas.drawBitmap(Texture.RULE,  RIGHT_BUTTON,BUTTON_Y, paint); // ルールボタン
	}
	
	/*********************************************
	 * playerButton                              *
	 * 引数 : Canvas canvas                      *
	 * 内容 : プレイヤー人数のボタンを描画します *
	 *********************************************/
	public void playerButton(Canvas canvas) {
		
		paint.setTextSize(100);
		paint.setColor(Color.WHITE);
		canvas.drawText("プレイ人数を", TEXT_X, TEXT_Y, paint);
		canvas.drawText("選んでください", TEXT_X - 50.0f, TEXT_Y + 200.0f, paint);
		canvas.drawBitmap(Texture.PLAYER_3, LEFT_BUTTON , PLAYER_TOP_BUTTON, paint);  // 3人用ボタン
		canvas.drawBitmap(Texture.PLAYER_4, RIGHT_BUTTON, PLAYER_TOP_BUTTON, paint);  // 4人用ボタン
		canvas.drawBitmap(Texture.PLAYER_5, LEFT_BUTTON , PLAYER_DOWN_BUTTON, paint); // 5人用ボタン
		canvas.drawBitmap(Texture.PLAYER_6, RIGHT_BUTTON, PLAYER_DOWN_BUTTON, paint); // 6人用ボタン
	}

	/********************************************
	 * touchメソッド                            *
	 * 引数 : MotionEvent event                 *
	 * 内容 : タイトルのタッチ処理を行います    *
	 ********************************************/
	public int touch(MotionEvent event) {
		int button = 0;
		
		/* スタートボタンの判定 */
		// 判定用の当たり判定矩形を作成
		Rect start   = new Rect();
		start.top    = (int) BUTTON_Y;
		start.left   = (int) LEFT_BUTTON;
		start.bottom = start.top  + Texture.START.getHeight();
		start.right  = start.left + Texture.START.getWidth();

		// スタートボタンがタッチされた時
		if (collision.touchCheck(event, start, MotionEvent.ACTION_UP)) {
			Sound.BUTTON_SE.start();
			button = 1;
		}
		
		/* ルールボタンの判定 */
		// 判定用の当たり判定矩形を作成
		Rect rule   = new Rect();
		rule.top    = (int) BUTTON_Y;
		rule.left   = (int) RIGHT_BUTTON;
		rule.bottom = rule.top  + Texture.RULE.getWidth();
		rule.right  = rule.left + Texture.RULE.getHeight();
		
		// ルールボタンがタッチされた時
		if(collision.touchCheck(event, rule, MotionEvent.ACTION_UP)) {
			Sound.BUTTON_SE.start();
			button = 2;
		}

		return button;
	}
	
	/*******************************************
	 * playerSelectメソッド                    *
	 * 引数 : MotionEvent event                *
	 * 内容 : タッチされたボタンの数字を返し   *
	 * 		  プレイヤー人数を決定します       *
	 * 戻り値 : 押されたボタンの人数を返します *
	 *******************************************/
	public int playerSelect(MotionEvent event) {
		
		// プレイヤー3用当たり判定矩形を作成
		Rect player3   = new Rect();
		player3.top    = (int) PLAYER_TOP_BUTTON;
		player3.left   = (int) LEFT_BUTTON;
		player3.bottom = player3.top  + Texture.PLAYER_3.getHeight();
		player3.right  = player3.left + Texture.PLAYER_3.getWidth();
		
		// プレイヤー3のボタン判定
		if(collision.touchCheck(event, player3, MotionEvent.ACTION_DOWN)) {
			Sound.BUTTON_SE.start();
			return 3; // 3人を返す
		}
		
		// プレイヤー4用当たり判定矩形を作成
		Rect player4   = new Rect();
		player4.top    = (int) PLAYER_TOP_BUTTON;
		player4.left   = (int) RIGHT_BUTTON;
		player4.bottom = player4.top  + Texture.PLAYER_4.getHeight();
		player4.right  = player4.left + Texture.PLAYER_4.getWidth();
		
		// プレイヤー4のボタン判定
		if(collision.touchCheck(event, player4, MotionEvent.ACTION_DOWN)) {
			Sound.BUTTON_SE.start();
			return 4; // 4人を返す
		}
		
		// プレイヤー5用当たり判定矩形を作成
		Rect player5   = new Rect();
		player5.top    = (int) PLAYER_DOWN_BUTTON;
		player5.left   = (int) LEFT_BUTTON;
		player5.bottom = player5.top  + Texture.PLAYER_5.getHeight();
		player5.right  = player5.left + Texture.PLAYER_5.getWidth();
		
		// プレイヤー5のボタン判定
		if(collision.touchCheck(event, player5, MotionEvent.ACTION_DOWN)) {
			Sound.BUTTON_SE.start();
			return 5; // 5人を返す
		}
		
		// プレイヤー6用当たり判定矩形を作成
		Rect player6   = new Rect();
		player6.top    = (int) PLAYER_DOWN_BUTTON;
		player6.left   = (int) RIGHT_BUTTON;
		player6.bottom = player6.top + Texture.PLAYER_6.getHeight();
		player6.right  = player6.left + Texture.PLAYER_6.getWidth();
		
		// プレイヤー6のボタン判定
		if(collision.touchCheck(event, player6, MotionEvent.ACTION_DOWN)) {
			Sound.BUTTON_SE.start();
			return 6;
		}
		
		return 0;
	}
}
