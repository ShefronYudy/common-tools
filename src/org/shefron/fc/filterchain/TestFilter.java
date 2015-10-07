package org.shefron.fc.filterchain;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TestFilter {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<FuncFilter> filters = new ArrayList<FuncFilter>();
		filters.add(new FuncFilterImpl1());
		filters.add(new FuncFilterImpl2());

		final Iterator it = filters.iterator();

		FuncFilterChain chain = new FuncFilterChain() {

			public Object doFilter(Object obj, Method method, Object[] params) {
				System.out.println("FuncFilterChain start ...");
				if (it.hasNext()) {
					FuncFilter filter = (FuncFilter) it.next();
					// 过滤器：增加对象值
					Integer in = (Integer) obj;
					return filter.doFilter(new Integer(in.intValue() + 1),
							method, params, this);
				} else {
					return obj;
				}
			}

		};

		Object ret = chain.doFilter(new Integer(1), null, null);
		//
		System.out.println(ret);

	}

}
