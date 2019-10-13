initParliamentarians()
$('#form').on('submit', submitForm)

function initParliamentarians() {
    $.getJSON(`${EMAIL_API_URL}/parliamentary/list`, function (data) {
        $(data).each(createMailField)
    })
}

function createMailField(index, parliamentary) {
    $('#parliamentarians').append(`
        <input type="checkbox" class="form-check-input" 
            id="${parliamentary.name}" data-toggle="collapse" 
            data-target="#parliamentary${index}" data-text="#message${index}"
            data-name="${parliamentary.name}">
        <label class="form-check-label" for="${parliamentary.name}">${parliamentary.name}</label>
        
        <div id="parliamentary${index}" class="collapse">
            <label for="message${index}">Mensagem:</label>
            <textarea class="form-control" rows="5" id="message${index}">${parliamentary.mailBodyPlaceholder}</textarea>
        </div>
    `)
}

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
}

function getFormData() {
    return {
        user: {
            name: $('#name').val(),
            email: $('#email').val(),
        },
        parliamentarian_mails: getChosenParliamentarians()
    }
}

function getChosenParliamentarians() {
    const chosenParliamentarians = []
    $('input:checkbox:checked').each(function () {
        const mailTextArea = $($(this).data('text'))

        chosenParliamentarians.push({
            name: $(this).data('name'),
            mail_body: mailTextArea.val()
        })
    })

    return chosenParliamentarians
}