package actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.CommentView;
import actions.views.FavoriteView;
import actions.views.ReportView;
import actions.views.UserView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.MessageConst;
import services.CommentService;
import services.FavoriteService;
import services.ReportService;

/**
 * 投稿に関する処理を行うActionクラス
 *
 */
public class ReportAction extends ActionBase {

    private ReportService service;
    private CommentService comService;

    /**
     * メソッドを実行する
     */
    @Override
    public void process() throws ServletException, IOException {

        service = new ReportService();
        comService = new CommentService();

        //メソッドを実行
        invoke();

        service.close();
        comService.close();
    }


    /**
     * 新規投稿画面を表示する
     */
    public void entryNew() throws ServletException, IOException {

        //CSRF対策用トークン
        putRequestScope(AttributeConst.TOKEN, getTokenId());

        //空の投稿インスタンス
        putRequestScope(AttributeConst.REPORT, new ReportView());

        //新規登録画面を表示
        forward(ForwardConst.FW_REP_NEW);
    }

    /**
     * 新規投稿登録を行う
     * @throws ServletException
     * @throws IOException
     */
    public void create() throws ServletException, IOException {

        //CSRF対策 tokenのチェック
        if (checkToken()) {

            //セッションからログイン中のユーザー情報を取得
            UserView uv = (UserView) getSessionScope(AttributeConst.LOGIN_USE);

            //パラメータの値をもとに日報情報のインスタンスを作成する
            ReportView rv = new ReportView(
                    null,
                    uv, //ログインしている従業員を、日報作成者として登録する
                    getRequestParam(AttributeConst.REP_TITLE),
                    getRequestParam(AttributeConst.REP_CONTENT),
                    null,
                    null);

            //日報情報登録
            List<String> errors = service.create(rv);

            if (errors.size() > 0) {
                //登録中にエラーがあった場合

                putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
                putRequestScope(AttributeConst.REPORT, rv); //入力された投稿情報
                putRequestScope(AttributeConst.ERR, errors); //エラーのリスト

                //新規投稿画面を再表示
                forward(ForwardConst.FW_REP_NEW);

            } else {
                //登録中にエラーがなかった場合

                //セッションに投稿完了のフラッシュメッセージを設定
                putSessionScope(AttributeConst.FLUSH, MessageConst.I_REGISTERED.getMessage());

                //一覧画面にリダイレクト
                redirect(ForwardConst.ACT_TOP, ForwardConst.CMD_INDEX);

            }
        }
    }

