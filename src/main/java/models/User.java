package models;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import constants.JpaConst;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * ユーザーデータのDTOモデル
 *
 */
@Table(name = JpaConst.TABLE_USE)
@NamedQueries({
    @NamedQuery(
            name =JpaConst.Q_USE_GET_ALL,
            query =JpaConst.Q_USE_GET_ALL_DEF),
    @NamedQuery(
            name =JpaConst.Q_USE_COUNT,
            query =JpaConst.Q_USE_COUNT_DEF),
    @NamedQuery(
            name =JpaConst.Q_USE_GET_BY_EMAIL_AND_PASS,
            query =JpaConst.Q_USE_GET_BY_EMAIL_AND_PASS_DEF),
    @NamedQuery(
            name =JpaConst.Q_USE_COUNT_RESISTERED_BY_EMAIL,
            query =JpaConst.Q_USE_COUNT_RESISTERED_BY_EMAIL_DEF),
})

@Getter //全てのクラスフィールドについてgetterを自動生成する(Lombok)
@Setter //全てのクラスフィールドについてsetterを自動生成する(Lombok)
@NoArgsConstructor //引数なしコンストラクタを自動生成する(Lombok)
@AllArgsConstructor //全てのクラスフィールドを引数にもつ引数ありコンストラクタを自動生成する(Lombok)
@Entity
public class User {

    /**
     * id
     */
    @Id
    @Column(name = JpaConst.USE_COL_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 名前
     */
    @Column(name = JpaConst.USE_COL_NAME, nullable = false)
    private String name;

    /**
     * メールアドレス
     */
    @Column(name = JpaConst.USE_COL_EMAIL, nullable = false, unique = true)
    private String email;

    /**
     * パスワード
     */
    @Column(name = JpaConst.USE_COL_PASS, length = 64, nullable = false)
    private String password;

    /**
     * 登録日時
     */
    @Column(name = JpaConst.USE_COL_CREATED_AT, nullable = false)
    private LocalDateTime createdAt;

    /**
     * 更新日時
     */
    @Column(name = JpaConst.USE_COL_UPDATED_AT, nullable = false)
    private LocalDateTime updatedAt;

    /**
     * 削除された従業員かどうか
     */
    @Column(name = JpaConst.USE_COL_DELETE_FLAG, nullable = false)
    private Integer deleteFlag;
}
