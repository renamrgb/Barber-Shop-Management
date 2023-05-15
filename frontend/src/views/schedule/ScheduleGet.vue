<template>
  <CCol>
    <CCard class="mb-2">
      <CCardHeader>
        <strong>Atendimentos agendados</strong>
      </CCardHeader>
      <CCardBody>
        <div class="row mb-3">
          <div class="col">
            <div class="col">
              <CFormLabel for="nome">* Profissional</CFormLabel>
            </div>
          </div>
        </div>
        <div class="row mb-3 align-items-center">
          <div class="col-md-4">
            <v-select
              id="selectV"
              v-model="profissionalSelected"
              label="label"
              :options="optionsSelectProfessional"
            ></v-select>
          </div>
          <div class="col-md-8">
            <button
              class="btn btn-outline-secondary bg-dark border-bottom-0 border rounded-pill ms-n5"
              type="button"
              @click="consultScheduledTimes"
            >
              <CIcon icon="cil-magnifying-glass" size="x" />
            </button>
          </div>
        </div>
        <div class="row">
          <div class="col">
            <FullCalendarComponent
              ref="calendar"
              v-if="schedulesLoaded"
              :schedules="schedules"
            />
          </div>
        </div>
      </CCardBody>
    </CCard>
  </CCol>
  <toast ref="toast" />
</template>
<script>
import FullCalendarComponent from "@/components/FullCalendar/FullCalendarComponent.vue";
import ScheduleService from "@/Services/scheduleService";
import ProfissionalService from "@/Services/profissionalService";
import Toast from "@/components/Toast.vue";
export default {
  components: {
    FullCalendarComponent, // make the <FullCalendar> tag available
    Toast,
  },
  data() {
    return {
      pService: new ProfissionalService(),
      schedulesLoaded: false,
      scheduleService: new ScheduleService(),
      schedules: [],
      profissionalSelected: undefined,
      optionsSelectProfessional: [],
    };
  },
  methods: {
    async carregarOptionsProfessional() {
      let res = await this.pService.consultarTodos();
      for (let e of res) {
        this.optionsSelectProfessional.push({
          value: e.id,
          label: e.name,
        });
      }
      this.profissionalSelected = this.optionsSelectProfessional[0];
      this.created();
    },
    async search() {
      this.schedules = await this.consultScheduledTimes();
      this.$refs.calendar.calendarOptions.update();
    },
    async created() {
      await this.consultScheduledTimes();
      this.schedulesLoaded = true;
    },
    async consultScheduledTimes() {
      if (this.profissionalSelected != null) {
        this.schedules = await this.scheduleService.consultScheduledTimes(
          this.profissionalSelected.value
        );
      } else this.$refs.toast.createToastDanger("Profissional é obrigatório");
    },    
  },
  mounted() {
    this.carregarOptionsProfessional();
  },
};
</script>
