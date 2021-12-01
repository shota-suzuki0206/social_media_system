package services;

import java.time.LocalDateTime;
import java.util.List;

import actions.views.FollowConverter;
import actions.views.FollowView;
import actions.views.UserConverter;
import actions.views.UserView;
import constants.JpaConst;
import models.Follow;

public class FollowService extends ServiceBase {

    /**
     * 指定したユーザーがフォローしたユーザーを、指定されたページ数の一覧画面に表示する文取得しFollowViewのリストで返却する
     * @param user ユーザー
     * @param page ページ数
     * @return 一覧画面に表示するデータのリスト
     */
    public List<FollowView> getAllPerPage(UserView follow, int page) {

        List<Follow> follows = em.createNamedQuery(JpaConst.Q_FLW_GET_ALL_MINE, Follow.class)
                .setParameter(JpaConst.JPQL_PARM_FOLLOW, UserConverter.toModel(follow))
                .setFirstResult(JpaConst.ROW_PER_PAGE * (page - 1))
                .setMaxResults(JpaConst.ROW_PER_PAGE)
                .getResultList();
        return FollowConverter.toViewList(follows);
    }

    /**
     * 指定したユーザーが作成した投稿データの件数を取得し、返却する
     * @param user
     * @return 投稿データの件数
     */
    public long countAllMine(UserView follow) {
        long count = (long) em.createNamedQuery(JpaConst.Q_FLW_COUNT_ALL_MINE, Long.class)
                .setParameter(JpaConst.JPQL_PARM_FOLLOW, UserConverter.toModel(follow))
                .getSingleResult();

        return count;
    }


    /**
     * フォローテーブルに情報を登録する
     * @param fv FollowViewインスタンス
     */
    public void create(FollowView fv) {

        // 現在日時を取得
        LocalDateTime ldt = LocalDateTime.now();
        // セッターで値をセット
        fv.setCreatedAt(ldt);
        fv.setUpdatedAt(ldt);
        // データベースに保存
        createInternal(fv);

    }

    /**
     * いいねデータを1件登録する
     * @param fv FollowViewインスタンス
     */
    private void createInternal(FollowView fv) {

        em.getTransaction().begin();
        em.persist(FollowConverter.toModel(fv));
        em.getTransaction().commit();

    }
}
