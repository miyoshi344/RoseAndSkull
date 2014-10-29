package example.rosesandskull.player;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

/**************************************
 * プレイヤーの基本クラス             *
 * 各プレイヤークラスはこれを継承して * 
 * 操作を行う                         *
 **************************************/
public class Actor {
	/**************************
	 * カード情報管理用クラス * 
	 * (構造体)               *
	 **************************/
	class Cald {
		float caldX; // カードのx座標
		float caldY; // カードのy座標

		float targetX; // カードのx座標当たり判定
		float targetY; // カードのy座標当たり判定

		boolean flag;        // カードを持っているかのフラグ
		boolean caldFlag;    // カードに触れているかのフラグ
		boolean use;         // カードが生きているかのフラグ
		boolean caldContent; // カードの配置番号格納用
		
		Bitmap table;         // 表面画像
		Bitmap back;          // 裏面画像
	}

	// 初期化
	protected Paint     paint       = new Paint();
	protected Paint     playerPaint = new Paint();
	protected Matrix    boardMatrix = new Matrix();
	protected Collision collision   = new Collision();
	protected Random    rand        = new Random();

	protected Cald[]     cald       = new Cald[4];        // カードクラス用配列
	protected boolean[] reverse    = new boolean[4];    // カードの判定用
	
	// 定数
	protected final static float BOARD_X     = 450.0f;  // ボードの初期x座標
	protected final static float BOARD_Y     = 950.0f;  // ボードの初期y座標
	protected final static float boardRotate = 60.0f;   // ボードの回転度数
	protected final static float boardMove   = -300.0f; // ボードの移動幅
	
	// 変数
	protected int     skullTarget; // 髑髏の配置用乱数
	protected int     count;       // カウント用
	protected float   judge_X;     // 判定時のx座標
	protected float   judge_Y;     // 判定時のy座標
	protected float   textX;
	protected float   textY;
	protected boolean board;

	protected String name;         // プレイヤーネーム

	/******************
	 * コンストラクタ *
	 ******************/
	public Actor() {
		// カードクラスの中身を初期化
		for (int i = 0; i < cald.length; i++) {
			cald[i] = new Cald(); // 配列は初期化しないと入れられない
		}
	
		// 髑髏の乱数を取得
		skullTarget   = rand.nextInt(4);
	}
	
	/*************************************
	 * resetメソッド                     *
	 * 内容 : カードの初期化や           *
	 * 		  カウントの初期化を行います *
	 *************************************/
	public void reset() {
		count   = 0; // カードのカウントを初期化
		caldS();     // カードの配置の初期化
	}
	
	/**********************************
	 * upDateメソッド                 * 
	 * 内容 :ボードの位置を更新させる * 
	 * 		 処理を行います           *
	 **********************************/
	public void upDate() {
		// boardMatrixに追加させる
		boardMatrix.preRotate(boardRotate);     // 角度の修正
		boardMatrix.preTranslate(boardMove, 0); // 位置の修正
	}

	/**************************************
	 * caldSetメソッド                    * 
	 * 内容 : caldクラス配列に            * 
	 * 		  カードの情報を格納する      *
	 **************************************/
	public void caldSet(int player) {
		// 内部変数宣言
		int skull   = 0; // 髑髏のBitmap番号
		int roses   = 1; // 薔薇のBitmap番号
		int reverse = 2; // 裏面のBitmap番号
		
		// カードの初期化
		for (int i = 0; i < cald.length; i++) {
			// カードの設定
			if (i == skullTarget) {
				cald[i].table = Texture.PLAYER[player][skull]; // 髑髏のカードをセットする
				cald[i].caldContent = true; // カードの判定をセットする
			}
			else {
				cald[i].table = Texture.PLAYER[player][roses]; // 薔薇のカードをセットする
				cald[i].caldContent = false; // カードの判定をセットする
			}
			
			cald[i].back = Texture.PLAYER[player][reverse]; // カードの裏面をセットする
			cald[i].use = true;
		}
	}
	
