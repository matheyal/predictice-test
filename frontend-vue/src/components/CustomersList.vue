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
          <ul>
            <ol v-for="(customer, index) in customers" :key="index">
              <router-link :to="{
                            name: 'customer-details',
                            params: { customer: customer, id: customer.id }
                        }"
                        v-b-modal.add-customer-modal>
                <font-awesome-icon icon="user"/>
                {{customer.name}}
              </router-link>
            </ol>
          </ul>
        </div>
      </div>
    </div>
    <!-- -->
    <b-modal id="add-customer-modal" title="Add customer" hide-footer>
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
        query: ""
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
