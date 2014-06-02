// Recent works thumbnail image height resize
//===========================================

$('.recent-works .thumbnail > .image').on(
		'resize',
		function() {
			$('.recent-works .thumbnail > .image').height(
					$('.recent-works .thumbnail > .image').width() / 1.6);
		}).resize();

// Sign In & Sign Out
// ==================

/*
 * $('#sign-in').on('click', function() { $("#user-bar").toggleClass("show
 * hidden"); $("#user-bar").toggleClass("animated2 flipInX");
 * $("#sign-in").toggleClass("hidden show"); $("#sign-up").toggleClass("hidden
 * show"); $("#sign-in").removeClass("animated2 flipInX");
 * $("#sign-up").removeClass("animated2 flipInX"); return false; });
 * 
 * $('#sign-out').on('click', function() { $("#user-bar").toggleClass("show
 * hidden"); $("#user-bar").toggleClass("animated2 flipInX");
 * $("#sign-in").toggleClass("hidden show"); $("#sign-in").addClass("animated2
 * flipInX"); $("#sign-up").toggleClass("hidden show");
 * $("#sign-up").addClass("animated2 flipInX"); return false; });
 */

// Style Toggle
// ============
$('.style-toggle-btn').on('click', function() {
	$(".style-toggle-btn").toggleClass("show hidden");
	$(".style-toggle").toggleClass("hidden show");
	return false;
});

$('.style-toggle-close').on('click', function() {
	$(".style-toggle-btn").toggleClass("show hidden");
	$(".style-toggle").toggleClass("hidden show");
	return false;
});

// Body Style Change
// =================

$('.style-toggle ul > li.green').on('click', function() {
	$("body").addClass("body-green");
	$("body").removeClass("body-blue");
	$("body").removeClass("body-red");
	$("body").removeClass("body-amethyst");
	return false;
});

$('.style-toggle ul > li.blue').on('click', function() {
	$("body").addClass("body-blue");
	$("body").removeClass("body-green");
	$("body").removeClass("body-red");
	$("body").removeClass("body-amethyst");
	return false;
});

$('.style-toggle ul > li.red').on('click', function() {
	$("body").addClass("body-red");
	$("body").removeClass("body-green");
	$("body").removeClass("body-blue");
	$("body").removeClass("body-amethyst");
	return false;
});

$('.style-toggle ul > li.amethyst').on('click', function() {
	$("body").addClass("body-amethyst");
	$("body").removeClass("body-blue");
	$("body").removeClass("body-red");
	$("body").removeClass("body-green");
	return false;
});

// Lost password
// =============

$('#lost-btn').on('click', function() {
	$("#lost-form").toggleClass("show hidden");
	return false;
});

// Contact Us
// ==========

$('#signed-in').on(
		'click',
		function() {
			$(".form-white > .contact-avatar > span")
					.toggleClass("show hidden");
			$(".form-white > .contact-avatar > img").toggleClass(
					"show hidden animated2 flipInX");
			return false;
		});

$('#signed-in').on('click', function() {
	$("#email-contact").toggleClass("show hidden");
	$("#email-contact-disabled").toggleClass("show hidden");
	$("#name-1").toggleClass("show hidden");
	$("#name-1-disabled").toggleClass("show hidden");
	$("#name-2").toggleClass("show hidden");
	$("#name-2-disabled").toggleClass("show hidden");
	return false;
});

$('#signed-in').on('click', function() {
	$("#signed-in > i").toggleClass("fa-circle-o fa-dot-circle-o");
	return false;
});

// Search box toggle
// =================
$('#search-btn').on('click', function() {
	$("#search-icon").toggleClass('fa-search fa-times margin-2');
	$("#search-box").toggleClass('hidden show animated2 flipInX');
	return false;
});

// Error page
// ==========

var divs = $("i.random").get().sort(function() {
	return Math.round(Math.random()) - 0.5; // random so we get the right +/-
	// combo
}).slice(0, 1);
$(divs).show();

var divs = $("i.random2").get().sort(function() {
	return Math.round(Math.random()) - 0.5; // random so we get the right +/-
	// combo
}).slice(0, 1);
$(divs).show();

var divs = $("i.random3").get().sort(function() {
	return Math.round(Math.random()) - 0.5; // random so we get the right +/-
	// combo
}).slice(0, 1);
$(divs).show();

// Corporate Index Features
// ========================

$('.crp-ft-action').hover(function() {
	$(this).children("a").toggleClass("show hidden");
	$(this).children("a").toggleClass("animated2 flipInX");
	return false;
});

// Add scrolling to specific anchors
// ==================================

$(function() {
	var $root = $('html, body');

	$('a').click(function() {
		var href = $.attr(this, 'href');
		if (href == '#early-user') {
			$root.animate({
				scrollTop : $(href).offset().top
			}, 500, function() {
				window.location.hash = href;
			});
			return false;
		}
		return true;
	});
});

// Ajax call
// ==================================

function ajaxCall(button, form, json, divResult, jsToExecuteWhenSucess) {
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	var l = Ladda.create(document.querySelector(button));
	l.start();
	$
			.ajax({
				type : 'POST',
				beforeSend : function(xhr) {
					xhr.setRequestHeader(header, token);
				},
				url : $(form).attr('action'),
				data : JSON.stringify(json),
				contentType : 'application/json',
				cache : false,
				success : function(response) {
					var type = "";
					if (response.status == "SUCCESS") {
						type = "success";
					} else if (response.status == "FAIL") {
						type = "danger";
					}
					responseInDiv = "<div class='alert alert-" + type
							+ " alert-dismissable'>";
					responseInDiv += "<button type='button' class='close' data-dismiss='alert' aria-hidden='true'>&times;</button>";
					for (var i = 0; i < response.result.length; i++) {
						responseInDiv += response.result[i] + "<br />";
					}
					responseInDiv += "</div><br />";
					$(divResult).html(responseInDiv);
					if (response.status == "SUCCESS") {
						jsToExecuteWhenSucess();
					}
					l.stop();
				}
			});
};

// Ladda buttons style
// ==================================

Ladda.bind('.ladda-button');
