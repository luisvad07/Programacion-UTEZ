const { nextui } = require("@nextui-org/react");

/** @type {import('tailwindcss').Config} */
export default {
  content: ["./src/**/*.{html,js}",
    "./node_modules/@nextui-org/theme/dist/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      margin: {
        '20px': '20px',
      },
      fontFamily: {
        sans: ['Poppins', 'sans-serif'],
      },
    },
  },
  darkMode: "class",
  plugins: [nextui({
    themes: {
      dark: {
        colors: {
          default: {
            DEFAULT: "#000000",
            foreground: "#ffffff",
          },
          primary: {
            DEFAULT: "#f50057",
            focus: "#ff4081",
          },
          focus: "#ffffff",
        }
      }
    }
  })],
}