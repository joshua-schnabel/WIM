<template>
  <div class="row row-mt-2">
    <div class="col-12 main-content p-5 fw-bolder">
      <div class="container-fluid">
        <div class="row">
          <div class="col">
            <h1>Administration</h1>
            <h2>Gäste</h2>
            <p>
              <button type="button" class="btn btn-success" @click="showGuestAddDialog()">
                <Plus /> Gast hinzufügen
              </button>
            </p>
            <table class="table table-light table-striped">
              <thead>
                <tr>
                  <th scope="col">#</th>
                  <th scope="col">Vorname</th>
                  <th scope="col">Nachname</th>
                  <th scope="col">Gästetyp</th>
                  <th scope="col">Zugeordnet</th>
                  <th scope="col">Aktionen</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="guest in guests" :key="guest.id">
                  <th scope="row">{{ guest.id }}</th>
                  <td>{{ guest.firstname }}</td>
                  <td>{{ guest.lastname }}</td>
                  <td>{{ guest.guestType }}</td>
                  <td>{{ guest.assigned ? "Ja" : "Nein" }}</td>
                  <td>
                    <button type="button" class="btn btn-warning" v-if="guest.actions.edit"
                      @click="deleteGuest(guest.actions.edit)">
                      <Pen />
                    </button>
                    <button type="button" class="btn btn-danger" v-if="guest.actions.delete"
                      @click="deleteGuest(guest.actions.delete)">
                      <Delete />
                    </button>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
        <div class="row">
          <div class="col">
            <h2>Einladung</h2>
            <p>
              <button type="button" class="btn btn-success" @click="shoInvitationAddDialog()">
                <Plus /> Einladung hinzufügen
              </button>
            </p>
            <table class="table table-light table-striped">
              <thead>
                <tr>
                  <th scope="col">#</th>
                  <th scope="col">Name</th>
                  <th scope="col">Status</th>
                  <th scope="col">Gäste</th>
                  <th scope="col">Anfrage</th>
                  <th scope="col">Angenommen</th>
                  <th scope="col">Antwort</th>
                  <th scope="col">Aktionen</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="invitation in invitations" :key="invitation.id">
                  <th scope="row">{{ invitation.id }}</th>
                  <td>{{ invitation.name }}</td>
                  <td>{{ invitation.status }}</td>
                  <td>{{ invitation.confirmedGuestsCount }}/{{ invitation.assignedGuestsCount }}</td>
                  <td>{{ invitation.specialRequest }}</td>
                  <td>{{ invitation.specialRequestAccepted ? "Ja" : "Nein" }}</td>
                  <td>{{ invitation.specialRequestAnswer }}</td>
                  <td>
                    <button type="button" class="btn btn-warning" v-if="invitation.actions.delete">
                      <Pen />
                    </button>
                    <button type="button" class="btn btn-danger" v-if="invitation.actions.delete"
                      @click="deleteInvitation(invitation.actions.delete)">
                      <Delete />
                    </button>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
      <!-- Guest Modal -->
      <div class="modal fade modal-lg" id="createGuest" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1">
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <h1 class="modal-title fs-5" id="createGuestLabel">Gast hinzufügen</h1>
              <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
              <form>
                <div class="mb-3">
                  <label for="firstname" class="form-label">Vorname</label>
                  <input type="text" class="form-control" id="firstname" v-model="newGuest.firstname">
                </div>
                <div class="mb-3">
                  <label for="lastname" class="form-label">Nachname</label>
                  <input type="text" class="form-control" id="lastname" v-model="newGuest.lastname">
                </div>
                <div class="mb-3">
                  <label class="form-check-label" for="guesttype">Gästetyp</label>
                  <select class="form-select" id="guesttype" v-model="newGuest.guestType">
                    <option value="PrimaryGuest" selected>Primärgast</option>
                    <option value="Companion">Begleitung</option>
                  </select>
                </div>
              </form>
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-primary" @click="createGuest(newGuest)">Speichern</button>
            </div>
          </div>
        </div>
      </div>
      <!-- Invitation Modal -->
      <div class="modal fade modal-lg" id="createInvitation" data-bs-backdrop="static" data-bs-keyboard="false"
        tabindex="-1">
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <h1 class="modal-title fs-5" id="createInvitationLable">Einladung hinzufügen</h1>
              <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
              <form>
                <div class="mb-3">
                  <label for="invitationName" class="form-label">Beschreibender Name</label>
                  <input type="text" class="form-control" id="invitationName" v-model="newInvitation.name"
                    placeholder="Beschreibender Name">
                </div>
                <div class="mb-3">
                  <label for="invitationUsers" class="form-label">Gäste</label>
                  <input type="text" class="form-control" id="invitationUsers" placeholder="Gast Suchen"
                    data-bs-toggle="dropdown" v-on:focus="updateInvitationModalGuestSearchResultsCurrent()"
                    v-model="invitationModalGuestSearchString">
                  <ul class="dropdown-menu" id="invitationUserSearch">
                    <li v-for="result in invitationModalGuestSearchResults" :key="result.id"><a class="dropdown-item"
                        @click="invitationModalGuestAdd(result.id)">{{ result.text }}</a></li>
                  </ul>
                  <table class="table table-striped">
                    <tbody>
                      <tr v-for="guestSelected in invitationModalGuestSelected" :key="guestSelected.id">
                        <th scope="row">{{ guestSelected.id }}</th>
                        <td>{{ guestSelected.firstname }}</td>
                        <td>{{ guestSelected.lastname }}</td>
                        <td> <button type="button" class="btn btn-danger"
                            @click="invitationModalGuestRemove(guestSelected.id)">
                            <Delete />
                          </button></td>
                      </tr>
                    </tbody>
                  </table>
                </div>
                <div class="mb-3">
                  <label for="companionsCount" class="form-label">Anzahl der Begleitungen</label>
                  <div class="row">
                    <div class="col col-11">
                      <input type="range" class="form-range" min="0" max="2" id="companionsCount"
                        v-model="invitationModalCompanionsCount">
                    </div>
                    <div class="col col-1"> {{ invitationModalCompanionsCount }}</div>
                  </div>
                </div>
                <div class="mb-3">
                  <label for="childrenCount" class="form-label">Anzahl der Kinder</label>
                  <div class="row">
                    <div class="col col-11">
                      <input type="range" class="form-range" min="0" max="4" id="childrenCount"
                        v-model="invitationModalChildrenCount">
                    </div>
                    <div class="col col-1">{{ invitationModalChildrenCount }}</div>
                  </div>
                </div>
                <div class="mb-3">
                  <label for="specialRequest" class="form-label">Anfrage</label>
                  <input type="text" class="form-control" id="specialRequest" v-model="newInvitation.specialRequest">
                </div>
              </form>
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-primary" @click="invitationModalCreate()">Speichern</button>
            </div>
          </div>
        </div>
      </div>
      <!-- Yes/No Modal -->
      <div class="modal fade" id="yesNoModal" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1">
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <h1 class="modal-title fs-5" id="exampleModalLabel">{{ yesNoModalData.title }}</h1>
              <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
              {{ yesNoModalData.question }}
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-error" @click="yesNoAnswer(false)">Nein</button>
              <button type="button" class="btn btn-success" @click="yesNoAnswer(true)">Ja</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref, computed, watch } from 'vue';
