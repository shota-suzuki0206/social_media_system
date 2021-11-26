package services;

import java.time.LocalDateTime;
import java.util.List;

import actions.views.CommentConverter;
import actions.views.CommentView;
import actions.views.ReportConverter;
import actions.views.ReportView;
import constants.JpaConst;
import models.Comment;
import models.validators.CommentValidator;

public class CommentService extends ServiceBase {

    /**
     * 指定した投稿に対してのコメントデータを、指定されたページ数の一覧画面に表示する分取得しCommentViewのリストで返却する
     * @param report 投稿
     * @param page ページ数
     * @return 一覧画面に表示するデータのリスト
     */
    public List<CommentView> getMinePerPage(ReportView report, int page) {

        List<Comment> comments = em.createNamedQuery(JpaConst.Q_COM_GET_ALL_MINE, Comment.class)
                .setParameter(JpaConst.JPQL_PARM_REPORT, ReportConverter.toModel(report))
                .setFirstResult(JpaConst.ROW_PER_PAGE * (page - 1))
                .setMaxResults(JpaConst.ROW_PER_PAGE)
                .getResultList();
        return CommentConverter.toViewList(comments);

    }

    /**
     * 指定した投稿に対してのコメントデータの件数を取得し、返却する
     * @param report
     * @return コメントデータの件数
     */
    public long countAllMine(ReportView report) {

        long count = (long) em.createNamedQuery(JpaConst.Q_COM_COUNT_ALL_MINE, Long.class)
                .setParameter(JpaConst.JPQL_PARM_REPORT, ReportConverter.toModel(report))
                .getSingleResult();

        return count;
    }

    /**
     * 画面から入力されたコメントの内容を元にデータを1件作成し、コメントテーブルに登録する
     * @param cv コメントの内容
     * @return バリデーションで発生したエラーのリスト
     */
    public  List<String> create(CommentView cv) {

        List<String> errors = CommentValidator.validate(cv);
        if (errors.size() == 0) {
            LocalDateTime ldt = LocalDateTime.now();
            cv.setCreatedAt(ldt);
            cv.setUpdatedAt(ldt);
            createInternal(cv);
        }

        //バリデーションで発生したエラーを返却（エラーがなければnull）
        return errors;
    }

    /**
     * コメントデータを1件登録する
     * @param cv コメントデータ
     */
    private void createInternal(CommentView cv) {

        em.getTransaction().begin();
        em.persist(CommentConverter.toModel(cv));
        em.getTransaction().commit();

    }
}
