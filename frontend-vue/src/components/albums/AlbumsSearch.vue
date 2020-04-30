<template>
  <div>
    <div class="row my-3">
      <b-form-input v-model="query" v-on:input="search($event)"
                    placeholder="Search an album"></b-form-input>
    </div>
    <div v-if="albums && albums.length > 0">
      <b-card-group columns class="row justify-content-center">
        <b-card
          v-for="album in albums"
          v-bind:key="album.id"
          :title="album.title"
          title-tag="h5"
          :img-src="album.coverURL"
          img-top
          class="album-card"
        >
          <b-card-text>
            {{ album.artist }}<br>
            {{ album.releaseYear }}
          </b-card-text>
          <div class="row justify-content-end">
            <b-dropdown size="sm" variant="outline-danger" no-caret>
              <template v-slot:button-content>
                <font-awesome-icon icon="trash"/>
              </template>
              <b-dropdown-item v-on:click="deleteAlbum(album.id)">Sure?</b-dropdown-item>
            </b-dropdown>
          </div>
        </b-card>
      </b-card-group>
      <div class="row justify-content-center">
        <b-pagination
          v-model="page"
          align="center"
          :total-rows="totalElements"
          :per-page="pageSize"
          v-on:change="retrieveAlbums(query, $event)"
        ></b-pagination>
      </div>
    </div>
    <div v-else class="row justify-content-center">
      <p class="my-5">Sorry, no results there...</p>
    </div>
  </div>
</template>

<script>
  import http from "../../http-common";
  import _ from "lodash";

  export default {
    name: "AlbumsSearch",
    data() {
      return {
        albums: [],
        query: "",
        page: 1,
        totalElements: 1,
        pageSize: 9,}
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
          page: (page && page > 1) ? page - 1 : 0,
          pageSize: this.pageSize
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
        this.retrieveAlbums(this.query, this.page);
      },
      deleteAlbum(id) {
        http.delete(`/albums/${id}`)
          .then(() => {
            this.refreshList()
          })
          .catch(e => {
            console.error(e);
          })
      }
    },
    mounted() {
      this.retrieveAlbums();
    }
  }
</script>

<style scoped>

</style>
