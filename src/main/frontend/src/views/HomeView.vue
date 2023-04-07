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
    <div class="col-12 main-content p-5 fw-bolder">
      <div class="container-fluid">
        <div class="row">
          <div class="col">
            <h1>W<u>ir heiraten!&nbsp;</u></h1>
            <p>Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore
              et
              dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet
              clita
              kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet,
              consetetur
              sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam
              voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea
              takimata
              sanctus est Lorem ipsum dolor sit amet.</p>
          </div>
        </div>
        <div class="row">
          <div class="col">
            <h2>Gästebereich</h2>
            <p>Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore
              et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum.
              Stet clita.</p>
          </div>
        </div>
        <div class="row mb-3">
          <label for="colFormLabel" class="col-sm-2 col-form-label">Gästecode</label>
          <div class="col-sm-8">
            <input type="text" class="form-control" id="colFormLabel" placeholder="code">
          </div>
          <div class="col-sm-2">
            <button type="button" class="btn btn-dark">Anmelden</button>
          </div>  
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
</script>

<style lang="scss" scoped>
@import "@/style/variables.scss";

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