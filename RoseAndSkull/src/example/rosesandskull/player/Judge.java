package example.rosesandskull.player;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

/************************
 * 勝敗を判定するクラス *
 ************************/
public class Judge {

	// 初期化
	private Paint paint = new Paint();
	
	// 定数
	private final static float FONT_X = 420.0f;
	private final static float FONT_Y = 650.0f;
	
	// 変数
	private int remnant;
	private int draw;
	
	/******************
	 * コンストラクタ *
	 ******************/
	public Judge() {
		// 文字の設定
		paint.setTextSize(80);
		paint.setColor(Color.WHITE);
	}
	
	/************************************
	 * resetメソッド                    *
	 * 内容 : Judgeの状態を初期化します *
	 ************************************/
	public void reset() {
		remnant = 0;
		draw    = 0;
	}
	
	/***************************************************
	 * setCountメソッド                                *
	 * 引数 : int count                                *
	 * 内容 : Rateクラスで押されたカウント回数を       *
	 *        このメソッドで受け取りカウントに代入する *
	 ***************************************************/
	public void setCount(int count) {
		remnant = count; // もらってきた引数を代入する
	}
	
	/*********************
	 * drawメソッド      *
	 * 引数 : Canvas     *
	 * 内容 : 描画の処理 *
	 *********************/
	public void draw(Canvas canvas) {
		canvas.drawText("残り" + remnant + "枚", FONT_X, FONT_Y, paint);
		canvas.drawBitmap(Texture.BOARD, 0, 1270, paint);
		
		for(int i = 0; i < draw; i++) {
			canvas.drawBitmap(Texture.PLAYER[0][1], 100 * i, 1310.0f, paint);
		}
	}
	
	public void clear(Canvas canvas, int player) {
		canvas.drawText("プレイヤー" + (player + 1) + "が成功しました", 100, 800, paint);
	}
	
	public void success(Canvas canvas ,int player) {
		canvas.drawText("プレイヤー" + (player + 1) + "が勝利者です", 100, 800, paint);
	}
	
	public void defeat(Canvas canvas, int player) {
		canvas.drawText("チャレンジ失敗", 340, 500, paint);
		canvas.drawText("プレイヤー" + player + "は", 360, 700, paint);
		canvas.drawText("1枚選んでください", 280, 800, paint);
	}
	
	
	/***********************************************
	 * touchメソッド                               *
	 * 引数 : MotionEvent event, boolean flag      *
	 * 内容 : flagでカードの結果をもらってきて     *
	 * 		  その内容を処理します                 *
	 * 戻り値 : countが0になるか髑髏を引いたらtrue *
	 *          薔薇の場合はfalseを返す            *
	 ***********************************************/
	public boolean touch(MotionEvent event, boolean flag) {
		// 指を離したときに
		if(event.getAction() == MotionEvent.ACTION_UP) {
			Sound.CALD_SE.start();
			
			// flagがオンになった場合
			if(flag) {
				return true;
			}
			else {
				remnant--; // 薔薇ならremnantを減らす
				draw++;
			}
		}
		
		return false;
	}
	
	/**************************************
	 * countUpメソッド                    *
	 * 内容 : もらってきた回数            *
	 * 		  での判定を行います          *
	 * 戻り値 : カウントが0になったらtrue *
	 * 			それ以外はfalseを返します *
	 **************************************/
	public boolean countUp() {
		// カウントが0になったらtrueを返す
		if(remnant <= 0) {
			return true;
		}
		
		return false;
	}
}
