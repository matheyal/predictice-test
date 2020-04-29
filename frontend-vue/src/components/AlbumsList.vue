<template>
  <div>
    <h4>Albums List</h4>
    <div class="row">
      <div class="col-md-6">
        <div class="row my-2">
          <b-form-input v-model="query" v-on:input="search($event)"
                        placeholder="Search an album"></b-form-input>
        </div>
        <div v-if="albums && albums.length > 0">
          <div class="row albums-list">
            <ul>
              <ol v-for="(album, index) in albums" :key="index" class="my-1">
                <font-awesome-icon icon="compact-disc"/>
                {{album.title}}
              </ol>
            </ul>
          </div>
          <div class="row">
            <b-pagination
              v-model="page"
              align="center"
              :total-rows="totalElements"
              :per-page="pageSize"
              v-on:change="retrieveAlbums(query, $event)"
            ></b-pagination>
          </div>
        </div>
      </div>
      <div class="col-md-6">
        <router-view @refreshData="refreshList"></router-view>
      </div>
    </div>
  </div>
</template>

<script>
  import http from "../http-common";
  import _ from "lodash";

  export default {
    name: "albums-list",
    data() {
      return {
        albums: [],
        query: "",
        page: 1,
        totalElements: 1,
        pageSize: 10,
        loading: false
      };
    },
    created() {
      this.search = _.debounce((query) => {
        this._search(query)
      }, 500);
    },
    methods: {
      retrieveAlbums(query, page) {
        this.loading = true;
        let data = {
          query: query || "",
          page: (page && page > 1) ? page - 1 : 0
        }
        http.post("/albums/search", data)
          .then(response => {
            this.albums = response.data.content;
            this.totalElements = response.data.totalElements;
          })
          .catch(e => {
            console.error(e);
          })
          .finally(() => {
            this.loading = false;
          });
      },
      _search(query) {
        if (query.length > 0 && query.length <= 2) {
          return;
        }
        this.page = 1;
        console.log(query);
        this.retrieveAlbums(query, this.page);
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
