// Define the base URL of your backend API
const baseUrl = 'http://localhost:8080';

// Function to handle reservation form submission
document.getElementById('reservationForm').addEventListener('submit', function(event) {
    event.preventDefault(); // Prevent form from refreshing the page

    // Get form data
    const name = document.getElementById('name').value;
    const age = document.getElementById('age').value;
    const aadharNumber = document.getElementById('aadharNumber').value;
    const city = document.getElementById('city').value;
    const hotelName = document.getElementById('hotelName').value;

    // Prepare the data for the POST request
    const reservationData = {
        name,
        age,
        aadharNumber,
        city,
        hotelName
    };

    // Make a POST request to reserve the room
    fetch(`${baseUrl}/reservation/reserve`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(reservationData)
    })
    .then(response => response.json()) // Parse the response as JSON
    .then(data => {
        // Check if the response contains a message
        if (data && data.message) {
            document.getElementById('reservationStatus').textContent = data.message; // Display the message
        } else {
            document.getElementById('reservationStatus').textContent = 'Unexpected response structure.';
        }
    })
    .catch(error => {
        console.error('Error:', error);
        document.getElementById('reservationStatus').textContent = 'Failed to reserve the room. Please try again.';
    });
});

// Function to handle confirmation form submission
document.getElementById('confirmationForm').addEventListener('submit', function(event) {
    event.preventDefault();

    // Get the reservation ID
    const reservationId = document.getElementById('reservationId').value;

    // Make a PUT request to confirm the reservation
    fetch(`${baseUrl}/reservation/confirm/${reservationId}`, {
        method: 'PUT'
    })
    .then(response => response.json()) // Parse the response as JSON
    .then(data => {
        // Check if the response contains a message
        if (data && data.message) {
            document.getElementById('confirmationStatus').textContent = data.message; // Display the message
        } else {
            document.getElementById('confirmationStatus').textContent = "Unexpected response structure.";
        }
    })
    .catch(error => {
        console.error('Error:', error);
        document.getElementById('confirmationStatus').textContent = 'Failed to confirm reservation. Please check the Reservation ID.';
    });
});

// Function to handle cancellation form submission
document.getElementById('cancelForm').addEventListener('submit', function(event) {
    event.preventDefault();

    // Get the reservation ID
    const reservationId = document.getElementById('cancelReservationId').value;

    // Make a DELETE request to cancel the reservation
    fetch(`${baseUrl}/reservation/cancel/${reservationId}`, {
        method: 'DELETE'
    })
    .then(response => response.json()) // Parse the response as JSON
    .then(data => {
        // Check if the response contains a message
        if (data && data.message) {
            document.getElementById('cancelStatus').textContent = data.message; // Display the message
        } else {
            document.getElementById('cancelStatus').textContent = "Unexpected response structure.";
        }
    })
    .catch(error => {
        console.error('Error:', error);
        document.getElementById('cancelStatus').textContent = 'Failed to cancel reservation. Please check the Reservation ID.';
    });
});
