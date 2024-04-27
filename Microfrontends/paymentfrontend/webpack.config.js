const HtmlWebPackPlugin = require("html-webpack-plugin");
const { ModuleFederationPlugin } = require("webpack").container;
const path = require("path");
const { dependencies } = require("./package.json");


const htmlPlugin = new HtmlWebPackPlugin({
  template: "./public/index.html",
});
module.exports = {
  entry: "./src/index",
  mode: 'development',
  devServer: {
    port: 3003,
    historyApiFallback: true,
  },

  module: {
    rules: [
      {
        test: /\.(js|jsx)?$/,
        exclude: /node_modules/,
        use: [
          {
            loader: "babel-loader",
            options: {
              presets: ["@babel/preset-env", "@babel/preset-react"],
            },
          },
        ],
      },
      {
        test: /\.css$/i,
        use: ["style-loader", "css-loader"],
      },
    ],
  },
  plugins: [
    htmlPlugin,
    new ModuleFederationPlugin({
      name: "PaymentApp",
      filename: "remoteEntry.js",
      exposes: {
        "./App": "./src/App",
        "./Home":"./src/Home",
        "./Create":"./src/CreatePayment",
        "./Update":"./src/UpdatePayment",
        "./Navbar":"./src/Navbar"
      },
      remotes: {
        UserApp: "UserApp@http://localhost:3001/remoteEntry.js",
        TicketApp:"TicketApp@http://localhost:3002/remoteEntry.js",
        PaymentApp: "PaymentApp@http://localhost:3003/remoteEntry.js",
        Shell: "Shell@http://localhost:3000/remoteEntry.js"

      },
      shared: { react: { singleton: true, eager: true }, "react-dom": { singleton: true, eager: true } },
    })
  ],
  resolve: {
    extensions: [".js", ".jsx"],
  },
};