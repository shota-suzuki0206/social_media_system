package constants;

/**
 * DB関連の項目値を定義するインターフェース
 * ※インターフェイスに定義した変数は public static final 修飾子がついているとみなされる
 */
public interface JpaConst {

  //persistence-unit名
    String PERSISTENCE_UNIT_NAME = "social_media_system";

    //データ取得件数の最大値
    int ROW_PER_PAGE = 15; //1ページに表示するレコードの数

    //ユーザーテーブル
    String TABLE_USE = "users"; //テーブル名

    //従業員テーブルカラム
    String USE_COL_ID = "id"; //id
    String USE_COL_NAME = "name"; //名前
    String USE_COL_EMAIL = "email"; //メールアドレス
    String USE_COL_PASS = "password"; //パスワード
    String USE_COL_CREATED_AT = "created_at"; //登録日時
    String USE_COL_UPDATED_AT = "updated_at"; //更新日時
    String USE_COL_DELETE_FLAG = "delete_flag"; //削除フラグ

    int USE_DEL_TRUE = 1; //削除フラグON(削除済み)
    int USE_DEL_FALSE = 0; //削除フラグOFF(現役)

    //投稿記事テーブル
    String TABLE_REP = "reports";//テーブル名

    //投稿記事テーブルカラム
    String REP_COL_ID ="id";//id
    String REP_COL_USE ="user_id";//投稿したユーザーのid
    String REP_COL_TITLE = "title";//投稿記事のタイトル
    String REP_COL_CONTENT = "content";//投稿記事の内容
    String REP_COL_CREATED_AT = "created_at";//登録日時
    String REP_COL_UPDATED_AT = "updated_at";//更新日時

    //Entity名
    String ENTITY_USE = "user"; //ユーザー
    String ENTITY_REP = "report"; //投稿記事

    //JPQL内パラメータ
    String JPQL_PARM_EMAIL = "email"; //メールアドレス
    String JPQL_PARM_PASSWORD = "password"; //パスワード
    String JPQL_PARM_USER = "user"; //ユーザー


    //NamedQueryの nameとquery
    //全てのユーザーをidの降順に取得する
    String Q_USE_GET_ALL = ENTITY_USE + ".getAll"; //name
    String Q_USE_GET_ALL_DEF = "SELECT u FROM User AS u ORDER BY u.id"; //query
    //削除されていないユーザーの件数を取得する
    String Q_USE_COUNT = ENTITY_USE + ".count";
    String Q_USE_COUNT_DEF = "SELECT COUNT(u) FROM User AS u WHERE u.deleteFlag = 0";
    //emailとハッシュ化済パスワードを条件に未削除のユーザーを取得する
    String Q_USE_GET_BY_EMAIL_AND_PASS = ENTITY_USE + ".getByEmailAndPass";
    String Q_USE_GET_BY_EMAIL_AND_PASS_DEF = "SELECT u FROM User AS u WHERE u.deleteFlag = 0 AND u.email = :" + JPQL_PARM_EMAIL + " AND u.password = :" + JPQL_PARM_PASSWORD;
    //指定したemailを保持するユーザーの件数を取得する
    String Q_USE_COUNT_RESISTERED_BY_EMAIL = ENTITY_USE + ".countRegisteredByEmail";
    String Q_USE_COUNT_RESISTERED_BY_EMAIL_DEF = "SELECT COUNT(u) FROM User AS u WHERE u.email = :" + JPQL_PARM_EMAIL;

    //全ての投稿記事をidの降順に取得する
    String Q_REP_GET_ALL = ENTITY_REP + ".getAll";
    String Q_REP_GET_ALL_DEF = "SELECT r FROM Report AS r ORDER BY r.id DESC";
    //全ての投稿記事の件数を取得する
    String Q_REP_COUNT = ENTITY_REP + ".count";
    String Q_REP_COUNT_DEF = "SELECT COUNT(r) FROM Report AS r";
    //指定したユーザーが作成した投稿記事を全件idの降順で取得する
    String Q_REP_GET_ALL_MINE = ENTITY_REP + ".getAllMine";
    String Q_REP_GET_ALL_MINE_DEF = "SELECT r FROM Report AS r WHERE r.user = :" + JPQL_PARM_USER + " ORDER BY r.id DESC";
    //指定したユーザーが作成した投稿記事の件数を取得する
    String Q_REP_COUNT_ALL_MINE = ENTITY_REP + ".countAllMine";
    String Q_REP_COUNT_ALL_MINE_DEF = "SELECT COUNT(r) FROM Report AS r WHERE r.user = :" + JPQL_PARM_USER;



}
