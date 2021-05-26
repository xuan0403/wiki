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
                  <a-button type="primary" @click="handleQuery({page : 1,size : pagination.pageSize})">
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
              :data-source="ebooks"
              :pagination="pagination"
              :loading="loading"
              @change="handleTableChange"
      >
        <!--定义渲染-->
        <template #cover="{ text: cover }">
          <img v-if="cover" :src="cover" alt="avatar" />
        </template>
          <!--新增渲染，因为分类不是普通字段，它是一种组合，是自定义的显示方式，渲染的名字是category，它会自动带上两个参数text, record -->
          <!--如果不带具体字段渲染，text和record是一样的-->
          <template v-slot:category="{ text, record }">
              <!--显示的值，定义getCategoryName方法，通过传进来的id去得到它对应的名称。先获得一级分类的名称，再获得二级分类的名称，显示出来加/-->
              <span>{{ getCategoryName(record.category1Id) }} / {{ getCategoryName(record.category2Id) }}</span>
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
            title="电子书表单"
            v-model:visible="modalVisible"
            :confirm-loading="modalLoading"
            @ok="handleModalOk"
    >
        <a-form :model="ebook" :label-col="{ span: 6 }" :wrapper-col="{ span: 18 }">
            <a-form-item label="封面">
                <a-input v-model:value="ebook.cover" />
            </a-form-item>
            <a-form-item label="名称">
                <a-input v-model:value="ebook.name" />
            </a-form-item>
            <!--将分类变为级联组件，级联组件要绑定value，绑定的value实际上是一个数组-->
            <a-form-item label="分类">
                <!--重新定义一个变量categoryIds，这是一个新的响应式变量。一般下拉框会有显示的值和实际的值-->
                <a-cascader
                        v-model:value="categoryIds"
                        :field-names="{ label: 'name', value: 'id', children: 'children' }"
                        :options="level1"
                />
            </a-form-item>
            <a-form-item label="描述">
                <a-input v-model:value="ebook.description" type="textarea" />
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
    name: 'AdminEbook',
    setup() {
      const param = ref();
      /*初始需要加此空对象，不加会报错*/
      param.value = {};
      /*定义电子书*/
      const ebooks = ref();
      /*定义分页*/
      const pagination = ref({
        current: 1,
        pageSize: 5,
        total: 0
      });
      const loading = ref(false);

      const columns = [
        {
          title: '封面',
          dataIndex: 'cover',
          slots: {customRender: 'cover'}
        },
        {
          title: '名称',
          dataIndex: 'name'
        },
          {
              title: '分类',
              slots: { customRender: 'category' }
          },
          {
              title: '描述',
              dataIndex: 'description'
          },
        {
          title: '文档数',
          dataIndex: 'docCount'
        },
        {
          title: '阅读数',
          dataIndex: 'viewCount'
        },
        {
          title: '点赞数',
          dataIndex: 'voteCount'
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
      const handleQuery = (params: any) => {
        loading.value = true;
        // 如果不清空现有数据，则编辑保存重新加载数据后，再点编辑，则列表显示的还是编辑前的数据
        ebook.value=[];
        axios.get("/ebook/list", {
          params:{
            page: params.page,
            size: params.size,
            name: param.value.name
          }
        }).then((response) => {
          loading.value = false;
          const data = response.data;

          if(data.success)
          {
              ebooks.value = data.content.list;
              // 重置分页按钮
              pagination.value.current = params.page;
              pagination.value.total = data.content.total;
          }else{
              message.error(data.message);
          }

        });
      };

      /**
       * 表格点击页码时触发
       */
      const handleTableChange = (pagination: any) => {
          console.log("看看自带的分页参数都有啥：" + pagination);
          handleQuery({
              page: pagination.current,
              size: pagination.pageSize
          });
      };

        // -------- 表单 ---------
        /**
         * 数组，[100, 101]对应：前端开发 / Vue
         */
        const categoryIds = ref();
        const ebook = ref();
        const modalVisible = ref(false);
        const modalLoading = ref(false);
        const handleModalOk = () => {
            modalLoading.value = true;
            ebook.value.category1Id = categoryIds.value[0];
            ebook.value.category2Id = categoryIds.value[1];
            axios.post("/ebook/save", ebook.value).then((response) => {
                modalLoading.value = false;
                const data = response.data; // data = commonResp
                if (data.success) {
                    modalVisible.value = false;

                    // 重新加载列表
                    handleQuery({
                        page: pagination.value.current,
                        size: pagination.value.pageSize,
                    });
                } else {
                    message.error(data.message);
                }
            });
        };

        /*编辑*/
        //record对应表数据，any表示它是任何类型都可以，其实他就是一个json对象。将record赋值到刚定义的ebook这个响应式变量
        //这样我们的表但就可以拿到当前点击的这一行的数据
        const edit = (record: any) => {
            modalVisible.value = true;
            ebook.value=Tool.copy(record);
            categoryIds.value = [ebook.value.category1Id, ebook.value.category2Id]
        };

        /*新增*/
        const add = () => {
            modalVisible.value = true;
            ebook.value={};
        };

        /*删除*/
        const handleDelete = (id : number) => {
            axios.delete("/ebook/delete/" + id).then((response) => {
                const data = response.data;  //data=CommonResp
                if(data.success){
                    //重新加载列表
                    handleQuery({
                        //page: 1,//初始查第一页
                        page: pagination.value.current,  //重新查当前分页组件所在的页码
                        size: pagination.value.pageSize
                    });
                }
            });
        };

        const level1 =  ref();
        //定义普通变量
        let categorys: any;
        /**
         * 查询所有分类
         **/
        const handleQueryCategory = () => {
            loading.value = true;
            axios.get("/category/all").then((response) => {
                loading.value = false;
                const data = response.data;
                if (data.success) {
                    //赋值
                    categorys = data.content;
                    console.log("原始数组：", categorys);

                    level1.value = [];
                    level1.value = Tool.array2Tree(categorys, 0);
                    console.log("树形结构：", level1.value);

                    // 加载完分类后，再加载电子书，否则如果分类树加载很慢，则电子书渲染会报错
                    handleQuery({
                        page: 1,
                        size: pagination.value.pageSize,
                    });
                } else {
                    message.error(data.message);
                }
            });
        };

        //循环，循环刚刚定义的变量categorys
        const getCategoryName = (cid: number) => {
            // console.log(cid)
            let result = "";
            categorys.forEach((item: any) => {
                if (item.id === cid) {
                    // return item.name; // 注意，这里直接return不起作用
                    result = item.name;
                }
            });
            return result;
        };

        //page,size要与PageReq一致
      onMounted(() => {
          //初始的时候应该把所有的分类也查出来，所以要执行handleQueryCategory()方法
        handleQueryCategory();

      });

      return {
          //表格
        param,
        ebooks,
        pagination,
        columns,
        loading,
        handleTableChange,
        handleQuery,
        getCategoryName,

          //方法
        edit,
        add,
        handleDelete,

          //表单
        ebook,
        modalVisible,
        modalLoading,
        handleModalOk,
        categoryIds,
        level1

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
