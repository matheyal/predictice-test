<template>
  <div>
    <h4>Albums List</h4>
    <div class="row">
      <div class="col-md-6">
        <b-form-input v-model="query" placeholder="Search an album"></b-form-input>
      </div>
    </div>
    <div class="row">
      <div class="col-md-6">

        <ul>
          <ol v-for="(album, index) in albums" :key="index">
            <font-awesome-icon icon="compact-disc"/>
            {{album.title}}
          </ol>
        </ul>
      </div>
      <div class="col-md-6">
        <router-view @refreshData="refreshList"></router-view>
      </div>
    </div>
  </div>
</template>

<script>
  import http from "../http-common";

  export default {
    name: "albums-list",
    data() {
      return {
        albums: [],
        query: "",
        page: 0,
        totalPages: 1
      };
    },
    methods: {
      retrieveAlbums(query, page) {
        let data = {
          query: query || "",
          page: (page && page > 0) ? page : 0
        }
        http.post("/albums/search", data)
          .then(response => {
            console.log(response.data);
            this.albums = response.data.content;
            this.page = response.data.number;
            this.totalPages = response.data.totalPages;
          })
          .catch(e => {
            console.error(e);
          });
      },
      refreshList() {
        this.retrieveAlbums();
      }
    },
    mounted() {
      this.retrieveAlbums();
    }
  };
</script>

<style>
</style>
