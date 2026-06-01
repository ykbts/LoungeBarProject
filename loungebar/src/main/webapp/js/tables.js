// automatic call at loaded web page
document.addEventListener("DOMContentLoaded", listTables);

const ENDPOINT = "/loungebar/rest/tables";

function listTables() {
    try {
        fetch(ENDPOINT, {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
                "Accept": "application/json"
            }
        })
        .then(response => {
            if (!response.ok) {
                const container = document.getElementById("message");

                const message = response.message;
                container.innerHTML = `
                    <span><strong>Message:</strong> ${message.message}</span>
                    <span><strong>Error:</strong> ${message.errorCode}</span>
                    <span><strong>Detail:</strong> ${message.errorDetails}</span>
                `;
                throw new Error("HTTP error, status: " + response.status);
            }
            return response.json();
        })
        .then(data => {
            console.log(data);
            renderTables(data.resourceList);
        })
        .catch(error => {
            console.error("Response error: ", error);
        });
    }
    catch (error) {
        console.error("Fetch error: ", error);
    }
}

function renderTables(tables) {
    const container = document.getElementById("tablesList");

    container.innerHTML = "";

    if (!tables || tables.length === 0) {
        container.innerHTML = "<p>NO tables found.</p>";
        return;
    }

    tables.forEach(resource => {

        const item = document.createElement("div");
        item.classList.add("table-item");
        const table = resource.table;

        item.innerHTML = `
            <span><strong>Table:</strong> ${table.number}     </span>
            <span><strong>Capacity:</strong> ${table.capacity}    </span>
            <span><strong>Available:</strong> ${table["is-available"] ? "Available" : "Not Available"}     </span>
        `;

        container.appendChild(item);
    });
}