<style></style>
<script>
import FullCalendar from "@fullcalendar/vue3";
import dayGridPlugin from "@fullcalendar/daygrid";
import timeGridPlugin from "@fullcalendar/timegrid";
import ptBrLocale from "@fullcalendar/core/locales/pt-br";
import interactionPlugin from "@fullcalendar/interaction";
import ParameterService from "@/Services/parameterService";
export default {
  props: {
    schedules: {
      type: Array,
      require: true,
    },
  },
  components: {
    FullCalendar,
  },
  watch: {
    schedules() {
      this.calendarOptions.events = this.schedules;
    },
  },
  data() {
    return {
      parameterService: new ParameterService(),
      calendarOptions: {
        locale: ptBrLocale,
        plugins: [
          dayGridPlugin,
          timeGridPlugin,
          interactionPlugin, // needed for dateClick
        ],
        headerToolbar: {
          left: "prev,next today",
          center: "title",
          right: "timeGridWeek,timeGridDay",
        },
        initialView: "timeGridWeek",
        initialEvents: [], // alternatively, use the `events` setting to fetch from a feed
        editable: true,
        selectable: true,
        selectMirror: true,
        dayMaxEvents: true,
        weekends: true,
        select: this.handleDateSelect,
        eventClick: this.handleEventClick,
        eventsSet: this.handleEvents,
        slotLabelInterval: "00:30",
        slotMinTime: "08:00:00", // Define o horário de início do calendário como 8h
        slotMaxTime: "18:00:00", // Define o horário de término do calendário como 18h
        events: [],
      },
      currentEvents: [],
    };
  },
  methods: {
    handleWeekendsToggle() {
      this.calendarOptions.weekends = !this.calendarOptions.weekends; // update a property
    },
    handleDateSelect(selectInfo) {
      this.$router.push(`/schedule/create/${selectInfo.startStr}`);
    },
    //Clicar no evento
    handleEventClick(clickInfo) {
      this.$router.push(`/schedule/get/${clickInfo.event.id}`);
    },
    handleEvents(events) {
      this.currentEvents = events;
    },
    async setSlotTime() {
      const WORK_SCHEDULES = await this.parameterService.consultWorkSchedules();
      for (const ELEMENT of WORK_SCHEDULES) {
        if (ELEMENT.parameter_key == "START_TIME") {
          this.calendarOptions.slotMinTime = ELEMENT.parameter_value;
        } else if (ELEMENT.parameter_key == "END_TIME") {
          this.calendarOptions.slotMaxTime = ELEMENT.parameter_value;
        }
      }
    },
  },
  mounted() {
    this.setSlotTime();
  },
  created() {
    this.calendarOptions.events = this.schedules;
  },
};
</script>
<template>
  <CRow>
    <FullCalendar :options="calendarOptions" />
  </CRow>
</template>
