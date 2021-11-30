<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="constants.ForwardConst" %>

<c:set var="actRep" value="${ForwardConst.ACT_REP.getValue()}" />
<c:set var="actFav" value="${ForwardConst.ACT_FAV.getValue()}" />

<c:set var="commShow" value="${ForwardConst.CMD_SHOW.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />

<c:import url="../layout/app.jsp">
    <c:param name="content">
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>
        <h2><c:out value="${user.name}" />&nbsp;さんのお気に入り投稿一覧</h2>


        <c:choose>
            <c:when test="${favorites_count == 0}">
                <br /><h3>お気に入り投稿はありません。</h3><br />
            </c:when>
            <c:otherwise>
                <br />お気に入り投稿件数: ${favorites_count} 件
                <table id="report_list">
                    <tbody>
                        <tr>
                            <th class="report_name">名前</th>
                            <th class="report_title">タイトル</th>
                            <th class="report_create_at">投稿日時</th>
                            <th class="report_action">操作</th>
                        </tr>
                        <c:forEach var="favorite" items="${favorites}" varStatus="status">
                            <fmt:parseDate value="${favorite.createdAt}" pattern="yyyy-MM-dd'T'HH:mm:ss" var="createDay" type="date" />
                            <tr class="row${status.count % 2}">
                                <td class="favorite_name"><c:out value="${favorite.report.user.name}" /></td>
                                <td class="favorite_title">${favorite.report.title}</td>
                                <td class="favorite_create_at"><fmt:formatDate value='${createDay}' pattern="yyyy-MM-dd HH:mm:ss" /></td>
                                <td class="favorite_action"><a href="<c:url value='?action=${actRep}&command=${commShow}&id=${favorite.report.id}' />">詳細を見る</a></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>
        <div id="pagination">
            （全 ${favorites_count} 件）<br />
            <c:forEach var="i" begin="1" end="${((favorites_count - 1) / maxRow) + 1}" step="1">
                <c:choose>
                    <c:when test="${i == page}">
                        <c:out value="${i}" />&nbsp;
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value='?action=${actFav}&command=${commIdx}&page=${i}' />"><c:out value="${i}" /></a>&nbsp;
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
    </c:param>
</c:import>