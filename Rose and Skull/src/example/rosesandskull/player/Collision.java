package example.rosesandskull.player;

import android.view.MotionEvent;

/**********************************
 * 当たり判定をチェックするクラス *
 **********************************/
public class Collision {

	// 変数宣言
	private float left;
	private float right;
	private float up;
	private float down;

	/******************
	 * コンストラクタ *
	 ******************/
	public Collision() {
		// 当たり判定の初期化
		left  = 0.0f;
		right = 0.0f;
		up    = 0.0f;
		down  = 0.0f;
	}

	/***************************************************
	 * checkSetメソッド                                * 
	 * 引数 : float x1 画像の左座標                    *
	 * 		  float y1 画像の上座標                    *
	 * 		  float x2 画像の右座標                    *
	 * 		  float y2 画像の下座標                    *
	 *                                                 *
	 * 内容 : 当たり判定に必要な範囲を引数としてもらい * 
	 * 		  変数に格納し保存します                   *
	 ***************************************************/
	public void checkSet(float x1, float y1, float x2, float y2) {
		left  = x1; // 左座標
		right = x2; // 右座標
		up    = y1; // 上座標
		down  = y2; // 下座標
	}

	/**********************************************************
	 * checkメソッド                                          * 
	 * 引数 : MotionEvent                                     * 
	 *                                                        * 
	 * 内容 : タッチ入力された場所がセットされた座標の中なら  *
	 * 		  trueを返す　それ以外だった場合はfalseを返します *
	 **********************************************************/
	public boolean check(MotionEvent event) {

		// 当たっているかの判定

		// x軸の判定
		if (left < event.getX() && right > event.getX()) {
			// y軸の判定
			if (up < event.getY() && down > event.getY()) {
				return true; // 当たっているのでtrueを返す
			}
		}

		// 当たっていないのでfalseを返す
		return false;
	}

}
