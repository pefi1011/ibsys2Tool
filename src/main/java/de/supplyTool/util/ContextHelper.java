package de.supplyTool.util;

import javax.faces.context.FacesContext;

public class ContextHelper {

	public static <T> T getManagedBean(Class<T> clazz) {
		try {

			FacesContext context = FacesContext.getCurrentInstance();
			String charAt = String.valueOf(clazz.getSimpleName().toString()
					.charAt(0));
			String ersterBuchstabeVomName = charAt.toLowerCase();
			String name = ersterBuchstabeVomName
					+ clazz.getSimpleName().substring(1,
							clazz.getSimpleName().length());
			return context.getApplication().evaluateExpressionGet(context,
					"#{" + name + "}", clazz);
		} catch (Exception e) {
			return null;
		}
	}
}
