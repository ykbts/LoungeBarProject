
const ENDPOINT = "/loungebar/rest/reservation"; // adjust to your servlet mapping

async function createReservation() {
    const btn = document.getElementById("confirmReservation");

    // ── collect & validate inputs ──────────────────────────────────────────
    const startTime    = document.getElementById("startTime").value;
    const endTime      = document.getElementById("endTime").value;
    const customerName = document.getElementById("customerID").value.trim();
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

    // ── build payload ──────────────────────────────────────────────────────
    const dataToSend = {
        startTime: startTime.replace(" ", "T") + ":00",
        endTime:   endTime.replace(" ", "T") + ":00",
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

        const data = await response.json();

        if (response.ok) {
            // HTTP 201 Created — render reservation details
            showSuccess(data);
        } else {
            // server returned an error Message object
            const detail = data["error-details"]
                ? `${data.message} (${data["error-details"]})`
                : data.message;
            showError(detail || "Unexpected error from server.");
        }

    } catch (err) {
        console.error("Fetch error: ", err);
        showError("Network error: could not reach the server.");
    } finally {
        btn.disabled = false;
        btn.textContent = "Confirm Reservation";
    }
}

// ── render helpers ─────────────────────────────────────────────────────────

/**
 * Renders the reservation details returned by the server.
 * The "id" field is intentionally excluded from the display.
 * @param {Object} reservation - the Reservation JSON from the response body
 */
function showSuccess(reservation) {
    const panel  = document.getElementById("result");
    const header = document.getElementById("resultHeader");
    const body   = document.getElementById("resultBody");

    // fields to display and their labels (id is excluded)
    const fields = [
        { key: "id",   label: "Id"      },
        { key: "customer",   label: "Customer"      },
        { key: "table",    label: "Table"         },
        { key: "people", label: "Guests"        },
        { key: "startTime",      label: "Start Time"    },
        { key: "endTime",        label: "End Time"      },
    ];

    header.textContent = "Reservation confirmed";
    body.innerHTML = "";

    fields.forEach(({ key, label }) => {
        const value = reservation[key];
        if (value === undefined || value === null) return;

        const row = document.createElement("div");
        row.className = "result-row";
        row.innerHTML = `
            <span class="result-key">${label}</span>
            <span class="result-value">${formatValue(key, value)}</span>
        `;
        body.appendChild(row);
    });

    panel.className = "success";
    panel.style.display = "block";
}

/**
 * Renders an error message panel.
 * @param {string} message
 */
function showError(message) {
    // const panel  = document.getElementById("result");
    // const header = document.getElementById("resultHeader");
    // const body   = document.getElementById("resultBody");
    //
    // header.textContent = "✗ Error";
    // body.innerHTML = `<p class="error-msg">${escapeHtml(message)}</p>`;
    //
    // panel.className = "error";
    // panel.style.display = "block";

    const div = document.getElementById("result");
    if (div) {
        div.style.display = "block";
        div.className = "error-panel"; // O el estilo que prefieras
        div.textContent = message;
    } else {
        // Si no existe el div, al menos lo vemos en un alert para debuguear
        alert("Error: " + message);
}
}
/**
 * Formats a field value for display (e.g. datetime strings).
 */
function formatValue(key, value) {
    if (key === "startTime" || key === "endTime") {
        const d = new Date(value.replace(" ", "T"));
        if (!isNaN(d)) {
            return d.toLocaleString("it-IT", {
                day: "2-digit", month: "2-digit", year: "numeric",
                hour: "2-digit", minute: "2-digit"
            });
        }
    }
    return escapeHtml(String(value));
}

/**
 * Escapes HTML special characters to prevent XSS.
 */
function escapeHtml(str) {
    return str
        .replace(/&/g, "&amp;")
        .replace(/</g, "&lt;")
        .replace(/>/g, "&gt;")
        .replace(/"/g, "&quot;");
}