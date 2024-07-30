import { createApp } from 'vue';
import App from './App.vue';
import router from './router';
import './assets/main.css';

const app = createApp(App);

app.use(router);

app.config.errorHandler = (err, vm, info) => {
    // Redirect to error page with the error message
    router.push({ name: 'error', query: { message: err.message } })
        .catch((navigationError) => {
            // Handle any errors that occur during navigation
            console.error('Error navigating to error page:', navigationError);
        });
    console.error('Global Error Handler:', err);
};

app.mount('#app');
