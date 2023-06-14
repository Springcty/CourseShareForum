module.exports = {
  productionSourceMap: false,
  devServer: {
    proxy: {
      "/api": {
        target: "http://101.37.254.247:9001",
        changeOrigin: true,
        pathRewrite: {
          "^/api": ""
        }
      },
      "/serverApi": {
        target: "http://101.37.254.247:9002/",
        changeOrigin: true,
        // secure: false,
        pathRewrite: {
          "^/serverApi": ""
        }
      }
    },
    disableHostCheck: true
  },
  chainWebpack: config => {
    config.resolve.alias.set("@", resolve("src"));
  }
};

const path = require("path");
function resolve(dir) {
  return path.join(__dirname, dir);
}
