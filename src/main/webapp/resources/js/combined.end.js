function ajaxCallPost(e,t,o,s,n,a){ajaxCallPostWithUrl(e,$(t).attr("action"),o,s,n,a)}function ajaxCallPostWithUrl(e,t,o,s,n,a){var r=$("meta[name='_csrf']").attr("content"),l=$("meta[name='_csrf_header']").attr("content");if(null!=e){var i=Ladda.create(document.querySelector(e));i.start()}$.ajax({type:"POST",beforeSend:function(e){e.setRequestHeader(l,r)},url:t,data:JSON.stringify(o),contentType:"application/json",cache:!1,success:function(t){var o="";"SUCCESS"==t.status?o="success":"FAIL"==t.status&&(o="danger"),responseInDiv=Boolean(a)?"":"<br />",responseInDiv+="<div class='alert alert-"+o+" alert-dismissable'>",responseInDiv+="<button type='button' class='close' data-dismiss='alert' aria-hidden='true'>&times;</button>";for(var r=0;r<t.result.length;r++)responseInDiv+=t.result[r]+"<br />";responseInDiv+="</div>",$(s).html(responseInDiv),"SUCCESS"==t.status&&null!=n&&n(),null!=e&&i.stop()}})}function ajaxCallGetAndRefresh(e,t){var o=$("meta[name='_csrf']").attr("content"),s=$("meta[name='_csrf_header']").attr("content");$.ajax({type:"GET",beforeSend:function(e){e.setRequestHeader(s,o)},url:e+"/get",contentType:"text/plain",cache:!1,success:function(e){var o=$(t);o.is("input")?o.val(e):o.html(e)}})}jQuery.uaMatch=function(e){e=e.toLowerCase();var t=/(chrome)[ \/]([\w.]+)/.exec(e)||/(webkit)[ \/]([\w.]+)/.exec(e)||/(opera)(?:.*version|)[ \/]([\w.]+)/.exec(e)||/(msie) ([\w.]+)/.exec(e)||e.indexOf("compatible")<0&&/(mozilla)(?:.*? rv:([\w.]+)|)/.exec(e)||[];return{browser:t[1]||"",version:t[2]||"0"}},jQuery.browser||(matched=jQuery.uaMatch(navigator.userAgent),browser={},matched.browser&&(browser[matched.browser]=!0,browser.version=matched.version),browser.chrome?browser.webkit=!0:browser.webkit&&(browser.safari=!0),jQuery.browser=browser),$(".recent-works .thumbnail > .image").on("resize",function(){$(".recent-works .thumbnail > .image").height($(".recent-works .thumbnail > .image").width()/1.6)}).resize(),$(".style-toggle-btn").on("click",function(){return $(".style-toggle-btn").toggleClass("show hidden"),$(".style-toggle").toggleClass("hidden show"),!1}),$(".style-toggle-close").on("click",function(){return $(".style-toggle-btn").toggleClass("show hidden"),$(".style-toggle").toggleClass("hidden show"),!1}),$(".style-toggle ul > li.green").on("click",function(){return $("body").addClass("body-green"),$("body").removeClass("body-blue"),$("body").removeClass("body-red"),$("body").removeClass("body-amethyst"),!1}),$(".style-toggle ul > li.blue").on("click",function(){return $("body").addClass("body-blue"),$("body").removeClass("body-green"),$("body").removeClass("body-red"),$("body").removeClass("body-amethyst"),!1}),$(".style-toggle ul > li.red").on("click",function(){return $("body").addClass("body-red"),$("body").removeClass("body-green"),$("body").removeClass("body-blue"),$("body").removeClass("body-amethyst"),!1}),$(".style-toggle ul > li.amethyst").on("click",function(){return $("body").addClass("body-amethyst"),$("body").removeClass("body-blue"),$("body").removeClass("body-red"),$("body").removeClass("body-green"),!1}),$("#lost-btn").on("click",function(){return $("#lost-form").toggleClass("show hidden"),setTimeout(function(){$("#email-lost").focus()},1e3),!1}),$("#signed-in").on("click",function(){return $(".form-white > .contact-avatar > span").toggleClass("show hidden"),$(".form-white > .contact-avatar > img").toggleClass("show hidden animated2 flipInX"),!1}),$("#signed-in").on("click",function(){return $("#email-contact").toggleClass("show hidden"),$("#email-contact-disabled").toggleClass("show hidden"),$("#name-1").toggleClass("show hidden"),$("#name-1-disabled").toggleClass("show hidden"),$("#name-2").toggleClass("show hidden"),$("#name-2-disabled").toggleClass("show hidden"),!1}),$("#signed-in").on("click",function(){return $("#signed-in > i").toggleClass("fa-circle-o fa-dot-circle-o"),!1}),$("#search-btn").on("click",function(){return $("#search-icon").toggleClass("fa-search fa-times margin-2"),$("#search-box").toggleClass("hidden show animated2 flipInX"),!1});var divs=$("i.random").get().sort(function(){return Math.round(Math.random())-.5}).slice(0,1);$(divs).show();var divs=$("i.random2").get().sort(function(){return Math.round(Math.random())-.5}).slice(0,1);$(divs).show();var divs=$("i.random3").get().sort(function(){return Math.round(Math.random())-.5}).slice(0,1);$(divs).show(),$(".crp-ft-action").hover(function(){return $(this).children("a").toggleClass("show hidden"),$(this).children("a").toggleClass("animated2 flipInX"),!1}),$(function(){$("html, body");$("a").click(function(){$.attr(this,"href");return!0})});

