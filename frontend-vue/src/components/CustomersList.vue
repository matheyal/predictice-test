<template>
  <div class="px-5">
    <div class="row justify-content-center">
      <div class="col-md-12">
        <div class="row justify-content-between">
          <h4>Customers List</h4>
          <router-link :to="{name: 'add'}">
            <b-button variant="primary" v-b-modal.customer-modal>
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
                   filterIncludedFields="name"
                   primary-key="id">
            <template v-slot:cell(active)="data">
              <font-awesome-icon v-if="data.value" icon="check"/>
              <font-awesome-icon v-else icon="times"/>
            </template>

            <template v-slot:cell(actions)="row">
              <router-link :to="{ name: 'customer-details', params: { customer: row.item, id: row.item.id }}">
                <b-button size="sm" v-b-modal.customer-modal>
                  Edit
                </b-button>
              </router-link>
              <router-link :to="{ name: 'customer-albums', params: { customer: row.item }}">
                <b-button size="sm" v-b-modal.customer-modal>
                  Albums
                </b-button>
              </router-link>
            </template>
          </b-table>
        </div>
      </div>
    </div>
    <!-- -->
    <b-modal id="customer-modal" title="Add customer" hide-footer>
      <router-view @refreshData="refreshList"></router-view>
    </b-modal>
  </div>
</template>

<script>
  import http from "../http-common";


  export default {
    name: "customers-list",
    data() {
      return {
        customers: [],
        query: "",
        fields: [
          { key: "name", sortable: true },
          { key: "age", sortable: true },
          { key: "active", sortable: true },
          { key: 'actions', label: 'Actions' }
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
      searchCustomer(query) {

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
