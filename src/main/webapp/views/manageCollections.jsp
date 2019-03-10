<%@ page import="com.archivemaster.fedora.Collection" %>
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>
<h2 class="text-primary display-4">Manage Your Archive</h2>
<div class="flex-container">
  <a  href="./#!/add-collection"><button class="button-orange button-hover">Add</button></a>
  <button class="button-orange button-hover">Edit</button>
</div>

<div>
  <table class="table table-striped">
    <thead class="thead-dark">
      <tr class="table-primary">
        <th scope="col">Name</th>
        <th scope="col">Description</th>
      </tr>
    </thead>
    <!-- TODO: Delete test rows -->
    <tbody>
    <c:forEach items="<%=Collection.getCollections()%>" var="collection">
      <tr>
        <td>${collection.name}</td>
        <td>${collection.description}</td>
      </tr>
    </c:forEach>
    </tbody>
  </table>
</div>
