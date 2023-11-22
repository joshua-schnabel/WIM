<!-- eslint-disable vue/no-unused-vars -->
<template>
  <div class="row row-mt-2">
    <div class="col-12 main-content fw-bolder">
      <div class="container-fluid">
        <div class="row">
          <div class="col">
            <h1>G<u>ästebereich&nbsp;</u></h1>
            <p>Liebe Gäste,</p>
            <p>wir freuen uns, dass ihr hier seid und mit uns diesen besonderen Tag feiern werdet. Auf dieser Seite
              findet ihr alle wichtigen Details zur Hochzeit, damit ihr bestens informiert seid. Schaut gerne hin und
              wieder auf der Webseite vorbei, Neuerungen oder Ergänzungen werden wir euch hier mitteilen.</p>
          </div>
        </div>
        <div class="row">
          <div class="col">
            <nav class="nav inner-nav sticky-top">
              <a class="nav-link" aria-current="page" href="#Rückmeldung">Rückmeldung</a>
              <a class="nav-link" aria-current="page" href="#Zeitplan">Zeitplan</a>
              <a class="nav-link" aria-current="page" href="#Anfahrt">Anfahrt</a>
              <a class="nav-link" aria-current="page" href="#Bilder">Fotos</a>
              <a class="nav-link" aria-current="page" href="#FAQ">FAQ</a>
            </nav>
          </div>
          <a id="Rückmeldung"></a>
        </div>
        <div class="row pt-5">
          <div class="col">
            <h2>Rückmeldung</h2>
            <p>
              Bitte gebt uns bis zum <u>1. Juni</u> Bescheid, ob ihr dabei sein werdet, damit wir alles Weitere planen
              können. Nutzt dafür bitte die Rückmeldungsfunktion hier auf dieser Webseite. Wir freuen uns auf euch!
            </p>
            <div class="upload-button-container">
              <a @click="showResponseModal()" type="button" class="btn btn-primary btn-lg">Jetzt zurückmelden!</a>
            </div>
          </div>
          <a id="Zeitplan"></a>
        </div>
        <div class="row pt-5">
          <div class="col">
            <h2>Zeitplan</h2>
            <div class="timeline">
              <div class="container-fluid">
                <div class="row timeline-entry" v-for="(timeEntry, index) in timeline" :key="index">
                  <div class="col-lg-6 circle-container">
                    <div class="circle">
                      <div class="time-container">
                        <div class="time">
                          {{ timeEntry.time }}
                        </div>
                        <div class="time-text">
                          Uhr
                        </div>
                      </div>
                    </div>
                    <div class="point"></div>
                  </div>
                  <div class="col-lg-6 description-container">
                    <div class="description">
                      <h3>{{ timeEntry.headline }}</h3>
                      <p>{{ timeEntry.text }}</p>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <a id="Anfahrt"></a>
        </div>
        <div class="row pt-5">
          <div class="col">
            <h2>Anfahrt</h2>
          </div>
        </div>
        <div class="row ">
          <div class="col-lg-6">
            <div class="adress">
              <h1 class="lovingu">Dieterskirchel</h1>
              <p>Rheinzaberner Str. 13</p>
              <p>76761 Rülzheim</p>
            </div>
            <div class="adress-map">
              <div class="adress-card">
                <div class="openstreetmap" id="map-2"></div>
              </div>
            </div>
          </div>
          <div class="col-lg-6">
            <div class="adress">
              <h1 class="lovingu">Grillhuette</h1>
              <p>Conrad-Rauh-Weg 2</p>
              <p>76877 Offenbach (Queich)</p>
            </div>
            <div class="adress-map">
              <div class="adress-card">
                <div class="openstreetmap" id="map-1"></div>
              </div>
            </div>
          </div>
          <a id="Bilder"></a>
        </div>
        <div class="row pt-5">
          <div class="col">
            <h2>Fotos</h2>
            <p>
              Wir möchten unsere Hochzeitserinnerungen gerne mit euch teilen und sind schon gespannt auf alle Fotos und
              Videos, die ihr auf unserer Hochzeitswebseite hochladen möchtet. Eure Eindrücke sind uns
              sehr wichtig und werden unseren besonderen Tag unvergesslich machen. Wir freuen uns darauf, in Erinnerungen
              zu schwelgen und danken euch für eure Unterstützung!
            </p>
            <div class="upload-button-container">
              <a href="https://cloud.1js.de/s/a34GNymKSD3D66Z" target="_blank" type="button"
                class="btn btn-primary btn-lg">Fotos hochladen</a>
            </div>
          </div>
          <a id="FAQ"></a>
        </div>
        <div class="row pt-5">
          <div class="col faq">
            <h2>FAQ</h2>
            <div v-for="(faq, index) in faqs" :key="faq.id">
              <p>
                <button class="btn btn-link" data-bs-toggle="collapse" role="button" @click="invertFAQStatus(index)">
                  <ChevronRight v-if="faq.state" />
                  <ChevronDown v-if="!faq.state" /> {{ faq.q }}
                </button>
              </p>
              <div class="collapse" :id="faq.id">
                <div class="card card-body" v-html="faq.a"></div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="modal fade modal-lg" id="responseModal" data-bs-backdrop="static" data-bs-keyboard="false"
        tabindex="-1">
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <h1 class="modal-title fs-5" id="createInvitationLable">Rückmeldung</h1>
              <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
              <div class="row">
                <div class="col">
                  <h3>Geladene Gäste</h3>
                  <table class="table table-striped table-bordered">
                    <thead class="table-dark">
                      <tr>
                        <th scope="col">Vorname</th>
                        <th scope="col">Nachname</th>
                        <th scope="col">Antwort</th>
                      </tr>
                    </thead>
                    <tbody>
                      <tr v-for="(guest) in modalModel.primaryGuests" :key="guest.id">
                        <td>{{ guest.firstname }}</td>
                        <td> {{ guest.lastname }}</td>
                        <td>
                          <SimpleRadioButtonGroup v-model="guest.answer"
                            :options="[{ text: 'Kommt', value: true }, { text: 'Kommt nicht', value: false }]" />
                        </td>
                      </tr>
                    </tbody>
                  </table>
                </div>
              </div>
              <div class="row" v-if="hasAttributes(modalModel.companionGuests)">
                <div class="col">
                  <h3>Begleitungen</h3>
                  Bitte trage gegebenfalls deine Begleitung mit Vor- und Nachnamen ein.
                  <table class="table table-striped table-bordered">
                    <thead class="table-dark">
                      <tr>
                        <th scope="col">Vorname</th>
                        <th scope="col">Nachname</th>
                        <th scope="col">Antwort</th>
                      </tr>
                    </thead>
                    <tbody>
                      <tr v-for="(guest) in modalModel.companionGuests" :key="guest.id">
                        <td>
                          <SimpleInput v-model="guest.firstname" placeholder="Vorname" regex="[A-Za-z \-]{3,}"
                            :testRegex="guest.answer" errorText="Vorname muss richtig gefüllt sein" />
                        </td>
                        <td>
                          <SimpleInput v-model="guest.lastname" placeholder="Nachname" regex="[A-Za-z \-]{3,}"
                            :testRegex="guest.answer" errorText="Nachname muss richtig gefüllt sein" />
                        </td>
                        <td>
                          <SimpleRadioButtonGroup v-model="guest.answer"
                            :options="[{ text: 'Kommt', value: true }, { text: 'Kommt nicht', value: false }]" />
                        </td>
                      </tr>
                    </tbody>
                  </table>
                </div>
              </div>
              <div class="row" v-if="hasAttributes(modalModel.childGuests)">
                <div class="col">
                  <h3>Kinder</h3>
                  Bitte tragt gegebenenfalls eure Kinder mit Vor- und Nachnamen ein.
                  <table class="table table-striped table-bordered">
                    <thead class="table-dark">
                      <tr>
                        <th scope="col">Vorname</th>
                        <th scope="col">Nachname</th>
                        <th scope="col">Antwort</th>
                      </tr>
                    </thead>
                    <tbody>
                      <tr v-for="(guest) in modalModel.childGuests" :key="guest.id">
                        <td>
                          <SimpleInput v-model="guest.firstname" placeholder="Vorname" regex="[A-Za-z \-]{3,}"
                            :testRegex="guest.answer" errorText="Vorname muss richtig gefüllt sein" />
                        </td>
                        <td>
                          <SimpleInput v-model="guest.lastname" placeholder="Nachname" regex="[A-Za-z \-]{3,}"
                            :testRegex="guest.answer" errorText="Nachname muss richtig gefüllt sein" />
                        </td>
                        <td>
                          <SimpleRadioButtonGroup v-model="guest.answer"
                            :options="[{ text: 'Kommt', value: true }, { text: 'Kommt nicht', value: false }]" />
                        </td>
                      </tr>
                    </tbody>
                  </table>
                </div>
              </div>
              <div class="row" v-if="invitation && invitation!.specialRequest != ''">
                <div class="col">
                  <h3>Persönliche Anfrage</h3>
                  Lieber Gast, <b>{{ invitation!.specialRequest }}</b>
                  <SimpleRadioButtonGroup v-model="modalModel.requestAnswer"
                    :options="[{ text: 'Ja', value: true }, { text: 'Nein', value: false }]" />
                </div>
              </div>
              <div class="row mt-2" v-if="invitation && invitation!.specialRequest != ''">
                <div class="mb-3 row">
                  <label for="request-answer" class="col-sm-3 col-form-label">Nähere Angaben</label>
                  <div class="col-sm-9">
                    <SimpleInput v-model="modalModel.requestAnswerDetail" id="request-answer"
                      placeholder="z.B. welcher Kuchen" />
                  </div>
                </div>
              </div>
            </div>
            <div class="modal-footer">
              <SimpleButton text="Speichern" :working="modalModel.working" :disabled="modalModel.btnDisabled"
                @click="sendResponse()" />
            </div>
          </div>
        </div>
      </div>
      <div class="modal fade" id="thankYouModal" tabindex="-1" role="dialog">
        <div class="modal-dialog modal-dialog-centered" role="document">
          <div class="modal-content">
            <div class="modal-header">
              <h5 class="modal-title" id="exampleModalLabel">Danke!</h5>
              <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
              <p>Vielen Dank für deine Rückmeldung!</p>
            </div>
            <div class="modal-footer">
              <SimpleButton text="Schließen" @click="hideThankYouModal()" />
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <SimpleModal ref="serverErrorDialog" title="Unbekannter Fehler"
    text="Es ist ein unerwarteter Fehler aufgetreten. Bitte versuche es später erneut!"
    @close="router.push({ name: 'home' })" />
  <SimpleModal ref="authErrorDialog" title="Anmeldung abgelaufen"
    text="Deine Anmeldung ist abgelaufen! Bitte melde dich erneut an!" @close="router.push({ name: 'home' })" />
