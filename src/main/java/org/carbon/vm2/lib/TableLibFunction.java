package org.carbon.vm2.lib;

import org.carbon.vm2.LuaValue;

class TableLibFunction extends LibFunction {
	public LuaValue call() {
		return argerror(1, "table expected, got no value");
	}
}
