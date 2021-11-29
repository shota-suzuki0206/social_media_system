package constants;

/**
 * 各出力メッセージを定義するEnumクラス
 *
 */
public enum MessageConst {


  //認証
    I_LOGINED("ログインしました"),
    E_LOGINED("ログインに失敗しました。"),
    I_LOGOUT("ログアウトしました。"),

    //DB更新
    I_MEMBER_REGISTERED("会員登録が完了しました。"),
    I_REGISTERED("登録が完了しました。"),
    I_UPDATED("更新が完了しました。"),
    I_DELETED("削除が完了しました。"),
    I_COM_REGISTERED("コメントを投稿しました。"),
    I_FAV_REGISTERED("いいねしました。"),
    I_FAV_DELETED("いいねを解除しました。"),

    //バリデーション
    E_NONAME("名前を入力してください"),
    E_NOEMAIL("メールアドレスを入力してください"),
    E_NOPASSWORD("パスワードを入力してください"),
    E_USE_EMAIL_EXIST("入力されたメールアドレスは既に登録されています"),

    E_NOTITLE("タイトルを入力してください"),
    E_NOCONTENT("内容を入力してください"),
    E_NOCOMMENT("コメント内容を入力してください");

    /**
     * 文字列を保持するフィールド
     */
    private final String text;

    /**
     * コンストラクタ
     */
    private MessageConst(final String text) {
        this.text = text;
    }

    /**
     * 値の取得
     */
    public String getMessage() {
        return this.text;
    }

}
