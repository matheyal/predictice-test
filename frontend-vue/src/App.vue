<template>
  <div id="app" class="container">
    <nav class="btn btn-group">
      <router-link class="btn btn-primary" to="/">
        <font-awesome-icon icon="users"/>
        Customers
      </router-link>
      <router-link class="btn btn-primary" to="/albums">
        <font-awesome-icon icon="compact-disc"/>
        Albums
      </router-link>
      <router-link class="btn btn-primary" to="/add">
        <font-awesome-icon icon="user-plus"/>
        Add
      </router-link>
      <router-link class="btn btn-primary" to="/search">
        <font-awesome-icon icon="search"/>
        Search
      </router-link>
    </nav>
    <hr class="hr"/>
    <br/>
    <router-view/>
    <footer>
      <hr class="hr"/>
      <font-awesome-icon icon="code"/>
      with
      <font-awesome-icon icon="heart"/>
      by Alexis Mathey
    </footer>
  </div>
</template>


<script>

  export default {
    name: "app",
  };
</script>

<style>

  body {
    padding-top: 20px;
  }

  .hr {
    background-color: #11B7D9;
  }

  .btn-primary {
    margin-right: 5px;
    max-width: 100%;
    height: auto;
    width: auto \9; /* ie8 */
  }

  img {
    max-width: 100%;
    height: auto;
    width: auto \9; /* ie8 */
  }

  .btn-info {
    margin-right: 10px;
  }

  .container-fluid {
    text-align: center;
  }
</style>
