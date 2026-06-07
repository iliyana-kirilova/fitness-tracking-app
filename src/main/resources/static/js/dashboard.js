document.getElementById('editBioBtn').addEventListener('click', function() {
    const btn = this;
    const fields = document.querySelectorAll('.bio-field');
    const form = document.getElementById('biometricsForm');

    if (btn.textContent === 'Edit') {
        fields.forEach(field => field.removeAttribute('disabled'));
        btn.textContent = 'Save';
        btn.classList.add('mode-save');
        fields[0].focus(); // Автоматичен фокус върху първото поле
    } else {
        // Подсигуряваме премахването на disabled, за да може формата да изпрати данните
        fields.forEach(field => field.removeAttribute('disabled'));
        btn.type = 'submit';
        form.submit();
    }
});