	/*********************************
	 * caldSメソッド                 *
	 * 内容 : カードの配置を行います *
	 *********************************/
	public void caldS() {
		float caldX = 30.0f;   // カードの初期x座標
		float caldY = 1310.0f; // カードの初期y座標
		float space = 280.0f;  // カードの置くスペース
		
		for(int i = 0; i < cald.length; i++) {
			if(cald[i].use) {
				// カードの座標の設定
				cald[i].caldX	 = caldX + (space * i); // x座標の設定
				cald[i].caldY	 = caldY;               // y座標の設定
				cald[i].flag 	 = true;               // カードを使える状態にする
				cald[i].caldFlag = true; // カードを表示できるようにする
			}
			else 
				cald[i].flag = false;  // カードを使えない状態にする
		}
	}
	
	/*****************************************
	 * boardSetメソッド                      * 
	 * 引数 : int player                     * 
	 * 内容 : 引数でプレイヤーの番号をもらい *
	 *        その数だけ初期位置を更新させる *
	 *****************************************/
	public void boardSet(int player) {
		// 初期値の設定
		boardMatrix.setTranslate(BOARD_X, BOARD_Y);

		// 拾ってきた数字分移動させる
		for (int i = 0; i < player; i++) {
			boardMatrix.preRotate(boardRotate);     // 角度の設定
			boardMatrix.preTranslate(boardMove, 0); // 位置の設定
		}
	}

	/*********************************
	 * drawメソッド                  * 
	 * 引数 : Canvas canvas          *
	 * 内容 : プレイヤーの現在の     * 
	 * 		  手持ちカードを描画する *
	 *********************************/
	public void draw(Canvas canvas) {
		// 枚数分判定を行う
		for (Cald caldNo : cald) {
			// 置かれてないのを確認したら
			if (caldNo.flag) {
				// 手札の描画を行う
				canvas.drawBitmap(caldNo.table, caldNo.caldX, caldNo.caldY,paint);
			}
		}
	}
	
	/***************************************
	 * touchメソッド                       *
	 * 引数 : MotionEvent event            * 
	 * 内容 : カードに触れている状態の     *
	 * 		  カードの動きにを操作する     *
	 * 戻り値 : 処理が終わったらtrueを返す *
	 ***************************************/
	public boolean touch(MotionEvent event) {
		
		// カードの枚数分判定を行う
		for (int i = 0; i < cald.length; i++) {
			// 当たり判定の位置を取得する
			Rect rect   = new Rect();
			rect.top    = (int) cald[i].caldY;
			rect.left   = (int) cald[i].caldX;
			rect.bottom = rect.top  + Texture.CALD.getHeight();
			rect.right  = rect.left + Texture.CALD.getWidth();

			// カードに触れていないかを確認
			if (cald[i].caldFlag) {
				// カードの当たり判定
				if (cald[i].flag && collision.touchCheck(event,rect, MotionEvent.ACTION_MOVE)) {
					// タッチした位置を画像の中央になるように修正
					float touchX = Texture.CALD.getWidth() / 2; // x座標の設定
					float touchY = Texture.CALD.getHeight() / 2; // y座標の設定

					// カードの位置を更新する
					cald[i].caldY = event.getY() - touchY; // x座標の設定
					cald[i].caldX = event.getX() - touchX; // y座標の設定
					
					// タッチされている時は
					for(int j = 0; j < cald.length; j++) {
						// 他のカードを触れないようにする
						if(j != i) {
							// 他のカードの判定をオフにしておく
							cald[j].caldFlag = false;
						}
					}
				}
			}
			
			// タッチを離したら
			if(event.getAction() == MotionEvent.ACTION_UP) {
				// カードを触れられるようにする
				for(Cald caldNo : cald) caldNo.caldFlag = true;
				
				// もしカードがボードに置かれたら
				if(cald[i].flag && boardTouch(event, rect)) {
					cald[i].flag   = false;              // カードのフラグをオフにする
					reverse[count] = cald[i].caldContent; // カードの情報を格納する
					count++;                              // カウントを増やす
					return true;
				}
			}
		}
	
		return false;
	}
	
