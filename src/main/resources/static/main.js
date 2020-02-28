$('#form').on('submit', submitForm)
$('#ombudsman-email').collapse('toggle')
$('#parliamentary-email').collapse('toggle')

function submitForm(event) {
    event.preventDefault()
    const data = JSON.stringify(getFormData())

    $.ajax({
        url: `${EMAIL_API_URL}/email/`,
        type: 'POST',
        data: data,
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
    })
    setTimeout(function () {
        alert('E-mails enviados!')
    }, 300)
}

function getFormData() {
    return {
        user: {
            name: $('#name').val(),
            email: $('#email').val(),
        }
    }
}
