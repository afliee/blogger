$(document).ready(function () {
    let Container = $('.container');
    Container.imagesLoaded(function() {
        let portfolio = $('.special-menu');
        portfolio.on('click', 'button', function() {
            $(this).addClass('active').siblings().removeClass('active');
            let filterValue = $(this).attr('data-filter');
            $grid.isotope({
                filter: filterValue
            });
        });
        let $grid = $('.special-list').isotope({
            itemSelector: '.special-grid'
        });
    });
})