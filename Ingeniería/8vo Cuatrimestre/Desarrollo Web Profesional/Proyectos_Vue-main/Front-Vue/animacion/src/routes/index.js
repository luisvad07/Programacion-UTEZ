import Vue from "vue";
import VueRouter from "vue-router";

Vue.use(VueRouter);

import DragAndDrop from '../views/DragAndDrop/DragAndDrop.vue'
import DragAndDrop1 from '../views/DragAndDrop/DragAndDrop1.vue'
import DragAndDrop2d from '../views/DragAndDrop/DragAndDrop2d.vue'

import Carousel from '../views/Carousel.vue'
import Scroll from '../views/Scroll.vue'

import InOutElements from '../views/imageAnimations/InOutElements.vue'
import ChangeSize from '../views/imageAnimations/ChangeSize.vue'
import SlideElement from '../views/imageAnimations/SlideElement.vue'
import Transitions from '../views/imageAnimations/Transitions.vue'

import AnimatedElements from '../views/AnimatedElements.vue'

const routes = [
    
    { path: '/', component: DragAndDrop1 },
    { path: '/1dd', component: DragAndDrop },
    { path: '/2ddd', component: DragAndDrop2d },

    { path: '/carousel', component: Carousel },
    { path: '/scroll', component: Scroll },

    { path: '/image1', component: InOutElements },
    { path: '/image2', component: ChangeSize },
    { path: '/image3', component: SlideElement },
    { path: '/image4', component: Transitions },

    { path : '/animated', component: AnimatedElements},
]

const router = new VueRouter({ routes, })
export default router;