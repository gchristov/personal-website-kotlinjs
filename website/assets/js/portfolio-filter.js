'use strict';

// Filter project cards 
var previousClickedMenuLink = undefined;
$('.portfolio-menu').on('click', 'a', function(event) {
    // Link style
    event.preventDefault();
    if (previousClickedMenuLink) {
        previousClickedMenuLink.removeClass('portfolio-menu__link--active');
    }
    var link = $(event.target);
    link.addClass('portfolio-menu__link--active');
    previousClickedMenuLink = link;
    // Filter
    var targetTag = $(event.target).data('portfolio-target-tag');
    var portfolioItems = $('.project-card');
    portfolioItems.each(function(index, value) {
        var item = $(value);
        item.hide();
    });
    portfolioItems.each(function(index, value) {
        var item = $(value);
        if (item.data('portfolio-tag') === targetTag || targetTag === 'all') {
            item.fadeIn({duration: 500});
        } 
    });
});