	/***********************************************
	 * caldFlagメソッド                            *
	 * 内  容 : カードが全部使えるかを確認します   *
	 * 戻り値 : 全部使い切っていたらtrueを返します *
	 ***********************************************/
	public boolean caldFlag() {
		int count = 0;
		
		// カードのフラグがオフになっていたらcountを1増やす
		for(Cald caldNo : cald) {
			if(!caldNo.flag) count++;
		}
		// カードを全部使っていたらtrueを返す
		if(count == 4) return true;
		// それ以外だったらfalseを返す
		else return false;
	}
	
	/*********************************
	 * playerメソッド                *
	 * 内容 : プレイヤー名を表示する *
	 *********************************/
	public void playerName(Canvas canvas) {
		// プレイヤーの表示
		paint.setTextSize(100);
		paint.setColor(Color.WHITE);
		canvas.drawText("" + name, 20, 1215.0f, paint);
	}
	
	public int playerCaldRemaining() {
		int count = 0;
		
		for(Cald caldNo : cald) if(caldNo.flag) count++;
		return count;
	}

	/*************************************
	 * backDrawメソッド                  *
	 * 引数 : Canvas canvas              * 
	 * 内容 : フィールドの設定した場所に * 
	 * 		  ボードを描画する           *
	 *************************************/
	public void backDraw(Canvas canvas) {
		// Bitmap
		Bitmap boardTexture;
		// 現在の点数に合わせてボードが変える
		if(!board) boardTexture = Texture.SKULLBOARD; // 髑髏盤
		else       boardTexture = Texture.ROSESBOARD; // 薔薇盤
		
		// ボードの描画
		canvas.drawBitmap(boardTexture, boardMatrix, paint);
		
		// 置かれたらカードの描画
		if(count > 0) canvas.drawBitmap(cald[0].back, boardMatrix, paint);
		if(deathFlag()) canvas.drawBitmap(cald[skullTarget].table, boardMatrix, paint);
	}
	
	/*******************************************
	 * boardTouchメソッド                      * 
	 * 引 数  : MotionEvent                    * 
	 * 内 容  : ボードのタッチ処理を管理します * 
	 * 戻り値 : ボードがタッチされていたらtrue * 
	 * 		    それ以外ならfalse              *
	 *******************************************/
	public boolean boardTouch(MotionEvent event ,Rect rect) {
		// 当たり判定用の矩形を作成
		Rect rect2   = new Rect();
		rect2.top    = (int) BOARD_Y;
		rect2.left   = (int) BOARD_X;
		rect2.bottom = rect2.top  + Texture.SKULLBOARD.getHeight();
		rect2.right  = rect2.left + Texture.SKULLBOARD.getWidth();
		
		// 当たっているかをチェックし当たっているならtrueを返す
		if(collision.hitCheck(rect, rect2)) return true;
		
		return false;
	}
	
	/*************************************************
	 * caldOpenメソッド                              *
	 * 引 数  : MotionEvent                          *
	 * 内 容  : めくった時にカードの情報を取得し     *
	 * 		    内容を返します                       *
	 * 戻り値 : 髑髏ならtrue 薔薇ならfalseを返します *
	 *************************************************/
	public boolean caldOpen(MotionEvent event) {
		// 当たり判定用の座標を取得
		Rect rect   = new Rect();
		rect.top    = (int) judge_Y;
		rect.left   = (int) judge_X;
		rect.bottom = rect.top  + Texture.SKULLBOARD.getHeight();
		rect.right  = rect.left + Texture.SKULLBOARD.getWidth();
		
		// 1枚以上置かれていて
		if(count > 0) {
			// ボードに触れているかをチェックする
			if(collision.touchCheck(event, rect, MotionEvent.ACTION_UP)) {
				return true;
			}
		}
		return false;
	}
	
