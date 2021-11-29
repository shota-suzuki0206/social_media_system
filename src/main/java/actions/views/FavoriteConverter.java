package actions.views;

import java.util.ArrayList;
import java.util.List;

import models.Favorite;

/**
 * いいねデータのDTOモデル⇔Viewモデルの変換を行うクラス
 *
 */
public class FavoriteConverter {

    /**
     * ViewモデルのインスタンスからDTOモデルのインスタンスを作成する
     * @param fv FavoriteViewのインスタンス
     * @return Favoriteのインスタンス
     */
    public static Favorite toModel(FavoriteView fv) {
        return new Favorite(
                fv.getId(),
                UserConverter.toModel(fv.getUser()),
                ReportConverter.toModel(fv.getReport()),
                fv.getCreatedAt(),
                fv.getUpdatedAt());
    }

    /**
     * DTOモデルのインスタンスからViewモデルのインスタンスを作成する
     * @param f Favoriteのインスタンス
     * @return FavoriteViewのインスタンス
     */
    public static FavoriteView toView(Favorite f) {

        if(f == null) {
            return null;
        }

        return new FavoriteView(
                f.getId(),
                UserConverter.toView(f.getUser()),
                ReportConverter.toView(f.getReport()),
                f.getCreatedAt(),
                f.getCreatedAt());
    }

    /**
     * DTOモデルのリストからViewモデルのリストを作成する
     * @param list DTOモデルのリスト
     * @return Viewモデルのリスト
     */
    public static List<FavoriteView> toViewList(List<Favorite> list){
        List<FavoriteView> evs = new ArrayList<>();

        for(Favorite f : list) {
            evs.add(toView(f));
        }

        return evs;
    }

    /**
     * Viewモデルの全フィールドの内容をDTOモデルのフィールドにコピーする
     * @param f DTOモデル（コピー先）
     * @param fv Viewモデル（コピー元）
     */
    public static void copyViewToModel(Favorite f, FavoriteView fv) {
        f.setId(fv.getId());
        f.setUser(UserConverter.toModel(fv.getUser()));
        f.setReport(ReportConverter.toModel(fv.getReport()));
        f.setCreatedAt(fv.getCreatedAt());
        f.setUpdatedAt(fv.getUpdatedAt());
    }
}
