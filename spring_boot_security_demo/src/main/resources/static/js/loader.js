// loader function
function showLoader() {
	$('#loading').show();
}

function hideLoader() {
	$('#loading').hide();
}

// Strongly recommended: Hide loader after 20 seconds, even if the page hasn't finished loading
setTimeout(hideLoader, 20 * 1000);

function setTimeOutForLoader() {
	setTimeout(hideLoader, 20 * 1000);
}