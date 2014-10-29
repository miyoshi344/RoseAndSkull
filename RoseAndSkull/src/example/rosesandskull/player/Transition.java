package example.rosesandskull.player;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

/**********************
 * ゲームの状態遷移を *
 * 管理するクラス     *
 **********************/
public class Transition {
	// 初期化
	private Paint   paint  = new Paint();
	private Title   title  = new Title();  // タイトル
	private Rule    rule   = new Rule();   // ルール
	private Button  button = new Button(); // ボタン
	private Rate    rate   = new Rate();   // レート
	private Judge   judge  = new Judge();  // 判定
	private Actor[] actor  = new Actor[6]; // プレイヤー用クラス
	private String  action = new String();

	// 定数
	private final static int YES = 1;     // YESボタン判定用
	private final static int NO  = 2;     // NOボタン判定用

	// シーン遷移用列挙
	enum Turn {
		TITLE,      // タイトル画面
		RULE,       // ルール画面
		WAIT,       // 待機画面
		PLAYER,     // プレイヤー
		CHALLENGE,  // チャレンジ
		RATE,       // レート
		SELF_JUDGE, // 宣言した人のボード判定
		OTHER_JUDGE,// 他の人のボード判定 
		WINNING,    // 成功時
		PENALTY,    // 失敗時
		RESULT,     // 結果
		RESET,      // 盤の初期化
	}

	// 遷移用列挙
	private Turn turn;

	// 変数
	private int     maxPlayer;       // 選択されたプレイヤー人数
	private int     turnCount;       // ターンカウント
	private int     synthesisTurn;   // ターン周期カウント
	private int     mainPlayer;      // 表示するプレイヤーの番号
	private int     skullPlayer;     // 髑髏を引かせたプレイヤーを保存します
	private int     challengePlayer; // チャレンジするプレイヤー
	private int 	 challengeCount;  // レート判定用フラグ
	
	private boolean titleFlag;      // タイトル画面の管理用
	private boolean waitFlag;       // 待機が終了したかのフラグ管理用
	
	/******************
	 * コンストラクタ *
	 ******************/
	public Transition() {
		// 初期化
		for(int i = 0; i < actor.length; i++) {
			actor[i] = new Player(i); // actorのコンストラクタを呼び出す
		}
		
		// 文字の設定を行います
		paint.setTextSize(50);
		paint.setColor(Color.WHITE);
		
		// 状態の初期化
		maxPlayer   = 0;
		titleFlag   = false;
		waitFlag    = false;
		action      = null;
		
		// 遷移の初期化
		turn = Turn.TITLE;
	}

	/***************************
	 * initメソッド            *
	 * 内容 : 初期の設定を行う *
	 ***************************/
	public void init() {
		// 配列に継承したプレイヤークラスに設定する
		for(int i = 0; i < maxPlayer; i++) {
			actor[i].caldSet(i);
		}
		
		// 状態の初期化
		reset();
	}
	
	/*********************************
	 * resetメソッド                 *
	 * 内容 : 状態の初期化を行います *
	 *********************************/
	public void reset() {
		for(int i = 0; i < maxPlayer; i++) {
			actor[i].boardSet(actor.length - i); // actor内のボードセット
			actor[i].reset(); // actorの状態リセット
		}
		
		// 最後のプレイヤーが手前に来るように調整する
		for(int i = 0; i < mainPlayer; i++) {
			for(Actor actorNo : actor) actorNo.upDate();
		}
		
		// 状態のリセット
		rate.reset();   // rateの初期化
		judge.reset();  // judgeの初期化 
		
		// 変数初期化
		skullPlayer    = 0;  // 髑髏を引いたプレイヤー
		challengeCount = 0;  // チャレンジ宣言してから1週したかの確認用
		synthesisTurn  = 0;  // 1週したかの確認
		turnCount      = 0;  // ターン数
	}
	
	/*********************************
	 * boardResetメソッド            *
	 * 内容 : ボードをリセットします *
	 *********************************/
	public void boardReset() {
		for(int i = 0; i < maxPlayer; i++) {
			actor[i].boardSet(actor.length - i); // actor内のボードセット
		}
	}
	
