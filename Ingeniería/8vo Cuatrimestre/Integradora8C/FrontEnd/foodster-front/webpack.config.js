// webpack.config.js

module.exports = {
    module: {
      rules: [
        {
          test: /\.s[ca]ss$/,
          use: [
            'vue-style-loader',
            'css-loader',
            {
              loader: 'sass-loader',
              // Requires sass-loader@^7.0.0
              options: {
                implementation: require('sass'),
                indentedSyntax: true // optional
              },
            },
          ],
        },
      ],
    }
  }