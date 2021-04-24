<!--此页面为vue初始页面-->
<!--不变的部分写在App.vue，变化的部分写在Home.vue，此变化部分在App.vue里写为 <router-view/>-->
<template>
  <a-layout id="components-layout-demo-top-side-2">
    <a-layout-header class="header">
      <a-menu
              theme="dark"
              mode="horizontal"
              v-model:selectedKeys="selectedKeys1"
              :style="{ lineHeight: '64px' }"
      >
        <a-menu-item key="1">nav 1</a-menu-item>
        <a-menu-item key="2">nav 2</a-menu-item>
        <a-menu-item key="3">nav 3</a-menu-item>
      </a-menu>
    </a-layout-header>
      <router-view/>
      <a-layout-footer style="text-align: center">
          wiki知识库
      </a-layout-footer>
  </a-layout>
</template>


<style>
    #components-layout-demo-top-side-2 .logo {
        float: left;
        width: 120px;
        height: 31px;
        margin: 16px 24px 16px 0;
        background: rgba(255, 255, 255, 0.3);
    }

</style>