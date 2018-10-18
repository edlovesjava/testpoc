

export default class VividSeats {
    constructor() {
        this._events = [];
    }

    all(onSuccess, onError) {

        fetch('http://localhost:8080/events')
        .then(result=>result.json())
        .then(items=>{
            this._events = items;
            onSuccess(items);
        })
    }

    add(event, onSuccess, onError) {

        this.postData('http://localhost:8080/events', event)
                .then(item => onSuccess(item))
                .catch(error => onError(error));


    }

    postData(url = '', payload = {}) {
        var data = new FormData();
        data.append("eventJson", JSON.stringify(payload))
        return fetch(url, {
              method: "POST",
              body: data,
        })
        .then(response => response.json())
    }

}


