import Vue from "vue";
import Router from "vue-router";
import CustomersList from "./components/customers/CustomersList.vue";
import AddCustomer from "./components/customers/AddCustomer.vue";
import Customer from "./components/customers/Customer.vue";
import AlbumsList from "./components/albums/AlbumsList";

Vue.use(Router);

export default new Router({
  mode: "history",
  routes: [
    {
      path: "/",
      name: "customers",
      alias: "/customer",
      component: CustomersList,
      children: [
        {
          path: "/customer/:id",
          name: "customer-details",
          component: Customer,
          props: true
        },
        {
          path: "/customer/:id/albums",
          name: "customer-albums",
          component: AlbumsList,
          props: true
        },
        {
          path: "/customer/:id/albums/add",
          name: "customer-add-album",
          component: AlbumsList,
          props: true
        },
        {
          path: "/customer/add",
          name: "add",
          component: AddCustomer
        }
      ]
    },
    {
      path: "/albums",
      name: "albums",
      component: AlbumsList
    }
  ]
});
