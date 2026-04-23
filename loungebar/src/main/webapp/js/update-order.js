

async function changeStatus(orderId) {

    const methodSelect = document.getElementById('pay-method-' + orderId);
    const status = 'PAID'; // Will always be paid
    const selectedMethod = methodSelect.value; 
    
    const btn = document.getElementById('pay-btn-' + orderId);
    

    if (!btn) return;

    
    btn.disabled = true;
    const originalText = btn.textContent;
    btn.textContent = "Updating...";

    const ENDPOINT = "/loungebar/rest/order/update-status";
    const url = `${ENDPOINT}?orderID=${orderId}&newStatus=PAID&payMethod=${selectedMethod}`;

    try {
        const response = await fetch(url, {
            method: 'POST',
            headers: { 'Accept': 'application/json', 'Content-Type': 'application/json' },
            body: JSON.stringify({})
        });

        if (response.ok) {
            document.getElementById('status-cell-' + orderId).textContent = 'PAID';
            document.getElementById('action-cell-' + orderId).innerHTML = ' Paid with ' + selectedMethod;
        } else {
            const data = await response.json();
            alert("Error: " + data.message);
            btn.disabled = false;
            btn.textContent = "Confirm Payment";
        }
    } catch (err) {
        alert("Network error.");
        btn.disabled = false;
    }
}