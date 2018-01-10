'use strict';


//Validation forms
function validateForm(selector) {
    Array.from(document.querySelectorAll(selector)).forEach(item => {
        item.addEventListener('input', (e) => {
            if(e.target.value === ''){
            item.dataset.touched = false;
            }
        });
        item.addEventListener('invalid', () => {
            item.dataset.touched = true;
        });
        item.addEventListener('blur', () => {
            if (item.value !== '') item.dataset.touched = true;
        });
    });
};

validateForm('.js-form .form-field');

var form = document.querySelector('.js-form');
var formName = '.js-form';

form.addEventListener('submit', function(e){
    submitForm(e, formName);
});

function submitForm(e, formName) {
    e.preventDefault();
    var name = $(formName + ' .js-field-name').val();
    var email = $(formName + ' .js-field-email').val();
    var message = $(formName + ' .js-field-message').val();

    var formData = {
        from_name: name,
        from_email: email,
        from_message: message
    };

    disableSendButton();
    emailjs.send("gmail", "simple_template", formData).then(
      function(response) {
        console.log('success');
        enableSendButton();
        clearForm();
        alert("Thanks. Your message has been sent successfully!");
      },
      function(error) {
        console.log('error', error);
        enableSendButton();
        alert("Could not send message at this time. Please try again.");
      }
    );

    // $.ajax({
    //     type: "POST",
    //     url: 'mail.php',
    //     data: formData,
    //     success: function () {
    //         console.log('success');
    //         $(formName + ' .js-field-name').val("");
    //         $(formName + ' .js-field-email').val("");
    //         $(formName + ' .js-field-message').val("");
    //         alert("Thanks. Your message has been sent successfully!");
    //     },
    //     error: function () {
    //         console.log('error');
    //         alert("Could not send message at this time. Please try again.");
    //     }
    // });
}

function disableSendButton() {
  document.getElementById("contact-send-btn").innerHTML = '<i class="fa fa-spinner fa-spin"></i>Sending';
  document.getElementById("contact-send-btn").disabled = true;
}

function enableSendButton() {
  document.getElementById("contact-send-btn").innerHTML = 'Send';
  document.getElementById("contact-send-btn").disabled = false;
}

function clearForm() {
  $(formName + ' .js-field-name').val("");
  $(formName + ' .js-field-email').val("");
  $(formName + ' .js-field-message').val("");
}
