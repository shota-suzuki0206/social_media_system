<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="constants.AttributeConst" %>
<%@ page import="constants.ForwardConst" %>

<c:set var="action" value="${ForwardConst.ACT_USE.getValue()}" />

<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commUpd" value="${ForwardConst.CMD_UPDATE.getValue()}" />
<c:set var="commDel" value="${ForwardConst.CMD_DESTROY.getValue()}" />
<c:set var="commShow" value="${ForwardConst.CMD_SHOW.getValue()}" />

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">

        <h2>ユーザー情報 編集ページ</h2>

        <p>（パスワードは変更する場合のみ入力してください）</p>

        <form method="POST"
            action="<c:url value='?action=${action}&command=${commUpd}' />">
            <c:if test="${errors != null}">
                <div id="flush_error">
                    入力内容にエラーがあります。<br />
                    <c:forEach var="error" items="${errors}">
                        ・<c:out value="${error}" /><br />
                    </c:forEach>

                </div>
            </c:if>
            <label for="${AttributeConst.USE_NAME.getValue()}">名前</label><br />
            <input class="form-control" type="text" name="${AttributeConst.USE_NAME.getValue()}" value="${user.name}" />
            <br /><br />


            <label for="${AttributeConst.USE_EMAIL.getValue()}">メールアドレス</label><br />
            <input class="form-control" type="text" name="${AttributeConst.USE_EMAIL.getValue()}" value="${user.email}" />
            <br /><br />

            <label for="${AttributeConst.USE_PASS.getValue()}">パスワード</label><br />
            <input class="form-control" type="password" name="${AttributeConst.USE_PASS.getValue()}" />
            <br /><br />

            <label for="${AttributeConst.USE_REPASS.getValue()}">パスワード(確認用)</label><br />
            <input class="form-control" type="password" name="${AttributeConst.USE_REPASS.getValue()}" >
            <br /><br />

            <input type="hidden" name="${AttributeConst.USE_ID.getValue()}" value="${user.id}" />
            <input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}" />
            <button type="submit">更新</button>
        </form>

        <p>
            <a href="<c:url value='?action=${action}&command=${commShow}&id=${user.id}' />">詳細ページに戻る</a>
        </p>
    </c:param>
</c:import>