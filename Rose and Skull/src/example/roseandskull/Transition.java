package example.roseandskull;

import android.graphics.Canvas;
import android.view.MotionEvent;
import example.rosesandskull.player.Actor;
import example.rosesandskull.player.Back;
import example.rosesandskull.player.Judge;
import example.rosesandskull.player.Player1;
import example.rosesandskull.player.Player2;
import example.rosesandskull.player.Player3;
import example.rosesandskull.player.Player4;
import example.rosesandskull.player.Player5;
import example.rosesandskull.player.Player6;
import example.rosesandskull.player.Rate;
import example.rosesandskull.player.Title;

/**********************
 * ゲームの状態遷移を * 管理するクラス *
 **********************/
public class Transition {

	// 初期化
	private Title title = new Title(); // タイトル
	private Back back = new Back(); // 背景
	private Rate rate = new Rate(); // レート
	private Judge judge = new Judge(); // 判定
	private Actor[] actor = new Actor[6]; // プレイヤー用クラス

	// 定数
	private final static int PLAYER1 = 0; // プレイヤー1
	private final static int PLAYER2 = 1; // プレイヤー2
	private final static int PLAYER3 = 2; // プレイヤー3
	private final static int PLAYER4 = 3; // プレイヤー4
	private final static int PLAYER5 = 4; // プレイヤー5
	private final static int PLAYER6 = 5; // プレイヤー6

	private final static int MAX_CALD = 4; // 1人の最大カード所持数

	// シーン遷移用列挙
	enum Turn {
		TITLE, // タイトル
		CLEAR, // 初期化
		PLAYER, // プレイヤー
		RATE, // レート
		JUDGE, // 判定
	}

	// 遷移用列挙
	private Turn turn;

	// 変数
	private int turnCount; // ターンカウント
	private int mainPlayer; // 表示するプレイヤーの番号
	private boolean buttonFlag; // ボタンを表示するフラグ
	private boolean turnFlag; // ターンが切り替わるフラグ

	/******************
	 * コンストラクタ *
	 ******************/
	public Transition() {
		// 遷移の初期化
		turn = Turn.TITLE;

		// プレイヤー基礎クラスの初期化
		for (int i = 0; i < actor.length; i++) {
			actor[i] = null;
		}

		// プレイヤークラスの格納
		actor[PLAYER1] = new Player1();
		actor[PLAYER2] = new Player2();
		actor[PLAYER3] = new Player3();
		actor[PLAYER4] = new Player4();
		actor[PLAYER5] = new Player5();
		actor[PLAYER6] = new Player6();

		// 変数の初期化
		init();
	}

	/*****************************
	 * initメソッド * 内容 : 変数の初期化を行う *
	 *****************************/
	public void init() {
		// 初期化
		turnCount = 0; // ターン数
		mainPlayer = PLAYER1; // 初期プレイヤー
		buttonFlag = false; // ボタン出現フラグ
		turnFlag = false; // ターン切り替えフラグ
	}

	/***********************************************
	 * upDateメソッド * 内容 : 操作するプレイヤーが変わった時に * ボードの位置を変更する処理 * * 戻り値 :
	 * 場のボードを移動させる処理を行う * turnFlagがtrueの時に処理を行い * 処理が終わったらfalseにし *
	 * 次のスレッドのタイミングで戻り値に * falseを渡す *
	 ***********************************************/
	public boolean upDate() {

		// プレイヤーが切り替わったなら
		if (turnFlag) {
			mainPlayer++; // 次のプレイヤーに移動

			// ボードの位置を更新させる
			for (int i = 0; i < actor.length; i++) {
				actor[i].upDate(); // 更新処理
			}

			// 一周したら
			if (mainPlayer > actor.length - 1) {
				turnCount++; // ターンカウントを増やす
				mainPlayer = PLAYER1; // プレイヤーを1週させる
				buttonFlag = true;

				// 全部置いたら
				if (turnCount == MAX_CALD) {
					turn = Turn.JUDGE; // レートに移行
					buttonFlag = false;
				}
			}

			// 処理が終わったらフラグをオンにしてtrueを返す
			turnFlag = false;
			return true;
		}

		// 次のスレッドのタイミングでfalseが呼ばれる
		return false;
	}

	/***********************
	 * drawメソッド * 引数 : Canvas * 内容 : 現在の遷移の * 描画を行う *
	 ***********************/
	public void draw(Canvas canvas) {

		// 背景の描画
		back.draw(canvas);
		if (buttonFlag)
			back.button(canvas);

		// ボードの描画
		for (int i = 0; i < actor.length; i++) {
			actor[i].backDraw(canvas);
		}

		// 現在の遷移を描画
		switch (turn) {

		// タイトル
		case TITLE:
			// タイトル描画
			title.draw(canvas);
			break;

		// プレイヤー
		case PLAYER:
			// プレイヤーの描画
			actor[mainPlayer].draw(canvas);
			break;

		case RATE:
			rate.draw(canvas);
			break;

		case JUDGE:
			judge.draw(canvas);
			break;

		// 初期化
		case CLEAR:
			break;
		}
	}

	/***************************
	 * soundメソッド * 内容 : 現在の遷移の音 * の再生処理を行う *
	 ***************************/
	public void sound() {

		// 現在の遷移の音を再生
		switch (turn) {
		// タイトル
		case TITLE:
			// タイトルのBGM
			title.sound();
			break;

		default:
			break;
		}
	}

	/***************************
	 * touchメソッド * 引数 : MotionEvent * 内容 : 現在の遷移の * タッチ処理を行う *
	 ***************************/
	public void touch(MotionEvent event) {

		// 現在の遷移の操作を行う
		switch (turn) {

		// タイトル処理
		case TITLE:
			// 入力処理が終わったら
			if (title.touch(event)) {

				// プレイヤーの初期化
				for (int i = 0; i < actor.length; i++) {
					actor[i].caldSet();
				}

				turn = Turn.PLAYER; // 遷移をプレイヤーに移動
			}
			break;

		// プレイヤー処理
		case PLAYER:
			// 入力処理が終わったら
			if (actor[mainPlayer].touch(event)) {
				turnFlag = true;
			}

			// 全員1枚出していてかつボタンが押されたら
			if (buttonFlag && back.buttonTouch(event)) {
				turn = Turn.JUDGE; // 判定に移行する
				buttonFlag = false;
			}

			break;

		case RATE:
			if (rate.touch(event)) {
				turn = Turn.JUDGE;
			}
			break;

		case JUDGE:

			for (int i = 0; i < actor.length; i++) {
				if (actor[i].boardTouch(event)) {
				}
			}

			break;

		// 初期化
		case CLEAR:
			// プレイヤーの初期化
			for (int i = 0; i < actor.length; i++) {
				actor[i].caldSet();
			}

			// 状態の初期化
			init();
			break;
		}
	}
}