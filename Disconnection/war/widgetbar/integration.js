(function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){(i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)})(window,document,'script','//www.google-analytics.com/analytics.js','ga');

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

console.debug ("Integration script is loaded.")
document.write("<a id='catchMe' href='htcmd:'></a>");
console.debug ("Integration script, <a> is created.")
ga('create', 'UA-42857182-1', 'disconstats.appspot.com');
ga('send', 'pageview');

/**
 * should be called by widget in order to send message to C++ client
 * @param message - valid json message
 */
function sendMessage(message) {
	try {
		console.debug("Sending message to C++ client:" + message);
		var a = document.getElementById('catchMe');
		a.href="htcmd:sendMessage?message=" + message;
		a.click();
		console.debug("HTCMD message is sent.");
	} catch(e) {
		console.info(e);
	}
}

/**
 * called by C++ client when message should be sent to widget
 * @param message - message from C++ client
 */
function handleMessageIntern(message) {
	try {
		console.debug("Received message from C++ client:" + message);
		window.handleMessage(message);
	} catch(e) {
		console.info(e);
	}
}
