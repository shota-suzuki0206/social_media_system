<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="constants.ForwardConst"%>

<c:set var="actRep" value="${ForwardConst.ACT_REP.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commEdt" value="${ForwardConst.CMD_EDIT.getValue()}" />

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <h2>投稿 詳細ページ</h2>

        <table id="report_list">
            <tbody>
                <tr>
                    <th class="show_name">名前</th>
                    <th class="show_title">タイトル</th>
                    <th class="show_content">内容</th>
                    <th class="show_create_at">投稿日時</th>
                    <th class="show_update_at">最終更新日時</th>
                </tr>
                <tr>
                    <td><c:out value="${report.user.name}" /></td>
                    <td><c:out value="${report.title}" /></td>
                    <td><pre><c:out value="${report.content}" /></pre></td>

                    <fmt:parseDate value="${report.createdAt}" pattern="yyyy-MM-dd'T'HH:mm:ss" var="createDay" type="date" />
                    <td><fmt:formatDate value="${createDay}" pattern="yyyy-MM-dd HH:mm:ss" /></td>

                    <fmt:parseDate value="${report.updatedAt}" pattern="yyyy-MM-dd'T'HH:mm:ss" var="updateDay" type="date" />
                    <td><fmt:formatDate value="${updateDay}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                </tr>
            </tbody>
        </table>

        <c:if test="${sessionScope.login_user.id == report.user.id}">
            <p>
                <a href="<c:url value='?action=${actRep}&command=${commEdt}&id=${report.id}' />">この投稿を編集する</a>
            </p>
        </c:if>

    </c:param>
</c:import>