    /**
     * 詳細画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void show() throws ServletException, IOException {

        //idを条件に投稿データを取得する
        ReportView rv = service.findOne(toNumber(getRequestParam(AttributeConst.REP_ID)));

        //セッションからログイン中の従業員情報を取得
        UserView uv = (UserView) getSessionScope(AttributeConst.LOGIN_USE);

        if (rv == null) {
            //該当の投稿データが存在しない場合はエラー画面を表示
            forward(ForwardConst.FW_ERR_UNKNOWN);

        } else {

            //FavoriteServiceのインスタンスを作成
            FavoriteService fs = new FavoriteService();

            //ログインしているユーザーが投稿にいいねしているか判定
            Boolean favorite_flag = fs.getFavoriteFlag(uv, rv);

            //該当投稿にいいねしているユーザー情報一覧を取得
            List<FavoriteView> favorites = fs.getFavoritesUser(rv);

            // その投稿にいいねしている人数を取得
            Integer favorites_count = favorites.size();

            //投稿のコメントデータを取得する
            int page = getPage();
            List<CommentView> comments = comService.getMinePerPage(rv, page);

            //コメントデータの件数を取得する。
            long myCommentsCount = comService.countAllMine(rv);

            //セッションにフラッシュメッセージが設定されている場合はリクエストスコープに移し替え、セッションからは削除する
            String flush = getSessionScope(AttributeConst.FLUSH);
            if (flush != null) {
                putRequestScope(AttributeConst.FLUSH, flush);
                removeSessionScope(AttributeConst.FLUSH);
            }

            //セッションにエラーメッセージが設定されている場合はリクエストスコープに移し替え、セッションからは削除する
            List<String> errors = getSessionScope(AttributeConst.ERR);
            if (errors != null) {
                putRequestScope(AttributeConst.ERR, errors);
                removeSessionScope(AttributeConst.ERR);
            }

            putSessionScope(AttributeConst.REP_ID,rv.getId());//レポートIDをセッションスコープに登録
            putRequestScope(AttributeConst.COMMENTS, comments); //取得したコメントデータ
            putRequestScope(AttributeConst.COM_COUNT, myCommentsCount); //コメントの数
            putRequestScope(AttributeConst.PAGE, page); //ページ数
            putRequestScope(AttributeConst.FAV_FLAG, favorite_flag);// すでにいいねしているかのフラグ
            putRequestScope(AttributeConst.FAVORITES, favorites);// いいね一覧
            putRequestScope(AttributeConst.FAV_COUNT, favorites_count);// いいね数
            putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
            putRequestScope(AttributeConst.REPORT, rv); //取得した投稿データ

            //詳細画面を表示
            forward(ForwardConst.FW_REP_SHOW);
        }
    }

    /**
     * 編集画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void edit() throws ServletException, IOException {

        //idを条件に投稿データを取得する
        ReportView rv = service.findOne(toNumber(getRequestParam(AttributeConst.REP_ID)));

        //セッションからログイン中のユーザー情報を取得
        UserView uv = (UserView) getSessionScope(AttributeConst.LOGIN_USE);

        if (rv == null || uv.getId() != rv.getUser().getId()) {
            //該当の投稿データが存在しない、または
            //ログインしているユーザーが該当投稿の作成者でない場合はエラー画面を表示
            forward(ForwardConst.FW_ERR_UNKNOWN);

        } else {

            putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
            putRequestScope(AttributeConst.REPORT, rv); //取得した日報データ

            //編集画面を表示
            forward(ForwardConst.FW_REP_EDIT);

        }
    }

    /**
     * 更新を行う
     * @throws ServletException
     * @throws IOException
     */
    public void update() throws ServletException, IOException {

        //CSRF対策 tokenのチェック
        if (checkToken()) {

            //idを条件に投稿データを取得する
            ReportView rv = service.findOne(toNumber(getRequestParam(AttributeConst.REP_ID)));

            //入力された投稿内容を設定する
            rv.setTitle(getRequestParam(AttributeConst.REP_TITLE));
            rv.setContent(getRequestParam(AttributeConst.REP_CONTENT));

            //投稿データを更新する
            List<String> errors = service.update(rv);

            if (errors.size() > 0) {
                //更新中にエラーが発生した場合

                putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
                putRequestScope(AttributeConst.REPORT, rv); //入力された投稿内容
                putRequestScope(AttributeConst.ERR, errors); //エラーのリスト

                //編集画面を再表示
                forward(ForwardConst.FW_REP_EDIT);
            } else {
                //更新中にエラーがなかった場合

                //セッションに更新完了のフラッシュメッセージを設定
                putSessionScope(AttributeConst.FLUSH, MessageConst.I_UPDATED.getMessage());

                //一覧画面にリダイレクト
                redirect(ForwardConst.ACT_TOP, ForwardConst.CMD_INDEX);

            }
        }
    }

    /**
     * 削除を行う
     * @throws ServletException
     * @throws IOException
     */
    public void destroy() throws ServletException, IOException {

        //CSRF対策 tokenのチェック
        if (checkToken()) {

            //idを条件に投稿データを削除する
            service.destroy(toNumber(getRequestParam(AttributeConst.REP_ID)));

            //セッションに削除完了のフラッシュメッセージを設定
            putSessionScope(AttributeConst.FLUSH, MessageConst.I_DELETED.getMessage());

            //一覧画面にリダイレクト
            redirect(ForwardConst.ACT_TOP, ForwardConst.CMD_INDEX);
        }
    }
}
