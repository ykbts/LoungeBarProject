
const ENDPOINT = "/loungebar/rest/items";

async function addItem() {

    const form = document.getElementById('addItemForm');

    const payload = ({
        orderItem: {
            drinkName: form.querySelector('[name="productID"]').value,
            orderID:   parseInt(form.querySelector('[name="orderID"]').value),
            quantity:  parseInt(form.querySelector('[name="quantity"]').value)
        }
    });

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

        const data = await response.json();

        const item = data.orderItem;
        console.log(item);

        const li = document.createElement('li');
        // li.textContent = `${item.drinkName} x ${item.quantity} `;
        const name = document.createElement('span');
        name.textContent = item.drinkName;

        const qty = document.createElement('span');
        qty.textContent = `x ${item.quantity}`;

        li.appendChild(name);
        li.appendChild(qty);
        document.getElementById('itemList').appendChild(li);

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

function clamp(input) {
    const val = parseInt(input.value);
    const min = parseFloat(input.min);
    const max = parseFloat(input.max);
    if (!isNaN(val)) {
        input.value = Math.min(max, Math.max(min, val));
    }
    updateStepperButtons(input);
}

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