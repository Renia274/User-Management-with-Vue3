<script setup>
import { useRouter } from 'vue-router'

const router = useRouter()

const goHome = () => {
  router.push('/')
}
</script>


<template>
  <div class="error-container">
    <h1>Page Not Found</h1>
    <p>The page you are looking for does not exist.</p>
    <button @click="goHome">Go to Home</button>
  </div>
</template>



<style scoped>
.error-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100vh;
  width: 100vw;
  text-align: center;
  background-color: #f8d7da;
  color: #721c24;
}

.error-container h1 {
  font-size: 2rem;
  margin-bottom: 1rem;
}

.error-container p {
  font-size: 1.2rem;
  margin-bottom: 2rem;
}

.error-container button {
  padding: 0.5rem 1rem;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.error-container button:hover {
  background-color: #0056b3;
}
</style>
