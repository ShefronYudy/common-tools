package org.shefron.fc.filterchain;

import java.lang.reflect.Method;

public interface FuncFilterChain {

	public Object doFilter(Object obj, Method method, Object[] params);

}
