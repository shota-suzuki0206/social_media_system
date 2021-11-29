<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="constants.ForwardConst"%>
<%@ page import="constants.AttributeConst"%>

<c:set var="actRep" value="${ForwardConst.ACT_REP.getValue()}" />
<c:set var="actFav" value="${ForwardConst.ACT_FAV.getValue()}" />
<c:set var="actCom" value="${ForwardConst.ACT_COM.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commEdt" value="${ForwardConst.CMD_EDIT.getValue()}" />
<c:set var="commCrt" value="${ForwardConst.CMD_CREATE.getValue()}" />
<c:set var="commDel" value="${ForwardConst.CMD_DESTROY.getValue()}" />

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">

        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>

        <c:if test="${errors != null}">
            <div id="flush_error">
                入力内容にエラーがあります。<br />
                <c:forEach var="error" items="${errors}">
                ・<c:out value="${error}" />
                    <br />
                </c:forEach>

            </div>
        </c:if>
        <h2>投稿 詳細ページ</h2>

        <table id="report_list">
            <tbody>
                <tr>
                    <th class="show_name">名前</th>
                    <th class="show_title">タイトル</th>
                    <th class="show_content">内容</th>
                    <th class="show_created_at">投稿日時</th>
                    <th class="show_updated_at">最終更新日時</th>
                    <th class="show_favorite_count">いいねの数</th>
                    <th class="show_favorite_list">いいねした人の一覧</th>
                </tr>
                <tr>
                    <td><c:out value="${report.user.name}" /></td>
                    <td><c:out value="${report.title}" /></td>
                    <td><pre><c:out value="${report.content}" /></pre></td>

                    <fmt:parseDate value="${report.createdAt}"
                        pattern="yyyy-MM-dd'T'HH:mm:ss" var="createDay" type="date" />
                    <td><fmt:formatDate value="${createDay}"
                            pattern="yyyy-MM-dd HH:mm:ss" /></td>

                    <fmt:parseDate value="${report.updatedAt}"
                        pattern="yyyy-MM-dd'T'HH:mm:ss" var="updateDay" type="date" />
                    <td><fmt:formatDate value="${updateDay}"
                            pattern="yyyy-MM-dd HH:mm:ss" /></td>
                    <td><c:out value="${favorites_count}" />人</td>
                    <td>
                        <ul>
                            <c:forEach var="favorite" items="${favorites}">
                            <li><c:out value="${favorite.user.name}"/></li>
                            </c:forEach>
                        </ul>
                    </td>
                </tr>
            </tbody>
        </table><br />

                    <form action="<c:url value='?action=${actFav}&command=${commCrt}' />" method="POST">
                        <input type="hidden" name="id" value="${report.id}">
                        <input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}" />
                        <input type="submit" value="いいね！">
                    </form>
                    <form action="<c:url value='?action=${actFav}&command=${commDel}' />" method="POST">
                        <input type="hidden" name="id" value="${report.id}">
                        <input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}" />
                        <input type="submit" value="いいね解除">
                    </form><br />

        <c:if test="${sessionScope.login_user.id == report.user.id}">
            <p>
                <a
                    href="<c:url value='?action=${actRep}&command=${commEdt}&id=${report.id}' />">この投稿を編集する</a>
            </p>
        </c:if>
        <br />
        <br />
        <br />

        <h3>コメント一覧</h3>
        <br />

        <form method="POST"
            action="<c:url value='/?action=${actCom}&command=${commCrt}' />">
            <label for="${AttributeConst.COM_CONTENT.getValue()}">コメント</label><br />
            <textarea name="${AttributeConst.COM_CONTENT.getValue()}" rows="3"
                cols="50">${comment.content}</textarea>
            <br /><input type="hidden" name="id" value="${report.id}">
            <input type="hidden" name="${AttributeConst.TOKEN.getValue()}"
                value="${_token}" />
            <button type="submit">投稿</button>
            <br />
            <br />
        </form>

        <c:choose>
            <c:when test="${comments_count == 0}">
                <p>まだ、コメントはありません。</p>
            </c:when>
            <c:otherwise>
                <table id="comment_list">
                    <tbody>
                        <tr>
                            <th class="commennt_name">名前</th>
                            <th class="comment_content">内容</th>
                            <th class="comment_create_at">投稿日時</th>
                        </tr>
                        <c:forEach var="comment" items="${comments}" varStatus="status">
                            <fmt:parseDate value="${comment.createdAt}"
                                pattern="yyyy-MM-dd'T'HH:mm:ss" var="comCreate" type="date" />


                            <tr class="row${status.count % 2}">
                                <td class="commennt_name"><c:out
                                        value="${comment.user.name}" /></td>
                                <td class="comment_content"><pre><c:out value="${comment.content}" /></pre></td>
                                <td class="comment_create_at"><fmt:formatDate
                                        value="${comCreate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                            </tr>

                        </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>
    </c:param>
</c:import>
