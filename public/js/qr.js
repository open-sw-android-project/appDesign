const menu_btn = document.querySelector("#menu_js");
const menu_container = document.querySelector(".menu_container");

const qr_btn = document.querySelector("#qr_btn");
const qr_info = document.querySelector(".qr_info");
const qr_code = document.querySelector(".qr_code");

const exit_btn = document.querySelector("#exit");

let qrcode = new QRCode(document.querySelector(".qr_code"),{
    width: 540,
    height: 540
});

function makeCode () {    
    //let code = "QWERQWER000000";//강좌코드 + 교수코드 + 랜덤코드 
    let code = pwd;

    if (!code) {
      alert("There is no code!");
      return;
    }
    
    qrcode.makeCode(code);
}

menu_btn.addEventListener('click', function(){
    menu_container.classList.toggle('display_none');
})

exit_btn.addEventListener('click', function(){
    menu_container.classList.toggle('display_none');
})

qr_btn.addEventListener('click', function(){
    qr_info.classList.toggle('display_none');
    qr_code.classList.toggle('display_none');
    makeCode();
})