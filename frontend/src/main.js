import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";

import CoreuiVue from "@coreui/vue";
import CIcon from "@coreui/icons-vue";
import { iconsSet as icons } from "@/assets/icons";
import DocsExample from "@/components/DocsExample";
import vSelect from "vue-select";

import { VueMaskDirective } from "v-mask";
const vMaskV2 = VueMaskDirective;
const vMaskV3 = {
  beforeMount: vMaskV2.bind,
  updated: vMaskV2.componentUpdated,
  unmounted: vMaskV2.unbind,
};

import VueDatePicker from "@vuepic/vue-datepicker";
import "@vuepic/vue-datepicker/dist/main.css";

const app = createApp(App).directive("mask", vMaskV3);

app.use(store);
app.use(router);
app.use(CoreuiVue);
app.component('VueDatePicker', VueDatePicker);

app.provide("icons", icons);
app.component("CIcon", CIcon);
app.component("DocsExample", DocsExample);
app.component("v-select", vSelect);
app.mount("#app");
