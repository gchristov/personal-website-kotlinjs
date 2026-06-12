'use strict';

function loadLanguage(languageCode, languageTitle) {
	$("[data-localize]").localize("assets/lang/lang", { language: languageCode }).localizePromise.done(function() {
		var copyrightEl = $("[data-localize='contact_copyright']");
		if (copyrightEl.length) {
			var currentYear = new Date().getFullYear();
			var text = copyrightEl.text();
			copyrightEl.text(text.replace("%year%", currentYear));
		}
	});
	$("#languageSwitcher").html("<img id='languageSwitcherFlag' src='assets/img/flag-" + languageCode + ".svg'/>" + languageTitle);
}
