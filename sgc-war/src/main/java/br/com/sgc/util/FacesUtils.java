package br.com.sgc.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.FactoryFinder;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.context.FacesContextFactory;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.lifecycle.LifecycleFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import br.com.sgc.util.Jsf2NavigationHandler.NavigationCase;

public final class FacesUtils {

	private static final String FACES_REDIRECT_TRUE = "faces-redirect=true";
	public static final String PARAMETRO_JSF_REDIRECT = "?" + FACES_REDIRECT_TRUE;
	private final static Pattern REDIRECT_EX = Pattern.compile("(.*?)[?&]" + FACES_REDIRECT_TRUE + "$");

	protected static String getDefaultViewSuffix(FacesContext facesContext) {
		final String suffix = facesContext.getExternalContext().getInitParameter("javax.faces.DEFAULT_SUFFIX");
		return suffix != null ? suffix : ".jsp";
	}

	protected static String getTargetViewId(FacesContext facesContext, String outcome) {
		String targetViewId;
		String viewSuffix = getDefaultViewSuffix(facesContext);

		if (isRedirect(outcome)) {
			Matcher m = REDIRECT_EX.matcher(outcome);
			m.matches();
			targetViewId = m.group(1) + viewSuffix;
		} else {
			targetViewId = outcome + viewSuffix;
		}
		if (!targetViewId.startsWith("/")) {
			String currentViewId = facesContext.getViewRoot().getViewId();
			int index = currentViewId.lastIndexOf('/');
			if (index == -1) {
				index = 0;
			}
			targetViewId = currentViewId.substring(0, index) + "/" + targetViewId;
		}
		return targetViewId;
	}

	protected static boolean isRedirect(String outcome) {
		return REDIRECT_EX.matcher(outcome).matches();
	}

	protected static boolean setupNavigationConfig(InputStream is, Map<String, List<NavigationCase>> casesMap, List<NavigationCase> wildCards) throws Exception {

		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		Document doc = docBuilder.parse(is);
		NodeList rules = doc.getElementsByTagName("navigation-rule");
		List<NavigationCase> cases = null;
		NavigationCase navCase = null;
		for (int i = 0; i < rules.getLength(); i++) {
			Node rule = rules.item(i);
			NodeList children = rule.getChildNodes();
			String fromId = null;
			cases = new ArrayList<NavigationCase>();
			for (int j = 0; j < children.getLength(); j++) {
				Node child = children.item(j);
				if ("from-view-id".equalsIgnoreCase(child.getNodeName())) {
					fromId = child.getTextContent();
				} else if ("navigation-case".equalsIgnoreCase(child.getNodeName())) {
					navCase = new NavigationCase();
					navCase.fromViewId = fromId;
					NodeList navCaseChils = child.getChildNodes();
					for (int k = 0; k < navCaseChils.getLength(); k++) {
						Node nvchild = navCaseChils.item(k);
						if ("from-action".equalsIgnoreCase(nvchild.getNodeName())) {
							navCase.fromAction = nvchild.getTextContent();
						} else if ("from-outcome".equalsIgnoreCase(nvchild.getNodeName())) {
							navCase.fromOutcome = nvchild.getTextContent();
						} else if ("to-view-id".equalsIgnoreCase(nvchild.getNodeName())) {
							navCase.toViewId = nvchild.getTextContent();
						} else if ("redirect".equalsIgnoreCase(nvchild.getNodeName())) {
							navCase.redirect = true;
						}
					}
					if (fromId != null && fromId.indexOf('*') != -1) {
						wildCards.add(navCase);
					} else {
						cases.add(navCase);
					}
				}
			}
			if (fromId != null && fromId.indexOf('*') == -1) {
				if (casesMap.containsKey(fromId)) {
					cases.addAll(casesMap.get(fromId));
				}
				casesMap.put(fromId, cases);
			}
		}
		return !casesMap.isEmpty() || !wildCards.isEmpty();
	}

	protected static boolean wildCardMatch(String text, String pattern) {
		if (pattern.equals("*")) {
			return true;
		}

		// Create the cards by splitting using a RegEx. If more speed
		// is desired, a simpler character based splitting can be done.
		String[] cards = pattern.split("\\*");

		// Iterate over the cards.
		for (String card : cards) {
			int idx = text.indexOf(card);

			// Card not detected in the text.
			if (idx == -1) {
				return false;
			}

			// Move ahead, towards the right of the text.
			text = text.substring(idx + card.length());
		}

		return true;
	}

	public static FacesContext getFacesContext(HttpServletRequest request, HttpServletResponse response) {
		// Get current FacesContext.
		FacesContext facesContext = FacesContext.getCurrentInstance();

		// Check current FacesContext.
		if (facesContext == null) {

			// Create new Lifecycle.
			LifecycleFactory lifecycleFactory = (LifecycleFactory) FactoryFinder.getFactory(FactoryFinder.LIFECYCLE_FACTORY);
			Lifecycle lifecycle = lifecycleFactory.getLifecycle(LifecycleFactory.DEFAULT_LIFECYCLE);

			// Create new FacesContext.
			FacesContextFactory contextFactory = (FacesContextFactory) FactoryFinder.getFactory(FactoryFinder.FACES_CONTEXT_FACTORY);
			facesContext = contextFactory.getFacesContext(request.getSession().getServletContext(), request, response, lifecycle);

			// Create new View.
			UIViewRoot view = facesContext.getApplication().getViewHandler().createView(facesContext, "");
			facesContext.setViewRoot(view);

			// Set current FacesContext.
			FacesContextWrapper.setCurrentInstance(facesContext);
		}

		return facesContext;
	}

	// Helpers
	// -----------------------------------------------------------------------------------

	// Wrap the protected FacesContext.setCurrentInstance() in a inner class.
	private static abstract class FacesContextWrapper extends FacesContext {

		protected static void setCurrentInstance(FacesContext facesContext) {
			FacesContext.setCurrentInstance(facesContext);
		}
	}

}