</template>

<script setup lang="ts">
import { ref, onMounted, type Ref, computed, watch } from "vue";
import ChevronRight from 'vue-material-design-icons/ChevronRight.vue';
import ChevronDown from 'vue-material-design-icons/ChevronDown.vue';
import { Collapse } from "bootstrap";
import TokenService from "@/services/TokenService";
import InvitationService, { type Invitation } from "@/services/InvitationService";
import type { Guest } from "@/services/GuestService";
import GuestService from "@/services/GuestService";
import { asyncComputed } from '@vueuse/core'
import { Modal } from "bootstrap";
import _ from "lodash";
import SimpleButton from "@/components/SimpleButton.vue";
import SimpleInput from "@/components/SimpleInput.vue";
import SimpleRadioButtonGroup from "@/components/SimpleRadioButtonGroup.vue";
import SimpleModal from "@/components/SimpleModal.vue";
import router from "@/router";
import HttpClient from "@/services/Axios";
import leaflet from "leaflet";

var invitationId = ref<string>("");
var invitationData = ref<Invitation>();

var guests = asyncComputed(async () => {
  const result: Guest[] = [];
  if (invitationData.value) {
    const guestsStatus = invitationData.value.guests;
    await Promise.all(guestsStatus.map(async (guest) => {
      result.push(await GuestService.getGuest(guest.guestId));
    }));
  }
  return {
    primaryGuests: _.filter(result, (guest) => guest.guestType == "PrimaryGuest"),
    companionGuests: _.filter(result, (guest) => guest.guestType == "Companion"),
    childGuests: _.filter(result, (guest) => guest.guestType == "Child")
  };
})

