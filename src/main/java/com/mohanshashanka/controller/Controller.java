package com.mohanshashanka.controller;

import com.mohanshashanka.controller.helpers.Context;

public interface Controller {
	public void setContext(Context context);
	public void logout();
	public void initController();
}
