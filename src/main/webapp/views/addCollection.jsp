<div class="text-center text-primary">
  <h1 class="display-4">Add a New Collection to the Archive</h1>
  <hr />
  <h3 style="color:#ff9900">Collections Allow you to Group Related Files</h3>
</div>

<form class="mx-auto" style="width: 600px" action="${pageContext.request.contextPath}/addCollection" method="post" enctype="multipart/form-data">
  <div class="form-group">
    <label for="title">Title</label>
    <input id="title" name="title" type="text" class="form-control" placeholder="Give your collection a name." required>
  </div>
  <div class="form-group">
    <label for="description">Description</label>
    <input id="description" name="description" type="text" class="form-control" placeholder="What's in your collection?" required>
  </div>
  <button class="button-orange button-hover">Create Collection</button>
</form>

<a  href="./"><button class="button-orange button-hover">Go Home</button></a>
