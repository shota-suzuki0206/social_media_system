package models.validators;

import java.util.ArrayList;
import java.util.List;

import actions.views.UserView;
import constants.MessageConst;
import services.UserService;

/**
 * ユーザーインスタンスに設定されている値のバリデーションを行うクラス
 *
 */
public class UserValidator {

    /**
     * ユーザーインスタンスの各項目についてバリデーションを行う
     * @param service 呼び出し元Serviceクラスのインスタンス
     * @param ev UserServiceのインスタンス
     * @param emailDuplicateCheckFlag メールアドレスの重複チェックを実施するかどうか(実施する:true 実施しない:false)
     * @param passwordCheckFlag パスワードの入力チェックを実施するかどうか(実施する:true 実施しない:false)
     * @return エラーのリスト
     */
    public static List<String> validate(
            UserService service, UserView uv, Boolean emailDuplicateCheckFlag, Boolean passwordCheckFlag) {
        List<String> errors = new ArrayList<String>();

        //名前のチェック
        String nameError = validateName(uv.getName());
        if (!nameError.equals("")) {
            errors.add(nameError);
        }

        //メールアドレスのチェック
        String emailError = validateEmail(service, uv.getEmail(), emailDuplicateCheckFlag);
        if (!emailError.equals("")) {
            errors.add(emailError);
        }
        //パスワードのチェック
        String passError = validatePassword(uv.getPassword(), passwordCheckFlag);
        if (!passError.equals("")) {
            errors.add(passError);
        }

        return errors;
    }

    /**
     * 名前に入力値があるかをチェックし、入力値がなければエラーメッセージを返却
     * @param name 名前
     * @return エラーメッセージ
     */
    private static String validateName(String name) {

        if (name == null || name.equals("")) {
            return MessageConst.E_NONAME.getMessage();
        }

        //入力値がある場合は空文字を返却
        return "";
    }


    /**
     * メールアドレスの入力チェックを行い、エラーメッセージを返却
     * @param service UserServiceのインスタンス
     * @param email メールアドレス
     * @param emailDuplicateCheckFlag メールアドレスの重複チェックを実施するかどうか(実施する:true 実施しない:false)
     * @return エラーメッセージ
     */
    private static String validateEmail(UserService service, String email, Boolean emailDuplicateCheckFlag) {

        //入力値がなければエラーメッセージを返却
        if (email == null || email.equals("")) {
            return MessageConst.E_NOEMAIL.getMessage();
        }

        if (emailDuplicateCheckFlag) {
            //メールアドレスの重複チェックを実施

            long usersCount = isDuplicateUser(service, email);

            //同一メールアドレスが既に登録されている場合はエラーメッセージを返却
            if (usersCount > 0) {
                return MessageConst.E_USE_EMAIL_EXIST.getMessage();
            }
        }

        //エラーがない場合は空文字を返却
        return "";
    }

    /**
     * @param service UserServiceのインスタンス
     * @param email メールアドレス
     * @return ユーザーテーブルに登録されている同一メールアドレスのデータの件数
     */
    private static long isDuplicateUser(UserService service, String email) {

        long usersCount = service.countByEmail(email);
        return usersCount;
    }

    /**
     * パスワードの入力チェックを行い、エラーメッセージを返却
     * @param password パスワード
     * @param passwordCheckFlag パスワードの入力チェックを実施するかどうか(実施する:true 実施しない:false)
     * @return エラーメッセージ
     */
    private static String validatePassword(String password, Boolean passwordCheckFlag) {

        //入力チェックを実施 かつ 入力値がなければエラーメッセージを返却
        if (passwordCheckFlag && (password == null || password.equals(""))) {
            return MessageConst.E_NOPASSWORD.getMessage();
        }

        //エラーがない場合は空文字を返却
        return "";
    }
}
