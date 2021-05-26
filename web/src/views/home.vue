<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
  <a-layout>
    <a-layout-sider width="200" style="background: #fff">
      <a-menu
              mode="inline"
              :style="{ height: '100%', borderRight: 0 }"
              @click="handleClick"
      >
        <!--欢迎菜单-->
        <a-menu-item key="welcome">
          <!--router-link跳转到其他页面，当前页面切换不使用-->
          <!--<router-link :to="'/'">-->
            <MailOutlined />
            <span>欢迎</span>
          <!--</router-link>-->
        </a-menu-item>
        <!--循环level1，是一个树形结构，两级分类-->
        <a-sub-menu v-for="item in level1" :key="item.id">
          <!--主菜单，一级菜单-->
          <template v-slot:title>
            <span><user-outlined />{{item.name}}</span>
          </template>
          <!--二级菜单-->
          <a-menu-item v-for="child in item.children" :key="child.id">
            <MailOutlined /><span>{{child.name}}</span>
          </a-menu-item>
        </a-sub-menu>
      </a-menu>

    </a-layout-sider>
    <a-layout-content
            :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }"
    >
      <div class="welcome" v-show="isShowWelcome">
        <h1>欢迎使用wiki知识库</h1>

      </div>
      <!--电子书列表显示-->
      <a-list v-show="!isShowWelcome" item-layout="vertical" size="large" :grid="{ gutter: 20, column: 3 }" :data-source="ebooks">
        <!--此item代表一个个的电子书，他会自动循环里面的ebooks，将每一个电子书设置成item变量-->
        <template #renderItem="{ item }">
          <!--使用item.xxx去访问电子书的数据-->
          <a-list-item key="item.name">
            <template #actions>
                <span v-for="{ type, text } in actions" :key="type">
                  <component v-bind:is="type" style="margin-right: 8px" />
                  {{ text }}
                </span>
            </template>
            <a-list-item-meta :description="item.description">
              <template #title>
                <!--页面跳转-->
                <a :href="item.href">{{ item.name }}</a>
              </template>
              <!--图标-->
              <template #avatar><a-avatar :src="item.cover" /></template>
            </a-list-item-meta>
          </a-list-item>
        </template>
      </a-list>

      <!--pre标签会把里面长什么样子，原封不动的全部展示在页面上-->
     <!-- <pre>
        {{ebooks}}
        {{ebooks2}}
      </pre>-->

    </a-layout-content>
  </a-layout>

</template>

<script lang="ts">
import { defineComponent,onMounted,ref,reactive } from 'vue';
import TheHeader from '@/components/the-header.vue';
import TheFooter from '@/components/the-footer.vue'; // @ is an alias to /src
import axios from 'axios';
import { message } from 'ant-design-vue';
import {Tool} from "@/util/tool";

import {response} from "express";

// const listData: any = [];
// for (let i = 0; i < 23; i++) {
//   listData.push({
//     href: 'https://www.antdv.com/',
//     title: `ant design vue part ${i}`,
//     avatar: 'https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png',
//     description:
//             'Ant Design, a design language for background applications, is refined by Ant UED Team.',
//     content:
//             'We supply a series of design principles, practical patterns and high quality design resources (Sketch and Axure), to help people create their product prototypes beautifully and efficiently.',
//   });
// }

export default defineComponent({
  name: 'Home',
  setup(){ //setup相当于入口
    //方法1：将一个数据变成ref变量
    //他是一个响应式的数据。所谓响应式数据就是在js里面，动态的修改这里面的值，需要实时反馈到页面上，就需要变为一个响应式数据
    //用ref可以使其变为响应式数据，vue3新增了ref，用来定义响应式数据
    const ebooks=ref();
    //reactive里面必须要用对象reactive({})，在空对象里面添加一个books属性，它对应的值，就放一个空数组，此段为一个json对象
   // const ebooks1=reactive({books:[]});

    const level1 =  ref();
    let categorys: any;
    /**
     * 查询所有分类
     **/
    const handleQueryCategory = () => {
      axios.get("/category/all").then((response) => {
        const data = response.data;
        if (data.success) {
          categorys = data.content;
          console.log("原始数组：", categorys);

          level1.value = [];
          level1.value = Tool.array2Tree(categorys, 0);
          console.log("树形结构：", level1.value);
        } else {
          message.error(data.message);
        }
      });
    };

    //定义变量，初始时显示welcome
    const isShowWelcome =  ref(true);
    let categoryId2 = 0;

    const handleQueryEbook =() => {
      //初始化的逻辑都写到onMounted方法里，setup就放一些参数定义、方法定义
      axios.get("/ebook/list",{
        params: {
          page: 1,
          size: 1000,
          categoryId2: categoryId2
        }
      }).then(function (response) { //从response里把电子书对应数据拿出来
        const data=response.data;  //data=CommonResp  在response里面有一个data，这个data就对应的是后端CommonResp的数据结构
        ebooks.value=data.content.list             //content对应电子书列表，要把内容显示到页面上，需要定义一个变量
        //ebooks1.books=data.content
        //console.log(response);
      });
    };


    //测试方法，点击某一菜单，输出日志
    const handleClick = (value: any) => {
      // console.log("menu click", value)
      if (value.key === 'welcome') {
        isShowWelcome.value = true;
      } else {
        categoryId2 = value.key;
        isShowWelcome.value = false;
        handleQueryEbook();
      }
      // isShowWelcome.value = value.key === 'welcome';
    };



    //生命周期函数
    onMounted(function () {
      //初始应该加载所有的分类
      handleQueryCategory();
      //handleQueryEbook();

    });

    return {
      ebooks,
      //ebooks2: toRef(ebooks1,"books")
      //listData,
      pagination: {
        onChange: (page:any) => {
          console.log(page);
        },
        pageSize: 3,
      },
      actions:[
        { type: 'StarOutlined', text: '156' },
        { type: 'LikeOutlined', text: '156' },
        { type: 'MessageOutlined', text: '2' },
      ],
      handleClick,
      level1,
      isShowWelcome
    }
  }
});
</script>

<!--scope表示加的样式只在当前组件起作用-->
<style scoped>
  .ant-avatar {
    width: 50px;
    height: 50px;
    line-height: 50px;
    border-radius: 8%;
    margin: 5px 0;
  }
</style>
