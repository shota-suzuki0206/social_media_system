package actions.views;

import java.util.ArrayList;
import java.util.List;

import models.Comment;

/**
 * コメントデータのDTOモデル⇔Viewモデルの変換を行うクラス
 *
 */
public class CommentConverter {

    /**
     * ViewモデルのインスタンスからDTOモデルのインスタンスを作成する
     * @param cv CommentViewのインスタンス
     * @return Commentのインスタンス
     */
    public static Comment toModel(CommentView cv) {
        return new Comment(
                cv.getId(),
                UserConverter.toModel(cv.getUser()),
                ReportConverter.toModel(cv.getReport()),
                cv.getContent(),
                cv.getCreatedAt(),
                cv.getUpdatedAt());
    }

    /**
     * DTOモデルのインスタンスからViewモデルのインスタンスを作成する
     * @param c Commentのインスタンス
     * @return CommentViewのインスタンス
     */
    public static CommentView toView(Comment c) {

        if(c == null) {
            return null;
        }

        return new CommentView(
                c.getId(),
                UserConverter.toView(c.getUser()),
                ReportConverter.toView(c.getReport()),
                c.getContent(),
                c.getCreatedAt(),
                c.getUpdatedAt());
    }

    /**
     * DTOモデルのリストからViewモデルのリストを作成する
     * @param list DTOモデルのリスト
     * @return Viewモデルのリスト
     */
    public static List<CommentView> toViewList(List<Comment> list){
        List<CommentView> evs = new ArrayList<>();

        for (Comment c : list) {
            evs.add(toView(c));
        }

        return evs;
    }

    /**
     * Viewモデルの全フィールドの内容をDTOモデルのフィールドにコピーする
     * @param c DTOモデル(コピー先)
     * @param cv Viewモデル(コピー元)
     */
    public static void copyViewToModel(Comment c, CommentView cv) {
        c.setId(cv.getId());
        c.setUser(UserConverter.toModel(cv.getUser()));
        c.setReport(ReportConverter.toModel(cv.getReport()));
        c.setContent(cv.getContent());
        c.setCreatedAt(cv.getCreatedAt());
        c.setUpdatedAt(cv.getUpdatedAt());

    }


}
