package kutokit.model;

import java.util.ArrayList;

import kutokit.view.components.Context;


public class Contexts {

	private ArrayList<Context> context_Arr = new ArrayList<Context>();
	public int contextId  = 0;

	public Contexts()
	{
	}

	public void addContext(Context context)
	{
		context_Arr.add(context);
		contextId ++;
	}

	public void deleteContext(int id) {
		for (Context c : context_Arr) {
            if (c.getcontextID()==id) {
                context_Arr.remove(c);
                contextId --;
                return;
            }
        }
	}

	public ArrayList<Context> getContext_Arr() {
		return context_Arr;
	}

	public int getContextId() {
		return contextId;
	}

	public void setContextId(int contextId) {
		this.contextId = contextId;
	}

}
