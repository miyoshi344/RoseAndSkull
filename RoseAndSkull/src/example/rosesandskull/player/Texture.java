package example.rosesandskull.player;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import example.roseandskull.R;

/**********************
 * テクスチャを一括で * 
 * 管理するクラスです *
 **********************/
public class Texture {
	// 定数
	/* カード用定数 */
	private static final int SKULL   = 0; // 髑髏
	private static final int ROSES   = 1; // 薔薇
	private static final int REVERSE = 2; // 裏面
	
	/* プレイヤー用定数 */
	private static final int PLAYER1 = 0; // プレイヤー1
	private static final int PLAYER2 = 1; // プレイヤー2
	private static final int PLAYER3 = 2; // プレイヤー3
	private static final int PLAYER4 = 3; // プレイヤー4
	private static final int PLAYER5 = 4; // プレイヤー5
	private static final int PLAYER6 = 5; // プレイヤー6

	/******************************
	 * プレイヤーに関する画像情報 *
	 ******************************/
	// Bitmap
	/* タイトル */
	static Bitmap TITLE;       // タイトル
	static Bitmap START;       // スタートボタン
	static Bitmap RULE;        // ルールボタン
	
	static Bitmap RULE1;
	static Bitmap RULE2;
	static Bitmap RULE3;
	static Bitmap RULE4;
	static Bitmap RULE5;
	static Bitmap RULE6;
	static Bitmap RULE7;
	static Bitmap RULE8;
	static Bitmap ARROW;
	
	/* プレイ人数 */
	static Bitmap PLAYER_3;    // 3人ボタン
	static Bitmap PLAYER_4;    // 4人ボタン
	static Bitmap PLAYER_5;    // 5人ボタン
	static Bitmap PLAYER_6;    // 6人ボタン
	
	/* 背景 */
	static Bitmap BAR;
	static Bitmap YES;
	static Bitmap NO;
	static Bitmap BOARD;
	
	/* ボード */
	static Bitmap ROSESBOARD;  // 薔薇のボード
	static Bitmap SKULLBOARD;  // 髑髏のボード
	
	/* ボタン */
	static Bitmap ARROW_RIGHT; // 右矢印
	static Bitmap ARROW_LEFT;  // 左矢印
	static Bitmap BUTTON;      // ボタン
	
	/* プレイヤー */
	// プレイヤー用画像格納配列
	static Bitmap[][] PLAYER = new Bitmap[6][3];
	static Bitmap CALD;        // カード
	
	/*********************
	 * getContext        *
	 * MainからContextを *
	 * もらうメソッド    *
	 *********************/
	public void getContext(Context context) {
		Resources res = context.getResources(); // リソースの作成

		// 画像の取得
		/* ボタン */
		TITLE       = BitmapFactory.decodeResource(res, R.drawable.title);
		START       = BitmapFactory.decodeResource(res, R.drawable.start);
		RULE        = BitmapFactory.decodeResource(res, R.drawable.rule);
		
		/* YES NOボタン */
		YES         = BitmapFactory.decodeResource(res, R.drawable.yes);
		NO          = BitmapFactory.decodeResource(res, R.drawable.no);
		
		/* ルール画面 */
		RULE1       = BitmapFactory.decodeResource(res, R.drawable.rule1);
		RULE2       = BitmapFactory.decodeResource(res, R.drawable.rule2);
		RULE3       = BitmapFactory.decodeResource(res, R.drawable.rule3);
		RULE4       = BitmapFactory.decodeResource(res, R.drawable.rule4);
		RULE5       = BitmapFactory.decodeResource(res, R.drawable.rule5);
		RULE6       = BitmapFactory.decodeResource(res, R.drawable.rule6);
		RULE7       = BitmapFactory.decodeResource(res, R.drawable.rule7);
		RULE8       = BitmapFactory.decodeResource(res, R.drawable.rule8);
		
		/* プレイヤーボタン */
		PLAYER_3    = BitmapFactory.decodeResource(res, R.drawable.player3);
		PLAYER_4    = BitmapFactory.decodeResource(res, R.drawable.player4);
		PLAYER_5    = BitmapFactory.decodeResource(res, R.drawable.player5);
		PLAYER_6    = BitmapFactory.decodeResource(res, R.drawable.player6);
		
		/* ボード */
		ROSESBOARD  = BitmapFactory.decodeResource(res, R.drawable.rosesboard);
		SKULLBOARD  = BitmapFactory.decodeResource(res, R.drawable.skullboard);
		
		/* 背景 */
		BAR         = BitmapFactory.decodeResource(res, R.drawable.bar);
		BOARD       = BitmapFactory.decodeResource(res, R.drawable.playerback);
		
		/* チャレンジ用ボタン */
		ARROW_RIGHT = BitmapFactory.decodeResource(res, R.drawable.right);
		BUTTON      = BitmapFactory.decodeResource(res, R.drawable.exclamation);
		
		/* カード */
		CALD        = BitmapFactory.decodeResource(res, R.drawable.cald1);
		
		// プレイヤー1
		PLAYER[PLAYER1][SKULL]   = BitmapFactory.decodeResource(res, R.drawable.skull1);
		PLAYER[PLAYER1][ROSES]   = BitmapFactory.decodeResource(res, R.drawable.roses1);
		PLAYER[PLAYER1][REVERSE] = BitmapFactory.decodeResource(res, R.drawable.cald1);
		
		// プレイヤー2
		PLAYER[PLAYER2][SKULL]   = BitmapFactory.decodeResource(res, R.drawable.skull2);
		PLAYER[PLAYER2][ROSES]   = BitmapFactory.decodeResource(res, R.drawable.roses2);
		PLAYER[PLAYER2][REVERSE] = BitmapFactory.decodeResource(res, R.drawable.cald2);
		
		// プレイヤー3
		PLAYER[PLAYER3][SKULL]   = BitmapFactory.decodeResource(res, R.drawable.skull3);
		PLAYER[PLAYER3][ROSES]   = BitmapFactory.decodeResource(res, R.drawable.roses3);
		PLAYER[PLAYER3][REVERSE] = BitmapFactory.decodeResource(res, R.drawable.cald3);
		
		// プレイヤー4
		PLAYER[PLAYER4][SKULL]   = BitmapFactory.decodeResource(res, R.drawable.skull4);
		PLAYER[PLAYER4][ROSES]   = BitmapFactory.decodeResource(res, R.drawable.roses4);
		PLAYER[PLAYER4][REVERSE] = BitmapFactory.decodeResource(res, R.drawable.cald4);
		
		// プレイヤー5
		PLAYER[PLAYER5][SKULL]   = BitmapFactory.decodeResource(res, R.drawable.skull5);
		PLAYER[PLAYER5][ROSES]   = BitmapFactory.decodeResource(res, R.drawable.roses5);
		PLAYER[PLAYER5][REVERSE] = BitmapFactory.decodeResource(res, R.drawable.cald5);
		
		// プレイヤー6
		PLAYER[PLAYER6][SKULL]   = BitmapFactory.decodeResource(res, R.drawable.skull6);
		PLAYER[PLAYER6][ROSES]   = BitmapFactory.decodeResource(res, R.drawable.roses6);
		PLAYER[PLAYER6][REVERSE] = BitmapFactory.decodeResource(res, R.drawable.cald6);
	}

	/******************
	 * コンストラクタ *
	 ******************/
	public Texture() {
	}
}
