package example.roseandskull;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import example.rosesandskull.player.Sound;
import example.rosesandskull.player.Texture;

/*************************************
 * MainActivityクラス * 内容 : SurfaceViewを継承し * 自分用のActivityを形成する *
 *************************************/
public class MainActivity extends Activity {
	private Thread thread = null; // スレッド

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(new MySurfaceView(this));
	}

	class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback,
			Runnable {

		// スレッドの開始管理
		private boolean surfaceStandby = false;

		// 初期化
		private Transition transition = new Transition(); // ゲームのメインクラス
		private Texture texture = new Texture(); // テクスチャクラス
		private Sound sound = new Sound(); // BGM管理クラス

		/******************
		 * コンストラクタ *
		 * 
		 * @param context
		 *            *
		 ******************/
		public MySurfaceView(Context context) {
			super(context);
			getHolder().addCallback(this);

			// コンテキストをもらう
			texture.getContext(context);
			sound.getContext(context);
		}

		/*******************************
		 * 解像度情報変更通知 * holder サーフェイスホルダー * format フォーマット * width 横幅 * height 縦幅
		 * *
		 *******************************/
		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width,
				int height) {
		}

		/************************************
		 * サーフェイスが生成された時の処理 * holder サーフェイスフォルダー *
		 ************************************/
		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			thread = new Thread(this); // スレッド作成
			thread.start(); // スレッド開始

			surfaceStandby = true;
		}

		@Override
		public void onWindowFocusChanged(boolean hasFocus) {
			super.onWindowFocusChanged(hasFocus);
		}

		/****************
		 * メインループ *
		 ****************/
		@Override
		public void run() {
			while (thread != null) {

				// 音の再生
				transition.sound();
				// 描画処理
				Draw(getHolder());

				try {
					// upDateが呼び出されたら
					if (transition.upDate()) {
						Thread.sleep(360); // スレッドを止める
					}
				} catch (Exception e) {
				}
				; // 例外処理
			}
		}

		/************
		 * 描画処理 *
		 ************/
		public void Draw(SurfaceHolder holder) {

			// キャンパスロック
			Canvas canvas = holder.lockCanvas();

			if (!surfaceStandby)
				return;

			// キャンバスを白で塗り潰す
			canvas.drawColor(Color.WHITE);

			// 描画処理
			transition.draw(canvas);

			// キャンバスをアンロック
			holder.unlockCanvasAndPost(canvas);
		}

		/**************
		 * タッチ処理 *
		 **************/
		@Override
		public boolean onTouchEvent(MotionEvent event) {

			// タッチイベント処理
			transition.touch(event);
			return true;
		}

		/************************************
		 * サーフェイスが破棄された時の処理 * holder サーフェイスフォルダー *
		 ************************************/
		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {
			surfaceStandby = false;
			// sound.destroy(); // サウンドの破棄
			thread = null; // スレッド破棄
		}
	}
}
