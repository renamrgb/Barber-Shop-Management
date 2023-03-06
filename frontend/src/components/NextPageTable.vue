<template>
  <nav v-if="totalPages > 0">
    <ul class="pagination">
      <li class="page-item">
        <a class="page-link" @click="previous()" aria-label="Previous">
          <span aria-hidden="true">&laquo;</span>
        </a>
      </li>
      <li
        :class="{ active: isActive(pageId) }"
        class="page-item"
        v-for="pageId in totalPages"
        :key="pageId"
      >
        <a class="page-link" @click="setNumberPage(pageId)">{{ pageId }}</a>
      </li>
      <li class="page-item">
        <a class="page-link" @click="next()" aria-label="Next">
          <span aria-hidden="true">&raquo;</span>
        </a>
      </li>
    </ul>
  </nav>
</template>

<script>
export default {
  name: "NextPageTable",
  props: {
    pageId: {
      type: Number,
      required: true,
    },
  },
  data() {
    return {
      page: this.pageId,
      totalPages: 0,
      totalElements: 0,
      last: false,
      pageable: {
        pageNumber: 0,
        pageSize: 0,
      },
    };
  },
  methods: {
    isActive(page) {
      if (page - 1 == this.pageable.pageNumber) return true;
      return false;
    },
    setNumberPage(page) {
      this.pageable.pageNumber = page - 1;
      this.$emit("update-pageId", this.pageable.pageNumber);
    },
    previous() {
      if (this.pageable.pageNumber != 0) {
        this.pageable.pageNumber = this.pageable.pageNumber - 1;
        this.$emit("update-pageId", this.pageable.pageNumber);
      }
    },
    next() {      
      if (this.pageable.pageNumber < this.totalPages-1) {
        this.pageable.pageNumber = this.pageable.pageNumber + 1;
        this.$emit("update-pageId", this.pageable.pageNumber);
      }
    },
  },
};
</script>

<style></style>
