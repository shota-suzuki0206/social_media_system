package actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.CommentView;
import actions.views.ReportView;
import actions.views.UserView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.MessageConst;
import services.CommentService;
import services.ReportService;

/**
 * コメントに関する処理を行うActionクラス
 *
 */
public class CommentAction extends ActionBase {

    private CommentService service;

    /**
     * メソッドを実行する
     */
    @Override
    public void process() throws ServletException, IOException {

        service = new CommentService();

        //メソッドを実行
        invoke();
        service.close();
    }

    /**
     * 新規コメント登録を行う
     * @throws ServletException
     * @throws IOException
     */
    public void create() throws ServletException, IOException {

        //CSRF対策 tokenのチェック
        if (checkToken()) {

            // ReportServiceインスタンスを生成
            ReportService rs = new ReportService();

            // idを条件に日報データを取得する
            ReportView rv = rs.findOne(toNumber(getRequestParam(AttributeConst.REP_ID)));

            //セッションからログイン中のユーザー情報を取得
            UserView uv = (UserView) getSessionScope(AttributeConst.LOGIN_USE);

            //パラメータの値をもとにコメント情報のインスタンスを作成する
            CommentView cv = new CommentView(
                    null,
                    uv, //ログインしている従業員を、日報作成者として登録する
                    rv,
                    getRequestParam(AttributeConst.COM_CONTENT),
                    null,
                    null);

            //日報情報登録
            List<String> errors = service.create(cv);

            if (errors.size() > 0) {

                //登録中にエラーがあった場合
                putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
                putRequestScope(AttributeConst.COMMENT, cv); //入力された投稿情報
                putRequestScope(AttributeConst.ERR, errors); //エラーのリスト

                //投稿詳細ページにリダイレクト
                int id = toNumber(getRequestParam(AttributeConst.REP_ID));
                redirect(ForwardConst.ACT_REP, ForwardConst.CMD_SHOW, id);

            } else {

                //登録中にエラーがなかった場合

                //セッションに登録完了のフラッシュメッセージを設定
                putSessionScope(AttributeConst.FLUSH, MessageConst.I_COM_REGISTERED.getMessage());
                putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン

                //投稿詳細ページにリダイレクト
                int id = toNumber(getRequestParam(AttributeConst.REP_ID));
                redirect(ForwardConst.ACT_REP, ForwardConst.CMD_SHOW, id);

            }
        }
    }
}
