/*
* These functions taken from online article here:
* http://www.webreference.com/programming/javascript/definitive2/index.html
* They are not included under the Apache license for the rest of the source.
*/

var TimelineHelper = function()
{
}

/**
 * Create a new Document object. If no arguments are specified,
 * the document will be empty. If a root tag is specified, the document
 * will contain that single root tag. If the root tag has a namespace
 * prefix, the second argument must specify the URL that identifies the
 * namespace.
 */

TimelineHelper.newDocument = function(rootTagName, namespaceURL) {
  if (!rootTagName) rootTagName = "";
  if (!namespaceURL) namespaceURL = "";
  if (document.implementation && document.implementation.createDocument) {
    // This is the W3C standard way to do it
    return document.implementation.createDocument(namespaceURL, rootTagName, null);
  }
  else { // This is the IE way to do it
    // Create an empty document as an ActiveX object
    // If there is no root element, this is all we have to do
    var doc = new ActiveXObject("MSXML2.DOMDocument");
    // If there is a root tag, initialize the document
    if (rootTagName) {
      // Look for a namespace prefix
      var prefix = "";
      var tagname = rootTagName;
      var p = rootTagName.indexOf(':');
      if (p != -1) {
        prefix = rootTagName.substring(0, p);
        tagname = rootTagName.substring(p+1);
      }
      // If we have a namespace, we must have a namespace prefix
      // If we don't have a namespace, we discard any prefix
      if (namespaceURL) {
        if (!prefix) prefix = "a0"; // What Firefox uses
      }
      else prefix = "";
      // Create the root element (with optional namespace) as a
      // string of text
      var text = "<" + (prefix?(prefix+":"):"") +  tagname +
          (namespaceURL
           ?(" xmlns:" + prefix + '="' + namespaceURL +'"')
           :"") +
          "/>";
      // And parse that text into the empty document
      doc.loadXML(text);
    }
    return doc;
  }
};


/**
 * Parse the XML document contained in the string argument and return
 * a Document object that represents it.
 */
TimelineHelper.parseXML = function(text) {
    if (typeof DOMParser != "undefined") {
        // Mozilla, Firefox, and related browsers
        return (new DOMParser()).parseFromString(text, "application/xml");
    }
    else if (typeof ActiveXObject != "undefined") {
        // Internet Explorer.
        var doc = TimelineHelper.newDocument();  // Create an empty document
        doc.loadXML(text);            // Parse text into it
        return doc;                   // Return it
    }
    else {
        // As a last resort, try loading the document from a data: URL
        // This is supposed to work in Safari. Thanks to Manos Batsis and
        // his Sarissa library (sarissa.sourceforge.net) for this technique.
        var url = "data:text/xml;charset=utf-8," + encodeURIComponent(text);
        var request = new XMLHttpRequest();
        request.open("GET", url, false);
        request.send(null);
        return request.responseXML;
    }
};