const invitation = computed(() => {
  return invitationData.value;
})

async function updateInvitation() {
  invitationData.value = await InvitationService.getInvitation(invitationId.value);
}

onMounted(async () => {
  invitationId.value = await TokenService.getInvitationId();
  await updateInvitation();
})

//Modal
interface GuestResponse {
  [propName: string]: {
    id: string
    firstname: string
    lastname: string
    answer: boolean
  }
}

interface Responses {
  requestAnswer: boolean,
  requestAnswerDetail: string
  primaryGuests: GuestResponse
  companionGuests: GuestResponse
  childGuests: GuestResponse
  working: boolean
  btnDisabled: boolean
}
var responseModal: Modal;

const modalModel = ref<Responses>(generateModel());

watch(guests, () => {
  modalModel.value = generateModel();
})

watch(modalModel, () => {
  var cs = _(modalModel.value.childGuests).map(c => {
    var regex = new RegExp("^[A-Za-z -]{3,}$");
    return (regex.test(c.firstname) && regex.test(c.lastname)) || !c.answer;
  }).reduce((sum, n) => sum && n);
  var cc = _(modalModel.value.companionGuests).map(c => {
    var regex = new RegExp("^[A-Za-z -]{3,}$");
    return (regex.test(c.firstname) && regex.test(c.lastname)) || !c.answer;
  }).reduce((sum, n) => sum && n);
  console.log("cs", cs);
  console.log("cc", cc);
  if (cs == undefined)
    cs = true;
  if (cc == undefined)
    cc = true;
  modalModel.value.btnDisabled = !(cs && cc);
}, { deep: true })

