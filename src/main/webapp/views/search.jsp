<%@ page import="com.archivemaster.fedora.Collection" %>
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container-fluid">
  <h2 class="display-3">Results</h2>
  <div class="form-group">
    <label for="sel1">Select list:</label>
    <select class="form-control" id="sel1">
      <c:forEach items="<%=Collection.getCollections()%>" var="collection">
        <option value="${collection.name}">${collection.name}</option>
      </c:forEach>
    </select>
  </div>
  <input type="text" placeholder="Search.."/>
  <button class ="button-orange">Search</button>
</div>
