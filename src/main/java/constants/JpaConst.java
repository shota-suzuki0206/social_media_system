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

    //ユーザーテーブルカラム
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
    String REP_COL_ID = "id";//id
    String REP_COL_USE = "user_id";//投稿したユーザーのid
    String REP_COL_TITLE = "title";//投稿記事のタイトル
    String REP_COL_CONTENT = "content";//投稿記事の内容
    String REP_COL_CREATED_AT = "created_at";//登録日時
    String REP_COL_UPDATED_AT = "updated_at";//更新日時

    //コメントテーブル
    String TABLE_COM = "comments";//テーブル名

    //コメントテーブルカラム
    String COM_COL_ID = "id";//id
    String COM_COL_USE = "user_id";//コメントしたユーザーのid
    String COM_COL_REP = "report_id";//コメントした投稿id
    String COM_COL_CONTENT = "content";//コメントの内容
    String COM_COL_CREATED_AT = "created_at";//登録日時
    String COM_COL_UPDATED_AT = "updated_at";//更新日時

    //いいねテーブル
    String TABLE_FAV = "favorites";//テーブル名

    //いいねテーブルカラム
    String FAV_COL_ID = "id";//id
    String FAV_COL_USE = "user_id";//いいねしたユーザーのid
    String FAV_COL_REP = "report_id";//いいねした投稿id
    String FAV_COL_CREATED_AT = "created_at";//登録日時
    String FAV_COL_UPDATED_AT = "updated_at";//更新日時

    //フォローテーブル
    String TABLE_FLW = "follows";//テーブル名

    //フォローテーブルカラム
    String FLW_COL_ID = "id";//id
    String FLW_COL_FOLLOW = "follow_id";//フォローしたユーザーのid
    String FLW_COL_FOLLOWER = "follower_id";//フォローされたユーザーのid
    String FLW_COL_CREATED_AT = "created_at";//登録日時
    String FLW_COL_UPDATED_AT = "updated_at";//更新日時

    //Entity名
    String ENTITY_USE = "user"; //ユーザー
    String ENTITY_REP = "report"; //投稿記事
    String ENTITY_COM = "comment"; //コメント
    String ENTITY_FAV = "favorite"; //いいね！
    String ENTITY_FLW = "follow"; //フォロー

    //JPQL内パラメータ
    String JPQL_PARM_EMAIL = "email"; //メールアドレス
    String JPQL_PARM_PASSWORD = "password"; //パスワード
    String JPQL_PARM_USER = "user"; //ユーザー
    String JPQL_PARM_REPORT = "report"; //レポート
    String JPQL_PARM_FOLLOW = "follow"; //レポート
    String JPQL_PARM_FOLLOWER = "follower"; //レポート

    //NamedQueryの nameとquery
    //全ての削除されていないユーザーをidの昇順に取得する
    String Q_USE_GET_ALL = ENTITY_USE + ".getAll"; //name
    String Q_USE_GET_ALL_DEF = "SELECT u FROM User AS u WHERE u.deleteFlag = 0 ORDER BY u.id"; //query
    //削除されていないユーザーの件数を取得する
    String Q_USE_COUNT = ENTITY_USE + ".count";
    String Q_USE_COUNT_DEF = "SELECT COUNT(u) FROM User AS u WHERE u.deleteFlag = 0";
    //emailとハッシュ化済パスワードを条件に未削除のユーザーを取得する
    String Q_USE_GET_BY_EMAIL_AND_PASS = ENTITY_USE + ".getByEmailAndPass";
    String Q_USE_GET_BY_EMAIL_AND_PASS_DEF = "SELECT u FROM User AS u WHERE u.deleteFlag = 0 AND u.email = :"
            + JPQL_PARM_EMAIL + " AND u.password = :" + JPQL_PARM_PASSWORD;
    //指定したemailを保持するユーザーの件数を取得する
    String Q_USE_COUNT_RESISTERED_BY_EMAIL = ENTITY_USE + ".countRegisteredByEmail";
    String Q_USE_COUNT_RESISTERED_BY_EMAIL_DEF = "SELECT COUNT(u) FROM User AS u WHERE u.email = :" + JPQL_PARM_EMAIL;

    //未削除のユーザーが作成した全ての投稿記事をidの降順に取得する
    String Q_REP_GET_ALL = ENTITY_REP + ".getAll";
    String Q_REP_GET_ALL_DEF = "SELECT r FROM Report AS r JOIN r.user u WHERE u.deleteFlag=0 ORDER BY r.id DESC";
    //未削除のユーザーが作成した全ての投稿記事の件数を取得する
    String Q_REP_COUNT = ENTITY_REP + ".count";
    String Q_REP_COUNT_DEF = "SELECT COUNT(r) FROM Report AS r JOIN r.user u WHERE u.deleteFlag=0";
    //指定したユーザーが作成した投稿記事を全件idの降順で取得する
    String Q_REP_GET_ALL_MINE = ENTITY_REP + ".getAllMine";
    String Q_REP_GET_ALL_MINE_DEF = "SELECT r FROM Report AS r WHERE r.user = :" + JPQL_PARM_USER
            + " ORDER BY r.id DESC";
    //指定したユーザーが作成した投稿記事の件数を取得する
    String Q_REP_COUNT_ALL_MINE = ENTITY_REP + ".countAllMine";
    String Q_REP_COUNT_ALL_MINE_DEF = "SELECT COUNT(r) FROM Report AS r WHERE r.user = :" + JPQL_PARM_USER;

    //指定した投稿への未削除ユーザーのコメントを全件idの昇順で取得する
    String Q_COM_GET_ALL_MINE = ENTITY_COM + ".getAllMine";
    String Q_COM_GET_ALL_MINE_DEF = "SELECT c FROM Comment AS c JOIN c.user u WHERE u.deleteFlag=0 AND c.report = :" + JPQL_PARM_REPORT+ " ORDER BY c.id";
    //指定した投稿への未削除ユーザーのコメントの件数を取得する
    String Q_COM_COUNT_ALL_MINE = ENTITY_COM + ".countAllMine";
    String Q_COM_COUNT_ALL_MINE_DEF = "SELECT COUNT(c) FROM Comment AS c JOIN c.user u WHERE u.deleteFlag=0 AND c.report = :" + JPQL_PARM_REPORT;

    //指定したユーザーがいいね！した未削除ユーザーの投稿記事を全件idの降順で取得する
    String Q_FAV_GET_ALL_MINE = ENTITY_FAV + ".getAllMine";
    String Q_FAV_GET_ALL_MINE_DEF = "SELECT f FROM Favorite AS f JOIN f.user u WHERE u.deleteFlag=0 AND f.user = :" + JPQL_PARM_USER +" ORDER BY f.id DESC";
    //指定したユーザーがいいね！した未削除ユーザーの投稿の件数を取得する
    String Q_FAV_COUNT_ALL_MINE = ENTITY_FAV + ".countAllMine";
    String Q_FAV_COUNT_ALL_MINE_DEF = "SELECT COUNT(f) FROM Favorite AS f JOIN f.user u WHERE u.deleteFlag=0 AND f.user = :" + JPQL_PARM_USER;
    //指定した投稿にいいねした未削除ユーザーの一覧を取得する
    String Q_FAV_GET_BY_REP_ID = ENTITY_FAV + ".getByReportId";
    String Q_FAV_GET_BY_REP_ID_DEF = "SELECT f FROM Favorite AS f JOIN f.user u WHERE u.deleteFlag=0 AND f.report = :" + JPQL_PARM_REPORT;
    //指定した投稿への未削除ユーザーのいいねの件数を取得する
    String Q_FAV_COUNT = ENTITY_FAV + ".count";
    String Q_FAV_COUNT_DEF = "SELECT COUNT(f) FROM Favorite AS f JOIN f.user u WHERE u.deleteFlag=0 AND f.report = :" + JPQL_PARM_REPORT;
    //ユーザーIDとレポートIDを条件にいいねデータを取得する
    String Q_FAV_GET_BY_USEID_AND_REPID = ENTITY_FAV + ".getByUseIdAndRepId";
    String Q_FAV_GET_BY_USEID_AND_REPID_DEF = "SELECT f FROM Favorite AS f WHERE f.user = :"+ JPQL_PARM_USER + " AND f.report = :" + JPQL_PARM_REPORT;
    //ユーザーIDとレポートIDを条件に該当するいいねデータの件数を取得する
    String Q_FAV_COUNT_BY_USEID_AND_REPID = ENTITY_FAV + ".countByUseIdAndRepId";
    String Q_FAV_COUNT_BY_USEID_AND_REPID_DEF = "SELECT COUNT(f) FROM Favorite AS f WHERE f.user = :"+ JPQL_PARM_USER + " AND f.report = :" + JPQL_PARM_REPORT;

    //指定したユーザーがフォローした未削除のユーザーを全件idの降順で取得する
    String Q_FLW_GET_ALL_MINE = ENTITY_FLW + ".getAllMine";
    String Q_FLW_GET_ALL_MINE_DEF = "SELECT f FROM Follow AS f JOIN f.follower u WHERE u.deleteFlag=0 AND f.follow = :" + JPQL_PARM_FOLLOW + " ORDER BY f.id DESC";
    //指定したユーザーがフォローした未削除ユーザー人数を取得する
    String Q_FLW_COUNT_ALL_MINE = ENTITY_FLW + ".countAllMine";
    String Q_FLW_COUNT_ALL_MINE_DEF = "SELECT COUNT(f) FROM Follow AS f JOIN f.follower u WHERE u.deleteFlag=0 AND f.follow = :" + JPQL_PARM_FOLLOW;
    //フォローIDとフォロワーIDを条件に該当するフォローデータを取得する
    String Q_FLW_GET_BY_FOLLOW_AND_FOLLOWER = ENTITY_FLW + ".getByFollowAndFollower";
    String Q_FLW_GET_BY_FOLLOW_AND_FOLLOWER_DEF = "SELECT f FROM Follow AS f WHERE f.follow = :"+ JPQL_PARM_FOLLOW + " AND f.follower = :" + JPQL_PARM_FOLLOWER;
    //フォローIDとフォロワーIDを条件に該当するフォローデータの件数を取得する
    String Q_FLW_COUNT_BY_FOLLOW_AND_FOLLOWER = ENTITY_FLW + "countByFollowAndFollower";
    String Q_FLW_COUNT_BY_FOLLOW_AND_FOLLOWER_DEF = "SELECT COUNT(f) FROM Follow AS f WHERE f.follow = :"+ JPQL_PARM_FOLLOW + " AND f.follower = :" + JPQL_PARM_FOLLOWER;
    //指定したユーザーの未削除のフォロワーを全件idの降順で取得する
    String Q_FLW_GET_FLW_MINE = ENTITY_FLW + ".getFlwMine";
    String Q_FLW_GET_FLW_MINE_DEF = "SELECT f FROM Follow AS f JOIN f.follow u WHERE u.deleteFlag=0 AND f.follower = :" + JPQL_PARM_FOLLOWER + " ORDER BY f.id DESC";
    //指定したユーザーの未削除フォロワーの人数を取得する
    String Q_FLW_COUNT_FLW_MINE = ENTITY_FLW + ".countFlwMine";
    String Q_FLW_COUNT_FLW_MINE_DEF = "SELECT COUNT(f) FROM Follow AS f JOIN f.follow u WHERE u.deleteFlag=0 AND f.follower = :" + JPQL_PARM_FOLLOWER;

    //タイムラインに表示するデータを取得する
    String Q_FLW_GET_TIMELINE = ENTITY_FLW + ".getTimeLine";
    String Q_FLW_GET_TIMELINE_DEF = "SELECT r FROM Report AS r WHERE r.user = :"+ JPQL_PARM_USER +" OR r.user IN (SELECT fol FROM Follow AS f JOIN f.follower AS fol WHERE f.follow = :user AND fol.deleteFlag=0) ORDER BY r.id DESC";
    //タイムラインに表示するデータの件数を取得する
    String Q_FLW_COUNT_TIMELINE = ENTITY_FLW + ".countTimeLine";
    String Q_FLW_COUNT_TIMELINE_DEF = "SELECT COUNT(r) FROM Report AS r WHERE r.user = :"+ JPQL_PARM_USER +" OR r.user IN (SELECT fol FROM Follow AS f JOIN f.follower AS fol WHERE f.follow = :user AND fol.deleteFlag=0)";

}
