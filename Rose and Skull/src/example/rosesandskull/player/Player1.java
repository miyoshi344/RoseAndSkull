package example.rosesandskull.player;

import android.view.MotionEvent;

/*************************************
 * Actorクラスを継承して作る         * 
 * Player1クラス                     * 
 * プレイヤーごとの設定はこちらで行う*
 *************************************/
public class Player1 extends Actor {

	// 変数宣言
	private int player = 0; // ボード配置場所
	private String name = "プレイヤー1";

	/******************
	 * コンストラクタ *
	 ******************/
	public Player1() {
		boardSet(player); // プレイヤー1を渡す
		super.name = name;
	}

	/*********************************
	 * caldSetメソッド               *
	 * 内容 : caldクラス配列に       *
	 *  	  カードの情報を格納する *
	 *********************************/
	@Override
	public void caldSet() {

		// カードの初期化
		for (int i = 0; i < cald.length; i++) {
			// カードの座標の設定
			cald[i].caldX = CALD_X + (SPACE * i); // x座標の設定
			cald[i].caldY = CALD_Y; // y座標の設定

			cald[i].flag = true; // カードを使ってない設定にする

			// カードの設定
			if (i == SKULL)
				cald[i].Table = Texture.SKULL1; // 髑髏のカードをセットする
			else
				cald[i].Table = Texture.ROSES1; // 薔薇のカードをセットする

			reverse[i] = null;
		}

		playerCald = Texture.CALD1;
	}

	public boolean disPlay(MotionEvent event) {

		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			if (boardTouch(event)) {
				return true;
			}
		}

		return false;
	}
}
