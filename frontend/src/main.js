import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";

import CoreuiVue from "@coreui/vue";
import CIcon from "@coreui/icons-vue";
import { iconsSet as icons } from "@/assets/icons";
import DocsExample from "@/components/DocsExample";

import { VueMaskDirective } from "v-mask";
const vMaskV2 = VueMaskDirective;
const vMaskV3 = {
  beforeMount: vMaskV2.bind,
  updated: vMaskV2.componentUpdated,
  unmounted: vMaskV2.unbind,
};

const app = createApp(App).directive("mask", vMaskV3);

app.use(store);
app.use(router);
app.use(CoreuiVue);

app.provide("icons", icons);
app.component("CIcon", CIcon);
app.component("DocsExample", DocsExample);

app.mount("#app");
