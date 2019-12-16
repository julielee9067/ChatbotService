respondToSizingMessage = function(e) {
    //if(e.origin == 'http://origin-domain.com'){
    // e.data is the string sent by the origin with postMessage.
    if(e.data == 'sizing?') {
        e.source.postMessage('sizing:'+$('html').height()+','+$('html').width(), e.origin);
    }
    //}
} // we have to listen for 'message'
window.addEventListener('message', respondToSizingMessage, false);