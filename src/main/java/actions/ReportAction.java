package actions;

import java.io.IOException;

import javax.servlet.ServletException;

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
    public void process() throws ServletException, IOException{

        service = new ReportService();

      //メソッドを実行
        invoke();
        service.close();
    }

    /**
     *
     */


}
