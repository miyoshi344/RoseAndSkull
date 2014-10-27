package example.rosesandskull.player;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.view.MotionEvent;

/**************************************
 * プレイヤーの基本クラス * 各プレイヤークラスはこれを継承して * 操作を行う *
 **************************************/
public class Actor {

	/**************************
	 * カード情報管理用クラス * (構造体) *
	 **************************/
	class Cald {

		float caldX; // カードのx座標
		float caldY; // カードのy座標

		float targetX; // カードのx座標当たり判定
		float targetY; // カードのy座標当たり判定

		boolean flag; // カードのフラグ

		Bitmap Table; // カードの表面画像
	}

	// 初期化
	protected Paint paint = new Paint();
	protected Matrix boardMatrix = new Matrix();
	protected Collision collision = new Collision();

	protected Cald[] cald = new Cald[4];
	protected Bitmap[] reverse = new Bitmap[4]; // 判定用カード画像

	// 定数
	protected final static int SKULL = 3; // 髑髏のカード

	protected final static float BACK = 835; // プレイヤーの背景描画位置
	protected final static float CALD_X = 30.0f; // カードの初期x座標
	protected final static float CALD_Y = 865.0f; // カードの初期y座標
	protected final static float BOARD_X = 300.0f; // ボードの初期x座標
	protected final static float BOARD_Y = 600.0f; // ボードの初期y座標
	protected final static float boardRotate = 60.0f; // ボードの回転度数
	protected final static float boardMove = -200.0f; // ボードの移動幅
	protected final static float SPACE = 185.0f; // カードの置くスペース

	// 変数
	protected String name;
	protected boolean set;
	protected boolean shutter;
	protected int count;

	protected Bitmap playerCald;

	/******************
	 * コンストラクタ *
	 ******************/
	public Actor() {
		// カードクラスの中身を初期化
		for (int i = 0; i < cald.length; i++) {
			cald[i] = new Cald(); // 配列は初期化しないと入れられない
		}

		set = false;
		shutter = false;
		count = 0;

		// 初期値の設定
		boardMatrix.setTranslate(BOARD_X, BOARD_Y);
	}

	/**************************************
	 * caldSetメソッド * カードの初期設定 * 内容 : caldクラス配列に * カードの情報を格納する *
	 * 各クラスで行いActorでは形だけ作る *
	 **************************************/
	public void caldSet() {
	}

	/****************************
	 * upDateメソッド * ボードの位置を更新させる * 処理を行います *
	 ****************************/
	public void upDate() {

		// boardMatrixに追加させる
		boardMatrix.preRotate(boardRotate); // 角度の修正
		boardMatrix.preTranslate(boardMove, 0); // 位置の修正
	}

	/***********************************
	 * boardSetメソッド * ボードの初期配置設定 * 引数 : player * 内容 : プレイヤーの番号をもらい * その数だけ位置を動かす
	 * *
	 ***********************************/
	public void boardSet(int player) {

		// 拾ってきた数字分移動させる
		for (int i = 0; i < player; i++) {
			boardMatrix.preRotate(boardRotate); // 角度の設定
			boardMatrix.preTranslate(boardMove, 0); // 位置の設定
		}
	}

	/*****************************
	 * drawメソッド * 描画処理 * 引数 : canvas * 内容 : プレイヤーの現在の * カードを描画する *
	 *****************************/
	public void draw(Canvas canvas) {

		// 背景の描画
		canvas.drawBitmap(Texture.PLAYERBACK, 0, BACK, paint);

		// 枚数分判定を行う
		for (int i = 0; i < cald.length; i++) {
			// 置かれてないのを確認したら
			if (cald[i].flag) {
				// 手札の描画を行う
				canvas.drawBitmap(cald[i].Table, cald[i].caldX, cald[i].caldY,paint);
			}
		}

		// プレイヤーの表示
		paint.setTextSize(40);
		canvas.drawText("" + name, 50, 800, paint);
	}

	/*************************************
	 * backDrawメソッド * プレイヤーの場の描画 * 引数 : canvas * 内容 : フィールドの設定した場所に * ボードを描画する *
	 *************************************/
	public void backDraw(Canvas canvas) {

		// ボードの描画
		canvas.drawBitmap(Texture.SKULLBOARD, boardMatrix, paint);

		// 置かれたらカードの描画
		if (set)
			canvas.drawBitmap(playerCald, boardMatrix, paint);
	}

	/**********************
	 * touchメソッド * タッチイベント処理 *
	 * 
	 * @param event
	 *            *
	 * @return *
	 **********************/
	public boolean touch(MotionEvent event) {
		boolean touchFlag = false;

		// カードの枚数分判定を行う
		for (int i = 0; i < cald.length; i++) {

			// 当たり判定の位置を更新する
			cald[i].targetX = cald[i].caldX + Texture.CALD1.getWidth();
			cald[i].targetY = cald[i].caldY + Texture.CALD1.getHeight();

			// カードの当たり判定をセット
			collision.checkSet(cald[i].caldX, cald[i].caldY, cald[i].targetX,
					cald[i].targetY);

			// カードの当たり判定
			if (collision.check(event)) {
				// カードを触れている時に
				if (cald[i].flag) {

					// 動かしたとき
					if (event.getAction() == MotionEvent.ACTION_MOVE) {
						// カードに触れていないかを確認
						if (!touchFlag) {
							// タッチした位置を画像の中央になるように修正
							float touchX = Texture.CALD1.getWidth() / 2; // x座標の設定
							float touchY = Texture.CALD1.getHeight() / 2; // y座標の設定

							// カードの位置を更新する
							cald[i].caldY = event.getY() - touchY; // x座標の設定
							cald[i].caldX = event.getX() - touchX; // y座標の設定

							touchFlag = true; // カードに触れている状態
						}
					}

					// 離したとき
					if (event.getAction() == MotionEvent.ACTION_UP) {
						// カードを離したときの位置がボードの上なら
						if (boardTouch(event)) {
							set = true; // ボードにカードを描画する
							cald[i].flag = false; // カードを使ったことにし表示を消す
							reverse[count] = cald[i].Table;
							count++;
							return true;
						}
					}
				}
			}
		}

		return false;
	}

	/*******************************************
	 * boardTouchメソッド * 引数 : MotionEvent * 内容 : ボードのタッチ処理を管理します * 戻り値 :
	 * ボードがタッチされていたらtrue * それ以外ならfalse *
	 *******************************************/
	public boolean boardTouch(MotionEvent event) {

		// 当たり判定用の座標を取得
		float boardTargetX = BOARD_X + Texture.SKULLBOARD.getWidth();
		float boardTargetY = BOARD_Y + Texture.SKULLBOARD.getHeight();

		// 当たり判定をセットする
		collision.checkSet(BOARD_X, BOARD_Y, boardTargetX, boardTargetY);

		// タッチした場所が当たっているかチェックする
		if (collision.check(event)) {
			return true; // 当たっていたらtrue
		}

		// それ以外はfalse
		return false;
	}

	public Bitmap open() {
		return reverse[count];
	}
}