<%@ page contentType="text/html; charset=UTF-8" %>

<%--
  ~ aside.jsp
  ~ This file was last modified at 2019-02-16 13:08 by Victor N. Skurikhin.
  ~ $Id$
  ~ This is free and unencumbered software released into the public domain.
  ~ For more information, please refer to <http://unlicense.org>
  --%>

<c:set var="req" value="${pageContext.request}" />
<c:set var="baseURL" value="${req.scheme}://${req.serverName}:${req.serverPort}${req.contextPath}" />
                <aside class = "w3-col w3-margin-0 my-left-aside">
                    <ul>
                        <ul id="asideList"></ul>
                    </ul>
                </aside>