var scrolltotop={setting:{startline:100,scrollto:0,scrollduration:1e3,fadeduration:[500,100]},controlHTML:'<i class="fa fa-angle-up backtotop"></i>',controlattrs:{offsetx:5,offsety:5},anchorkeyword:"#top",state:{isvisible:!1,shouldvisible:!1},scrollup:function(){this.cssfixedsupport||this.$control.css({opacity:0});var t=isNaN(this.setting.scrollto)?this.setting.scrollto:parseInt(this.setting.scrollto);t="string"==typeof t&&1==jQuery("#"+t).length?jQuery("#"+t).offset().top:0,this.$body.animate({scrollTop:t},this.setting.scrollduration)},keepfixed:function(){var t=jQuery(window),o=t.scrollLeft()+t.width()-this.$control.width()-this.controlattrs.offsetx,s=t.scrollTop()+t.height()-this.$control.height()-this.controlattrs.offsety;this.$control.css({left:o+"px",top:s+"px"})},togglecontrol:function(){var t=jQuery(window).scrollTop();this.cssfixedsupport||this.keepfixed(),this.state.shouldvisible=t>=this.setting.startline?!0:!1,this.state.shouldvisible&&!this.state.isvisible?(this.$control.stop().animate({opacity:1},this.setting.fadeduration[0]),this.state.isvisible=!0):0==this.state.shouldvisible&&this.state.isvisible&&(this.$control.stop().animate({opacity:0},this.setting.fadeduration[1]),this.state.isvisible=!1)},init:function(){jQuery(document).ready(function(t){var o=scrolltotop,s=document.all;o.cssfixedsupport=!s||s&&"CSS1Compat"==document.compatMode&&window.XMLHttpRequest,o.$body=t(window.opera?"CSS1Compat"==document.compatMode?"html":"body":"html,body"),o.$control=t('<div id="topcontrol">'+o.controlHTML+"</div>").css({position:o.cssfixedsupport?"fixed":"absolute",bottom:o.controlattrs.offsety,right:o.controlattrs.offsetx,opacity:0,cursor:"pointer"}).attr({title:""}).click(function(){return o.scrollup(),!1}).appendTo("body"),document.all&&!window.XMLHttpRequest&&""!=o.$control.text()&&o.$control.css({width:o.$control.width()}),o.togglecontrol(),t('a[href="'+o.anchorkeyword+'"]').click(function(){return o.scrollup(),!1}),t(window).bind("scroll resize",function(){o.togglecontrol()})})}};scrolltotop.init();

!function(e){"function"==typeof define&&define.amd?define(["jquery"],e):e(jQuery)}(function(e){function t(t){var a={},l=/^jQuery\d+$/;return e.each(t.attributes,function(e,t){t.specified&&!l.test(t.name)&&(a[t.name]=t.value)}),a}function a(t,a){var l=this,o=e(l);if(l.value==o.attr("placeholder")&&o.hasClass(p.customClass))if(o.data("placeholder-password")){if(o=o.hide().nextAll('input[type="password"]:first').show().attr("id",o.removeAttr("id").data("placeholder-id")),t===!0)return o[0].value=a;o.focus()}else l.value="",o.removeClass(p.customClass),l==r()&&l.select()}function l(){var l,r=this,o=e(r),n=this.id;if(""===r.value){if("password"===r.type){if(!o.data("placeholder-textinput")){try{l=o.clone().attr({type:"text"})}catch(s){l=e("<input>").attr(e.extend(t(this),{type:"text"}))}l.removeAttr("name").data({"placeholder-password":o,"placeholder-id":n}).bind("focus.placeholder",a),o.data({"placeholder-textinput":l,"placeholder-id":n}).before(l)}o=o.removeAttr("id").hide().prevAll('input[type="text"]:first').attr("id",n).show()}o.addClass(p.customClass),o[0].value=o.attr("placeholder")}else o.removeClass(p.customClass)}function r(){try{return document.activeElement}catch(e){}}var o,n,s="[object OperaMini]"==Object.prototype.toString.call(window.operamini),c="placeholder"in document.createElement("input")&&!s,d="placeholder"in document.createElement("textarea")&&!s,i=e.valHooks,u=e.propHooks;if(c&&d)n=e.fn.placeholder=function(){return this},n.input=n.textarea=!0;else{var p={};n=e.fn.placeholder=function(t){var r={customClass:"placeholder"};p=e.extend({},r,t);var o=this;return o.filter((c?"textarea":":input")+"[placeholder]").not("."+p.customClass).bind({"focus.placeholder":a,"blur.placeholder":l}).data("placeholder-enabled",!0).trigger("blur.placeholder"),o},n.input=c,n.textarea=d,o={get:function(t){var a=e(t),l=a.data("placeholder-password");return l?l[0].value:a.data("placeholder-enabled")&&a.hasClass(p.customClass)?"":t.value},set:function(t,o){var n=e(t),s=n.data("placeholder-password");return s?s[0].value=o:n.data("placeholder-enabled")?(""===o?(t.value=o,t!=r()&&l.call(t)):n.hasClass(p.customClass)?a.call(t,!0,o)||(t.value=o):t.value=o,n):t.value=o}},c||(i.input=o,u.value=o),d||(i.textarea=o,u.value=o),e(function(){e(document).delegate("form","submit.placeholder",function(){var t=e("."+p.customClass,this).each(a);setTimeout(function(){t.each(l)},10)})}),e(window).bind("beforeunload.placeholder",function(){e("."+p.customClass).each(function(){this.value=""})})}}),$(document).ready(function(){$("input").placeholder()});