	/*************************************************
	 * totalCaldメソッド                             *
	 * 内容 : 場に出ているカードの合計値を計算します *
	 * 戻り値 : 合計した値を返します                 *
	 *************************************************/
	public int totalCald() {
		int total = 0;
		// 場のカードの合計を計算して返します
		for(int i = 0; i < maxPlayer; i++) total += actor[i].number();
		
		// 合計値を返す
		return total;
	}

	/***********************************************
	 * upDateメソッド                              *
	 * 内容 : 操作するプレイヤーが変わった時に     *
	 * ボードの位置を変更する処理                  *
	 * 戻り値 : 場のボードを移動させる処理を行う   *
	 * 			turnFlagがtrueの時に処理を行い     *
	 * 			処理が終わったらfalseにし          *
	 * 			次のスレッドのタイミングで戻り値に *
	 * 			falseを渡す                        *
	 ***********************************************/
	public void upDate() {
		
		// 1週回ったら
		if(++synthesisTurn == maxPlayer) {
			turnCount++;       // ターンカウントを1増やす
			synthesisTurn = 0; // ターン周期を0に戻す
		}
		
		// 次のプレイヤーに移動
		if(++mainPlayer == maxPlayer) {
			mainPlayer = 0; // プレイヤーが最大値になったら初期化する
			boardReset();
		}else {
			// ボードの位置を更新させる
			for (int i = 0; i < maxPlayer; i++ ) actor[i].upDate(); // 更新処理
		}
	}

	/***********************
	 * drawメソッド        *
	 * 引数 : Canvas       *
	 * 内容 : 現在の遷移の *
	 * 		  描画を行う   *
	 ***********************/
	public void draw(Canvas canvas) {
		// 背景の描画
		canvas.drawBitmap(Texture.BAR, 0, 0, paint);
		
		// 現在のプレイヤーと状態を表示
		canvas.drawText("プレイヤー" + (mainPlayer + 1) + action, 0, 80, paint);

		// 現在の遷移を描画
		switch (turn) {

		// タイトル
		case TITLE:
			// タイトル画面のサウンドの設定
			Sound.TITLESOUND.setLooping(true); // ループ設定にする
			Sound.TITLESOUND.start();          // サウンドの再生
			
			// タイトル描画
			title.backDraw(canvas);
			if(!titleFlag) title.gameButton(canvas); // スタートとルールのボタンを表示する
			else title.playerButton(canvas);         // プレイ人数のボタンを表示する
			break;
		
		// ルール
		case RULE:
			// ルール画面のサウンドの設定
			Sound.RULESOUND.setLooping(true);  // ループ設定にする
			Sound.RULESOUND.start();           // サウンドの再生
			rule.draw(canvas);                 // ルール画面の描画
			break;
			
		// 待機画面
		case WAIT:
			// 現在の状態
			action = "待機画面";
			
			Paint playerPaint = new Paint();
			playerPaint.setTextSize(10);
			// ボードの描画
			for(int i = 0; i < maxPlayer; i++) {
				// ボードの描画
				actor[i].backDraw(canvas);
				
				canvas.drawText("プレイヤーの残りカード枚数", 50, 1350, paint);
				canvas.drawText("プレイヤー" + (i + 1) + "　" + actor[i].playerCaldRemaining() + "枚", actor[i].textX, actor[i].textY, actor[i].playerPaint);
			}
			
			// ボタンフラグが立っていればボタンを表示する
			if(waitFlag) button.waitDraw(canvas, mainPlayer + 1); // フラグが立っていない状態
			if(!waitFlag && turnCount != 0) button.draw(canvas);  // フラグが立っている状態

			break;

		// プレイヤー
		case PLAYER:
			// 現在の状態
			action = "操作画面";
			
			// ゲーム中のサウンドの設定
			Sound.GAMESOUND.setLooping(true);  // ループ設定にする
			Sound.GAMESOUND.start();           // サウンドの再生
			
			// ボードの描画
			for(int i = 0; i < maxPlayer; i++) {
				// ボードの描画
				actor[i].backDraw(canvas);
			}
			
			// カードがなくなったら判定に移行する
			if(actor[mainPlayer].caldFlag()) {
				rate.totalCald(totalCald());   // カードの合計値を渡す
				challengePlayer = mainPlayer;  // チャレンジ宣言したプレイヤーを格納
				turn = Turn.RATE;              // レートに移行
			}
			
			// 現在のプレイヤーの名前を表示する
			actor[mainPlayer].playerName(canvas);
			
			// プレイヤーの手札が全部除外されているか確認
			// 手札がまだ残っているなら
			if(!actor[mainPlayer].deathFlag()) {
				// プレイヤーの描画
				actor[mainPlayer].draw(canvas);
			}
			// カードが全部除外されていたら
			else {
				// その人をとばして次に移行
				upDate();          // 場の更新
				turn = Turn.WAIT;  // 待機状態に移行
			}
			break;
			
		// レート
		case RATE:
			// 現在の状態
			action = "チャレンジ";
			
			// ボードの描画
			for(int i = 0; i < maxPlayer; i++) {
				// ボードの描画
				actor[i].backDraw(canvas);
			}
			
			// 現在のプレイヤーと状況を描画する
			rate.countDraw(canvas);                   // 現在のレートを表示します
			rate.draw(canvas);                        // Rateの描画処理を行います
			rate.statusDraw(canvas, challengePlayer); // 現在の場のカード合計値と宣言したプレイヤーを宣言します               
			
			break;
		
		// チャレンジ画面
		case CHALLENGE:
			// ボードの描画
			for(int i = 0; i < maxPlayer; i++) {
				// ボードの描画
				actor[i].backDraw(canvas);
			}
			
			// ボタンの描画
			button.draw(canvas);
			
			// 現在の状況を描画
			rate.statusDraw(canvas, challengePlayer);  // 宣言したプレイヤーと場の枚数を表示
			rate.countDraw(canvas);                    // 自分が宣言しようとしてるレートを表示
			
			break;
		
		// 自分の場の判定
		case SELF_JUDGE:
			actor[mainPlayer].judgeDraw(canvas);  // 自分の場を表示する
			break;
			
		// 判定
		case OTHER_JUDGE:
			// 判定の描画
			judge.draw(canvas);
			for(int i = 0; i < maxPlayer; i++) {
				// 判定画面の描画
				actor[i].judgeDraw(canvas);
			}
			
			break;
		
		// 宣言が成功した時の処理
		case WINNING:
			// 現在の状態
			action = "チャレンジ成功";
			
			// 成功画面の描画
			judge.clear(canvas,mainPlayer);
			break;
		
		// 宣言が失敗した時のペナルティ
		case PENALTY:
			// 現在の状態
			action = "チャレンジ失敗";
			
			// ペナルティの描画
			judge.defeat(canvas, skullPlayer);
			actor[mainPlayer].caldS();             // 失敗したプレイヤーのカードを表示して
			actor[mainPlayer].penaltyDraw(canvas); // 現在使えるカードを表示します
			break;
			
		// リザルト画面
		case RESULT:
			// 勝利画面を描画します
			judge.success(canvas, mainPlayer);
			break;
		
		// 盤面の初期化
		case RESET:
			// 初期化
			reset();
			turn = Turn.PLAYER;
			break;
		}
	}

