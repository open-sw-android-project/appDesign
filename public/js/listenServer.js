// var getJSON = function(url, callback) {
//     var xhr = new XMLHttpRequest();
//     xhr.open('GET', url, true);
//     xhr.responseType = 'json';
//     xhr.onload = function() {
//       var status = xhr.status;
//       if (status === 200) {
//         callback(null, xhr.response);
//       } else {
//         callback(status, xhr.response);
//       }
//     };
//     xhr.send();
// };


jQuery(document).ready(function($){
    // standard on load code goes here with $ prefix
    // note: the $ is setup inside the anonymous function of the ready command
    });

// $('#qr_btn').on('click', () => {
//     $.getJSON('http://192.168.0.184/package.json', (data) => {
//       console.log(data);
//     });

//     console.log("hi \n");
//   });
const subName = document.querySelector('.sub_js');

subName.innerText = "함수가돌아가긴함";

var pwd=0;
var subject;

window.addEventListener('load', (event) => {
    $.getJSON('/public/js/qr.json', (data) => {
        pwd = data.password;
        subject = data.name;

        subName.innerText = subject;

        console.log(data+"\n");
        console.log(pwd+"\n");
        console.log(subject+"\n");
      });
});


//10초마다 새로고침
//setInterval(function(){location.reload()}, 10000);