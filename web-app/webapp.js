// import axios, { isCancel, AxiosError } from 'axios';

let recievedJSON;
let itemsJSON;

function submitBarcode() {
    let barcode = document.getElementById('barcode').value;
    let barcodeJSON = `{ "barcode": ${barcode} }`;

    console.log(barcode);
    console.log(barcodeJSON);
    recievedJSON = getItems(barcodeJSON);
    itemsJSON = recievedJSON.items;
    let totalPrice = recievedJSON.total;

    let jsonString = JSON.stringify(recievedJSON);
    document.getElementById("textarea").innerHTML = jsonString;

    buildTable(itemsJSON);
    updateTotals(totalPrice);

    populateRemoveItem(itemsJSON);
}

async function getItems(barcode) {
    
    const URL = "https://project-acme.herokuapp.com/barcode";

    try {
        let response = await fetch(URL, {
            method: 'POST',

            headers: {
                'Accept': 'application/json',
                "Content-Type": "application/json",
                'Access-Control-Request-Origin': 'http://localhost:8080/',
                'Access-Control-Request-Method': 'POST',
                
            },
            body: JSON.stringify({
                "barcode":123456789055
            })
        })
        console.log(response);
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        let items = await response.json();
        return items;

    } catch (error) {
        console.error(error);
    }
    
    /*
const URL = "https://project-acme.herokuapp.com/barcode";
const barcodeEntered = {
    barcode:123456789055
}
axios({
    method: 'post',
    url: URL,
    data: {
        barcodeEntered
    }
})
    .then(data => console.log(data))
    .catch(err => console.log(err))
*/
    /*
    fetch("https://project-acme.herokuapp.com/barcode", {
        method: 'POST',
        body: barcode,
        headers: {
            'Accept': 'application/json',
            'Content-type': 'application/json',
        },
        mode: "no-cors",
    })
        .then(function (response) {
            console.log(response);
            return response.json();
        })
        .then(function (data) {
            console.log(data);
            
        }).catch(error => console.error('Error:', error));
        */

};



function buildTable(data) {
    let table = document.getElementById('itemTable');
    table.innerHTML = "";

    for (let i = 0; i < data.length; i++) {
        let row = `<tr>
							<td>${data[i].name}</td>
							<td>${data[i].price}</td>
							<td>${data[i].quantity}</td>
					  </tr>`
        table.innerHTML += row;
    }
}

function populateRemoveItem(data) {
    let itemDropdown = document.getElementById('removeItemName');
    itemDropdown.innerText = null;

    itemDropdown.add(new Option('--Select--', 'Select'));

    for (let i = 0; i < data.length; i++) {
        itemDropdown.add(new Option(data[i].name, data[i].name));
    }
}

function updateTotals(newTotal) {
    total = document.getElementById('totalPrice');
    total.innerHTML = `Total Price: £${newTotal}`;

    let discountSavings = 0;

    for (let i = 0; i < itemsJSON.length; i++) {

        let quant = itemsJSON[i].quantity;

        while (quant >= 5) {
            discountSavings = discountSavings + (itemsJSON[i].price) * (0.5);
            quant = quant - 5;
        }
    }

    discount = document.getElementById('discountSavings');
    discount.innerHTML = `(Discount Savings: £${discountSavings})`.italics();
}

function removeItem() {

}

async function submitPayment() {
    const URL2 = "https://project-acme.herokuapp.com/creditCard";

    let paymentJSON =
    {
        "amount": recievedJSON.total,

        "creditCardNumber": document.getElementById('creditCardNumber').innerHTML,

        "expiryDate": document.getElementById('expiryDate').innerHTML,

        "cvc": document.getElementById('cvc').innerHTML,

        "address": document.getElementById('address').innerHTML,

        "postcode": document.getElementById('postcode').innerHTML,

        "accountHolderName": document.getElementById('accHolderName').innerHTML
    };

    try {
        let response = await fetch(URL2, {
            method: 'POST',
            mode: "no-cors",
            body: paymentJSON
        })

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        let items = await response.json();
        return items;

    } catch (error) {
        console.error(error);
    }
}