package example.rosesandskull.player;

import android.graphics.Color;

/*************************************
 * Actorクラスを継承して作る         * 
 * Playerクラス                      * 
 * プレイヤーごとの設定はこちらで行う*
 *************************************/
public class Player extends Actor {
	
	// 変数
	private static final float ROW_X    = 450.0f; // 横の配置座標
	private static final float ROW_1    = 200.0f; // 上のY軸配置座標
	private static final float ROW_2    = 750.0f; // 下のY軸配置座標
	private static final float INTERVAL = 350.0f; // カードの間隔
	
	private static final float TEXT_LEFT  = 80.0f;
	private static final float TEXT_RIGHT = 680.0f;
	
	private static final float TEXT_UP     = 1420.0f;
	private static final float TEXT_MIDDLE = 1520.0f;
	private static final float TEXT_DOWN   = 1620.0f;
	
	/***********************************
	 * コンストラクタ                  *
	 *                                 *
	 * 引数 : int player               *
	 * 内容 : 引数でもらった数字の     *
	 * 		  コンストラクタを実行する *
	 ***********************************/
	public Player(int player) {
		// 親クラスのinitを実行
		super.reset();
		
		// フォントの設定
		super.playerPaint.setTextSize(50);
		
		// もらってきた引数のコンストラクタを実行する
		switch(player) {
		
			// プレイヤー1のコンストラクタを実行
			case 0: Player1(); break;
			
			// プレイヤー2のコンストラクタを実行
			case 1: Player2(); break;
			
			// プレイヤー3のコンストラクタを実行
			case 2: Player3(); break;
			
			// プレイヤー4のコンストラクタを実行
			case 3: Player4(); break;
			
			// プレイヤー5のコンストラクタを実行
			case 4: Player5(); break;
			
			// プレイヤー6のコンストラクタを実行
			case 5: Player6(); break;
		}
	}

	/*********************
	 * プレイヤー1の設定 *
	 *********************/
	public void Player1() {
		super.name    = "Player1"; // プレイヤーの名前
		super.judge_Y = ROW_1;     // 判定に移行した時のY座標
		super.judge_X = ROW_X - INTERVAL; // 判定に移行した時のX座標
		super.playerPaint.setColor(Color.rgb(200, 139, 147));
		super.textX = TEXT_LEFT;
		super.textY = TEXT_UP;
	}

	/*********************
	 * プレイヤー2の設定 *
	 *********************/
	public void Player2()  {
		super.name = "Player2"; // プレイヤーの名前 
		super.judge_Y = ROW_1;  // 判定に移行した時のY座標
		super.judge_X = ROW_X;  // 判定に移行した時のX座標
		super.playerPaint.setColor(Color.rgb(200, 82, 38));
		super.textX = TEXT_LEFT;
		super.textY = TEXT_MIDDLE;
	}
	
	/*********************
	 * プレイヤー3の設定 *
	 *********************/
	public void Player3() {
		super.name = "Player3"; // プレイヤーの名前 
		super.judge_Y = ROW_1;  // 判定に移行した時のY座標
		super.judge_X = ROW_X + INTERVAL;  // 判定に移行した時のX座標
		super.playerPaint.setColor(Color.rgb(71, 107, 94));
		super.textX = TEXT_LEFT;
		super.textY = TEXT_DOWN;
	}
	
	/*********************
	 * プレイヤー4の設定 *
	 *********************/
	public void Player4() {
		super.name = "Player4"; // プレイヤーの名前
		super.judge_Y = ROW_2;  // 判定に移行した時のY座標
		super.judge_X = ROW_X - INTERVAL;  // 判定に移行した時のX座標
		super.playerPaint.setColor(Color.rgb(221, 206, 139));
		super.textX = TEXT_RIGHT;
		super.textY = TEXT_UP;
	}
	
	/*********************
	 * プレイヤー5の設定 *
	 *********************/
	public void Player5() {
		super.name = "Player5"; // プレイヤーの名前
		super.judge_X = ROW_X;  // 判定に移行した時のX座標
		super.judge_Y = ROW_2;  // 判定に移行した時のY座標
		super.playerPaint.setColor(Color.rgb(153, 147, 133));
		super.textX = TEXT_RIGHT;
		super.textY = TEXT_MIDDLE;
	}
	
	/*********************
	 * プレイヤー6の設定 *
	 *********************/
	public void Player6() {
		super.name = "Player6"; // プレイヤーの名前
		super.judge_Y = ROW_2;  // 判定に移行した時のY座標
		super.judge_X = ROW_X + INTERVAL;  // 判定に移行した時のX座標
		super.playerPaint.setColor(Color.rgb(153, 155, 166));
		super.textX = TEXT_RIGHT;
		super.textY = TEXT_DOWN;
	}
}