import Delete from 'vue-material-design-icons/Delete.vue';
import Pen from 'vue-material-design-icons/Pen.vue';
import Plus from 'vue-material-design-icons/Plus.vue';
import guestService, { type Guest, type NewGuest } from '@/services/GuestService';
import invitationService, { type Invitation, type NewInvitation } from '@/services/InvitationService';
import { Modal, Dropdown } from "bootstrap";
import _ from 'lodash';

/* Guests */
var guestsData = ref<Guest[]>();

const guests = computed(() => {
  return guestsData.value;
})

async function updateGuest() {
  guestsData.value = await guestService.getGuests()
}

async function deleteGuest(action: string | undefined) {
  if (action !== undefined && await askYesNo("Löschen", "Soll der Gast wirklich gelöscht werden?")) {
    await guestService.deleteGuest(action);
    await updateGuest();
  }
}

onMounted(() => {
  updateGuest();
})

/*Invitation*/
var invitationsData = ref<Invitation[]>();

const invitations = computed(() => {
  return invitationsData.value;
})

async function updateInvitations() {
  invitationsData.value = await invitationService.getInvitations();
}

async function deleteInvitation(action: string | undefined) {
  if (action !== undefined && await askYesNo("Löschen", "Soll die Einladung wirklich gelöscht werden?")) {
    await invitationService.deletInvitation(action);
    await updateInvitations();
    await updateGuest();
  }
}

onMounted(() => {
  updateInvitations();
})

