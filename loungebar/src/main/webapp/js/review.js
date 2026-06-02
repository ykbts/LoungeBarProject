const ENDPOINT = contextPath + "/rest/review";

async function createReview() {

    const btn = document.getElementById("confirmReview");

    // collect and validate inputs 
    const waiter = document.getElementById("waiterID").value;
    const client = document.getElementById("clientID").value;
    const score = document.getElementById("score").value ;
    const comment = document.getElementById("comment").value;

    if (!waiter || !client || !score)
    {
        showError("Fulfill mandatory fields");
        return;
        }


    // prepare payload to send
    const payload = {
        review: {
            waiter: waiter,
            customer: client,
            score: parseInt(score),
            comment: comment
        }
    }

    btn.disabled = true;
    btn.textContent = "Sending…";

    try {

        console.log("Performing the HTTP GET request.");

        const response = await fetch(ENDPOINT, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Accept": "application/json"
            },
            body: JSON.stringify(payload)
        });

        console.log("Performing the HTTP GET request.");


        const text = await response.text();

        let data;
        try {
            data = JSON.parse(text);
        } catch (e) {
            console.error("JSON parse failed:", e);
            showError(`Server error (${response.status})`);
            return;
        }

        if (response.ok) {
            showSuccess(data.review);
        } else {
              const msg = data.message;
              showError(msg.message || "Unexpected error from server.");
        }

    } catch (err) {
        console.error("Fetch error: ", err);
        showError("Network error: could not reach the server.");
    } finally {
        btn.disabled = false;
        btn.textContent = "Send";
    }


}

function showSuccess(review) {
    const panel = document.getElementById("result");
    const header = document.getElementById("resultHeader");
    const body   = document.getElementById("resultBody");

    header.textContent = "Review stored!";
    body.innerHTML = "";
    panel.classList.add("visible");

    // create the fields containers
    const waiter = document.createElement("div");
    const client = document.createElement("div");
    const score = document.createElement("div");
    const comment = document.createElement("div");

    // assign the values
    waiter.textContent = review.waiter;
    client.textContent = review.customer
    score.textContent = review.score;
    comment.textContent = review.comment;

    // create labels
    const l_waiter = document.createElement("label");
    l_waiter.textContent = "Waiter";
    const l_client = document.createElement("label");
    l_client.textContent = "Client";
    const l_score = document.createElement("label");
    l_score.textContent = "Score";
    const l_comment = document.createElement("label");
    l_comment.textContent = "Comment";


    // append to parent div
    body.append(l_waiter, waiter, l_client, client, l_score, score, l_comment, comment);

}

function showError(message) {
    const header = document.getElementById("resultHeader");
    const body   = document.getElementById("resultBody");

    header.textContent = "Error";
    body.innerHTML = "";

    const msg = document.createElement("div");
    msg.textContent = message;
    body.appendChild(msg);

}

async function loadWaiters() {
    try {
        const response = await fetch(contextPath + "/rest/review/waiters", {
            method: "GET",
            headers: { "Accept": "application/json" }
        });

        if (!response.ok) {
            console.error("Failed to load waiters:", response.status);
            return;
        }

        const waiters = await response.json();
        const select = document.getElementById("waiterID");

        waiters.forEach(username => {
            const option = document.createElement("option");
            option.value = username;
            option.textContent = username;
            select.appendChild(option);
        });

    } catch (err) {
        console.error("Error loading waiters:", err);
    }
}



const labels = document.querySelectorAll('#stars label');

function updateStars(selected) {

    const score = document.getElementById('score');

    labels.forEach((label, i) => {
        label.classList.toggle('active', i < selected);
    });
    score.value = selected > 0 ? selected : '';
}

labels.forEach((label, i) => {

    const inputs = document.querySelectorAll('#stars input[type="radio"]');

    label.addEventListener('mouseover', () => updateStars(i + 1));

    label.addEventListener('mouseleave', () => {
        const checked = [...inputs].findIndex(inp => inp.checked);
        updateStars(checked >= 0 ? checked + 1 : 0);
    });

    label.addEventListener('click', () => {
        inputs[i].checked = true;
        updateStars(i + 1);
    });
});
 

document.addEventListener("DOMContentLoaded", loadWaiters);