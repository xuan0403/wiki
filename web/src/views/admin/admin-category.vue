<template>
    <div class="note" :style ="note"></div>
  <a-layout>
    <a-layout-content :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }"
    >
      <p>
          <a-form
                  layout="inline"
                  :model="param"
          >
              <a-form-item>
                  <a-input v-model:value="param.name" placeholder="名称">
                  </a-input>
              </a-form-item>
              <a-form-item>
                  <!--{page : 1,size : pagination.pageSize})为json对象-->
                  <a-button type="primary" @click="handleQuery()">
                      查询
                  </a-button>
              </a-form-item>
              <a-form-item>
                  <a-button type="primary" @click="add()" size="large">
                      新增
                  </a-button>
              </a-form-item>
          </a-form>

      </p>
      <a-table
              :columns="columns"
              :row-key="record => record.id"
              :data-source="categorys"
              :loading="loading"
              :pagination="false"
      >
        <!--定义渲染-->
        <template #cover="{ text: cover }">
          <img v-if="cover" :src="cover" alt="avatar" />
        </template>
        <template v-slot:action="{ text, record }">
          <!--a-space代表两个按钮之间有空格-->
          <a-space size="small">
            <a-button type="primary" @click="edit(record)">
              编辑
            </a-button>

              <a-popconfirm
                      title="删除后不可回复，是否确认删除？"
                      ok-text="是"
                      cancel-text="否"
                      @confirm="handleDelete(record.id)"
              >
                  <a-button type="danger">
                      删除
                  </a-button>
              </a-popconfirm>

          </a-space>
        </template>
      </a-table>
    </a-layout-content>
  </a-layout>

    <a-modal
            title="分类表单"
            v-model:visible="modalVisible"
            :confirm-loading="modalLoading"
            @ok="handleModalOk"
    >
        <a-form :model="category" :label-col="{ span: 6 }" :wrapper-col="{ span: 18 }">
            <a-form-item label="名称">
                <a-input v-model:value="category.name" />
            </a-form-item>
            <a-form-item label="父分类">
                <a-input v-model:value="category.parent" />
            </a-form-item>
            <a-form-item label="顺序">
                <a-input v-model:value="category.sort" />
            </a-form-item>
        </a-form>
    </a-modal>
</template>

<script lang="ts">
  import {defineComponent, onMounted, ref} from 'vue';
  import axios from 'axios';
  import { message } from 'ant-design-vue';
  import {Tool} from "@/util/tool";

  export default defineComponent({
    name: 'AdminCategory',
    setup() {
      const param = ref();
      /*初始需要加此空对象，不加会报错*/
      param.value = {};
      /*定义分类*/
      const categorys = ref();

      const loading = ref(false);

      const columns = [
        {
          title: '名称',
          dataIndex: 'name'
        },
        {
          title: '父分类',
          key: 'parent',
          dataIndex: 'parent'
        },
        {
          title: '顺序',
          dataIndex: 'sort'
        },
        {
            title: 'Action',
                key: 'action',
            slots: {customRender: 'action'}
        }
      ];

      /**
       * 数据查询
       **/
      /*用axios调用后端的接口*/
      const handleQuery = () => {
        loading.value = true;
        axios.get("/category/all").then((response) => {
          loading.value = false;
          const data = response.data;

          if(data.success)
          {
              categorys.value = data.content;
          }else{
              message.error(data.message);
          }

        });
      };


        //-------------表单-------------
        const category=ref({});
        const modalVisible = ref(false);
        const modalLoading = ref(false);
        const handleModalOk = () => {
            modalLoading.value = true;
            //此category代表绑定到表单的category
            axios.post("/category/save", category.value).then((response) => {
                modalLoading.value=false;
                const data = response.data;  //data=CommonResp
                if(data.success){
                    modalVisible.value = false;
                    //重新加载列表
                    handleQuery();
                }
                else {
                    message.error(data.message);
                }
            });
        };

        /*编辑*/
        //record对应表数据，any表示它是任何类型都可以，其实他就是一个json对象。将record赋值到刚定义的category这个响应式变量
        //这样我们的表但就可以拿到当前点击的这一行的数据
        const edit = (record: any) => {
            modalVisible.value = true;
            category.value=Tool.copy(record);
        };

        /*新增*/
        const add = () => {
            modalVisible.value = true;
            category.value={};
        };

        /*删除*/
        const handleDelete = (id : number) => {
            axios.delete("/category/delete/" + id).then((response) => {
                const data = response.data;  //data=CommonResp
                if(data.success){
                    //重新加载列表
                    handleQuery();
                }
            });
        };

      //page,size要与PageReq一致
      onMounted(() => {
        handleQuery();
      });

      return {
          //表格
        param,
        categorys,
        columns,
        loading,
        handleQuery,

          //方法
        edit,
        add,
        handleDelete,

          //表单
        category,
        modalVisible,
        modalLoading,
        handleModalOk

      }
    }
  });
</script>

<style scoped>
  img {
    width: 50px;
    height: 50px;
  }

  #logo{
      background: url("../../assets/background1.jpg");
      background-size: 100% 100%;
      height: 100%;
  }
</style>
