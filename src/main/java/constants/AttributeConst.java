package constants;


/**
 * 画面の項目値等を定義するEnumクラス
 *
 */
public enum AttributeConst {

    //フラッシュメッセージ
    FLUSH("flush"),

    //一覧画面共通
    MAX_ROW("maxRow"),
    PAGE("page"),

    //入力フォーム共通
    TOKEN("_token"),
    ERR("errors"),

    //ログイン中のユーザー
    LOGIN_USE("login_user"),

    //ログイン画面
    LOGIN_ERR("loginError"),

    //ユーザー管理
    USER("user"),
    USERS("users"),
    USE_COUNT("users_count"),
    USE_ID("id"),
    USE_NAME("name"),
    USE_EMAIL("email"),
    USE_PASS("password"),
    USE_REPASS("password_confirmation"),

    //削除フラグ
    DEL_FLAG_TRUE(1),
    DEL_FLAG_FALSE(0),

    //投稿内容管理
    REPORT("report"),
    REPORTS("reports"),
    REP_COUNT("reports_count"),
    REP_ID("id"),
    REP_TITLE("title"),
    REP_CONTENT("content"),

    //コメント管理
    COMMENT("comment"),
    COM_CONTENT("content");

    private final String text;
    private final Integer i;

    private AttributeConst(final String text) {
        this.text = text;
        this.i = null;
    }

    private AttributeConst(final Integer i) {
        this.text = null;
        this.i = i;
    }

    public String getValue() {
        return this.text;
    }

    public Integer getIntegerValue() {
        return this.i;
    }

}
