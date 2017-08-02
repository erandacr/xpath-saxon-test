package tryout.xpath.wso2;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.xml.namespace.NamespaceContext;

public class DOMSynapseXPathNamespaceMap implements NamespaceContext {
    Map<String, String> prefixToURIMap = new HashMap<String, String>();
    Set<String> knownPrefixMap = new HashSet<String>();

    public DOMSynapseXPathNamespaceMap() {
        // Adding known prefixes to synapse namespace
        knownPrefixMap.add("syn");
    }

    public String getNamespaceURI(String prefix) {
            return prefixToURIMap.get(prefix);
    }

    public String getPrefix(String namespaceURI) {
        return null;
    }

    public Iterator getPrefixes(String namespaceURI) {
        return null;
    }

    public void addNamespace(String prefix, String uri) {
        prefixToURIMap.put(prefix, uri);
    }
}
