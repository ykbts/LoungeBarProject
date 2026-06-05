
const ENDPOINT = "/loungebar/rest/items";

async function addItem() {

    // retrieve data from form
    const form = document.getElementById('addItemForm');

    const name = form.querySelector('[name="productID"]').value;
    const id = form.querySelector('[name="orderID"]').value;
    const quantity = form.querySelector('[name="quantity"]').value;

    if (!name || !quantity) {
        alert("Fulfill all fields!");
        return;
    }

    const payload = ({
        orderItem: {
            drinkName: name,
            orderID:   parseInt(id),
            quantity:  parseInt(quantity)
        }
    });

    // send request
    try {
        console.log(JSON.stringify(payload));

        const response = await fetch(ENDPOINT, {
            method: 'POST',
            headers: {
                "Content-Type": "application/json",
                "Accept":       "application/json"
            },
            body: JSON.stringify(payload)
        });

        if (!response.ok) {
            const text = await response.text();
            console.log('Body raw: ', text)
            alert('Error adding item.');
            return;
        }

        // parse response
        const data = await response.json();

        const item = data.orderItem;
        console.log(item);

        const itemList = document.getElementById('itemList');

        // find <li> with same drinkName
        const items = itemList.querySelectorAll('li');
        let existingItem = null;

        items.forEach(li => {
            if (li.querySelector('.item-name').textContent === item.drinkName) {
                existingItem = li;
            }
        });

        // if exists, update; otherwise, insert
        if (existingItem) {

            existingItem.querySelector('.item-qty').textContent = `x ${item.quantity}`;
        }
        else {
            const li = document.createElement('li');

            const name = document.createElement('span');
            name.classList.add('item-name');
            name.textContent = item.drinkName;

            const qty = document.createElement('span');
            name.classList.add('item-qty');
            qty.textContent = `x ${item.quantity}`;

            li.appendChild(name);
            li.appendChild(qty);
            itemList.appendChild(li);
        }

    } catch (err) {
        console.error("Fetch error: ", err);
    }
}



function step(fieldId, delta) {
    const input = document.getElementById(fieldId);
    const min = parseInt(input.min);
    const max = parseInt(input.max);
    const current = parseInt(input.value) || min;
    input.value = Math.min(max, Math.max(min, current + delta));

    updateStepperButtons(input);
}

// for the input: if I insert a value out of permitted range, fix the input to closest valid input
function clamp(input) {
    const val = parseInt(input.value);
    const min = parseFloat(input.min);
    const max = parseFloat(input.max);
    if (!isNaN(val)) {
        input.value = Math.min(max, Math.max(min, val));
    }
    updateStepperButtons(input);
}

// update the STATE of the buttons (enabled/disabled)
function updateStepperButtons(input) {
    const val = parseInt(input.value);
    const container = input.closest('.stepper-input');
    const [btnMinus, btnPlus] = container.querySelectorAll('.stepper-btn');
    btnMinus.disabled = val <= parseInt(input.min);
    btnPlus.disabled  = val >= parseInt(input.max);
}

// set buttons state on window loaded
document.addEventListener('DOMContentLoaded', () => {
    document.querySelectorAll('.stepper-input input').forEach(input => {
        updateStepperButtons(input);
    });
});