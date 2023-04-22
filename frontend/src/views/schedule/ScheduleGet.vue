<template>
  <CCol>
    <CCard class="mb-2">
      <CCardHeader>
        <strong>Atendimentos agendados</strong>
      </CCardHeader>
      <CCardBody>
        <FullCalendarComponent
          ref="calendar"
          v-if="schedulesLoaded"
          :schedules="schedules"
        />
      </CCardBody>
    </CCard>
  </CCol>
</template>
<script>
import FullCalendarComponent from "@/components/FullCalendar/FullCalendarComponent.vue";
import ScheduleService from "@/Services/scheduleService";
export default {
  components: {
    FullCalendarComponent, // make the <FullCalendar> tag available
  },
  data() {
    return {
      schedulesLoaded: false,
      scheduleService: new ScheduleService(),
      schedules: [],
    };
  },
  methods: {
    async created() {
      await this.consultScheduledTimes();
      this.schedulesLoaded = true;
    },
    async consultScheduledTimes() {
      this.schedules = await this.scheduleService.consultScheduledTimes();
    },
  },
  mounted() {
    this.created();
  },
};
</script>
