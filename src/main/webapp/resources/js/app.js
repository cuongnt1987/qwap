$(document).ready(function() {

	/*trigger left navigator*/
	$('a#nav-trigger').click(function(event) {
		$('#list_menu').toggleClass('hidden');
	});
	/*End - trigger left navigator*/

	/*main navigator iscoll configuration*/
	var myScroll = new IScroll('#main-nav-wrapper', { eventPassthrough: true, scrollX: true, scrollY: false, preventDefault: false }); 

	document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);
	/*End - main navigator iscoll configuration*/

	/*Sticky navtivate*/
	var stickyNavTop = $('#main-nav').offset().top;
	 
	var stickyNav = function(){
		var scrollTop = $(window).scrollTop();    
		if (scrollTop > stickyNavTop) { 
		    $('#main-nav').addClass('sticky');
		} else {
		    $('#main-nav').removeClass('sticky'); 
		}
	};
	 
	stickyNav();
	 
	$(window).scroll(function() {
	    stickyNav();
	});

	/*End - Sticky navtivate*/

	$('.app-slider.responsive').slick({
	    lazyLoad: 'ondemand',
	    centerMode: false,
	    centerPadding: '60px',
	    dots: false,
	    arrows: false,
	    infinite: true,
	    speed: 300,
	    slidesToShow: 4,
	    slidesToScroll: 1,
	    autoplay: true,
	    autoplaySpeed: 2000,
	    responsive: [
	      {
	        breakpoint: 1024,
	        settings: {
	          arrows: false,
	          centerMode: false,
	          centerPadding: '40px',
	          slidesToShow: 3,
	          slidesToScroll: 1,
	          infinite: true,
	          dots: false
	        }
	      },
	      {
	        breakpoint: 600,
	        settings: {
	          arrows: false,
	          centerMode: false,
	          centerPadding: '40px',
	          slidesToShow: 2,
	          slidesToScroll: 1,
	          dots: false
	        }
	      },
	      {
	        breakpoint: 480,
	        settings: {
	          arrows: false,
	          centerMode: false,
	          centerPadding: '40px',
	          slidesToShow: 1,
	          slidesToScroll: 1,
	          dots: false
	        }
	      }
	    ]
	});

$('.app-slider.responsive3').slick({
	    lazyLoad: 'ondemand',
	    centerMode: false,
	    centerPadding: '60px',
	    dots: false,
	    arrows: false,
	    infinite: true,
	    speed: 300,
	    slidesToShow: 3,
	    slidesToScroll: 1,
	    autoplay: true,
	    autoplaySpeed: 2000,
	    responsive: [
	      {
	        breakpoint: 1024,
	        settings: {
	          arrows: false,
	          centerMode: false,
	          centerPadding: '40px',
	          slidesToShow: 3,
	          slidesToScroll: 1,
	          infinite: true,
	          dots: false
	        }
	      },
	      {
	        breakpoint: 600,
	        settings: {
	          arrows: false,
	          centerMode: false,
	          centerPadding: '40px',
	          slidesToShow: 2,
	          slidesToScroll: 1,
	          dots: false
	        }
	      },
	      {
	        breakpoint: 480,
	        settings: {
	          arrows: false,
	          centerMode: false,
	          centerPadding: '40px',
	          slidesToShow: 2,
	          slidesToScroll: 1,
	          dots: false
	        }
	      }
	    ]
	});

	$('.toggle-desc').click(function(event) {
		$('.desc_app').toggleClass('active');
	});
        
        $('.csbuttons').cSButtons();
});
