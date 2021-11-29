package actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.FavoriteView;
import actions.views.ReportView;
import actions.views.UserView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
import constants.MessageConst;
import services.FavoriteService;
import services.ReportService;

/**
 * いいね！に関する操作を行うActionクラス
 *
 */
public class FavoriteAction extends ActionBase{

    private FavoriteService service;

    /**
     * メソッドを実行する
     */
    @Override
    public void process() throws ServletException, IOException {

        service = new FavoriteService();

        //メソッドを実行
        invoke();

        service.close();
    }

    /**
     * いいね！した投稿の一覧を表示する
     */
    public void index() throws ServletException, IOException{

        //セッションからログイン中のユーザー情報を取得
        UserView loginUser = (UserView) getSessionScope(AttributeConst.LOGIN_USE);

        //ログイン中のユーザーがいいね！した投稿を、指定されたページ数の一覧画面に表示する分取得する
        int page = getPage();
        List<FavoriteView> favorites = service.getMinePerPage(loginUser, page);

        //ログイン中のユーザーがいいね！した投稿データの件数を取得
        long myFavoritesCount = service.countAllMine(loginUser);

        putRequestScope(AttributeConst.FAVORITES, favorites); //取得したいいねデータ
        putRequestScope(AttributeConst.FAV_COUNT, myFavoritesCount); //ログイン中のユーザーがいいね！した投稿の数
        putRequestScope(AttributeConst.PAGE, page); //ページ数
        putRequestScope(AttributeConst.MAX_ROW, JpaConst.ROW_PER_PAGE); //1ページに表示するレコードの数

        //セッションにフラッシュメッセージが設定されている場合はリクエストスコープに移し替え、セッションからは削除する
        String flush = getSessionScope(AttributeConst.FLUSH);
        if (flush != null) {
            putRequestScope(AttributeConst.FLUSH, flush);
            removeSessionScope(AttributeConst.FLUSH);
        }

        //一覧画面を表示
        forward(ForwardConst.FW_FAV_INDEX);
    }

    /**
     *いいねテーブルの新規登録を行う
     * @throws ServletException
     * @throws IOException
     */
    public void create() throws ServletException, IOException {

        //CSRF対策 tokenのチェック
        if (checkToken()) {

            //セッションからログイン中のユーザー情報を取得
            UserView uv = (UserView) getSessionScope(AttributeConst.LOGIN_USE);

            //ReportServiceインスタンスを生成
            ReportService rs = new ReportService();

            // idを条件に投稿データを取得する
            ReportView rv = rs.findOne(toNumber(getRequestParam(AttributeConst.REP_ID)));

            //パラメータの値をもとにいいね情報のインスタンスを作成する
            FavoriteView fv = new FavoriteView(
                    null,
                    uv, //ログインしているユーザーをいいねしたユーザーとして登録する
                    rv, //いいねされた投稿を登録する
                    null,
                    null);

            //いいね情報登録
            service.create(fv);

            //セッションに登録完了のフラッシュメッセージを設定
            putSessionScope(AttributeConst.FLUSH, MessageConst.I_FAV_REGISTERED.getMessage());

            //投稿詳細ページにリダイレクト
            int id = toNumber(getRequestParam(AttributeConst.REP_ID));
            redirect(ForwardConst.ACT_REP, ForwardConst.CMD_SHOW, id);
        }
    }

    /**
     * いいね削除を行う
     * @throws ServletException
     * @throws IOException
     */

    public void destroy() throws ServletException, IOException {

        //CSRF対策tokenのチェック
        if (checkToken()) {

            //セッションからログイン中のユーザー情報を取得
            UserView uv = (UserView) getSessionScope(AttributeConst.LOGIN_USE);

            //ReportServiceインスタンスを生成
            ReportService rs = new ReportService();

            // idを条件に投稿データを取得する
            ReportView rv = rs.findOne(toNumber(getRequestParam(AttributeConst.REP_ID)));

            //いいねデータを削除する
            service.destroy(rv, uv);

            //セッションに削除完了のフラッシュメッセージを設定
            putSessionScope(AttributeConst.FLUSH, MessageConst.I_FAV_DELETED.getMessage());


            //投稿詳細ページにリダイレクト
            int id = toNumber(getRequestParam(AttributeConst.REP_ID));
            redirect(ForwardConst.ACT_REP, ForwardConst.CMD_SHOW, id);

        }
    }
}
