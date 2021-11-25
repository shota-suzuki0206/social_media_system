package actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.ReportView;
import actions.views.UserView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
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

}
