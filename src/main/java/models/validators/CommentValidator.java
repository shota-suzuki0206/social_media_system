package models.validators;

import java.util.ArrayList;
import java.util.List;

import actions.views.CommentView;
import constants.MessageConst;

/**
 * コメントインスタンスに設定されている値のバリデーションを行うクラス
 *
 */
public class CommentValidator {

    /**
     * コメントインスタンスの項目についてバリデーションを行う
     * @param cv コメントインスタンス
     * @return エラーのリスト
     */
    public static List<String> validate(CommentView cv) {
        List<String> errors = new ArrayList<String>();

        //投稿内容のチェック
        String contentError = validateContent(cv.getContent());
        if (!contentError.equals("")) {
            errors.add(contentError);
        }

        return errors;
    }

    /**
     * コメント内容に入力値があるかをチェックし、入力値がなければエラーメッセージを返却
     * @param content コメント内容
     * @return エラーメッセージ
     */
    private static String validateContent(String content) {
        if (content == null || content.equals("")) {
            return MessageConst.E_NOCOMMENT.getMessage();
        }

        //入力値がある場合は空文字を返却
        return "";
    }
}
