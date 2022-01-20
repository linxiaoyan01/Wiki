<template>
  <a-layout>
    <a-layout-content :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }">
      <a-row :gutter="24">
        <a-col :span="8">
          <p>
            <a-form layout="inline" :model="param">
              <a-form-item>
                <a-button type="primary" @click="handleQuery()" >
                  查询
                </a-button>
              </a-form-item>
              <a-form-item>
                <a-button type="primary" @click="add()">
                  新增
                </a-button>
              </a-form-item>
            </a-form>
          </p>
          <a-table
              v-if="level1.length > 0"
              :columns="columns"
              :row-key="record => record.id"
              :data-source="level1"
              :loading="loading"
              :pagination="false"
              size="small"
              :defaultExpandAllRows="true"
          >
            <template #name="{ text, record }">
              {{record.sort}} {{text}}
            </template>
            <template v-slot:action="{text, record}">
              <a-space size="small">
                <a-button type="primary" @click="edit(record)" size="small">
                  编辑
                </a-button>
                <a-popconfirm
                    title="删除不可恢复，确认删除?"
                    ok-text="Yes"
                    cancel-text="No"
                    @confirm="handleDelete(record.id)"
                >
                  <a-button type="danger" size="small">
                    删除
                  </a-button>
                </a-popconfirm>
              </a-space>
            </template>
          </a-table>
        </a-col>
        <a-col :span="16">
          <p>
            <a-form layout="inline" :model="param">
              <a-form-item>
                <a-button type="primary" @click="handleSave()">
                  保存
                </a-button>
              </a-form-item>
            </a-form>
          </p>
          <a-form :model="doc" layout="vertical">
            <a-form-item>
              <a-input v-model:value="doc.name" placeholder="名称"/>
            </a-form-item>
            <a-form-item>
              <a-tree-select
                  v-model:value="doc.parent"
                  style="width: 100%"
                  :dropdown-style="{ maxHeight: '400px', overflow: 'auto' }"
                  :tree-data="treeSelectData"
                  placeholder="请选择父文档"
                  tree-default-expand-all
                  :replaceFields="{title:'name', key:'id',value:'id'}"
              >
              </a-tree-select>
            </a-form-item>
            <a-form-item>
              <a-input v-model:value="doc.sort" placeholder="顺序"/>
            </a-form-item>
            <a-form-item>
              <div id="content"></div>
            </a-form-item>
          </a-form>
        </a-col>
      </a-row>

    </a-layout-content>
  </a-layout>

<!--  <a-modal-->
<!--    title="文档"-->
<!--    v-model:visible="modalVisible"-->
<!--    :confim-loading="modalLoading"-->
<!--    @ok="handleModalOk"-->
<!--    >-->
<!--  </a-modal>-->
</template>

