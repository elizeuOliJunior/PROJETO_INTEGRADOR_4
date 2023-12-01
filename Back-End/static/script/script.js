function submitForm() {
    const completeForm = document.getElementById('complete-form');

    completeForm.addEventListener('submit', function (event) {
        event.preventDefault();

        const personalInfoForm = document.getElementById('personal-info-form');
        const addressForm = document.getElementById('address-form');
        const appointmentForm = document.getElementById('appointment-form');

        const personalInfoData = new FormData(personalInfoForm);
        const addressData = new FormData(addressForm);
        const appointmentData = new FormData(appointmentForm);

        const data = {
            personalInfo: Object.fromEntries(personalInfoData),
            address: Object.fromEntries(addressData),
            appointment: Object.fromEntries(appointmentData)
        };

        // Enviar dados para o servidor Java
        fetch('http://localhost:8000/submit', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data),
        })
        .then(response => response.json())
        .then(data => {
            console.log('Sucesso:', data);
        })
        .catch((error) => {
            console.error('Erro:', error);
        });
    });
}
