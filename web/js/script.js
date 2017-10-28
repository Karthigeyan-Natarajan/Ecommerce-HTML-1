$(document).scroll(function(e){
    var scrollTop = $(document).scrollTop();
    if(scrollTop > 20){
        console.log(scrollTop);
        $('.navbar').removeClass('navbar-static-top').addClass('navbar-fixed-top');
    } else {
        $('.navbar').removeClass('navbar-fixed-top').addClass('navbar-static-top');
    }
});


var ajax = {};
ajax.x = function () {
    if (typeof XMLHttpRequest !== 'undefined') {
        return new XMLHttpRequest();
    }
    var versions = [
        "MSXML2.XmlHttp.6.0",
        "MSXML2.XmlHttp.5.0",
        "MSXML2.XmlHttp.4.0",
        "MSXML2.XmlHttp.3.0",
        "MSXML2.XmlHttp.2.0",
        "Microsoft.XmlHttp"
    ];

    var xhr;
    for (var i = 0; i < versions.length; i++) {
        try {
            xhr = new ActiveXObject(versions[i]);
            break;
        } catch (e) {
        }
    }
    return xhr;
};

ajax.send = function (url, callback, method, data, async) {
    if (async === undefined) {
        async = true;
    }
    var x = ajax.x();
    x.open(method, url, async);
    x.onreadystatechange = function () {
        if (x.readyState == 4) {
            callback(x.responseText)
        }
    };
    if (method == 'POST') {
        x.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    }
    x.send(data)
};

ajax.get = function (url, data, callback, async) {
    var query = [];
    for (var key in data) {
        query.push(encodeURIComponent(key) + '=' + encodeURIComponent(data[key]));
    }
    ajax.send(url + (query.length ? '?' + query.join('&') : ''), callback, 'GET', null, async)
};

ajax.post = function (url, data, callback, async) {
    var query = [];
    for (var key in data) {
        query.push(encodeURIComponent(key) + '=' + encodeURIComponent(data[key]));
    }
    ajax.send(url, callback, 'POST', query.join('&'), async)
};

window.onscroll = function() {myscroll()};
paginationFlag=false;
function myscroll() {
	//console.log((document.documentElement.scrollTop||document.body.scrollTop)+' '+window.innerHeight+' '+document.body.scrollHeight);
	//console.log( ((document.documentElement.scrollTop||document.body.scrollTop) + window.innerHeight) +'  '+ (document.body.scrollHeight - 100) )
	if(paginationFlag==false && (document.documentElement.scrollTop||document.body.scrollTop) + window.innerHeight >= document.body.scrollHeight - 100) {
		paginationFlag=true;	
		pagination();
	}
	if(paginationFlag==true && (document.documentElement.scrollTop||document.body.scrollTop) + window.innerHeight < document.body.scrollHeight - 100) {
		paginationFlag=false;	
	}
	if (document.body.scrollTop > 300 || document.documentElement.scrollTop > 300) {
        document.getElementById("myTop").style.display = "block";
    } else {
        document.getElementById("myTop").style.display = "none";
    }
}
function pagination() {}


var pageindex=0;
function pagination() {
	if(++pageindex>=5) return;
	url=window.location.href;
	url = url.replace(".html",pageindex+".html");
	ajax.get(url,{},function(r) {
		if(r=='') {
		} else {
			//alert(r);
			var a = document.getElementById("loader").innerHTML;
			document.getElementById("loader").innerHTML = a + r ;
		}
	});
}



function topFunction() {
    scrollToTop();
}

var timeOut;
var scroll=false;
function scrollToTop() {
	
	if (document.body.scrollTop!=0 || document.documentElement.scrollTop!=0){
		window.scrollBy(0,-5);
		timeOut=setTimeout('scrollToTop()',10);
		scroll=true;
	}
	else { 
		clearTimeout(timeOut);
		scroll=false;
	}
}
