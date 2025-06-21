/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./index.html",
    "./src/**/*.{vue,js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        fdoscuro: '#03071E',
        fdvino: '#370617',
        fdvinodos: '#6A040F',
        fdvinotres: '#9D0208',
        fdrojo: '#D00000',
        fdnaranja: '#DC2F02',
        fdnaranjados: '#E85D04',
        fdmostaza: '#F48C06',
        fdamarillo: '#FAA307',
        fdamarillodos: '#FFBA08',
      },
    },
  },
  plugins: [],
}
