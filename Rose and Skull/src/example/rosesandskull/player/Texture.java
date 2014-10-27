package example.rosesandskull.player;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import example.roseandskull.R;

/**********************
 * テクスチャを一括で * 管理するクラスです *
 **********************/
public class Texture {

	/******************************
	 * プレイヤーに関する画像情報 *
	 ******************************/
	// Bitmap
	static Bitmap TITLE; // タイトル
	static Bitmap ROSESBOARD; // 薔薇のボード
	static Bitmap SKULLBOARD; // 髑髏のボード
	static Bitmap PLAYERBACK; // プレイヤー用背景
	static Bitmap BOARDBACK; // 背景
	static Bitmap SCORE; // スコア

	static Bitmap ROSES1; // プレイヤー1薔薇
	static Bitmap SKULL1; // プレイヤー1髑髏
	static Bitmap CALD1; // プレイヤー1裏面

	static Bitmap ROSES2; // プレイヤー2薔薇
	static Bitmap SKULL2; // プレイヤー2髑髏
	static Bitmap CALD2; // プレイヤー2裏面

	static Bitmap ROSES3; // プレイヤー3薔薇
	static Bitmap SKULL3; // プレイヤー3髑髏
	static Bitmap CALD3; // プレイヤー3裏面

	static Bitmap ROSES4; // プレイヤー4薔薇
	static Bitmap SKULL4; // プレイヤー4髑髏
	static Bitmap CALD4; // プレイヤー4裏面

	static Bitmap ROSES5; // プレイヤー5薔薇
	static Bitmap SKULL5; // プレイヤー5髑髏
	static Bitmap CALD5; // プレイヤー5裏面

	static Bitmap ROSES6; // プレイヤー6薔薇
	static Bitmap SKULL6; // プレイヤー6髑髏
	static Bitmap CALD6; // プレイヤー6裏面

	/*********************
	 * getContext * MainからContextを * もらうメソッド *
	 *********************/
	public void getContext(Context context) {
		Resources res = context.getResources(); // リソースの作成

		// 画像の取得
		TITLE      = BitmapFactory.decodeResource(res, R.drawable.image);
		PLAYERBACK = BitmapFactory.decodeResource(res, R.drawable.playerback);
		ROSESBOARD = BitmapFactory.decodeResource(res, R.drawable.rosesboard);
		SKULLBOARD = BitmapFactory.decodeResource(res, R.drawable.skullboard);
		BOARDBACK  = BitmapFactory.decodeResource(res, R.drawable.background);

		ROSES1 = BitmapFactory.decodeResource(res, R.drawable.roses1);
		SKULL1 = BitmapFactory.decodeResource(res, R.drawable.skull1);
		CALD1  = BitmapFactory.decodeResource(res, R.drawable.cald1);

		ROSES2 = BitmapFactory.decodeResource(res, R.drawable.roses2);
		SKULL2 = BitmapFactory.decodeResource(res, R.drawable.skull2);
		CALD2 = BitmapFactory.decodeResource(res, R.drawable.cald2);

		ROSES3 = BitmapFactory.decodeResource(res, R.drawable.roses3);
		SKULL3 = BitmapFactory.decodeResource(res, R.drawable.skull3);
		CALD3 = BitmapFactory.decodeResource(res, R.drawable.cald3);

		ROSES4 = BitmapFactory.decodeResource(res, R.drawable.roses4);
		SKULL4 = BitmapFactory.decodeResource(res, R.drawable.skull4);
		CALD4 = BitmapFactory.decodeResource(res, R.drawable.cald4);

		ROSES5 = BitmapFactory.decodeResource(res, R.drawable.roses5);
		SKULL5 = BitmapFactory.decodeResource(res, R.drawable.skull5);
		CALD5 = BitmapFactory.decodeResource(res, R.drawable.cald5);

		ROSES6 = BitmapFactory.decodeResource(res, R.drawable.roses6);
		SKULL6 = BitmapFactory.decodeResource(res, R.drawable.skull6);
		CALD6 = BitmapFactory.decodeResource(res, R.drawable.cald6);
	}

	/******************
	 * コンストラクタ *
	 ******************/
	public Texture() {
	}
}
