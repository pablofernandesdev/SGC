package br.com.sgc.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.application.NavigationHandler;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import br.com.sgc.exception.NegocioException;


public class Jsf2NavigationHandler extends NavigationHandler {

	public static class NavigationCase {

		String fromViewId = null;
		String fromAction = null;
		String fromOutcome = null;
		String toViewId = null;
		String key = null;
		boolean redirect;

	}

	private Map<String, List<NavigationCase>> casesMap;
	private List<NavigationCase> wildCards;
	private boolean navigationConfigured;

	public Jsf2NavigationHandler() throws Exception {
		super();
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		InputStream is = null;
		try {
			is = externalContext.getResourceAsStream("WEB-INF/faces-config.xml");
		} catch (Exception e) {
		}
		casesMap = new HashMap<String, List<NavigationCase>>();
		wildCards = new ArrayList<NavigationCase>();
		navigationConfigured = false;
		if (is != null) {
			navigationConfigured = FacesUtils.setupNavigationConfig(is, casesMap, wildCards);
		}
	}

	private NavigationCase getDefaultNavigation(FacesContext facesContext, String fromAction, String outcome) {
		if (navigationConfigured) {
			String fromViewId = facesContext.getViewRoot().getViewId();
			List<NavigationCase> cases = casesMap.get(fromViewId);
			if (cases != null) {
				for (NavigationCase nc : cases) {
					if ((nc.fromAction != null && nc.fromAction.equals(fromAction) && nc.fromOutcome != null && nc.fromOutcome
							.equals(outcome))
							|| (nc.fromAction == null && nc.fromOutcome != null && nc.fromOutcome.equals(outcome))
							|| (nc.fromAction != null && nc.fromAction.equals(fromAction) && nc.fromOutcome == null)) {
						return nc;
					}
				}
			}
			for (NavigationCase nc : wildCards) {
				if (FacesUtils.wildCardMatch(fromViewId, nc.fromViewId)) {
					if ((nc.fromAction != null && nc.fromAction.equals(fromAction) && nc.fromOutcome != null && nc.fromOutcome
							.equals(outcome))
							|| (nc.fromAction == null && nc.fromOutcome != null && nc.fromOutcome.equals(outcome))
							|| (nc.fromAction != null && nc.fromAction.equals(fromAction) && nc.fromOutcome == null)) {
						return nc;
					}
				}
			}
		}
		return null;
	}

	@Override
	public void handleNavigation(FacesContext facesContext, String fromAction, String outcome) {
		if (outcome == null) {
			return; // no navigation
		}
		ExternalContext externalContext = facesContext.getExternalContext();
		ViewHandler viewHandler = facesContext.getApplication().getViewHandler();

		String targetViewId = null;
		boolean redirecting = false;

		NavigationCase navigationCase = getDefaultNavigation(facesContext, fromAction, outcome);

		if (navigationCase == null) {
			redirecting = FacesUtils.isRedirect(outcome);
			targetViewId = FacesUtils.getTargetViewId(facesContext, outcome);
		} else {
			redirecting = navigationCase.redirect;
			targetViewId = navigationCase.toViewId;
		}

		if (redirecting) {
			String redirectPath = viewHandler.getActionURL(facesContext, targetViewId);

			try {
				externalContext.redirect(externalContext.encodeActionURL(redirectPath));
			} catch (IOException e) {
				throw new FacesException(e.getMessage(), e);
			}
		} else {
			try {
				if (facesContext.getExternalContext().getResource(targetViewId) == null) {
					return;
				}
			} catch (MalformedURLException e) {
				throw new NegocioException(e);
			}
			UIViewRoot viewRoot = viewHandler.createView(facesContext, targetViewId);
			facesContext.setViewRoot(viewRoot);
			facesContext.renderResponse();
		}
	}

}
