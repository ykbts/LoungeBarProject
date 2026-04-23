const ENDPOINT = "/loungebar/rest/review";

async function createReview() {

    const btn = document.getElementById("confirmReview");

    // ── collect & validate inputs ──────────────────────────────────────────
    const waiter = document.getElementById("waiterID").value;
    const client = document.getElementById("clientID").value;
    const score = document.getElementById("score").value ;
    const comment = document.getElementById("comment").value;

    if (!waiter || !client || !score)
        showError("Fulfill mandatory fields");

    console.log(waiter);
    console.log(client);
    console.log(score);
    console.log(comment);

    // ── build payload ──────────────────────────────────────────────────────
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

        const data = await response.json();

        if (response.ok) {
            // HTTP 201 Created — render reservation details
            showSuccess(data.review);
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
        btn.textContent = "Send";
    }


}

function showSuccess(review) {

    const panel  = document.getElementById("result");
    const header = document.getElementById("resultHeader");
    const body   = document.getElementById("resultBody");

    header.textContent = "Review stored";
    body.innerHTML = "";

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


    // append to parent div
    body.append(waiter, client, score, comment);

    panel.className = "success";
    panel.style.display = "block";
}