package actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.ReportView;
import actions.views.UserView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
import constants.MessageConst;
import constants.PropertyConst;
import services.FavoriteService;
import services.FollowService;
import services.ReportService;
import services.UserService;

/**
 * ユーザの関わる処理を行うActionクラス
 *
 */
public class UserAction extends ActionBase {

    private UserService service;
    private ReportService repService;
    private FavoriteService favService;
    private FollowService flwService;

    /**
     * メソッドを実行する
     */
    @Override
    public void process() throws ServletException, IOException {

        service = new UserService();
        repService = new ReportService();
        favService = new FavoriteService();
        flwService = new FollowService();

        //メソッドを実行
        invoke();

        flwService.close();
        favService.close();
        repService.close();
        service.close();
    }

    /**
     * 一覧画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void index() throws ServletException, IOException {

        //指定されたページ数の一覧画面に表示するデータを取得
        int page = getPage();
        List<UserView> users = service.getPerpage(page);

        //全てのユーザーデータの件数を取得
        long userCount = service.countAll();

        putRequestScope(AttributeConst.USERS, users); //取得したユーザーデータ
        putRequestScope(AttributeConst.USE_COUNT, userCount); //すべてのユーザーデータの件数
        putRequestScope(AttributeConst.PAGE, page); //ページ数
        putRequestScope(AttributeConst.MAX_ROW, JpaConst.ROW_PER_PAGE); //一ページに表示するレコードの数

        //セッションにフラッシュメッセージが設定されている場合はリクエストスコープに移し替え、セッションからは削除する
        String flush = getSessionScope(AttributeConst.FLUSH);
        if (flush != null) {
            putRequestScope(AttributeConst.FLUSH, flush);
            removeSessionScope(AttributeConst.FLUSH);
        }

        //一覧画面を表示
        forward(ForwardConst.FW_USE_INDEX);
    }

    /**
     * 新規会員登録画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void entryNew() throws ServletException, IOException {

        putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
        putRequestScope(AttributeConst.USER, new UserView()); //空の従業員インスタンス

        //新規会員登録画面を表示
        forward(ForwardConst.FW_USE_NEW);
    }

    /**
     * 新規会員登録を行う
     * @throws ServletException
     * @throws IOException
     */
    public void create() throws ServletException, IOException {

        //CSRF対策 tokenのチェック
        if (checkToken()) {

            //パラメータの値を元に従業員情報のインスタンスを作成する
            UserView uv = new UserView(
                    null,
                    getRequestParam(AttributeConst.USE_NAME),
                    getRequestParam(AttributeConst.USE_EMAIL),
                    getRequestParam(AttributeConst.USE_PASS),
                    null,
                    null,
                    AttributeConst.DEL_FLAG_FALSE.getIntegerValue());

            //パスワードの一致チェック

            if (!getRequestParam(AttributeConst.USE_PASS).equals(getRequestParam(AttributeConst.USE_REPASS))) {

                String errors = "パスワードとパスワード（確認用）が不一致です。";

                putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
                putRequestScope(AttributeConst.USER, uv); //入力されたユーザー情報
                putRequestScope(AttributeConst.ERR, errors); //エラーのリスト

                //新規会員登録画面を再表示
                forward(ForwardConst.FW_USE_NEW);
            } else {

                //アプリケーションスコープからpepper文字列を取得
                String pepper = getContextScope(PropertyConst.PEPPER);

                //ユーザー情報登録
                List<String> errors = service.create(uv, pepper);

                if (errors.size() > 0) {
                    //登録中にエラーがあった場合

                    putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
                    putRequestScope(AttributeConst.USER, uv); //入力されたユーザー情報
                    putRequestScope(AttributeConst.ERR, errors); //エラーのリスト

                    //新規会員登録画面を再表示
                    forward(ForwardConst.FW_USE_NEW);

                } else {
                    //登録中にエラーがなかった場合

                    String email = getRequestParam(AttributeConst.USE_EMAIL);
                    String plainPass = getRequestParam(AttributeConst.USE_PASS);
                    String newPepper = getContextScope(PropertyConst.PEPPER);

                    //登録した従業員のDBデータを取得
                    UserView nuv = service.findOne(email, plainPass, newPepper);

                    //セッションに登録完了のフラッシュメッセージを設定
                    putSessionScope(AttributeConst.FLUSH, MessageConst.I_MEMBER_REGISTERED.getMessage());
                    //セッションにログインした従業員を設定
                    putSessionScope(AttributeConst.LOGIN_USE, nuv);

                    //トップページにリダイレクト
                    redirect(ForwardConst.ACT_TOP, ForwardConst.CMD_INDEX);
                }
            }

        }
    }

