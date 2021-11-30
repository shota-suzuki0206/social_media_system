package actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.FollowView;
import actions.views.UserView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
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
     * 一覧画面を表示する
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

        putRequestScope(AttributeConst.FOLLOWS, follows); //取得したフォローデータ
        putRequestScope(AttributeConst.FLW_COUNT, followsCount); //ユーザーがフォローした人数
        putRequestScope(AttributeConst.PAGE, page); //ページ数
        putRequestScope(AttributeConst.MAX_ROW, JpaConst.ROW_PER_PAGE); //1ページに表示するレコードの数

        //一覧画面を表示
        forward(ForwardConst.FW_FLW_INDEX);
    }

}
