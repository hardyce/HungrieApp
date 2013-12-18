package ie.hungr.hungrieapp.activities.customUI;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Spinner;

public class CustomSpinner extends Spinner {

	private int childPosition;
	private int parentPosition;

	public CustomSpinner(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public CustomSpinner(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public CustomSpinner(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public void setParentPosition(int parentPosition) {
		this.parentPosition = parentPosition;
	}

	public int getParentPosition() {
		return parentPosition;
	}

	public void setChildPosition(int childPosition) {
		this.childPosition = childPosition;
	}

	public int getChildPosition() {
		return childPosition;
	}

}
