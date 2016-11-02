package org.lip6.struts.servletAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.Globals;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.lip6.struts.domain.DAOContact;
import org.lip6.struts.domain.DAOGroup;
import org.lip6.struts.domain.DisplayAllContact;

public class DeleteGroupAction extends Action {

	public ActionForward execute(final ActionMapping pMapping, ActionForm pForm, final HttpServletRequest pRequest,
			final HttpServletResponse pResponse) {

		final String groupName = pRequest.getParameter("groupName");
		final String id = (String) pRequest.getParameter("id");

		final DAOGroup daoGroup = new DAOGroup();
		final DAOContact daoContact = new DAOContact();

		final String lError = daoGroup.deleteGroup(Integer.parseInt(id), groupName);
		final DisplayAllContact display = daoContact.displayContact(Integer.parseInt(id));

		if (display.getError() == null && lError == null) {

			pRequest.setAttribute("CONTACT", display.getContacts());
			return pMapping.findForward("success");
		} else if (lError == null) {
			final ActionMessages lErreurs = getErrors(pRequest);
			final ActionMessage lActionMessage = new ActionMessage(display.getError(), false);
			lErreurs.add(Globals.ERROR_KEY, lActionMessage);
			saveErrors(pRequest, lErreurs);
			return pMapping.findForward("error");
		} else {
			final ActionMessages lErreurs = getErrors(pRequest);
			final ActionMessage lActionMessage = new ActionMessage(lError, false);
			lErreurs.add(Globals.ERROR_KEY, lActionMessage);
			saveErrors(pRequest, lErreurs);
			return pMapping.findForward("error");
		}
	}
}
