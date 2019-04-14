<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container-fluid">
    <h2 class="display-3">Search Archive</h2>
    <div class="form-group">
        <form class="mx-auto" style="width: 700px" action="${pageContext.request.contextPath}/searchArchive" method="post" enctype="multipart/form-data">
            <label for="search">Select Search Filter:</label>
            <select class="form-control" name="search" id="search">
                <option value="all">All</option>
                <option value="collectionName">Collection Name</option>
                <option value="collectionDescription">Collection Description</option>
                <option value="fileName">File Name</option>
                <option value="creator">Creator</option>
                <option value="subject">Subject</option>
                <option value="fileDescription">File Description</option>
                <option value="publisher">Publisher</option>
                <option value="contributor">Contributor</option>
                <option value="date">Date</option>
                <option value="identifier">Identifier</option>
                <option value="source">Source</option>
                <option value="language">Language</option>
                <option value="coverage">Coverage</option>
                <option value="rights">Rights</option>
            </select>

            <label for="searchFilter">Enter Search:</label>
            <input type="text" class="form-control" name="searchFilter" id="searchFilter" placeholder="Type what you would like to search...">

            <button class="button-orange button-hover">Search</button>
        </form>
    </div>
</div>