package it.unipd.dei.bding.erasmusadvisor.beans;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;


/**
 * Some utilities to populate beans, usually based on incoming request
 * parameters. Requires three packages from the Apache Commons library:
 * beanutils, collections, and logging. To obtain these packages, see
 * http://jakarta.apache.org/commons/. Also, the book's source code archive (see
 * http://www.coreservlets.com/) contains links to all URLs mentioned in the
 * book, including to the specific sections of the Jakarta Commons package.
 * <P>
 * Note that this class is in the coreservlets.beans package, so must be
 * installed in .../coreservlets/beans/.
 */

public class BeanUtilities {
	/**
	 * Examines all of the request parameters to see if any match a bean
	 * property (i.e., a setXxx method) in the object. If so, the request
	 * parameter value is passed to that method. If the method expects an int,
	 * Integer, double, Double, or any of the other primitive or wrapper types,
	 * parsing and conversion is done automatically. If the request parameter
	 * value is malformed (cannot be converted into the expected type), numeric
	 * properties are assigned zero and boolean properties are assigned false:
	 * no exception is thrown.
	 */

	public static void populateBean(Object formBean, HttpServletRequest request) {
		populateBean(formBean, request.getParameterMap());
	}

	/**
	 * Populates a bean based on a Map: Map keys are the bean property names;
	 * Map values are the bean property values. Type conversion is performed
	 * automatically as described above.
	 */

	@SuppressWarnings("rawtypes")
	public static void populateBean(Object bean, Map propertyMap) {
		try {
			Map sanitized=BeanUtilities.sanitize(propertyMap);
			BeanUtils.populate(bean, sanitized);
		} catch (Exception e) {
			// Empty catch. The two possible exceptions are
			// java.lang.IllegalAccessException and
			// java.lang.reflect.InvocationTargetException.
			// In both cases, just skip the bean operation.
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map sanitize(Map propertyMap){
		PolicyFactory policy = Sanitizers.FORMATTING.and(Sanitizers.LINKS);
		for (Object key : propertyMap.keySet()) {
			Object valueObj = propertyMap.get(key);
			if (valueObj instanceof String) {
				String value = (String) valueObj;
				value = policy.sanitize(value);
				propertyMap.put(key, value);
			}
		}
		return propertyMap;
	}
	
	public static String sanitize(String html){
		PolicyFactory policy = Sanitizers.FORMATTING.and(Sanitizers.LINKS);
		return policy.sanitize(html);
	}
}
