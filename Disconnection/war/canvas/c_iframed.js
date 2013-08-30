																																																																		(function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){(i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)})(window,document,'script','//www.google-analytics.com/analytics.js','ga');ga('create', 'UA-43203900-1', 'disconstats.appspot.com');ga('send', 'pageview');
	
// create a noop console object if the browser doesn't provide one ...
if (!window.console){
  window.console = {};
}

// IE has a console that has a 'log' function but no 'debug'. to make console.debug work in IE,
// we just map the function. (extend for info etc if needed)
else {
  if (!window.console.debug && typeof window.console.log !== 'undefined') {
    window.console.debug = window.console.log;
  }
}

// ... and create all functions we expect the console to have (took from firebug).
var names = ["log", "debug", "info", "warn", "error", "assert", "dir", "dirxml",
    "group", "groupEnd", "time", "timeEnd", "count", "trace", "profile", "profileEnd"];

for (var i = 0; i < names.length; ++i){
  if(!window.console[names[i]]){
    window.console[names[i]] = function() {};
  }
}


var w = 0;
var h = 0;

/**
 * update the canvas size, the one that is inside an iFrame
 * @param 
 */
var updCan = function updateCanvasSize() {
	try {
		if (window.frames.length <= 0) {
			console.warn("iFrame not found yet.")
			setTimeout(updCan, 1000);
			return;
		}
		if (window.frames[0].document.getElementsByTagName("canvas").length <= 0) {
			colsole.warn("Canvas was not found in parent frame");
			setTimeout(updCan, 1000);
			return;
		}
		if (w != window.innerWidth) {
			var can = window.frames[0].document.getElementsByTagName("canvas")[0]
			can.style.width = ""+ window.innerWidth + "px";
			can.style.height = ""+window.innerHeight + "px";
			w = window.innerWidth;
			h = window.innerHeight;
			console.debug("Canvas size was updated to: w="+w+", h="+h)
		}
		setTimeout(updCan, 100);
	} catch(e) {
		console.info(e);
		setTimeout(updCan, 1000);
	}
}

setTimeout(updCan, 100);
updCan();