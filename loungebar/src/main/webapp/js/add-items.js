const ENDPOINT = "/loungebar/rest/orderItems";

async function addItem() {

        const form = document.getElementById('addItemForm');
        
        const payload = new URLSearchParams({
            drinkName: form.querySelector('[name="productID"]').value,
            orderID:   form.querySelector('[name="orderID"]').value,
            quantity:  form.querySelector('[name="quantity"]').value
        });

        try {
            const response = await fetch(ENDPOINT, {
                method: 'POST',
                headers: {
                    "Content-Type": "application/json",
                    "Accept":       "application/json"
                },
                body: JSON.stringify(payload)
            });

            if (!response.ok) {
                alert('Error adding item.');
                return;
            }

            const item = await response.json();

            const li = document.createElement('li');
            li.textContent = `${item.drinkName} x ${item.quantity} `;
            document.getElementById('itemList').appendChild(li);

        } catch (err) {
            console.error("Fetch error: ", err);
        showError("Network error: could not reach the server.");
        }

    }