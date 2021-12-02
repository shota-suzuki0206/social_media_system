<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="constants.ForwardConst" %>
<%@ page import="constants.AttributeConst" %>

<c:set var="actTop" value="${ForwardConst.ACT_TOP.getValue()}" />
<c:set var="actUse" value="${ForwardConst.ACT_USE.getValue()}" />
<c:set var="actRep" value="${ForwardConst.ACT_REP.getValue()}" />
<c:set var="actAuth" value="${ForwardConst.ACT_AUTH.getValue()}" />
<c:set var="actFav" value="${ForwardConst.ACT_FAV.getValue()}" />
<c:set var="actFlw" value="${ForwardConst.ACT_FLW.getValue()}" />

<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commOut" value="${ForwardConst.CMD_LOGOUT.getValue()}" />
<c:set var="commShow" value="${ForwardConst.CMD_SHOW.getValue()}" />
<c:set var="commNew" value="${ForwardConst.CMD_NEW.getValue()}" />
<c:set var="commTime" value="${ForwardConst.CMD_TIMELINE.getValue()}" />

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
    <title><c:out value="会員制投稿サイト" /></title>
    <link rel="stylesheet" href="<c:url value='/css/reset.css' />">
    <link rel="stylesheet" href="<c:url value='/css/style.css' />">
</head>
<body>
    <div id="wrapper">
        <div id="header">
            <div id="header_menu">
                <h1><a href="<c:url value='/?action=${actTop}&command=${commIdx}' />">会員制投稿サイト</a></h1>&nbsp;&nbsp;&nbsp;
                <a href="<c:url value='?action=${actUse}&command=${commShow}&id=${login_user.id}' />">マイページ</a>&nbsp;
                <a href="<c:url value='?action=${actRep}&command=${commNew}' />">新規投稿</a>&nbsp;
                <a href="<c:url value='?action=${actFlw}&command=${commTime}' />">タイムライン</a>&nbsp;
                <a href="<c:url value='?action=${actFav}&command=${commIdx}&id=${login_user.id}' />">お気に入り投稿一覧</a>&nbsp;
                <a href="<c:url value='?action=${actUse}&command=${commIdx}' />">ユーザー一覧</a>&nbsp;
                <a href="<c:url value='?action=${actFlw}&command=${commIdx}&id=${login_user.id}' />">フォロー一覧</a>&nbsp;
                <a href="<c:url value='?action=${actFlw}&command=${commShow}&id=${login_user.id}' />">フォロワー一覧</a>&nbsp;
            </div>
            <div id="user_name">
                <a href="<c:url value='?action=${actAuth}&command=${commOut}' />">ログアウト</a>
            </div>
        </div>
        <div id="content">${param.content}</div>

    </div>
</body>
</html>
