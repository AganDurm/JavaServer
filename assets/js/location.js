const GET_REQUEST = "GET";
const URL_LOCAL = "http://localhost:8500/local";
let xhr = new XMLHttpRequest();

xhr.open(GET_REQUEST, URL_LOCAL, true);
xhr.send();
xhr.onreadystatechange = function() {
    if (this.readyState === 4 && this.status === 200 ) {
        let status = document.getElementById('status');
        status.innerText = "ONLINE";
        status.classList.add('online')
    } else if (this.status !== 200) {
        let status = document.getElementById('status');
        status.innerText = "OFFLINE";
        status.classList.add('offline')
    }
};

function reload() {
    event.preventDefault();
    let status = document.getElementById('status');
    if(status.classList.contains('online')) {
        status.classList.remove('online');
    } else if(status.classList.contains('offline')) {
        status.classList.remove('offline');
    }
    location.reload();
}
