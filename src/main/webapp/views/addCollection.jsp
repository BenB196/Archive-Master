<div class="text-center text-primary">
  <h1 class="display-4">Add New Collection to Archive</h1>
  <hr />
  <h3 style="color:#ff9900">Collections allow you to group related files</h3>
</div>

<form class="mx-auto" style="width: 600px" action="${pageContext.request.contextPath}/addCollection" method="post" enctype="multipart/form-data">
  <div class="form-group">
    <label for="title">Title</label>
    <input id="title" name="title" type="text" class="form-control" placeholder="Give your collection a name.">
  </div>
  <div class="form-group">
    <label for="description">Description</label>
    <input id="description" name="description" type="text" class="form-control" placeholder="What's in your collection?">
  </div>
  <button class="button-orange button-hover">Submit</button>
</form>
