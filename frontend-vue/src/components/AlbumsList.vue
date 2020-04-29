<template>
  <div>
    <div class="row justify-content-center">
      <div class="col-md-10">
        <div class="row justify-content-between">
          <h4>Albums List</h4>
          <b-button variant="primary" v-b-modal.add-album-modal>
            <font-awesome-icon icon="plus"/>
            Add album
          </b-button>
        </div>
        <div class="row my-2">
          <b-form-input v-model="query" v-on:input="search($event)"
                        placeholder="Search an album"></b-form-input>
        </div>
        <div v-if="albums && albums.length > 0">
          <b-card-group columns class="row">
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
      <div class="col-md-6">
        <router-view @refreshData="refreshList"></router-view>
      </div>
    </div>
    <!-- -->
    <b-modal id="add-album-modal" title="Add Album" @ok="handleOkModal">
      <b-form ref="addAlbumForm">
        <b-form-group id="title-group" label="Title:" label-for="title">
          <b-form-input
            id="title"
            type="text"
            required
            placeholder="Album title"
          ></b-form-input>
        </b-form-group>
        <b-form-group id="artist-group" label="Artist:" label-for="artist">
          <b-form-input
            id="artist"
            type="text"
            required
            placeholder="Artist"
          ></b-form-input>
        </b-form-group>
        <b-form-group id="releaseYear-group" label="Release Year:" label-for="releaseYear">
          <b-form-input
            id="releaseYear"
            type="number"
            required
            placeholder="Release year"
          ></b-form-input>
        </b-form-group>
        <b-form-group id="coverURL-group" label="Cover URL:" label-for="coverURL">
          <b-form-input
            id="coverURL"
            type="url"
            placeholder="Cover URL"
          ></b-form-input>
        </b-form-group>
      </b-form>
      <template v-slot:modal-footer="{ ok, cancel }">
        <!-- Emulate built in modal footer ok and cancel button actions -->
        <b-button @click="cancel()">
          Cancel
        </b-button>
        <b-button @click="ok()" variant="primary">
          Add
        </b-button>
      </template>
    </b-modal>
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
        pageSize: 9,
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
      },
      handleOkModal(bvModalEvt) {
        bvModalEvt.preventDefault();
        let form = this.$refs.addAlbumForm;
        // Check form validity
        console.log(form.elements["artist"].value);
        if (!form.checkValidity()) {
          form.reportValidity();
          return;
        }
        this.addAlbum({
          "title": form.elements["title"].value,
          "artist": form.elements["artist"].value,
          "releaseYear": form.elements["releaseYear"].value,
          "coverURL": form.elements["coverURL"].value,
        }).then(()=> {
          this.$bvModal.hide('add-album-modal');
        });
      },
      addAlbum(album) {
        console.log(album);
        return http.post("/albums", album)
          .then(() => {
            this.$toasted.show(
              "Album successfully created!",
              { duration: 3000, type: "success" }
              );
          })
          .catch(e => {
            console.error(e);
            this.$toasted.show(
              "Oops! An error occurred...",
              {
                type: "error",
                action : {
                  text : 'OK',
                  onClick : (e, toastObject) => { toastObject.goAway(0);}
                }
              }
            );
          })
      }
    },
    mounted() {
      this.retrieveAlbums();
    }
  };
</script>

<style>
  .album-card {
    max-width: 300px;
  }
</style>
