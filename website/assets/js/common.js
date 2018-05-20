'use strict';

if (window.attachEvent) {window.attachEvent('onload', performWindowLoadOperations);}
else if (window.addEventListener) {window.addEventListener('load', performWindowLoadOperations, false);}
else {document.addEventListener('load', performWindowLoadOperations, false);}

function performWindowLoadOperations() {
    // loadLanguage("gb", "English");
    injectEmail();
}