    	function setLoadingMessage(msg) {
    		document.getElementById('loadingMsg').innerHTML = msg;
    	};

    	function showLoadingIndicator(show) {
    		visibility="";
    		if (show) {
    			visibility = "visible";
    		}
    		else {
    			visibility = "hidden";
    		}
    		
    		if (document.getElementById) { // DOM3 = IE5, NS6
    			document.getElementById('loadingWrapper').style.visibility = visibility;
    		}
    		else if (document.layers) { // Netscape 4
    			document.hideShow.visibility = visibility;
    		}
    		else { // IE 4
    			document.all.hideShow.style.visibility = visibility;
    		}
    	};
