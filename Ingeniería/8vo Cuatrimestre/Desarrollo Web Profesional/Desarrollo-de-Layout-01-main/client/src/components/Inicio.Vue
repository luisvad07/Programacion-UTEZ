<template>
    <b-breadcrumb :items="items"></b-breadcrumb>
</template>

<script>
export default {
  data() {
    return {
      items: [
        {
          text: "Inicio",
          href: "/#/inicio",
          name:"inicio"
        },
        {
          text: "Home",
          href: "/#/home",
          name:"home"
        },
        {
          text: "User",
          active: true
        },
      ],
    };
  },
};
</script>