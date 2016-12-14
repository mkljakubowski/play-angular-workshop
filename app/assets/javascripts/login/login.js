/**
 * The cycle background images function
 */
function cycleImages() {
    var $background = $('#background');
    var $active = $background.find('.active');
    var $next = ($background.find('.active').next().length > 0) ? $background.find('.active').next() : $background.find('img:first');
    $next.css('z-index', 2);//move the next image up the pile
    //fade out the top image
    $active.fadeOut(2500, function () {
        //reset the z-index and unhide the image
        $active.css('z-index', 1).show().removeClass('active');
        //make the next image the top one
        $next.css('z-index', 3).addClass('active');
    });
}

/**
 * The document onReady function when the page is loaded
 */
$(document).ready(function () {
    //fade the background back in once all the images are loaded
    $('#background').fadeIn(500);

    // run every 30s
    setInterval(cycleImages, 30000);
});
