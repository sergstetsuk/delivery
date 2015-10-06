package ua.com.vodapitna.delivery;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by sergal on 06.10.15.
 */
public class ClientsEditFragment extends DialogFragment {

    public interface EmpDialogFragmentListener {
        void onFinishDialog();
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //employeeDAO = new EmployeeDAO(getActivity());

        Bundle bundle = this.getArguments();
        //employee = bundle.getParcelable("selectedEmployee");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View customDialogView = inflater.inflate(R.layout.clients_edit_fragment,
                null);
        builder.setView(customDialogView);

        //empNameEtxt = (EditText) customDialogView.findViewById(R.id.etxt_name);
        //empSalaryEtxt = (EditText) customDialogView
        //        .findViewById(R.id.etxt_salary);
        //empDobEtxt = (EditText) customDialogView.findViewById(R.id.etxt_dob);
        //submitLayout = (LinearLayout) customDialogView
        //        .findViewById(R.id.layout_submit);
        //submitLayout.setVisibility(View.GONE);

        //setValue();

        //builder.setTitle(R.string.update_emp);
        //builder.setCancelable(false);
        /*builder.setPositiveButton(R.string.update,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        try {
                            employee.setDateOfBirth(formatter.parse(empDobEtxt.getText().toString()));
                        } catch (ParseException e) {
                            Toast.makeText(getActivity(),
                                    "Invalid date format!",
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }
                        employee.setName(empNameEtxt.getText().toString());
                        employee.setSalary(Double.parseDouble(empSalaryEtxt
                                .getText().toString()));
                        long result = employeeDAO.update(employee);
                        if (result > 0) {
                            MainActivity activity = (MainActivity) getActivity();
                            activity.onFinishDialog();
                        } else {
                            Toast.makeText(getActivity(),
                                    "Unable to update employee",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        builder.setNegativeButton(R.string.cancel,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
*/
        AlertDialog alertDialog = builder.create();

        return alertDialog;
    }
/*
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //employeeDAO = new EmployeeDAO(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.clients_edit_fragment, container,
                false);

        //findViewsById(rootView);

        //setListeners();


        return rootView;
    }
    */
}
