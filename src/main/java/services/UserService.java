package services;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.NoResultException;

import actions.views.UserConverter;
import actions.views.UserView;
import constants.JpaConst;
import models.User;
import models.validators.UserValidator;
import utils.EncryptUtil;

/**
 * ユーザーテーブルの操作に関わる処理を行うクラス
 */
public class UserService extends ServiceBase {

    /**
     * 指定されたページ数の一覧画面に表示するデータを取得し、UserViewのリストで返却する
     * @param page ページ数
     * @return 表示するデータのリスト
     */
    public List<UserView> getPerpage(int page){
        List<User> users = em.createNamedQuery(JpaConst.Q_USE_GET_ALL, User.class)
                .setFirstResult(JpaConst.ROW_PER_PAGE * (page - 1))
                .setMaxResults(JpaConst.ROW_PER_PAGE)
                .getResultList();

        return UserConverter.toViewList(users);
    }

    /**
     * 未削除のユーザーテーブルのデータの件数を取得し、返却する
     * @return ユーザーテーブルのデータの件数
     */
    public long countAll() {
        long useCount = (long)em.createNamedQuery(JpaConst.Q_USE_COUNT, Long.class)
                .getSingleResult();

        return useCount;
    }

    /**
     * メールアドレス、パスワードを条件に取得したデータをUserViewのインスタンスで返却する
     * @param email メールアドレス
     * @param plainPass パスワード文字列
     * @param pepper pepper文字列
     * @return 取得データのインスタンス 取得できない場合null
     */
    public UserView findOne(String email,String plainPass, String pepper) {
        User u = null;
        try {
            //パスワードのハッシュ化
            String pass = EncryptUtil.getPasswordEncrypt(plainPass, pepper);

            //メールアドレスとハッシュ化済パスワードを条件に未削除のユーザーを一件取得する
            u = em.createNamedQuery(JpaConst.Q_USE_GET_BY_EMAIL_AND_PASS, User.class)
                    .setParameter(JpaConst.JPQL_PARM_EMAIL, email)
                    .setParameter(JpaConst.JPQL_PARM_PASSWORD, pass)
                    .getSingleResult();

        }catch(NoResultException ex) {

        }
        return UserConverter.toView(u);
    }

    /**
     * idを条件に取得したデータをUserViewのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */
    public UserView findOne(int id) {
        User u = findOneInternal(id);
        return UserConverter.toView(u);
    }


    /**
     *  メールアドレスを条件に該当するデータの件数を取得し、返却する
     * @param email メールアドレス
     * @return 該当するデータの件数
     */
    public long countByEmail(String email) {

        //指定したメールアドレスを保持するユーザーの件数を取得する
        long users_count = (long) em.createNamedQuery(JpaConst.Q_USE_COUNT_RESISTERED_BY_EMAIL, Long.class)
                .setParameter(JpaConst.JPQL_PARM_EMAIL, email)
                .getSingleResult();
        return users_count;
    }

    /**
     * 画面から入力されたユーザーの登録内容を元にデータを1件作成し、ユーザーテーブルに登録する
     * @param uv 画面から入力されたユーザーの登録内容
     * @param pepper pepper文字列
     * @return バリデーションや登録処理中に発生したエラーのリスト
     */
    public List<String> create(UserView uv, String pepper) {

        //パスワードをハッシュ化して設定
        String pass = EncryptUtil.getPasswordEncrypt(uv.getPassword(), pepper);
        uv.setPassword(pass);

        //登録日時、更新日時は現在時刻を設定する
        LocalDateTime now = LocalDateTime.now();
        uv.setCreatedAt(now);
        uv.setUpdatedAt(now);

        //登録内容のバリデーションを行う
        List<String> errors = UserValidator.validate(this, uv, true, true);

        //バリデーションエラーがなければデータを登録する
        if (errors.size() == 0) {
            create(uv);
        }

        //エラーを返却（エラーがなければ0件の空リスト）
        return errors;
    }

    /**
     * 画面から入力されたユーザーの更新内容を元にデータを1件作成し、ユーザーテーブルを更新する
     * @param uv 画面から入力されたユーザーの登録内容
     * @param pepper pepper文字列
     * @return バリデーションや更新処理中に発生したエラーのリスト
     */
    public List<String> update(UserView uv, String pepper) {

        //idを条件に登録済みのユーザー情報を取得する
        UserView savedUse = findOne(uv.getId());

        boolean validateEmail = false;
        if (!savedUse.getEmail().equals(uv.getEmail())) {
            //メールアドレスを更新する場合

            //メールアドレスについてのバリデーションを行う
            validateEmail = true;
            //変更後のメールアドレスを設定する
            savedUse.setEmail(uv.getEmail());
        }

        boolean validatePass = false;
        if (uv.getPassword() != null && !uv.getPassword().equals("")) {
            //パスワードに入力がある場合

            //パスワードについてのバリデーションを行う
            validatePass = true;

            //変更後のパスワードをハッシュ化し設定する
            savedUse.setPassword( EncryptUtil.getPasswordEncrypt(uv.getPassword(), pepper));

        }

        savedUse.setName(uv.getName()); //変更後の名前を設定する

        //更新日時に現在時刻を設定する
        LocalDateTime today = LocalDateTime.now();
        savedUse.setUpdatedAt(today);

        //更新内容についてバリデーションを行う
        List<String> errors = UserValidator.validate(this, savedUse,validateEmail, validatePass);

        //バリデーションエラーがなければデータを更新する
        if (errors.size() == 0) {
            update(savedUse);
        }

        //エラーを返却（エラーがなければ0件の空リスト）
        return errors;
    }

    /**
     * ユーザーデータを更新する
     * @param ev 画面から入力されたユーザーの登録内容
     */
    private void update(UserView uv) {

        em.getTransaction().begin();
        User u = findOneInternal(uv.getId());
        UserConverter.copyViewToModel(u, uv);
        em.getTransaction().commit();

    }

    /**
     * メールアドレスとパスワードを条件に検索し、データが取得できるかどうかで認証結果を返却する
     * @param email メールアドレス
     * @param plainPass パスワード
     * @param pepper pepper文字列
     * @return 認証結果を返却す(成功:true 失敗:false)
     */
    public Boolean validateLogin(String email, String plainPass, String pepper) {

        boolean isValidUser = false;
        if (email != null && !email.equals("") && plainPass != null && !plainPass.equals("")) {
            UserView uv = findOne(email, plainPass, pepper);

            if (uv != null && uv.getId() != null) {

                //データが取得できた場合、認証成功
                isValidUser = true;
            }
        }

        //認証結果を返却する
        return isValidUser;
    }


    /**
     * idを条件にデータを1件取得し、Userのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */
    private User findOneInternal(int id) {
        User u = em.find(User.class, id);

        return u;
    }

    /**
     * ユーザーデータを1件登録する
     * @param uv ユーザーデータ
     * @return 登録結果(成功:true 失敗:false)
     */
    private void create(UserView uv) {

        em.getTransaction().begin();
        em.persist(UserConverter.toModel(uv));
        em.getTransaction().commit();

    }



}
