package org.lip6.struts.servletAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.lip6.struts.actionForm.AddContactValidationForm;
import org.lip6.struts.actionForm.UpdateContactValidationForm;
import org.lip6.struts.domain.DAOContact;
import org.lip6.struts.domain.DisplayAllContact;
import org.apache.struts.actions.DispatchAction;

public class ContactAction extends DispatchAction {

	public ActionForward add(final ActionMapping pMapping, ActionForm pForm, final HttpServletRequest pRequest,
			final HttpServletResponse pResponse) {

		final AddContactValidationForm lForm = (AddContactValidationForm) pForm;

		// Contact
		final long id = lForm.getId();

		// La fonction trim permet de supprimer les espaces en d�but et en fin
		// de cha�ne s'il y a
		// plusieurs espaces entre les mots, replaceAll les remplacent par un
		// seul espace
		final String firstName = lForm.getFirstName().trim().replaceAll(" +", " ");
		final String lastName = lForm.getLastName().trim().replaceAll(" +", " ");
		final String email = lForm.getEmail();

		// Address
		final String street = lForm.getStreet().trim().replaceAll(" +", " ");
		final String city = lForm.getCity().trim().replaceAll(" +", " ");
		final String zip = lForm.getZip().trim().replaceAll(" +", " ");
		final String country = lForm.getCountry().trim().replaceAll(" +", " ");

		// create a new Contact
		final DAOContact lDAOContact = new DAOContact();
		final String lError = lDAOContact.addContact(id, firstName, lastName, email, id, street, city, zip, country);
		final DisplayAllContact display = lDAOContact.displayAllContacts();

		if (display.getError() == null && lError == null) {

			pRequest.setAttribute("LISTECONTACTS", display.getContacts());
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

	public ActionForward delete(final ActionMapping pMapping, ActionForm pForm, final HttpServletRequest pRequest,
			final HttpServletResponse pResponse) {

		final String id = pRequest.getParameter("id");

		final DAOContact daoContact = new DAOContact();

		final String lError = daoContact.deleteContact(Integer.valueOf(id));
		final DisplayAllContact display = daoContact.displayAllContacts();

		if (display.getError() == null && lError == null) {

			pRequest.setAttribute("LISTECONTACTS", display.getContacts());
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

	public ActionForward update(final ActionMapping pMapping, ActionForm pForm, final HttpServletRequest pRequest,
			final HttpServletResponse pResponse) {

		System.out.println("Entre dans action update contact");

		final UpdateContactValidationForm lForm = (UpdateContactValidationForm) pForm;

		final long id = lForm.getId();
		final String firstName = lForm.getFirstName().trim().replaceAll(" +", " ");
		final String lastName = lForm.getLastName().trim().replaceAll(" +", " ");
		final String email = lForm.getEmail();

		final String street = lForm.getStreet().trim().replaceAll(" +", " ");
		final String city = lForm.getCity().trim().replaceAll(" +", " ");
		final String zip = lForm.getZip().trim().replaceAll(" +", " ");
		final String country = lForm.getCountry().trim().replaceAll(" +", " ");

		final DAOContact lDAOContact = new DAOContact();
		final String lError = lDAOContact.updateContact(id, lastName, firstName, email, street, city, zip, country);
		
		final DisplayAllContact display = lDAOContact.displayAllContacts();
		
		if (display.getError() == null && lError == null) {

			pRequest.setAttribute("LISTECONTACTS", display.getContacts());
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
