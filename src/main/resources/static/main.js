$('#form').on('submit', submitForm)
$('#nextPage').on('click', goToNextPage)
$('#toggleSelected').on('click', toggleSelected)

function goToNextPage() {
    if ($('#form')[0].checkValidity()) {
        $('#info').hide()
        initParliamentarians()
        $('#parliamentarians').show()
    }
}

function initParliamentarians() {
    $.getJSON(`${EMAIL_API_URL}/parliamentary/list`, function (data) {
        $(data).each(createMailField)
        $('.collapse').on('hide.bs.collapse', function () {
            return !isChecked($(this).data('input'))
        }).on('show.bs.collapse', function () {
            return isChecked($(this).data('input'))
        });
    })
}

function isChecked(id) {
    return $(id)[0].checked
}

function createMailField(index, parliamentary) {
    const mailBodyPlaceholder = getFilledPlaceholder(parliamentary.mailBodyPlaceholder)
    $('#list-parliamentarians').append(`
        <div style="display: block">
            <input type="checkbox" class="form-check-input parliamentary-check" 
                id="check${index}" data-toggle="collapse" 
                data-target="#parliamentary${index}" data-text="#message${index}"
                data-name="${parliamentary.name}">
            <label class="form-check-label" style="font-weight: 100 !important;"
             for="${parliamentary.name}">${parliamentary.name}</label>
            
            <div id="parliamentary${index}" class="collapse" data-input="#check${index}">
                <label for="message${index}">Mensagem:</label>
                <textarea class="form-control" rows="5" id="message${index}">${mailBodyPlaceholder}</textarea>
            </div>
        </div>
    `)
}

function getFilledPlaceholder(message) {
    return message
        .replace(/%NOME_USUARIO%/g, $('#name').val())
        .replace(/%EMAIL_USUARIO%/g, $('#email').val())
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
    $('.parliamentary-check').each(function () {
        if (!$(this)[0].checked) return
        const mailTextArea = $($(this).data('text'))

        chosenParliamentarians.push({
            name: $(this).data('name'),
            mail_body: mailTextArea.val()
        })
    })

    return chosenParliamentarians
}

let checked = true
function toggleSelected() {
    $('.parliamentary-check').each(function() {
        if ((checked && !$(this)[0].checked)
        || (!checked && $(this)[0].checked)) {
            $(this).trigger('click')
        }
    });
    checked = !checked
}