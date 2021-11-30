<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="constants.ForwardConst" %>
<%@ page import="constants.AttributeConst"%>

<c:set var="actRep" value="${ForwardConst.ACT_REP.getValue()}" />
<c:set var="commShow" value="${ForwardConst.CMD_SHOW.getValue()}" />

<c:import url="../layout/app.jsp">
    <c:param name="content">
        <h2><c:out value="${user.name}" />&nbsp;さんのマイページ</h2><br />



        <br />
        <h3><c:out value="${user.name}" />&nbsp;さんの投稿一覧</h3>
        <table id="report_list">
            <tbody>
                <tr>
                    <th class="report_name">名前</th>
                    <th class="report_title">タイトル</th>
                    <th class="report_create_at">投稿日時</th>
                    <th class="report_action">操作</th>
                </tr>
                <c:forEach var="report" items="${reports}" varStatus="status">
                    <fmt:parseDate value="${report.createdAt}" pattern="yyyy-MM-dd'T'HH:mm:ss" var="createDay" type="date" />
                    <tr class="row${status.count % 2}">
                        <td class="report_name"><c:out value="${report.user.name}" /></td>
                        <td class="report_title">${report.title}</td>
                        <td class="report_create_at"><fmt:formatDate value='${createDay}' pattern="yyyy-MM-dd HH:mm:ss" /></td>
                        <td class="report_action"><a href="<c:url value='?action=${actRep}&command=${commShow}&id=${report.id}' />">詳細を見る</a></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <div id="pagination">
            （全 ${reports_count} 件）<br />
            <c:forEach var="i" begin="1" end="${((reports_count - 1) / maxRow) + 1}" step="1">
                <c:choose>
                    <c:when test="${i == page}">
                        <c:out value="${i}" />&nbsp;
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value='?action=${actRep}&command=${commIdx}&page=${i}' />"><c:out value="${i}" /></a>&nbsp;
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
    </c:param>
</c:import>