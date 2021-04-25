<!--此页面为vue初始页面-->
<!--不变的部分写在App.vue，变化的部分写在Home.vue，此变化部分在App.vue里写为 <router-view/>-->
<template>
  <a-layout id="components-layout-demo-top-side-2">
      <the-header></the-header>
      <router-view/>
      <the-footer></the-footer>
  </a-layout>
</template>

<script lang="ts">
    import { defineComponent } from 'vue';
    //引入组件
    import TheHeader from '@/components/the-header.vue';
    import TheFooter from '@/components/the-footer.vue';

    export default defineComponent({
        name: 'app',
        components: {
            TheHeader,
            TheFooter,
    },
    });
</script>

<style>
    #components-layout-demo-top-side-2 .logo {
        float: left;
        width: 120px;
        height: 31px;
        margin: 16px 24px 16px 0;
        background: rgba(255, 255, 255, 0.3);
    }

</style>