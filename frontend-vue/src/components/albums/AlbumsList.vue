<template>
  <div class="px-5">
    <div class="row justify-content-center">
      <div class="col-md-12">
        <div class="row justify-content-between">
          <h4>Albums List</h4>
          <b-button variant="primary" v-b-modal.add-album-modal>
            <font-awesome-icon icon="plus"/>
            Add album
          </b-button>
        </div>
        <AlbumsSearch></AlbumsSearch>
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
  import http from "../../http-common";
  import AlbumsSearch from "./AlbumsSearch";

  export default {
    name: "albums-list",
    components: {AlbumsSearch},
    data() {
      return {
      };
    },
    methods: {
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
    }
  };
</script>

<style>
  .album-card {
    max-width: 300px;
  }
</style>
