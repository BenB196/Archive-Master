<%@ page import="com.archivemaster.fedora.Collection" %>
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>
<h2 class="text-primary display-4">Manage Your Archive Collections</h2>
<div class="flex-container">
  <a  href="./#!/add-collection"><button class="button-orange button-hover">Add Collection</button></a>
  <!--<a  href="./#!/search-collections"><button class="button-orange button-hover">Edit</button></a>-->
</div>

<div>
  <form action="${pageContext.request.contextPath}/editCollection" method="post" enctype="multipart/form-data">
    <table class="table table-striped">
      <thead class="thead-dark">
        <tr class="table-primary">
          <th scope="col">Name</th>
          <th scope="col">Description</th>
          <th scope="col">Edit</th>
        </tr>
      </thead>
      <tbody>
      <c:forEach items="<%=Collection.getCollections()%>" var="collection">
        <tr>
          <td>${collection.name}</td>
          <td>${collection.description}</td>
          <td><button class="button-orange button-hover" type="submit" name="editCollection" value="${collection.name}">Edit</button></td>
        </tr>
      </c:forEach>
      </tbody>
    </table>
  </form>
</div>
