package cn.cellcom.szba.filter;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Vector;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.lang.StringUtils;

public class XssFilter implements Filter {

	static class FilteredRequest extends HttpServletRequestWrapper {

		private final Map<String, String[]> paras =new HashMap<String, String[]>();

		public FilteredRequest(ServletRequest request) {
			super((HttpServletRequest) request);
			buildMap(request.getParameterMap());
		}

		private void buildMap(Map<String, String[]> sourceMap) {
			Set<Entry<String, String[]>> paraSet = sourceMap.entrySet();
			Iterator<Entry<String, String[]>> paraIterator = paraSet.iterator();
			while(paraIterator.hasNext()){
				Entry<String, String[]> paras = paraIterator.next();
				String name = initName(paras.getKey());
				if(StringUtils.isNotBlank(name)&&paras.getValue()!=null&&paras.getValue().length>0){
					Vector<String> valVector=new Vector<String>();
					for(String val:paras.getValue()){
						String initVal=initValue(val);
						if(name.endsWith("password")||name.endsWith("Pwd")||name.equals("addressBook")){
							initVal=val;
						}
						if(StringUtils.isNotBlank(initVal)){
							valVector.addElement(initVal);
						}
					}
					valVector.trimToSize();
					if(!valVector.isEmpty()){
						this.paras.put(name,valVector.toArray(new String[0]));
					}
				}
			}
		}

		private String initName(String sourceName) {
			return sourceName == null ? sourceName : sourceName.replaceAll(
					"[\\[\\]\\(\\)]", "");
		}

		private String initValue(String sourceValue) {
			if (StringUtils.isBlank(sourceValue)) {
				return null;
			} else {
				return StringUtils.trimToEmpty(sourceValue)
						.replaceAll("<", "&lt;").replaceAll(">", "&gt;")
						.replaceAll("\"", "&quot;").replaceAll("'", "&apos;")
						.replaceAll("[\\[\\]\\(\\)]", "");
			}
		}

		@Override
		public Map<String, String[]> getParameterMap() {
			return this.paras;
		}

		@Override
		public Enumeration<String> getParameterNames() {
			Vector<String> names = new Vector<String>(this.paras.keySet());
			return names.elements();
		}

		@Override
		public String getParameter(String paramName) {
			String[] values = this.paras.get(paramName);
			return (values != null && values.length > 0) ? values[0] : null;
		}

		@Override
		public String[] getParameterValues(String paramName) {
			return this.paras.get(paramName);
		}
	}

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		String uri = ((HttpServletRequest) request).getServletPath();
		if (StringUtils.endsWith(uri, "jsp") || StringUtils.endsWith(uri, "do")) {
			chain.doFilter(new FilteredRequest(request), response);
			return;
		}
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
	}

}
