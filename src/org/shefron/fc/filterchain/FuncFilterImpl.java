package org.shefron.fc.filterchain;

import java.lang.reflect.Method;

public class FuncFilterImpl implements FuncFilter {

	public Object doFilter(Object obj, Method method, Object[] params,
			FuncFilterChain filterChain) {
		return filterChain.doFilter(obj, method, params);
	}

}
