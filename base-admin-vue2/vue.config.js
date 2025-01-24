const { defineConfig } = require('@vue/cli-service')

module.exports = defineConfig({
  transpileDependencies: true,
  lintOnSave: false,
  devServer: {
    host: '0.0.0.0',
    port: 8081,
    proxy: {
      // '/onlyoffice': {
      //   target: process.env.VUE_APP_BASE_API,
      //   changeOrigin: true
      // }
    }
  }
})