onMounted(() => {
  responseModal = new Modal('#responseModal', { backdrop: true });
})

function hasAttributes(myObject: Object) {
  return Object.keys(myObject).length;
}

function generateModel(): Responses {
  const result: Responses = {
    requestAnswer: (invitation.value == undefined) ? false : invitation.value.specialRequestAccepted,
    requestAnswerDetail: (invitation.value == undefined) ? "" : invitation.value.specialRequestAnswer,
    primaryGuests: (guests.value == undefined) ? {} : mapGuests(guests.value.primaryGuests),
    companionGuests: (guests.value == undefined) ? {} : mapGuests(guests.value.companionGuests),
    childGuests: (guests.value == undefined) ? {} : mapGuests(guests.value.childGuests),
    working: false,
    btnDisabled: false
  };
  return result;

  function mapGuests(g: Guest[]): GuestResponse {
    return _(g).map(x => {
      var status = (invitation.value == undefined) ? false : _.find(invitation.value.guests, y => y.guestId == x.id)?.accepted;
      return {
        id: x.id,
        firstname: x.firstname,
        lastname: x.lastname,
        answer: (status) ? status : false
      };
    }).keyBy("id").value();
  }
}

async function showResponseModal() {
  responseModal.show();
}

async function sendResponse() {
  modalModel.value.working = true;
  const guestResponsesToRename = _.concat(_.values(modalModel.value.companionGuests), _.values(modalModel.value.childGuests));
  await Promise.all(guestResponsesToRename.map(async (guestRes) => {
    await GuestService.updateGuest({
      id: guestRes.id,
      firstname: guestRes.firstname,
      lastname: guestRes.lastname
    })
  }));
  const guestResponses = _.concat(_.values(modalModel.value.primaryGuests), _.values(modalModel.value.companionGuests), _.values(modalModel.value.childGuests));
  for (const guestRes of guestResponses) {
    await InvitationService.updateGuestStatus(invitationId.value, {
      guestId: guestRes.id,
      accepted: guestRes.answer
    })
  }
  await InvitationService.updateInvitation({
    id: invitationId.value,
    specialRequestAccepted: modalModel.value.requestAnswer,
    specialRequestAnswer: modalModel.value.requestAnswerDetail
  });
  modalModel.value.working = false;
  await updateInvitation();
  await responseModal.hide();
  await showThankYouModal();
}

