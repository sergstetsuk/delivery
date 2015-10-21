package ua.com.vodapitna.delivery;

import android.app.Activity;
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
import android.widget.ListView;
import android.widget.Spinner;
import android.util.Log;
import android.widget.SimpleCursorAdapter;

/**
 * Created by sergal on 06.10.15.
 */
public class ClientsEditFragment extends DialogFragment {
    private Cursor mSpinnerCursor;
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
        final Spinner clientStreetCategEtxt = (Spinner) customDialogView.findViewById(R.id.ecl_cat);
        final EditText clientAddrEtxt = (EditText) customDialogView.findViewById(R.id.ecl_addr);
        final EditText clientHouseEtxt = (EditText) customDialogView.findViewById(R.id.ecl_house);
        final EditText clientOfficeEtxt = (EditText) customDialogView.findViewById(R.id.ecl_office);
        final EditText clientPhoneEtxt = (EditText) customDialogView.findViewById(R.id.ecl_phone);
        Button clientOkButton = (Button) customDialogView.findViewById(R.id.button_add);
        Button clientCancelButton = (Button) customDialogView.findViewById(R.id.button_reset);

        final String clientid = getArguments().getString("clientid");
        SQLHandler mDbHandler = new SQLHandler(getContext());
        Cursor mSpinnerCursor = mDbHandler.selectQuery("SELECT streetcategid as _id, streetcateg FROM streetcateg;");
        Log.d("WATCH",String.valueOf(mSpinnerCursor.getCount()));
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getContext(),android.R.layout.simple_spinner_item,
                                        mSpinnerCursor, new String[]{"streetcateg"},
                                        new int[]{android.R.id.text1});
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        clientStreetCategEtxt.setAdapter(adapter);

        
        if(clientid != null) {
            Cursor mCursor = mDbHandler.selectQuery("SELECT c.clientname, c.streetcategid, c.streetname, c.house, c.office, c.phone FROM clients c WHERE clientid=" + clientid + ";");
            mCursor.moveToFirst();
            clientNameEtxt.setText(mCursor.getString(mCursor.getColumnIndex("clientname")));
            Log.d("WATCH setid",mCursor.getString(mCursor.getColumnIndex("streetcategid")));
            
            clientStreetCategEtxt.setSelection(0); //by default in not found

                int seekid = mCursor.getInt(mCursor.getColumnIndex("streetcategid"));
            
                for (int pos = clientStreetCategEtxt.getAdapter().getCount(); pos>=0; pos--){
                    if (clientStreetCategEtxt.getAdapter().getItemId(pos) == seekid){
                        clientStreetCategEtxt.setSelection(pos);
                        break;
                    }
                }

            
            clientAddrEtxt.setText(mCursor.getString(mCursor.getColumnIndex("streetname")));
            clientHouseEtxt.setText(mCursor.getString(mCursor.getColumnIndex("house")));
            clientOfficeEtxt.setText(mCursor.getString(mCursor.getColumnIndex("office")));
            clientPhoneEtxt.setText(mCursor.getString(mCursor.getColumnIndex("phone")));
            mCursor.close();
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
                    Log.d("WATCH id",String.valueOf(clientStreetCategEtxt.getSelectedItemId()));
                    String query = "UPDATE clients SET "
                            + "clientname ='" + clientNameEtxt.getText() + "'"
                            + ",streetcategid='" + /*"1'" //*/clientStreetCategEtxt.getSelectedItemId() +"'"
                            + ",streetname='" + clientAddrEtxt.getText() + "'"
                            + ",house='" + clientHouseEtxt.getText() + "'"
                            + ",office='" + clientOfficeEtxt.getText() + "'"
                            + ",phone='" + clientPhoneEtxt.getText() + "'"
                            + "WHERE clientid='"+clientid+"';";
                    mDbHandler.executeQuery(query);
                    Log.d("vodapitna.SQLWATCH",query);
                    
                }
                else {
                    SQLHandler mDbHandler = new SQLHandler(getContext());
                    String query = "INSERT INTO clients (clientname, streetcategid, streetname, house, office, phone) "
                            +"VALUES ("
                            +"'"+clientNameEtxt.getText()+"'"
                            +",'"+clientStreetCategEtxt.getSelectedItemId()+"'"
                            +",'"+clientAddrEtxt.getText()+"'"
                            +",'"+clientHouseEtxt.getText()+"'"
                            +",'"+clientOfficeEtxt.getText()+"'"
                            +",'"+clientPhoneEtxt.getText()+"'"
                            +");";
                    mDbHandler.executeQuery(query);
                    Log.d("vodapitna.SQLWATCH",query);
                }
                    ListView lv = (ListView) getActivity().findViewById(R.id.lvContactList);
                    ClientsAdapter ad = (ClientsAdapter) lv.getAdapter();
                    ad.notifyDataSetChanged();
                    dismiss();
                }
        });
        clientCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                //Data not changed. No need to update view
            }
        });

        AlertDialog alertDialog = builder.create();

        return alertDialog;
    }
}