/*Invitation Modal*/
var newInvitation = ref<NewInvitation>({ name: "", specialRequest: "" });
var invitationModal: Modal;
var invitationModalDropdown: Dropdown;
var invitationModalGuestSearchString = ref("");
var invitationModalGuestSearchResults = ref<{ id: string, text: string }[]>([{ id: "non", text: "Es wurde kein Gast gefunden..." }]);
var invitationModalGuestSelected = ref<Guest[]>([]);
var invitationModalChildrenCount = ref(0);
var invitationModalCompanionsCount = ref(0);

function shoInvitationAddDialog() {
  invitationModal.show();
}

async function invitationModalCreate() {
  await invitationService.createInvitation(newInvitation.value, invitationModalGuestSelected.value, invitationModalCompanionsCount.value, invitationModalChildrenCount.value);
  invitationModal.hide();
  await updateGuest();
  await updateInvitations();
  invitationModalGuestSearchString.value = "";
  invitationModalGuestSearchResults.value = [{ id: "non", text: "Es wurde kein Gast gefunden..." }];
  invitationModalGuestSelected.value = [];
  invitationModalChildrenCount.value = 0;
  invitationModalCompanionsCount.value = 0;
}

function invitationModalGuestAdd(id: String) {
  var x = _.find(guestsData.value, (guest: Guest) => guest.id == id);
  if (x != undefined)
    invitationModalGuestSelected.value.push(x);
  updateInvitationModalGuestSearchResultsCurrent();
}

function invitationModalGuestRemove(id: String) {
  _.remove(invitationModalGuestSelected.value, element => element.id == id);
  updateInvitationModalGuestSearchResultsCurrent();
}

function updateInvitationModalGuestSearchResultsCurrent() {
  updateInvitationModalGuestSearchResults(invitationModalGuestSearchString.value);
}

function updateInvitationModalGuestSearchResults(search: string) {
  console.log("updateInvitationModalGuestSearchResults");
  var x = _.filter(guestsData.value, guest => (guest.firstname.toLocaleLowerCase().startsWith(search.toLocaleLowerCase())
    || guest.lastname.toLocaleLowerCase().startsWith(search.toLocaleLowerCase()) && guest.guestType == "PrimaryGuest" && !guest.assigned))
    .filter(guest => !_.some(invitationModalGuestSelected.value, (guest2) => guest.id == guest2.id))
    .map((guest: Guest) => { return { id: guest.id, text: guest.firstname + " " + guest.lastname } });
  if (x.length == 0) {
    x.push({ id: "non", text: "Es wurde kein Gast gefunden..." });
  }
  invitationModalGuestSearchResults.value = x;
}

watch(invitationModalGuestSearchString, async (newString) => {
  updateInvitationModalGuestSearchResults(newString);
})

onMounted(() => {
  invitationModal = new Modal('#createInvitation', { backdrop: true });
  invitationModalDropdown = new Dropdown('#invitationUserSearch');
})

/* Guest Modal */
var newGuest = ref<NewGuest>({ firstname: "", lastname: "", guestType: "" });
var guestModal: Modal;

function showGuestAddDialog() {
  guestModal.show();
}

async function createGuest(guest: NewGuest) {
  await guestService.createGuest(guest);
  await updateGuest();
  guestModal.hide();
}

onMounted(() => {
  guestModal = new Modal('#createGuest', { backdrop: true });
})


/* Yes No Modal */
var yesNoModalData = ref<{ title: string, question: string, answer: Boolean }>({ title: "", question: "", answer: false });
var yesNoModal: Modal;
var yesNoModalElement: HTMLElement;

async function askYesNo(title: string, question: string): Promise<Boolean> {
  yesNoModalData.value = {
    title, question, answer: false
  };
  return new Promise<Boolean>((resolve) => {
    yesNoModal.show();
    yesNoModalElement.addEventListener('hide.bs.modal', () => {
      resolve(yesNoModalData.value.answer);
    }, { once: true });
  });
}

function yesNoAnswer(answer: Boolean) {
  yesNoModalData.value.answer = answer;
  yesNoModal.hide();
}

onMounted(() => {
  yesNoModal = new Modal('#yesNoModal');
  const element = document.getElementById('yesNoModal');
  if (element !== null)
    yesNoModalElement = element;
})

</script>

<style lang="scss" scoped>
#invitationUserSearch {
  width: calc(100% - 2 * var(--bs-modal-padding));
}
</style>