// Thank You
var thankYouModal: Modal;

onMounted(() => {
  thankYouModal = new Modal('#thankYouModal', { backdrop: true });
})

async function showThankYouModal() {
  thankYouModal.show();
}

async function hideThankYouModal() {
  thankYouModal.hide();
}

// Error 
const serverErrorDialog = ref();
const authErrorDialog = ref();

defineExpose({ serverError, authError })

function serverError(err: Error) {
  console.error(err.message);
  responseModal.hide();
  thankYouModal.hide();
  serverErrorDialog.value.show();
}

function authError(err: Error) {
  console.error(err.message);
  TokenService.removeToken();
  responseModal.hide();
  thankYouModal.hide();
  authErrorDialog.value.show();
}

// Timeline

const timelineData = ref<{ time: string, headline: string, text: string }[]>([]);

onMounted(async () => {
  var respone = await HttpClient.get<{ time: string, headline: string, text: string }[]>("/assets/protected/timeline.json");
  timelineData.value = respone.data;
});

let timeline = computed(() => {
  return timelineData.value;
});

//FAQ

const faqData = ref<{ q: string, a: string }[]>([]);

onMounted(async () => {
  var respone = await HttpClient.get<{ q: string, a: string }[]>("/assets/protected/faq.json");
  faqData.value = respone.data;
});

let faqs = ref<{ id: string, q: string, a: string, state: boolean }[]>([]);

let faqStatus: Ref<Collapse[]> = ref([]);

watch(faqData, (newV, oldV) => {
  faqs.value = _(faqData.value).map(x => {
    return {
      id: "faq-" + crypto.randomUUID(),
      q: x.q,
      a: x.a,
      state: false
    }
  }).value();

  setTimeout(() => {
    faqStatus.value = [];
    faqs.value.forEach((arrayItem, index) => {
      const el = document.querySelector('#' + arrayItem.id);
      if (el)
        faqStatus.value.push(new Collapse(el, { toggle: false }));
    })
  }, 200);
});

function invertFAQStatus(id: number) {
  faqs.value[id].state = !faqs.value[id].state;
  if (faqs.value[id].state) {
    faqStatus.value[id].show()
  }
  else {
    faqStatus.value[id].hide()
  }
}
function openInNewTab(url: string) {
  document.open(url, '_blank');
}

// Maps

onMounted(() => {
  leaflet.Icon.Default.prototype.options.imagePath = "/assets/";
  var map1 = leaflet.map('map-1', { scrollWheelZoom: false, touchZoom: true }).setView([49.20304, 8.19063], 17);
  map1.attributionControl.setPrefix(false);
  leaflet.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
    maxZoom: 19,
    attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
  }).addTo(map1);
  leaflet.marker([49.20313, 8.19199]).addTo(map1);

  var map2 = leaflet.map('map-2', { scrollWheelZoom: false, touchZoom: true }).setView([49.14154, 8.29129], 17);
  map2.attributionControl.setPrefix(false);
  leaflet.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
    maxZoom: 19,
    attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
  }).addTo(map2);
  leaflet.marker([49.14146, 8.29148]).addTo(map2);
});

