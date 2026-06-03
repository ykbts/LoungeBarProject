
const ENDPOINT = "/loungebar/rest/reservation";

async function createReservation() {
    const btn = document.getElementById("confirmReservation");

    // collect and validate inputs 
    const startTime    = document.getElementById("startTime").value;
    const endTime      = document.getElementById("endTime").value;
    const customerName = document.getElementById("clientID").value.trim();
    const tableNumber  = document.getElementById("tableNumber").value;
    const numberOfPeople = document.getElementById("numberOfPeople").value;

    if (!startTime || !endTime || !customerName || !tableNumber || !numberOfPeople) {
        showError("All fields are required.");
        return;
    }

    if (new Date(endTime) <= new Date(startTime)) {
        showError("End time must be after start time.");
        return;
    }

    console.log(startTime);
    console.log(endTime);
    console.log(customerName);
    console.log(tableNumber);
    console.log(numberOfPeople);

    // build payload 
    const dataToSend = {
        startTime: startTime,
        endTime:   endTime,
        customer:  customerName,
        table:     parseInt(tableNumber),
        people:    parseInt(numberOfPeople)
    };

    const payload = {
    reservation: dataToSend
    };

    btn.disabled = true;
    btn.textContent = "Sending…";


    try {

        console.log("Performing the HTTP GET request.");

        const response = await fetch(ENDPOINT, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Accept":       "application/json"
            },
            body: JSON.stringify(payload)
        });

        console.log("Performing the HTTP GET request.");

        const text = await response.text();
        console.log("STATUS:", response.status);
        console.log("RAW RESPONSE:", text);

        let data;
        try {
            data = JSON.parse(text);
        } catch (e) {
            console.error("JSON parse failed:", e);
            showError(`Server error (${response.status})`);
            return;
        }

    if (response.ok) {
        showSuccess(data.reservation);
    }  else {
          const msg = data.message;
          showError(msg.message || "Unexpected error from server.");
      }

    } catch (err) {
        console.error("Fetch error: ", err);
        showError("Network error: could not reach the server.");
    } finally {
        btn.disabled = false;
        btn.textContent = "Confirm Reservation";
    }
}

// render helpers

function showSuccess(reservation) {

    const panel  = document.getElementById("result");
    const header = document.getElementById("resultHeader");
    const body   = document.getElementById("resultBody");


    header.textContent = "Reservation confirmed!";
    body.innerHTML = "";
    panel.classList.add("visible");

    // create the fields containers
    const id = document.createElement("div");
    const start = document.createElement("div");
    const end = document.createElement("div");
    const people = document.createElement("div");
    const customer = document.createElement("div");
    const table = document.createElement("div");

    // assign the values
    id.textContent = reservation.id;
    start.textContent = formatDateTime(reservation.startTime);
    end.textContent = reservation.endTime;
    people.textContent = reservation.people;
    customer.textContent = reservation.customer;
    table.textContent = reservation.table;

    // create labels
    const l_id = document.createElement("label");
    l_id.textContent = "ID: ";
    const l_start = document.createElement("label");
    l_start.textContent = "Start Time: ";
    const l_end = document.createElement("label");
    l_end.textContent = "End Time: ";
    const l_people = document.createElement("label");
    l_people.textContent = "Number of people: ";
    const l_table = document.createElement("label");
    l_table.textContent = "Table Number: ";
    const l_customer = document.createElement("label");
    l_customer.textContent = "Table: ";

    // append to parent div
    body.append(l_id, id, l_customer, customer, l_table, table, l_start, start, l_end, end, l_people, people);

}


function showError(message) {

    const panel  = document.getElementById("result");
    const header = document.getElementById("resultHeader");
    const body   = document.getElementById("resultBody");

    header.textContent = "Error!";
    body.innerHTML = `<p class="error-msg">${escapeHtml(message)}</p>`;


    // const div = document.getElementById("result");
    // if (div) {
    //     div.style.display = "block";
    //     div.className = "error-panel"; // O el estilo que prefieras
    //     div.textContent = message;
    // } else {
    //     // Si no existe el div, al menos lo vemos en un alert para debuguear
    //     alert("Error: " + message);
    // }
}
/**
 * Formats a field value for display (e.g. datetime strings).
 */
// function formatValue(key, value) {
//     if (key === "startTime" || key === "endTime") {
//         const d = new Date(value.replace(" ", "T"));
//         if (!isNaN(d)) {
//             return d.toLocaleString("it-IT", {
//                 day: "2-digit", month: "2-digit", year: "numeric",
//                 hour: "2-digit", minute: "2-digit"
//             });
//         }
//     }
//     return escapeHtml(String(value));
// }

/**
 * Escapes HTML special characters to prevent XSS.
 */
function escapeHtml(str) {
    if (str == null) return "";
    str = String(str);
    return str
        .replace(/&/g, "&amp;")
        .replace(/</g, "&lt;")
        .replace(/>/g, "&gt;")
        .replace(/"/g, "&quot;");
}

function formatDateTime(str) {
    if (!str) return "-";

    const date = new Date(str.replace(" ", "T"));
    return date.toLocaleString("it-IT");
}