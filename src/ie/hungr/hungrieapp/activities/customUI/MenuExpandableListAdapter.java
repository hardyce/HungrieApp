package ie.hungr.hungrieapp.activities.customUI;

import ie.hungr.hungrieapp.R;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class MenuExpandableListAdapter extends BaseExpandableListAdapter {

	private LayoutInflater inflater;
	private ArrayList<ItemParent> parents;
	private Context context;
	private static boolean alowListener = false;

	public MenuExpandableListAdapter(Context context,
			ArrayList<ItemParent> parents) {
		inflater = LayoutInflater.from(context);
		this.parents = parents;
		this.context = context;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return parents.get(groupPosition).getChildren().get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.menu_list_parent, parent,
					false);
		}

		TextView tvMenuSectionName = (TextView) convertView
				.findViewById(R.id.tvMenuSectionName);
		tvMenuSectionName.setText(parents.get(groupPosition).getName());

		return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.menu_list_child, parent,
					false);
		}

		CustomSpinner spItemQuantity = (CustomSpinner) convertView
				.findViewById(R.id.spQuantity);
		spItemQuantity.setOnItemSelectedListener(null);

		TextView tvMenuItemName = (TextView) convertView
				.findViewById(R.id.tvMenuItemName);
		TextView tvMenuItemPrice = (TextView) convertView
				.findViewById(R.id.tvMenuItemPrice);

		// filling row info
		tvMenuItemName.setText(parents.get(groupPosition).getChildren()
				.get(childPosition).getName());
		Double itemPrice = parents.get(groupPosition).getChildren()
				.get(childPosition).getPrice();
		tvMenuItemPrice.setText(String.valueOf(itemPrice) + "€");

		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				context, R.array.numbers, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spItemQuantity.setAdapter(adapter);

		// restore selected quantity
		// spItemQuantity.setSelection(parents.get(groupPosition).getChildren().get(childPosition).getQuantity());

		/* Spinner listener */
		spItemQuantity.setOnItemSelectedListener(new CustomOnItemListener());

		alowListener = true;

		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return parents.get(groupPosition).getChildren().size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return parents.get(groupPosition).getName();
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return parents.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}

	public ArrayList<ItemParent> getListInformation() {
		return parents;
	}

	public class CustomOnItemListener implements
			AdapterView.OnItemSelectedListener {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {

			Spinner spinner = (Spinner) view.getParent();
			if (spinner instanceof CustomSpinner) {
				CustomSpinner mySpinner = (CustomSpinner) spinner;

				// get position info
				int childPosition = mySpinner.getChildPosition();
				int parentPosition = mySpinner.getParentPosition();

				if (MenuExpandableListAdapter.alowListener)
					parents.get(parentPosition).getChildren()
							.get(childPosition)
							.setQuantity((mySpinner.getSelectedItemPosition()));

			} else {
				Log.d("Spinner Error", "Spinner is not CustomSpinner");
			}

		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub

		}

	}

	@Override
	public void onGroupCollapsed(int groupPosition) {
		// TODO Auto-generated method stub
		super.onGroupCollapsed(groupPosition);

		alowListener = false;

	}

	@Override
	public void onGroupExpanded(int groupPosition) {
		// TODO Auto-generated method stub
		super.onGroupExpanded(groupPosition);

	}

}