</script>

<style lang="scss" scoped>
.btn-link {
  font-size: 25px;
}

.upload-button-container {
  text-align: center;
}

.adress {
  p {
    margin-bottom: 0rem;
  }
}

@include media-breakpoint-down(lg) {
  .adress {
    margin-top: 1rem;
  }
}

.adress-map {
  .adress-card {
    margin-top: 1rem;
    padding: 1rem;
    background-color: $white;
    box-shadow: $default-shadow-wim;
  }

  .openstreetmap {
    width: 100%;
    height: 350px;
  }
}

.faq {

  .btn-link {
    text-align: left;
  }

  a,
  a:visited,
  a:active {
    color: $black;
  }

  a[aria-expanded="true"]::before {
    /* CSS-Regeln, wenn aria-expanded="true" */
    content: "󰅀";
  }

  a[aria-expanded="false"]::before {
    /* CSS-Regeln, wenn aria-expanded="false" */
    content: "󰅂";
  }
}

.timeline {

  position: relative;

  &::before {
    content: "";
    display: block;
    width: 2px;
    height: 100%;
    background: $black;
    margin: 0 auto;
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
  }

  .timeline-entry {
    margin-bottom: 2rem;

    .circle-container {
      display: flex;
      justify-content: center;
      align-items: center;
      position: relative;
      order: 1;

      &::before {
        content: "";
        width: 35%;
        height: 2px;
        background: $black;
        margin: auto 0;
        position: absolute;
        top: 0;
        right: 10px;
        bottom: 0;
      }

      .circle {
        width: 8rem;
        height: 8rem;
        position: relative;
        z-index: 0;
        display: table;

        .time-container {
          display: table-cell;
          text-align: center;
          vertical-align: middle;
        }

        &::before {
          content: "";
          position: absolute;
          top: 0;
          left: 0;
          width: 100%;
          height: 100%;
          z-index: -1;
          background-image: url("@/assets/images/circle.png");
          background-size: cover;
          transition: all 0.5s ease-in-out 0s;
        }
      }

      .point {

        &::before,
        &::after {
          content: "";
          display: block;
          width: 1rem;
          height: 1rem;
          margin: auto 0;
          position: absolute;
          top: 0;
          right: -0.5rem;
          bottom: 0;
          border-radius: 50%;
          transition: all 0.5s ease-in-out 0s;
          border: 2px solid $black;
        }

        &::before {
          transform: translateX(-0.25rem);
          background-color: #EFEDED;
        }

        &::after {
          transform: translateX(0.25rem);
        }
      }
    }

    .description-container {
      display: flex;
      align-items: center;
      order: 2;

      .description {
        padding-left: 4rem;
      }
    }

    &:nth-child(2n) {
      .circle-container {
        order: 2;

        &::before {
          left: 10px;
        }
      }

      .description-container {
        order: 1;
      }

      .point {

        &::before,
        &::after {
          left: -0.5rem;
        }
      }
    }
  }

  .timeline-entry:hover {
    .circle {
      &::before {
        rotate: 15deg;
      }
    }

    .point {
      &::before {
        transform: translateX(0.25rem);
      }

      &::after {
        transform: translateX(-0.25rem);
      }
    }
  }
}

@include media-breakpoint-down(xl) {
  .timeline {
    .timeline-entry {
      .circle-container::before {
        width: 30%
      }
    }
  }
}

@include media-breakpoint-down(lg) {
  .timeline {
    margin-left: 3rem;

    .timeline-entry {
      .description-container {
        h3 {
          text-align: center;
        }

        .description {
          padding-left: 2rem;
          padding-right: 2rem;
        }
      }
    }

    &::before {
      margin: 0;
    }

    .timeline-entry {

      .circle-container {
        order: 1 !important;

        &::before {
          display: none;
        }
      }

      .description-container {
        order: 2 !important;
      }

      .point {

        &::before,
        &::after {
          left: -0.5rem !important;
        }
      }
    }
  }
}</style>