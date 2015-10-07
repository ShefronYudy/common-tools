package org.shefron.fc.filterchain;

import java.lang.reflect.Method;

public interface FuncFilter {

	public Object doFilter(Object obj, Method method, Object[] params,
			FuncFilterChain filterChain);

}
