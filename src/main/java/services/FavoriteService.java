package services;

import java.time.LocalDateTime;
import java.util.List;

import actions.views.FavoriteConverter;
import actions.views.FavoriteView;
import actions.views.ReportConverter;
import actions.views.ReportView;
import actions.views.UserConverter;
import actions.views.UserView;
import constants.JpaConst;
import models.Favorite;
import models.Report;
import models.User;

/**
 * いいねテーブルの操作にかかわる処理を行うクラス
 *
 */
public class FavoriteService extends ServiceBase {

    /**
     * 指定したユーザーがいいね！した投稿の一覧を取得
     * @param user ログイン中ユーザー
     * @param page ページ数
     * @return 一覧画面に表示するデータのリスト
     */
    public List<FavoriteView> getMinePerPage(UserView user, int page){
        List<Favorite> favorites = em.createNamedQuery(JpaConst.Q_FAV_GET_ALL_MINE, Favorite.class)
                .setParameter(JpaConst.JPQL_PARM_USER, UserConverter.toModel(user))
                .setFirstResult(JpaConst.ROW_PER_PAGE * (page - 1))
                .setMaxResults(JpaConst.ROW_PER_PAGE)
                .getResultList();
        return FavoriteConverter.toViewList(favorites);
    }

    /**
     * 指定したユーザーがいいね！した投稿の件数を取得し、返却する
     * @param user
     * @return 投稿データの件数
     */
    public long countAllMine(UserView user) {

        long count = (long) em.createNamedQuery(JpaConst.Q_FAV_COUNT_ALL_MINE, Long.class)
                .setParameter(JpaConst.JPQL_PARM_USER, UserConverter.toModel(user))
                .getSingleResult();

        return count;
    }

    /**
     * いいねテーブルに情報を登録する
     * @param fv FavoriteViewインスタンス
     */
    public void create(FavoriteView fv) {

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
     * @param fv FavoriteViewインスタンス
     */
    private void createInternal(FavoriteView fv) {

        em.getTransaction().begin();
        em.persist(FavoriteConverter.toModel(fv));
        em.getTransaction().commit();

    }

    /**
     * いいねデータを削除する
     * @param rv 投稿情報
     * @param uv ログイン中のユーザー情報
     */
    public void destroy(ReportView rv, UserView uv) {

        //従業員id、日報idを条件にデータを取得
        Favorite f = em.createNamedQuery(JpaConst.Q_FAV_GET_BY_USEID_AND_REPID, Favorite.class)
                .setParameter(JpaConst.JPQL_PARM_USER, UserConverter.toModel(uv))
                .setParameter(JpaConst.JPQL_PARM_REPORT, ReportConverter.toModel(rv))
                .getSingleResult();

            em.getTransaction().begin();
            em.remove(f); // いいねデータ削除
            em.getTransaction().commit();
            em.close();
    }

    /**
     * ログインユーザーが、投稿にいいねしているか判定
     * @param uv ログインユーザー
     * @param rv 投稿情報
     * @return true いいねしている
     * @return false いいねしていない
     */
    public Boolean getFavoriteFlag(UserView uv, ReportView rv) {

        //ViewクラスからModelクラスのインスタンスに変換
        User u = UserConverter.toModel(uv);
        Report r = ReportConverter.toModel(rv);

        //変換した値を元に該当するいいねデータがあるか検索する
        long count = em.createNamedQuery(JpaConst.Q_FAV_COUNT_BY_USEID_AND_REPID, Long.class)
                .setParameter("user", u)
                .setParameter("report", r)
                .getSingleResult();


        if (count == 0) {

            //いいねしてない場合falseで返す
            return false;


        } else {

            //いいねしている場合trueで返す
            return true;

        }
    }


    //詳細画面を開いた投稿に対するいいねリスト取得
    public List<FavoriteView> getFavoritesUser(ReportView rv) {

        Report r = ReportConverter.toModel(rv);
        List<Favorite> favorites = em.createNamedQuery(JpaConst.Q_FAV_GET_BY_REP_ID, Favorite.class)
                .setParameter("report", r)
                .getResultList();
        return FavoriteConverter.toViewList(favorites);
    }

}
