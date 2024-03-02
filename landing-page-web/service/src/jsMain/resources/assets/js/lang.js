'use strict';

function loadLanguage(languageCode, languageTitle) {
	$("[data-localize]").localize("assets/lang/lang", { language: languageCode });
	$("#languageSwitcher").html("<img id='languageSwitcherFlag' src='assets/img/flag-" + languageCode + ".svg'/>" + languageTitle);
}
