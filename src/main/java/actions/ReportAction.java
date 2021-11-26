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
import services.ReportService;

/**
 * 投稿に関する処理を行うActionクラス
 *
 */
public class ReportAction extends ActionBase {

    private ReportService service;

    /**
     * メソッドを実行する
     */
    @Override
    public void process() throws ServletException, IOException {

        service = new ReportService();

        //メソッドを実行
        invoke();
        service.close();
    }

    /**
     *自分の投稿一覧を表示する
     */
    public void index() throws ServletException, IOException {

        //セッションからログイン中のユーザー情報を取得する
        UserView loginUser = (UserView) getSessionScope(AttributeConst.LOGIN_USE);

        //ログイン中のユーザーが作成した投稿データを、指定されたページ数の一覧画面に表示する分取得する
        int page = getPage();
        List<ReportView> reports = service.getMinePerPage(loginUser, page);

        //ログイン中のユーザーが作成した投稿データの件数を取得する。
        long myReportsCount = service.countAllMine(loginUser);

        putRequestScope(AttributeConst.REPORTS, reports); //取得した投稿データ
        putRequestScope(AttributeConst.REP_COUNT, myReportsCount); //ログイン中のユーザーが作成した投稿の数
        putRequestScope(AttributeConst.PAGE, page); //ページ数
        putRequestScope(AttributeConst.MAX_ROW, JpaConst.ROW_PER_PAGE); //1ページに表示するレコードの数

        //一覧画面を表示
        forward(ForwardConst.FW_REP_INDEX);
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

                //セッションに登録完了のフラッシュメッセージを設定
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

        if (rv == null) {
            //該当の投稿データが存在しない場合はエラー画面を表示
            forward(ForwardConst.FW_ERR_UNKNOWN);

        } else {

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
     * 物理削除を行う
     * @throws ServletException
     * @throws IOException
     */
    public void destroy() throws ServletException, IOException {

        //CSRF対策 tokenのチェック
        if (checkToken()) {

            //idを条件に投稿データを論理削除する
            service.destroy(toNumber(getRequestParam(AttributeConst.REP_ID)));

            //セッションに削除完了のフラッシュメッセージを設定
            putSessionScope(AttributeConst.FLUSH, MessageConst.I_DELETED.getMessage());

            //一覧画面にリダイレクト
            redirect(ForwardConst.ACT_TOP, ForwardConst.CMD_INDEX);
        }
    }
}
