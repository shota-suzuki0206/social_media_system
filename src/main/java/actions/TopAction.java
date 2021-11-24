package actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.ReportView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
import services.ReportService;

/**
 * トップページに関する処理を行うActionクラス
 *
 */
public class TopAction extends ActionBase {

    private ReportService service;

    /**
     * indexメソッドを実行する
     */
    @Override
    public void process() throws ServletException, IOException {

        service = new ReportService();
        //メソッドを実行
        invoke();

        service.close();

    }

    /**
     * 投稿一覧画面を表示する
     */
    public void index() throws ServletException, IOException {

        //投稿データを、指定されたページの一覧画面に表示する分取得する
        int page = getPage();
        List<ReportView> reports = service.getAllPage(page);

        //全投稿データの件数を取得する
        long reportsCount = service.countAll();

        putRequestScope(AttributeConst.REPORTS, reports); //取得した投稿データ
        putRequestScope(AttributeConst.REP_COUNT, reportsCount); //全投稿の数
        putRequestScope(AttributeConst.PAGE, page); //ページ数
        putRequestScope(AttributeConst.MAX_ROW, JpaConst.ROW_PER_PAGE); //1ページに表示するレコードの数

        //セッションにフラッシュメッセージが設定されている場合はリクエストスコープに移し替え、セッションからは削除する
        String flush = getSessionScope(AttributeConst.FLUSH);
        if (flush != null) {
            putRequestScope(AttributeConst.FLUSH, flush);
            removeSessionScope(AttributeConst.FLUSH);
        }

        //一覧画面を表示
        forward(ForwardConst.FW_TOP_INDEX);
    }

}
