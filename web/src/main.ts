/*启动文件*/
import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import Antd from 'ant-design-vue';
import 'ant-design-vue/dist/antd.css';
import * as Icons from '@ant-design/icons-vue';
import axios from 'axios';

//修改baseURL，此baseURL是axios自己的
axios.defaults.baseURL = process.env.VUE_APP_SERVER;

/**
 * axios拦截器
 */
/*拦截请求*/
axios.interceptors.request.use(function (config) {
    console.log('请求参数：', config);
    return config;
}, error => {
    return Promise.reject(error);
});
/*拦截返回*/
axios.interceptors.response.use(function (response) {
    console.log('返回结果：', response);
    return response;
}, error => {
    console.log('返回错误：', error);
    return Promise.reject(error);
});


const app = createApp(App);
app.use(store).use(router).use(Antd).mount('#app');
/*通过main.ts将App.vue和index.html关联起来
* vue cli初始执行main.ts，将页面App.vue内容渲染到index.html，完成页面显示
* */

// 全局使用图标
const icons: any = Icons;
for (const i in icons) {
    app.component(i, icons[i]);
}

/*打印日志环境，使用process.env.xxx读环境变量，xxx是配置文件里的名称*/
console.log('环境：', process.env.NODE_ENV);
console.log('服务端：', process.env.VUE_APP_SERVER);
