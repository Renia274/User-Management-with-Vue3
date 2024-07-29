import { fileURLToPath, URL } from 'node:url'
import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  },
  build: {
    outDir: '../../../../target/classes/static/vite-project',
    emptyOutDir: true
  },
  base: '/vite-project/',
  define: {
    'process.env.VUE_ROUTER_BASE': '"/v/"'
  },
  server: {
    open: true,
    proxy: {
      '/api/': {
        target: 'http://localhost:8080',
        changeOrigin: true
      }
    }
  }
})