	/***************************
	 * touchメソッド           *
	 * 引数 : MotionEvent      *
	 * 内容 : 現在の遷移の     *
	 * 		  タッチ処理を行う *
	 ***************************/
	public void touch(MotionEvent event) {
		// 現在の遷移の操作を行う
		switch (turn) {

		// タイトル処理
		case TITLE:
			// startボタンが押されたら
			if (title.touch(event) == YES) {
				titleFlag = true;      // 人数入力に移行
			}
			// ruleボタンが押されたら
			else if(title.touch(event) == NO) {
				Sound.TITLESOUND.release();
				turn = Turn.RULE;       // ルール画面に移行
			}
			
			// startボタンが押されていたら
			if(titleFlag) { 
				// ボタンが押されたらその人数を最大人数にする
				maxPlayer = title.playerSelect(event);
				// ボタンが入力されたら
				if(maxPlayer != 0) {
					init();                  // 場の初期化
					Sound.TITLESOUND.release(); // タイトルのサウンドを停止
					turn = Turn.PLAYER;      // プレイヤーに移行
				}
			}
			break;
		
		// ルール画面の処理を行います
		case RULE:
			if(rule.touch(event)) {
				Sound.RULESOUND.release();      // rule画面のサウンドを停止
				turn = Turn.TITLE;           // タイトル画面に戻る
			}
			break;
			
		// 待機処理
		case WAIT:
			// ボタンが押されるまで進まない
			if(button.touch(event)) waitFlag = false;  // 押されたら次へ進む
			if(waitFlag) break;  // 押されていない場合は抜ける
			
			// 全員1枚出していてかつボタンが押されたら
			if (turnCount != 0) {
				switch (button.buttonTouch(event)) {
				// YESが押されたら
				case YES:
					rate.totalCald(totalCald());
					turn = Turn.RATE;    // 判定に移行する
					break;
					
				// NOが押されたら
				case NO:
					turn = Turn.PLAYER;  // プレイヤーに移行する
					break;
				}
				break;
			}
			else turn = Turn.PLAYER;    // 1枚以上出していないならプレイヤーに移行する
			
		// プレイヤー処理
		case PLAYER:
			// フラグをオンに戻す
			waitFlag = true;
			
			// 入力処理が終わったら
			if (actor[mainPlayer].touch(event)) {
				Sound.CALD_SE.start();  // カードを置いたSEを再生する
				upDate();               // 場の更新を行う
  				turn = Turn.WAIT;       // 待機に移行する
			}
			break;
		
		// レート処理 
		case RATE:
				if(rate.touch(event)) {
					judge.setCount(rate.numberSheets());  // カウントした回数を格納する
					upDate();                             // 場の更新を行う
					turn = Turn.CHALLENGE;                // チャレンジに移行する
				}
				break;
				
		// チャレンジ選択処理
		case CHALLENGE:
			// レートを上げるか選択する
			switch (button.buttonTouch(event)) {
			// YESが押されたら
			case YES:
				challengeCount = 0;           // 周回をリセットする
				challengePlayer = mainPlayer; // 宣言したプレイヤーを宣言プレイヤーにする
				turn = Turn.RATE;             // レート画面に移行する
				break;
			// NOが押されたら
			case NO:
				upDate();                               // 場の更新を行う
				if(++challengeCount == maxPlayer -1) { 
					turn = Turn.SELF_JUDGE;             // 1週したら判定に移行する
				}
				break;
			}
			break;
		
		// 宣言プレイヤーの判定処理
		case SELF_JUDGE:
			// 宣言プレイヤーのカードを判定する
			if(actor[mainPlayer].caldOpen(event)) {
				// 判定を行い髑髏を引いた場合
				if(judge.touch(event, actor[mainPlayer].caldCheck())) {
					skullPlayer = mainPlayer + 1; // 髑髏を引いたプレイヤーを格納する
					turn = Turn.PENALTY;          // ペナルティに移行
				}
				
				// 自分のカードが無くなり、まだ枚数を引くなら
				if(actor[mainPlayer].number() == 0) turn = Turn.OTHER_JUDGE;  // 周りのプレイヤーの判定に移行する
				
				// 髑髏を引かずカウントが0になった場合
				if(judge.countUp()) {
					turn = Turn.WINNING;  // 勝利画面に移行
				}
			}
			break;
		
		// 他のプレイヤーの判定処理
		case OTHER_JUDGE:
			// プレイヤーの人数分行う
			for(int i = 0; i < maxPlayer; i++) {
				// カードに触れたら
				if(actor[i].caldOpen(event)) {
					// 判定を行い髑髏を引いた場合
					if(judge.touch(event, actor[i].caldCheck())) {
						skullPlayer = (i + 1);
						turn = Turn.PENALTY; // ペナルティに移行
					}
					// 髑髏を引かずカウントが0になった場合
					if(judge.countUp()) {
						turn = Turn.WINNING; // 勝利画面に移行
					}
				}
			}
			break;
		
		// 勝利画面
		case WINNING:
			// ボードのフラグをオンにします
			if(!actor[mainPlayer].boardFalg()) {
				turn = Turn.RESET;    // リセットに移行
			}
			else turn = Turn.RESULT;
			break;
			
		// ペナルティ処理
		case PENALTY:		
			// カードを選択し、そのカードを次回以降使えなくします
			if(actor[mainPlayer].penaltyTouch(event)) {
				reset();             // 場をリセットする
				turn = Turn.PLAYER;  // プレイヤーに移行
			}
			break;
		
		// リザルト処理
		case RESULT:
			break;
			
		// 初期化
		case RESET:
			break;
		}
	}
}