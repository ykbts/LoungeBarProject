
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
            li.textContent = `${item.drinkName} x ${item.quantity} `;
            document.getElementById('itemList').appendChild(li);

        } catch (err) {
            console.error("Fetch error: ", err);
        }

    }