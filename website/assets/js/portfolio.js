function injectPortfolio() {
  let projects = [
    {
      "tag":"mobile apps",
      "key":"everlog",
      "stack":["iOS", "Android", "MVP", "Firebase"],
      "url":"everlogapp.com"
    },
    {
      "tag":"platforms",
      "key":"graffitab",
      "stack":["iOS", "Android", "MVP", "Spring Boot", "Amazon Web Services"],
      "url":"graffitab.com"
    },
    {
      "tag":"slack apps",
      "key":"codinglove",
      "stack":["Slack", "Vertx", "DigitalOcean"],
      "url":"thecodinglove.gchristov.com"
    },
    {
      "tag":"platforms",
      "key":"fieldmargin",
      "stack":["iOS", "Android", "MVP", "ReactJS", "Spring Boot", "Google Cloud Platform"],
      "url":"fieldmargin.com"
    },
    {
      "tag":"platforms",
      "key":"qumu",
      "stack":["iOS", "Android", "MVP", "Spring Boot", "Google Cloud Platform"],
      "url":"qumu.com"
    },
    {
      "tag":"mobile apps",
      "key":"massalert",
      "stack":["iOS", "Android", "MVC", "MVP", "Google Cloud Platform"],
      "url":"futurist-labs.com"
    },
    {
      "tag":"mobile apps",
      "key":"tabex",
      "stack":["iOS", "Android", "MVP", "Google Cloud Platform"],
      "url":"tabex.bg"
    },
    {
      "tag":"platforms",
      "key":"mentalist",
      "stack":["Android", "MVP", "Google Cloud Platform"],
      "url":"mentalist.bg"
    },
    {
      "tag":"mobile apps",
      "key":"getti",
      "stack":["iOS", "Android", "MVP"],
      "url":"getti.org"
    },
    {
      "tag":"platforms",
      "key":"larctest",
      "stack":["iOS", "Android", "MVP", "Google Cloud Platform"],
      "url":"larctest.com"
    },
    {
      "tag":"mobile apps",
      "key":"tbob",
      "stack":["iOS", "Android", "MVP"],
      "url":"thebestofbulgaria.eu/en/home"
    },
  ];
  let cardHtmlTemplate = `
    <div class="row project-card"  data-toggle="modal" data-target="#modal-%KEY" data-portfolio-tag="%TAG">
      <div class="col-md-6 col-lg-5 project-card__img">
        <img class="" src="assets/img/project_%KEY.png" alt="project-img">
      </div>
      <div class="col-md-6 col-lg-7 project-card__info">
        <h3 class="project-card__title" data-localize="portfolio_%KEY_title">&nbsp;</h3>
        <p class="project-card__description" data-localize="portfolio_%KEY_description">
          &nbsp;
        </p>
        <p class="project-card__stack" data-localize="portfolio_used_stack">&nbsp;</p>
        <ul class="tags">
          %STACK
        </ul>
        <a href="https://%URL" target="_blank" class="project-card__link">www.%URL</a>
      </div>
    </div>
  `;
  let modalHtmlTemplate = `
    <div class="modal fade portfolio-modal" id="modal-%KEY" tabindex="-1" role="dialog" aria-hidden="true">
      <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div class="modal-body col-md-11 col-lg-9 ml-auto mr-auto">
            <p class="portfolio-modal__title" data-localize="portfolio_%KEY_title">&nbsp;</p>
            <img class="portfolio-modal__img" src="assets/img/project_%KEY.png" alt="modal_img">
            <p class="portfolio-modal__description" data-localize="portfolio_%KEY_description">
              <!-- Placeholder -->
              <br/>
              <br/>
              <br/>
            </p>
            <div class="portfolio-modal__link">
              <a href="https://%URL" target="_blank">www.%URL</a>
            </div>
            <div  class="portfolio-modal__stack">
              <p class="portfolio-modal__stack-title" data-localize="portfolio_used_stack">&nbsp;</p>
              <ul class="tags">
                %STACK
              </ul>
            </div>
          </div>
        </div>
      </div>
    </div>
  `;
  var contentHtml = ""
  for (index in projects) {
    let project = projects[index];
    // Stack
    var stackHtml = "";
    for (stackIndex in project.stack) {
      stackHtml += "<li>" + project.stack[stackIndex] + "</li>"
    }
    // Add card
    let cardHtml = cardHtmlTemplate
      .replaceAll("%TAG", project.tag)
      .replaceAll("%KEY", project.key)
      .replaceAll("%URL", project.url)
      .replaceAll("%STACK", stackHtml);
    contentHtml += cardHtml;
    // Add modal
    let modalHtml = modalHtmlTemplate.replaceAll("%KEY", project.key).replaceAll("%URL", project.url).replaceAll("%STACK", stackHtml);
    contentHtml += modalHtml;
  }
  document.getElementById('portfolioList').innerHTML = contentHtml;
}