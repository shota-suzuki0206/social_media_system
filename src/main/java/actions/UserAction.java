package actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.UserView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
import constants.MessageConst;
import constants.PropertyConst;
import services.UserService;

/**
 * ユーザの関わる処理を行うActionクラス
 *
 */
public class UserAction extends ActionBase{

    private UserService service;

    /**
     * メソッドを実行する
     */
    @Override
    public void process() throws ServletException, IOException{

        service = new UserService();

        //メソッドを実行
        invoke();

        service.close();
    }

    /**
     * 一覧画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void index() throws ServletException, IOException{

        //指定されたページ数の一覧画面に表示するデータを取得
        int page = getPage();
        List<UserView> users = service.getPerpage(page);

        //全てのユーザーデータの件数を取得
        long userCount = service.countAll();

        putRequestScope(AttributeConst.USERS,users); //取得したユーザーデータ
        putRequestScope(AttributeConst.USE_COUNT,userCount); //すべてのユーザーデータの件数
        putRequestScope(AttributeConst.PAGE,page); //ページ数
        putRequestScope(AttributeConst.MAX_ROW,JpaConst.ROW_PER_PAGE); //一ページに表示するレコードの数

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

            String passCheck = null;

            //パスワードの一致チェック
            if(AttributeConst.USE_PASS.getValue() != AttributeConst.USE_PASS_CONFIRMATION.getValue()) {
                passCheck ="パスワードとパスワード（確認用）が不一致です";
            }

            //パラメータの値を元に従業員情報のインスタンスを作成する
            UserView uv = new UserView(
                    null,
                    getRequestParam(AttributeConst.USE_NAME),
                    getRequestParam(AttributeConst.USE_EMAIL),
                    getRequestParam(AttributeConst.USE_PASS),
                    null,
                    null,
                    AttributeConst.DEL_FLAG_FALSE.getIntegerValue());

            //アプリケーションスコープからpepper文字列を取得
            String pepper = getContextScope(PropertyConst.PEPPER);

            //ユーザー情報登録
            List<String> errors = service.create(uv, pepper);


            if (errors.size() > 0 && passCheck !=null) {
                //登録中にエラーがあった場合

                putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
                putRequestScope(AttributeConst.USER, uv); //入力されたユーザー情報
                putRequestScope(AttributeConst.ERR, errors); //エラーのリスト
                putRequestScope(AttributeConst.PASSCHECK, passCheck); //エラーのリスト

                //新規会員登録画面を再表示
                forward(ForwardConst.FW_USE_NEW);

            } else {
                //登録中にエラーがなかった場合

                //セッションに登録完了のフラッシュメッセージを設定
                putSessionScope(AttributeConst.FLUSH, MessageConst.I_REGISTERED.getMessage());

                //一覧画面にリダイレクト
                redirect(ForwardConst.ACT_USE, ForwardConst.CMD_INDEX);
            }

        }
    }
}
