package actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.FollowView;
import actions.views.UserView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
import constants.MessageConst;
import services.FollowService;
import services.UserService;

public class FollowAction extends ActionBase {

    private FollowService service;
    private UserService useService;

    /**
     * メソッドを実行する
     */
    @Override
    public void process() throws ServletException, IOException {

        service = new FollowService();
        useService = new UserService();

        //メソッドを実行
        invoke();

        useService.close();
        service.close();
    }

    /**
     * フォロー一覧画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void index() throws ServletException, IOException {

        //セッションからユーザー情報を取得
        UserView uv = useService.findOne(toNumber(getRequestParam(AttributeConst.USE_ID)));

        //指定したユーザのフォローリストを指定されたページ数の一覧画面に表示する分のデータを取得
        int page = getPage();
        List<FollowView> follows = service.getAllPerPage(uv,page);

        //ユーザーがフォローした人数を取得
        long followsCount = service.countAllMine(uv);

        putRequestScope(AttributeConst.USER, uv); //取得したユーザーデータ
        putRequestScope(AttributeConst.FOLLOWS, follows); //取得したフォローデータ
        putRequestScope(AttributeConst.FLW_COUNT, followsCount); //ユーザーがフォローした人数
        putRequestScope(AttributeConst.PAGE, page); //ページ数
        putRequestScope(AttributeConst.MAX_ROW, JpaConst.ROW_PER_PAGE); //1ページに表示するレコードの数

        //一覧画面を表示
        forward(ForwardConst.FW_FLW_INDEX);
    }

    /**
     * フォロワーー一覧画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void show() throws ServletException, IOException {

        //セッションからユーザー情報を取得
        UserView uv = useService.findOne(toNumber(getRequestParam(AttributeConst.USE_ID)));

        //指定したユーザのフォロワーリストを指定されたページ数の一覧画面に表示する分のデータを取得
        int page = getPage();
        List<FollowView> followers = service.getFollowerPerPage(uv,page);

        //フォロワーの人数を取得
        long followersCount = service.countFollowerMine(uv);

        putRequestScope(AttributeConst.USER, uv); //取得したユーザーデータ
        putRequestScope(AttributeConst.FOLLOWERS, followers); //取得したフォロワーデータ
        putRequestScope(AttributeConst.FOLLOWERS_COUNT, followersCount); //フォロワーの人数
        putRequestScope(AttributeConst.PAGE, page); //ページ数
        putRequestScope(AttributeConst.MAX_ROW, JpaConst.ROW_PER_PAGE); //1ページに表示するレコードの数

        //一覧画面を表示
        forward(ForwardConst.FW_FLW_SHOW);
    }

    /**
     * フォローの登録を行う
     * @throws ServletException
     * @throws IOException
     */
    public void create() throws ServletException, IOException{

        //CSRF対策_tokenのチェック
        if(checkToken()) {

            //セッションからログイン中のユーザー情報を取得
            UserView uv = (UserView) getSessionScope(AttributeConst.LOGIN_USE);

            //セッションから詳細画面を開いたユーザーのidを取得
            Integer id = getSessionScope(AttributeConst.USE_ID);

            //idを条件にユーザー情報を取得する
            UserView flw = useService.findOne(id);

            //セッションスコープからユーザーidを消去
            removeSessionScope(AttributeConst.USE_ID);

            //パラメータの値をもとにフォロー情報のインスタンスを作成する
            FollowView fv = new FollowView(
                    null,
                    uv, //ログインしているユーザーをフォローユーザーとして登録する
                    flw, //詳細画面のユーザーをフォロワーとして登録する
                    null,
                    null);

            //フォロー情報登録
            service.create(fv);

            //セッションに登録完了のフラッシュメッセージを登録
            putSessionScope(AttributeConst.FLUSH, MessageConst.I_FLW_REGISTERED.getMessage());

            //ユーザー詳細ページにリダイレクト
            redirect(ForwardConst.ACT_USE, ForwardConst.CMD_SHOW, id);
        }
    }

    /**
     * フォロー削除を行う
     * @throws ServletException
     * @throws IOException
     */
    public void destroy() throws ServletException, IOException {

        //CSRF対策_tokenのチェック
        if(checkToken()) {

            //セッションからログイン中のユーザー情報を取得
            UserView uv = (UserView) getSessionScope(AttributeConst.LOGIN_USE);

            //セッションから詳細画面を開いたユーザーのidを取得
            Integer id = getSessionScope(AttributeConst.USE_ID);

            //idを条件にユーザー情報を取得する
            UserView flw = useService.findOne(id);

            //セッションスコープからユーザーidを消去
            removeSessionScope(AttributeConst.USE_ID);

            //フォローデータを削除する
            service.destroy(uv, flw);

            //セッションにフォロー解除のフラッシュメッセージを設定
            putSessionScope(AttributeConst.FLUSH, MessageConst.I_FLW_DELETED.getMessage());

            //ユーザー詳細ページにリダイレクト
            redirect(ForwardConst.ACT_USE, ForwardConst.CMD_SHOW, id);
        }
    }

}
