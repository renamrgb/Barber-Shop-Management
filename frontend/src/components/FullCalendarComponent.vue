<script>
import FullCalendar from "@fullcalendar/vue3";
import dayGridPlugin from "@fullcalendar/daygrid";
import timeGridPlugin from "@fullcalendar/timegrid";
import ptBr from "@/assets/lang/pt-br";
export default {
  components: {
    FullCalendar,
  },
  data() {
    return {
      locale: ptBr,
      calendarOptions: {
        plugins: [dayGridPlugin, timeGridPlugin],
        initialView: "timeGridWeek",
        events: [
          // insira seus eventos aqui
        ],
        views: {
          day: {
            type: "dayGrid",
            duration: { days: 1 },
          },
          week: {
            type: "timeGrid",
            duration: { weeks: 1 },
          },
        },
        headerToolbar: {
          left: "prev,next today",
          center: "title",
          right: "dayGridDay,timeGridWeek",
        },
      },
    };
  },
};
</script>
<template>
  <CRow>
    <CCol :xs="12">
      <CCard class="mb-4">
        <CCardHeader>
          <strong>Registrar Agendamento</strong>
        </CCardHeader>
        <CCardBody
          ><FullCalendar :options="calendarOptions" /></CCardBody></CCard></CCol
  ></CRow>
</template>
