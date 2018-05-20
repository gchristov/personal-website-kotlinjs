'use strict';

function loadLanguage(languageCode, languageTitle) {
	$("[data-localize]").localize("assets/lang/lang", { language: languageCode });
	$("#languageSwitcher").html("<img id='languageSwitcherFlag' src='http://www.countryflags.io/" + languageCode + "/shiny/24.png'/>" + languageTitle);
}