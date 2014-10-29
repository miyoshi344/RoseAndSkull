package example.rosesandskull.player;

import android.content.Context;
import android.media.MediaPlayer;
import example.roseandskull.R;

/***********************
 * BGMを管理するクラス *
 ***********************/
public class Sound {

	// 定数
	/* BGM用 */
	static MediaPlayer TITLESOUND;
	static MediaPlayer GAMESOUND;
	static MediaPlayer RULESOUND;
	
	/* SE用 */
	static MediaPlayer BUTTON_SE;
	static MediaPlayer CALD_SE;
	
	/******************
	 * コンストラクタ *
	 ******************/
	public Sound() {
	}

	/*********************************
	 * getContextメソッド            *
	 * 引数 : Context context        *
	 * 内容 : Contextを引数でもらい  *
	 * 		  音楽用の定数を作成する *
	 *********************************/
	public void getContext(Context context) {
		/* BGM用 */
		TITLESOUND = MediaPlayer.create(context, R.raw.bgm_maoudamashii_neorock34);
		GAMESOUND  = MediaPlayer.create(context, R.raw.bgm_maoudamashii_neorock38);
		RULESOUND  = MediaPlayer.create(context, R.raw.bgm_maoudamashii_neorock40);
		
		/* SE用 */
		BUTTON_SE  = MediaPlayer.create(context, R.raw.on07);
		CALD_SE    = MediaPlayer.create(context, R.raw.clap00);
	}
	
	public void pause() {
		TITLESOUND.stop();
		GAMESOUND.stop();
		
		BUTTON_SE.stop();
		CALD_SE.stop();
	}

	/*********************************************
	 * destroyメソッド                           *
	 * 内容 : 所持していた音楽用定数を破棄します *
	 *********************************************/
	public void destroy() {
		TITLESOUND.release();
		GAMESOUND.release();
		
		BUTTON_SE.release();
		CALD_SE.release();
	}
}
