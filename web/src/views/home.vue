<template>
  <a-layout>
    <a-layout-sider width="200" style="background: #fff">
      <a-menu
          mode="inline"
          v-model:selectedKeys="selectedKeys2"
          v-model:openKeys="openKeys"
          :style="{ height: '100%', borderRight: 0 }"
          @click="handleClick"
      >
        <a-menu-item key="welcome">
            <MailOutlined />
            <span>欢迎</span>
        </a-menu-item>
        <a-sub-menu v-for="item in level1" :key="item.id">
          <template v-slot:title>
            <span><user-outlined />{{item.name}}</span>
          </template>
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
        <h1>欢迎</h1>
      </div>
      <a-list v-show="!isShowWelcome" item-layout="vertical" size="large" :data-source="ebooks" :grid="{gutter:20,column:3}">
        <template #renderItem="{ item }">
          <a-list-item key="item.name">
            <template #actions>
              <span v-for="{ type, text } in actions" :key="type">
                <component v-bind:is="type" style="margin-right: 8px" />
                {{ text }}
              </span>
            </template>
            <a-list-item-meta :description="item.description">
              <template #title>
                <router-link :to="'/doc?ebookId='+item.id">
                  {{item.name}}
                </router-link>
              </template>
              <template #avatar><a-avatar :src="item.cover" /></template>
            </a-list-item-meta>
            {{ item.content }}
          </a-list-item>
        </template>
      </a-list>
    </a-layout-content>
  </a-layout>
</template>

<script lang="ts">

import { defineComponent, onMounted, ref, reactive, toRef } from 'vue';
import axios from 'axios';
import { Tool } from '@/util/tool';
import { message } from 'ant-design-vue';
export default defineComponent({

  name: 'Home',
  setup(){
    //console.log("setup")
    const ebooks = ref();
    //const ebooks1 = reactive({books: []});


    const handleQueryEbook = ()=>{
      axios.get("/ebook/list",{
        params:{
          page:1,
          size:1000,
          categoryId2: categoryId2
        }
      }).then(function (response){
        const data = response.data;
        ebooks.value = data.content.list;
        //ebooks1.books = data.content;
        //console.log(response)
      });
    }
    onMounted(()=>{
      handleQueryCategory();
      //console.log("onMounted");
      //handleQueryEbook();
    });
    const pagination = {
      onChange: (page: number) => {
        console.log(page);
      },
      pageSize: 3,
    };
    const actions: Record<string, string>[] = [
      { type: 'StarOutlined', text: '156' },
      { type: 'LikeOutlined', text: '156' },
      { type: 'MessageOutlined', text: '2' },
    ];
    const openKeys =  ref();

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

          // 加载完分类后，将侧边栏全部展开
          openKeys.value = [];
          for (let i = 0; i < categorys.length; i++) {
            openKeys.value.push(categorys[i].id)
          }

          level1.value = [];
          level1.value = Tool.array2Tree(categorys, 0);
          console.log("树形结构：", level1.value);
        } else {
          message.error(data.message);
        }
      });
    };
    const isShowWelcome = ref(true);
    let categoryId2 = 0;
    const handleClick = (value: any) => {
      console.log("menu click", value);
      if(value.key === 'welcome'){
        isShowWelcome.value = true;
      }else {
        categoryId2 = value.key
        isShowWelcome.value = false;
        handleQueryEbook();
      }
    };

    return{
      ebooks,
      //ebooks2: toRef(ebooks1, "books"),
      pagination,
      actions,
      handleClick,
      level1,
      isShowWelcome
    }

  }

});
</script>
<style scoped>
  .ant-avatar{
    width: 50px;
    height: 50px;
    line-height: 50px;
    border-radius: 8%;
    margin: 5px 0;
  }
</style>
