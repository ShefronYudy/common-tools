package org.shefron.fc.filterchain;

import java.lang.reflect.Method;

public class FuncFilterImpl1 implements FuncFilter {

	public Object doFilter(Object obj, Method method, Object[] params,
			FuncFilterChain filterChain) {

		System.out.println("FuncFilterImpl1 start ...");

		return filterChain.doFilter(obj, method, params);
	}

}
