<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--
  ~ menu.jsp
  ~ This file was last modified at 2019-02-16 12:25 by Victor N. Skurikhin.
  ~ $Id$
  ~ This is free and unencumbered software released into the public domain.
  ~ For more information, please refer to <http://unlicense.org>
  --%>

<c:set var="req" value="${pageContext.request}" />
<c:set var="baseURL" value="${req.scheme}://${req.serverName}:${req.serverPort}${req.contextPath}" />
        <tr>
            <td class="tg-0lax" colspan="2">
                <nav class="menu">
                    <ul>
                        <li><a id="navigation-menu-0" href="<c:out value="${baseURL}/"/>admins/index.jsp">«Домашняя страница»</a></li>
                        <li><a id="navigation-menu-1" href="<c:out value="${baseURL}/"/>admins/incidents.jsp">«Консоль инцидентов»</a></li>
                        <li><a id="navigation-menu-2" href="<c:out value="${baseURL}/"/>admins/tasks.jsp">«Консоль задач»</a></li>
                        <li><a id="navigation-menu-3" href="<c:out value="${baseURL}/"/>admins/index-cmdb.jsp">«Консоль CMDB»</a></li>
                        <li><a id="navigation-menu-4" href="<c:out value="${baseURL}/"/>logout.do">Выход</a></li>
                    </ul>
                </nav>
            </td>
        </tr>
