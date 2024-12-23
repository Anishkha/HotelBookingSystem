document.getElementById('addHotelForm').addEventListener('submit', function(event) {
    event.preventDefault(); // Prevent the default form submission

    // Get values from the form
    const hotelName = document.getElementById('hotelName').value;
    const hotelCity = document.getElementById('hotelCity').value;
    const roomsAvailable = document.getElementById('roomsAvailable').value;

    // Prepare the data for the POST request
    const hotelData = {
        hotelName: hotelName,
        city: hotelCity,
        roomsAvailable: parseInt(roomsAvailable)  // Convert roomsAvailable to an integer
    };

    // Send POST request to the backend
    fetch('http://localhost:8080/hoteladmin/add', {
        method: 'POST', // Specify the method
        headers: {
            'Content-Type': 'application/json' // Set content type as JSON
        },
        body: JSON.stringify(hotelData)  // Convert hotelData to JSON format
    })
    .then(response => response.json())  // Parse the JSON response
    .then(data => {
        console.log('Backend response:', data);  // Log the response for debugging
        // Display the success or error message from the backend
        document.getElementById('statusMessage').textContent = data.message;  
    })
    .catch(error => {
        console.error('Error:', error);  // Log any errors to the console
        // Display a generic error message if something goes wrong
        document.getElementById('statusMessage').textContent = 'Failed to add hotel. Please try again.';  
    });
});