<script lang="ts">
 import {defineComponent, onMounted, ref} from 'vue';
 import axios from 'axios';
 import {message} from 'ant-design-vue'
 import {Tool} from "@/util/tool"
 import {useRoute} from "vue-router";
 import E from 'wangeditor';

 export default defineComponent({
   name: 'AdminDoc',
   setup(){

     const route = useRoute();
     console.log("路由：", route);
     console.log("route.path",route.path);
     console.log("route.query:",route.params);
     console.log("route.fullPath:", route.fullPath);
     console.log("route.name:",route.name);
     console.log("route.meta:",route.meta);

     const param = ref();
     param.value = {};

     //表单
     //因为数选择组件的属性状态，会随当前编辑的节点而变化，所以单独声明一个响应式变量
     const treeSelectData = ref();
     treeSelectData.value = [];

     const doc = ref();
     doc.value = {}
     const docs = ref();
     const loading = ref(false);
     const columns = [
       {
         title: '名称',
         dataIndex: 'name',
         slots: {customRender: 'name'}
       },
       {
         title:'Action',
         key:'action',
         slots: {customRender: 'action'}
       }
     ];
     const level1 = ref();
     level1.value = [];
     const modalVisible = ref(false);
     const modalLoading = ref(false);
     let editor: E;
     editor = new E('#content');
     editor.config.zIndex = 0;
     let ids: Array<string>[] = [];
     const handleSave = () =>{
       modalLoading.value = true;
       doc.value.content=editor.txt.html();
       console.log(doc.value)
       axios.post("/doc/save", doc.value).then((response)=>{
         modalLoading.value = false;
         const data = response.data; //data = commonResp
         if(data.success){
           modalVisible.value = false;
           //重新加载列表
           handleQuery();
         }else{
           message.error(data.message);
         }
       });
     };
     const handleQuery = ()=>{
       loading.value = true;
       axios.get("/doc/all").then((response)=>{
         loading.value = false;
         const data = response.data;
         if(data.success){
           docs.value = data.content;
           level1.value = [];
           level1.value = Tool.array2Tree(docs.value,0);
           console.log("树形结构:",level1);
         }else {
           message.error(data.message)
         }
       });
     };
     const setDisable = (treeSelectData: any, id: any) =>{
       for(let i = 0; i < treeSelectData.length; i++){
         const node = treeSelectData[i];
         if(node.id === id){
           //如果目前节点就是目标节点
           console.log("disabled", node);
           node.disable = true;
           //遍历所有子节点，将所有子节点全部都加上disabled
           const children = node.children;
           if(Tool.isNotEmpty(children)){
             for(let j = 0; j < children.length; j++){
               setDisable(children, children[j].id)
             }
           }
         }else {
           //如果当前节点不是目标节点，则到其子节点再找找看
           const children = node.children;
           if(Tool.isNotEmpty(children)){
             setDisable(children, id)
           }
         }
       }
     }
     const edit = (record: any)=>{

       modalVisible.value = true;

       doc.value = Tool.copy(record);

       //不能选择当前节点机器所有子孙节点，作为父节点，会使树断开
       treeSelectData.value = Tool.copy(level1.value);
       setDisable(treeSelectData.value, record.id);
       //为选择树添加一个无
       treeSelectData.value.unshift({id:0, name:'无'});
     };
     //查找整根树枝
     const getDeleteIds = (treeSelectData: any, id: any) =>{
       for(let i = 0; i < treeSelectData.length; i++){
         const node = treeSelectData[i];
         if(node.id === id){
           //如果目前节点就是目标节点
           console.log("disabled", node);
           //node.disable = true;
           //将目标id放入结果集ids
           ids.push(id);
           //遍历所有子节点
           const children = node.children;
           if(Tool.isNotEmpty(children)){
             for(let j = 0; j < children.length; j++){
               getDeleteIds(children, children[j].id)
             }
           }
         }else {
           //如果当前节点不是目标节点，则到其子节点再找找看
           const children = node.children;
           if(Tool.isNotEmpty(children)){
             getDeleteIds(children, id)
           }
         }
       }
     }
     const add = ()=>{
       modalVisible.value = true;
       doc.value ={
         ebookId: route.query.ebookId
       }

       treeSelectData.value = Tool.copy(level1.value);

       //为选择树添加一个无
       treeSelectData.value.unshift({id: 0, name: '无'})
     }
     const handleDelete = (id: number)=>{
       getDeleteIds(level1.value,id);
       axios.delete("/doc/delete/"+ids.join(",")).then((response)=>{
         const data = response.data; //data = commonResp
         if(data.success){
           //重新加载列表
           handleQuery();
         }
       });
     }
     onMounted(()=>{
       handleQuery();
       editor.create();
     });
     return {
       param,
       docs,
       level1,
       columns,
       loading,
       edit,
       add,
       handleDelete,
       doc,
       modalVisible,
       modalLoading,
       handleSave,
       handleQuery,
       treeSelectData

     }
   }
 })
</script>

