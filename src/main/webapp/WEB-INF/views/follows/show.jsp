<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="constants.ForwardConst" %>

<c:set var="actFlw" value="${ForwardConst.ACT_FLW.getValue()}" />
<c:set var="actUse" value="${ForwardConst.ACT_USE.getValue()}" />

<c:set var="commShow" value="${ForwardConst.CMD_SHOW.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />

 <c:import url="../layout/app.jsp">
    <c:param name="content">
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>
        <h2><c:out value="${user.name}" />&nbsp;さんのフォロワー一覧</h2>

        <c:choose>
            <c:when test="${followers_count == 0}">
                <br /><h3>フォロワーはいません。</h3><br />
            </c:when>
            <c:otherwise>
                フォロワー人数: ${followers_count} 件
                <table id="user_list">
                    <tbody>
                        <tr>
                            <th class="user_id">ID</th>
                            <th class="user_name">名前</th>
                            <th class="user_email">メールアドレス</th>
                            <th class="user_create_at">登録日時</th>
                            <th class="user_action">操作</th>
                        </tr>
                        <c:forEach var="follower" items="${followers}" varStatus="status">
                            <fmt:parseDate value="${follower.follow.createdAt}" pattern="yyyy-MM-dd'T'HH:mm:ss" var="createDay" type="date" />
                            <tr class="row${status.count % 2}">
                                <td class="user_id"><c:out value="${follower.follow.id}" /></td>
                                <td class="user_name"><c:out value="${follower.follow.name}" /></td>
                                <td class="user_email"><c:out value="${follower.follow.email}" /></td>
                                <td class="user_create_at"><fmt:formatDate value='${createDay}' pattern="yyyy-MM-dd HH:mm:ss" /></td>
                                <td class="user_action"><a href="<c:url value='?action=${actUse}&command=${commShow}&id=${follower.follow.id}' />">詳細画面へ移動</a></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <div id="pagination">
                    （全 ${followers_count} 件）<br />
                    <c:forEach var="i" begin="1" end="${((followers_count - 1) / maxRow) + 1}" step="1">
                        <c:choose>
                            <c:when test="${i == page}">
                                <c:out value="${i}" />&nbsp;
                            </c:when>
                            <c:otherwise>
                                <a href="<c:url value='?action=${actFlw}&command=${commShow}&page=${i}' />"><c:out value="${i}" /></a>&nbsp;
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </div>
            </c:otherwise>
        </c:choose>
    </c:param>
</c:import>