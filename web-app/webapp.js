let barcode;
let barcodeJSON;
let itemsJSON;
const URL = "https://project-acme.herokuapp.com/barcode";

function submitBarcode() {
    barcode = document.getElementById('barcode').value;
    barcodeJSON =
    {
        "barcode": barcode
    };

    itemsJSON = getItems();

    let jsonString = JSON.stringify(itemsJSON);
    document.getElementById("textarea").innerHTML = jsonString;

    buildTable(itemsJSON);
}

async function getItems() {
    try {
        let response = await fetch(URL, {
            method: 'POST',
            body: barcodeJSON
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

function buildTable(data) {
    let table = document.getElementById('itemTable');

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
    let option = document.createElement('option');

    for (let i = 0; i < data.length; i++) {
        option.text = data[i].name;
        itemDropdown.add(option);
    }
}