	/***************************************************
	 * judgeDrawメソッド                               *
	 * 引数 : Canvas canvas                            *
	 * 内容 : 判定に移行した時のボードの描画を行います *
	 ***************************************************/
	public void judgeDraw(Canvas canvas) {
		// Bitmap
		Bitmap boardTexture;
		if(!board) boardTexture = Texture.SKULLBOARD; // 髑髏盤
		else       boardTexture = Texture.ROSESBOARD; // 薔薇盤
		
		// ボードの描画
		canvas.drawBitmap(boardTexture, judge_X, judge_Y, paint);
		
		// 置かれたらカードの描画
		if (count > 0) canvas.drawBitmap(cald[0].back, judge_X, judge_Y, paint);
		canvas.drawText("" + count, judge_X, judge_Y, paint);
	}
	
	/*************************************************
	 * caldCheckメソッド                             *
	 * 内 容  : 場に置かれているカードの内容を       *
	 * 		    判定し戻り値で返します               *
	 * 戻り値 : 髑髏ならtrue 薔薇ならfalseを返します *
	 *************************************************/
	public boolean caldCheck() {
		if(reverse[count - 1]) return true; // 内容が髑髏ならtrueを返す
		else count--;						  // 内容が薔薇ならカウントを1減らす
		
		return false;
	}
	
	/***********************************************
	 * penaltyDrawメソッド                         *
	 * 引数 : Canvas canvas                        *
	 * 内容 : ペナルティ移行時のカードを描画します *
	 ***********************************************/
	public void penaltyDraw(Canvas canvas) {
		// カードの枚数分行う
		for(Cald caldNo : cald) {
			// カードのフラグが生きてるか
			if(caldNo.use) {
				// 手札の描画を行う
				canvas.drawBitmap(caldNo.back, caldNo.caldX, caldNo.caldY,paint);
			}
		}
	}
	
	/*************************************************
	 * penaltyTouchメソッド                          *
	 * 引 数  : MotionEvent event                    *
	 * 内 容  : タッチしたカードのフラグをオフにして *
	 *          次回以降使えないようにします         *
	 * 戻り値 : 処理が終わったらtrue                 *
	 *************************************************/
	public boolean penaltyTouch(MotionEvent event) {
		// カードの枚数分判定を行う
		for (int i = 0; i < cald.length; i++) {
			// 当たり判定の位置を取得する
			Rect rect   = new Rect();
			rect.top    = (int) cald[i].caldY;
			rect.left   = (int) cald[i].caldX;
			rect.bottom = rect.top  + Texture.CALD.getHeight();
			rect.right  = rect.left + Texture.CALD.getWidth();

			// カードの当たり判定
			if (cald[i].flag && collision.touchCheck(event,rect, MotionEvent.ACTION_UP)) {
				cald[i].use = false; // カードのフラグをオフにして使えないようにする
				return true;
			}
		}
		
		return false;
	}
	
	/*********************************************
	 * numberメソッド                            *
	 * 内  容 : 現在置いた枚数を戻り値で返します *
	 * 戻り値 : count 置いたカードの枚数         *
	 *********************************************/
	public int number() {
		return count; // 置いた枚数を返します
	}
	
	/*************************************
	 * boardFlagメソッド                 *
	 * 内 容  : ボードのフラグを管理する *
	 * 戻り値 : ボードが既に薔薇に       *
	 * 			なっていたらtrueを返す   *
	 *************************************/
	public boolean boardFalg() {
		
		// ボードのフラグが既に立っていたら
		if(board) return true; // trueを返す
		else board = true; // フラグをオンにする
		
		return false;     // falseを返す
	}
	
	/*******************************************************
	 * deathFlagメソッド                                   *
	 * 内  容 : プレイヤーのカードがあるかの判定を行います *
	 * 		    無くなっていた場合はtrueを返します         *
	 * 戻り値 : カードが残っていたらfalse                  *
	 *          無くなっていた場合はtrue                   *
	 *******************************************************/
	public boolean deathFlag() {
		int count = 0;
		
		// カードの判定を確認します
		for(Cald caldNo : cald) {
			if(!caldNo.use) count++; // 無くなっていた場合カウントを増やす
		}
		
		// 全部無くなっていたらtrueを返す
		if(count == cald.length) {
			return true;
		}
		
		return false;
	}
}