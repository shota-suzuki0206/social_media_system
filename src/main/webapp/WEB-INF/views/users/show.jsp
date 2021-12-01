<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="constants.ForwardConst" %>
<%@ page import="constants.AttributeConst"%>

<c:set var="actRep" value="${ForwardConst.ACT_REP.getValue()}" />
<c:set var="actFav" value="${ForwardConst.ACT_FAV.getValue()}" />
<c:set var="actFlw" value="${ForwardConst.ACT_FLW.getValue()}" />

<c:set var="commShow" value="${ForwardConst.CMD_SHOW.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commCrt" value="${ForwardConst.CMD_CREATE.getValue()}" />
<c:set var="commDel" value="${ForwardConst.CMD_DESTROY.getValue()}" />

<c:import url="../layout/app.jsp">
    <c:param name="content">
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>

        <h2><c:out value="${user.name}" />&nbsp;さんのマイページ</h2><br />

        <c:choose>
            <c:when test="${follow_flag == false}" >
                <form action="<c:url value='?action=${actFlw}&command=${commCrt}' />" method="POST">
                    <input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}" />
                    <input type="submit" value="フォロー">
                </form>
            </c:when>
            <c:otherwise>
                <form action="<c:url value='?action=${actFlw}&command=${commDel}' />" method="POST">
                    <input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}" />
                    <input type="submit" value="フォロー解除">
                </form>
            </c:otherwise>
        </c:choose>

        <div class="my_page_list">
            <p><a href="<c:url value='?action=${actFav}&command=${commIdx}&id=${user.id}' />">お気に入り投稿一覧(${favorites_count}件)</a></p>
            <p><a href="<c:url value='?action=${actFlw}&command=${commIdx}&id=${user.id}' />">フォロー一覧(${follows_count}人)</a></p>
            <p>フォロワー一覧</p>
        </div>

        <br />
        <h3><c:out value="${user.name}" />&nbsp;さんの投稿一覧</h3>
        <c:choose>
            <c:when test="${reports_count == 0}">
                <br /><h3>投稿はありません。</h3><br />
            </c:when>
            <c:otherwise>
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
            </c:otherwise>
        </c:choose>
    </c:param>
</c:import>