import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'

createApp(App).use(store).use(router).mount('#app')
/*通过main.ts将App.vue和index.html关联起来
* vue cli初始执行main.ts，将页面App.vue内容渲染到index.html，完成页面显示
* */