    /**
     *詳細画面を表示する
     */
    public void show() throws ServletException, IOException {

        //idを条件にユーザーデータを取得する
        UserView uv = service.findOne(toNumber(getRequestParam(AttributeConst.USE_ID)));

        if (uv == null || uv.getDeleteFlag() == AttributeConst.DEL_FLAG_TRUE.getIntegerValue()) {

            //データが取得できなかった、または論理削除されている場合はエラー画面を表示
            forward(ForwardConst.FW_ERR_UNKNOWN);
            return;
        }

        //対象のユーザーが作成した投稿データを、指定されたページ数の一覧画面に表示する分取得する
        int page = getPage();
        List<ReportView> reports = repService.getMinePerPage(uv, page);

        //対象のユーザーが作成した投稿データの件数を取得する。
        long myReportsCount = repService.countAllMine(uv);

        //いいねした投稿の件数をユーザーIDを使って取得する
        long favoritesCount = favService.countAllMine(uv);

        //セッションからログイン中のユーザー情報を取得
        UserView loginUser = (UserView) getSessionScope(AttributeConst.LOGIN_USE);

        //ログインしているユーザーが詳細画面を開いたユーザーをフォローしているか判定
        Boolean follow_flag = flwService.getFollowFlag(uv, loginUser);

        //対象のユーザーがフォローした人数を取得
        long followsCount = flwService.countAllMine(uv);

        //対象のユーザーのフォロワー人数を取得
        long followersCount = flwService.countFollowerMine(uv);

        //セッションにフラッシュメッセージが設定されている場合はリクエストスコープに移し替え、セッションからは削除する
        String flush = getSessionScope(AttributeConst.FLUSH);
        if (flush != null) {
            putRequestScope(AttributeConst.FLUSH, flush);
            removeSessionScope(AttributeConst.FLUSH);
        }

        putSessionScope(AttributeConst.USE_ID, uv.getId()); //対象のユーザーのidをセッションスコープに登録する
        putRequestScope(AttributeConst.FLW_FLAG, follow_flag);// すでにフォローしているかのフラグ
        putRequestScope(AttributeConst.FLW_COUNT, followsCount); //ユーザーがフォローした人数
        putRequestScope(AttributeConst.FOLLOWERS_COUNT, followersCount); //ユーザーがフォローした人数
        putRequestScope(AttributeConst.FAV_COUNT, favoritesCount); //ユーザーがいいね！した投稿の数
        putRequestScope(AttributeConst.USER, uv); //取得したユーザーデータ
        putRequestScope(AttributeConst.LOGIN_USER, loginUser); //取得したユーザーデータ
        putRequestScope(AttributeConst.REPORTS, reports); //取得した投稿データ
        putRequestScope(AttributeConst.REP_COUNT, myReportsCount); //ユーザーが作成した投稿の数
        putRequestScope(AttributeConst.PAGE, page); //ページ数
        putRequestScope(AttributeConst.MAX_ROW, JpaConst.ROW_PER_PAGE); //1ページに表示するレコードの数
        putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン

        //一覧画面を表示
        forward(ForwardConst.FW_USE_SHOW);
    }
}
