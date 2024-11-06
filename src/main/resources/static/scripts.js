// scripts.js

// Función para simular el pago con una tarjeta guardada
function payWithSavedCard(cardNumber) {
    alert("Procediendo al pago con la tarjeta: " + cardNumber);
    // Aquí iría la lógica para procesar el pago
    // Por ejemplo, podrías hacer una llamada a la API de tu backend para procesar el pago
}

// Función para manejar el envío del formulario de agregar una nueva tarjeta
document.addEventListener('DOMContentLoaded', function() {
    const addCardForm = document.getElementById('add-card-form');

    // Verifica si el formulario existe antes de agregar el evento
    if (addCardForm) {
        addCardForm.addEventListener('submit', function(event) {
            event.preventDefault(); // Previene el envío del formulario por defecto

            const cardNumberInput = document.getElementById('card-number');
            const cardNumber = cardNumberInput.value;

            if (cardNumber) {
                // Aquí podrías agregar la lógica para enviar el número de tarjeta al backend
                alert("Tarjeta agregada: " + cardNumber);
                // Reiniciar el campo después de agregar la tarjeta
                cardNumberInput.value = '';
            } else {
                alert("Por favor, ingresa un número de tarjeta válido.");
            }
        });
    }

    // Simulando que no hay tarjetas guardadas
    const hasSavedCards = false;

    if (!hasSavedCards) {
        document.getElementById('saved-cards').style.display = 'none';
        document.getElementById('no-saved-cards').style.display = 'block';
    }
});