package ua.com.vodapitna.delivery;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

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

        final EditText clientNameEtxt = (EditText) customDialogView.findViewById(R.id.ecl_name);
        EditText clientStreetCategEtxt = (EditText) customDialogView.findViewById(R.id.ecl_cat);
        final EditText clientAddrEtxt = (EditText) customDialogView.findViewById(R.id.ecl_addr);
        final EditText clientHouseEtxt = (EditText) customDialogView.findViewById(R.id.ecl_house);
        final EditText clientOfficeEtxt = (EditText) customDialogView.findViewById(R.id.ecl_office);
        final EditText clientPhoneEtxt = (EditText) customDialogView.findViewById(R.id.ecl_phone);
        Button clientOkButton = (Button) customDialogView.findViewById(R.id.button_add);
        Button clientCancelButton = (Button) customDialogView.findViewById(R.id.button_reset);

        final String clientid = getArguments().getString("clientid");
        if(clientid != null) {
            SQLHandler mDbHandler = new SQLHandler(getContext());
            Cursor mCursor = mDbHandler.selectQuery("SELECT c.clientname, c.streetcategid, s.streetcateg, c.streetname, c.house, c.office, " +
                    "c.phone FROM clients c LEFT JOIN streetcateg s ON s.streetcategid=c.streetcategid; WHERE clientid='" + clientid + "';");

            clientPhoneEtxt.setText(mCursor.getString(mCursor.getColumnIndex("c.phone")));
        }
        //builder.setTitle(R.string.ecl_header);
/*        builder.setPositiveButton(R.string.ecl_add,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    */
            clientOkButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                 if(clientid != null) {
                    SQLHandler mDbHandler = new SQLHandler(getContext());
                    mDbHandler.executeQuery("UPDATE clients (clientname, streetcategid, streetname, house, office,phone) "
                            +"VALUES ("
                            +clientNameEtxt.getText()
                            +",1"
                            +","+clientAddrEtxt.getText()
                            +","+clientHouseEtxt.getText()
                            +","+clientOfficeEtxt.getText()
                            +","+clientPhoneEtxt.getText()
                            +" WHERE clientid='"+clientid+"');");
                }
                else {
                    SQLHandler mDbHandler = new SQLHandler(getContext());
                    mDbHandler.executeQuery("INSERT INTO clients (clientname, streetcategid, streetname, house, office, phone) "
                            +"VALUES ('"
                            +clientNameEtxt.getText()
                            +"','1"
                            +"','"+clientAddrEtxt.getText()
                            +"','"+clientHouseEtxt.getText()
                            +"','"+clientOfficeEtxt.getText()
                            +"','"+clientPhoneEtxt.getText()
                            +"');");
                }
                    dismiss();
            }
        });
        clientCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();

        return alertDialog;
    }
}
