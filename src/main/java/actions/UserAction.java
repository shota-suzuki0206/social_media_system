package actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.UserView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
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
}
