package models;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import constants.JpaConst;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * フォローデータのDTOモデル
 *
 */
@Table(name = JpaConst.TABLE_FLW)
@NamedQueries({
    @NamedQuery(
            name =  JpaConst.Q_FLW_GET_ALL_MINE,
            query = JpaConst.Q_FLW_GET_ALL_MINE_DEF),
    @NamedQuery(
            name =  JpaConst.Q_FLW_COUNT_ALL_MINE,
            query = JpaConst.Q_FLW_COUNT_ALL_MINE_DEF),
    @NamedQuery(
            name =  JpaConst.Q_FLW_GET_BY_FOLLOW_AND_FOLLOWER,
            query = JpaConst.Q_FLW_GET_BY_FOLLOW_AND_FOLLOWER_DEF),
    @NamedQuery(
            name =  JpaConst.Q_FLW_COUNT_BY_FOLLOW_AND_FOLLOWER,
            query = JpaConst.Q_FLW_COUNT_BY_FOLLOW_AND_FOLLOWER_DEF),
    @NamedQuery(
            name =  JpaConst.Q_FLW_GET_FLW_MINE,
            query = JpaConst.Q_FLW_GET_FLW_MINE_DEF),
    @NamedQuery(
            name =  JpaConst.Q_FLW_COUNT_FLW_MINE,
            query = JpaConst.Q_FLW_COUNT_FLW_MINE_DEF)

})

@Getter //全てのクラスフィールドについてgetterを自動生成する(Lombok)
@Setter //全てのクラスフィールドについてsetterを自動生成する(Lombok)
@NoArgsConstructor //引数なしコンストラクタを自動生成する(Lombok)
@AllArgsConstructor //全てのクラスフィールドを引数にもつ引数ありコンストラクタを自動生成する(Lombok)
@Entity
public class Follow {

    /**
     * id
     */
    @Id
    @Column(name = JpaConst.FLW_COL_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * フォローしたユーザーのid
     */
    @ManyToOne
    @JoinColumn(name = JpaConst.FLW_COL_FOLLOW, nullable = false)
    private User follow;

    /**
     * フォローされたユーザーのid
     */
    @ManyToOne
    @JoinColumn(name = JpaConst.FLW_COL_FOLLOWER, nullable = false)
    private User follower;

    /**
     * 登録日時
     */
    @Column(name = JpaConst.FAV_COL_CREATED_AT, nullable = false)
    private LocalDateTime createdAt;

    /**
     * 更新日時
     */
    @Column(name = JpaConst.FAV_COL_UPDATED_AT, nullable = false)
    private LocalDateTime updatedAt;
}
