<template>
  <div>
    <h4>Customers List</h4>
    <div class="row">
      <div class="col-md-6">

        <ul>
          <ol v-for="(customer, index) in customers" :key="index">
            <router-link :to="{
                            name: 'customer-details',
                            params: { customer: customer, id: customer.id }
                        }">
              <font-awesome-icon icon="user"/>
              {{customer.name}}
            </router-link>
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
    name: "customers-list",
    data() {
      return {
        customers: []
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
