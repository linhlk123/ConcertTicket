const seats = document.querySelectorAll('.seat');
let selectedSeats = [];
let totalPrice = 0;

// Handle seat selection
seats.forEach(seat => {
    seat.addEventListener('click', () => {
        if (seat.classList.contains('sold-out')) return; // Don't allow selection if seat is sold-out

        if (seat.classList.contains('selected')) {
            seat.classList.remove('selected');
            selectedSeats = selectedSeats.filter(s => s !== seat.dataset.seat);
            totalPrice -= parseFloat(seat.dataset.price);
        } else {
            seat.classList.add('selected');
            selectedSeats.push(seat.dataset.seat);
            totalPrice += parseFloat(seat.dataset.price);
        }
        updateSeatInfo();
    });
});

// Update the selected seats display
function updateSeatInfo() {
    document.getElementById('selected-seats').textContent = selectedSeats.join(', ') || 'None';
    document.getElementById('total-price').textContent = totalPrice.toFixed(2);
}

function confirmSeats() {
    const selectedSeats = document.getElementById('selected-seats').textContent;
    const totalPrice = document.getElementById('total-price').textContent;
    
    if (selectedSeats === 'None') {
        alert('Please select at least one seat.');
        return;
    }

    // Get ticket type based on first selected seat
    const firstSeatElement = document.querySelector('.seat.selected');
    let ticketType = 'Regular';
    if (firstSeatElement) {
        const price = parseFloat(firstSeatElement.dataset.price);
        if (price === 60) ticketType = 'VIP';
        else if (price === 50) ticketType = 'Premium';
    }

    // Show confirmation dialog
    if (confirm(`Confirm your selection?\nSeats: ${selectedSeats}\nTicket Type: ${ticketType}\nTotal Price: $${totalPrice}`)) {
        // Create URL with parameters
        const url = `/users/new?seats=${encodeURIComponent(selectedSeats)}&types=${encodeURIComponent(ticketType)}&price=${encodeURIComponent(totalPrice)}`;
        window.location.href = url;
    }
}