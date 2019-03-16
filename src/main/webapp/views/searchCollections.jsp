<%@ page import="com.archivemaster.fedora.Collection" %>
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container-fluid">
  <h2 class="display-3">Collections</h2>
  <div class="form-group">
    <label for="collection">Select Collection to Edit:</label>
    <select class="form-control" id="collection">
      <c:forEach items="<%=Collection.getCollections()%>" var="collection">
        <option value="${collection.name}">${collection.name}</option>
      </c:forEach>
    </select>
  </div>
  <button class="button-orange button-hover">Select</button>

  <br>
  <h2>Delete File from Collection</h2>

  <form action="${pageContext.request.contextPath}/fedora" method="post">
    <input type="text" name="collectionName"><br>
    <input type="text" name="fileName"><br>
    <input type="submit" name="submit" value="Delete File">
  </form>
</div>
