package example.rosesandskull.player;

import android.graphics.Rect;
import android.view.MotionEvent;

/**********************************
 * 当たり判定をチェックするクラス *
 **********************************/
public class Collision {

	// 変数宣言

	/******************
	 * コンストラクタ *
	 ******************/
	public Collision() {
	}
	
	/************************************************************
	 * checkメソッド                                            * 
	 * 引数 : MotionEvent                                       * 
	 * 内容 : タッチ入力された場所が                            *
	 *		  セットされた座標の中かどうかを判定します          *
	 * 戻り値 : trueを返す　それ以外だった場合はfalseを返します *
	 ************************************************************/
	public boolean touchCheck(MotionEvent event, Rect rect1, int action) {
		// 当たっているかの判定
		if(event.getAction() == action) {
			// 当たっているかの判定
			if (rect1.left < event.getX() && rect1.right > event.getX() && 
					rect1.top < event.getY() && rect1.bottom > event.getY() ) {
					return true; // 当たっているのでtrueを返す
			}
		}
		
		// 当たっていないのでfalseを返す
		return false;
	}
	
	/*******************************************************************
	 * hitCheckメソッド                                                *
	 * 引数   : Rect rect1(対象1) rect2(対象2)                         *
	 * 内容   : 2つの矩形を引数でもらい                                *
	 * 		  その2つが当たっているかを判定します                      *
	 * 戻り値 : 当たっていればtrue それ以外だった場合はfalseを返します *
	 *******************************************************************/
	public boolean hitCheck(Rect rect1, Rect rect2) {
		// rect1とrect2が当たっているかを判定
		if(rect1.left < rect2.right && rect1.right > rect2.left &&
			rect1.top < rect2.bottom && rect1.bottom > rect2.top ) {
			return true; // 当たっていたらtrueを返す
		}
		
		return false;
	}
}
