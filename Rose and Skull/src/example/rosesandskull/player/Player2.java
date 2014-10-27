package example.rosesandskull.player;

/***************************
 * プレイヤー2の処理クラス *
 ***************************/
public class Player2 extends Actor {

	// 変数宣言
	private int player = 5; // ボード配置場所
	private String name = "プレイヤー2";

	/******************
	 * コンストラクタ *
	 ******************/
	public Player2() {
		boardSet(player);
		super.name = name;
	}

	/*********************************
	 * caldSetメソッド               * 
	 * 内容 : caldクラス配列に       * 
	 * 		  カードの情報を格納する *
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
				cald[i].Table = Texture.SKULL2; // 髑髏のカードをセットする
			else
				cald[i].Table = Texture.ROSES2; // 薔薇のカードをセットする

			reverse[i] = null;
		}

		playerCald = Texture.CALD2;
	}
}