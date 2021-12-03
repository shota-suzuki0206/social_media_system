<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="constants.ForwardConst" %>

<c:set var="actFlw" value="${ForwardConst.ACT_FLW.getValue()}" />
<c:set var="actUse" value="${ForwardConst.ACT_USE.getValue()}" />
<c:set var="actRep" value="${ForwardConst.ACT_REP.getValue()}" />

<c:set var="commShow" value="${ForwardConst.CMD_SHOW.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />

 <c:import url="../layout/app.jsp">
    <c:param name="content">
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>
        <h2>タイムライン</h2>

        <c:choose>
            <c:when test="${reports_count == 0}">
                <br /><h3>まだ投稿されていません。</h3><br />
            </c:when>
            <c:otherwise>
                タイムライン件数:${reports_count}件
                <table id="report_list">
                    <tbody>
                        <tr>
                            <th class="report_name">名前</th>
                            <th class="report_title">タイトル</th>
                            <th class="report_created_at">投稿日時</th>
                            <th class="report_action">操作</th>
                        </tr>
                        <c:forEach var="report" items="${report}" varStatus="status">
                            <fmt:parseDate value="${report.createdAt}" pattern="yyyy-MM-dd'T'HH:mm:ss" var="createDay" type="date" />

                            <tr class="row${status.count % 2}">
                                <td class="report_name"><a href="<c:url value='?action=${actUse}&command=${commShow}&id=${report.user.id}' />"><c:out value="${report.user.name}" /></a></td>
                                <td class="report_title">${report.title}</td>
                                <td class="report_created_at"><fmt:formatDate value='${createDay}' pattern="yyyy-MM-dd HH:mm:ss" /></td>
                                <td class="report_action"><a href="<c:url value='?action=${actRep}&command=${commShow}&id=${report.id}' />">詳細を見る</a></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>
    </c:param>
</c:import>