const clock = document.querySelector(".clock");

function getTime(){
    const date = new Date();

    const year = String(date.getFullYear());
    const month = String(date.getMonth()+1).padStart(2, "0");
    const day = String(date.getDate()).padStart(2, "0");
    const hours = String(date.getHours()).padStart(2,"0");
    const minutes = String(date.getMinutes()).padStart(2,"0");
    const seconds = String(date.getSeconds()).padStart(2,"0");
    
    clock.innerText = `${year}/${month}/${day} - ${hours}시 ${minutes}분 ${seconds}초`;
}

getTime();
setInterval(getTime, 1000);