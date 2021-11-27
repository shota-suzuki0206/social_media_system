package models;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
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
 * コメントデータのDTOモデル
 *
 */
@Table(name = JpaConst.TABLE_COM)
@NamedQueries({
    @NamedQuery(
            name =  JpaConst.Q_COM_GET_ALL_MINE,
            query = JpaConst.Q_COM_GET_ALL_MINE_DEF),
    @NamedQuery(
            name =  JpaConst.Q_COM_COUNT_ALL_MINE,
            query = JpaConst.Q_COM_COUNT_ALL_MINE_DEF)

})

@Getter //全てのクラスフィールドについてgetterを自動生成する(Lombok)
@Setter //全てのクラスフィールドについてsetterを自動生成する(Lombok)
@NoArgsConstructor //引数なしコンストラクタを自動生成する(Lombok)
@AllArgsConstructor //全てのクラスフィールドを引数にもつ引数ありコンストラクタを自動生成する(Lombok)
@Entity
public class Comment {

    /**
     * id
     */
    @Id
    @Column(name = JpaConst.COM_COL_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * コメントを投稿したユーザー
     */
    @ManyToOne (fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = JpaConst.COM_COL_USE, nullable = false)
    private User user;

    /**
     * コメントをした投稿
     */
    @ManyToOne (fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = JpaConst.COM_COL_REP, nullable = false)
    private Report report;

    /**
     * コメント内容
     */
    @Lob
    @Column(name = JpaConst.COM_COL_CONTENT, nullable = false)
    private String content;

    /**
     * 登録日時
     */
    @Column(name = JpaConst.COM_COL_CREATED_AT, nullable = false)
    private LocalDateTime createdAt;

    /**
     * 更新日時
     */
    @Column(name = JpaConst.COM_COL_UPDATED_AT, nullable = false)
    private LocalDateTime updatedAt;


}
