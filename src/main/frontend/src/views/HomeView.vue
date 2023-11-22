<template>
  <div class="row row-mt-1">
    <div class="col-2">
    </div>
    <div class="col-8 images-container">
      <div class="images">
        <div class="image image-1">
          <img src="@/assets/images/us2.jpg" alt="image" />
        </div>
        <div class="image image-2">
          <img src="@/assets/images/us1.jpg" alt="image" />
        </div>
        <div class="head-text lovingu">
          <div class="text" data-text="Jule & Joshua">
            Jule & Joshua
          </div>
        </div>
      </div>
    </div>
    <div class="col-2">
    </div>
  </div>
  <div class="row row-mt-2">
    <div class="col-12 main-content fw-bolder">
      <div class="container-fluid">
        <div class="row">
          <div class="col">
            <h1>W<u>ir heiraten!&nbsp;</u></h1>
            <p>Liebe Gäste, herzlich Wilkommen auf unserer Webseite. </p>
            <p>Hier findet ihr alle Informationen zu
              unserer Hochzeit und habt die Möglichkeit, euch zurückzumelden.</p>
          </div>
        </div>
        <div class="row">
          <div class="col">
            <h2>Gästebereich</h2>
            <p>Bitte gebt euren individuellen Code aus der Einladung hier ein. Falls ihr per QR-Code oder NFC auf die
              Seite gelangt seid, ist der Code bereits ausgefüllt. Mit dem "Anmelden"-Button gelangt ihr in den
              Gästebereich.</p>
          </div>
        </div>
        <div class="row mb-3">       
          <label for="colFormLabel" class="col-12 col-sm-2 col-form-label">Gästecode</label>
          <div class="col-8">
            <SimpleInput v-model="code" placeholder="Euer persönlicher Code" regex="[A-Za-z0-9]{8}" error-text="Bitte den achtstelligen Code eingeben." @regexStatus="event => func1(event)"/>
          </div>
          <div class="col-sm-2 col-4">
            <SimpleButton text="Anmelden" @click="sendCode()" :working="working" :disabled="disableButton" />
          </div>
          <div class="col-12" v-if="codeStatus">
            <div class="error-text">Der Code ist leider nicht korrekt.</div>
          </div>   
        </div>
      </div>
    </div>
  </div>
  <SimpleModal ref="serverErrorDialog" title="Unbekannter Fehler"
    text="Es ist ein unerwarteter Fehler aufgetreten. Bitte versuche es später erneut!"
    @close="router.push({ name: 'home' })" />
</template>

<script setup lang="ts">
import router from "@/router";
import Loading from 'vue-material-design-icons/Loading.vue';
import TokenService from "@/services/TokenService";
import { ref } from "vue";
import SimpleInput from "@/components/SimpleInput.vue";

const props = defineProps({
  code: { type: String, required: false, defaultValue: "" }
})

/*Error*/
defineExpose({ serverError, authError })

const serverErrorDialog = ref();

function serverError(_err: Error) {
  serverErrorDialog.value.show();
}

function authError(_err: Error) {
  serverErrorDialog.value.show();
}

var code = ref<string>(props.code || "");
var codeStatus = ref<boolean>(false);
var working = ref<boolean>(false);
var disableButton = ref<boolean>(false);

async function sendCode() {
  working.value = true;
  const result = await TokenService.getToken(code.value);
  if (result) {
    codeStatus.value = false;
    router.push({ name: "guests" });
    working.value = false;
  } else {
    codeStatus.value = true;
    working.value = false;
  }
}

function func1(event: boolean)  {
  disableButton.value = event;
}

</script>

<style lang="scss" scoped>
@import "@/style/variables.scss";

.error-text {
  color: $red-500;
}

.image {
  background-color: #fff;
  display: inline-block;
  padding: 1vw 1vw 4vw;
  width: 40%;
  box-shadow: $default-shadow-wim;

  img {
    width: 100%;
  }

  &.image-1 {
    rotate: 15deg;
  }

  &.image-2 {
    rotate: -10deg;
  }
}

.images-container {
  position: relative;
}

.images {
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}

.row-mt-1 {
  margin-top: 3rem;
}

.row-mt-2 {
  margin-top: 5rem;
}


.head-text {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;

  .text {
    color: $white;
    text-align: center;
    font-size: 10vw;
    position: relative;
    z-index: 0
  }

  .text:after {
    content: attr(data-text);
    -webkit-text-stroke: 0.5vw $gray-800;
    color: $gray-800;
    position: absolute;
    left: 0;
    z-index: -1
  }
}
</style>