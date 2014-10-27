package example.rosesandskull.player;

import android.content.Context;
import android.media.MediaPlayer;
import example.roseandskull.R;

/***********************
 * BGMを管理するクラス *
 ***********************/
public class Sound {

	static MediaPlayer TITLESOUND;

	enum SoundList {
		Title,
	}

	public void getContext(Context context) {

		TITLESOUND = MediaPlayer.create(context, R.raw.title);
	}

	public void destroy(SoundList list) {
		TITLESOUND.release();
	}
}
