const API_KEY = 'P4rxgp7JvalaOH8yQVrtosNuWf57ocfT';
const inputSearch = document.getElementById("search");
const btn = document.getElementById("btn");
const url = `https://api.giphy.com/v1/gifs/search`;
const rand = document.getElementById("gifRandom");

btn.onclick = () => {
    document.getElementById("portfolio").innerHTML = ""
    data();
};


const data = async () => {
    console.log(inputSearch.value);
    await fetch(`https://api.giphy.com/v1/gifs/search?api_key=${API_KEY}&q=${inputSearch.value}`).then((response) => {
       return response.json();
    }).then((giphy)=>{
        console.log(giphy);

        for (let i = 0; i < giphy.data.length; i++) {
            const gif = document.createElement('img');
            gif.src = giphy.data[i].images["original"].url
            gif.className = "mb-3";
            document.getElementById("portfolio").appendChild(gif)  
        }
    });
}; 

const aletorio = async () =>{
    let urlr = `https://api.giphy.com/v1/gifs/random?api_key=${API_KEY}`;
    const response = await fetch(urlr);
    const gifs = await response.json();
    const {url} = gifs.data.images.original

    console.log(url);
    document.getElementById("random").innerHTML =`
        <img src="${url}" width="60%" class="rounded mx-auto d-block" >
    `;
    gifs.innerHTML = '';
};

rand.onclick = () => {
    aletorio();
    document.addEventListener('DOMContentLoaded', change);
};

/*
    inputSearch.onkeyup = async (event) => {
    if (event.keyCode !== 13) return;
    const response = await fetch(`http://api.giphy.com/v1/gifs/search?api_key=${API_KEY}&q=${inputSearch.value}`);
        const data = await response.json();
        console.log(data);
        let content = ``;
        data.data.map((gif, index)=>{
            content += `
            <img src="${gif.images.original.url}" class="mb-3 ms-1 thumbail" alt="${gif.title}">
            `;
        });
        gif.innerHTML = content;
    };
*/