<template>
  <div class="px-5">
    <div class="row justify-content-center">
      <div class="col-md-12">
        <div class="row justify-content-between">
          <h4>Customers List</h4>
          <router-link :to="{name: 'add'}">
            <b-button variant="primary" v-b-modal.add-customer-modal>
              <font-awesome-icon icon="plus"/>
              Add user
            </b-button>
          </router-link>
        </div>
        <div class="row my-3">
          <b-form-input v-model="query" v-on:input="searchCustomer($event)"
                        placeholder="Search a customer"></b-form-input>
        </div>
      </div>
    </div>
    <div class="row justify-content-center">
      <div class="col-md-12">
        <div v-if="customers && customers.length > 0">
          <b-table hover
                   :items="customers"
                   :fields="fields"
                   :filter="query"
                   :filterIncludedFields="['name']"
                   primary-key="id">
            <template v-slot:cell(active)="data">
              <font-awesome-icon v-if="data.value" icon="check"/>
              <font-awesome-icon v-else icon="times"/>
            </template>

            <template v-slot:cell(actions)="row">
              <router-link :to="{ name: 'customer-details', params: { customer: row.item, id: row.item.id }}">
                <b-button size="sm" v-b-modal.edit-customer-modal>
                  Edit
                </b-button>
              </router-link>
              <router-link :to="{ name: 'customer-albums', params: { customer: row.item, id: row.item.id }}">
                <b-button size="sm" v-b-modal.customer-albums-modal>
                  View albums
                </b-button>
              </router-link>
              <router-link :to="{ name: 'customer-add-album', params: { customer: row.item, id: row.item.id }}">
                <b-button size="sm" v-on:click="openAddAlbumModal(row.item)">
                  Add album
                </b-button>
              </router-link>
            </template>
          </b-table>
        </div>
      </div>
    </div>
    <!-- -->
    <b-modal id="add-customer-modal" title="Add customer" hide-footer>
      <router-view @refreshData="refreshList"></router-view>
    </b-modal>
    <b-modal id="edit-customer-modal" title="Edit customer" hide-footer>
      <router-view @refreshData="refreshList"></router-view>
    </b-modal>
    <b-modal size="xl" id="add-album-modal" title="Add albums" hide-footer>
      <AlbumsSearch ref="albumsSearch"
                    action-text="Add album"
                    v-on:on-action="addAlbum($event)"
                    class="mx-4"
      >
      </AlbumsSearch>
    </b-modal>
    <b-modal size="xl" id="customer-albums-modal" title="View albums" hide-footer>
      <router-view></router-view>
    </b-modal>
  </div>
</template>

<script>
  import http from "../../http-common";
  import AlbumsSearch from "../albums/AlbumsSearch";


  export default {
    name: "customers-list",
    components: {AlbumsSearch},
    data() {
      return {
        customers: [],
        selectedCustomer: null,
        query: "",
        fields: [
          {key: "name", sortable: true},
          {key: "age", sortable: true},
          {key: "active", sortable: true},
          {key: 'actions', label: 'Actions'}
        ]
      };
    },
    methods: {
      /* eslint-disable no-console */
      retrieveCustomers() {
        http
          .get("/customers")
          .then(response => {
            this.customers = response.data; // JSON are parsed automatically.
            console.log(response.data);
          })
          .catch(e => {
            console.log(e);
          });
      },
      refreshList() {
        this.$bvModal.hide('customer-modal');
        this.retrieveCustomers();
      },
      openAddAlbumModal(customer) {
        this.selectedCustomer = customer;
        this.$bvModal.show('add-album-modal')
      },
      addAlbum(albumId) {
        http.post(`/customers/${this.selectedCustomer.id}/albums`, `"${albumId}"`)
          .then(() => {
            this.$toasted.show(
              "Album successfully added!",
              {duration: 3000, type: "success"}
            );
            this.$bvModal.hide('add-album-modal')
          })
          .catch(e => {
            console.error(e);
            this.$toasted.show(
              "Oops! An error occurred...",
              {
                type: "error",
                action: {
                  text: 'OK',
                  onClick: (e, toastObject) => {
                    toastObject.goAway(0);
                  }
                }
              }
            );
          })
      }
      /* eslint-enable no-console */
    },
    mounted() {
      this.retrieveCustomers();
    }
  };
</script>

<style>
</style>
