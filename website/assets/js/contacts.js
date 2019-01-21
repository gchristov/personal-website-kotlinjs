"use strict";

// Email injection

function injectEmail() {
  var email = "gmail.com";
  email = "ga.christov" + "@" + email;
  var mailTo = "mailto:" + email;
  document.getElementById("contact_header").href = mailTo;
  document.getElementById("contact_header").text = email;
  document.getElementById("contact_footer").href = mailTo;
  document.getElementById("contact_footer").text = email;
  injectSkype();
  injectEmailJS();
}

function injectSkype() {
  var skype = "stevefransis";
  var skypeTo = "skype:" + skype;
  document.getElementById("contact_skype").href = skypeTo;
  document.getElementById("contact_skype").text = skype;
}

function injectEmailJS() {
  var part1 = "user_6y8bLaIug";
  var part2 = "Pk7189CKrLwh";
  emailjs.init(part1 + part2);
}

// Form validation

function validateForm(selector) {
  Array.from(document.querySelectorAll(selector)).forEach(item => {
    item.addEventListener("input", e => {
      if (e.target.value === "") {
        item.dataset.touched = false;
      }
    });
    item.addEventListener("invalid", () => {
      item.dataset.touched = true;
    });
    item.addEventListener("blur", () => {
      if (item.value !== "") {
        item.dataset.touched = true;
      }
    });
  });
}

validateForm(".js-form .form-field");

var form = document.querySelector(".js-form");
var formName = ".js-form";

form.addEventListener("submit", function(e) {
  submitForm(e, formName);
});

function submitForm(e, formName) {
  e.preventDefault();
  var botTest = $(formName + " .js-field-nickname").val();
  var name = $(formName + " .js-field-name").val();
  var email = $(formName + " .js-field-email").val();
  var message = $(formName + " .js-field-message").val();

  if (botTest !== "") {
    return;
  }
  var formData = {
    from_name: name,
    from_email: email,
    from_message: message
  };

  disableSendButton();
  emailjs.send("gmail", "simple_template", formData).then(
    function(response) {
      console.log("success");
      enableSendButton();
      clearForm();
      alert("Thanks. Your message has been sent successfully!");
    },
    function(error) {
      console.log("error", error);
      enableSendButton();
      alert("Could not send message at this time. Please try again.");
    }
  );
}

function disableSendButton() {
  document.getElementById("contact-send-btn").innerHTML =
    '<i class="fa fa-spinner fa-spin"></i>Sending';
  document.getElementById("contact-send-btn").disabled = true;
}

function enableSendButton() {
  document.getElementById("contact-send-btn").innerHTML = "Send";
  document.getElementById("contact-send-btn").disabled = false;
}

function clearForm() {
  $(formName + " .js-field-name").val("");
  $(formName + " .js-field-email").val("");
  $(formName + " .js-field-message").val("");
}
