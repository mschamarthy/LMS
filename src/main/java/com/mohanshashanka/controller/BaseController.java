package com.mohanshashanka.controller;

import com.mohanshashanka.controller.helpers.Context;
import com.mohanshashanka.controller.helpers.StageHelper;

public abstract class BaseController implements Controller{

	// Wrapper class to hold the values that are needed in the controller logic
	// & that which needs to be passed to next controller like stage & user details.
	protected Context context;

	public BaseController() {
	}

	public void setContext(Context context) {
		this.context = context;
		initController();
	}
	
	protected void setTitle(String title){
		this.context.getStage().setTitle(title);
	}
	
	public void logout(){
		this.context.setUser(null);
		StageHelper.setScene(context, "LoginView");
	}
	
	public abstract void initController();

}
